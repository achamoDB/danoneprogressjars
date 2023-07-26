// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import com.progress.common.exception.ExceptionMessageAdapter;
import java.io.IOException;

public class EhnLog implements IEhnLog
{
    public static final int LOGGING_OFF = 0;
    public static final int LOGGING_ERRORS = 1;
    public static final int LOGGING_BASIC = 2;
    public static final int LOGGING_VERBOSE = 3;
    public static final int LOGGING_EXTENDED = 4;
    public static final int LOGGING_TERSE = 2;
    public static final int LOGGING_DIAG = 4;
    public static final long LOGENT_NONE = 0L;
    public static final int LOG_OVERWRITE = 0;
    public static final int LOG_APPEND = 1;
    public static final int LOGTHRESHOLD_NONE = 0;
    public static final int NUMLOGFILES_NONE = 0;
    public static final int DEST_NONE = 0;
    public static final int DEST_DISPLAY = 1;
    public static final int DEST_LOGFILE = 2;
    public static final int DEST_BOTH = 3;
    public static final int MAXLOGENTRIES = 64;
    public static final int SL_NONE = 0;
    public static final String CI_NONE = "---";
    public static final String EI_NONE = "---";
    private static final long LOGTHRESHOLD_LIMIT_LOW = 500000L;
    private static final long LOGTHRESHOLD_LIMIT_HIGH = 2147438647L;
    private int loggingLevel;
    private long logEntries;
    private int numLogFiles;
    private long logThreshold;
    private boolean subLevelUsed;
    private byte[] logSubLevels;
    private boolean loggingIsOn;
    private LogHandler fileHandler;
    private LogHandler dispHandler;
    private LogWriter fileWriter;
    private LogWriter dispWriter;
    
    public EhnLog() {
        this.loggingLevel = 0;
        this.logEntries = 0L;
        this.numLogFiles = 0;
        this.subLevelUsed = false;
        this.logSubLevels = new byte[64];
        this.loggingIsOn = false;
        this.dispWriter = LogWriter.NULL;
        this.fileWriter = LogWriter.NULL;
        this.dispHandler = new DefaultLogHandler(this.dispWriter);
        this.fileHandler = new DefaultLogHandler(this.fileWriter);
    }
    
    public EhnLog(final String s, final int n, final int loggingLevel, final long logThreshold, final int numLogFiles, final long logEntries) throws IOException {
        this.loggingLevel = loggingLevel;
        this.logEntries = logEntries;
        this.subLevelUsed = false;
        this.logSubLevels = new byte[64];
        if (numLogFiles == 0 || numLogFiles > 1) {
            this.numLogFiles = numLogFiles;
        }
        else {
            this.numLogFiles = 0;
        }
        if (logThreshold == 0L || (logThreshold >= 500000L && logThreshold < 2147438647L)) {
            this.logThreshold = logThreshold;
        }
        else {
            this.logThreshold = 0L;
        }
        this.loggingIsOn = (loggingLevel > 0 || logEntries > 0L);
        this.dispWriter = this.getDisplayWriter();
        this.fileWriter = this.getFileWriter(s, n, this.logThreshold, this.numLogFiles, 2, 6);
        this.dispHandler = new DefaultLogHandler(this.dispWriter);
        this.fileHandler = new DefaultLogHandler(this.fileWriter);
        this.ehnLogWrite(2, 1, "---", "---", this.getCurrentLogFileName() + " opened.");
        if (numLogFiles != this.numLogFiles) {
            this.ehnLogWrite(2, 1, "---", "---", "Invalid numLogFiles value. Setting its value to zero.");
        }
        if (logThreshold != this.logThreshold) {
            this.ehnLogWrite(2, 1, "---", "---", "Invalid logThreshold value. Setting its value to zero.");
        }
    }
    
    public EhnLog(final IEhnLog ehnLog) {
        this.loggingLevel = ehnLog.getLoggingLevel();
        this.logEntries = ehnLog.getLogEntries();
        this.numLogFiles = ehnLog.getNumLogFiles();
        this.subLevelUsed = ehnLog.getSubLevelUsed();
        this.logSubLevels = ehnLog.getLogSubLevels();
        this.loggingIsOn = ehnLog.getLoggingIsOn();
        this.dispWriter = ehnLog.getDispWriter();
        this.fileWriter = ehnLog.getFileWriter();
        this.dispHandler = ehnLog.getDispHandler();
        this.fileHandler = ehnLog.getFileHandler();
    }
    
    public EhnLog(final IEhnLog ehnLog, final int loggingLevel, final long logEntries) {
        this(ehnLog);
        this.loggingLevel = loggingLevel;
        this.logEntries = logEntries;
        this.subLevelUsed = false;
    }
    
    public EhnLog(final LogHandler fileHandler, final int loggingLevel, final long logEntries) throws IOException {
        this.loggingLevel = loggingLevel;
        this.logEntries = logEntries;
        this.subLevelUsed = false;
        this.loggingIsOn = (loggingLevel > 0);
        this.logSubLevels = new byte[64];
        this.dispWriter = this.getDisplayWriter();
        this.fileWriter = LogWriter.NULL;
        this.dispHandler = new DefaultLogHandler(this.dispWriter);
        this.fileHandler = fileHandler;
    }
    
    public int getLoggingLevel() {
        return this.loggingLevel;
    }
    
    public int setLoggingLevel(final int loggingLevel) {
        final int loggingLevel2 = this.loggingLevel;
        synchronized (this) {
            this.loggingLevel = loggingLevel;
            for (int i = 0; i < 64; ++i) {
                this.logSubLevels[i] = (byte)this.loggingLevel;
            }
            if (this.loggingLevel < 2) {
                this.subLevelUsed = false;
            }
            this.loggingIsOn = (this.loggingLevel > 0 || this.subLevelUsed);
        }
        return loggingLevel2;
    }
    
    public long getLogEntries() {
        return this.logEntries;
    }
    
    public int getNumLogFiles() {
        return this.numLogFiles;
    }
    
    public boolean getSubLevelUsed() {
        return this.subLevelUsed;
    }
    
    public byte[] getLogSubLevels() {
        return this.logSubLevels;
    }
    
    public boolean getLoggingIsOn() {
        return this.loggingIsOn;
    }
    
    public LogWriter getDispWriter() {
        return this.dispWriter;
    }
    
    public LogWriter getFileWriter() {
        return this.fileWriter;
    }
    
    public LogHandler getDispHandler() {
        return this.dispHandler;
    }
    
    public LogHandler getFileHandler() {
        return this.fileHandler;
    }
    
    public String getCurrentLogFileName() {
        if (this.fileWriter != null) {
            return this.fileWriter.getCurrentLogFileName();
        }
        return new String("");
    }
    
    public boolean registerThresholdEventHandler(final ILogEvntHandler logEvntHandler) {
        return this.fileWriter != null && this.fileWriter.registerThresholdEventHandler(logEvntHandler);
    }
    
    public boolean ifLogLevel(final int n) {
        return this.loggingLevel >= n;
    }
    
    public long setLogEntries(final long logEntries, final boolean subLevelUsed, final byte[] array) {
        final long logEntries2 = this.logEntries;
        synchronized (this) {
            this.logEntries = logEntries;
            this.subLevelUsed = subLevelUsed;
            for (int i = 0; i < 64; ++i) {
                this.logSubLevels[i] = array[i];
            }
            if (this.loggingLevel > 0 || this.subLevelUsed) {
                this.loggingIsOn = true;
            }
            else {
                this.loggingIsOn = false;
            }
        }
        return logEntries2;
    }
    
    public void chgLogSettings(final int loggingLevel, final long logEntries, final boolean subLevelUsed, final byte[] array) {
        synchronized (this) {
            this.loggingLevel = loggingLevel;
            this.logEntries = logEntries;
            this.subLevelUsed = subLevelUsed;
            for (int i = 0; i < 64; ++i) {
                this.logSubLevels[i] = array[i];
            }
            if (this.loggingLevel > 0 || this.subLevelUsed) {
                this.loggingIsOn = true;
            }
            else {
                this.loggingIsOn = false;
            }
        }
    }
    
    public boolean ifLogIt(final int n, final long n2, final int n3) {
        boolean b = false;
        final long n4 = this.logEntries & n2;
        if ((this.logEntries & n2) == n2) {
            if (this.subLevelUsed) {
                b = (n3 < 64 && this.logSubLevels[n3] >= n);
            }
            else {
                b = (this.loggingLevel >= n);
            }
        }
        return b;
    }
    
    public boolean ifLogBasic(final long n, final int n2) {
        boolean b = false;
        if ((this.logEntries & n) == n) {
            if (this.subLevelUsed) {
                b = (n2 < 64 && this.logSubLevels[n2] >= 2);
            }
            else {
                b = (this.loggingLevel >= 2);
            }
        }
        return b;
    }
    
    public boolean ifLogVerbose(final long n, final int n2) {
        boolean b = false;
        if ((this.logEntries & n) == n) {
            if (this.subLevelUsed) {
                b = (n2 < 64 && this.logSubLevels[n2] >= 3);
            }
            else {
                b = (this.loggingLevel >= 3);
            }
        }
        return b;
    }
    
    public boolean ifLogExtended(final long n, final int n2) {
        boolean b = false;
        if ((this.logEntries & n) == n) {
            if (this.subLevelUsed) {
                b = (n2 < 64 && this.logSubLevels[n2] >= 4);
            }
            else {
                b = (this.loggingLevel >= 4);
            }
        }
        return b;
    }
    
    public void ehnLogClose() {
        synchronized (this.fileHandler) {
            if (this.fileHandler instanceof DefaultLogHandler) {
                this.ehnLogWrite(2, 1, "---", "---", "Log Closed");
            }
            this.fileWriter.stop();
        }
        this.dispWriter.stop();
    }
    
    public void ehnLogWrite(final int n, final int n2, final String s, final String s2, final String s3) {
        if (!this.loggingIsOn) {
            return;
        }
        if (this.isToDisplay(n)) {
            if (this.dispWriter == LogWriter.NULL) {
                try {
                    this.dispWriter = this.getDisplayWriter();
                }
                catch (IOException ex) {
                    this.dispWriter = LogWriter.NULL;
                }
                this.dispHandler = new DefaultLogHandler(this.dispWriter);
            }
            this.dispHandler.log(n2, s, s2, s3);
        }
        if (this.isToFile(n)) {
            this.fileHandler.log(n2, s, s2, s3);
        }
    }
    
    public void ehnLogWriteN(final int n, final int n2, final String s, final String s2, final long n3, final Object[] array) {
        this.ehnLogWrite(n, n2, s, s2, ExceptionMessageAdapter.getMessage(n3, array));
    }
    
    public void ehnLogDump(final int n, final int n2, final String s, final String s2, final String s3, final byte[] array, final int n3) {
        if (!this.loggingIsOn) {
            return;
        }
        if (this.isToDisplay(n)) {
            this.dispHandler.log(n2, s, s2, s3, array, n3);
        }
        if (this.isToFile(n)) {
            this.fileHandler.log(n2, s, s2, s3, array, n3);
        }
    }
    
    public void ehnLogStackTrace(final int n, final int n2, final String s, final String s2, final String s3, final Throwable t) {
        if (!this.loggingIsOn) {
            return;
        }
        if (this.isToDisplay(n)) {
            this.dispHandler.log(n2, s, s2, s3, t);
        }
        if (this.isToFile(n)) {
            this.fileHandler.log(n2, s, s2, s3, t);
        }
    }
    
    public void ehnLogStackTraceN(final int n, final int n2, final String s, final String s2, final long n3, final Object[] array, final Throwable t) {
        this.ehnLogStackTrace(n, n2, s, s2, ExceptionMessageAdapter.getMessage(n3, array), t);
    }
    
    private boolean isToDisplay(final int n) {
        return (n & 0x1) != 0x0;
    }
    
    private boolean isToFile(final int n) {
        return (n & 0x2) != 0x0;
    }
    
    private LogWriter getFileWriter(final String s, final int n, final long n2, final int n3, final int n4, final int n5) throws IOException {
        final boolean b = n == 1;
        if (!this.loggingIsOn) {
            return LogWriter.NULL;
        }
        if (s == null) {
            return LogWriter.NULL;
        }
        final FileLogWriter fileLogWriter = new FileLogWriter(s, b, n2, n3, n4, n5);
        fileLogWriter.start();
        return fileLogWriter;
    }
    
    private LogWriter getDisplayWriter() throws IOException {
        if (!this.loggingIsOn) {
            return LogWriter.NULL;
        }
        final StreamLogWriter streamLogWriter = new StreamLogWriter();
        streamLogWriter.start();
        return streamLogWriter;
    }
    
    public static class Test
    {
        public static void main(final String[] array) throws IOException {
            final String s = (array.length > 0) ? array[0] : "ehnlogtest.log";
            EhnLog ehnLog;
            try {
                ehnLog = new EhnLog(s, 1, 2, 0L, 0, 0L);
            }
            catch (IOException obj) {
                System.err.println("Error opening file " + obj);
                ehnLog = new EhnLog();
            }
            tdmain(ehnLog);
            ehnLog.ehnLogClose();
        }
        
        public static void tdmain(final EhnLog ehnLog) {
            ehnLog.ehnLogWrite(2, 0, "---", "---", "Come and listen to a story 'bout a man named Jed");
            if (ehnLog.ifLogBasic(1L, 0)) {
                ehnLog.ehnLogWrite(2, 0, "---", "---", "if this message appears in the log, there is a problem!");
            }
            ehnLog.setLogEntries(1L, false, new byte[64]);
            if (ehnLog.ifLogVerbose(1L, 0)) {
                ehnLog.ehnLogWrite(2, 0, "---", "---", "if this message appears in the log, there is a bug!");
            }
            ehnLog.chgLogSettings(3, 2L, false, new byte[64]);
            if (ehnLog.ifLogVerbose(2L, 1)) {
                ehnLog.ehnLogWrite(2, 0, "---", "---", "then one day he was shootin at some food");
            }
            if (ehnLog.ifLogExtended(1L, 0)) {
                ehnLog.ehnLogWrite(2, 0, "---", "---", "this message should not appear in the log");
            }
            ehnLog.chgLogSettings(4, 2L, false, new byte[64]);
            if (ehnLog.ifLogBasic(1L, 0)) {
                ehnLog.ehnLogWrite(2, 0, "---", "---", "this message should not appear in the log either");
            }
            if (ehnLog.ifLogBasic(2L, 1)) {
                ehnLog.ehnLogWrite(2, 0, "---", "---", "up through the ground come a bubblin' \ncrude\n\n");
            }
            ehnLog.ehnLogDump(2, 0, "---", "---", "Oil, \n\n\nthat is ... ", "black gold ... Texas tea".getBytes(), "black gold ... Texas tea".length());
            ehnLog.ehnLogStackTrace(2, 0, "---", "---", "This is a message", new Exception("This is an exception message"));
        }
    }
}

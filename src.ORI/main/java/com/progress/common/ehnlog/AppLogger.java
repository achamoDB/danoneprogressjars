// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import com.progress.common.exception.ExceptionMessageAdapter;
import java.io.IOException;
import java.io.Serializable;

public class AppLogger extends EhnLog implements IAppLogger, Serializable
{
    protected String m_execEnvID;
    protected LogContext m_logContext;
    private int m_loggingLevel;
    private long m_logEntries;
    
    public static void main(final String[] array) throws IOException, LogException {
        final AppLogger appLogger = new AppLogger("Test1", 1, 1, 0L, 3, "WSADefault:2,aaa", "Instance", "Wsa");
        if (appLogger.ifLogExtended(1L, 0)) {
            appLogger.logExtended(0, "Hello (bug)");
        }
        if (appLogger.ifLogVerbose(2L, 1)) {
            appLogger.logVerbose(1, "Goodbye (bug)");
        }
        if (appLogger.ifLogBasic(2L, 1)) {
            appLogger.logBasic(1, "This is the one!");
        }
        appLogger.logError("This is the two!");
        if (appLogger.ifLogBasic(8589934592L, 33)) {
            appLogger.logBasic(33, "This is UBROKER - should not appear!");
        }
        appLogger.logStackTrace(1, "This is a message", new Exception("This is an exception message"));
        appLogger.logClose();
        final AppLogger appLogger2 = new AppLogger();
        appLogger2.setLoggingLevel(1);
        appLogger2.setLogEntries(525L, false, new byte[64]);
        appLogger2.chgLogSettings(2, 512L, false, new byte[64]);
        if (appLogger2.ifLogExtended(2L, 1)) {
            appLogger2.logExtended(4, "Hello");
        }
        if (appLogger2.ifLogVerbose(2L, 1)) {
            appLogger2.logVerbose(4, "Goodbye");
        }
        if (appLogger2.ifLogBasic(2L, 1)) {
            appLogger2.logBasic(4, "This is the one!");
        }
        appLogger2.logError("This is the two again!");
        appLogger2.logClose();
        final AppLogger appLogger3 = new AppLogger(appLogger2);
        appLogger3.logExtended(1, "Hello");
        appLogger3.logVerbose(1, "Goodbye");
        appLogger3.logBasic(1, "This is the one!");
        appLogger3.logError("This is the two!");
        appLogger3.logClose();
        final AppLogger appLogger4 = new AppLogger("Test2", 0, 4, 0L, 3, "Default", "GenInstance", "Sample");
        if (appLogger4.ifLogExtended(32L, 5)) {
            appLogger4.logExtended(5, "Hi There (bug)");
        }
        if (appLogger4.ifLogBasic(32L, 5)) {
            appLogger4.logBasic(5, "Hi There (bug)");
        }
        appLogger4.logError("This is another long rambling message like most real ones are");
        appLogger4.logClose();
        final AppLogger appLogger5 = new AppLogger("Test3", 0, 2, 0L, 4, "Default", "GenInstance", "Initial");
        if (appLogger5.ifLogExtended(128L, 7)) {
            appLogger5.logExtended(7, "this message should not appear in the log");
        }
        appLogger5.logError("This is another long rambling message like most real ones are");
        appLogger5.logClose();
    }
    
    public AppLogger(final String s, final int n, final int n2, final long n3, final int n4, final String logEntries, final String execEnvId, final String logContext) throws IOException {
        super(s, n, n2, n3, n4, (logEntries != null) ? logEntries.length() : 0);
        this.m_execEnvID = "";
        this.m_logContext = null;
        try {
            this.m_loggingLevel = n2;
            this.setLogContext(logContext);
            this.setExecEnvId(execEnvId);
            this.writeLog("Logging level set to = " + n2);
            if (this.m_logContext != null && logEntries != null && logEntries.length() > 0) {
                this.setLogEntries(logEntries);
            }
        }
        catch (LogException ex) {
            this.writeLog(ex.getMessage());
        }
    }
    
    public AppLogger() {
        this.m_execEnvID = "";
        this.m_logContext = null;
        try {
            this.setLogContext("Initial");
            this.m_execEnvID = "App-Startup";
        }
        catch (LogException ex) {}
    }
    
    public AppLogger(final IAppLogger appLogger) {
        super(appLogger);
        this.m_execEnvID = "";
        this.m_logContext = null;
        try {
            this.setLogContext(appLogger.getLogContext().getLogContextName());
            this.m_execEnvID = new String(appLogger.getExecEnvId());
            this.m_loggingLevel = appLogger.getLoggingLevel();
            this.m_logEntries = appLogger.getLogEntries();
        }
        catch (LogException ex) {}
    }
    
    public AppLogger(final LogHandler logHandler, final int loggingLevel, final long logEntries, final String execEnvId, final String logContext) throws IOException {
        super(logHandler, loggingLevel, logEntries);
        this.m_execEnvID = "";
        this.m_logContext = null;
        try {
            this.setLogContext(logContext);
            this.setExecEnvId(execEnvId);
            this.m_loggingLevel = loggingLevel;
            this.m_logEntries = logEntries;
        }
        catch (LogException ex) {
            this.writeLog(ex.getMessage());
        }
    }
    
    public int setLoggingLevel(final int n) {
        final int setLoggingLevel = super.setLoggingLevel(n);
        this.m_loggingLevel = n;
        return setLoggingLevel;
    }
    
    public long resetLogEntries(final String logEntries) {
        this.m_logContext.resetLogEntryTypes();
        return this.setLogEntries(logEntries);
    }
    
    public long setLogEntries(final String s) {
        this.writeLog(this.m_logContext.parseEntrytypeString(s, this.m_loggingLevel));
        return this.setLogEntriesProp(this.m_logContext.getLogEntries(), this.m_logContext.getLogSubLevelUsed(), this.m_logContext.getLogSublevelArray());
    }
    
    private long setLogEntriesProp(final long logEntries, final boolean b, final byte[] array) {
        final long setLogEntries = super.setLogEntries(logEntries, b, array);
        this.m_logEntries = logEntries;
        return setLogEntries;
    }
    
    public void logExtended(final int n, final String s) {
        this.writeLog(4, n, s);
    }
    
    public void logExtended(final int n, final long n2, final Object[] array) {
        this.writeLog(4, n, n2, array);
    }
    
    public void logExtended(final int n, final String s, final Object[] array) {
        this.writeLog(4, n, s, array);
    }
    
    public void logAssert(final boolean b, final int n, final String s) {
        if (!b) {
            this.writeLog(4, n, s);
        }
    }
    
    public void logVerbose(final int n, final String s) {
        this.writeLog(3, n, s);
    }
    
    public void logVerbose(final int n, final long n2, final Object[] array) {
        this.writeLog(3, n, n2, array);
    }
    
    public void logVerbose(final int n, final String s, final Object[] array) {
        this.writeLog(3, n, s, array);
    }
    
    public void logBasic(final int n, final String s) {
        this.writeLog(2, n, s);
    }
    
    public void logBasic(final int n, final long n2, final Object[] array) {
        this.writeLog(2, n, n2, array);
    }
    
    public void logBasic(final int n, final String s, final Object[] array) {
        this.writeLog(2, n, s, array);
    }
    
    public void logError(final String s) {
        this.writeLog(1, 0, s);
    }
    
    public void logError(final long n, final Object[] array) {
        this.writeLog(1, 0, n, array);
    }
    
    public void logError(final String s, final Object[] array) {
        this.writeLog(1, 0, s, array);
    }
    
    public void logWithThisLevel(final int n, final int n2, final String s) {
        this.writeLog(n, n2, s);
    }
    
    public void logStackTrace(final String s, final Throwable t) {
        super.ehnLogStackTrace(2, 1, this.m_execEnvID, this.m_logContext.getErrorStringTag(), s, t);
    }
    
    public void logStackTrace(final long n, final Object[] array, final Throwable t) {
        super.ehnLogStackTrace(2, 1, this.m_execEnvID, this.m_logContext.getErrorStringTag(), formatMessage(n, array), t);
    }
    
    public void logStackTrace(final int n, final String s, final Throwable t) {
        super.ehnLogStackTrace(2, 1, this.m_execEnvID, this.m_logContext.getEntrytypeName(n), s, t);
    }
    
    public void logStackTrace(final int n, final long n2, final Object[] array, final Throwable t) {
        super.ehnLogStackTrace(2, 1, this.m_execEnvID, this.m_logContext.getEntrytypeName(n), formatMessage(n2, array), t);
    }
    
    public void logDump(final int n, final int n2, final String s, final byte[] array, final int n3) {
        super.ehnLogDump(2, n, this.m_execEnvID, this.m_logContext.getEntrytypeName(n2), s, array, n3);
    }
    
    public void logWriteMessage(final int n, final int n2, final String s, final String s2, final String s3) {
        super.ehnLogWrite(n, n2, s, s2, s3);
    }
    
    public void logWriteMessage(final int n, final int n2, final String s, final String s2, final String s3, final Throwable t) {
        super.ehnLogStackTrace(n, n2, s, s2, s3, t);
    }
    
    public LogContext getLogContext() {
        return this.m_logContext;
    }
    
    public String getExecEnvId() {
        return this.m_execEnvID;
    }
    
    public void setExecEnvId(final String original) throws NullPointerException {
        if (null == original) {
            throw new NullPointerException("Null execution env id names are not allowed.");
        }
        this.m_execEnvID = new String(original);
    }
    
    public void setLogContextHdr(final String s, final Object[] array) {
        if (this.m_logContext != null) {
            this.m_logContext.setMsgHdr(s, array);
        }
    }
    
    public void setLogContextHdr(final String msgHdr) {
        if (this.m_logContext != null) {
            this.m_logContext.setMsgHdr(msgHdr);
        }
    }
    
    public String getLogContextHdr() {
        if (this.m_logContext != null) {
            return this.m_logContext.getMsgHdr();
        }
        return null;
    }
    
    public void logClose() {
        super.ehnLogClose();
    }
    
    public long logGetEntrytypeBit(final int n) {
        if (this.m_logContext == null) {
            return 0L;
        }
        return this.m_logContext.getEntrytypeBitAt(n);
    }
    
    protected void setLogContext(final String str) throws LogException {
        this.m_logContext = LogContextFactory.createLogContext(str);
        if (this.m_logContext == null) {
            throw new LogException("A log context required for application logger. Can't use context " + str);
        }
        this.m_logContext.initEntrytypeNames();
    }
    
    public String nameAt(final int n) throws ArrayIndexOutOfBoundsException {
        String entrytypeName;
        try {
            entrytypeName = this.m_logContext.entrytypeNameAt(n);
        }
        catch (LogException ex) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return entrytypeName;
    }
    
    public static String formatMessage(final String s, final Object[] array) {
        String message = null;
        try {
            message = ExceptionMessageAdapter.getMessage(s, array);
        }
        catch (Exception ex) {
            System.err.println("Error in AppLogger: " + ex.toString());
        }
        return message;
    }
    
    public static String formatMessage(final long n, final Object[] array) {
        String message = null;
        try {
            message = ExceptionMessageAdapter.getMessage(n, array);
        }
        catch (Exception ex) {
            System.err.println("Error in AppLogger: " + ex.toString());
        }
        return message;
    }
    
    protected void writeLog(final int n, final int n2, final String str) {
        try {
            String s;
            if (n == 1) {
                s = this.m_logContext.getErrorStringTag();
            }
            else {
                s = this.m_logContext.getEntrytypeName(n2);
            }
            super.ehnLogWrite(2, n, this.m_execEnvID, s, this.m_logContext.getMsgHdr() + str);
        }
        catch (Exception ex) {
            System.err.println("Error in AppLogger: " + ex.toString());
        }
    }
    
    protected void writeLog(final String s) {
        try {
            super.ehnLogWrite(2, 1, "---", "---", s);
        }
        catch (Exception ex) {
            System.err.println("Error in AppLogger: " + ex.toString());
        }
    }
    
    protected void writeLog(final int n, final int n2, final long n3, final Object[] array) {
        try {
            final String string = this.m_logContext.getMsgHdr() + ExceptionMessageAdapter.getMessage(n3, array);
            String s;
            if (n == 1) {
                s = this.m_logContext.getErrorStringTag();
            }
            else {
                s = this.m_logContext.getEntrytypeName(n2);
            }
            super.ehnLogWrite(2, n, this.m_execEnvID, s, string);
        }
        catch (Exception ex) {
            System.err.println("Error in AppLogger: " + ex.toString());
        }
    }
    
    protected void writeLog(final int n, final int n2, final String s, final Object[] array) {
        try {
            final String string = this.m_logContext.getMsgHdr() + ExceptionMessageAdapter.getMessage(s, array);
            String s2;
            if (n == 1) {
                s2 = this.m_logContext.getErrorStringTag();
            }
            else {
                s2 = this.m_logContext.getEntrytypeName(n2);
            }
            super.ehnLogWrite(2, n, this.m_execEnvID, s2, string);
        }
        catch (Exception ex) {
            System.err.println("Error in AppLogger: " + ex.toString());
        }
    }
}

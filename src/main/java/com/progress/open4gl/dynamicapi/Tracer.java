// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.common.ehnlog.LogHandler;
import com.progress.common.ehnlog.LogWriter;
import com.progress.common.ehnlog.LogContext;
import com.progress.open4gl.RunTimeProperties;
import com.progress.common.ehnlog.ILogEvntHandler;
import com.progress.common.ehnlog.AppLogger;
import com.progress.common.ehnlog.IAppLogger;
import java.io.Serializable;

public class Tracer implements Serializable, IAppLogger
{
    public static final String DEF_EXEC_ENV_ID = "O4GL ";
    public static final String DEF_SUBSYSTEM_ID = "Trace";
    public static final String O4GLRT_VERSIONID = "O4GLRT Version 10.2A (01/28/08)";
    private int m_traceLevel;
    private IAppLogger m_log;
    private boolean m_isTracing;
    private int m_loggingLevel;
    private String m_execEnvID;
    private String m_subsystemID;
    
    public Tracer() {
        this.m_log = new AppLogger();
        this.m_traceLevel = 0;
        this.m_isTracing = false;
        this.m_loggingLevel = 2;
        this.m_execEnvID = "O4GL ";
        this.m_subsystemID = "Trace";
    }
    
    int getTraceLevel() {
        return this.m_traceLevel;
    }
    
    public void startTrace() {
        this.startTrace(null, 2);
    }
    
    public void startTrace(final int n) {
        this.startTrace(null, n);
    }
    
    public void startTrace(final String s) {
        this.startTrace(s, 2);
    }
    
    public String getCurrentLogFileName() {
        if (this.m_log != null) {
            return this.m_log.getCurrentLogFileName();
        }
        return new String("");
    }
    
    public boolean registerThresholdEventHandler(final ILogEvntHandler logEvntHandler) {
        return false;
    }
    
    public void startTrace(final String str, final int traceLevel) {
        this.m_traceLevel = traceLevel;
        if (this.m_isTracing && this.m_log != null) {
            this.m_log.logClose();
            this.m_log = new AppLogger();
            this.m_isTracing = false;
        }
        this.m_execEnvID = "O4GL ";
        this.m_subsystemID = "Trace";
        try {
            if (traceLevel > 0 && str != null) {
                this.m_log = new AppLogger(str, 1, traceLevel, 0L, 0, RunTimeProperties.getLogEntryTypes(), this.m_execEnvID, "O4gl");
                this.m_isTracing = true;
                this.print("O4GLRT Version 10.2A (01/28/08)", 1);
            }
        }
        catch (Throwable t) {
            System.err.println("cannot open file " + str + " for tracing");
            System.err.println("Trace messages will go to standard error.");
            this.m_log = new AppLogger();
            this.m_isTracing = false;
        }
    }
    
    public void print(final String s) {
        this.print(s, 2);
    }
    
    public void print(final String x, final int n) {
        if (this.m_traceLevel < n) {
            return;
        }
        if (this.m_isTracing && this.m_log != null) {
            if (this.m_log.ifLogIt(n, 1L, 0)) {
                this.m_log.logWriteMessage(2, n, this.m_execEnvID, this.m_subsystemID, x);
            }
        }
        else {
            System.err.println(x);
        }
    }
    
    public void print(final Throwable t) {
        this.print(t, 2);
    }
    
    public void print(final Throwable t, final int n) {
        if (t == null || this.m_traceLevel < n) {
            return;
        }
        if (this.m_isTracing && this.m_log != null) {
            if (this.m_log.ifLogIt(n, 1L, 0)) {
                this.m_log.logWriteMessage(2, n, this.m_execEnvID, this.m_subsystemID, t.toString(), t);
            }
        }
        else {
            t.printStackTrace();
        }
    }
    
    public void endTrace() {
        if (this.m_log != null) {
            this.m_log.logClose();
        }
        this.m_log = new AppLogger();
        this.m_isTracing = false;
        this.m_execEnvID = null;
        this.m_subsystemID = null;
        this.m_traceLevel = 0;
    }
    
    public int setLoggingLevel(final int loggingLevel) {
        return (this.m_log == null) ? 0 : this.m_log.setLoggingLevel(loggingLevel);
    }
    
    public long setLogEntries(final long n, final boolean b, final byte[] array) {
        return (this.m_log == null) ? 0L : this.m_log.setLogEntries(n, b, array);
    }
    
    public long getLogEntries() {
        return (this.m_log == null) ? 0L : this.m_log.getLogEntries();
    }
    
    public long setLogEntries(final String logEntries) {
        return (this.m_log == null) ? 0L : this.m_log.setLogEntries(logEntries);
    }
    
    public long resetLogEntries(final String s) {
        return (this.m_log == null) ? 0L : this.m_log.resetLogEntries(s);
    }
    
    public int getLoggingLevel() {
        return (this.m_log == null) ? 0 : this.m_log.getLoggingLevel();
    }
    
    public boolean ifLogLevel(final int n) {
        return this.m_log != null && this.m_log.ifLogLevel(n);
    }
    
    public boolean ifLogIt(final int n, final long n2, final int n3) {
        return this.m_log != null && this.m_log.ifLogIt(n, n2, n3);
    }
    
    public boolean ifLogExtended(final long n, final int n2) {
        return this.m_log != null && this.m_log.ifLogExtended(n, n2);
    }
    
    public boolean ifLogBasic(final long n, final int n2) {
        return this.m_log != null && this.m_log.ifLogBasic(n, n2);
    }
    
    public boolean ifLogVerbose(final long n, final int n2) {
        return this.m_log != null && this.m_log.ifLogVerbose(n, n2);
    }
    
    public void logExtended(final int n, final String s) {
        if (this.m_log != null) {
            this.m_log.logExtended(n, s);
        }
    }
    
    public void logExtended(final int n, final long n2, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logExtended(n, n2, array);
        }
    }
    
    public void logExtended(final int n, final String s, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logExtended(n, s, array);
        }
    }
    
    public void logAssert(final boolean b, final int n, final String s) {
        if (this.m_log != null) {
            this.m_log.logAssert(b, n, s);
        }
    }
    
    public void logBasic(final int n, final String s) {
        if (this.m_log != null) {
            this.m_log.logBasic(n, s);
        }
    }
    
    public void logBasic(final int n, final long n2, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logBasic(n, n2, array);
        }
    }
    
    public void logBasic(final int n, final String s, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logBasic(n, s, array);
        }
    }
    
    public void logVerbose(final int n, final String s) {
        if (this.m_log != null) {
            this.m_log.logVerbose(n, s);
        }
    }
    
    public void logVerbose(final int n, final long n2, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logVerbose(n, n2, array);
        }
    }
    
    public void logVerbose(final int n, final String s, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logVerbose(n, s, array);
        }
    }
    
    public void logError(final String s) {
        if (this.m_log != null) {
            this.m_log.logError(s);
        }
    }
    
    public void logError(final long n, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logError(n, array);
        }
    }
    
    public void logError(final String s, final Object[] array) {
        if (this.m_log != null) {
            this.m_log.logError(s, array);
        }
    }
    
    public void logWithThisLevel(final int n, final int n2, final String s) {
        if (this.m_log != null) {
            this.m_log.logWithThisLevel(n, n2, s);
        }
    }
    
    public void logWriteMessage(final int n, final int n2, final String s, final String s2, final String s3) {
        if (this.m_log != null) {
            this.m_log.logWriteMessage(n, n2, s, s2, s3);
        }
    }
    
    public void logWriteMessage(final int n, final int n2, final String s, final String s2, final String s3, final Throwable t) {
        if (this.m_log != null) {
            this.m_log.logWriteMessage(n, n2, s, s2, s3, t);
        }
    }
    
    public void ehnLogWrite(final int n, final int n2, final String s, final String s2, final String s3) {
        if (this.m_log != null) {
            this.m_log.ehnLogWrite(n, n2, s, s2, s3);
        }
    }
    
    public LogContext getLogContext() {
        return (this.m_log == null) ? null : this.m_log.getLogContext();
    }
    
    public String getExecEnvId() {
        return (this.m_log == null) ? null : this.m_log.getExecEnvId();
    }
    
    public void setExecEnvId(final String execEnvId) throws NullPointerException {
        if (this.m_log != null) {
            this.m_log.setExecEnvId(execEnvId);
        }
    }
    
    public void logStackTrace(final String s, final Throwable t) {
        if (this.m_log != null) {
            this.m_log.logStackTrace(s, t);
        }
    }
    
    public void logStackTrace(final long n, final Object[] array, final Throwable t) {
        if (this.m_log != null) {
            this.m_log.logStackTrace(n, array, t);
        }
    }
    
    public void logStackTrace(final int n, final String s, final Throwable t) {
        if (this.m_log != null) {
            this.m_log.logStackTrace(n, s, t);
        }
    }
    
    public void logStackTrace(final int n, final long n2, final Object[] array, final Throwable t) {
        if (this.m_log != null) {
            this.m_log.logStackTrace(n, n2, array, t);
        }
    }
    
    public void ehnLogDump(final int n, final int n2, final String s, final String s2, final String s3, final byte[] array, final int n3) {
        if (this.m_log != null) {
            this.m_log.ehnLogDump(n, n2, s, s2, s3, array, n3);
        }
    }
    
    public void ehnLogStackTrace(final int n, final int n2, final String s, final String s2, final String s3, final Throwable t) {
        if (this.m_log != null) {
            this.m_log.ehnLogStackTrace(n, n2, s, s2, s3, t);
        }
    }
    
    public void logDump(final int n, final int n2, final String s, final byte[] array, final int n3) {
        if (this.m_log != null) {
            this.m_log.logDump(n, n2, s, array, n3);
        }
    }
    
    public void logClose() {
        if (this.m_log != null) {
            this.m_log.logClose();
        }
    }
    
    public String nameAt(final int n) throws ArrayIndexOutOfBoundsException {
        return (this.m_log == null) ? null : this.m_log.nameAt(n);
    }
    
    public int getNumLogFiles() {
        return (this.m_log == null) ? 0 : this.m_log.getNumLogFiles();
    }
    
    public boolean getSubLevelUsed() {
        return this.m_log != null && this.m_log.getSubLevelUsed();
    }
    
    public byte[] getLogSubLevels() {
        return (byte[])((this.m_log == null) ? null : this.m_log.getLogSubLevels());
    }
    
    public boolean getLoggingIsOn() {
        return this.m_log != null && this.m_log.getLoggingIsOn();
    }
    
    public LogWriter getDispWriter() {
        return (this.m_log == null) ? LogWriter.NULL : this.m_log.getDispWriter();
    }
    
    public LogWriter getFileWriter() {
        return (this.m_log == null) ? LogWriter.NULL : this.m_log.getFileWriter();
    }
    
    public LogHandler getDispHandler() {
        return (this.m_log == null) ? null : this.m_log.getDispHandler();
    }
    
    public LogHandler getFileHandler() {
        return (this.m_log == null) ? null : this.m_log.getFileHandler();
    }
}

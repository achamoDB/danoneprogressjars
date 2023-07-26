// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public interface IAppLogger extends IEhnLog
{
    int setLoggingLevel(final int p0);
    
    long setLogEntries(final String p0);
    
    long resetLogEntries(final String p0);
    
    void logExtended(final int p0, final String p1);
    
    void logExtended(final int p0, final long p1, final Object[] p2);
    
    void logExtended(final int p0, final String p1, final Object[] p2);
    
    void logAssert(final boolean p0, final int p1, final String p2);
    
    void logVerbose(final int p0, final String p1);
    
    void logVerbose(final int p0, final long p1, final Object[] p2);
    
    void logVerbose(final int p0, final String p1, final Object[] p2);
    
    void logBasic(final int p0, final String p1);
    
    void logBasic(final int p0, final long p1, final Object[] p2);
    
    void logBasic(final int p0, final String p1, final Object[] p2);
    
    void logError(final String p0);
    
    void logError(final long p0, final Object[] p1);
    
    void logError(final String p0, final Object[] p1);
    
    void logWithThisLevel(final int p0, final int p1, final String p2);
    
    void logStackTrace(final String p0, final Throwable p1);
    
    void logStackTrace(final long p0, final Object[] p1, final Throwable p2);
    
    void logStackTrace(final int p0, final String p1, final Throwable p2);
    
    void logStackTrace(final int p0, final long p1, final Object[] p2, final Throwable p3);
    
    void logDump(final int p0, final int p1, final String p2, final byte[] p3, final int p4);
    
    void logWriteMessage(final int p0, final int p1, final String p2, final String p3, final String p4);
    
    void logWriteMessage(final int p0, final int p1, final String p2, final String p3, final String p4, final Throwable p5);
    
    LogContext getLogContext();
    
    String getExecEnvId();
    
    void setExecEnvId(final String p0) throws NullPointerException;
    
    void logClose();
    
    String nameAt(final int p0) throws ArrayIndexOutOfBoundsException;
}

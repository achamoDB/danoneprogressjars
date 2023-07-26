// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl;

public interface Log
{
    public static final int LOGGING_OFF = 0;
    public static final int LOGGING_ERRORS = 1;
    public static final int LOGGING_TERSE = 2;
    public static final int LOGGING_VERBOSE = 3;
    public static final int LOGGING_STATS = 4;
    public static final int LOGGING_TRACE = 36;
    
    boolean ignore(final int p0);
    
    void LogMsgln(final int p0, final boolean p1, final String p2, final String p3);
    
    void LogStackTrace(final int p0, final boolean p1, final String p2, final Throwable p3);
}

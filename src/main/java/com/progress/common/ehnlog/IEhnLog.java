// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public interface IEhnLog
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
    
    int getLoggingLevel();
    
    boolean ifLogLevel(final int p0);
    
    boolean ifLogIt(final int p0, final long p1, final int p2);
    
    boolean ifLogBasic(final long p0, final int p1);
    
    boolean ifLogVerbose(final long p0, final int p1);
    
    boolean ifLogExtended(final long p0, final int p1);
    
    void ehnLogWrite(final int p0, final int p1, final String p2, final String p3, final String p4);
    
    long setLogEntries(final long p0, final boolean p1, final byte[] p2);
    
    long getLogEntries();
    
    void ehnLogDump(final int p0, final int p1, final String p2, final String p3, final String p4, final byte[] p5, final int p6);
    
    void ehnLogStackTrace(final int p0, final int p1, final String p2, final String p3, final String p4, final Throwable p5);
    
    int getNumLogFiles();
    
    boolean getSubLevelUsed();
    
    byte[] getLogSubLevels();
    
    boolean getLoggingIsOn();
    
    LogWriter getDispWriter();
    
    LogWriter getFileWriter();
    
    LogHandler getDispHandler();
    
    LogHandler getFileHandler();
    
    boolean registerThresholdEventHandler(final ILogEvntHandler p0);
    
    String getCurrentLogFileName();
}

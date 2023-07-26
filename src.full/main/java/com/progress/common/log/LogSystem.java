// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import com.progress.chimera.common.Tools;
import com.progress.common.message.IProMessage;
import com.progress.common.util.PromsgsFile;
import java.io.IOException;
import com.progress.common.exception.ExceptionMessageAdapter;

public abstract class LogSystem
{
    static LogFile defaultLogFile;
    static String defaultLogFileName;
    
    public static void setDefaultLogFileName(final String defaultLogFileName) {
        LogSystem.defaultLogFileName = defaultLogFileName;
    }
    
    static LogFile getDefaultLogFile() {
        if (LogSystem.defaultLogFile == null) {
            LogSystem.defaultLogFile = new LogFile(LogSystem.defaultLogFileName);
        }
        return LogSystem.defaultLogFile;
    }
    
    public static int getLogLevel() {
        return LogFile.getWriteLevel();
    }
    
    public static void logIt(final String s, final String s2) {
        logIt(s, 3, s2, null);
    }
    
    public static void logIt(final String s, final String s2, final Object[] array) {
        logIt(s, 3, s2, array);
    }
    
    public static void logIt(final String s, final int n, final String s2) {
        logIt(s, n, s2, null);
    }
    
    public static void logIt(final String s, final int n, final String s2, final Object[] array) {
        writeIt(getDefaultLogFile(), s, false, n, s2, array);
    }
    
    public static void logIt(final String s, final long n) {
        logIt(s, 3, n, null);
    }
    
    public static void logIt(final String s, final long n, final Object[] array) {
        logIt(s, 3, n, array);
    }
    
    public static void logIt(final String s, final int n, final long n2) {
        logIt(s, n, n2, null);
    }
    
    public static void logIt(final String s, final int n, final long n2, final Object[] array) {
        writeIt(getDefaultLogFile(), s, false, n, n2, array);
    }
    
    public static void logItErr(final String s, final String s2) {
        logItErr(s, 0, s2, null);
    }
    
    public static void logItErr(final String s, final String s2, final Object[] array) {
        logItErr(s, 0, s2, array);
    }
    
    protected static void logItErr(final String s, final int n, final String s2) {
        logItErr(s, n, s2, null);
    }
    
    protected static void logItErr(final String s, final int n, final String s2, final Object[] array) {
        writeIt(getDefaultLogFile(), s, true, n, s2, array);
    }
    
    public static void logItErr(final String s, final long n) {
        logItErr(s, 0, n, null);
    }
    
    public static void logItErr(final String s, final long n, final Object[] array) {
        logItErr(s, 0, n, array);
    }
    
    protected static void logItErr(final String s, final int n, final long n2) {
        logItErr(s, n, n2, null);
    }
    
    protected static void logItErr(final String s, final int n, final long n2, final Object[] array) {
        writeIt(getDefaultLogFile(), s, true, n, n2, array);
    }
    
    protected static void writeIt(final LogFile logFile, final String s, final boolean b, final int n, final String s2, final Object[] array) {
        try {
            logFile.write(s, b, n, ExceptionMessageAdapter.getMessage(s2, array));
        }
        catch (IOException ex) {}
    }
    
    protected static void writeIt(final LogFile logFile, final String s, final boolean b, final int n, final long n2, final Object[] array) {
        try {
            logFile.write(s, b, n, ExceptionMessageAdapter.getMessage(n2, array));
        }
        catch (IOException ex) {}
    }
    
    static {
        LogSystem.defaultLogFile = null;
        LogSystem.defaultLogFileName = "default.log";
        try {
            ExceptionMessageAdapter.setMessageSubsystem(new PromsgsFile());
        }
        catch (Throwable t) {
            Tools.px(t);
        }
    }
}

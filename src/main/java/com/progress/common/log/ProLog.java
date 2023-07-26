// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import com.progress.common.exception.ExceptionMessageAdapter;
import java.util.Hashtable;

public class ProLog
{
    public static final String Fathom = "Fathom";
    public static final String ADMSRV = "AdminServer";
    private static Hashtable subsystems;
    
    public static void setDefaultLogFileName(final String defaultLogFileName) {
        LogSystem.setDefaultLogFileName(defaultLogFileName);
    }
    
    public static void setSubsystem(final String key, final Subsystem value) {
        ProLog.subsystems.put(key, value);
    }
    
    private static Subsystem getSubsystem(final String s) {
        Subsystem value = ProLog.subsystems.get(s);
        if (value == null) {
            value = new Subsystem(s);
            ProLog.subsystems.put(s, value);
        }
        return value;
    }
    
    public static void log(final String s, final long n) {
        log(s, 3, n, null);
    }
    
    public static void log(final String s, final long n, final Object o) {
        log(s, 3, n, new Object[] { o });
    }
    
    public static void log(final String s, final long n, final Object o, final Object o2) {
        log(s, 3, n, new Object[] { o, o2 });
    }
    
    public static void log(final String s, final long n, final Object o, final Object o2, final Object o3) {
        log(s, 3, n, new Object[] { o, o2, o3 });
    }
    
    public static void log(final String s, final long n, final Object[] array) {
        log(s, 3, n, array);
    }
    
    public static void log(final String s, final int n, final long n2) {
        log(s, n, n2, null);
    }
    
    public static void log(final String s, final int n, final long n2, final Object o) {
        log(s, n, n2, new Object[] { o });
    }
    
    public static void log(final String s, final int n, final long n2, final Object o, final Object o2) {
        log(s, n, n2, new Object[] { o, o2 });
    }
    
    public static void log(final String s, final int n, final long n2, final Object o, final Object o2, final Object o3) {
        log(s, n, n2, new Object[] { o, o2, o3 });
    }
    
    public static void log(final String s, final int n, final long n2, final Object[] array) {
        getSubsystem(s).log(n, n2, array);
    }
    
    public static void logErr(final String s, final long n) {
        logErr(s, 0, n, null);
    }
    
    public static void logErr(final String s, final long n, final Object o) {
        logErr(s, 0, n, new Object[] { o });
    }
    
    public static void logErr(final String s, final long n, final Object o, final Object o2) {
        logErr(s, 0, n, new Object[] { o, o2 });
    }
    
    public static void logErr(final String s, final long n, final Object o, final Object o2, final Object o3) {
        logErr(s, 0, n, new Object[] { o, o2, o3 });
    }
    
    public static void logErr(final String s, final long n, final Object[] array) {
        logErr(s, 0, n, array);
    }
    
    public static void logErr(final String s, final int n, final long n2) {
        logErr(s, n, n2, null);
    }
    
    public static void logErr(final String s, final int n, final long n2, final Object o) {
        logErr(s, n, n2, new Object[] { o });
    }
    
    public static void logErr(final String s, final int n, final long n2, final Object o, final Object o2) {
        logErr(s, n, n2, new Object[] { o, o2 });
    }
    
    public static void logErr(final String s, final int n, final long n2, final Object o, final Object o2, final Object o3) {
        logErr(s, n, n2, new Object[] { o, o2, o3 });
    }
    
    public static void logErr(final String s, final int n, final long n2, final Object[] array) {
        getSubsystem(s).logErr(n, n2, array);
    }
    
    public static void logd(final String s, final String s2) {
        logd(s, 3, s2, null);
    }
    
    public static void logd(final String s, final String s2, final Object o) {
        logd(s, 3, s2, new Object[] { o });
    }
    
    public static void logd(final String s, final String s2, final Object o, final Object o2) {
        logd(s, 3, s2, new Object[] { o, o2 });
    }
    
    public static void logd(final String s, final String s2, final Object o, final Object o2, final Object o3) {
        logd(s, 3, s2, new Object[] { o, o2, o3 });
    }
    
    public static void logd(final String s, final String s2, final Object[] array) {
        logd(s, 3, s2, array);
    }
    
    public static void logd(final String s, final int n, final String s2) {
        logd(s, n, s2, null);
    }
    
    public static void logd(final String s, final int n, final String s2, final Object o) {
        logd(s, n, s2, new Object[] { o });
    }
    
    public static void logd(final String s, final int n, final String s2, final Object o, final Object o2) {
        logd(s, n, s2, new Object[] { o, o2 });
    }
    
    public static void logd(final String s, final int n, final String s2, final Object o, final Object o2, final Object o3) {
        logd(s, n, s2, new Object[] { o, o2, o3 });
    }
    
    public static void logd(final String s, final int n, final String s2, final Object[] array) {
        getSubsystem(s).log(n, s2, array);
    }
    
    public static void logdErr(final String s, final String s2) {
        logdErr(s, 0, s2, null);
    }
    
    public static void logdErr(final String s, final String s2, final Object o) {
        logdErr(s, 0, s2, new Object[] { o });
    }
    
    public static void logdErr(final String s, final String s2, final Object o, final Object o2) {
        logdErr(s, 0, s2, new Object[] { o, o2 });
    }
    
    public static void logdErr(final String s, final String s2, final Object o, final Object o2, final Object o3) {
        logdErr(s, 0, s2, new Object[] { o, o2, o3 });
    }
    
    public static void logdErr(final String s, final String s2, final Object[] array) {
        logErr(s, 0L, s2, array);
    }
    
    public static void logdErr(final String s, final int n, final String s2) {
        logdErr(s, n, s2, null);
    }
    
    public static void logdErr(final String s, final int n, final String s2, final Object o) {
        logdErr(s, n, s2, new Object[] { o });
    }
    
    public static void logdErr(final String s, final int n, final String s2, final Object o, final Object o2) {
        logdErr(s, n, s2, new Object[] { o, o2 });
    }
    
    public static void logdErr(final String s, final int n, final String s2, final Object o, final Object o2, final Object o3) {
        logdErr(s, n, s2, new Object[] { o, o2, o3 });
    }
    
    public static void logdErr(final String s, final int n, final String s2, final Object[] array) {
        getSubsystem(s).logErr(n, s2, array);
    }
    
    public static int logX(final Throwable t) {
        return Excp.print(t, null);
    }
    
    public static int logX(final Throwable t, final String s) {
        return Excp.print(t, s);
    }
    
    public static String format(final long n) {
        return format(n, null);
    }
    
    public static String format(final long n, final Object o) {
        return format(n, new Object[] { o });
    }
    
    public static String format(final long n, final Object o, final Object o2) {
        return format(n, new Object[] { o, o2 });
    }
    
    public static String format(final long n, final Object o, final Object o2, final Object o3) {
        return format(n, new Object[] { o, o2, o3 });
    }
    
    public static String format(final long n, final Object[] array) {
        return ExceptionMessageAdapter.getMessage(n, array);
    }
    
    static {
        ProLog.subsystems = new Hashtable();
    }
}

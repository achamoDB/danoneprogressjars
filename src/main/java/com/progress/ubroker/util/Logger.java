// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.util.Locale;
import java.util.TimeZone;
import com.progress.common.exception.ExceptionMessageAdapter;
import java.util.Date;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.progress.common.ehnlog.AppLogger;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class Logger implements ubConstants
{
    public static final boolean NOTIMESTAMP = false;
    public static final boolean TIMESTAMP = true;
    public static final int LOGGING_MASK = 15;
    public static final int LOGGING_OFF = 0;
    public static final int LOGGING_ALL = 0;
    public static final int LOGGING_ERRORS = 1;
    public static final int LOGGING_TERSE = 2;
    public static final int LOGGING_VERBOSE = 3;
    public static final int LOGGING_STATS = 4;
    public static final int LOGGING_OPT = 4;
    public static final int LOGGING_DEBUG = 5;
    public static final int LOGGING_TRACE = 6;
    public static final int LOGGING_POLL = 7;
    public static final int LOGOPT_MASK = -16;
    public static final int LOGOPT_NONE = 0;
    public static final int LOGOPT_DEBUG = 16;
    public static final int LOGOPT_TRACE = 32;
    public static final int LOGOPT_CLIENTFSM = 64;
    public static final int LOGOPT_SERVERFSM = 128;
    public static final int LOGOPT_TERSE = 256;
    public static final int LOGOPT_CLIENTMSGSTREAM = 512;
    public static final int LOGOPT_SERVERMSGSTREAM = 1024;
    public static final int LOGOPT_XXX = 2048;
    public static final int LOGOPT_CLIENTMSGQUEUE = 4096;
    public static final int LOGOPT_SERVERMSGQUEUE = 8192;
    public static final int LOGOPT_CLIENTMEMTRACE = 16384;
    public static final int LOGOPT_SERVERMEMTRACE = 32768;
    public static final int LOGOPT_THREADPOOL = 65536;
    public static final int LOGOPT_STATS = 131072;
    public static final int DEST_NONE = 0;
    public static final int DEST_DISPLAY = 1;
    public static final int DEST_LOGFILE = 2;
    public static final int DEST_BOTH = 3;
    public static final int TERSE_MSGLEN = 64;
    private static final String hexdigits = "0123456789ABCDEF";
    public static final long DEF_COMPONENTSUBSYSTEMID = 1L;
    public static final int DEF_SYSTEMNAMEID = 0;
    public static final String DEF_COMPID_LIT = "Logger";
    public static final String DEF_SUBSYS_LIT = "---";
    public static final String DEF_REQID_LIT = "";
    private static DecimalFormat fmt2;
    private static DecimalFormat fmt3;
    private static DecimalFormat fmt4;
    private static SimpleDateFormat tf;
    private static DateFormat df;
    private static NumberFormat nf;
    private PrintWriter pw;
    private String fid;
    private int logging_level;
    private int logopt;
    private boolean bAppend;
    private AppLogger ehnlog;
    private long componentSubSystemID;
    private int systemNameID;
    
    public Logger() {
        this.componentSubSystemID = 1L;
        this.systemNameID = 0;
        this.logging_level = 0;
        this.logopt = 0;
        this.pw = null;
        this.fid = null;
        this.ehnlog = null;
        this.bAppend = false;
    }
    
    public Logger(final int n) {
        this.componentSubSystemID = 1L;
        this.systemNameID = 0;
        this.logging_level = (n & 0xF);
        this.logopt = (n & 0xFFFFFFF0);
        this.pw = null;
        this.fid = null;
        this.ehnlog = null;
        this.bAppend = false;
    }
    
    public Logger(final String s, final int n) throws IOException {
        this.componentSubSystemID = 1L;
        this.systemNameID = 0;
        this.logging_level = (n & 0xF);
        this.logopt = (n & 0xFFFFFFF0);
        if (n > 0 && s != null) {
            this.pw = new PrintWriter(new BufferedWriter(new FileWriter(s)));
            this.fid = new String(s);
            this.logMsgln(2, true, true, s + " opened.");
        }
        else {
            this.pw = null;
            this.fid = null;
        }
        this.ehnlog = null;
        this.bAppend = false;
        if (s != null) {
            this.fid = new String(s);
        }
    }
    
    public Logger(final String s, final int n, final boolean b) throws IOException {
        this.componentSubSystemID = 1L;
        this.systemNameID = 0;
        this.logging_level = (n & 0xF);
        this.logopt = (n & 0xFFFFFFF0);
        if (n > 0 && s != null) {
            this.pw = new PrintWriter(new BufferedWriter(new FileWriter(s, b)));
            this.fid = new String(s);
            if (b) {
                this.logMsgln(2, false, true, "======================================================================");
            }
            this.logMsgln(2, true, true, s + " opened.");
        }
        else {
            this.pw = null;
            this.fid = null;
        }
        this.ehnlog = null;
        this.bAppend = b;
        if (s != null) {
            this.fid = new String(s);
        }
    }
    
    public Logger(final AppLogger ehnlog) {
        this.componentSubSystemID = 1L;
        this.systemNameID = 0;
        this.ehnlog = ehnlog;
        this.componentSubSystemID = 8589934592L;
        this.systemNameID = 33;
        this.logging_level = ehnlog.getLoggingLevel();
        this.logopt = (int)ehnlog.getLogEntries() << 4;
        this.pw = null;
        this.fid = null;
        this.bAppend = false;
    }
    
    public void LogMsg(final int n, final int n2, final boolean b, final String s) {
        final int n3 = n2 & 0xF;
        if (this.logging_level >= n3 && (n3 != 4 || (n2 & this.logopt) > 0)) {
            this.logMsg(n, b, s);
        }
    }
    
    public void LogMsgln(final int n, final int n2, final boolean b, final String s) {
        final int n3 = n2 & 0xF;
        if (this.logging_level >= n3 && (n3 != 4 || (n2 & this.logopt) > 0)) {
            this.logMsgln(n, b, true, s);
        }
    }
    
    public void LogMsgN(final int n, final int n2, final boolean b, final long n3, final Object[] array) {
        final int n4 = n2 & 0xF;
        if (this.logging_level >= n4 && (n4 != 4 || (n2 & this.logopt) > 0)) {
            this.logMsgN(n, b, true, n3, array);
        }
    }
    
    public void LogDump(final int n, final int n2, final boolean b, final String s, final byte[] array, final int n3) {
        final int n4 = n2 & 0xF;
        if (this.logging_level >= n4 && (n4 != 4 || (n2 & this.logopt) > 0)) {
            this.logDump(n, b, s, array, n3);
        }
    }
    
    public void LogStackTrace(final int n, final int n2, final boolean b, final String s, final Throwable t) {
        final int n3 = n2 & 0xF;
        if (this.logging_level >= n3 && (n3 != 4 || (n2 & this.logopt) > 0)) {
            this.logStackTrace(n, b, s, t);
        }
    }
    
    public void LogStackTraceN(final int n, final int n2, final boolean b, final long n3, final Object[] array, final Throwable t) {
        final int n4 = n2 & 0xF;
        if (this.logging_level >= n4 && (n4 != 4 || (n2 & this.logopt) > 0)) {
            this.logStackTraceN(n, b, n3, array, t);
        }
    }
    
    public void setLoggingLevel(final int n) {
        int n2 = n;
        if (n2 < 0) {
            n2 = 0;
        }
        if (n2 > 7) {
            n2 = 7;
        }
        this.logging_level = n2;
        try {
            if (n2 > 0 && this.fid != null && this.pw == null) {
                this.pw = new PrintWriter(new BufferedWriter(new FileWriter(this.fid, this.bAppend)));
                if (this.bAppend) {
                    this.logMsgln(2, false, true, "======================================================================");
                }
                this.logMsgln(2, true, true, this.fid + " opened.");
            }
        }
        catch (IOException ex) {}
        if (n2 > 0) {
            this.logMsgln(2, true, true, "Logging Level set to = " + n2);
        }
    }
    
    public boolean ignore(final int n) {
        return this.logging_level < (n & 0xF);
    }
    
    public boolean isOptSet(final int n) {
        return (n & this.logopt) > 0;
    }
    
    public synchronized void CloseLogfile() {
        if (this.pw != null) {
            this.logMsgln(2, true, true, "Log Closed");
            this.pw.close();
        }
    }
    
    private String timestamp(final Date date) {
        return "(" + Logger.df.format(date) + " " + Logger.tf.format(date) + ")";
    }
    
    private synchronized void logMsg(final int n, final boolean b, final String s) {
        final Date date = new Date();
        if (this.ehnlog == null) {
            if ((n & 0x1) != 0x0) {
                if (b) {
                    System.err.print(this.timestamp(date) + " ");
                }
                System.err.print(s);
            }
            if (this.pw != null && (n & 0x2) != 0x0) {
                if (b) {
                    this.pw.print(this.timestamp(date) + " ");
                }
                this.pw.print(s);
                if (this.pw.checkError()) {
                    this.pw.close();
                    this.pw = null;
                }
            }
        }
        else {
            this.ehnwrite(n, s);
        }
    }
    
    private synchronized void logMsgln(final int n, final boolean b, final boolean b2, final String s) {
        final Date date = new Date();
        if (this.ehnlog == null) {
            if ((n & 0x1) != 0x0) {
                if (b2) {
                    System.err.print(Thread.currentThread().getName() + ">");
                }
                if (b) {
                    System.err.print(this.timestamp(date) + " ");
                }
                System.err.println(s);
            }
            if (this.pw != null && (n & 0x2) != 0x0) {
                if (b2) {
                    this.pw.print(Thread.currentThread().getName() + ">");
                }
                if (b) {
                    this.pw.print(this.timestamp(date) + " ");
                }
                this.pw.println(s);
                if (this.pw.checkError()) {
                    this.pw.close();
                    this.pw = null;
                }
            }
        }
        else {
            this.ehnwrite(n, s);
        }
    }
    
    private synchronized void logMsgN(final int n, final boolean b, final boolean b2, final long n2, final Object[] array) {
        this.logMsgln(n, b, b2, ExceptionMessageAdapter.getMessage(n2, array));
    }
    
    private synchronized void logDump(final int n, final boolean b, final String str, final byte[] array, final int n2) {
        if (this.ehnlog == null) {
            this.logMsgln(n, b, true, str);
            if (array == null) {
                this.logMsgln(n, false, true, "<<NULL pointer>>");
                return;
            }
            final StringBuffer sb = new StringBuffer(17);
            final StringBuffer sb2 = new StringBuffer(256);
            final char[] str2 = { ' ', '\0', '\0' };
            for (int i = 0; i < n2; ++i) {
                if (i % 16 == 0) {
                    if (sb.length() > 0) {
                        this.logMsgln(n, false, true, sb2.toString() + " :" + sb.toString());
                    }
                    sb.setLength(0);
                    sb2.setLength(0);
                    sb2.append(Logger.fmt4.format(i) + ":");
                }
                str2[1] = "0123456789ABCDEF".charAt(array[i] >> 4 & 0xF);
                str2[2] = "0123456789ABCDEF".charAt(array[i] & 0xF);
                sb2.append(str2);
                sb.append((array[i] >= 32 && array[i] < 127) ? ((char)array[i]) : '.');
            }
            int j;
            if ((j = sb.length()) > 0) {
                while (j < 16) {
                    sb2.append("   ");
                    ++j;
                }
                this.logMsgln(n, false, true, sb2.toString() + " :" + sb.toString());
            }
            else {
                this.logMsgln(n, false, false, "");
            }
        }
        else {
            this.ehnlog.ehnLogDump(n, this.logging_level, this.ehnlog.getExecEnvId(), "uBroker-Client  ", this.ehnlog.getLogContext().getMsgHdr() + str, array, n2);
        }
    }
    
    private synchronized void logStackTrace(final int n, final boolean b, final String s, final Throwable t) {
        final Date date = new Date();
        if ((n & 0x1) != 0x0) {
            System.err.print(Thread.currentThread().getName() + ">");
            if (b) {
                System.err.print(this.timestamp(date) + " ");
            }
            System.err.println(s);
            t.printStackTrace();
        }
        if (this.pw != null && (n & 0x2) != 0x0) {
            this.pw.print(Thread.currentThread().getName() + ">");
            if (b) {
                this.pw.print(this.timestamp(date) + " ");
            }
            this.pw.println(s);
            t.printStackTrace(this.pw);
            if (this.pw.checkError()) {
                this.pw.close();
                this.pw = null;
            }
        }
    }
    
    private synchronized void logStackTraceN(final int n, final boolean b, final long n2, final Object[] array, final Throwable t) {
        this.logStackTrace(n, b, ExceptionMessageAdapter.getMessage(n2, array), t);
    }
    
    private void ehnwrite(final int n, final String s) {
        switch (this.logging_level) {
            case 1: {
                this.ehnlog.logError(s);
                break;
            }
            case 2: {
                this.ehnlog.logBasic(this.systemNameID, s);
                break;
            }
            case 3: {
                this.ehnlog.logVerbose(this.systemNameID, s);
                break;
            }
            case 5:
            case 6:
            case 7: {
                this.ehnlog.logExtended(this.systemNameID, s);
                break;
            }
        }
    }
    
    static {
        Logger.fmt2 = new DecimalFormat("00");
        Logger.fmt3 = new DecimalFormat("000");
        Logger.fmt4 = new DecimalFormat("0000");
        (Logger.tf = new SimpleDateFormat("HH:mm:ss:SSS")).setTimeZone(TimeZone.getDefault());
        Logger.df = DateFormat.getDateInstance(2, Locale.getDefault());
        (Logger.nf = Logger.df.getNumberFormat()).setMinimumIntegerDigits(2);
        Logger.nf.setMaximumIntegerDigits(2);
        Logger.df.setNumberFormat(Logger.nf);
    }
}

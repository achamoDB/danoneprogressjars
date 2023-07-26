// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import com.progress.common.ehnlog.IAppLogger;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class ubMsgTrace implements ubConstants
{
    public static final int FMT_TERSE = 0;
    public static final int FMT_BASIC = 0;
    public static final int FMT_VERBOSE = 1;
    public static final int DEF_LIMIT = 20;
    private static DecimalFormat fmt2;
    private static DecimalFormat fmt3;
    private static DecimalFormat fmt4;
    private static SimpleDateFormat tf;
    private static DateFormat df;
    private static NumberFormat nf;
    TraceRec[] trace;
    int limit;
    int next;
    
    public ubMsgTrace(final int limit) {
        this.limit = limit;
        this.trace = new TraceRec[limit];
        for (int i = 0; i < limit; ++i) {
            this.trace[i] = null;
        }
        this.next = 0;
    }
    
    public ubMsgTrace() {
        this(20);
    }
    
    public void addMsg(final ubMsg ubMsg, final String s, final String s2, final String s3) {
        this.trace[this.next] = new TraceRec(ubMsg, s, s2, s3);
        this.next = this.advance_cursor(this.next);
    }
    
    public void print(final IAppLogger appLogger, final int n, final int n2, final String s) {
        appLogger.logBasic(n2, s);
        int n3 = this.next;
        for (int i = 0; i < this.limit; ++i) {
            if (this.trace[n3] != null) {
                this.trace[n3].print(appLogger, n2, n);
            }
            n3 = this.advance_cursor(n3);
        }
    }
    
    private String fmttimestamp(final long date) {
        final Date date2 = new Date(date);
        return (date2 == null) ? "               " : ubMsgTrace.tf.format(date2);
    }
    
    private int advance_cursor(int n) {
        n = (n + 1) % this.limit;
        return n;
    }
    
    static {
        ubMsgTrace.fmt2 = new DecimalFormat("00");
        ubMsgTrace.fmt3 = new DecimalFormat("000");
        ubMsgTrace.fmt4 = new DecimalFormat("0000");
        (ubMsgTrace.tf = new SimpleDateFormat("HH:mm:ss:SSS")).setTimeZone(TimeZone.getDefault());
        ubMsgTrace.df = DateFormat.getDateInstance(2, Locale.getDefault());
        (ubMsgTrace.nf = ubMsgTrace.df.getNumberFormat()).setMinimumIntegerDigits(2);
        ubMsgTrace.nf.setMaximumIntegerDigits(2);
        ubMsgTrace.df.setNumberFormat(ubMsgTrace.nf);
    }
    
    private class TraceRec
    {
        String id;
        ubMsg msg;
        long tsMsg;
        String src;
        String dest;
        
        public TraceRec(final ubMsg msg, final String id, final String src, final String dest) {
            this.id = id;
            this.msg = msg;
            this.src = src;
            this.dest = dest;
            this.tsMsg = System.currentTimeMillis();
        }
        
        public void print(final IAppLogger appLogger, final int n, final int n2) {
            final String access$000 = ubMsgTrace.this.fmttimestamp(this.tsMsg);
            final String s = (this.msg instanceof ubAppServerMsg) ? ubMsgTrace.fmt4.format(((ubAppServerMsg)this.msg).getSeqnum()) : "";
            switch (n2) {
                case 1: {
                    this.msg.print("[" + access$000 + "] {" + this.id + "}" + " " + this.msg.getubRqDesc() + " " + s + " " + this.src + " ---> " + this.dest, 2, n, appLogger);
                    break;
                }
                case 0: {
                    appLogger.logBasic(n, "[" + access$000 + "] {" + this.id + "}" + " " + this.msg.getubRqDesc() + " " + s + " " + this.src + " --->" + " " + this.dest);
                    break;
                }
            }
        }
    }
}

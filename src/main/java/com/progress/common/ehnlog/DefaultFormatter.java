// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class DefaultFormatter implements Formatter
{
    private static DecimalFormat fmt1;
    private static DecimalFormat fmt2;
    private static DecimalFormat fmt3;
    private static DecimalFormat fmt4;
    private static DecimalFormat fmt6;
    private static SimpleDateFormat tf;
    private static SimpleDateFormat tfbkp;
    private static TimeZone tz;
    private static String pidstr;
    private static String tzostr;
    public static final int NOT_IMPLEMENTED_FORMAT = -1;
    public static final int EHNLOG_TIME_FORMAT = 1;
    public static final int EHNLOG_SEQ_FORMAT = 2;
    public static final int TIMESTAMP_EHN_BKP = 13;
    public static final int SEQNUM_EHN_BKP = 6;
    
    public String format(final int n, final String str, final String str2) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        final StringBuffer sb = new StringBuffer();
        sb.append(this.timestamp(gregorianCalendar));
        sb.append(" ");
        sb.append(DefaultFormatter.pidstr);
        sb.append(" ");
        sb.append(this.getTid());
        sb.append(" ");
        sb.append(DefaultFormatter.fmt1.format(n));
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        sb.append(" ");
        return sb.toString();
    }
    
    private String timestamp(final GregorianCalendar gregorianCalendar) {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(DefaultFormatter.fmt2.format(gregorianCalendar.get(1) % 100));
        sb.append("/");
        sb.append(DefaultFormatter.fmt2.format(gregorianCalendar.get(2) + 1));
        sb.append("/");
        sb.append(DefaultFormatter.fmt2.format(gregorianCalendar.get(5)));
        sb.append("@");
        sb.append(DefaultFormatter.tf.format(gregorianCalendar.getTime()));
        sb.append(DefaultFormatter.tzostr);
        sb.append("]");
        return sb.toString();
    }
    
    public String formatBackupFileName(final int n, final int n2) {
        final StringBuffer sb = new StringBuffer();
        if (n == 1) {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            sb.append(DefaultFormatter.fmt2.format(gregorianCalendar.get(1) % 100));
            sb.append(DefaultFormatter.fmt2.format(gregorianCalendar.get(2) + 1));
            sb.append(DefaultFormatter.fmt2.format(gregorianCalendar.get(5)));
            sb.append("@");
            sb.append(DefaultFormatter.tfbkp.format(gregorianCalendar.getTime()));
        }
        else if (n == 2) {
            sb.append(DefaultFormatter.fmt6.format(n2));
        }
        else {
            sb.append("");
        }
        return sb.toString();
    }
    
    private String getTid() {
        final StringBuffer sb = new StringBuffer();
        sb.append("T-");
        sb.append(Thread.currentThread().getName());
        return sb.toString();
    }
    
    private static String timeZoneOffset() {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        int n = (gregorianCalendar.get(15) + gregorianCalendar.get(16)) / 1000;
        final StringBuffer sb = new StringBuffer();
        if (n < 0) {
            sb.append("-");
            n = -n;
        }
        else {
            sb.append("+");
        }
        sb.append(DefaultFormatter.fmt2.format(n / 3600));
        sb.append(DefaultFormatter.fmt2.format(n / 60 % 60));
        return sb.toString();
    }
    
    private static SimpleDateFormat createTimeFormatter() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat;
    }
    
    static {
        DefaultFormatter.fmt1 = new DecimalFormat("0");
        DefaultFormatter.fmt2 = new DecimalFormat("00");
        DefaultFormatter.fmt3 = new DecimalFormat("000");
        DefaultFormatter.fmt4 = new DecimalFormat("0000");
        DefaultFormatter.fmt6 = new DecimalFormat("000000");
        DefaultFormatter.tz = TimeZone.getDefault();
        (DefaultFormatter.tfbkp = new SimpleDateFormat("HHmmss")).setTimeZone(DefaultFormatter.tz);
        DefaultFormatter.tf = createTimeFormatter();
        DefaultFormatter.tzostr = timeZoneOffset();
        try {
            final Class<?> forName = Class.forName("com.progress.common.util.Environment");
            DefaultFormatter.pidstr = "P-" + DefaultFormatter.fmt6.format((int)forName.getMethod("getCurrent_PID_JNI", Integer.TYPE).invoke(forName.newInstance(), new Integer(0)));
        }
        catch (Throwable t) {
            DefaultFormatter.pidstr = "P-000000";
        }
    }
}

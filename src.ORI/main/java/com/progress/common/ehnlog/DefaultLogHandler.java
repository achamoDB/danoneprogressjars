// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;

public class DefaultLogHandler implements LogHandler
{
    private static final String hexdigits = "0123456789ABCDEF";
    private static final int MAXLINE = 512;
    private static DecimalFormat fmt4;
    private LogWriter writer;
    private Formatter formatter;
    private Object lock;
    
    public DefaultLogHandler() {
        this(new StreamLogWriter());
    }
    
    public DefaultLogHandler(final LogWriter writer) {
        this.writer = new StreamLogWriter();
        this.formatter = new DefaultFormatter();
        this.lock = new Object();
        this.writer = writer;
    }
    
    public void log(final int n, final String s, final String s2, final String s3) {
        final String format = this.formatter.format(n, s, s2);
        synchronized (this.lock) {
            this.writeMultiline(format, s3);
        }
    }
    
    public void log(final int n, final String s, final String s2, final String s3, final byte[] array, final int n2) {
        final String format = this.formatter.format(n, s, s2);
        synchronized (this.lock) {
            this.writeMultiline(format, s3);
            this.writeMultiline(format, array, n2);
        }
    }
    
    public void log(final int n, final String s, final String s2, final String s3, final Throwable t) {
        final String format = this.formatter.format(n, s, s2);
        synchronized (this.lock) {
            this.writeMultiline(format, s3);
            this.writeMultiline(format, t);
        }
    }
    
    protected void writeMultiline(final String str, final String s) {
        int n2;
        for (int i = 0, length = s.length(); i < length; i = n2) {
            final int n = (length - i > 512) ? (i + 512) : length;
            int index = s.indexOf(10, i);
            if (index == -1 || index > n) {
                index = (n2 = n);
            }
            else {
                n2 = index + 1;
            }
            final StringBuffer sb = new StringBuffer(str);
            sb.append(this.trimEnd(s.substring(i, index)));
            this.writer.write(sb.toString());
        }
    }
    
    public String trimEnd(final String s) {
        int length;
        for (length = s.length(); length > 0 && s.charAt(length - 1) <= ' '; --length) {}
        return (length < s.length()) ? s.substring(0, length) : s;
    }
    
    protected void writeMultiline(final String str, final byte[] array, int length) {
        if (array == null) {
            this.writer.write(str + "<<NULL pointer>>");
            return;
        }
        final StringBuffer sb = new StringBuffer(17);
        final StringBuffer sb2 = new StringBuffer(256);
        final char[] str2 = { ' ', '\0', '\0' };
        if (array.length < length) {
            length = array.length;
        }
        for (int i = 0; i < length; ++i) {
            if (i % 16 == 0) {
                if (sb.length() > 0) {
                    this.writer.write(str + sb2.toString() + " : " + sb.toString());
                }
                sb.setLength(0);
                sb2.setLength(0);
                sb2.append(DefaultLogHandler.fmt4.format(i) + ":");
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
            this.writer.write(str + sb2.toString() + " : " + sb.toString());
        }
    }
    
    protected void writeMultiline(final String s, final Throwable t) {
        this.writeMultiline(s, this.getStackTraceString(t));
    }
    
    private String getStackTraceString(final Throwable t) {
        final StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out));
        return out.toString();
    }
    
    static {
        DefaultLogHandler.fmt4 = new DecimalFormat("0000");
    }
}

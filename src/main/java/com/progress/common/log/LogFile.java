// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.io.IOException;
import com.progress.chimera.common.Tools;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;
import java.text.DateFormat;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.OutputStream;

class LogFile
{
    private static final int buffSize = 10000;
    private static final boolean flushEnabled = true;
    static final int defaultWriteLevel = 3;
    protected static int m_writeLevel;
    static final String BLANKS = "                           ";
    static final int MIN_PREF_LEN;
    String fileName;
    OutputStream oStream;
    OutputStreamWriter osw;
    Writer w;
    boolean isOpen;
    protected boolean usingFile;
    public static final int TIME_LOCAL = 1;
    public static final int TIME_ABSOLUTE_MILLIS = 2;
    public static final int TIME_RELATIVE_MILLIS = 3;
    protected int m_timeType;
    protected DateFormat m_dateFormat;
    protected long m_startMillis;
    protected String m_startDate;
    static final String TIME_STRING_MASK = "00000000";
    static final int TIME_STRING_MIN_LEN;
    
    void setWriteLevel(final int writeLevel) {
        LogFile.m_writeLevel = writeLevel;
        LogFile.m_writeLevel = adjustLevel(LogFile.m_writeLevel);
    }
    
    static int getWriteLevel() {
        return LogFile.m_writeLevel;
    }
    
    static int adjustLevel(final int n) {
        if (n < 0) {
            return 1;
        }
        if (n > 5) {
            return 5;
        }
        return n;
    }
    
    public LogFile(final OutputStream oStream) {
        this.fileName = "default.log";
        this.oStream = null;
        this.osw = null;
        this.w = null;
        this.isOpen = false;
        this.usingFile = false;
        this.m_timeType = 1;
        this.m_startMillis = System.currentTimeMillis();
        this.initDateFormat();
        this.oStream = oStream;
    }
    
    public LogFile(final String fileName) {
        this.fileName = "default.log";
        this.oStream = null;
        this.osw = null;
        this.w = null;
        this.isOpen = false;
        this.usingFile = false;
        this.m_timeType = 1;
        this.m_startMillis = System.currentTimeMillis();
        this.initDateFormat();
        this.fileName = fileName;
        this.usingFile = true;
    }
    
    private void initDateFormat() {
        (this.m_dateFormat = DateFormat.getDateTimeInstance(3, 2, Locale.getDefault())).setTimeZone(TimeZone.getDefault());
        this.m_startDate = this.m_dateFormat.format(new Date());
    }
    
    public void open() {
        try {
            if (this.oStream == null) {
                this.oStream = new FileOutputStream(this.fileName, true);
            }
            this.osw = new OutputStreamWriter(this.oStream);
            this.w = new BufferedWriter(this.osw);
            this.isOpen = true;
            this.write(" ");
            this.write(((ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.CMNMsgBundle")).getTranString("StartLogging", (Object)this.m_startDate));
            this.write(" ");
        }
        catch (Throwable t) {
            Tools.px(t);
        }
    }
    
    public synchronized void close() throws IOException {
        if (this.w != null) {
            this.write(((ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.CMNMsgBundle")).getTranString("EndLogging"));
            if (this.usingFile) {
                this.w.close();
            }
            this.w = null;
        }
    }
    
    protected void finalize() throws Throwable {
        this.close();
    }
    
    void write(final String s, final boolean b, final int n, final String s2) throws IOException {
        final int n2 = s.length() + 2 + 3 + 1 + 1;
        if (n2 < LogFile.MIN_PREF_LEN) {
            this.write(s2, "[" + n + "] " + "[" + s + "]" + "                           ".substring(0, "                           ".length() - n2) + (b ? "*" : " "));
        }
        else {
            this.write(s2, "[" + n + "] " + "[" + s + "]" + (b ? " *" : "  "));
        }
    }
    
    protected void write(final String s) throws IOException {
        this.write(s, null);
    }
    
    protected synchronized void write(final String str, final String str2) throws IOException {
        if (!this.isOpen) {
            this.open();
        }
        final int n = (str2 == null) ? 0 : str2.length();
        final StringBuffer obj = new StringBuffer(55 + n);
        final boolean b = false;
        obj.append(this.myGetTime());
        if (n > 0) {
            obj.append(" " + str2);
        }
        String s;
        try {
            s = ((n <= 0) ? "" : " ");
        }
        catch (NullPointerException ex) {
            s = "";
        }
        if (str == null || str.length() == 0) {
            this.w.write((Object)obj + CConst.NEWLN);
        }
        else {
            final StringTokenizer stringTokenizer = new StringTokenizer(str, "\r\n\f");
            String nextToken;
            try {
                nextToken = stringTokenizer.nextToken();
            }
            catch (NoSuchElementException ex2) {
                return;
            }
            if (stringTokenizer.hasMoreTokens() && !b) {
                this.w.write(CConst.NEWLN);
            }
            this.w.write((Object)obj + s + nextToken + CConst.NEWLN);
            if (stringTokenizer.hasMoreTokens()) {
                do {
                    this.w.write((Object)obj + s + stringTokenizer.nextToken() + CConst.NEWLN);
                } while (stringTokenizer.hasMoreTokens());
                this.w.write(CConst.NEWLN);
            }
        }
        this.w.flush();
    }
    
    protected String myGetTime() {
        String s = null;
        switch (this.m_timeType) {
            case 2: {
                s = Long.toString(System.currentTimeMillis());
                break;
            }
            case 3: {
                String s2 = Long.toString(System.currentTimeMillis() - this.m_startMillis);
                if (s2.length() < LogFile.TIME_STRING_MIN_LEN) {
                    s2 = "00000000".substring(0, LogFile.TIME_STRING_MIN_LEN - s2.length()) + s2;
                }
                s = "+" + s2;
                break;
            }
            default: {
                s = "[" + this.m_dateFormat.format(new Date()) + "]";
                break;
            }
        }
        return s;
    }
    
    static {
        LogFile.m_writeLevel = 3;
        try {
            LogFile.m_writeLevel = Integer.getInteger("LogLevel");
            LogFile.m_writeLevel = adjustLevel(LogFile.m_writeLevel);
        }
        catch (Throwable t) {}
        MIN_PREF_LEN = "                           ".length();
        TIME_STRING_MIN_LEN = "00000000".length();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;

public class Subsystem extends LogSystem
{
    protected static ProgressResources m_resources;
    protected String m_name;
    protected LogFile logfile;
    protected int m_writeLevel;
    
    protected int getDefaultWriteLevel() {
        return 3;
    }
    
    public void setWriteLevel(final int writeLevel) {
        this.m_writeLevel = writeLevel;
        this.m_writeLevel = LogFile.adjustLevel(this.m_writeLevel);
    }
    
    public int getWriteLevel() {
        return this.m_writeLevel;
    }
    
    public Subsystem(final String s) {
        this(s, null);
    }
    
    public Subsystem(final String s, final LogFile logfile) {
        this.m_name = null;
        this.logfile = null;
        this.logfile = logfile;
        try {
            this.m_writeLevel = Integer.getInteger("LogLevel" + s);
            this.m_writeLevel = LogFile.adjustLevel(this.m_writeLevel);
        }
        catch (Throwable t) {
            this.m_writeLevel = LogFile.getWriteLevel();
        }
        try {
            if (Subsystem.m_resources == null) {
                Subsystem.m_resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.LogSubsystemBundle");
            }
            this.m_name = Subsystem.m_resources.getTranString(s);
        }
        catch (Throwable t2) {
            this.m_name = s;
        }
    }
    
    public void log(final String s) {
        this.log(3, s, null);
    }
    
    public void log(final String s, final Object o) {
        this.log(3, s, new Object[] { o });
    }
    
    public void log(final String s, final Object o, final Object o2) {
        this.log(3, s, new Object[] { o, o2 });
    }
    
    public void log(final String s, final Object o, final Object o2, final Object o3) {
        this.log(3, s, new Object[] { o, o2, o3 });
    }
    
    public void log(final String s, final Object[] array) {
        this.log(3, s, array);
    }
    
    public void log(final int n, final String s) {
        this.log(n, s, null);
    }
    
    public void log(final int n, final String s, final Object o) {
        this.log(n, s, new Object[] { o });
    }
    
    public void log(final int n, final String s, final Object o, final Object o2) {
        this.log(n, s, new Object[] { o, o2 });
    }
    
    public void log(final int n, final String s, final Object o, final Object o2, final Object o3) {
        this.log(n, s, new Object[] { o, o2, o3 });
    }
    
    public void log(final int n, final String s, final Object[] array) {
        this.write(false, n, s, array);
    }
    
    public void log(final long n) {
        this.log(3, n, null);
    }
    
    public void log(final long n, final Object o) {
        this.log(3, n, new Object[] { o });
    }
    
    public void log(final long n, final Object o, final Object o2) {
        this.log(3, n, new Object[] { o, o2 });
    }
    
    public void log(final long n, final Object o, final Object o2, final Object o3) {
        this.log(3, n, new Object[] { o, o2, o3 });
    }
    
    public void log(final long n, final Object[] array) {
        this.log(3, n, array);
    }
    
    public void log(final int n, final long n2) {
        this.log(n, n2, null);
    }
    
    public void log(final int n, final long n2, final Object o) {
        this.log(n, n2, new Object[] { o });
    }
    
    public void log(final int n, final long n2, final Object o, final Object o2) {
        this.log(n, n2, new Object[] { o, o2 });
    }
    
    public void log(final int n, final long n2, final Object o, final Object o2, final Object o3) {
        this.log(n, n2, new Object[] { o, o2, o3 });
    }
    
    public void log(final int n, final long n2, final Object[] array) {
        this.write(false, n, n2, array);
    }
    
    public void logErr(final String s) {
        this.logErr(0, s, null);
    }
    
    public void logErr(final String s, final Object o) {
        this.logErr(0, s, new Object[] { o });
    }
    
    public void logErr(final String s, final Object o, final Object o2) {
        this.logErr(0, s, new Object[] { o, o2 });
    }
    
    public void logErr(final String s, final Object o, final Object o2, final Object o3) {
        this.logErr(0, s, new Object[] { o, o2, o3 });
    }
    
    public void logErr(final String s, final Object[] array) {
        this.logErr(0, s, array);
    }
    
    public void logErr(final int n, final String s) {
        this.logErr(n, s, null);
    }
    
    public void logErr(final int n, final String s, final Object o) {
        this.logErr(n, s, new Object[] { o });
    }
    
    public void logErr(final int n, final String s, final Object o, final Object o2) {
        this.logErr(n, s, new Object[] { o, o2 });
    }
    
    public void logErr(final int n, final String s, final Object o, final Object o2, final Object o3) {
        this.logErr(n, s, new Object[] { o, o2, o3 });
    }
    
    public void logErr(final int n, final String s, final Object[] array) {
        this.write(true, n, s, array);
    }
    
    public void logErr(final long n) {
        this.logErr(0, n, null);
    }
    
    public void logErr(final long n, final Object o) {
        this.logErr(0, n, new Object[] { o });
    }
    
    public void logErr(final long n, final Object o, final Object o2) {
        this.logErr(0, n, new Object[] { o, o2 });
    }
    
    public void logErr(final long n, final Object o, final Object o2, final Object o3) {
        this.logErr(0, n, new Object[] { o, o2, o3 });
    }
    
    public void logErr(final long n, final Object[] array) {
        this.logErr(0, n, array);
    }
    
    public void logErr(final int n, final long n2) {
        this.logErr(n, n2, null);
    }
    
    public void logErr(final int n, final long n2, final Object o) {
        this.logErr(n, n2, new Object[] { o });
    }
    
    public void logErr(final int n, final long n2, final Object o, final Object o2) {
        this.logErr(n, n2, new Object[] { o, o2 });
    }
    
    public void logErr(final int n, final long n2, final Object o, final Object o2, final Object o3) {
        this.logErr(n, n2, new Object[] { o, o2, o3 });
    }
    
    public void logErr(final int n, final long n2, final Object[] array) {
        this.write(true, n, n2, array);
    }
    
    protected void write(final boolean b, final int n, final String s, final Object[] array) {
        if (this.logfile == null) {
            this.logfile = LogSystem.getDefaultLogFile();
        }
        if (n > this.m_writeLevel) {
            return;
        }
        LogSystem.writeIt(this.logfile, this.m_name, b, n, s, array);
    }
    
    protected void write(final boolean b, final int n, final long n2, final Object[] array) {
        if (this.logfile == null) {
            this.logfile = LogSystem.getDefaultLogFile();
        }
        if (n > this.m_writeLevel) {
            return;
        }
        LogSystem.writeIt(this.logfile, this.m_name, b, n, n2, array);
    }
    
    static {
        Subsystem.m_resources = null;
    }
}

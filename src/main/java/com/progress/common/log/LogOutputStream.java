// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.io.IOException;
import java.io.OutputStream;

public class LogOutputStream extends OutputStream
{
    private Subsystem logfile;
    private int loglevel;
    
    public LogOutputStream(final Subsystem logfile) {
        this.loglevel = 4;
        this.logfile = logfile;
    }
    
    public LogOutputStream(final Subsystem logfile, final int loglevel) {
        this.loglevel = 4;
        this.logfile = logfile;
        this.loglevel = loglevel;
    }
    
    public void close() throws IOException {
    }
    
    public void flush() throws IOException {
    }
    
    public void write(final byte[] bytes) throws IOException {
        this.logfile.log(this.loglevel, new String(bytes));
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        final byte[] bytes = new byte[n2];
        System.arraycopy(array, n, bytes, 0, n2);
        this.logfile.log(this.loglevel, new String(bytes));
    }
    
    public void write(final int n) throws IOException {
        this.logfile.log(this.loglevel, new String(new byte[] { (byte)n }));
    }
}

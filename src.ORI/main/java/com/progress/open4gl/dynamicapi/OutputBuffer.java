// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.OutputStream;

public class OutputBuffer extends OutputStream
{
    private byte[] buffer;
    private int pos;
    
    public OutputBuffer(final int n) {
        this.pos = 0;
        this.buffer = new byte[n];
    }
    
    public int getLen() {
        return this.pos;
    }
    
    public byte[] getBuf() {
        return this.buffer;
    }
    
    public void reset() {
        this.pos = 0;
    }
    
    public void write(final int n) {
        this.buffer[this.pos++] = (byte)n;
        if (this.pos == this.buffer.length) {
            final byte[] buffer = new byte[this.buffer.length * 2];
            System.arraycopy(this.buffer, 0, buffer, 0, this.buffer.length);
            this.buffer = buffer;
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

public class BufMgr
{
    byte[] buffer;
    int bsize;
    
    public BufMgr() {
        this.buffer = new byte[64];
        this.bsize = 0;
    }
    
    public void append(final int n) {
        if (this.bsize + 1 >= this.buffer.length) {
            final byte[] buffer = new byte[this.buffer.length * 2];
            System.arraycopy(this.buffer, 0, buffer, 0, this.buffer.length);
            this.buffer = buffer;
        }
        this.buffer[this.bsize++] = (byte)n;
    }
    
    public int getSize() {
        return this.bsize;
    }
    
    public String toString() {
        return new String(this.buffer, 0, 0, this.bsize);
    }
}

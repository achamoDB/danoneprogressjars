// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.io.IOException;
import com.progress.open4gl.BooleanHolder;
import com.progress.open4gl.IntHolder;
import java.io.InputStream;

class FromUBInputStream extends InputStream
{
    private InputBufferQueue queue;
    private byte[] buffer;
    private int pos;
    private int how_many;
    private IntHolder modeH;
    private IntHolder lenH;
    private BooleanHolder lastH;
    
    FromUBInputStream(final InputBufferQueue queue) {
        this.queue = queue;
        this.pos = 0;
        this.how_many = 0;
        this.buffer = null;
        this.modeH = new IntHolder(0);
        this.lastH = new BooleanHolder(false);
        this.lenH = new IntHolder(0);
    }
    
    public int read() throws IOException {
        if (this.pos == this.how_many && !this.lastH.getBooleanValue()) {
            try {
                this.buffer = this.queue.get(this.modeH, this.lastH, this.lenH);
                this.how_many = this.lenH.getIntValue();
            }
            catch (Exception ex) {
                throw new IOException(ex.getMessage());
            }
            this.pos = 0;
        }
        if (this.pos == this.how_many) {
            this.how_many = 0;
            this.lastH.setBooleanValue(false);
            this.pos = 0;
            return -1;
        }
        final byte b = this.buffer[this.pos++];
        int n = b & 0x7F;
        if ((b & 0x80) != 0x0) {
            n |= 0x80;
        }
        return n;
    }
    
    public void close() {
        try {
            while (this.read() != -1) {}
        }
        catch (IOException ex) {}
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.io.IOException;
import java.io.OutputStream;

class ToUBOutputStream extends OutputStream
{
    private OutputBufferQueue outputQueue;
    private byte[] buffer;
    private int pos;
    
    ToUBOutputStream(final OutputBufferQueue outputQueue) {
        this.outputQueue = outputQueue;
        this.buffer = null;
    }
    
    public void write(final int n) throws IOException {
        if (this.buffer == null) {
            this.getBuffer();
        }
        this.buffer[this.pos++] = (byte)n;
        if (this.pos == this.buffer.length) {
            try {
                this.outputQueue.enqueue(this.buffer, this.pos);
                this.buffer = null;
            }
            catch (Exception ex) {
                throw new IOException(ex.getMessage());
            }
        }
    }
    
    private void getBuffer() throws IOException {
        try {
            this.buffer = new byte[8192];
            this.pos = 0;
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
    
    void abnormalClose() throws IOException {
        if (this.buffer == null) {
            this.getBuffer();
        }
        try {
            this.outputQueue.enqueue(this.buffer, -1);
            this.buffer = null;
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
    
    public void close() throws IOException {
        try {
            if (this.buffer == null) {
                this.getBuffer();
            }
            this.outputQueue.enqueue(this.buffer, this.pos);
            if (this.pos == this.buffer.length) {
                this.buffer = null;
                this.getBuffer();
                this.outputQueue.enqueue(this.buffer, this.pos);
            }
            this.buffer = null;
        }
        catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
}

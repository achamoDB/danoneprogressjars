// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.IOException;
import com.progress.open4gl.broker.BrokerException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.broker.Broker;
import java.io.InputStream;

class SinkInputStream extends InputStream
{
    private final int BUFFER_SIZE = 33000;
    private Broker source;
    private byte[] buffer;
    private int pos;
    private int how_many;
    private int num;
    private Tracer tracer;
    private boolean last;
    
    SinkInputStream(final Broker source) {
        this.tracer = RunTimeProperties.tracer;
        this.last = false;
        this.source = source;
        this.buffer = null;
        this.pos = 0;
    }
    
    public int read() throws IOException {
        if (this.source == null) {
            return -1;
        }
        if (this.buffer == null) {
            this.buffer = new byte[33000];
        }
        if (this.pos == this.how_many && !this.last) {
            try {
                this.pos = 0;
                this.how_many = 0;
                this.tracer.print("Broker Call: before read()", 4);
                this.how_many = this.source.read(this.buffer);
                this.tracer.print("Broker Call: after read()", 4);
                if (this.how_many < 33000) {
                    this.last = true;
                }
            }
            catch (BrokerException ex) {
                switch (ex.getErrorNum()) {
                    default: {
                        try {
                            this.tracer.print("Broker Call: disconnect()", 4);
                            this.source.disconnect();
                        }
                        catch (Exception ex2) {}
                        throw ExceptionConverter.convertBrokerException(ex);
                    }
                    case -2: {
                        throw new EndOfStreamException(ExceptionConverter.getProgressMessage(63));
                    }
                }
            }
        }
        if (this.pos == this.how_many || (this.last && -1 == this.how_many)) {
            this.how_many = 0;
            this.last = false;
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
    
    void flushClose() {
        try {
            while (this.read() != -1) {}
            this.close();
        }
        catch (IOException ex) {
            this.tracer.print(ex, 1);
        }
    }
    
    public void close() throws IOException {
        try {
            this.validate();
        }
        catch (Exception ex) {
            return;
        }
        this.source = null;
    }
    
    private void validate() throws IOException {
        if (this.source == null) {
            throw new IOException(ExceptionConverter.getProgressMessage(64));
        }
    }
}

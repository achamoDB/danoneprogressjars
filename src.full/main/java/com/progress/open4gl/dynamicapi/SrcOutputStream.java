// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.broker.BrokerException;
import java.io.IOException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.broker.Broker;
import java.io.OutputStream;

class SrcOutputStream extends OutputStream
{
    private final int BUFFER_SIZE = 25000;
    private Broker sink;
    private byte[] buffer;
    private int pos;
    private Tracer tracer;
    
    SrcOutputStream(final Broker sink) {
        this.tracer = RunTimeProperties.tracer;
        this.sink = sink;
        this.pos = 0;
        this.buffer = new byte[25000];
    }
    
    public void write(final int n) throws IOException {
        this.validate();
        this.buffer[this.pos++] = (byte)n;
        if (this.pos == 25000) {
            try {
                this.tracer.print("Broker Call: write()", 4);
                this.sink.write(this.buffer, 0, 25000);
            }
            catch (BrokerException ex) {
                switch (ex.getErrorNum()) {
                    case -5: {
                        this.stopClose();
                        this.pos = 0;
                        throw new IOException(ExceptionConverter.getProgressMessage(61));
                    }
                    default: {
                        try {
                            this.tracer.print("Broker Call: disconnect()", 4);
                            this.sink.disconnect();
                        }
                        catch (BrokerException ex2) {}
                        this.sink = null;
                        this.pos = 0;
                        throw ExceptionConverter.convertBrokerException(ex);
                    }
                }
            }
            this.pos = 0;
        }
    }
    
    void badClose() throws IOException {
        this.validate();
        try {
            this.tracer.print("Broker Call: prepareToReceive(CLOSE_WRITE_BAD)", 4);
            this.sink.prepareToReceive(2);
        }
        catch (BrokerException ex) {
            throw ExceptionConverter.convertBrokerException(ex);
        }
        finally {
            this.sink = null;
        }
    }
    
    void stopClose() throws IOException {
        this.validate();
        try {
            this.tracer.print("Broker Call: prepareToReceive(CLOSE_WRITE_STOP)", 4);
            this.sink.prepareToReceive(3);
        }
        catch (BrokerException ex) {
            throw ExceptionConverter.convertBrokerException(ex);
        }
        finally {
            this.sink = null;
        }
    }
    
    public void close() throws IOException {
        this.validate();
        try {
            if (this.pos == 0) {
                this.tracer.print("Broker Call: prepareToReceive(CLOSE_WRITE_GOOD)", 4);
                this.sink.prepareToReceive(1);
            }
            else {
                this.tracer.print("Broker Call: write()", 4);
                this.sink.write(this.buffer, 0, this.pos);
                this.tracer.print("Broker Call: prepareToReceive(CLOSE_WRITE_GOOD)", 4);
                this.sink.prepareToReceive(1);
            }
        }
        catch (BrokerException ex) {
            if (!(ex instanceof BrokerException) || ex.getErrorNum() != -5) {
                throw ExceptionConverter.convertBrokerException(ex);
            }
            this.stopClose();
        }
        finally {
            this.sink = null;
        }
    }
    
    boolean isOpen() {
        return this.sink != null;
    }
    
    private void validate() throws IOException {
        if (this.sink == null) {
            throw new Open4GLError(62L, null);
        }
    }
}

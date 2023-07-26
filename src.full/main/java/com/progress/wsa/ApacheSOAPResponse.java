// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.soap.transport.TransportMessage;

public class ApacheSOAPResponse implements WsaSOAPResponse
{
    TransportMessage message;
    boolean fault;
    
    public ApacheSOAPResponse(final TransportMessage message, final boolean fault) {
        this.message = message;
        this.fault = fault;
    }
    
    public void write(final OutputStream outputStream) throws IOException {
        this.message.writeTo(outputStream);
        outputStream.flush();
    }
    
    public long getContentLength() {
        return this.message.getContentLength();
    }
    
    public boolean isFault() {
        return this.fault;
    }
}

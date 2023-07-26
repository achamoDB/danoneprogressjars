// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa;

import java.io.IOException;
import java.io.OutputStream;

public interface WsaSOAPResponse
{
    public static final WsaSOAPResponse NULL = new WsaSOAPResponse() {
        public void write(final OutputStream outputStream) {
        }
        
        public long getContentLength() {
            return 0L;
        }
        
        public boolean isFault() {
            return false;
        }
    };
    
    void write(final OutputStream p0) throws IOException;
    
    long getContentLength();
    
    boolean isFault();
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.webspeed.wsgateway;

import java.io.StringBufferInputStream;

class HTTPStream
{
    BufMgr rawData;
    StringBufferInputStream in;
    
    public int nextChar() {
        final int read = this.in.read();
        if (read != -1) {
            this.rawData.append(read);
        }
        return read;
    }
    
    public HTTPStream(final StringBufferInputStream in, final BufMgr rawData) {
        this.rawData = rawData;
        this.in = in;
    }
}

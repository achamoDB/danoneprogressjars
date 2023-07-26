// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.io.OutputStream;
import com.progress.open4gl.dynamicapi.InputTableStreamer;

public class ErrorStreamer extends InputTableStreamer
{
    public ErrorStreamer(final OutputStream outputStream) {
        super(outputStream);
    }
    
    protected void doStream(final String s) throws Exception {
        super.stream.write(1);
        super.stream.write(0);
        super.stream.write(0);
        super.stream.write(2);
        final Integer n = new Integer(0);
        super.streamer.streamColumn(new Integer(1), 4);
        super.streamer.streamColumn(n, 4);
        super.streamer.streamColumn(n, 4);
        super.streamer.streamColumn(n, 4);
        super.streamer.streamColumn(s, 1);
        super.streamer.streamColumn(s, 1);
    }
}

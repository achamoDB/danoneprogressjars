// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProDataException;
import java.io.OutputStream;
import java.util.Vector;

class TableStreamer implements Interruptable
{
    private InputTableStreamer streamer;
    private Vector schemas;
    
    TableStreamer(final OutputStream outputStream, final MetaSchema metaSchema) {
        if (metaSchema != null) {
            this.streamer = new InputTableStreamer(outputStream);
            this.schemas = metaSchema.getSchemas();
        }
        else {
            this.streamer = new InputTableStreamerNoSchema(outputStream);
        }
    }
    
    public void streamOut(final ParameterSet set, final int n) throws ClientException, ProDataException {
        if (this.streamer instanceof InputTableStreamerNoSchema) {
            ((InputTableStreamerNoSchema)this.streamer).streamOut(set);
        }
        else {
            this.streamer.streamOut(set, this.schemas, n);
        }
    }
    
    public void stop() {
        this.streamer.stop();
    }
}

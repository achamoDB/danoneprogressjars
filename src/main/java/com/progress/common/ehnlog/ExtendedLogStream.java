// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class ExtendedLogStream extends ByteArrayOutputStream
{
    private long mask;
    private int index;
    private IAppLogger logger;
    
    public ExtendedLogStream(final IAppLogger logger, final long mask, final int index) {
        this.logger = logger;
        this.mask = mask;
        this.index = index;
    }
    
    public PrintStream getPrintStream() {
        return new PrintStream(this, true);
    }
    
    public void close() throws IOException {
        this.flush();
    }
    
    public void flush() throws IOException {
        final String trim = this.toString().trim();
        if (trim.length() > 0 && this.logger.ifLogExtended(this.mask, this.index)) {
            this.logger.logExtended(this.index, trim, null);
        }
        this.reset();
    }
}

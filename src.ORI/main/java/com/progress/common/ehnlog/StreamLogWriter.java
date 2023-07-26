// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StreamLogWriter implements LogWriter
{
    private PrintWriter _writer;
    private OutputStream _stream;
    public static final PrintWriter NULL_WRITER;
    
    public StreamLogWriter() {
        this(System.err);
        this._writer = this.createPrintWriter();
    }
    
    public StreamLogWriter(final OutputStream stream) {
        this._writer = StreamLogWriter.NULL_WRITER;
        this._stream = stream;
    }
    
    public void start() throws IOException {
        this._writer = this.createPrintWriter();
    }
    
    public void stop() {
        this._writer.flush();
        this._writer.close();
        this._writer = StreamLogWriter.NULL_WRITER;
    }
    
    public void write(final String x) {
        this._writer.println(x);
        if (this._writer.checkError()) {
            System.err.println("Errors while logging.  Logging stopped.");
            this.stop();
        }
    }
    
    public void write(final Throwable t) {
        t.printStackTrace(this._writer);
        if (this._writer.checkError()) {
            System.err.println("Errors while logging.  Logging stopped.");
            this.stop();
        }
    }
    
    public void flush() {
        this._writer.flush();
    }
    
    public boolean registerThresholdEventHandler(final ILogEvntHandler logEvntHandler) {
        return false;
    }
    
    public String getCurrentLogFileName() {
        return new String("");
    }
    
    protected PrintWriter createPrintWriter() {
        return new PrintWriter(this._stream);
    }
    
    static {
        NULL_WRITER = new PrintWriter(new OutputStream() {
            public void write(final int n) throws IOException {
            }
        });
    }
}

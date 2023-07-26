// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import java.io.IOException;

public interface LogWriter
{
    public static final LogWriter NULL = new LogWriter() {
        public void start() {
        }
        
        public void stop() {
        }
        
        public void write(final String s) {
        }
        
        public void write(final Throwable t) {
        }
        
        public void flush() {
        }
        
        public boolean registerThresholdEventHandler(final ILogEvntHandler logEvntHandler) {
            return false;
        }
        
        public String getCurrentLogFileName() {
            return new String("");
        }
    };
    
    void start() throws IOException;
    
    void stop();
    
    void write(final String p0);
    
    void write(final Throwable p0);
    
    void flush();
    
    boolean registerThresholdEventHandler(final ILogEvntHandler p0);
    
    String getCurrentLogFileName();
}

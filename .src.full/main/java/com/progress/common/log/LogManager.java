// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import com.progress.common.event.LogEvent;
import com.progress.common.event.ProEvent;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.progress.common.event.IEventListener;

public class LogManager implements IEventListener
{
    OutputStream m_outputStream;
    
    public LogManager(final OutputStream outputStream) {
        this.m_outputStream = outputStream;
    }
    
    public LogManager(final String name) throws IOException {
        this.m_outputStream = new FileOutputStream(name);
    }
    
    public void processEvent(final ProEvent proEvent) {
        try {
            this.m_outputStream.write((((LogEvent)proEvent).getLocalizedMessage() + "\n").getBytes());
        }
        catch (IOException ex) {}
    }
}

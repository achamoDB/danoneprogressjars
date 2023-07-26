// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.IOException;

public interface IubMsgOutputStream
{
    void writeMsg(final ubMsg p0) throws IOException, NetworkProtocolException;
    
    void flushMsg() throws IOException, NetworkProtocolException;
    
    void close() throws IOException;
    
    void setMsgBufferSize(final int p0) throws Exception;
    
    int getMsgBufferSize();
    
    void setLoggingTraceLevel(final int p0) throws Exception;
    
    int getLoggingTraceLevel();
}

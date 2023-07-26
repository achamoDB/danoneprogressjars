// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.IOException;

public interface IubMsgInputStream
{
    ubMsg readMsg() throws IOException, ubMsg.MsgFormatException, NetworkProtocolException;
    
    int available() throws IOException;
    
    void close() throws IOException;
    
    void setMsgBufferSize(final int p0) throws Exception;
    
    int getMsgBufferSize();
    
    void setLoggingTraceLevel(final int p0) throws Exception;
    
    int getLoggingTraceLevel();
}

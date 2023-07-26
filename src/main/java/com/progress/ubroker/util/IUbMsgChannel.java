// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.io.IOException;

public interface IUbMsgChannel
{
    ubMsg readMsg() throws IOException, ubMsg.MsgFormatException, NetworkProtocolException;
    
    void writeMsg(final ubMsg p0) throws IOException, NetworkProtocolException;
    
    void close() throws IOException;
}

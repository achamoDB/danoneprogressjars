// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsockagent;

public interface ServerComSockAgentListener
{
    void clientConnect(final int p0);
    
    void clientDisconnect(final int p0);
    
    void messageReceived(final int p0, final ComMsgAgent p1);
}

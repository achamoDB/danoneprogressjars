// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsock;

public interface ServerComSockListener
{
    void clientConnect(final int p0);
    
    void clientDisconnect(final int p0);
    
    void messageReceived(final int p0, final ComMsg p1);
}

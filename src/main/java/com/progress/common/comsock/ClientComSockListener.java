// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsock;

public interface ClientComSockListener
{
    void disconnect();
    
    void messageReceived(final ComMsg p0);
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;

public interface IClient extends IEventStream
{
    void processEvent(final IEventObject p0, final IEventListener p1, final Object p2, final IClient p3, final boolean p4) throws RemoteException;
    
    void terminate() throws RemoteException;
}

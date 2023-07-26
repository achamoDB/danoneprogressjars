// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IEventListener extends Remote
{
    void processEvent(final IEventObject p0) throws RemoteException;
}

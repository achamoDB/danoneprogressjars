// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IEventBrokerDebug extends Remote
{
    void printDispatchTableRemote() throws RemoteException;
}

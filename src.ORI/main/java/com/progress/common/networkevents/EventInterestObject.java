// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

abstract class EventInterestObject extends UnicastRemoteObject implements IEventInterestObject
{
    IClient client;
    
    EventInterestObject(final IClient client) throws RemoteException {
        this.client = null;
        this.client = client;
    }
    
    public abstract void revokeInterest(final IEventInterestObject p0) throws RemoteException;
    
    public abstract void revokeInterest() throws RemoteException;
    
    public abstract Class eventType() throws RemoteException;
    
    public IClient getClient() throws RemoteException {
        return this.client;
    }
}

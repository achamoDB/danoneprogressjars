// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IEventInterestObject extends Remote
{
    void revokeInterest() throws RemoteException;
    
    void revokeInterest(final IEventInterestObject p0) throws RemoteException;
    
    Class eventType() throws RemoteException;
    
    IClient getClient() throws RemoteException;
}

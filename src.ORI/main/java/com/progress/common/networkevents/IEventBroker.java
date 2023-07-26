// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import com.progress.common.rmiregistry.IPingable;
import com.progress.common.rmiregistry.RegistryManager;
import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IEventBroker extends Remote
{
    IEventStream openEventStream(final String p0) throws RemoteException;
    
    IEventStream openEventStream(final String p0, final int p1) throws RemoteException;
    
    void closeEventStream(final IEventStream p0) throws RemoteException;
    
    IEventInterestObject expressInterest(final Class p0, final IEventListener p1, final IEventStream p2) throws RemoteException;
    
    IEventInterestObject expressInterest(final Class p0, final IEventListener p1, final RemoteStub p2, final IEventStream p3) throws RemoteException;
    
    IEventInterestObject expressInterest(final Class p0, final IEventListener p1, final Class p2, final IEventStream p3) throws RemoteException;
    
    void revokeInterest(final IEventInterestObject p0) throws RemoteException;
    
    void postEvent(final IEventObject p0) throws RemoteException;
    
    IEventInterestObject locateObject(final String p0, final RegistryManager p1, final IEventListener p2) throws RemoteException;
    
    IEventInterestObject locateObject(final String p0, final RegistryManager p1, final int p2, final IEventListener p3) throws RemoteException;
    
    IEventInterestObject monitorObject(final IPingable p0, final IEventListener p1) throws RemoteException;
    
    IEventInterestObject monitorObject(final IPingable p0, final int p1, final IEventListener p2) throws RemoteException;
    
    IEventInterestObject monitorObject(final IPingable p0, final int p1, final int p2, final IEventListener p3) throws RemoteException;
}

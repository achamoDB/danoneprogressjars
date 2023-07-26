// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public interface IJAExecutableObject extends IJARemoteObject
{
    boolean isStartable() throws RemoteException;
    
    void start() throws RemoteException;
    
    boolean isStopable() throws RemoteException;
    
    void stop() throws RemoteException;
    
    void delete(final Object p0) throws RemoteException;
    
    boolean isIdle() throws RemoteException;
    
    boolean isStarting() throws RemoteException;
    
    boolean isInitializing() throws RemoteException;
    
    boolean isRunning() throws RemoteException;
    
    boolean isShuttingDown() throws RemoteException;
    
    boolean isDeleteable() throws RemoteException;
    
    String getStateDescriptor() throws RemoteException;
    
    JAStatusObject getStatus() throws RemoteException;
}

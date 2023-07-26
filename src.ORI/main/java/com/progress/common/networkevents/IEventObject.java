// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.util.Date;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IEventObject extends Remote
{
    Object issuer() throws RemoteException;
    
    Date timeIssued() throws RemoteException;
    
    Class classDef() throws RemoteException;
    
    String eventTypeString() throws RemoteException;
    
    boolean expedite() throws RemoteException;
    
    String description() throws RemoteException;
    
    String issuerName() throws RemoteException;
}

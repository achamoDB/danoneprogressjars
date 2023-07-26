// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IRemoteInteger extends Remote
{
    void put(final int p0) throws RemoteException;
    
    int get() throws RemoteException;
}

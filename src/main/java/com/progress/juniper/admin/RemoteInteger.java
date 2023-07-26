// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteInteger extends UnicastRemoteObject implements IRemoteInteger
{
    int value;
    
    public RemoteInteger() throws RemoteException {
        this.value = 0;
    }
    
    public RemoteInteger(final int value) throws RemoteException {
        this.value = value;
    }
    
    public void put(final int value) throws RemoteException {
        this.value = value;
    }
    
    public int get() throws RemoteException {
        return this.value;
    }
}

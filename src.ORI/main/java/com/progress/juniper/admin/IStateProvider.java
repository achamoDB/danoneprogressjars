// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IStateProvider extends Remote
{
    JAStatusObject getStatus() throws RemoteException;
}

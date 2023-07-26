// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IDispatchEntry extends Remote
{
    DispatchEntry getBaseObject() throws RemoteException;
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IEFailure extends Remote
{
    String reasonForFailure() throws RemoteException;
}

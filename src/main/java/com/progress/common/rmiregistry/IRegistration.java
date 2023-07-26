// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IRegistration extends Remote
{
    void registered(final Object p0, final IPingable p1) throws RemoteException;
}

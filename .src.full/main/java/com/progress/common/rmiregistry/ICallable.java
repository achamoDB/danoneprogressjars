// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface ICallable extends Remote
{
    Object call(final Object p0) throws RemoteException, Exception;
}

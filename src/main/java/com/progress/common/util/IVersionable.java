// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IVersionable extends Remote
{
    String getVersion() throws RemoteException;
}

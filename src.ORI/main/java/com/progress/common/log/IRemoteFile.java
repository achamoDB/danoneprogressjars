// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventListener;
import java.rmi.Remote;

public interface IRemoteFile extends Remote
{
    IFileRef openFile(final String p0, final IEventListener p1) throws RemoteException;
}

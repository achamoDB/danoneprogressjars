// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraRemoteObject;

public interface IJARemoteObject extends IChimeraRemoteObject
{
    IJAPlugIn getPlugIn() throws RemoteException;
    
    RemoteStub remoteStub() throws RemoteException;
}

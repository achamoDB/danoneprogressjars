// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteStub;

public abstract class ChimeraRemoteObject implements IChimeraRemoteObject
{
    private RemoteStub stubObject;
    
    public ChimeraRemoteObject() throws RemoteException {
        this.stubObject = null;
        this.stubObject = UnicastRemoteObject.exportObject(this);
    }
    
    public RemoteStub stub() {
        return this.stubObject;
    }
}

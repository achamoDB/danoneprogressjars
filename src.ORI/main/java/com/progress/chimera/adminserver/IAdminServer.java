// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.RemoteException;
import java.util.Vector;
import java.rmi.Remote;

public interface IAdminServer extends Remote
{
    Vector getPlugins() throws RemoteException;
    
    Vector getPlugins(final String p0) throws RemoteException;
    
    void shutdown() throws RemoteException;
}

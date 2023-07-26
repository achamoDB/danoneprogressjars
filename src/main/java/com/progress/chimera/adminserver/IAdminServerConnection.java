// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IAdminServerConnection extends Remote
{
    IAdminServer connect(final String p0, final String p1) throws RemoteException;
    
    IAdminServer connect(final String p0, final String p1, final boolean p2) throws RemoteException;
}

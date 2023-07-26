// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IAdminJuniper extends Remote
{
    void serviceClosed(final String p0) throws RemoteException;
    
    void serviceFailed(final String p0, final String[] p1) throws RemoteException;
    
    void exiting() throws RemoteException;
}

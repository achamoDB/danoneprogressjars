// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.rmiregistry.IPingable;

public interface IJuniper extends IPingable
{
    void setAdminServer(final IAdminJuniper p0) throws RemoteException;
    
    void disconnectAdminServer() throws RemoteException;
    
    void shutDown() throws RemoteException;
}

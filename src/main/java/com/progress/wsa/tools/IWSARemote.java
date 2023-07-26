// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import com.progress.wsa.admin.AppContainer;
import java.util.Enumeration;
import com.progress.wsa.admin.AppRuntimeProps;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IWSARemote extends Remote
{
    public static final int LIST_TYPE_FRIENDLY_NAME = 0;
    public static final int LIST_TYPE_NAMESPACE = 1;
    
    void setWsaUid(final String p0, final String p1) throws RemoteException;
    
    AppRuntimeProps getDefaultRuntimeProps() throws RemoteException;
    
    void setDefaultRuntimeProps(final AppRuntimeProps p0) throws RemoteException;
    
    Enumeration list(final int p0) throws RemoteException;
    
    void Deploy(final String p0, final String p1, final String p2) throws RemoteException;
    
    void Import(final AppContainer p0) throws RemoteException;
    
    IWSAAppRemote Query(final String p0) throws RemoteException;
}

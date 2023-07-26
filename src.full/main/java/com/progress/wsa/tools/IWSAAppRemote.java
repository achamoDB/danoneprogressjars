// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.tools;

import com.progress.wsa.admin.AppContainer;
import com.progress.wsa.admin.AppRuntimeStats;
import com.progress.wsa.admin.AppRuntimeProps;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IWSAAppRemote extends Remote
{
    void undeploy() throws RemoteException;
    
    AppRuntimeProps getRuntimeProps() throws RemoteException;
    
    void setRuntimeProps(final AppRuntimeProps p0) throws RemoteException;
    
    AppRuntimeStats getRuntimeStatistics() throws RemoteException;
    
    void resetRuntimeStatistics() throws RemoteException;
    
    void enableApplication() throws RemoteException;
    
    void disableApplication() throws RemoteException;
    
    AppContainer export() throws RemoteException;
}

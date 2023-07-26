// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;

public interface IJADatabase extends IChimeraHierarchy, IJARemoteObject, IJAHierarchy, IJAExecutableObject, IJAAgentRemoteObject, IJAAgentRemote, IJAReplRemoteObject
{
    public static final int DB_VANILLA = 0;
    public static final int DB_ERROR = 2;
    public static final int DB_REPL_SOURCE = 11;
    public static final int DB_REPL_TARGET = 12;
    
    void setUser(final String p0) throws RemoteException;
    
    String getUser() throws RemoteException;
    
    IJAConfiguration getDefaultConfiguration() throws RemoteException;
    
    IJAConfiguration getRunningConfiguration() throws RemoteException;
    
    IJAConfiguration getCurrentConfiguration() throws RemoteException;
    
    String getDatabaseFileName() throws RemoteException;
    
    JAStatusObject getStatus(final IJAConfiguration p0) throws RemoteException;
    
    void refresh() throws RemoteException;
    
    int getDatabaseEnablement() throws RemoteException;
}

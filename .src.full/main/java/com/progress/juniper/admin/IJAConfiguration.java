// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;

public interface IJAConfiguration extends IChimeraHierarchy, IJAHierarchy, IJARemoteObject, IJAExecutableObject
{
    void toggleDefaultSetting() throws RemoteException;
    
    boolean isDefault() throws RemoteException;
    
    boolean portUsed(final String p0) throws RemoteException;
    
    IJAAuxiliary getBIWriter() throws RemoteException;
    
    IJAAuxiliary getAIWriter() throws RemoteException;
    
    IJAAuxiliary getAPWriter() throws RemoteException;
    
    IJAAuxiliary getWatchdog() throws RemoteException;
    
    Enumeration getAuxiliaries() throws RemoteException;
    
    boolean isNetworked() throws RemoteException;
}

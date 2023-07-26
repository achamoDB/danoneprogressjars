// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.networkevents.IEventBroker;
import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;

public interface IJAPlugIn extends IChimeraHierarchy, IJAHierarchy, IJARemoteObject
{
    String getOS() throws RemoteException;
    
    IEventBroker getEventBroker() throws RemoteException;
    
    IPropertyManagerRemote getPropertyManager() throws RemoteException;
    
    String getPropFileName() throws RemoteException;
    
    IJADatabase createDatabase(final String p0, final String p1, final Object p2) throws RemoteException;
    
    boolean initializationComplete() throws RemoteException;
    
    ILicenseMgr getLicenseManager() throws RemoteException;
    
    boolean isAgentLicensed() throws RemoteException;
    
    boolean isReplLicensed() throws RemoteException;
    
    IJARmiComponent getJARmiComponent() throws RemoteException;
}

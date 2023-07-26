// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.system;

import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraRemoteObject;

public interface ISystemPlugIn extends IChimeraRemoteObject
{
    String getOS() throws RemoteException;
    
    IAdministrationServer getAdminServer() throws RemoteException;
    
    IEventBroker getEventBroker() throws RemoteException;
    
    IEventStream getEventStream() throws RemoteException;
    
    ISystemPlugIn getPlugIn() throws RemoteException;
    
    ILicenseMgr getLicenseManager() throws RemoteException;
}

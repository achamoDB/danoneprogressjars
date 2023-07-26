// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.networkevents.IEventBroker;
import java.rmi.RemoteException;
import java.util.Vector;
import java.rmi.Remote;

public interface IAdministrationServer extends Remote
{
    Vector getServerPluginInfo() throws RemoteException;
    
    String getHost() throws RemoteException;
    
    String getPort() throws RemoteException;
    
    boolean isReqUserName() throws RemoteException;
    
    String getAdminPort() throws RemoteException;
    
    String getOS() throws RemoteException;
    
    String getDbPropFile() throws RemoteException;
    
    String getDbaPropFile() throws RemoteException;
    
    String getSmdbPropFile() throws RemoteException;
    
    String getUbPropFile() throws RemoteException;
    
    String getMgmtPropFile() throws RemoteException;
    
    String getFathomPropFile() throws RemoteException;
    
    String getFathomInitParams() throws RemoteException;
    
    IEventBroker getEventBroker() throws RemoteException;
    
    EventBroker getEventBrokerLocal() throws RemoteException;
    
    IEventStream getEventStream() throws RemoteException;
    
    ILicenseMgr getLicenseManager() throws RemoteException;
}

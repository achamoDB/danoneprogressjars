// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Hashtable;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IYodaSharedResources extends Remote
{
    String getPropGroupPath() throws RemoteException;
    
    String getSvcName() throws RemoteException;
    
    String getAdminSrvrOSName() throws RemoteException;
    
    String getAdminSrvrHost() throws RemoteException;
    
    String getAdminServerIPAddr() throws RemoteException;
    
    String getAdminSrvrPort() throws RemoteException;
    
    String getAdminSrvrURL() throws RemoteException;
    
    String[] getLoginUserInfo() throws RemoteException;
    
    IPropertyManagerRemote getRPM() throws RemoteException;
    
    String getPropertyFilename() throws RemoteException;
    
    String getSchemaFilename() throws RemoteException;
    
    String[] getSchemaPropFnList() throws RemoteException;
    
    IEventBroker getEventBroker() throws RemoteException;
    
    IYodaRMI getRemoteManageObject() throws RemoteException;
    
    RemoteStub getRemStub() throws RemoteException;
    
    boolean getRscLookedUp(final String p0) throws RemoteException;
    
    void regRscLookedUp(final String p0) throws RemoteException;
    
    Hashtable getLogFiles(final String p0) throws RemoteException;
}

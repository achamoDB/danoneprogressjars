// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;

public interface INotificationEvent extends IEventObject
{
    public static final String notificationType = null;
    
    String getNotificationType() throws RemoteException;
    
    short getCategory() throws RemoteException;
    
    String getSubCategory() throws RemoteException;
    
    String getNotificationName() throws RemoteException;
    
    int getSeverityLevel() throws RemoteException;
    
    String getSource() throws RemoteException;
    
    void setSource(final String p0) throws RemoteException;
    
    Object getEventData() throws RemoteException;
    
    String getErrorString() throws RemoteException;
}

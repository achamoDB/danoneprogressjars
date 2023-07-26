// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EConfigurationRemoved extends EConfigurationEvent implements IERemoveEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EConfigurationRemoved(final IJADatabase ijaDatabase, final IJAConfiguration ijaConfiguration, final Object guiID) throws RemoteException {
        super(ijaDatabase, ijaConfiguration);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
    
    static {
        EConfigurationRemoved.notificationName = "EConfigurationRemoved";
        EConfigurationRemoved.notificationType = "application.state." + EConfigurationRemoved.notificationName;
    }
}

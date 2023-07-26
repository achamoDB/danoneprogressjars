// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EConfigurationStateChanged extends EConfigurationEvent implements IEStateChangedEvent
{
    public static String notificationName;
    public static String notificationType;
    JAStatusObject statusObject;
    
    EConfigurationStateChanged(final IJADatabase ijaDatabase, final IJAConfiguration ijaConfiguration, final JAStatusObject statusObject) throws RemoteException {
        super(ijaDatabase, ijaConfiguration);
        this.statusObject = null;
        super.addEventData(statusObject);
        this.statusObject = statusObject;
    }
    
    public JAStatusObject getStatus() {
        return this.statusObject;
    }
    
    static {
        EConfigurationStateChanged.notificationName = "EConfigurationStateChanged";
        EConfigurationStateChanged.notificationType = "application.state." + EConfigurationStateChanged.notificationName;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EConfigurationCrashEvent extends EConfigurationEvent
{
    public static String notificationName;
    public static String notificationType;
    
    EConfigurationCrashEvent(final IJADatabase ijaDatabase, final IJAConfiguration ijaConfiguration) throws RemoteException {
        super(ijaDatabase, ijaConfiguration);
    }
    
    static {
        EConfigurationCrashEvent.notificationName = "EConfigurationCrashEvent";
        EConfigurationCrashEvent.notificationType = "application.state." + EConfigurationCrashEvent.notificationName;
    }
}

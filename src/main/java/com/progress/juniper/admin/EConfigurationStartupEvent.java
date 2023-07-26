// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EConfigurationStartupEvent extends EConfigurationEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EConfigurationStartupEvent(final Object o, final IJAConfiguration ijaConfiguration) throws RemoteException {
        super(o, ijaConfiguration);
    }
    
    static {
        EConfigurationStartupEvent.notificationName = "EConfigurationStartupEvent";
        EConfigurationStartupEvent.notificationType = "application.state." + EConfigurationStartupEvent.notificationName;
    }
}

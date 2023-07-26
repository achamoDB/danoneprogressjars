// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EServerGroupCrashEvent extends EServerGroupEvent
{
    public static String notificationName;
    public static String notificationType;
    
    EServerGroupCrashEvent(final IJAConfiguration ijaConfiguration, final IJAService ijaService) throws RemoteException {
        super(ijaConfiguration, ijaService);
    }
    
    static {
        EServerGroupCrashEvent.notificationName = "EServerGroupCrashEvent";
        EServerGroupCrashEvent.notificationType = "application.state." + EServerGroupCrashEvent.notificationName;
    }
}

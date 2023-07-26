// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EServerGroupStartupEvent extends EServerGroupEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EServerGroupStartupEvent(final JAService jaService) throws RemoteException {
        super(jaService.remoteStub(), jaService);
    }
    
    static {
        EServerGroupStartupEvent.notificationName = "EServerGroupStartupEvent";
        EServerGroupStartupEvent.notificationType = "application.state." + EServerGroupStartupEvent.notificationName;
    }
}

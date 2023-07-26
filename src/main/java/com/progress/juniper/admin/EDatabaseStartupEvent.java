// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EDatabaseStartupEvent extends EDatabaseEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EDatabaseStartupEvent(final JADatabase jaDatabase) throws RemoteException {
        super(jaDatabase.remoteStub(), jaDatabase);
    }
    
    static {
        EDatabaseStartupEvent.notificationName = "EDatabaseStartupEvent";
        EDatabaseStartupEvent.notificationType = "application.state." + EDatabaseStartupEvent.notificationName;
    }
}

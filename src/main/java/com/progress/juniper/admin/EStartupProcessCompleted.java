// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EStartupProcessCompleted extends EDatabaseStartupEvent implements IEStartupComplete
{
    public static String notificationName;
    public static String notificationType;
    
    public EStartupProcessCompleted(final JADatabase jaDatabase) throws RemoteException {
        super(jaDatabase);
        super.addEventData(jaDatabase.getStatus());
    }
    
    static {
        EStartupProcessCompleted.notificationName = "EStartupProcessCompleted";
        EStartupProcessCompleted.notificationType = "application.state." + EStartupProcessCompleted.notificationName;
    }
}

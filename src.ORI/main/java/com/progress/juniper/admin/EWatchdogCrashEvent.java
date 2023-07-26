// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EWatchdogCrashEvent extends EAuxiliaryCrashEvent
{
    public static String notificationName;
    public static String notificationType;
    
    EWatchdogCrashEvent(final IJAAuxiliary ijaAuxiliary) throws RemoteException {
        super(ijaAuxiliary);
    }
    
    static {
        EWatchdogCrashEvent.notificationName = "EWatchdogCrashEvent";
        EWatchdogCrashEvent.notificationType = "application.state." + EWatchdogCrashEvent.notificationName;
    }
}

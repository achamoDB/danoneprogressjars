// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAPWCrashEvent extends EAuxiliaryCrashEvent
{
    public static String notificationName;
    public static String notificationType;
    
    EAPWCrashEvent(final IJAAuxiliary ijaAuxiliary) throws RemoteException {
        super(ijaAuxiliary);
    }
    
    static {
        EAPWCrashEvent.notificationName = "EAPWCrashEvent";
        EAPWCrashEvent.notificationType = "application.state." + EAPWCrashEvent.notificationName;
    }
}

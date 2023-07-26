// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EBICrashEvent extends EAuxiliaryCrashEvent
{
    public static String notificationName;
    public static String notificationType;
    
    EBICrashEvent(final IJAAuxiliary ijaAuxiliary) throws RemoteException {
        super(ijaAuxiliary);
    }
    
    static {
        EBICrashEvent.notificationName = "EBICrashEvent";
        EBICrashEvent.notificationType = "application.state." + EBICrashEvent.notificationName;
    }
}

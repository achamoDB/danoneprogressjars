// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAICrashEvent extends EAuxiliaryCrashEvent
{
    public static String notificationName;
    public static String notificationType;
    
    EAICrashEvent(final IJAAuxiliary ijaAuxiliary) throws RemoteException {
        super(ijaAuxiliary);
    }
    
    static {
        EAICrashEvent.notificationName = "EAICrashEvent";
        EAICrashEvent.notificationType = "application.state." + EAICrashEvent.notificationName;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAuxiliaryCrashEvent extends EAuxiliaryEvent
{
    public static String notificationName;
    public static String notificationType;
    
    EAuxiliaryCrashEvent(final IJAAuxiliary ijaAuxiliary) throws RemoteException {
        super(ijaAuxiliary, ijaAuxiliary);
    }
    
    static {
        EAuxiliaryCrashEvent.notificationName = "EAuxiliaryCrashEvent";
        EAuxiliaryCrashEvent.notificationType = "application.state." + EAuxiliaryCrashEvent.notificationName;
    }
}

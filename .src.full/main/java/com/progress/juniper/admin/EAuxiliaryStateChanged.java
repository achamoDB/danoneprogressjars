// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAuxiliaryStateChanged extends EAuxiliaryEvent implements IEStateChangedEvent
{
    public static String notificationName;
    public static String notificationType;
    JAStatusObject statusObject;
    
    EAuxiliaryStateChanged(final IJAAuxiliary ijaAuxiliary, final JAStatusObject statusObject) throws RemoteException {
        super(ijaAuxiliary, ijaAuxiliary);
        this.statusObject = null;
        super.addEventData(statusObject);
        this.statusObject = statusObject;
    }
    
    public JAStatusObject getStatus() {
        return this.statusObject;
    }
    
    static {
        EAuxiliaryStateChanged.notificationName = "EAuxiliaryStateChanged";
        EAuxiliaryStateChanged.notificationType = "application.state." + EAuxiliaryStateChanged.notificationName;
    }
}

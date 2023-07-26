// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAuxiliaryEvent extends EJAEvent implements IEAuxiliaryEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EAuxiliaryEvent(final Object o, final IJAAuxiliary ijaAuxiliary) throws RemoteException {
        super(o, ijaAuxiliary);
    }
    
    public IJAAuxiliary auxiliary() {
        return (IJAAuxiliary)this.getObject();
    }
    
    static {
        EAuxiliaryEvent.notificationName = "EAuxiliaryEvent";
        EAuxiliaryEvent.notificationType = "application.state." + EAuxiliaryEvent.notificationName;
    }
}

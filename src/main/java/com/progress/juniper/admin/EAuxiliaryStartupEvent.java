// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.rmi.server.RemoteStub;

public class EAuxiliaryStartupEvent extends EAuxiliaryEvent implements IEJAEvent
{
    public static String notificationName;
    public static String notificationType;
    String description;
    IJAAuxiliary auxiliary;
    
    public String description() {
        return this.description;
    }
    
    public IJAAuxiliary auxiliary() {
        return this.auxiliary;
    }
    
    public EAuxiliaryStartupEvent(final RemoteStub remoteStub, final JAAuxiliary auxiliary, final String description) throws RemoteException {
        super(remoteStub, auxiliary);
        this.description = null;
        this.auxiliary = null;
        this.description = description;
        this.auxiliary = auxiliary;
    }
    
    static {
        EAuxiliaryStartupEvent.notificationName = "EAuxiliaryStartupEvent";
        EAuxiliaryStartupEvent.notificationType = "application.state." + EAuxiliaryStartupEvent.notificationName;
    }
}

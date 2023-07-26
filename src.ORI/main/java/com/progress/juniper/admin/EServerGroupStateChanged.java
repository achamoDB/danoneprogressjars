// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EServerGroupStateChanged extends EServerGroupEvent implements IEStateChangedEvent
{
    public static String notificationName;
    public static String notificationType;
    JAStatusObject statusObject;
    
    EServerGroupStateChanged(final IJAConfiguration ijaConfiguration, final IJAService ijaService) throws RemoteException {
        super(ijaConfiguration, ijaService);
        super.addEventData(this.statusObject = null);
        this.statusObject = this.statusObject;
    }
    
    public JAStatusObject getStatus() {
        return this.statusObject;
    }
    
    static {
        EServerGroupStateChanged.notificationName = "EServerGroupStateChanged";
        EServerGroupStateChanged.notificationType = "application.state." + EServerGroupStateChanged.notificationName;
    }
}

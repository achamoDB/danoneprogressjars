// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EServerGroupEvent extends EJAEvent implements IEServerGroupEvent
{
    public static String notificationName;
    public static String notificationType;
    IJAService serverGroup;
    
    public IJAService serverGroup() {
        return (IJAService)this.getObject();
    }
    
    EServerGroupEvent(final Object o, final IJAService ijaService) throws RemoteException {
        super(o, ijaService);
        this.serverGroup = null;
    }
    
    static {
        EServerGroupEvent.notificationName = "EServerGroupEvent";
        EServerGroupEvent.notificationType = "application.state." + EServerGroupEvent.notificationName;
    }
}

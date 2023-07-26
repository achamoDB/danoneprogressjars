// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EServerGroupRemoved extends EServerGroupEvent implements IERemoveEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EServerGroupRemoved(final IJAConfiguration ijaConfiguration, final IJAService ijaService, final Object guiID) throws RemoteException {
        super(ijaConfiguration, ijaService);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
    
    static {
        EServerGroupRemoved.notificationName = "EServerGroupRemoved";
        EServerGroupRemoved.notificationType = "application.state." + EServerGroupRemoved.notificationName;
    }
}

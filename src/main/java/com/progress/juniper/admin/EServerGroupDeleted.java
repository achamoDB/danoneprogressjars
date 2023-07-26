// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.util.IEDeleteEvent;

public class EServerGroupDeleted extends EServerGroupEvent implements IEDeleteEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EServerGroupDeleted(final IJAService ijaService, final Object guiID) throws RemoteException {
        super(ijaService, ijaService);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
    
    static {
        EServerGroupDeleted.notificationName = "EServerGroupDeleted";
        EServerGroupDeleted.notificationType = "application.state." + EServerGroupDeleted.notificationName;
    }
}

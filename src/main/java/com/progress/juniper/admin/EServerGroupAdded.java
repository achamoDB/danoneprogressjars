// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.util.IEAddEvent;

public class EServerGroupAdded extends EServerGroupEvent implements IEAddEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EServerGroupAdded(final IJAConfiguration ijaConfiguration, final IJAService ijaService, final Object guiID) throws RemoteException {
        super(ijaConfiguration, ijaService);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object getChild() {
        return this.getObject();
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
    
    static {
        EServerGroupAdded.notificationName = "EServerGroupAdded";
        EServerGroupAdded.notificationType = "application.state." + EServerGroupAdded.notificationName;
    }
}

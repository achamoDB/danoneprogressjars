// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EDatabaseRemoved extends EDatabaseEvent implements IERemoveEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EDatabaseRemoved(final IJAPlugIn ijaPlugIn, final IJADatabase ijaDatabase, final Object guiID) throws RemoteException {
        super(ijaPlugIn, ijaDatabase);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
    
    static {
        EDatabaseRemoved.notificationName = "EDatabaseRemoved";
        EDatabaseRemoved.notificationType = "application.state." + EDatabaseRemoved.notificationName;
    }
}

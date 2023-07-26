// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.util.IEDeleteEvent;

public class EDatabaseDeleted extends EDatabaseEvent implements IEDeleteEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EDatabaseDeleted(final IJADatabase ijaDatabase, final Object guiID) throws RemoteException {
        super(ijaDatabase, ijaDatabase);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
    
    static {
        EDatabaseDeleted.notificationName = "EDatabaseDeleted";
        EDatabaseDeleted.notificationType = "application.state." + EDatabaseDeleted.notificationName;
    }
}

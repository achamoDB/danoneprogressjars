// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.util.IEAddEvent;

public class EDatabaseAdded extends EDatabaseEvent implements IEAddEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EDatabaseAdded(final IJAPlugIn ijaPlugIn, final IJADatabase ijaDatabase, final Object guiID) throws RemoteException {
        super(ijaPlugIn, ijaDatabase);
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
        EDatabaseAdded.notificationName = "EDatabaseAdded";
        EDatabaseAdded.notificationType = "application.state." + EDatabaseAdded.notificationName;
    }
}

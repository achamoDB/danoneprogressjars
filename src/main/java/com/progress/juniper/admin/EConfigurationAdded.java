// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.util.IEAddEvent;

public class EConfigurationAdded extends EConfigurationEvent implements IEAddEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EConfigurationAdded(final IJADatabase ijaDatabase, final IJAConfiguration ijaConfiguration, final Object guiID) throws RemoteException {
        super(ijaDatabase, ijaConfiguration);
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
        EConfigurationAdded.notificationName = "EConfigurationAdded";
        EConfigurationAdded.notificationType = "application.state." + EConfigurationAdded.notificationName;
    }
}

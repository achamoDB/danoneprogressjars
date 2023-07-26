// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.util.IEDeleteEvent;

public class EConfigurationDeleted extends EConfigurationEvent implements IEDeleteEvent
{
    public static String notificationName;
    public static String notificationType;
    Object guiID;
    
    EConfigurationDeleted(final IJAConfiguration ijaConfiguration, final Object guiID) throws RemoteException {
        super(ijaConfiguration, ijaConfiguration);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
    
    static {
        EConfigurationDeleted.notificationName = "EConfigurationDeleted";
        EConfigurationDeleted.notificationType = "application.state." + EConfigurationDeleted.notificationName;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class EDatabaseNameChanged extends EJAEvent implements IEventObject
{
    public static String notificationName;
    public static String notificationType;
    
    EDatabaseNameChanged(final IJADatabase ijaDatabase, final String value) throws RemoteException {
        super(ijaDatabase, ijaDatabase);
        super.m_content.put("newDatabaseFilename", value);
    }
    
    static {
        EDatabaseNameChanged.notificationName = "EDatabaseNameChanged";
        EDatabaseNameChanged.notificationType = "application.state." + EDatabaseNameChanged.notificationName;
    }
}

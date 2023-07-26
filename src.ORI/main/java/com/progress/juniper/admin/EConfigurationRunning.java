// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.EventObject;

public class EConfigurationRunning extends EventObject
{
    public static String notificationName;
    public static String notificationType;
    
    public EConfigurationRunning(final Object o) throws RemoteException {
        super(o);
    }
    
    static {
        EConfigurationRunning.notificationName = "EConfigurationRunning";
        EConfigurationRunning.notificationType = "application.state." + EConfigurationRunning.notificationName;
    }
}

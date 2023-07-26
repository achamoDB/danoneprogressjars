// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class EAutostartCompleted extends EJAEvent implements IEventObject
{
    public static String notificationName;
    public static String notificationType;
    
    public EAutostartCompleted(final Object o) throws RemoteException {
        super(o, o);
    }
    
    static {
        EAutostartCompleted.notificationName = "EAutostartCompleted";
        EAutostartCompleted.notificationType = "application.state." + EAutostartCompleted.notificationName;
    }
}

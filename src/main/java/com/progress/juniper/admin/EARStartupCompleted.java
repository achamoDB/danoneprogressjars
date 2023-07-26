// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class EARStartupCompleted extends EAREvent implements IEventObject
{
    public static String notificationName;
    public static String notificationType;
    
    public EARStartupCompleted(final IJAAgent ijaAgent) throws RemoteException {
        super(ijaAgent, ijaAgent);
    }
    
    static {
        EARStartupCompleted.notificationName = "EARStartupCompleted";
        EARStartupCompleted.notificationType = "application.state." + EARStartupCompleted.notificationName;
    }
}

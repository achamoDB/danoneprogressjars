// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class EPrimaryStartupFailed extends EStartupProcessFailure implements IEStartupComplete
{
    public static String notificationName;
    public static String notificationType;
    
    public EPrimaryStartupFailed(final JADatabase jaDatabase, final IEventObject eventObject) throws RemoteException {
        super(jaDatabase, eventObject);
    }
    
    static {
        EPrimaryStartupFailed.notificationName = "EPrimaryStartupFailed";
        EPrimaryStartupFailed.notificationType = "application.state." + EPrimaryStartupFailed.notificationName;
    }
}

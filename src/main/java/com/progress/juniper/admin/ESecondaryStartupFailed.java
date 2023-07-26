// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class ESecondaryStartupFailed extends EStartupProcessFailure
{
    public static String notificationName;
    public static String notificationType;
    
    public ESecondaryStartupFailed(final JADatabase jaDatabase, final IEventObject eventObject) throws RemoteException {
        super(jaDatabase, eventObject);
    }
    
    static {
        ESecondaryStartupFailed.notificationName = "ESecondaryStartupFailed";
        ESecondaryStartupFailed.notificationType = "application.state." + ESecondaryStartupFailed.notificationName;
    }
}

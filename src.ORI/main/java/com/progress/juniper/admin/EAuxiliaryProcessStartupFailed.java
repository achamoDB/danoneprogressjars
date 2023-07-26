// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class EAuxiliaryProcessStartupFailed extends EStartupProcessFailure
{
    public static String notificationName;
    public static String notificationType;
    
    public EAuxiliaryProcessStartupFailed(final JADatabase jaDatabase, final IEventObject eventObject) throws RemoteException {
        super(jaDatabase, eventObject);
    }
    
    static {
        EAuxiliaryProcessStartupFailed.notificationName = "EAuxiliaryProcessStartupFailed";
        EAuxiliaryProcessStartupFailed.notificationType = "application.state." + EAuxiliaryProcessStartupFailed.notificationName;
    }
}

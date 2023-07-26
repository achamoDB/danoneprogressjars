// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EWatchdogStartupFailed extends EAuxiliaryStartupFailed
{
    public static String notificationName;
    public static String notificationType;
    
    public EWatchdogStartupFailed(final JAAuxiliary jaAuxiliary, final String s, final String s2) throws RemoteException {
        super(jaAuxiliary, s, s2);
    }
    
    static {
        EWatchdogStartupFailed.notificationName = "EWatchdogStartupFailed";
        EWatchdogStartupFailed.notificationType = "application.state." + EWatchdogStartupFailed.notificationName;
    }
}

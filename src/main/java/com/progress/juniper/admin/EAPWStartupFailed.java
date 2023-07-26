// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAPWStartupFailed extends EAuxiliaryStartupFailed
{
    public static String notificationName;
    public static String notificationType;
    
    public EAPWStartupFailed(final JAAuxiliary jaAuxiliary, final String s, final String s2) throws RemoteException {
        super(jaAuxiliary, s, s2);
    }
    
    static {
        EAPWStartupFailed.notificationName = "EAPWStartupFailed";
        EAPWStartupFailed.notificationType = "application.state." + EAPWStartupFailed.notificationName;
    }
}

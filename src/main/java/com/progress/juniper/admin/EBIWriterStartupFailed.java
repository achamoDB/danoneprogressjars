// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EBIWriterStartupFailed extends EAuxiliaryStartupFailed
{
    public static String notificationName;
    public static String notificationType;
    
    public EBIWriterStartupFailed(final JAAuxiliary jaAuxiliary, final String s, final String s2) throws RemoteException {
        super(jaAuxiliary, s, s2);
    }
    
    static {
        EBIWriterStartupFailed.notificationName = "EBIWriterStartupFailed";
        EBIWriterStartupFailed.notificationType = "application.state." + EBIWriterStartupFailed.notificationName;
    }
}

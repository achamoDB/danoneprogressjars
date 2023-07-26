// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAIWriterStartupFailed extends EAuxiliaryStartupFailed
{
    public static String notificationName;
    public static String notificationType;
    
    public EAIWriterStartupFailed(final JAAuxiliary jaAuxiliary, final String s, final String s2) throws RemoteException {
        super(jaAuxiliary, s, s2);
    }
    
    static {
        EAIWriterStartupFailed.notificationName = "EAIWriterStartupFailed";
        EAIWriterStartupFailed.notificationType = "application.state." + EAIWriterStartupFailed.notificationName;
    }
}

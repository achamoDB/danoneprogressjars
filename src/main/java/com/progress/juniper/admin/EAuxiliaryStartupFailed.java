// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAuxiliaryStartupFailed extends EAuxiliaryStartupEvent implements IEFailure
{
    public static String notificationName;
    public static String notificationType;
    String reasonForFailure;
    
    public String reasonForFailure() {
        return this.reasonForFailure;
    }
    
    public EAuxiliaryStartupFailed(final JAAuxiliary jaAuxiliary, final String s, final String s2) throws RemoteException {
        super(jaAuxiliary.remoteStub(), jaAuxiliary, s);
        this.reasonForFailure = null;
        super.setErrorString(s2);
        this.reasonForFailure = s2;
    }
    
    static {
        EAuxiliaryStartupFailed.notificationName = "EAuxiliaryStartupFailed";
        EAuxiliaryStartupFailed.notificationType = "application.state." + EAuxiliaryStartupFailed.notificationName;
    }
}

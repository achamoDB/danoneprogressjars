// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EServerGroupStartupFailed extends EServerGroupStartupEvent implements IEFailure
{
    public static String notificationName;
    public static String notificationType;
    String reasonForFailure;
    
    public String reasonForFailure() {
        return this.reasonForFailure;
    }
    
    public EServerGroupStartupFailed(final JAService jaService, final String s) throws RemoteException {
        super(jaService);
        this.reasonForFailure = null;
        super.setErrorString(s);
        this.reasonForFailure = s;
    }
    
    static {
        EServerGroupStartupFailed.notificationName = "EServerGroupStartupFailed";
        EServerGroupStartupFailed.notificationType = "application.state." + EServerGroupStartupFailed.notificationName;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.international.resources.ProgressResources;

public class EStartupProcessTimedOut extends EDatabaseStartupEvent implements IEStartupComplete, IEFailure
{
    public static String notificationName;
    public static String notificationType;
    
    public String reasonForFailure() {
        return ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "Timed Out");
    }
    
    public EStartupProcessTimedOut(final JADatabase jaDatabase) throws RemoteException {
        super(jaDatabase);
        super.setErrorString(this.reasonForFailure());
    }
    
    static {
        EStartupProcessTimedOut.notificationName = "EStartupProcessTimedOut";
        EStartupProcessTimedOut.notificationType = "application.state." + EStartupProcessTimedOut.notificationName;
    }
}

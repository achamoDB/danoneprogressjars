// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.international.resources.ProgressResources;

public class EServerGroupStartupTimedOut extends EServerGroupStartupFailed implements IEFailure
{
    public static String notificationName;
    public static String notificationType;
    
    public EServerGroupStartupTimedOut(final JAService jaService, final String s) throws RemoteException {
        super(jaService, ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "Timed Out"));
    }
    
    static {
        EServerGroupStartupTimedOut.notificationName = "EServerGroupStartupTimedOut";
        EServerGroupStartupTimedOut.notificationType = "application.state." + EServerGroupStartupTimedOut.notificationName;
    }
}

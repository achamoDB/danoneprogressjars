// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.rmi.RemoteException;

public class ESMDStartupCompleted extends ESMDEvent implements ISMDEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public ESMDStartupCompleted(final ISMDatabase ismDatabase) throws RemoteException {
        super(ismDatabase);
    }
    
    static {
        ESMDStartupCompleted.notificationName = "ESMDStartupCompleted";
        ESMDStartupCompleted.notificationType = "application.state." + ESMDStartupCompleted.notificationName;
    }
}

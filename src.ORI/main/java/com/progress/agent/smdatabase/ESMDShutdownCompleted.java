// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.rmi.RemoteException;

public class ESMDShutdownCompleted extends ESMDEvent implements ISMDEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public ESMDShutdownCompleted(final ISMDatabase ismDatabase) throws RemoteException {
        super(ismDatabase);
    }
    
    static {
        ESMDShutdownCompleted.notificationName = "ESMDShutdownCompleted";
        ESMDShutdownCompleted.notificationType = "application.state." + ESMDShutdownCompleted.notificationName;
    }
}

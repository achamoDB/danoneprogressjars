// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.rmi.RemoteException;

public class ESMDCrash extends ESMDEvent implements ISMDEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public ESMDCrash(final ISMDatabase ismDatabase) throws RemoteException {
        super(ismDatabase);
    }
    
    static {
        ESMDCrash.notificationName = "ESMDCrash";
        ESMDCrash.notificationType = "application.state." + ESMDCrash.notificationName;
    }
}

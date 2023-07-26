// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.rmi.RemoteException;
import com.progress.juniper.admin.EJAEvent;

public class ESMDEvent extends EJAEvent implements ISMDEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public ESMDEvent(final ISMDatabase ismDatabase) throws RemoteException {
        super(ismDatabase, ismDatabase);
        this.setSource(SMPlugIn.getCanonicalName());
    }
    
    static {
        ESMDEvent.notificationName = "ESMDEvent";
        ESMDEvent.notificationType = "application.state." + ESMDEvent.notificationName;
    }
}

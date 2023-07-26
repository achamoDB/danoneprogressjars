// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.rmi.RemoteException;
import com.progress.juniper.admin.JAStatusObject;

public class EDBAStateChanged extends EDBAEvent implements IEDBAStateChanged
{
    public static String notificationName;
    public static String notificationType;
    JAStatusObject statusObject;
    
    EDBAStateChanged(final IAgentDatabase agentDatabase, final JAStatusObject statusObject) throws RemoteException {
        super(agentDatabase, agentDatabase);
        this.statusObject = null;
        super.addEventData(statusObject);
        this.statusObject = statusObject;
    }
    
    public JAStatusObject getStatus() {
        return this.statusObject;
    }
    
    static {
        EDBAStateChanged.notificationName = "EDBAStateChanged";
        EDBAStateChanged.notificationType = "application.state." + EDBAStateChanged.notificationName;
    }
}

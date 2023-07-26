// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class EDBAStartupCompleted extends EDBAEvent implements IEventObject
{
    public static String notificationName;
    public static String notificationType;
    
    public EDBAStartupCompleted(final IAgentDatabase agentDatabase) throws RemoteException {
        super(agentDatabase, agentDatabase);
        if (agentDatabase != null) {
            super.addEventData(agentDatabase.getStatus());
        }
    }
    
    static {
        EDBAStartupCompleted.notificationName = "EDBAStartupCompleted";
        EDBAStartupCompleted.notificationType = "application.state." + EDBAStartupCompleted.notificationName;
    }
}

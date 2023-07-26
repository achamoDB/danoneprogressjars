// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.rmi.RemoteException;
import com.progress.juniper.admin.EJAEvent;

public class EDBAEvent extends EJAEvent implements IEDBAEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EDBAEvent(final Object o, final IAgentDatabase agentDatabase) throws RemoteException {
        super(o, agentDatabase);
    }
    
    public IAgentDatabase agent() {
        return (IAgentDatabase)this.getObject();
    }
    
    static {
        EDBAEvent.notificationName = "EDBAEvent";
        EDBAEvent.notificationType = "application.state." + EDBAEvent.notificationName;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ETrimAgentsEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ETrimAgentsEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ETrimAgentsEvent";
    }
    
    static {
        ETrimAgentsEvent.notificationType = "application.state.ETrimAgentsEvent";
    }
}

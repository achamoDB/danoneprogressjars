// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EAddAgentsEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public EAddAgentsEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "EAddAgentsEvent";
    }
    
    static {
        EAddAgentsEvent.notificationType = "application.state.EAddAgentsEvent";
    }
}

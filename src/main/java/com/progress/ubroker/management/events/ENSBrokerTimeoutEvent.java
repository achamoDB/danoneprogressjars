// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ENSBrokerTimeoutEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ENSBrokerTimeoutEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ENSBrokerTimeoutEvent";
    }
    
    static {
        ENSBrokerTimeoutEvent.notificationType = "application.state.ENSBrokerTimeoutEvent";
    }
}

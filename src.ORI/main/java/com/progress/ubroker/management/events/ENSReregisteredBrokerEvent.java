// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ENSReregisteredBrokerEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ENSReregisteredBrokerEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ENSReregisteredBrokerEvent";
    }
    
    static {
        ENSReregisteredBrokerEvent.notificationType = "application.state.ENSReregisteredBrokerEvent";
    }
}

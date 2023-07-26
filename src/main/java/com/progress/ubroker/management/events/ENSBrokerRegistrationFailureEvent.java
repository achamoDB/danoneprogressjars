// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ENSBrokerRegistrationFailureEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ENSBrokerRegistrationFailureEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ENSBrokerRegistrationFailureEvent";
    }
    
    static {
        ENSBrokerRegistrationFailureEvent.notificationType = "application.state.ENSBrokerRegistrationFailureEvent";
    }
}

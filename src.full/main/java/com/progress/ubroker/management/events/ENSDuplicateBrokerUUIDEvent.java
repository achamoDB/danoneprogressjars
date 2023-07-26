// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ENSDuplicateBrokerUUIDEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ENSDuplicateBrokerUUIDEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ENSDuplicateBrokerUUIDEvent";
    }
    
    static {
        ENSDuplicateBrokerUUIDEvent.notificationType = "application.state.ENSDuplicateBrokerUUIDEvent";
    }
}

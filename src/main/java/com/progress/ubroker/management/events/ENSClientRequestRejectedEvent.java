// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ENSClientRequestRejectedEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ENSClientRequestRejectedEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ENSClientRequestRejectedEvent";
    }
    
    static {
        ENSClientRequestRejectedEvent.notificationType = "application.state.ENSClientRequestRejectedEvent";
    }
}

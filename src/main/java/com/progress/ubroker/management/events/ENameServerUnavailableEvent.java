// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ENameServerUnavailableEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ENameServerUnavailableEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public ENameServerUnavailableEvent(final Object o, final String s, final String s2) throws RemoteException {
        super(o, new COpenEdgeManagementContent(s, new Object[] { s2 }));
    }
    
    public String getNotificationName() throws RemoteException {
        return "ENameServerUnavailableEvent";
    }
    
    static {
        ENameServerUnavailableEvent.notificationType = "application.state.ENameServerUnavailableEvent";
    }
}

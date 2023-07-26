// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EUbrokerProcessActiveEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public EUbrokerProcessActiveEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "EUbrokerProcessActiveEvent";
    }
    
    static {
        EUbrokerProcessActiveEvent.notificationType = "application.state.EUbrokerProcessActiveEvent";
    }
}

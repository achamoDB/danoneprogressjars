// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class ENSAppServiceNotFoundEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public ENSAppServiceNotFoundEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ENSAppServiceNotFoundEvent";
    }
    
    static {
        ENSAppServiceNotFoundEvent.notificationType = "application.state.ENSAppServiceNotFoundEvent";
    }
}

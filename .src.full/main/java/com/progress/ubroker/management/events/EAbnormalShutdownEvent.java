// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EAbnormalShutdownEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public EAbnormalShutdownEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public String getNotificationName() throws RemoteException {
        return "EAbnormalShutdownEvent";
    }
    
    static {
        EAbnormalShutdownEvent.notificationType = "application.state.EAbnormalShutdownEvent";
    }
}

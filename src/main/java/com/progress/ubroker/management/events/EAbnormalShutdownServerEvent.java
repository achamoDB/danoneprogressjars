// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EAbnormalShutdownServerEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public EAbnormalShutdownServerEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
    
    public EAbnormalShutdownServerEvent(final Object o, final String s, final String s2, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, s, s2, cOpenEdgeManagementContent);
    }
    
    public EAbnormalShutdownServerEvent(final Object o, final String s, final Long n) throws RemoteException {
        super(o, new COpenEdgeManagementContent(s, new Object[] { n }));
    }
    
    public String getNotificationName() throws RemoteException {
        return "EAbnormalShutdownServerEvent";
    }
    
    static {
        EAbnormalShutdownServerEvent.notificationType = "application.state.EAbnormalShutdownServerEvent";
    }
}

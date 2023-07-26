// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EUbrokerClientAbnormalDisconnectEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public EUbrokerClientAbnormalDisconnectEvent(final Object o, final String s) throws RemoteException {
        super(o);
        super.m_content = new COpenEdgeManagementContent(o.toString(), new Object[] { s });
    }
    
    public EUbrokerClientAbnormalDisconnectEvent(final Object o, final String s, final String source) throws RemoteException {
        super(o);
        super.m_content = new COpenEdgeManagementContent(o.toString(), new Object[] { s });
        super.m_source = source;
    }
    
    public String getNotificationName() throws RemoteException {
        return "EUbrokerClientAbnormalDisconnectEvent";
    }
    
    static {
        EUbrokerClientAbnormalDisconnectEvent.notificationType = "application.state.EUbrokerClientAbnormalDisconnectEvent";
    }
}

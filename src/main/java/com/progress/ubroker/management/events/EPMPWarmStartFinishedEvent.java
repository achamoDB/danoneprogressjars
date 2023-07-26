// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EPMPWarmStartFinishedEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public EPMPWarmStartFinishedEvent(final Object o, final String source) throws RemoteException {
        super(o);
        super.m_source = source;
    }
    
    public String getNotificationName() throws RemoteException {
        return "EPMPWarmStartFinishedEvent";
    }
    
    static {
        EPMPWarmStartFinishedEvent.notificationType = "application.state.EPMPWarmStartFinishedEvent";
    }
}

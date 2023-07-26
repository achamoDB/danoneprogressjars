// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EPMPWarmStartBeganEvent extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    
    public EPMPWarmStartBeganEvent(final Object o, final String source) throws RemoteException {
        super(o);
        super.m_source = source;
    }
    
    public String getNotificationName() throws RemoteException {
        return "EPMPWarmStartBeganEvent";
    }
    
    static {
        EPMPWarmStartBeganEvent.notificationType = "application.state.EPMPWarmStartBeganEvent";
    }
}

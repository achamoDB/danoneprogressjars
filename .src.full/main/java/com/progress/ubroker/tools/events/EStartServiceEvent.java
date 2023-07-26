// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.events;

import java.rmi.RemoteException;

public class EStartServiceEvent extends YodaPluginEventSuper implements IYodaPluginEventSuper
{
    public static String notificationType;
    
    public EStartServiceEvent(final Object o, final String s, final String s2) throws RemoteException {
        super(o, s, s2);
    }
    
    public EStartServiceEvent(final Object o, final String s, final String s2, final Object o2) throws RemoteException {
        super(o, s, s2, o2);
    }
    
    public String getNotificationName() throws RemoteException {
        return "EStartServiceEvent";
    }
    
    static {
        EStartServiceEvent.notificationType = "application.state.EStartServiceEvent";
    }
}

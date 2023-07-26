// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.events;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventStream;
import com.progress.chimera.common.IChimeraHierarchy;

public class ETrimSrvrEvent extends YodaPluginEventSuper implements IYodaPluginEventSuper
{
    public static String notificationType;
    
    public ETrimSrvrEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final String s, final String s2, final IEventStream eventStream) throws RemoteException {
        super(o, chimeraHierarchy, s, s2, eventStream);
    }
    
    public ETrimSrvrEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final String s, final String s2, final IEventStream eventStream, final Object o2) throws RemoteException {
        super(o, chimeraHierarchy, s, s2, eventStream, o2);
    }
    
    public String getNotificationName() throws RemoteException {
        return "ETrimSrvrEvent";
    }
    
    static {
        ETrimSrvrEvent.notificationType = "application.state.ETrimSrvrEvent";
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.events;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventStream;
import com.progress.chimera.common.IChimeraHierarchy;

public class EAddSrvrEvent extends YodaPluginEventSuper implements IYodaPluginEventSuper
{
    public static String notificationType;
    
    public EAddSrvrEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final String s, final String s2, final IEventStream eventStream) throws RemoteException {
        super(o, chimeraHierarchy, s, s2, eventStream);
    }
    
    public EAddSrvrEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final String s, final String s2, final IEventStream eventStream, final Object o2) throws RemoteException {
        super(o, chimeraHierarchy, s, s2, eventStream, o2);
    }
    
    public String getNotificationName() throws RemoteException {
        return "EAddSrvrEvent";
    }
    
    static {
        EAddSrvrEvent.notificationType = "application.state.EAddSrvrEvent";
    }
}

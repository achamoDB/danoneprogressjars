// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.container.events;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.util.IEDeleteEvent;

public class ContainerDeleteTreeNodeEvent extends ChimeraContainerEventSuper implements IEDeleteEvent
{
    public static String notificationType;
    
    public ContainerDeleteTreeNodeEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final Object o2) throws RemoteException {
        super(o, null, chimeraHierarchy, o2);
    }
    
    public ContainerDeleteTreeNodeEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final Object o2, final Object eventContent) throws RemoteException {
        super(o, null, chimeraHierarchy, o2);
        super.m_eventContent = eventContent;
    }
    
    public String getNotificationName() throws RemoteException {
        return "ContainerDeleteTreeNodeEvent";
    }
    
    static {
        ContainerDeleteTreeNodeEvent.notificationType = "application.state.ContainerDeleteTreeNodeEvent";
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.container.events;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.util.IEAddEvent;

public class ContainerAddTreeNodeEvent extends ChimeraContainerEventSuper implements IEAddEvent
{
    public static String notificationType;
    
    public ContainerAddTreeNodeEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, final Object o2) throws RemoteException {
        super(o, chimeraHierarchy, chimeraHierarchy2, o2);
    }
    
    public ContainerAddTreeNodeEvent(final Object o, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, final Object o2, final Object eventContent) throws RemoteException {
        super(o, chimeraHierarchy, chimeraHierarchy2, o2);
        super.m_eventContent = eventContent;
    }
    
    public Object getChild() throws RemoteException {
        return super.nodeObj;
    }
    
    public String getNotificationName() throws RemoteException {
        return "ContainerAddTreeNodeEvent";
    }
    
    static {
        ContainerAddTreeNodeEvent.notificationType = "application.state.ContainerAddTreeNodeEvent";
    }
}

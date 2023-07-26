// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.container.events;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.networkevents.IEventObject;

public interface IChimeraContainerEventSuper extends IEventObject
{
    IChimeraHierarchy getNodeObj() throws RemoteException;
    
    IChimeraHierarchy getParentNode() throws RemoteException;
}

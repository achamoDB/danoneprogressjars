// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.events;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.networkevents.IEventObject;

public interface IYodaPluginEventSuper extends IEventObject
{
    public static final int EVT_START_SVC = 1;
    public static final int EVT_STOP_SVC = 2;
    
    IChimeraHierarchy getPluginObj() throws RemoteException;
    
    String getPropGrpFullSpec() throws RemoteException;
    
    String getInstanceName() throws RemoteException;
}

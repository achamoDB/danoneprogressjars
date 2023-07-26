// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public interface IEServerGroupEvent extends IEventObject
{
    IJAService serverGroup() throws RemoteException;
}

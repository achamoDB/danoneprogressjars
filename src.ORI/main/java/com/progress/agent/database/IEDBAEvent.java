// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public interface IEDBAEvent extends IEventObject
{
    IAgentDatabase agent() throws RemoteException;
}

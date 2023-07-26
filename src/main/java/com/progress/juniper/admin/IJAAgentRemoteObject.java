// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraRemoteObject;

public interface IJAAgentRemoteObject extends IChimeraRemoteObject
{
    IAgentDatabaseHandle getAgent() throws RemoteException;
}

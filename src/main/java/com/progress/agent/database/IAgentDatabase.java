// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.rmi.RemoteException;
import com.progress.chimera.common.Database;
import com.progress.juniper.admin.IAgentDatabaseHandle;

public interface IAgentDatabase extends IAgentDatabaseHandle
{
    Database getDatabase() throws RemoteException;
    
    boolean isRemoteDb() throws RemoteException;
}

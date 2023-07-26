// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraRemoteObject;

public interface IJAReplRemoteObject extends IChimeraRemoteObject
{
    IReplDatabaseHandle getReplDatabaseHandle() throws RemoteException;
}

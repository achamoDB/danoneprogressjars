// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import java.rmi.RemoteException;
import com.progress.ubroker.tools.IAdminRemote;

public interface IRemoteManager extends IAdminRemote
{
    public static final int STATUS_PROCESS_STATE = 3;
    
    int invokeCommand(final int p0, final Object[] p1) throws RemoteException;
    
    Object getData(final String[] p0) throws RemoteException;
}

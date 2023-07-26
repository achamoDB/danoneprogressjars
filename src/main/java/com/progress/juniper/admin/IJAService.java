// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.chimera.common.IChimeraHierarchy;

public interface IJAService extends IChimeraHierarchy, IJAHierarchy, IJARemoteObject, IJAExecutableObject
{
    String getServiceNameOrPort() throws RemoteException;
}

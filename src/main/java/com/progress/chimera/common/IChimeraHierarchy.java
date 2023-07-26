// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import java.rmi.RemoteException;
import java.util.Enumeration;

public interface IChimeraHierarchy extends IChimeraRemoteObject
{
    Enumeration getChildren() throws RemoteException;
    
    String getDisplayName() throws RemoteException;
    
    String getMMCClientClass() throws RemoteException;
}

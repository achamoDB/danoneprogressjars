// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Enumeration;
import java.rmi.RemoteException;

public interface IJAHierarchy extends IJARemoteObject
{
    IJAHierarchy getParent() throws RemoteException;
    
    Enumeration getChildrenObjects() throws RemoteException;
    
    IJAHierarchy createChild(final String p0, final Object p1, final Object p2) throws RemoteException;
    
    String makeNewChildName() throws RemoteException;
    
    boolean childNameUsed(final String p0) throws RemoteException;
    
    String getDisplayName(final boolean p0) throws RemoteException;
}

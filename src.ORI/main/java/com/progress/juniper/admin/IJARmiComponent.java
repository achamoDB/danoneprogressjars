// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.util.Hashtable;

public interface IJARmiComponent extends IJARemoteObject
{
    Hashtable getStatistics(final String p0, final String[] p1) throws RemoteException;
    
    Object[] getTableSchema(final String p0, final String p1) throws RemoteException;
}

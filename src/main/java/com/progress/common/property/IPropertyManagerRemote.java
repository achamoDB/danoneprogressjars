// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.util.Vector;
import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IPropertyManagerRemote extends Remote
{
    PropertyTransferObject makePropertyTransferObject(final String p0) throws RemoteException;
    
    int updateFromClient(final String p0, final IPropertyValueProvider p1, final RemoteStub p2, final String p3, final Vector p4) throws RemoteException, PropertyManager.SaveIOException;
    
    void restoreDefaults(final String p0, final IPropertyValueProvider p1, final RemoteStub p2, final String p3, final Vector p4) throws RemoteException;
    
    boolean groupExists(final String p0) throws RemoteException;
}

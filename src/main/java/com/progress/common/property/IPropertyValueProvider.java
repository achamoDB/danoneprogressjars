// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IPropertyValueProvider extends Remote
{
    Object getValueRemote(final String p0) throws RemoteException;
}

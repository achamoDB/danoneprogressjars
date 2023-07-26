// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IPropertyValueSetRemote extends Remote
{
    Object getValueRemote(final String p0) throws XPropertyHasNoValue, XPropertyDoesNotExist, RemoteException;
}

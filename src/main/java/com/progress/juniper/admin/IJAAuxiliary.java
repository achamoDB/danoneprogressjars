// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public interface IJAAuxiliary extends IJAHierarchy, IJAExecutableObject
{
    String getDisplayName() throws RemoteException;
}

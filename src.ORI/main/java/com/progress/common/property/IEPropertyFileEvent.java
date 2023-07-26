// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.rmi.RemoteException;
import com.progress.common.util.IEClientSpecificEvent;

public interface IEPropertyFileEvent extends IEClientSpecificEvent
{
    String getPropertyFileName() throws RemoteException;
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public interface IEAddEvent extends IEventObject, IEClientSpecificEvent
{
    Object getChild() throws RemoteException;
}

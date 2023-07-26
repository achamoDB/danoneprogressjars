// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public interface IEClientSpecificEvent extends IEventObject
{
    Object guiID() throws RemoteException;
}

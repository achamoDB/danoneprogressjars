// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public interface IEPropertyChangedEvent extends IEventObject
{
    Object guiID() throws RemoteException;
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import java.rmi.Remote;

public interface IENestedEvent extends Remote
{
    IEventObject getNestedEvent() throws RemoteException;
}

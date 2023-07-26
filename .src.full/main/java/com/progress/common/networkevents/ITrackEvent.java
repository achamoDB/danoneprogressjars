// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;

public interface ITrackEvent extends IEventObject
{
    public static final IEventInterestObject eieio = null;
    
    IEventInterestObject getEventInterestObject() throws RemoteException;
}

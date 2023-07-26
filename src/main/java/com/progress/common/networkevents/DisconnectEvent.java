// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;

public class DisconnectEvent extends TrackEvent
{
    public DisconnectEvent(final Object o, final IEventInterestObject eventInterestObject) throws RemoteException {
        super(o, eventInterestObject);
    }
}

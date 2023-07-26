// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;

public class TrackEvent extends EventObject implements ITrackEvent
{
    IEventInterestObject eieio;
    
    public TrackEvent(final Object o, final IEventInterestObject eieio) throws RemoteException {
        super(o);
        this.eieio = null;
        this.eieio = eieio;
    }
    
    public IEventInterestObject getEventInterestObject() throws RemoteException {
        return this.eieio;
    }
}

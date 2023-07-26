// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

public class EventListener implements IEventListener
{
    public EventListener() {
        try {
            UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {}
    }
    
    public void processEvent(final IEventObject eventObject) throws RemoteException {
    }
}

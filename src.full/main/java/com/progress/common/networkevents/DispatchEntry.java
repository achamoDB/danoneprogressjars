// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import com.progress.chimera.common.Tools;
import java.rmi.RemoteException;
import java.util.Enumeration;

class DispatchEntry extends EventInterestObject
{
    Class eventType;
    Object issuer;
    Class cIssuers;
    Enumeration eIssuers;
    EventBroker broker;
    IEventListener callback;
    
    DispatchEntry(final EventBroker broker, final Class eventType, final IEventListener callback, final IClient client) throws RemoteException {
        super(client);
        this.eventType = null;
        this.issuer = null;
        this.cIssuers = null;
        this.eIssuers = null;
        this.broker = null;
        this.callback = null;
        this.eventType = eventType;
        this.callback = callback;
        this.broker = broker;
    }
    
    DispatchEntry(final EventBroker broker, final Class eventType, final IEventListener callback, final Object issuer, final IClient client) throws RemoteException {
        super(client);
        this.eventType = null;
        this.issuer = null;
        this.cIssuers = null;
        this.eIssuers = null;
        this.broker = null;
        this.callback = null;
        this.eventType = eventType;
        this.callback = callback;
        this.issuer = issuer;
        this.broker = broker;
    }
    
    DispatchEntry(final EventBroker broker, final Class eventType, final IEventListener callback, final Class cIssuers, final IClient client) throws RemoteException {
        super(client);
        this.eventType = null;
        this.issuer = null;
        this.cIssuers = null;
        this.eIssuers = null;
        this.broker = null;
        this.callback = null;
        this.eventType = eventType;
        this.callback = callback;
        this.cIssuers = cIssuers;
        this.broker = broker;
    }
    
    DispatchEntry(final EventBroker broker, final Class eventType, final IEventListener callback, final Object issuer, final Class cIssuers, final Enumeration eIssuers, final IClient client) throws RemoteException {
        super(client);
        this.eventType = null;
        this.issuer = null;
        this.cIssuers = null;
        this.eIssuers = null;
        this.broker = null;
        this.callback = null;
        this.eventType = eventType;
        this.callback = callback;
        this.issuer = issuer;
        this.cIssuers = cIssuers;
        this.eIssuers = eIssuers;
        this.broker = broker;
    }
    
    public Class eventType() {
        return this.eventType;
    }
    
    public void revokeInterest() throws RemoteException {
        this.broker.printMess(4, "Interest revoked for: " + this.eventType);
        if (Tools.isaSubclass(this.eventType, DisconnectEvent.class)) {
            this.broker.monitors.eliminate(this);
        }
        else if (Tools.isaSubclass(this.eventType, LocateEvent.class)) {
            this.broker.locators.eliminate(this);
        }
        synchronized (this.broker.dispatchTable) {
            this.broker.dispatchTable.removeElement(this);
        }
    }
    
    public void revokeInterest(final IEventInterestObject eventInterestObject) throws RemoteException {
    }
}

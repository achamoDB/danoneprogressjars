// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import java.util.Date;
import java.rmi.server.UnicastRemoteObject;

public class EventObject extends UnicastRemoteObject implements IEventObject
{
    Object issuer;
    Date timeIssued;
    
    public EventObject(final Object issuer) throws RemoteException {
        this.issuer = null;
        this.timeIssued = new Date();
        this.issuer = issuer;
    }
    
    static Class eventBaseClass() {
        Class<?> forName = null;
        try {
            forName = Class.forName("com.progress.common.networkevents.EventObject");
        }
        catch (Throwable t) {}
        return forName;
    }
    
    public Object issuer() {
        return this.issuer;
    }
    
    public Date timeIssued() {
        return this.timeIssued;
    }
    
    public boolean expedite() {
        return false;
    }
    
    public final String eventTypeString() {
        return this.getClass().getName();
    }
    
    public final Class classDef() {
        return this.getClass();
    }
    
    public String description() throws RemoteException {
        return "Event of type " + this.eventTypeString() + " issued by " + this.issuerName() + " at " + this.timeIssued();
    }
    
    public String issuerName() throws RemoteException {
        try {
            return this.issuer().toString();
        }
        catch (Exception ex) {
            return "INACSESSIBLE";
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.util.Date;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class EventObject_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 6354199966435820571L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Class classDef()"), new Operation("java.lang.String description()"), new Operation("java.lang.String eventTypeString()"), new Operation("boolean expedite()"), new Operation("java.lang.Object issuer()"), new Operation("java.lang.String issuerName()"), new Operation("java.util.Date timeIssued()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 6354199966435820571L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final EventObject eventObject = (EventObject)remote;
        switch (n) {
            case 0: {
                remoteCall.releaseInputStream();
                final Class classDef = eventObject.classDef();
                try {
                    remoteCall.getResultStream(true).writeObject(classDef);
                    return;
                }
                catch (IOException ex) {
                    throw new MarshalException("error marshalling return", ex);
                }
            }
            case 1: {
                remoteCall.releaseInputStream();
                final String description = eventObject.description();
                try {
                    remoteCall.getResultStream(true).writeObject(description);
                    return;
                }
                catch (IOException ex2) {
                    throw new MarshalException("error marshalling return", ex2);
                }
            }
            case 2: {
                remoteCall.releaseInputStream();
                final String eventTypeString = eventObject.eventTypeString();
                try {
                    remoteCall.getResultStream(true).writeObject(eventTypeString);
                    return;
                }
                catch (IOException ex3) {
                    throw new MarshalException("error marshalling return", ex3);
                }
            }
            case 3: {
                remoteCall.releaseInputStream();
                final boolean expedite = eventObject.expedite();
                try {
                    remoteCall.getResultStream(true).writeBoolean(expedite);
                    return;
                }
                catch (IOException ex4) {
                    throw new MarshalException("error marshalling return", ex4);
                }
            }
            case 4: {
                remoteCall.releaseInputStream();
                final Object issuer = eventObject.issuer();
                try {
                    remoteCall.getResultStream(true).writeObject(issuer);
                    return;
                }
                catch (IOException ex5) {
                    throw new MarshalException("error marshalling return", ex5);
                }
            }
            case 5: {
                remoteCall.releaseInputStream();
                final String issuerName = eventObject.issuerName();
                try {
                    remoteCall.getResultStream(true).writeObject(issuerName);
                    return;
                }
                catch (IOException ex6) {
                    throw new MarshalException("error marshalling return", ex6);
                }
            }
            case 6: {
                remoteCall.releaseInputStream();
                final Date timeIssued = eventObject.timeIssued();
                try {
                    remoteCall.getResultStream(true).writeObject(timeIssued);
                    return;
                }
                catch (IOException ex7) {
                    throw new MarshalException("error marshalling return", ex7);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return EventObject_Skel.operations.clone();
    }
}

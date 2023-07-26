// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class EventInterestObject_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 7179863061515146745L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Class eventType()"), new Operation("com.progress.common.networkevents.IClient getClient()"), new Operation("void revokeInterest()"), new Operation("void revokeInterest(com.progress.common.networkevents.IEventInterestObject)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 7179863061515146745L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final EventInterestObject eventInterestObject = (EventInterestObject)remote;
        while (true) {
            switch (n) {
                case 0: {
                    remoteCall.releaseInputStream();
                    final Class eventType = eventInterestObject.eventType();
                    try {
                        remoteCall.getResultStream(true).writeObject(eventType);
                        return;
                    }
                    catch (IOException ex) {
                        throw new MarshalException("error marshalling return", ex);
                    }
                }
                case 1: {
                    remoteCall.releaseInputStream();
                    final IClient client = eventInterestObject.getClient();
                    try {
                        remoteCall.getResultStream(true).writeObject(client);
                        return;
                    }
                    catch (IOException ex2) {
                        throw new MarshalException("error marshalling return", ex2);
                    }
                }
                case 2: {
                    remoteCall.releaseInputStream();
                    eventInterestObject.revokeInterest();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex3) {
                        throw new MarshalException("error marshalling return", ex3);
                    }
                    IEventInterestObject eventInterestObject2;
                    try {
                        eventInterestObject2 = (IEventInterestObject)remoteCall.getInputStream().readObject();
                    }
                    catch (IOException ex4) {
                        throw new UnmarshalException("error unmarshalling arguments", ex4);
                    }
                    catch (ClassNotFoundException ex5) {
                        throw new UnmarshalException("error unmarshalling arguments", ex5);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    eventInterestObject.revokeInterest(eventInterestObject2);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex6) {
                        throw new MarshalException("error marshalling return", ex6);
                    }
                    break;
                }
                case 3: {
                    continue;
                }
            }
            break;
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return EventInterestObject_Skel.operations.clone();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.io.ObjectInput;
import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class Client_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 892188447052064438L;
    
    static {
        operations = new Operation[] { new Operation("void processEvent(com.progress.common.networkevents.IEventObject, com.progress.common.networkevents.IEventListener, java.lang.Object, com.progress.common.networkevents.IClient, boolean)"), new Operation("void terminate()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 892188447052064438L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final Client client = (Client)remote;
        switch (n) {
            case 0: {
                IEventObject eventObject;
                IEventListener eventListener;
                Object object;
                IClient client2;
                boolean boolean1;
                try {
                    final ObjectInput inputStream = remoteCall.getInputStream();
                    eventObject = (IEventObject)inputStream.readObject();
                    eventListener = (IEventListener)inputStream.readObject();
                    object = inputStream.readObject();
                    client2 = (IClient)inputStream.readObject();
                    boolean1 = inputStream.readBoolean();
                }
                catch (IOException ex) {
                    throw new UnmarshalException("error unmarshalling arguments", ex);
                }
                catch (ClassNotFoundException ex2) {
                    throw new UnmarshalException("error unmarshalling arguments", ex2);
                }
                finally {
                    remoteCall.releaseInputStream();
                }
                client.processEvent(eventObject, eventListener, object, client2, boolean1);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException ex3) {
                    throw new MarshalException("error marshalling return", ex3);
                }
            }
            case 1: {
                remoteCall.releaseInputStream();
                client.terminate();
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException ex4) {
                    throw new MarshalException("error marshalling return", ex4);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return Client_Skel.operations.clone();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import com.progress.common.networkevents.IEventObject;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class AdminServerShutdown_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 9150587618287247166L;
    
    static {
        operations = new Operation[] { new Operation("void processEvent(com.progress.common.networkevents.IEventObject)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 9150587618287247166L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final AdminServerShutdown adminServerShutdown = (AdminServerShutdown)remote;
        switch (n) {
            case 0: {
                IEventObject eventObject;
                try {
                    eventObject = (IEventObject)remoteCall.getInputStream().readObject();
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
                adminServerShutdown.processEvent(eventObject);
                try {
                    remoteCall.getResultStream(true);
                    return;
                }
                catch (IOException ex3) {
                    throw new MarshalException("error marshalling return", ex3);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return AdminServerShutdown_Skel.operations.clone();
    }
}

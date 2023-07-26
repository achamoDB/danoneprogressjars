// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class RemoteInteger_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -5571926337095455531L;
    
    static {
        operations = new Operation[] { new Operation("int get()"), new Operation("void put(int)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -5571926337095455531L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final RemoteInteger remoteInteger = (RemoteInteger)remote;
        while (true) {
            switch (n) {
                case 0: {
                    remoteCall.releaseInputStream();
                    final int value = remoteInteger.get();
                    try {
                        remoteCall.getResultStream(true).writeInt(value);
                        return;
                    }
                    catch (IOException ex) {
                        throw new MarshalException("error marshalling return", ex);
                    }
                    int int1;
                    try {
                        int1 = remoteCall.getInputStream().readInt();
                    }
                    catch (IOException ex2) {
                        throw new UnmarshalException("error unmarshalling arguments", ex2);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    remoteInteger.put(int1);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex3) {
                        throw new MarshalException("error marshalling return", ex3);
                    }
                    break;
                }
                case 1: {
                    continue;
                }
            }
            break;
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return RemoteInteger_Skel.operations.clone();
    }
}

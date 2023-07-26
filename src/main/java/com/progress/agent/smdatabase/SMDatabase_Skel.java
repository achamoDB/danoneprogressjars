// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.util.Enumeration;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class SMDatabase_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -3634889999576623773L;
    
    static {
        operations = new Operation[] { new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.lang.String getMMCClientClass()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -3634889999576623773L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final SMDatabase smDatabase = (SMDatabase)remote;
        switch (n) {
            case 0: {
                remoteCall.releaseInputStream();
                final Enumeration children = smDatabase.getChildren();
                try {
                    remoteCall.getResultStream(true).writeObject(children);
                    return;
                }
                catch (IOException ex) {
                    throw new MarshalException("error marshalling return", ex);
                }
            }
            case 1: {
                remoteCall.releaseInputStream();
                final String displayName = smDatabase.getDisplayName();
                try {
                    remoteCall.getResultStream(true).writeObject(displayName);
                    return;
                }
                catch (IOException ex2) {
                    throw new MarshalException("error marshalling return", ex2);
                }
            }
            case 2: {
                remoteCall.releaseInputStream();
                final String mmcClientClass = smDatabase.getMMCClientClass();
                try {
                    remoteCall.getResultStream(true).writeObject(mmcClientClass);
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
        return SMDatabase_Skel.operations.clone();
    }
}

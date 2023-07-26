// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.util.Vector;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class AdminContext_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -2504202288551725341L;
    
    static {
        operations = new Operation[] { new Operation("java.util.Vector getPlugins()"), new Operation("java.util.Vector getPlugins(java.lang.String)"), new Operation("void ping()"), new Operation("void shutdown()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -2504202288551725341L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final AdminContext adminContext = (AdminContext)remote;
        Label_0104: {
            switch (n) {
                case 1: {
                    break Label_0104;
                }
                case 2: {
                    remoteCall.releaseInputStream();
                    adminContext.ping();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex6) {
                        throw new MarshalException("error marshalling return", ex6);
                    }
                }
                case 3: {
                    remoteCall.releaseInputStream();
                    adminContext.shutdown();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex5) {
                        throw new MarshalException("error marshalling return", ex5);
                    }
                    break;
                }
                case 0: {
                    remoteCall.releaseInputStream();
                    final Vector plugins = adminContext.getPlugins();
                    try {
                        remoteCall.getResultStream(true).writeObject(plugins);
                        return;
                    }
                    catch (IOException ex) {
                        throw new MarshalException("error marshalling return", ex);
                    }
                    String s;
                    try {
                        s = (String)remoteCall.getInputStream().readObject();
                    }
                    catch (IOException ex2) {
                        throw new UnmarshalException("error unmarshalling arguments", ex2);
                    }
                    catch (ClassNotFoundException ex3) {
                        throw new UnmarshalException("error unmarshalling arguments", ex3);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    final Vector plugins2 = adminContext.getPlugins(s);
                    try {
                        remoteCall.getResultStream(true).writeObject(plugins2);
                        return;
                    }
                    catch (IOException ex4) {
                        throw new MarshalException("error marshalling return", ex4);
                    }
                }
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return AdminContext_Skel.operations.clone();
    }
}

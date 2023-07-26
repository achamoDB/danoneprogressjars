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

public final class MProservJuniper_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7419295899085538247L;
    
    static {
        operations = new Operation[] { new Operation("void disconnectAdminServer()"), new Operation("void ping()"), new Operation("void setAdminServer(com.progress.juniper.admin.IAdminJuniper)"), new Operation("void shutDown()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -7419295899085538247L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final MProservJuniper mProservJuniper = (MProservJuniper)remote;
        Label_0128: {
            switch (n) {
                case 2: {
                    break Label_0128;
                }
                case 1: {
                    remoteCall.releaseInputStream();
                    mProservJuniper.ping();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex3) {
                        throw new MarshalException("error marshalling return", ex3);
                    }
                    IAdminJuniper adminServer;
                    try {
                        adminServer = (IAdminJuniper)remoteCall.getInputStream().readObject();
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
                    mProservJuniper.setAdminServer(adminServer);
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
                    mProservJuniper.shutDown();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex2) {
                        throw new MarshalException("error marshalling return", ex2);
                    }
                    break;
                }
                case 0: {
                    remoteCall.releaseInputStream();
                    mProservJuniper.disconnectAdminServer();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex) {
                        throw new MarshalException("error marshalling return", ex);
                    }
                }
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return MProservJuniper_Skel.operations.clone();
    }
}

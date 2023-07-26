// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.server.RemoteStub;
import java.util.Enumeration;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class JAAbstractCategory_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -6247516139969673665L;
    
    static {
        operations = new Operation[] { new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.lang.String getMMCClientClass()"), new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("java.rmi.server.RemoteStub remoteStub()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -6247516139969673665L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final JAAbstractCategory jaAbstractCategory = (JAAbstractCategory)remote;
        switch (n) {
            case 0: {
                remoteCall.releaseInputStream();
                final Enumeration children = jaAbstractCategory.getChildren();
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
                final String displayName = jaAbstractCategory.getDisplayName();
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
                final String mmcClientClass = jaAbstractCategory.getMMCClientClass();
                try {
                    remoteCall.getResultStream(true).writeObject(mmcClientClass);
                    return;
                }
                catch (IOException ex3) {
                    throw new MarshalException("error marshalling return", ex3);
                }
            }
            case 3: {
                remoteCall.releaseInputStream();
                final IJAPlugIn plugIn = jaAbstractCategory.getPlugIn();
                try {
                    remoteCall.getResultStream(true).writeObject(plugIn);
                    return;
                }
                catch (IOException ex4) {
                    throw new MarshalException("error marshalling return", ex4);
                }
            }
            case 4: {
                remoteCall.releaseInputStream();
                final RemoteStub remoteStub = jaAbstractCategory.remoteStub();
                try {
                    remoteCall.getResultStream(true).writeObject(remoteStub);
                    return;
                }
                catch (IOException ex5) {
                    throw new MarshalException("error marshalling return", ex5);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return JAAbstractCategory_Skel.operations.clone();
    }
}

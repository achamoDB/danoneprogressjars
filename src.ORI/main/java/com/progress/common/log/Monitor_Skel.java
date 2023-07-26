// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class Monitor_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 3103311997983563335L;
    
    static {
        operations = new Operation[0];
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 3103311997983563335L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final Monitor monitor = (Monitor)remote;
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return Monitor_Skel.operations.clone();
    }
}

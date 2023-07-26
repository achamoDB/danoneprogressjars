// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class PropertyValueSet_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -8121598313125218002L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Object getValueRemote(java.lang.String)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -8121598313125218002L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final PropertyValueSet set = (PropertyValueSet)remote;
        switch (n) {
            case 0: {
                String s;
                try {
                    s = (String)remoteCall.getInputStream().readObject();
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
                final Object valueRemote = set.getValueRemote(s);
                try {
                    remoteCall.getResultStream(true).writeObject(valueRemote);
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
        return PropertyValueSet_Skel.operations.clone();
    }
}

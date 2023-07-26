// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.server.RemoteStub;
import java.util.Hashtable;
import java.io.ObjectInput;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class JARmiComponent_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -3997381754267381036L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("java.util.Hashtable getStatistics(java.lang.String, java.lang.String[])"), new Operation("java.lang.Object getTableSchema(java.lang.String, java.lang.String)[]"), new Operation("java.rmi.server.RemoteStub remoteStub()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -3997381754267381036L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final JARmiComponent jaRmiComponent = (JARmiComponent)remote;
        while (true) {
            switch (n) {
                case 1: {
                    continue;
                }
                case 0: {
                    remoteCall.releaseInputStream();
                    final IJAPlugIn plugIn = jaRmiComponent.getPlugIn();
                    try {
                        remoteCall.getResultStream(true).writeObject(plugIn);
                        return;
                    }
                    catch (IOException ex) {
                        throw new MarshalException("error marshalling return", ex);
                    }
                    try {
                        final ObjectInput inputStream = remoteCall.getInputStream();
                        final String s = (String)inputStream.readObject();
                        final String[] array = (String[])inputStream.readObject();
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
                }
                case 2: {
                    String s2 = null;
                    String s3 = null;
                    Label_0251: {
                        break Label_0251;
                        final String s;
                        final String[] array;
                        final Hashtable statistics = jaRmiComponent.getStatistics(s, array);
                        try {
                            remoteCall.getResultStream(true).writeObject(statistics);
                            return;
                        }
                        catch (IOException ex4) {
                            throw new MarshalException("error marshalling return", ex4);
                        }
                        try {
                            final ObjectInput inputStream2 = remoteCall.getInputStream();
                            s2 = (String)inputStream2.readObject();
                            s3 = (String)inputStream2.readObject();
                        }
                        catch (IOException ex5) {
                            throw new UnmarshalException("error unmarshalling arguments", ex5);
                        }
                        catch (ClassNotFoundException ex6) {
                            throw new UnmarshalException("error unmarshalling arguments", ex6);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                    }
                    final Object[] tableSchema = jaRmiComponent.getTableSchema(s2, s3);
                    try {
                        remoteCall.getResultStream(true).writeObject(tableSchema);
                        return;
                    }
                    catch (IOException ex7) {
                        throw new MarshalException("error marshalling return", ex7);
                    }
                }
                case 3: {
                    remoteCall.releaseInputStream();
                    final RemoteStub remoteStub = jaRmiComponent.remoteStub();
                    try {
                        remoteCall.getResultStream(true).writeObject(remoteStub);
                        return;
                    }
                    catch (IOException ex8) {
                        throw new MarshalException("error marshalling return", ex8);
                    }
                    break;
                }
            }
            break;
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return JARmiComponent_Skel.operations.clone();
    }
}

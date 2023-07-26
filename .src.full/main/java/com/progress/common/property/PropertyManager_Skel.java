// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.io.ObjectInput;
import java.util.Vector;
import java.rmi.server.RemoteStub;
import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class PropertyManager_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7278256915521254475L;
    
    static {
        operations = new Operation[] { new Operation("boolean groupExists(java.lang.String)"), new Operation("com.progress.common.property.PropertyTransferObject makePropertyTransferObject(java.lang.String)"), new Operation("void restoreDefaults(java.lang.String, com.progress.common.property.IPropertyValueProvider, java.rmi.server.RemoteStub, java.lang.String, java.util.Vector)"), new Operation("int updateFromClient(java.lang.String, com.progress.common.property.IPropertyValueProvider, java.rmi.server.RemoteStub, java.lang.String, java.util.Vector)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -7278256915521254475L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final PropertyManager propertyManager = (PropertyManager)remote;
        switch (n) {
            case 0: {
                try {
                    final String s = (String)remoteCall.getInputStream().readObject();
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
            }
            case 1: {
                Label_0189: {
                    break Label_0189;
                    final String s;
                    final boolean groupExists = propertyManager.groupExists(s);
                    try {
                        remoteCall.getResultStream(true).writeBoolean(groupExists);
                        return;
                    }
                    catch (IOException ex3) {
                        throw new MarshalException("error marshalling return", ex3);
                    }
                    try {
                        final String s2 = (String)remoteCall.getInputStream().readObject();
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
                }
            }
            case 2: {
                Label_0322: {
                    break Label_0322;
                    final String s2;
                    final PropertyTransferObject propertyTransferObject = propertyManager.makePropertyTransferObject(s2);
                    try {
                        remoteCall.getResultStream(true).writeObject(propertyTransferObject);
                        return;
                    }
                    catch (IOException ex6) {
                        throw new MarshalException("error marshalling return", ex6);
                    }
                    try {
                        final ObjectInput inputStream = remoteCall.getInputStream();
                        final String s3 = (String)inputStream.readObject();
                        final IPropertyValueProvider propertyValueProvider = (IPropertyValueProvider)inputStream.readObject();
                        final RemoteStub remoteStub = (RemoteStub)inputStream.readObject();
                        final String s4 = (String)inputStream.readObject();
                        final Vector vector = (Vector)inputStream.readObject();
                    }
                    catch (IOException ex7) {
                        throw new UnmarshalException("error unmarshalling arguments", ex7);
                    }
                    catch (ClassNotFoundException ex8) {
                        throw new UnmarshalException("error unmarshalling arguments", ex8);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                }
            }
            case 3: {
                String s5 = null;
                IPropertyValueProvider propertyValueProvider2 = null;
                RemoteStub remoteStub2 = null;
                String s6 = null;
                Vector vector2 = null;
                Label_0499: {
                    break Label_0499;
                    final String s3;
                    final IPropertyValueProvider propertyValueProvider;
                    final RemoteStub remoteStub;
                    final String s4;
                    final Vector vector;
                    propertyManager.restoreDefaults(s3, propertyValueProvider, remoteStub, s4, vector);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex9) {
                        throw new MarshalException("error marshalling return", ex9);
                    }
                    try {
                        final ObjectInput inputStream2 = remoteCall.getInputStream();
                        s5 = (String)inputStream2.readObject();
                        propertyValueProvider2 = (IPropertyValueProvider)inputStream2.readObject();
                        remoteStub2 = (RemoteStub)inputStream2.readObject();
                        s6 = (String)inputStream2.readObject();
                        vector2 = (Vector)inputStream2.readObject();
                    }
                    catch (IOException ex10) {
                        throw new UnmarshalException("error unmarshalling arguments", ex10);
                    }
                    catch (ClassNotFoundException ex11) {
                        throw new UnmarshalException("error unmarshalling arguments", ex11);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                }
                final int updateFromClient = propertyManager.updateFromClient(s5, propertyValueProvider2, remoteStub2, s6, vector2);
                try {
                    remoteCall.getResultStream(true).writeInt(updateFromClient);
                    return;
                }
                catch (IOException ex12) {
                    throw new MarshalException("error marshalling return", ex12);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return PropertyManager_Skel.operations.clone();
    }
}

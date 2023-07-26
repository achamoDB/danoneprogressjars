// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.server.RemoteStub;
import java.util.Enumeration;
import java.io.ObjectInput;
import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class JAService_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 1896728163973074686L;
    
    static {
        operations = new Operation[] { new Operation("boolean childNameUsed(java.lang.String)"), new Operation("com.progress.juniper.admin.IJAHierarchy createChild(java.lang.String, java.lang.Object, java.lang.Object)"), new Operation("void delete(java.lang.Object)"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.util.Enumeration getChildrenObjects()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.lang.String getDisplayName(boolean)"), new Operation("java.lang.String getMMCClientClass()"), new Operation("com.progress.juniper.admin.IJAHierarchy getParent()"), new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("java.lang.String getServiceNameOrPort()"), new Operation("java.lang.String getStateDescriptor()"), new Operation("com.progress.juniper.admin.JAStatusObject getStatus()"), new Operation("boolean isDeleteable()"), new Operation("boolean isIdle()"), new Operation("boolean isInitializing()"), new Operation("boolean isRunning()"), new Operation("boolean isShuttingDown()"), new Operation("boolean isStartable()"), new Operation("boolean isStarting()"), new Operation("boolean isStopable()"), new Operation("java.lang.String makeNewChildName()"), new Operation("void propertiesChanged()"), new Operation("java.rmi.server.RemoteStub remoteStub()"), new Operation("void start()"), new Operation("void stop()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 1896728163973074686L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final JAService jaService = (JAService)remote;
        Label_0694: {
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
                    Label_0277: {
                        break Label_0277;
                        final String s;
                        final boolean childNameUsed = jaService.childNameUsed(s);
                        try {
                            remoteCall.getResultStream(true).writeBoolean(childNameUsed);
                            return;
                        }
                        catch (IOException ex3) {
                            throw new MarshalException("error marshalling return", ex3);
                        }
                        try {
                            final ObjectInput inputStream = remoteCall.getInputStream();
                            final String s2 = (String)inputStream.readObject();
                            final Object object = inputStream.readObject();
                            final Object object2 = inputStream.readObject();
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
                    Object object3 = null;
                    Label_0432: {
                        break Label_0432;
                        final String s2;
                        final Object object;
                        final Object object2;
                        final IJAHierarchy child = jaService.createChild(s2, object, object2);
                        try {
                            remoteCall.getResultStream(true).writeObject(child);
                            return;
                        }
                        catch (IOException ex6) {
                            throw new MarshalException("error marshalling return", ex6);
                        }
                        try {
                            object3 = remoteCall.getInputStream().readObject();
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
                    jaService.delete(object3);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex9) {
                        throw new MarshalException("error marshalling return", ex9);
                    }
                }
                case 3: {
                    remoteCall.releaseInputStream();
                    final Enumeration children = jaService.getChildren();
                    try {
                        remoteCall.getResultStream(true).writeObject(children);
                        return;
                    }
                    catch (IOException ex10) {
                        throw new MarshalException("error marshalling return", ex10);
                    }
                }
                case 6: {
                    break Label_0694;
                }
                case 4: {
                    remoteCall.releaseInputStream();
                    final Enumeration childrenObjects = jaService.getChildrenObjects();
                    try {
                        remoteCall.getResultStream(true).writeObject(childrenObjects);
                        return;
                    }
                    catch (IOException ex11) {
                        throw new MarshalException("error marshalling return", ex11);
                    }
                }
                case 5: {
                    remoteCall.releaseInputStream();
                    final String displayName = jaService.getDisplayName();
                    try {
                        remoteCall.getResultStream(true).writeObject(displayName);
                        return;
                    }
                    catch (IOException ex12) {
                        throw new MarshalException("error marshalling return", ex12);
                    }
                    boolean boolean1;
                    try {
                        boolean1 = remoteCall.getInputStream().readBoolean();
                    }
                    catch (IOException ex13) {
                        throw new UnmarshalException("error unmarshalling arguments", ex13);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    final String displayName2 = jaService.getDisplayName(boolean1);
                    try {
                        remoteCall.getResultStream(true).writeObject(displayName2);
                        return;
                    }
                    catch (IOException ex14) {
                        throw new MarshalException("error marshalling return", ex14);
                    }
                }
                case 7: {
                    remoteCall.releaseInputStream();
                    final String mmcClientClass = jaService.getMMCClientClass();
                    try {
                        remoteCall.getResultStream(true).writeObject(mmcClientClass);
                        return;
                    }
                    catch (IOException ex15) {
                        throw new MarshalException("error marshalling return", ex15);
                    }
                }
                case 8: {
                    remoteCall.releaseInputStream();
                    final IJAHierarchy parent = jaService.getParent();
                    try {
                        remoteCall.getResultStream(true).writeObject(parent);
                        return;
                    }
                    catch (IOException ex16) {
                        throw new MarshalException("error marshalling return", ex16);
                    }
                }
                case 9: {
                    remoteCall.releaseInputStream();
                    final IJAPlugIn plugIn = jaService.getPlugIn();
                    try {
                        remoteCall.getResultStream(true).writeObject(plugIn);
                        return;
                    }
                    catch (IOException ex17) {
                        throw new MarshalException("error marshalling return", ex17);
                    }
                }
                case 10: {
                    remoteCall.releaseInputStream();
                    final String serviceNameOrPort = jaService.getServiceNameOrPort();
                    try {
                        remoteCall.getResultStream(true).writeObject(serviceNameOrPort);
                        return;
                    }
                    catch (IOException ex18) {
                        throw new MarshalException("error marshalling return", ex18);
                    }
                }
                case 11: {
                    remoteCall.releaseInputStream();
                    final String stateDescriptor = jaService.getStateDescriptor();
                    try {
                        remoteCall.getResultStream(true).writeObject(stateDescriptor);
                        return;
                    }
                    catch (IOException ex19) {
                        throw new MarshalException("error marshalling return", ex19);
                    }
                }
                case 12: {
                    remoteCall.releaseInputStream();
                    final JAStatusObject status = jaService.getStatus();
                    try {
                        remoteCall.getResultStream(true).writeObject(status);
                        return;
                    }
                    catch (IOException ex20) {
                        throw new MarshalException("error marshalling return", ex20);
                    }
                }
                case 13: {
                    remoteCall.releaseInputStream();
                    final boolean deleteable = jaService.isDeleteable();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(deleteable);
                        return;
                    }
                    catch (IOException ex21) {
                        throw new MarshalException("error marshalling return", ex21);
                    }
                }
                case 14: {
                    remoteCall.releaseInputStream();
                    final boolean idle = jaService.isIdle();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(idle);
                        return;
                    }
                    catch (IOException ex22) {
                        throw new MarshalException("error marshalling return", ex22);
                    }
                }
                case 15: {
                    remoteCall.releaseInputStream();
                    final boolean initializing = jaService.isInitializing();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(initializing);
                        return;
                    }
                    catch (IOException ex23) {
                        throw new MarshalException("error marshalling return", ex23);
                    }
                }
                case 16: {
                    remoteCall.releaseInputStream();
                    final boolean running = jaService.isRunning();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(running);
                        return;
                    }
                    catch (IOException ex24) {
                        throw new MarshalException("error marshalling return", ex24);
                    }
                }
                case 17: {
                    remoteCall.releaseInputStream();
                    final boolean shuttingDown = jaService.isShuttingDown();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(shuttingDown);
                        return;
                    }
                    catch (IOException ex25) {
                        throw new MarshalException("error marshalling return", ex25);
                    }
                }
                case 18: {
                    remoteCall.releaseInputStream();
                    final boolean startable = jaService.isStartable();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(startable);
                        return;
                    }
                    catch (IOException ex26) {
                        throw new MarshalException("error marshalling return", ex26);
                    }
                }
                case 19: {
                    remoteCall.releaseInputStream();
                    final boolean starting = jaService.isStarting();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(starting);
                        return;
                    }
                    catch (IOException ex27) {
                        throw new MarshalException("error marshalling return", ex27);
                    }
                }
                case 20: {
                    remoteCall.releaseInputStream();
                    final boolean stopable = jaService.isStopable();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(stopable);
                        return;
                    }
                    catch (IOException ex28) {
                        throw new MarshalException("error marshalling return", ex28);
                    }
                }
                case 21: {
                    remoteCall.releaseInputStream();
                    final String newChildName = jaService.makeNewChildName();
                    try {
                        remoteCall.getResultStream(true).writeObject(newChildName);
                        return;
                    }
                    catch (IOException ex29) {
                        throw new MarshalException("error marshalling return", ex29);
                    }
                }
                case 22: {
                    remoteCall.releaseInputStream();
                    jaService.propertiesChanged();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex30) {
                        throw new MarshalException("error marshalling return", ex30);
                    }
                }
                case 24: {
                    remoteCall.releaseInputStream();
                    jaService.start();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex33) {
                        throw new MarshalException("error marshalling return", ex33);
                    }
                }
                case 25: {
                    remoteCall.releaseInputStream();
                    jaService.stop();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex32) {
                        throw new MarshalException("error marshalling return", ex32);
                    }
                    break;
                }
                case 23: {
                    remoteCall.releaseInputStream();
                    final RemoteStub remoteStub = jaService.remoteStub();
                    try {
                        remoteCall.getResultStream(true).writeObject(remoteStub);
                        return;
                    }
                    catch (IOException ex31) {
                        throw new MarshalException("error marshalling return", ex31);
                    }
                }
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return JAService_Skel.operations.clone();
    }
}

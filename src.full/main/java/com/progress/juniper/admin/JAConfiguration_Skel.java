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

public final class JAConfiguration_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -3961504854728558975L;
    
    static {
        operations = new Operation[] { new Operation("boolean childNameUsed(java.lang.String)"), new Operation("com.progress.juniper.admin.IJAHierarchy createChild(java.lang.String, java.lang.Object, java.lang.Object)"), new Operation("void delete(java.lang.Object)"), new Operation("void exiting()"), new Operation("com.progress.juniper.admin.IJAAuxiliary getAIWriter()"), new Operation("com.progress.juniper.admin.IJAAuxiliary getAPWriter()"), new Operation("java.util.Enumeration getAuxiliaries()"), new Operation("com.progress.juniper.admin.IJAAuxiliary getBIWriter()"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.util.Enumeration getChildrenObjects()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.lang.String getDisplayName(boolean)"), new Operation("java.lang.String getMMCClientClass()"), new Operation("com.progress.juniper.admin.IJAHierarchy getParent()"), new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("java.lang.String getStateDescriptor()"), new Operation("com.progress.juniper.admin.JAStatusObject getStatus()"), new Operation("com.progress.juniper.admin.IJAAuxiliary getWatchdog()"), new Operation("boolean isDefault()"), new Operation("boolean isDeleteable()"), new Operation("boolean isIdle()"), new Operation("boolean isInitializing()"), new Operation("boolean isNetworked()"), new Operation("boolean isRunning()"), new Operation("boolean isShuttingDown()"), new Operation("boolean isStartable()"), new Operation("boolean isStarting()"), new Operation("boolean isStopable()"), new Operation("java.lang.String makeNewChildName()"), new Operation("boolean portUsed(java.lang.String)"), new Operation("void propertiesChanged()"), new Operation("java.rmi.server.RemoteStub remoteStub()"), new Operation("void serviceClosed(java.lang.String)"), new Operation("void serviceFailed(java.lang.String, java.lang.String[])"), new Operation("void start()"), new Operation("void stop()"), new Operation("void toggleDefaultSetting()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -3961504854728558975L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final JAConfiguration jaConfiguration = (JAConfiguration)remote;
        Label_1898: {
            while (true) {
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
                        Label_0321: {
                            break Label_0321;
                            final String s;
                            final boolean childNameUsed = jaConfiguration.childNameUsed(s);
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
                        Label_0476: {
                            break Label_0476;
                            final String s2;
                            final Object object;
                            final Object object2;
                            final IJAHierarchy child = jaConfiguration.createChild(s2, object, object2);
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
                        jaConfiguration.delete(object3);
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
                        jaConfiguration.exiting();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex10) {
                            throw new MarshalException("error marshalling return", ex10);
                        }
                    }
                    case 4: {
                        remoteCall.releaseInputStream();
                        final IJAAuxiliary aiWriter = jaConfiguration.getAIWriter();
                        try {
                            remoteCall.getResultStream(true).writeObject(aiWriter);
                            return;
                        }
                        catch (IOException ex11) {
                            throw new MarshalException("error marshalling return", ex11);
                        }
                    }
                    case 5: {
                        remoteCall.releaseInputStream();
                        final IJAAuxiliary apWriter = jaConfiguration.getAPWriter();
                        try {
                            remoteCall.getResultStream(true).writeObject(apWriter);
                            return;
                        }
                        catch (IOException ex12) {
                            throw new MarshalException("error marshalling return", ex12);
                        }
                    }
                    case 6: {
                        remoteCall.releaseInputStream();
                        final Enumeration auxiliaries = jaConfiguration.getAuxiliaries();
                        try {
                            remoteCall.getResultStream(true).writeObject(auxiliaries);
                            return;
                        }
                        catch (IOException ex13) {
                            throw new MarshalException("error marshalling return", ex13);
                        }
                    }
                    case 7: {
                        remoteCall.releaseInputStream();
                        final IJAAuxiliary biWriter = jaConfiguration.getBIWriter();
                        try {
                            remoteCall.getResultStream(true).writeObject(biWriter);
                            return;
                        }
                        catch (IOException ex14) {
                            throw new MarshalException("error marshalling return", ex14);
                        }
                    }
                    case 8: {
                        remoteCall.releaseInputStream();
                        final Enumeration children = jaConfiguration.getChildren();
                        try {
                            remoteCall.getResultStream(true).writeObject(children);
                            return;
                        }
                        catch (IOException ex15) {
                            throw new MarshalException("error marshalling return", ex15);
                        }
                    }
                    case 11: {
                        break Label_1898;
                    }
                    case 9: {
                        remoteCall.releaseInputStream();
                        final Enumeration childrenObjects = jaConfiguration.getChildrenObjects();
                        try {
                            remoteCall.getResultStream(true).writeObject(childrenObjects);
                            return;
                        }
                        catch (IOException ex16) {
                            throw new MarshalException("error marshalling return", ex16);
                        }
                    }
                    case 10: {
                        remoteCall.releaseInputStream();
                        final String displayName = jaConfiguration.getDisplayName();
                        try {
                            remoteCall.getResultStream(true).writeObject(displayName);
                            return;
                        }
                        catch (IOException ex17) {
                            throw new MarshalException("error marshalling return", ex17);
                        }
                        boolean boolean1;
                        try {
                            boolean1 = remoteCall.getInputStream().readBoolean();
                        }
                        catch (IOException ex18) {
                            throw new UnmarshalException("error unmarshalling arguments", ex18);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        final String displayName2 = jaConfiguration.getDisplayName(boolean1);
                        try {
                            remoteCall.getResultStream(true).writeObject(displayName2);
                            return;
                        }
                        catch (IOException ex19) {
                            throw new MarshalException("error marshalling return", ex19);
                        }
                    }
                    case 12: {
                        remoteCall.releaseInputStream();
                        final String mmcClientClass = jaConfiguration.getMMCClientClass();
                        try {
                            remoteCall.getResultStream(true).writeObject(mmcClientClass);
                            return;
                        }
                        catch (IOException ex20) {
                            throw new MarshalException("error marshalling return", ex20);
                        }
                    }
                    case 13: {
                        remoteCall.releaseInputStream();
                        final IJAHierarchy parent = jaConfiguration.getParent();
                        try {
                            remoteCall.getResultStream(true).writeObject(parent);
                            return;
                        }
                        catch (IOException ex21) {
                            throw new MarshalException("error marshalling return", ex21);
                        }
                    }
                    case 14: {
                        remoteCall.releaseInputStream();
                        final IJAPlugIn plugIn = jaConfiguration.getPlugIn();
                        try {
                            remoteCall.getResultStream(true).writeObject(plugIn);
                            return;
                        }
                        catch (IOException ex22) {
                            throw new MarshalException("error marshalling return", ex22);
                        }
                    }
                    case 15: {
                        remoteCall.releaseInputStream();
                        final String stateDescriptor = jaConfiguration.getStateDescriptor();
                        try {
                            remoteCall.getResultStream(true).writeObject(stateDescriptor);
                            return;
                        }
                        catch (IOException ex23) {
                            throw new MarshalException("error marshalling return", ex23);
                        }
                    }
                    case 16: {
                        remoteCall.releaseInputStream();
                        final JAStatusObject status = jaConfiguration.getStatus();
                        try {
                            remoteCall.getResultStream(true).writeObject(status);
                            return;
                        }
                        catch (IOException ex24) {
                            throw new MarshalException("error marshalling return", ex24);
                        }
                    }
                    case 17: {
                        remoteCall.releaseInputStream();
                        final IJAAuxiliary watchdog = jaConfiguration.getWatchdog();
                        try {
                            remoteCall.getResultStream(true).writeObject(watchdog);
                            return;
                        }
                        catch (IOException ex25) {
                            throw new MarshalException("error marshalling return", ex25);
                        }
                    }
                    case 18: {
                        remoteCall.releaseInputStream();
                        final boolean default1 = jaConfiguration.isDefault();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(default1);
                            return;
                        }
                        catch (IOException ex26) {
                            throw new MarshalException("error marshalling return", ex26);
                        }
                    }
                    case 19: {
                        remoteCall.releaseInputStream();
                        final boolean deleteable = jaConfiguration.isDeleteable();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(deleteable);
                            return;
                        }
                        catch (IOException ex27) {
                            throw new MarshalException("error marshalling return", ex27);
                        }
                    }
                    case 20: {
                        remoteCall.releaseInputStream();
                        final boolean idle = jaConfiguration.isIdle();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(idle);
                            return;
                        }
                        catch (IOException ex28) {
                            throw new MarshalException("error marshalling return", ex28);
                        }
                    }
                    case 21: {
                        remoteCall.releaseInputStream();
                        final boolean initializing = jaConfiguration.isInitializing();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(initializing);
                            return;
                        }
                        catch (IOException ex29) {
                            throw new MarshalException("error marshalling return", ex29);
                        }
                    }
                    case 22: {
                        remoteCall.releaseInputStream();
                        final boolean networked = jaConfiguration.isNetworked();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(networked);
                            return;
                        }
                        catch (IOException ex30) {
                            throw new MarshalException("error marshalling return", ex30);
                        }
                    }
                    case 23: {
                        remoteCall.releaseInputStream();
                        final boolean running = jaConfiguration.isRunning();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(running);
                            return;
                        }
                        catch (IOException ex31) {
                            throw new MarshalException("error marshalling return", ex31);
                        }
                    }
                    case 24: {
                        remoteCall.releaseInputStream();
                        final boolean shuttingDown = jaConfiguration.isShuttingDown();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(shuttingDown);
                            return;
                        }
                        catch (IOException ex32) {
                            throw new MarshalException("error marshalling return", ex32);
                        }
                    }
                    case 25: {
                        remoteCall.releaseInputStream();
                        final boolean startable = jaConfiguration.isStartable();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(startable);
                            return;
                        }
                        catch (IOException ex33) {
                            throw new MarshalException("error marshalling return", ex33);
                        }
                    }
                    case 26: {
                        remoteCall.releaseInputStream();
                        final boolean starting = jaConfiguration.isStarting();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(starting);
                            return;
                        }
                        catch (IOException ex34) {
                            throw new MarshalException("error marshalling return", ex34);
                        }
                    }
                    case 29: {
                        break Label_1898;
                    }
                    case 27: {
                        remoteCall.releaseInputStream();
                        final boolean stopable = jaConfiguration.isStopable();
                        try {
                            remoteCall.getResultStream(true).writeBoolean(stopable);
                            return;
                        }
                        catch (IOException ex35) {
                            throw new MarshalException("error marshalling return", ex35);
                        }
                    }
                    case 30: {
                        remoteCall.releaseInputStream();
                        jaConfiguration.propertiesChanged();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex43) {
                            throw new MarshalException("error marshalling return", ex43);
                        }
                    }
                    case 28: {
                        remoteCall.releaseInputStream();
                        final String newChildName = jaConfiguration.makeNewChildName();
                        try {
                            remoteCall.getResultStream(true).writeObject(newChildName);
                            return;
                        }
                        catch (IOException ex36) {
                            throw new MarshalException("error marshalling return", ex36);
                        }
                        String s3;
                        try {
                            s3 = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex37) {
                            throw new UnmarshalException("error unmarshalling arguments", ex37);
                        }
                        catch (ClassNotFoundException ex38) {
                            throw new UnmarshalException("error unmarshalling arguments", ex38);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        final boolean portUsed = jaConfiguration.portUsed(s3);
                        try {
                            remoteCall.getResultStream(true).writeBoolean(portUsed);
                            return;
                        }
                        catch (IOException ex39) {
                            throw new MarshalException("error marshalling return", ex39);
                        }
                    }
                    case 32: {
                        continue;
                    }
                    case 31: {
                        remoteCall.releaseInputStream();
                        final RemoteStub remoteStub = jaConfiguration.remoteStub();
                        try {
                            remoteCall.getResultStream(true).writeObject(remoteStub);
                            return;
                        }
                        catch (IOException ex40) {
                            throw new MarshalException("error marshalling return", ex40);
                        }
                        try {
                            final String s4 = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex41) {
                            throw new UnmarshalException("error unmarshalling arguments", ex41);
                        }
                        catch (ClassNotFoundException ex42) {
                            throw new UnmarshalException("error unmarshalling arguments", ex42);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                    }
                    case 33: {
                        String s5 = null;
                        String[] array = null;
                        Label_2236: {
                            break Label_2236;
                            final String s4;
                            jaConfiguration.serviceClosed(s4);
                            try {
                                remoteCall.getResultStream(true);
                                return;
                            }
                            catch (IOException ex44) {
                                throw new MarshalException("error marshalling return", ex44);
                            }
                            try {
                                final ObjectInput inputStream2 = remoteCall.getInputStream();
                                s5 = (String)inputStream2.readObject();
                                array = (String[])inputStream2.readObject();
                            }
                            catch (IOException ex45) {
                                throw new UnmarshalException("error unmarshalling arguments", ex45);
                            }
                            catch (ClassNotFoundException ex46) {
                                throw new UnmarshalException("error unmarshalling arguments", ex46);
                            }
                            finally {
                                remoteCall.releaseInputStream();
                            }
                        }
                        jaConfiguration.serviceFailed(s5, array);
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex47) {
                            throw new MarshalException("error marshalling return", ex47);
                        }
                    }
                    case 34: {
                        remoteCall.releaseInputStream();
                        jaConfiguration.start();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex48) {
                            throw new MarshalException("error marshalling return", ex48);
                        }
                    }
                    case 35: {
                        remoteCall.releaseInputStream();
                        jaConfiguration.stop();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex49) {
                            throw new MarshalException("error marshalling return", ex49);
                        }
                    }
                    case 36: {
                        remoteCall.releaseInputStream();
                        jaConfiguration.toggleDefaultSetting();
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex50) {
                            throw new MarshalException("error marshalling return", ex50);
                        }
                        break;
                    }
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return JAConfiguration_Skel.operations.clone();
    }
}

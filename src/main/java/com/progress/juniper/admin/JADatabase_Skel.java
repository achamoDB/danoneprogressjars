// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.server.RemoteStub;
import com.progress.common.log.IFileRef;
import java.util.Enumeration;
import java.io.ObjectInput;
import com.progress.common.networkevents.IEventListener;
import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class JADatabase_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 3961530066805925774L;
    
    static {
        operations = new Operation[] { new Operation("boolean childNameUsed(java.lang.String)"), new Operation("com.progress.juniper.admin.IJAHierarchy createChild(java.lang.String, java.lang.Object, java.lang.Object)"), new Operation("void delete(java.lang.Object)"), new Operation("com.progress.juniper.admin.IAgentDatabaseHandle getAgent()"), new Operation("com.progress.juniper.admin.IJAAgent getAgentRemote()"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.util.Enumeration getChildrenObjects()"), new Operation("com.progress.juniper.admin.IJAConfiguration getCurrentConfiguration()"), new Operation("int getDatabaseEnablement()"), new Operation("java.lang.String getDatabaseFileName()"), new Operation("com.progress.juniper.admin.IJAConfiguration getDefaultConfiguration()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.lang.String getDisplayName(boolean)"), new Operation("java.lang.String getMMCClientClass()"), new Operation("com.progress.juniper.admin.IJAHierarchy getParent()"), new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("com.progress.juniper.admin.IReplDatabaseHandle getReplDatabaseHandle()"), new Operation("com.progress.juniper.admin.IJAConfiguration getRunningConfiguration()"), new Operation("java.lang.String getStateDescriptor()"), new Operation("com.progress.juniper.admin.JAStatusObject getStatus()"), new Operation("com.progress.juniper.admin.JAStatusObject getStatus(com.progress.juniper.admin.IJAConfiguration)"), new Operation("java.lang.String getUser()"), new Operation("boolean isDeleteable()"), new Operation("boolean isIdle()"), new Operation("boolean isInitializing()"), new Operation("boolean isRunning()"), new Operation("boolean isShuttingDown()"), new Operation("boolean isStartable()"), new Operation("boolean isStarting()"), new Operation("boolean isStopable()"), new Operation("java.lang.String makeNewChildName()"), new Operation("com.progress.common.log.IFileRef openFile(java.lang.String, com.progress.common.networkevents.IEventListener)"), new Operation("void propertiesChanged()"), new Operation("void refresh()"), new Operation("java.rmi.server.RemoteStub remoteStub()"), new Operation("void setUser(java.lang.String)"), new Operation("void start()"), new Operation("void stop()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 3961530066805925774L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final JADatabase jaDatabase = (JADatabase)remote;
        Label_2362: {
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
                    Label_0325: {
                        break Label_0325;
                        final String s;
                        final boolean childNameUsed = jaDatabase.childNameUsed(s);
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
                    Label_0480: {
                        break Label_0480;
                        final String s2;
                        final Object object;
                        final Object object2;
                        final IJAHierarchy child = jaDatabase.createChild(s2, object, object2);
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
                    jaDatabase.delete(object3);
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
                    final IAgentDatabaseHandle agent = jaDatabase.getAgent();
                    try {
                        remoteCall.getResultStream(true).writeObject(agent);
                        return;
                    }
                    catch (IOException ex10) {
                        throw new MarshalException("error marshalling return", ex10);
                    }
                }
                case 4: {
                    remoteCall.releaseInputStream();
                    final IJAAgent agentRemote = jaDatabase.getAgentRemote();
                    try {
                        remoteCall.getResultStream(true).writeObject(agentRemote);
                        return;
                    }
                    catch (IOException ex11) {
                        throw new MarshalException("error marshalling return", ex11);
                    }
                }
                case 5: {
                    remoteCall.releaseInputStream();
                    final Enumeration children = jaDatabase.getChildren();
                    try {
                        remoteCall.getResultStream(true).writeObject(children);
                        return;
                    }
                    catch (IOException ex12) {
                        throw new MarshalException("error marshalling return", ex12);
                    }
                }
                case 6: {
                    remoteCall.releaseInputStream();
                    final Enumeration childrenObjects = jaDatabase.getChildrenObjects();
                    try {
                        remoteCall.getResultStream(true).writeObject(childrenObjects);
                        return;
                    }
                    catch (IOException ex13) {
                        throw new MarshalException("error marshalling return", ex13);
                    }
                }
                case 7: {
                    remoteCall.releaseInputStream();
                    final IJAConfiguration currentConfiguration = jaDatabase.getCurrentConfiguration();
                    try {
                        remoteCall.getResultStream(true).writeObject(currentConfiguration);
                        return;
                    }
                    catch (IOException ex14) {
                        throw new MarshalException("error marshalling return", ex14);
                    }
                }
                case 8: {
                    remoteCall.releaseInputStream();
                    final int databaseEnablement = jaDatabase.getDatabaseEnablement();
                    try {
                        remoteCall.getResultStream(true).writeInt(databaseEnablement);
                        return;
                    }
                    catch (IOException ex15) {
                        throw new MarshalException("error marshalling return", ex15);
                    }
                }
                case 9: {
                    remoteCall.releaseInputStream();
                    final String databaseFileName = jaDatabase.getDatabaseFileName();
                    try {
                        remoteCall.getResultStream(true).writeObject(databaseFileName);
                        return;
                    }
                    catch (IOException ex16) {
                        throw new MarshalException("error marshalling return", ex16);
                    }
                }
                case 12: {
                    break Label_2362;
                }
                case 10: {
                    remoteCall.releaseInputStream();
                    final IJAConfiguration defaultConfiguration = jaDatabase.getDefaultConfiguration();
                    try {
                        remoteCall.getResultStream(true).writeObject(defaultConfiguration);
                        return;
                    }
                    catch (IOException ex17) {
                        throw new MarshalException("error marshalling return", ex17);
                    }
                }
                case 11: {
                    remoteCall.releaseInputStream();
                    final String displayName = jaDatabase.getDisplayName();
                    try {
                        remoteCall.getResultStream(true).writeObject(displayName);
                        return;
                    }
                    catch (IOException ex18) {
                        throw new MarshalException("error marshalling return", ex18);
                    }
                    boolean boolean1;
                    try {
                        boolean1 = remoteCall.getInputStream().readBoolean();
                    }
                    catch (IOException ex19) {
                        throw new UnmarshalException("error unmarshalling arguments", ex19);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    final String displayName2 = jaDatabase.getDisplayName(boolean1);
                    try {
                        remoteCall.getResultStream(true).writeObject(displayName2);
                        return;
                    }
                    catch (IOException ex20) {
                        throw new MarshalException("error marshalling return", ex20);
                    }
                }
                case 13: {
                    remoteCall.releaseInputStream();
                    final String mmcClientClass = jaDatabase.getMMCClientClass();
                    try {
                        remoteCall.getResultStream(true).writeObject(mmcClientClass);
                        return;
                    }
                    catch (IOException ex21) {
                        throw new MarshalException("error marshalling return", ex21);
                    }
                }
                case 14: {
                    remoteCall.releaseInputStream();
                    final IJAHierarchy parent = jaDatabase.getParent();
                    try {
                        remoteCall.getResultStream(true).writeObject(parent);
                        return;
                    }
                    catch (IOException ex22) {
                        throw new MarshalException("error marshalling return", ex22);
                    }
                }
                case 15: {
                    remoteCall.releaseInputStream();
                    final IJAPlugIn plugIn = jaDatabase.getPlugIn();
                    try {
                        remoteCall.getResultStream(true).writeObject(plugIn);
                        return;
                    }
                    catch (IOException ex23) {
                        throw new MarshalException("error marshalling return", ex23);
                    }
                }
                case 16: {
                    remoteCall.releaseInputStream();
                    final IReplDatabaseHandle replDatabaseHandle = jaDatabase.getReplDatabaseHandle();
                    try {
                        remoteCall.getResultStream(true).writeObject(replDatabaseHandle);
                        return;
                    }
                    catch (IOException ex24) {
                        throw new MarshalException("error marshalling return", ex24);
                    }
                }
                case 17: {
                    remoteCall.releaseInputStream();
                    final IJAConfiguration runningConfiguration = jaDatabase.getRunningConfiguration();
                    try {
                        remoteCall.getResultStream(true).writeObject(runningConfiguration);
                        return;
                    }
                    catch (IOException ex25) {
                        throw new MarshalException("error marshalling return", ex25);
                    }
                }
                case 20: {
                    break Label_2362;
                }
                case 18: {
                    remoteCall.releaseInputStream();
                    final String stateDescriptor = jaDatabase.getStateDescriptor();
                    try {
                        remoteCall.getResultStream(true).writeObject(stateDescriptor);
                        return;
                    }
                    catch (IOException ex26) {
                        throw new MarshalException("error marshalling return", ex26);
                    }
                }
                case 19: {
                    remoteCall.releaseInputStream();
                    final JAStatusObject status = jaDatabase.getStatus();
                    try {
                        remoteCall.getResultStream(true).writeObject(status);
                        return;
                    }
                    catch (IOException ex27) {
                        throw new MarshalException("error marshalling return", ex27);
                    }
                    IJAConfiguration ijaConfiguration;
                    try {
                        ijaConfiguration = (IJAConfiguration)remoteCall.getInputStream().readObject();
                    }
                    catch (IOException ex28) {
                        throw new UnmarshalException("error unmarshalling arguments", ex28);
                    }
                    catch (ClassNotFoundException ex29) {
                        throw new UnmarshalException("error unmarshalling arguments", ex29);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    final JAStatusObject status2 = jaDatabase.getStatus(ijaConfiguration);
                    try {
                        remoteCall.getResultStream(true).writeObject(status2);
                        return;
                    }
                    catch (IOException ex30) {
                        throw new MarshalException("error marshalling return", ex30);
                    }
                }
                case 21: {
                    remoteCall.releaseInputStream();
                    final String user = jaDatabase.getUser();
                    try {
                        remoteCall.getResultStream(true).writeObject(user);
                        return;
                    }
                    catch (IOException ex31) {
                        throw new MarshalException("error marshalling return", ex31);
                    }
                }
                case 22: {
                    remoteCall.releaseInputStream();
                    final boolean deleteable = jaDatabase.isDeleteable();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(deleteable);
                        return;
                    }
                    catch (IOException ex32) {
                        throw new MarshalException("error marshalling return", ex32);
                    }
                }
                case 23: {
                    remoteCall.releaseInputStream();
                    final boolean idle = jaDatabase.isIdle();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(idle);
                        return;
                    }
                    catch (IOException ex33) {
                        throw new MarshalException("error marshalling return", ex33);
                    }
                }
                case 24: {
                    remoteCall.releaseInputStream();
                    final boolean initializing = jaDatabase.isInitializing();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(initializing);
                        return;
                    }
                    catch (IOException ex34) {
                        throw new MarshalException("error marshalling return", ex34);
                    }
                }
                case 25: {
                    remoteCall.releaseInputStream();
                    final boolean running = jaDatabase.isRunning();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(running);
                        return;
                    }
                    catch (IOException ex35) {
                        throw new MarshalException("error marshalling return", ex35);
                    }
                }
                case 26: {
                    remoteCall.releaseInputStream();
                    final boolean shuttingDown = jaDatabase.isShuttingDown();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(shuttingDown);
                        return;
                    }
                    catch (IOException ex36) {
                        throw new MarshalException("error marshalling return", ex36);
                    }
                }
                case 27: {
                    remoteCall.releaseInputStream();
                    final boolean startable = jaDatabase.isStartable();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(startable);
                        return;
                    }
                    catch (IOException ex37) {
                        throw new MarshalException("error marshalling return", ex37);
                    }
                }
                case 28: {
                    remoteCall.releaseInputStream();
                    final boolean starting = jaDatabase.isStarting();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(starting);
                        return;
                    }
                    catch (IOException ex38) {
                        throw new MarshalException("error marshalling return", ex38);
                    }
                }
                case 31: {
                    break Label_2362;
                }
                case 29: {
                    remoteCall.releaseInputStream();
                    final boolean stopable = jaDatabase.isStopable();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(stopable);
                        return;
                    }
                    catch (IOException ex39) {
                        throw new MarshalException("error marshalling return", ex39);
                    }
                }
                case 30: {
                    remoteCall.releaseInputStream();
                    final String newChildName = jaDatabase.makeNewChildName();
                    try {
                        remoteCall.getResultStream(true).writeObject(newChildName);
                        return;
                    }
                    catch (IOException ex40) {
                        throw new MarshalException("error marshalling return", ex40);
                    }
                    String s3;
                    IEventListener eventListener;
                    try {
                        final ObjectInput inputStream2 = remoteCall.getInputStream();
                        s3 = (String)inputStream2.readObject();
                        eventListener = (IEventListener)inputStream2.readObject();
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
                    final IFileRef openFile = jaDatabase.openFile(s3, eventListener);
                    try {
                        remoteCall.getResultStream(true).writeObject(openFile);
                        return;
                    }
                    catch (IOException ex43) {
                        throw new MarshalException("error marshalling return", ex43);
                    }
                }
                case 32: {
                    remoteCall.releaseInputStream();
                    jaDatabase.propertiesChanged();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex44) {
                        throw new MarshalException("error marshalling return", ex44);
                    }
                }
                case 35: {
                    break Label_2362;
                }
                case 33: {
                    remoteCall.releaseInputStream();
                    jaDatabase.refresh();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex45) {
                        throw new MarshalException("error marshalling return", ex45);
                    }
                }
                case 36: {
                    remoteCall.releaseInputStream();
                    jaDatabase.start();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex51) {
                        throw new MarshalException("error marshalling return", ex51);
                    }
                }
                case 37: {
                    remoteCall.releaseInputStream();
                    jaDatabase.stop();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex50) {
                        throw new MarshalException("error marshalling return", ex50);
                    }
                    break;
                }
                case 34: {
                    remoteCall.releaseInputStream();
                    final RemoteStub remoteStub = jaDatabase.remoteStub();
                    try {
                        remoteCall.getResultStream(true).writeObject(remoteStub);
                        return;
                    }
                    catch (IOException ex46) {
                        throw new MarshalException("error marshalling return", ex46);
                    }
                    String user2;
                    try {
                        user2 = (String)remoteCall.getInputStream().readObject();
                    }
                    catch (IOException ex47) {
                        throw new UnmarshalException("error unmarshalling arguments", ex47);
                    }
                    catch (ClassNotFoundException ex48) {
                        throw new UnmarshalException("error unmarshalling arguments", ex48);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    jaDatabase.setUser(user2);
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex49) {
                        throw new MarshalException("error marshalling return", ex49);
                    }
                }
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return JADatabase_Skel.operations.clone();
    }
}

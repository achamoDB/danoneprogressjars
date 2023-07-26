// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.server.RemoteStub;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventBroker;
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

public final class JAPlugIn_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 1405696027394241279L;
    
    static {
        operations = new Operation[] { new Operation("boolean childNameUsed(java.lang.String)"), new Operation("com.progress.juniper.admin.IJAHierarchy createChild(java.lang.String, java.lang.Object, java.lang.Object)"), new Operation("com.progress.juniper.admin.IJADatabase createDatabase(java.lang.String, java.lang.String, java.lang.Object)"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.util.Enumeration getChildrenObjects()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.lang.String getDisplayName(boolean)"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("com.progress.juniper.admin.IJARmiComponent getJARmiComponent()"), new Operation("com.progress.common.licensemgr.ILicenseMgr getLicenseManager()"), new Operation("java.lang.String getMMCClientClass()"), new Operation("java.lang.String getOS()"), new Operation("com.progress.juniper.admin.IJAHierarchy getParent()"), new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("java.lang.String getPropFileName()"), new Operation("com.progress.common.property.IPropertyManagerRemote getPropertyManager()"), new Operation("boolean initializationComplete()"), new Operation("boolean isAgentLicensed()"), new Operation("boolean isReplLicensed()"), new Operation("java.lang.String makeNewChildName()"), new Operation("java.rmi.server.RemoteStub remoteStub()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 1405696027394241279L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final JAPlugIn jaPlugIn = (JAPlugIn)remote;
        Label_0714: {
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
                    Label_0257: {
                        break Label_0257;
                        final String s;
                        final boolean childNameUsed = jaPlugIn.childNameUsed(s);
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
                    String s3 = null;
                    String s4 = null;
                    Object object3 = null;
                    Label_0412: {
                        break Label_0412;
                        final String s2;
                        final Object object;
                        final Object object2;
                        final IJAHierarchy child = jaPlugIn.createChild(s2, object, object2);
                        try {
                            remoteCall.getResultStream(true).writeObject(child);
                            return;
                        }
                        catch (IOException ex6) {
                            throw new MarshalException("error marshalling return", ex6);
                        }
                        try {
                            final ObjectInput inputStream2 = remoteCall.getInputStream();
                            s3 = (String)inputStream2.readObject();
                            s4 = (String)inputStream2.readObject();
                            object3 = inputStream2.readObject();
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
                    final IJADatabase database = jaPlugIn.createDatabase(s3, s4, object3);
                    try {
                        remoteCall.getResultStream(true).writeObject(database);
                        return;
                    }
                    catch (IOException ex9) {
                        throw new MarshalException("error marshalling return", ex9);
                    }
                }
                case 3: {
                    remoteCall.releaseInputStream();
                    final Enumeration children = jaPlugIn.getChildren();
                    try {
                        remoteCall.getResultStream(true).writeObject(children);
                        return;
                    }
                    catch (IOException ex10) {
                        throw new MarshalException("error marshalling return", ex10);
                    }
                }
                case 6: {
                    break Label_0714;
                }
                case 4: {
                    remoteCall.releaseInputStream();
                    final Enumeration childrenObjects = jaPlugIn.getChildrenObjects();
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
                    final String displayName = jaPlugIn.getDisplayName();
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
                    final String displayName2 = jaPlugIn.getDisplayName(boolean1);
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
                    final IEventBroker eventBroker = jaPlugIn.getEventBroker();
                    try {
                        remoteCall.getResultStream(true).writeObject(eventBroker);
                        return;
                    }
                    catch (IOException ex15) {
                        throw new MarshalException("error marshalling return", ex15);
                    }
                }
                case 8: {
                    remoteCall.releaseInputStream();
                    final IJARmiComponent jaRmiComponent = jaPlugIn.getJARmiComponent();
                    try {
                        remoteCall.getResultStream(true).writeObject(jaRmiComponent);
                        return;
                    }
                    catch (IOException ex16) {
                        throw new MarshalException("error marshalling return", ex16);
                    }
                }
                case 9: {
                    remoteCall.releaseInputStream();
                    final ILicenseMgr licenseManager = jaPlugIn.getLicenseManager();
                    try {
                        remoteCall.getResultStream(true).writeObject(licenseManager);
                        return;
                    }
                    catch (IOException ex17) {
                        throw new MarshalException("error marshalling return", ex17);
                    }
                }
                case 10: {
                    remoteCall.releaseInputStream();
                    final String mmcClientClass = jaPlugIn.getMMCClientClass();
                    try {
                        remoteCall.getResultStream(true).writeObject(mmcClientClass);
                        return;
                    }
                    catch (IOException ex18) {
                        throw new MarshalException("error marshalling return", ex18);
                    }
                }
                case 11: {
                    remoteCall.releaseInputStream();
                    final String os = jaPlugIn.getOS();
                    try {
                        remoteCall.getResultStream(true).writeObject(os);
                        return;
                    }
                    catch (IOException ex19) {
                        throw new MarshalException("error marshalling return", ex19);
                    }
                }
                case 12: {
                    remoteCall.releaseInputStream();
                    final IJAHierarchy parent = jaPlugIn.getParent();
                    try {
                        remoteCall.getResultStream(true).writeObject(parent);
                        return;
                    }
                    catch (IOException ex20) {
                        throw new MarshalException("error marshalling return", ex20);
                    }
                }
                case 13: {
                    remoteCall.releaseInputStream();
                    final IJAPlugIn plugIn = jaPlugIn.getPlugIn();
                    try {
                        remoteCall.getResultStream(true).writeObject(plugIn);
                        return;
                    }
                    catch (IOException ex21) {
                        throw new MarshalException("error marshalling return", ex21);
                    }
                }
                case 14: {
                    remoteCall.releaseInputStream();
                    final String propFileName = jaPlugIn.getPropFileName();
                    try {
                        remoteCall.getResultStream(true).writeObject(propFileName);
                        return;
                    }
                    catch (IOException ex22) {
                        throw new MarshalException("error marshalling return", ex22);
                    }
                }
                case 15: {
                    remoteCall.releaseInputStream();
                    final IPropertyManagerRemote propertyManager = jaPlugIn.getPropertyManager();
                    try {
                        remoteCall.getResultStream(true).writeObject(propertyManager);
                        return;
                    }
                    catch (IOException ex23) {
                        throw new MarshalException("error marshalling return", ex23);
                    }
                }
                case 16: {
                    remoteCall.releaseInputStream();
                    final boolean initializationComplete = jaPlugIn.initializationComplete();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(initializationComplete);
                        return;
                    }
                    catch (IOException ex24) {
                        throw new MarshalException("error marshalling return", ex24);
                    }
                }
                case 17: {
                    remoteCall.releaseInputStream();
                    final boolean agentLicensed = jaPlugIn.isAgentLicensed();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(agentLicensed);
                        return;
                    }
                    catch (IOException ex25) {
                        throw new MarshalException("error marshalling return", ex25);
                    }
                }
                case 19: {
                    remoteCall.releaseInputStream();
                    final String newChildName = jaPlugIn.makeNewChildName();
                    try {
                        remoteCall.getResultStream(true).writeObject(newChildName);
                        return;
                    }
                    catch (IOException ex28) {
                        throw new MarshalException("error marshalling return", ex28);
                    }
                }
                case 20: {
                    remoteCall.releaseInputStream();
                    final RemoteStub remoteStub = jaPlugIn.remoteStub();
                    try {
                        remoteCall.getResultStream(true).writeObject(remoteStub);
                        return;
                    }
                    catch (IOException ex27) {
                        throw new MarshalException("error marshalling return", ex27);
                    }
                    break;
                }
                case 18: {
                    remoteCall.releaseInputStream();
                    final boolean replLicensed = jaPlugIn.isReplLicensed();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(replLicensed);
                        return;
                    }
                    catch (IOException ex26) {
                        throw new MarshalException("error marshalling return", ex26);
                    }
                }
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return JAPlugIn_Skel.operations.clone();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import com.progress.common.log.IFileRef;
import java.util.Vector;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.networkevents.IEventBroker;
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

public final class AdminServer_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 8422141365588997890L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.chimera.adminserver.IAdminServer connect(java.lang.String, java.lang.String)"), new Operation("com.progress.chimera.adminserver.IAdminServer connect(java.lang.String, java.lang.String, boolean)"), new Operation("java.lang.String getAdminPort()"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDbPropFile()"), new Operation("java.lang.String getDbaPropFile()"), new Operation("java.lang.String getDisplayName()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("com.progress.common.networkevents.EventBroker getEventBrokerLocal()"), new Operation("com.progress.common.networkevents.IEventStream getEventStream()"), new Operation("java.lang.String getFathomInitParams()"), new Operation("java.lang.String getFathomPropFile()"), new Operation("java.lang.String getHost()"), new Operation("com.progress.common.licensemgr.ILicenseMgr getLicenseManager()"), new Operation("java.lang.String getMMCClientClass()"), new Operation("java.lang.String getMgmtPropFile()"), new Operation("java.lang.String getOS()"), new Operation("java.lang.String getPort()"), new Operation("java.util.Vector getServerPluginInfo()"), new Operation("java.lang.String getSmdbPropFile()"), new Operation("java.lang.String getUbPropFile()"), new Operation("java.lang.String getVersion()"), new Operation("boolean isReqUserName()"), new Operation("com.progress.common.log.IFileRef openFile(java.lang.String, com.progress.common.networkevents.IEventListener)"), new Operation("void ping()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 8422141365588997890L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final AdminServer adminServer = (AdminServer)remote;
        Label_1453: {
            switch (n) {
                case 0: {
                    try {
                        final ObjectInput inputStream = remoteCall.getInputStream();
                        final String s = (String)inputStream.readObject();
                        final String s2 = (String)inputStream.readObject();
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
                    String s3 = null;
                    String s4 = null;
                    boolean boolean1 = false;
                    Label_0287: {
                        break Label_0287;
                        final String s;
                        final String s2;
                        final IAdminServer connect = adminServer.connect(s, s2);
                        try {
                            remoteCall.getResultStream(true).writeObject(connect);
                            return;
                        }
                        catch (IOException ex3) {
                            throw new MarshalException("error marshalling return", ex3);
                        }
                        try {
                            final ObjectInput inputStream2 = remoteCall.getInputStream();
                            s3 = (String)inputStream2.readObject();
                            s4 = (String)inputStream2.readObject();
                            boolean1 = inputStream2.readBoolean();
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
                    final IAdminServer connect2 = adminServer.connect(s3, s4, boolean1);
                    try {
                        remoteCall.getResultStream(true).writeObject(connect2);
                        return;
                    }
                    catch (IOException ex6) {
                        throw new MarshalException("error marshalling return", ex6);
                    }
                }
                case 2: {
                    remoteCall.releaseInputStream();
                    final String adminPort = adminServer.getAdminPort();
                    try {
                        remoteCall.getResultStream(true).writeObject(adminPort);
                        return;
                    }
                    catch (IOException ex7) {
                        throw new MarshalException("error marshalling return", ex7);
                    }
                }
                case 3: {
                    remoteCall.releaseInputStream();
                    final Enumeration children = adminServer.getChildren();
                    try {
                        remoteCall.getResultStream(true).writeObject(children);
                        return;
                    }
                    catch (IOException ex8) {
                        throw new MarshalException("error marshalling return", ex8);
                    }
                }
                case 4: {
                    remoteCall.releaseInputStream();
                    final String dbPropFile = adminServer.getDbPropFile();
                    try {
                        remoteCall.getResultStream(true).writeObject(dbPropFile);
                        return;
                    }
                    catch (IOException ex9) {
                        throw new MarshalException("error marshalling return", ex9);
                    }
                }
                case 5: {
                    remoteCall.releaseInputStream();
                    final String dbaPropFile = adminServer.getDbaPropFile();
                    try {
                        remoteCall.getResultStream(true).writeObject(dbaPropFile);
                        return;
                    }
                    catch (IOException ex10) {
                        throw new MarshalException("error marshalling return", ex10);
                    }
                }
                case 6: {
                    remoteCall.releaseInputStream();
                    final String displayName = adminServer.getDisplayName();
                    try {
                        remoteCall.getResultStream(true).writeObject(displayName);
                        return;
                    }
                    catch (IOException ex11) {
                        throw new MarshalException("error marshalling return", ex11);
                    }
                }
                case 7: {
                    remoteCall.releaseInputStream();
                    final IEventBroker eventBroker = adminServer.getEventBroker();
                    try {
                        remoteCall.getResultStream(true).writeObject(eventBroker);
                        return;
                    }
                    catch (IOException ex12) {
                        throw new MarshalException("error marshalling return", ex12);
                    }
                }
                case 8: {
                    remoteCall.releaseInputStream();
                    final EventBroker eventBrokerLocal = adminServer.getEventBrokerLocal();
                    try {
                        remoteCall.getResultStream(true).writeObject(eventBrokerLocal);
                        return;
                    }
                    catch (IOException ex13) {
                        throw new MarshalException("error marshalling return", ex13);
                    }
                }
                case 9: {
                    remoteCall.releaseInputStream();
                    final IEventStream eventStream = adminServer.getEventStream();
                    try {
                        remoteCall.getResultStream(true).writeObject(eventStream);
                        return;
                    }
                    catch (IOException ex14) {
                        throw new MarshalException("error marshalling return", ex14);
                    }
                }
                case 10: {
                    remoteCall.releaseInputStream();
                    final String fathomInitParams = adminServer.getFathomInitParams();
                    try {
                        remoteCall.getResultStream(true).writeObject(fathomInitParams);
                        return;
                    }
                    catch (IOException ex15) {
                        throw new MarshalException("error marshalling return", ex15);
                    }
                }
                case 11: {
                    remoteCall.releaseInputStream();
                    final String fathomPropFile = adminServer.getFathomPropFile();
                    try {
                        remoteCall.getResultStream(true).writeObject(fathomPropFile);
                        return;
                    }
                    catch (IOException ex16) {
                        throw new MarshalException("error marshalling return", ex16);
                    }
                }
                case 12: {
                    remoteCall.releaseInputStream();
                    final String host = adminServer.getHost();
                    try {
                        remoteCall.getResultStream(true).writeObject(host);
                        return;
                    }
                    catch (IOException ex17) {
                        throw new MarshalException("error marshalling return", ex17);
                    }
                }
                case 13: {
                    remoteCall.releaseInputStream();
                    final ILicenseMgr licenseManager = adminServer.getLicenseManager();
                    try {
                        remoteCall.getResultStream(true).writeObject(licenseManager);
                        return;
                    }
                    catch (IOException ex18) {
                        throw new MarshalException("error marshalling return", ex18);
                    }
                }
                case 14: {
                    remoteCall.releaseInputStream();
                    final String mmcClientClass = adminServer.getMMCClientClass();
                    try {
                        remoteCall.getResultStream(true).writeObject(mmcClientClass);
                        return;
                    }
                    catch (IOException ex19) {
                        throw new MarshalException("error marshalling return", ex19);
                    }
                }
                case 15: {
                    remoteCall.releaseInputStream();
                    final String mgmtPropFile = adminServer.getMgmtPropFile();
                    try {
                        remoteCall.getResultStream(true).writeObject(mgmtPropFile);
                        return;
                    }
                    catch (IOException ex20) {
                        throw new MarshalException("error marshalling return", ex20);
                    }
                }
                case 16: {
                    remoteCall.releaseInputStream();
                    final String os = adminServer.getOS();
                    try {
                        remoteCall.getResultStream(true).writeObject(os);
                        return;
                    }
                    catch (IOException ex21) {
                        throw new MarshalException("error marshalling return", ex21);
                    }
                }
                case 17: {
                    remoteCall.releaseInputStream();
                    final String port = adminServer.getPort();
                    try {
                        remoteCall.getResultStream(true).writeObject(port);
                        return;
                    }
                    catch (IOException ex22) {
                        throw new MarshalException("error marshalling return", ex22);
                    }
                }
                case 18: {
                    remoteCall.releaseInputStream();
                    final Vector serverPluginInfo = adminServer.getServerPluginInfo();
                    try {
                        remoteCall.getResultStream(true).writeObject(serverPluginInfo);
                        return;
                    }
                    catch (IOException ex23) {
                        throw new MarshalException("error marshalling return", ex23);
                    }
                }
                case 19: {
                    remoteCall.releaseInputStream();
                    final String smdbPropFile = adminServer.getSmdbPropFile();
                    try {
                        remoteCall.getResultStream(true).writeObject(smdbPropFile);
                        return;
                    }
                    catch (IOException ex24) {
                        throw new MarshalException("error marshalling return", ex24);
                    }
                }
                case 20: {
                    remoteCall.releaseInputStream();
                    final String ubPropFile = adminServer.getUbPropFile();
                    try {
                        remoteCall.getResultStream(true).writeObject(ubPropFile);
                        return;
                    }
                    catch (IOException ex25) {
                        throw new MarshalException("error marshalling return", ex25);
                    }
                }
                case 23: {
                    break Label_1453;
                }
                case 22: {
                    remoteCall.releaseInputStream();
                    final boolean reqUserName = adminServer.isReqUserName();
                    try {
                        remoteCall.getResultStream(true).writeBoolean(reqUserName);
                        return;
                    }
                    catch (IOException ex28) {
                        throw new MarshalException("error marshalling return", ex28);
                    }
                    String s5;
                    IEventListener eventListener;
                    try {
                        final ObjectInput inputStream3 = remoteCall.getInputStream();
                        s5 = (String)inputStream3.readObject();
                        eventListener = (IEventListener)inputStream3.readObject();
                    }
                    catch (IOException ex29) {
                        throw new UnmarshalException("error unmarshalling arguments", ex29);
                    }
                    catch (ClassNotFoundException ex30) {
                        throw new UnmarshalException("error unmarshalling arguments", ex30);
                    }
                    finally {
                        remoteCall.releaseInputStream();
                    }
                    final IFileRef openFile = adminServer.openFile(s5, eventListener);
                    try {
                        remoteCall.getResultStream(true).writeObject(openFile);
                        return;
                    }
                    catch (IOException ex31) {
                        throw new MarshalException("error marshalling return", ex31);
                    }
                }
                case 24: {
                    remoteCall.releaseInputStream();
                    adminServer.ping();
                    try {
                        remoteCall.getResultStream(true);
                        return;
                    }
                    catch (IOException ex27) {
                        throw new MarshalException("error marshalling return", ex27);
                    }
                    break;
                }
                case 21: {
                    remoteCall.releaseInputStream();
                    final String version = adminServer.getVersion();
                    try {
                        remoteCall.getResultStream(true).writeObject(version);
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
        return AdminServer_Skel.operations.clone();
    }
}

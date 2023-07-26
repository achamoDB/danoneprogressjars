// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.log.IFileRef;
import java.io.ObjectInput;
import java.rmi.server.RemoteStub;
import com.progress.common.property.IPropertyManagerRemote;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventBroker;
import java.util.Enumeration;
import com.progress.common.networkevents.IEventListener;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class MSTRemoteObject_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -8456230562858104503L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.String getAdminServerIPAddr()"), new Operation("java.lang.String getAdminSrvrHost()"), new Operation("java.lang.String getAdminSrvrOSName()"), new Operation("java.lang.String getAdminSrvrPort()"), new Operation("java.lang.String getAdminSrvrURL()"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDisplayName()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("java.util.Hashtable getLogFiles(java.lang.String)"), new Operation("java.lang.String getLoginUserInfo()[]"), new Operation("java.lang.String getMMCClientClass()"), new Operation("java.lang.String getPropGroupPath()"), new Operation("java.lang.String getPropertyFilename()"), new Operation("com.progress.common.property.IPropertyManagerRemote getRPM()"), new Operation("java.rmi.server.RemoteStub getRemStub()"), new Operation("com.progress.ubroker.tools.IYodaRMI getRemoteManageObject()"), new Operation("boolean getRscLookedUp(java.lang.String)"), new Operation("java.lang.String getSchemaFilename()"), new Operation("java.lang.String getSchemaPropFnList()[]"), new Operation("java.lang.String getSvcName()"), new Operation("com.progress.common.log.IFileRef openFile(java.lang.String, com.progress.common.networkevents.IEventListener)"), new Operation("void regRscLookedUp(java.lang.String)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -8456230562858104503L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final MSTRemoteObject mstRemoteObject = (MSTRemoteObject)remote;
        Label_0981: {
            while (true) {
                switch (n) {
                    case 0: {
                        remoteCall.releaseInputStream();
                        final String adminServerIPAddr = mstRemoteObject.getAdminServerIPAddr();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminServerIPAddr);
                            return;
                        }
                        catch (IOException ex) {
                            throw new MarshalException("error marshalling return", ex);
                        }
                    }
                    case 1: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrHost = mstRemoteObject.getAdminSrvrHost();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrHost);
                            return;
                        }
                        catch (IOException ex2) {
                            throw new MarshalException("error marshalling return", ex2);
                        }
                    }
                    case 2: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrOSName = mstRemoteObject.getAdminSrvrOSName();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrOSName);
                            return;
                        }
                        catch (IOException ex3) {
                            throw new MarshalException("error marshalling return", ex3);
                        }
                    }
                    case 3: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrPort = mstRemoteObject.getAdminSrvrPort();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrPort);
                            return;
                        }
                        catch (IOException ex4) {
                            throw new MarshalException("error marshalling return", ex4);
                        }
                    }
                    case 4: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrURL = mstRemoteObject.getAdminSrvrURL();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrURL);
                            return;
                        }
                        catch (IOException ex5) {
                            throw new MarshalException("error marshalling return", ex5);
                        }
                    }
                    case 5: {
                        remoteCall.releaseInputStream();
                        final Enumeration children = mstRemoteObject.getChildren();
                        try {
                            remoteCall.getResultStream(true).writeObject(children);
                            return;
                        }
                        catch (IOException ex6) {
                            throw new MarshalException("error marshalling return", ex6);
                        }
                    }
                    case 8: {
                        break Label_0981;
                    }
                    case 6: {
                        remoteCall.releaseInputStream();
                        final String displayName = mstRemoteObject.getDisplayName();
                        try {
                            remoteCall.getResultStream(true).writeObject(displayName);
                            return;
                        }
                        catch (IOException ex7) {
                            throw new MarshalException("error marshalling return", ex7);
                        }
                    }
                    case 7: {
                        remoteCall.releaseInputStream();
                        final IEventBroker eventBroker = mstRemoteObject.getEventBroker();
                        try {
                            remoteCall.getResultStream(true).writeObject(eventBroker);
                            return;
                        }
                        catch (IOException ex8) {
                            throw new MarshalException("error marshalling return", ex8);
                        }
                        String s;
                        try {
                            s = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex9) {
                            throw new UnmarshalException("error unmarshalling arguments", ex9);
                        }
                        catch (ClassNotFoundException ex10) {
                            throw new UnmarshalException("error unmarshalling arguments", ex10);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        final Hashtable logFiles = mstRemoteObject.getLogFiles(s);
                        try {
                            remoteCall.getResultStream(true).writeObject(logFiles);
                            return;
                        }
                        catch (IOException ex11) {
                            throw new MarshalException("error marshalling return", ex11);
                        }
                    }
                    case 9: {
                        remoteCall.releaseInputStream();
                        final String[] loginUserInfo = mstRemoteObject.getLoginUserInfo();
                        try {
                            remoteCall.getResultStream(true).writeObject(loginUserInfo);
                            return;
                        }
                        catch (IOException ex12) {
                            throw new MarshalException("error marshalling return", ex12);
                        }
                    }
                    case 10: {
                        remoteCall.releaseInputStream();
                        final String mmcClientClass = mstRemoteObject.getMMCClientClass();
                        try {
                            remoteCall.getResultStream(true).writeObject(mmcClientClass);
                            return;
                        }
                        catch (IOException ex13) {
                            throw new MarshalException("error marshalling return", ex13);
                        }
                    }
                    case 11: {
                        remoteCall.releaseInputStream();
                        final String propGroupPath = mstRemoteObject.getPropGroupPath();
                        try {
                            remoteCall.getResultStream(true).writeObject(propGroupPath);
                            return;
                        }
                        catch (IOException ex14) {
                            throw new MarshalException("error marshalling return", ex14);
                        }
                    }
                    case 12: {
                        remoteCall.releaseInputStream();
                        final String propertyFilename = mstRemoteObject.getPropertyFilename();
                        try {
                            remoteCall.getResultStream(true).writeObject(propertyFilename);
                            return;
                        }
                        catch (IOException ex15) {
                            throw new MarshalException("error marshalling return", ex15);
                        }
                    }
                    case 13: {
                        remoteCall.releaseInputStream();
                        final IPropertyManagerRemote rpm = mstRemoteObject.getRPM();
                        try {
                            remoteCall.getResultStream(true).writeObject(rpm);
                            return;
                        }
                        catch (IOException ex16) {
                            throw new MarshalException("error marshalling return", ex16);
                        }
                    }
                    case 16: {
                        break Label_0981;
                    }
                    case 14: {
                        remoteCall.releaseInputStream();
                        final RemoteStub remStub = mstRemoteObject.getRemStub();
                        try {
                            remoteCall.getResultStream(true).writeObject(remStub);
                            return;
                        }
                        catch (IOException ex17) {
                            throw new MarshalException("error marshalling return", ex17);
                        }
                    }
                    case 15: {
                        remoteCall.releaseInputStream();
                        final IYodaRMI remoteManageObject = mstRemoteObject.getRemoteManageObject();
                        try {
                            remoteCall.getResultStream(true).writeObject(remoteManageObject);
                            return;
                        }
                        catch (IOException ex18) {
                            throw new MarshalException("error marshalling return", ex18);
                        }
                        String s2;
                        try {
                            s2 = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex19) {
                            throw new UnmarshalException("error unmarshalling arguments", ex19);
                        }
                        catch (ClassNotFoundException ex20) {
                            throw new UnmarshalException("error unmarshalling arguments", ex20);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        final boolean rscLookedUp = mstRemoteObject.getRscLookedUp(s2);
                        try {
                            remoteCall.getResultStream(true).writeBoolean(rscLookedUp);
                            return;
                        }
                        catch (IOException ex21) {
                            throw new MarshalException("error marshalling return", ex21);
                        }
                    }
                    case 18: {
                        remoteCall.releaseInputStream();
                        final String[] schemaPropFnList = mstRemoteObject.getSchemaPropFnList();
                        try {
                            remoteCall.getResultStream(true).writeObject(schemaPropFnList);
                            return;
                        }
                        catch (IOException ex26) {
                            throw new MarshalException("error marshalling return", ex26);
                        }
                    }
                    case 17: {
                        remoteCall.releaseInputStream();
                        final String schemaFilename = mstRemoteObject.getSchemaFilename();
                        try {
                            remoteCall.getResultStream(true).writeObject(schemaFilename);
                            return;
                        }
                        catch (IOException ex22) {
                            throw new MarshalException("error marshalling return", ex22);
                        }
                    }
                    case 20: {
                        continue;
                    }
                    case 19: {
                        remoteCall.releaseInputStream();
                        final String svcName = mstRemoteObject.getSvcName();
                        try {
                            remoteCall.getResultStream(true).writeObject(svcName);
                            return;
                        }
                        catch (IOException ex23) {
                            throw new MarshalException("error marshalling return", ex23);
                        }
                        try {
                            final ObjectInput inputStream = remoteCall.getInputStream();
                            final String s3 = (String)inputStream.readObject();
                            final IEventListener eventListener = (IEventListener)inputStream.readObject();
                        }
                        catch (IOException ex24) {
                            throw new UnmarshalException("error unmarshalling arguments", ex24);
                        }
                        catch (ClassNotFoundException ex25) {
                            throw new UnmarshalException("error unmarshalling arguments", ex25);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                    }
                    case 21: {
                        String s4 = null;
                        Label_1405: {
                            break Label_1405;
                            final String s3;
                            final IEventListener eventListener;
                            final IFileRef openFile = mstRemoteObject.openFile(s3, eventListener);
                            try {
                                remoteCall.getResultStream(true).writeObject(openFile);
                                return;
                            }
                            catch (IOException ex27) {
                                throw new MarshalException("error marshalling return", ex27);
                            }
                            try {
                                s4 = (String)remoteCall.getInputStream().readObject();
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
                        }
                        mstRemoteObject.regRscLookedUp(s4);
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex30) {
                            throw new MarshalException("error marshalling return", ex30);
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
        return MSTRemoteObject_Skel.operations.clone();
    }
}

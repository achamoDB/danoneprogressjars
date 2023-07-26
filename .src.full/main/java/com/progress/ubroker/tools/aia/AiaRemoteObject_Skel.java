// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.aia;

import com.progress.ubroker.tools.IYodaRMI;
import java.rmi.server.RemoteStub;
import com.progress.common.property.IPropertyManagerRemote;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventBroker;
import java.util.Enumeration;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import java.rmi.MarshalException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class AiaRemoteObject_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = 7520452742455794744L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.ubroker.util.ToolRemoteCmdStatus doRemoteToolCmd(com.progress.ubroker.util.ToolRemoteCmdDescriptor)"), new Operation("java.lang.String getAdminServerIPAddr()"), new Operation("java.lang.String getAdminSrvrHost()"), new Operation("java.lang.String getAdminSrvrOSName()"), new Operation("java.lang.String getAdminSrvrPort()"), new Operation("java.lang.String getAdminSrvrURL()"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDisplayName()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("java.util.Hashtable getLogFiles(java.lang.String)"), new Operation("java.lang.String getLoginUserInfo()[]"), new Operation("java.lang.String getMMCClientClass()"), new Operation("java.lang.String getPropGroupPath()"), new Operation("java.lang.String getPropertyFilename()"), new Operation("com.progress.common.property.IPropertyManagerRemote getRPM()"), new Operation("java.rmi.server.RemoteStub getRemStub()"), new Operation("com.progress.ubroker.tools.IYodaRMI getRemoteManageObject()"), new Operation("boolean getRscLookedUp(java.lang.String)"), new Operation("java.lang.String getSchemaFilename()"), new Operation("java.lang.String getSchemaPropFnList()[]"), new Operation("java.lang.String getSvcName()"), new Operation("void regRscLookedUp(java.lang.String)") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != 7520452742455794744L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final AiaRemoteObject aiaRemoteObject = (AiaRemoteObject)remote;
        Label_1114: {
            while (true) {
                switch (n) {
                    case 0: {
                        ToolRemoteCmdDescriptor toolRemoteCmdDescriptor;
                        try {
                            toolRemoteCmdDescriptor = (ToolRemoteCmdDescriptor)remoteCall.getInputStream().readObject();
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
                        final ToolRemoteCmdStatus doRemoteToolCmd = aiaRemoteObject.doRemoteToolCmd(toolRemoteCmdDescriptor);
                        try {
                            remoteCall.getResultStream(true).writeObject(doRemoteToolCmd);
                            return;
                        }
                        catch (IOException ex3) {
                            throw new MarshalException("error marshalling return", ex3);
                        }
                    }
                    case 1: {
                        remoteCall.releaseInputStream();
                        final String adminServerIPAddr = aiaRemoteObject.getAdminServerIPAddr();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminServerIPAddr);
                            return;
                        }
                        catch (IOException ex4) {
                            throw new MarshalException("error marshalling return", ex4);
                        }
                    }
                    case 2: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrHost = aiaRemoteObject.getAdminSrvrHost();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrHost);
                            return;
                        }
                        catch (IOException ex5) {
                            throw new MarshalException("error marshalling return", ex5);
                        }
                    }
                    case 3: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrOSName = aiaRemoteObject.getAdminSrvrOSName();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrOSName);
                            return;
                        }
                        catch (IOException ex6) {
                            throw new MarshalException("error marshalling return", ex6);
                        }
                    }
                    case 4: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrPort = aiaRemoteObject.getAdminSrvrPort();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrPort);
                            return;
                        }
                        catch (IOException ex7) {
                            throw new MarshalException("error marshalling return", ex7);
                        }
                    }
                    case 5: {
                        remoteCall.releaseInputStream();
                        final String adminSrvrURL = aiaRemoteObject.getAdminSrvrURL();
                        try {
                            remoteCall.getResultStream(true).writeObject(adminSrvrURL);
                            return;
                        }
                        catch (IOException ex8) {
                            throw new MarshalException("error marshalling return", ex8);
                        }
                    }
                    case 6: {
                        remoteCall.releaseInputStream();
                        final Enumeration children = aiaRemoteObject.getChildren();
                        try {
                            remoteCall.getResultStream(true).writeObject(children);
                            return;
                        }
                        catch (IOException ex9) {
                            throw new MarshalException("error marshalling return", ex9);
                        }
                    }
                    case 9: {
                        break Label_1114;
                    }
                    case 7: {
                        remoteCall.releaseInputStream();
                        final String displayName = aiaRemoteObject.getDisplayName();
                        try {
                            remoteCall.getResultStream(true).writeObject(displayName);
                            return;
                        }
                        catch (IOException ex10) {
                            throw new MarshalException("error marshalling return", ex10);
                        }
                    }
                    case 8: {
                        remoteCall.releaseInputStream();
                        final IEventBroker eventBroker = aiaRemoteObject.getEventBroker();
                        try {
                            remoteCall.getResultStream(true).writeObject(eventBroker);
                            return;
                        }
                        catch (IOException ex11) {
                            throw new MarshalException("error marshalling return", ex11);
                        }
                        String s;
                        try {
                            s = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex12) {
                            throw new UnmarshalException("error unmarshalling arguments", ex12);
                        }
                        catch (ClassNotFoundException ex13) {
                            throw new UnmarshalException("error unmarshalling arguments", ex13);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        final Hashtable logFiles = aiaRemoteObject.getLogFiles(s);
                        try {
                            remoteCall.getResultStream(true).writeObject(logFiles);
                            return;
                        }
                        catch (IOException ex14) {
                            throw new MarshalException("error marshalling return", ex14);
                        }
                    }
                    case 10: {
                        remoteCall.releaseInputStream();
                        final String[] loginUserInfo = aiaRemoteObject.getLoginUserInfo();
                        try {
                            remoteCall.getResultStream(true).writeObject(loginUserInfo);
                            return;
                        }
                        catch (IOException ex15) {
                            throw new MarshalException("error marshalling return", ex15);
                        }
                    }
                    case 11: {
                        remoteCall.releaseInputStream();
                        final String mmcClientClass = aiaRemoteObject.getMMCClientClass();
                        try {
                            remoteCall.getResultStream(true).writeObject(mmcClientClass);
                            return;
                        }
                        catch (IOException ex16) {
                            throw new MarshalException("error marshalling return", ex16);
                        }
                    }
                    case 12: {
                        remoteCall.releaseInputStream();
                        final String propGroupPath = aiaRemoteObject.getPropGroupPath();
                        try {
                            remoteCall.getResultStream(true).writeObject(propGroupPath);
                            return;
                        }
                        catch (IOException ex17) {
                            throw new MarshalException("error marshalling return", ex17);
                        }
                    }
                    case 13: {
                        remoteCall.releaseInputStream();
                        final String propertyFilename = aiaRemoteObject.getPropertyFilename();
                        try {
                            remoteCall.getResultStream(true).writeObject(propertyFilename);
                            return;
                        }
                        catch (IOException ex18) {
                            throw new MarshalException("error marshalling return", ex18);
                        }
                    }
                    case 14: {
                        remoteCall.releaseInputStream();
                        final IPropertyManagerRemote rpm = aiaRemoteObject.getRPM();
                        try {
                            remoteCall.getResultStream(true).writeObject(rpm);
                            return;
                        }
                        catch (IOException ex19) {
                            throw new MarshalException("error marshalling return", ex19);
                        }
                    }
                    case 17: {
                        break Label_1114;
                    }
                    case 15: {
                        remoteCall.releaseInputStream();
                        final RemoteStub remStub = aiaRemoteObject.getRemStub();
                        try {
                            remoteCall.getResultStream(true).writeObject(remStub);
                            return;
                        }
                        catch (IOException ex20) {
                            throw new MarshalException("error marshalling return", ex20);
                        }
                    }
                    case 16: {
                        remoteCall.releaseInputStream();
                        final IYodaRMI remoteManageObject = aiaRemoteObject.getRemoteManageObject();
                        try {
                            remoteCall.getResultStream(true).writeObject(remoteManageObject);
                            return;
                        }
                        catch (IOException ex21) {
                            throw new MarshalException("error marshalling return", ex21);
                        }
                        String s2;
                        try {
                            s2 = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex22) {
                            throw new UnmarshalException("error unmarshalling arguments", ex22);
                        }
                        catch (ClassNotFoundException ex23) {
                            throw new UnmarshalException("error unmarshalling arguments", ex23);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        final boolean rscLookedUp = aiaRemoteObject.getRscLookedUp(s2);
                        try {
                            remoteCall.getResultStream(true).writeBoolean(rscLookedUp);
                            return;
                        }
                        catch (IOException ex24) {
                            throw new MarshalException("error marshalling return", ex24);
                        }
                    }
                    case 19: {
                        remoteCall.releaseInputStream();
                        final String[] schemaPropFnList = aiaRemoteObject.getSchemaPropFnList();
                        try {
                            remoteCall.getResultStream(true).writeObject(schemaPropFnList);
                            return;
                        }
                        catch (IOException ex30) {
                            throw new MarshalException("error marshalling return", ex30);
                        }
                    }
                    case 20: {
                        remoteCall.releaseInputStream();
                        final String svcName = aiaRemoteObject.getSvcName();
                        try {
                            remoteCall.getResultStream(true).writeObject(svcName);
                            return;
                        }
                        catch (IOException ex26) {
                            throw new MarshalException("error marshalling return", ex26);
                        }
                        String s3;
                        try {
                            s3 = (String)remoteCall.getInputStream().readObject();
                        }
                        catch (IOException ex27) {
                            throw new UnmarshalException("error unmarshalling arguments", ex27);
                        }
                        catch (ClassNotFoundException ex28) {
                            throw new UnmarshalException("error unmarshalling arguments", ex28);
                        }
                        finally {
                            remoteCall.releaseInputStream();
                        }
                        aiaRemoteObject.regRscLookedUp(s3);
                        try {
                            remoteCall.getResultStream(true);
                            return;
                        }
                        catch (IOException ex29) {
                            throw new MarshalException("error marshalling return", ex29);
                        }
                        break;
                    }
                    case 18: {
                        remoteCall.releaseInputStream();
                        final String schemaFilename = aiaRemoteObject.getSchemaFilename();
                        try {
                            remoteCall.getResultStream(true).writeObject(schemaFilename);
                            return;
                        }
                        catch (IOException ex25) {
                            throw new MarshalException("error marshalling return", ex25);
                        }
                    }
                    case 21: {
                        continue;
                    }
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return AiaRemoteObject_Skel.operations.clone();
    }
}

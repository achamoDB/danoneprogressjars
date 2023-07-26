// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.SkeletonMismatchException;
import java.rmi.server.RemoteCall;
import java.rmi.Remote;
import java.rmi.server.Operation;
import java.rmi.server.Skeleton;

public final class AgentPlugIn_Skel implements Skeleton
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7362926278114106377L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.chimera.adminserver.IAdministrationServer getAdminServer()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("com.progress.common.networkevents.IEventStream getEventStream()"), new Operation("com.progress.common.licensemgr.ILicenseMgr getLicenseManager()"), new Operation("java.lang.String getOS()"), new Operation("com.progress.agent.database.IAgentPlugIn getPlugIn()"), new Operation("java.lang.String getPropertyFileName()"), new Operation("com.progress.common.property.IPropertyManagerRemote getPropertyManager()") };
    }
    
    public void dispatch(final Remote remote, final RemoteCall remoteCall, final int n, final long n2) throws Exception {
        if (n2 != -7362926278114106377L) {
            throw new SkeletonMismatchException("interface hash mismatch");
        }
        final AgentPlugIn agentPlugIn = (AgentPlugIn)remote;
        switch (n) {
            case 0: {
                remoteCall.releaseInputStream();
                final IAdministrationServer adminServer = agentPlugIn.getAdminServer();
                try {
                    remoteCall.getResultStream(true).writeObject(adminServer);
                    return;
                }
                catch (IOException ex) {
                    throw new MarshalException("error marshalling return", ex);
                }
            }
            case 1: {
                remoteCall.releaseInputStream();
                final IEventBroker eventBroker = agentPlugIn.getEventBroker();
                try {
                    remoteCall.getResultStream(true).writeObject(eventBroker);
                    return;
                }
                catch (IOException ex2) {
                    throw new MarshalException("error marshalling return", ex2);
                }
            }
            case 2: {
                remoteCall.releaseInputStream();
                final IEventStream eventStream = agentPlugIn.getEventStream();
                try {
                    remoteCall.getResultStream(true).writeObject(eventStream);
                    return;
                }
                catch (IOException ex3) {
                    throw new MarshalException("error marshalling return", ex3);
                }
            }
            case 3: {
                remoteCall.releaseInputStream();
                final ILicenseMgr licenseManager = agentPlugIn.getLicenseManager();
                try {
                    remoteCall.getResultStream(true).writeObject(licenseManager);
                    return;
                }
                catch (IOException ex4) {
                    throw new MarshalException("error marshalling return", ex4);
                }
            }
            case 4: {
                remoteCall.releaseInputStream();
                final String os = agentPlugIn.getOS();
                try {
                    remoteCall.getResultStream(true).writeObject(os);
                    return;
                }
                catch (IOException ex5) {
                    throw new MarshalException("error marshalling return", ex5);
                }
            }
            case 5: {
                remoteCall.releaseInputStream();
                final IAgentPlugIn plugIn = agentPlugIn.getPlugIn();
                try {
                    remoteCall.getResultStream(true).writeObject(plugIn);
                    return;
                }
                catch (IOException ex6) {
                    throw new MarshalException("error marshalling return", ex6);
                }
            }
            case 6: {
                remoteCall.releaseInputStream();
                final String propertyFileName = agentPlugIn.getPropertyFileName();
                try {
                    remoteCall.getResultStream(true).writeObject(propertyFileName);
                    return;
                }
                catch (IOException ex7) {
                    throw new MarshalException("error marshalling return", ex7);
                }
            }
            case 7: {
                remoteCall.releaseInputStream();
                final IPropertyManagerRemote propertyManager = agentPlugIn.getPropertyManager();
                try {
                    remoteCall.getResultStream(true).writeObject(propertyManager);
                    return;
                }
                catch (IOException ex8) {
                    throw new MarshalException("error marshalling return", ex8);
                }
                break;
            }
        }
        throw new UnmarshalException("invalid method number");
    }
    
    public Operation[] getOperations() {
        return AgentPlugIn_Skel.operations.clone();
    }
}

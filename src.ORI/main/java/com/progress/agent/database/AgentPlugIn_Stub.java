// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.chimera.common.IChimeraRemoteObject;
import java.rmi.server.RemoteStub;

public final class AgentPlugIn_Stub extends RemoteStub implements IAgentPlugIn, IChimeraRemoteObject
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7362926278114106377L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.chimera.adminserver.IAdministrationServer getAdminServer()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("com.progress.common.networkevents.IEventStream getEventStream()"), new Operation("com.progress.common.licensemgr.ILicenseMgr getLicenseManager()"), new Operation("java.lang.String getOS()"), new Operation("com.progress.agent.database.IAgentPlugIn getPlugIn()"), new Operation("java.lang.String getPropertyFileName()"), new Operation("com.progress.common.property.IPropertyManagerRemote getPropertyManager()") };
    }
    
    public AgentPlugIn_Stub() {
    }
    
    public AgentPlugIn_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public IAdministrationServer getAdminServer() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 0, -7362926278114106377L);
            super.ref.invoke(call);
            IAdministrationServer administrationServer;
            try {
                administrationServer = (IAdministrationServer)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return administrationServer;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public IEventBroker getEventBroker() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 1, -7362926278114106377L);
            super.ref.invoke(call);
            IEventBroker eventBroker;
            try {
                eventBroker = (IEventBroker)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return eventBroker;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public IEventStream getEventStream() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 2, -7362926278114106377L);
            super.ref.invoke(call);
            IEventStream eventStream;
            try {
                eventStream = (IEventStream)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return eventStream;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public ILicenseMgr getLicenseManager() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 3, -7362926278114106377L);
            super.ref.invoke(call);
            ILicenseMgr licenseMgr;
            try {
                licenseMgr = (ILicenseMgr)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return licenseMgr;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public String getOS() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 4, -7362926278114106377L);
            super.ref.invoke(call);
            String s;
            try {
                s = (String)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return s;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public IAgentPlugIn getPlugIn() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 5, -7362926278114106377L);
            super.ref.invoke(call);
            IAgentPlugIn agentPlugIn;
            try {
                agentPlugIn = (IAgentPlugIn)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return agentPlugIn;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public String getPropertyFileName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 6, -7362926278114106377L);
            super.ref.invoke(call);
            String s;
            try {
                s = (String)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return s;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public IPropertyManagerRemote getPropertyManager() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AgentPlugIn_Stub.operations, 7, -7362926278114106377L);
            super.ref.invoke(call);
            IPropertyManagerRemote propertyManagerRemote;
            try {
                propertyManagerRemote = (IPropertyManagerRemote)call.getInputStream().readObject();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            catch (ClassNotFoundException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return propertyManagerRemote;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
}

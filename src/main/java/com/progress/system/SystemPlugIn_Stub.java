// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.system;

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

public final class SystemPlugIn_Stub extends RemoteStub implements ISystemPlugIn, IChimeraRemoteObject
{
    private static final Operation[] operations;
    private static final long interfaceHash = -3735122454478133727L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.chimera.adminserver.IAdministrationServer getAdminServer()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("com.progress.common.networkevents.IEventStream getEventStream()"), new Operation("com.progress.common.licensemgr.ILicenseMgr getLicenseManager()"), new Operation("java.lang.String getOS()"), new Operation("com.progress.system.ISystemPlugIn getPlugIn()") };
    }
    
    public SystemPlugIn_Stub() {
    }
    
    public SystemPlugIn_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public IAdministrationServer getAdminServer() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, SystemPlugIn_Stub.operations, 0, -3735122454478133727L);
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
            final RemoteCall call = super.ref.newCall(this, SystemPlugIn_Stub.operations, 1, -3735122454478133727L);
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
            final RemoteCall call = super.ref.newCall(this, SystemPlugIn_Stub.operations, 2, -3735122454478133727L);
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
            final RemoteCall call = super.ref.newCall(this, SystemPlugIn_Stub.operations, 3, -3735122454478133727L);
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
            final RemoteCall call = super.ref.newCall(this, SystemPlugIn_Stub.operations, 4, -3735122454478133727L);
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
    
    public ISystemPlugIn getPlugIn() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, SystemPlugIn_Stub.operations, 5, -3735122454478133727L);
            super.ref.invoke(call);
            ISystemPlugIn systemPlugIn;
            try {
                systemPlugIn = (ISystemPlugIn)call.getInputStream().readObject();
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
            return systemPlugIn;
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

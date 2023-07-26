// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.io.DataOutput;
import java.io.ObjectOutput;
import com.progress.common.log.IFileRef;
import com.progress.common.networkevents.IEventListener;
import java.util.Vector;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.networkevents.IEventBroker;
import java.util.Enumeration;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import com.progress.common.log.IRemoteFile;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.util.IVersionable;
import com.progress.common.rmiregistry.IPingable;
import java.rmi.server.RemoteStub;

public final class AdminServer_Stub extends RemoteStub implements IAdminServerConnection, IAdministrationServer, IPingable, IVersionable, IChimeraHierarchy, IRemoteFile, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = 8422141365588997890L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.chimera.adminserver.IAdminServer connect(java.lang.String, java.lang.String)"), new Operation("com.progress.chimera.adminserver.IAdminServer connect(java.lang.String, java.lang.String, boolean)"), new Operation("java.lang.String getAdminPort()"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDbPropFile()"), new Operation("java.lang.String getDbaPropFile()"), new Operation("java.lang.String getDisplayName()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("com.progress.common.networkevents.EventBroker getEventBrokerLocal()"), new Operation("com.progress.common.networkevents.IEventStream getEventStream()"), new Operation("java.lang.String getFathomInitParams()"), new Operation("java.lang.String getFathomPropFile()"), new Operation("java.lang.String getHost()"), new Operation("com.progress.common.licensemgr.ILicenseMgr getLicenseManager()"), new Operation("java.lang.String getMMCClientClass()"), new Operation("java.lang.String getMgmtPropFile()"), new Operation("java.lang.String getOS()"), new Operation("java.lang.String getPort()"), new Operation("java.util.Vector getServerPluginInfo()"), new Operation("java.lang.String getSmdbPropFile()"), new Operation("java.lang.String getUbPropFile()"), new Operation("java.lang.String getVersion()"), new Operation("boolean isReqUserName()"), new Operation("com.progress.common.log.IFileRef openFile(java.lang.String, com.progress.common.networkevents.IEventListener)"), new Operation("void ping()") };
    }
    
    public AdminServer_Stub() {
    }
    
    public AdminServer_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public IAdminServer connect(final String s, final String s2) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 0, 8422141365588997890L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(s2);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            Object outputStream;
            try {
                outputStream = call.getInputStream().readObject();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            catch (ClassNotFoundException ex3) {
                throw new UnmarshalException("error unmarshalling return", ex3);
            }
            finally {
                super.ref.done(call);
            }
            return (IAdminServer)outputStream;
        }
        catch (RuntimeException ex4) {
            throw ex4;
        }
        catch (RemoteException ex5) {
            throw ex5;
        }
        catch (Exception ex6) {
            throw new UnexpectedException("undeclared checked exception", ex6);
        }
    }
    
    public IAdminServer connect(final String s, final String s2, final boolean b) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 1, 8422141365588997890L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(s2);
                ((DataOutput)outputStream).writeBoolean(b);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            Object outputStream;
            try {
                outputStream = call.getInputStream().readObject();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            catch (ClassNotFoundException ex3) {
                throw new UnmarshalException("error unmarshalling return", ex3);
            }
            finally {
                super.ref.done(call);
            }
            return (IAdminServer)outputStream;
        }
        catch (RuntimeException ex4) {
            throw ex4;
        }
        catch (RemoteException ex5) {
            throw ex5;
        }
        catch (Exception ex6) {
            throw new UnexpectedException("undeclared checked exception", ex6);
        }
    }
    
    public String getAdminPort() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 2, 8422141365588997890L);
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
    
    public Enumeration getChildren() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 3, 8422141365588997890L);
            super.ref.invoke(call);
            Enumeration enumeration;
            try {
                enumeration = (Enumeration)call.getInputStream().readObject();
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
            return enumeration;
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
    
    public String getDbPropFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 4, 8422141365588997890L);
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
    
    public String getDbaPropFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 5, 8422141365588997890L);
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
    
    public String getDisplayName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 6, 8422141365588997890L);
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
    
    public IEventBroker getEventBroker() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 7, 8422141365588997890L);
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
    
    public EventBroker getEventBrokerLocal() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 8, 8422141365588997890L);
            super.ref.invoke(call);
            EventBroker eventBroker;
            try {
                eventBroker = (EventBroker)call.getInputStream().readObject();
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
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 9, 8422141365588997890L);
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
    
    public String getFathomInitParams() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 10, 8422141365588997890L);
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
    
    public String getFathomPropFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 11, 8422141365588997890L);
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
    
    public String getHost() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 12, 8422141365588997890L);
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
    
    public ILicenseMgr getLicenseManager() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 13, 8422141365588997890L);
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
    
    public String getMMCClientClass() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 14, 8422141365588997890L);
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
    
    public String getMgmtPropFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 15, 8422141365588997890L);
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
    
    public String getOS() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 16, 8422141365588997890L);
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
    
    public String getPort() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 17, 8422141365588997890L);
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
    
    public Vector getServerPluginInfo() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 18, 8422141365588997890L);
            super.ref.invoke(call);
            Vector vector;
            try {
                vector = (Vector)call.getInputStream().readObject();
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
            return vector;
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
    
    public String getSmdbPropFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 19, 8422141365588997890L);
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
    
    public String getUbPropFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 20, 8422141365588997890L);
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
    
    public String getVersion() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 21, 8422141365588997890L);
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
    
    public boolean isReqUserName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 22, 8422141365588997890L);
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
        }
        catch (RuntimeException ex2) {
            throw ex2;
        }
        catch (RemoteException ex3) {
            throw ex3;
        }
        catch (Exception ex4) {
            throw new UnexpectedException("undeclared checked exception", ex4);
        }
    }
    
    public IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 23, 8422141365588997890L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(eventListener);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            Object outputStream;
            try {
                outputStream = call.getInputStream().readObject();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            catch (ClassNotFoundException ex3) {
                throw new UnmarshalException("error unmarshalling return", ex3);
            }
            finally {
                super.ref.done(call);
            }
            return (IFileRef)outputStream;
        }
        catch (RuntimeException ex4) {
            throw ex4;
        }
        catch (RemoteException ex5) {
            throw ex5;
        }
        catch (Exception ex6) {
            throw new UnexpectedException("undeclared checked exception", ex6);
        }
    }
    
    public void ping() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServer_Stub.operations, 24, 8422141365588997890L);
            super.ref.invoke(call);
            super.ref.done(call);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (RemoteException ex2) {
            throw ex2;
        }
        catch (Exception ex3) {
            throw new UnexpectedException("undeclared checked exception", ex3);
        }
    }
}

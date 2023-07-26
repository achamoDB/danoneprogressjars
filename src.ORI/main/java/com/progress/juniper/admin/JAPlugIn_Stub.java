// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.io.DataOutput;
import java.io.ObjectOutput;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.licensemgr.ILicenseMgr;
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
import com.progress.chimera.common.IChimeraRemoteObject;
import java.rmi.server.RemoteStub;

public final class JAPlugIn_Stub extends RemoteStub implements IJAPlugIn, IChimeraRemoteObject
{
    private static final Operation[] operations;
    private static final long interfaceHash = 1405696027394241279L;
    
    static {
        operations = new Operation[] { new Operation("boolean childNameUsed(java.lang.String)"), new Operation("com.progress.juniper.admin.IJAHierarchy createChild(java.lang.String, java.lang.Object, java.lang.Object)"), new Operation("com.progress.juniper.admin.IJADatabase createDatabase(java.lang.String, java.lang.String, java.lang.Object)"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.util.Enumeration getChildrenObjects()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.lang.String getDisplayName(boolean)"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("com.progress.juniper.admin.IJARmiComponent getJARmiComponent()"), new Operation("com.progress.common.licensemgr.ILicenseMgr getLicenseManager()"), new Operation("java.lang.String getMMCClientClass()"), new Operation("java.lang.String getOS()"), new Operation("com.progress.juniper.admin.IJAHierarchy getParent()"), new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("java.lang.String getPropFileName()"), new Operation("com.progress.common.property.IPropertyManagerRemote getPropertyManager()"), new Operation("boolean initializationComplete()"), new Operation("boolean isAgentLicensed()"), new Operation("boolean isReplLicensed()"), new Operation("java.lang.String makeNewChildName()"), new Operation("java.rmi.server.RemoteStub remoteStub()") };
    }
    
    public JAPlugIn_Stub() {
    }
    
    public JAPlugIn_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public boolean childNameUsed(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 0, 1405696027394241279L);
            try {
                call.getOutputStream().writeObject(s);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
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
    
    public IJAHierarchy createChild(final String s, final Object o, final Object o2) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 1, 1405696027394241279L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(o);
                ((ObjectOutput)outputStream).writeObject(o2);
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
            return (IJAHierarchy)outputStream;
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
    
    public IJADatabase createDatabase(final String s, final String s2, final Object o) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 2, 1405696027394241279L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(s2);
                ((ObjectOutput)outputStream).writeObject(o);
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
            return (IJADatabase)outputStream;
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
    
    public Enumeration getChildren() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 3, 1405696027394241279L);
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
    
    public Enumeration getChildrenObjects() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 4, 1405696027394241279L);
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
    
    public String getDisplayName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 5, 1405696027394241279L);
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
    
    public String getDisplayName(final boolean b) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 6, 1405696027394241279L);
            try {
                final Object outputStream = call.getOutputStream();
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
            return (String)outputStream;
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
    
    public IEventBroker getEventBroker() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 7, 1405696027394241279L);
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
    
    public IJARmiComponent getJARmiComponent() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 8, 1405696027394241279L);
            super.ref.invoke(call);
            IJARmiComponent ijaRmiComponent;
            try {
                ijaRmiComponent = (IJARmiComponent)call.getInputStream().readObject();
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
            return ijaRmiComponent;
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
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 9, 1405696027394241279L);
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
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 10, 1405696027394241279L);
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
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 11, 1405696027394241279L);
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
    
    public IJAHierarchy getParent() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 12, 1405696027394241279L);
            super.ref.invoke(call);
            IJAHierarchy ijaHierarchy;
            try {
                ijaHierarchy = (IJAHierarchy)call.getInputStream().readObject();
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
            return ijaHierarchy;
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
    
    public IJAPlugIn getPlugIn() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 13, 1405696027394241279L);
            super.ref.invoke(call);
            IJAPlugIn ijaPlugIn;
            try {
                ijaPlugIn = (IJAPlugIn)call.getInputStream().readObject();
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
            return ijaPlugIn;
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
    
    public String getPropFileName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 14, 1405696027394241279L);
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
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 15, 1405696027394241279L);
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
    
    public boolean initializationComplete() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 16, 1405696027394241279L);
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
    
    public boolean isAgentLicensed() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 17, 1405696027394241279L);
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
    
    public boolean isReplLicensed() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 18, 1405696027394241279L);
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
    
    public String makeNewChildName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 19, 1405696027394241279L);
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
    
    public RemoteStub remoteStub() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JAPlugIn_Stub.operations, 20, 1405696027394241279L);
            super.ref.invoke(call);
            RemoteStub remoteStub;
            try {
                remoteStub = (RemoteStub)call.getInputStream().readObject();
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
            return remoteStub;
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

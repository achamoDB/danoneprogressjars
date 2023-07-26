// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.io.ObjectOutput;
import java.rmi.MarshalException;
import java.util.Hashtable;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.chimera.common.IChimeraRemoteObject;
import java.rmi.server.RemoteStub;

public final class JARmiComponent_Stub extends RemoteStub implements IJARmiComponent, IChimeraRemoteObject
{
    private static final Operation[] operations;
    private static final long interfaceHash = -3997381754267381036L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.juniper.admin.IJAPlugIn getPlugIn()"), new Operation("java.util.Hashtable getStatistics(java.lang.String, java.lang.String[])"), new Operation("java.lang.Object getTableSchema(java.lang.String, java.lang.String)[]"), new Operation("java.rmi.server.RemoteStub remoteStub()") };
    }
    
    public JARmiComponent_Stub() {
    }
    
    public JARmiComponent_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public IJAPlugIn getPlugIn() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JARmiComponent_Stub.operations, 0, -3997381754267381036L);
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
    
    public Hashtable getStatistics(final String s, final String[] array) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JARmiComponent_Stub.operations, 1, -3997381754267381036L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(array);
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
            return (Hashtable)outputStream;
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
    
    public Object[] getTableSchema(final String s, final String s2) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JARmiComponent_Stub.operations, 2, -3997381754267381036L);
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
            return (Object[])outputStream;
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
    
    public RemoteStub remoteStub() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, JARmiComponent_Stub.operations, 3, -3997381754267381036L);
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

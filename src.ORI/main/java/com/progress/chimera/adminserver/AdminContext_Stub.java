// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.io.ObjectOutput;
import java.rmi.MarshalException;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import java.util.Vector;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import com.progress.common.rmiregistry.IPingable;
import java.rmi.server.RemoteStub;

public final class AdminContext_Stub extends RemoteStub implements IAdminServer, IPingable, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = -2504202288551725341L;
    
    static {
        operations = new Operation[] { new Operation("java.util.Vector getPlugins()"), new Operation("java.util.Vector getPlugins(java.lang.String)"), new Operation("void ping()"), new Operation("void shutdown()") };
    }
    
    public AdminContext_Stub() {
    }
    
    public AdminContext_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public Vector getPlugins() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminContext_Stub.operations, 0, -2504202288551725341L);
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
    
    public Vector getPlugins(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminContext_Stub.operations, 1, -2504202288551725341L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
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
            return (Vector)outputStream;
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
            final RemoteCall call = super.ref.newCall(this, AdminContext_Stub.operations, 2, -2504202288551725341L);
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
    
    public void shutdown() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminContext_Stub.operations, 3, -2504202288551725341L);
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

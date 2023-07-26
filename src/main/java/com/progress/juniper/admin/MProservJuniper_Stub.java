// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.server.RemoteStub;

public final class MProservJuniper_Stub extends RemoteStub implements IJuniper
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7419295899085538247L;
    
    static {
        operations = new Operation[] { new Operation("void disconnectAdminServer()"), new Operation("void ping()"), new Operation("void setAdminServer(com.progress.juniper.admin.IAdminJuniper)"), new Operation("void shutDown()") };
    }
    
    public MProservJuniper_Stub() {
    }
    
    public MProservJuniper_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public void disconnectAdminServer() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, MProservJuniper_Stub.operations, 0, -7419295899085538247L);
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
    
    public void ping() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, MProservJuniper_Stub.operations, 1, -7419295899085538247L);
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
    
    public void setAdminServer(final IAdminJuniper adminJuniper) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, MProservJuniper_Stub.operations, 2, -7419295899085538247L);
            try {
                call.getOutputStream().writeObject(adminJuniper);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            super.ref.done(call);
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
    
    public void shutDown() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, MProservJuniper_Stub.operations, 3, -7419295899085538247L);
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

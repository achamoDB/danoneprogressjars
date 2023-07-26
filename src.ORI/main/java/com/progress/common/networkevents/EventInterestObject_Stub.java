// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.MarshalException;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import java.rmi.server.RemoteStub;

public final class EventInterestObject_Stub extends RemoteStub implements IEventInterestObject, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = 7179863061515146745L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Class eventType()"), new Operation("com.progress.common.networkevents.IClient getClient()"), new Operation("void revokeInterest()"), new Operation("void revokeInterest(com.progress.common.networkevents.IEventInterestObject)") };
    }
    
    public EventInterestObject_Stub() {
    }
    
    public EventInterestObject_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public Class eventType() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventInterestObject_Stub.operations, 0, 7179863061515146745L);
            super.ref.invoke(call);
            Class clazz;
            try {
                clazz = (Class)call.getInputStream().readObject();
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
            return clazz;
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
    
    public IClient getClient() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventInterestObject_Stub.operations, 1, 7179863061515146745L);
            super.ref.invoke(call);
            IClient client;
            try {
                client = (IClient)call.getInputStream().readObject();
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
            return client;
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
    
    public void revokeInterest() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventInterestObject_Stub.operations, 2, 7179863061515146745L);
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
    
    public void revokeInterest(final IEventInterestObject eventInterestObject) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventInterestObject_Stub.operations, 3, 7179863061515146745L);
            try {
                call.getOutputStream().writeObject(eventInterestObject);
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
}

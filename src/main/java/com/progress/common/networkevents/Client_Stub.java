// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.io.ObjectOutput;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import java.rmi.server.RemoteStub;

public final class Client_Stub extends RemoteStub implements IClient, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = 892188447052064438L;
    
    static {
        operations = new Operation[] { new Operation("void processEvent(com.progress.common.networkevents.IEventObject, com.progress.common.networkevents.IEventListener, java.lang.Object, com.progress.common.networkevents.IClient, boolean)"), new Operation("void terminate()") };
    }
    
    public Client_Stub() {
    }
    
    public Client_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public void processEvent(final IEventObject eventObject, final IEventListener eventListener, final Object o, final IClient client, final boolean b) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, Client_Stub.operations, 0, 892188447052064438L);
            try {
                final ObjectOutput outputStream = call.getOutputStream();
                outputStream.writeObject(eventObject);
                outputStream.writeObject(eventListener);
                outputStream.writeObject(o);
                outputStream.writeObject(client);
                outputStream.writeBoolean(b);
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
    
    public void terminate() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, Client_Stub.operations, 1, 892188447052064438L);
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

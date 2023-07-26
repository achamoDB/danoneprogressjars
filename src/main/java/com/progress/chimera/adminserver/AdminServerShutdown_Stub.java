// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import com.progress.common.networkevents.IEventObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.common.networkevents.IEventListener;
import java.rmi.server.RemoteStub;

public final class AdminServerShutdown_Stub extends RemoteStub implements IEventListener
{
    private static final Operation[] operations;
    private static final long interfaceHash = 9150587618287247166L;
    
    static {
        operations = new Operation[] { new Operation("void processEvent(com.progress.common.networkevents.IEventObject)") };
    }
    
    public AdminServerShutdown_Stub() {
    }
    
    public AdminServerShutdown_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public void processEvent(final IEventObject eventObject) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AdminServerShutdown_Stub.operations, 0, 9150587618287247166L);
            try {
                call.getOutputStream().writeObject(eventObject);
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

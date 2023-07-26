// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

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

public final class RemoteInteger_Stub extends RemoteStub implements IRemoteInteger, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = -5571926337095455531L;
    
    static {
        operations = new Operation[] { new Operation("int get()"), new Operation("void put(int)") };
    }
    
    public RemoteInteger_Stub() {
    }
    
    public RemoteInteger_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public int get() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, RemoteInteger_Stub.operations, 0, -5571926337095455531L);
            super.ref.invoke(call);
            int int1;
            try {
                int1 = call.getInputStream().readInt();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            finally {
                super.ref.done(call);
            }
            return int1;
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
    
    public void put(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, RemoteInteger_Stub.operations, 1, -5571926337095455531L);
            try {
                call.getOutputStream().writeInt(n);
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

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.io.ObjectOutput;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import com.progress.common.networkevents.IEventListener;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.server.RemoteStub;

public final class AbstractLogFileReader_Stub extends RemoteStub implements IRemoteFile
{
    private static final Operation[] operations;
    private static final long interfaceHash = 2549853320296197113L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.common.log.IFileRef openFile(java.lang.String, com.progress.common.networkevents.IEventListener)") };
    }
    
    public AbstractLogFileReader_Stub() {
    }
    
    public AbstractLogFileReader_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, AbstractLogFileReader_Stub.operations, 0, 2549853320296197113L);
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
}

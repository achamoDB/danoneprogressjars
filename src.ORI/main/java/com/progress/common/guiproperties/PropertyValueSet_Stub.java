// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.io.ObjectOutput;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.common.property.IPropertyValueProvider;
import java.rmi.server.RemoteStub;

public final class PropertyValueSet_Stub extends RemoteStub implements IPropertyValueProvider
{
    private static final Operation[] operations;
    private static final long interfaceHash = -8121598313125218002L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Object getValueRemote(java.lang.String)") };
    }
    
    public PropertyValueSet_Stub() {
    }
    
    public PropertyValueSet_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public Object getValueRemote(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, PropertyValueSet_Stub.operations, 0, -8121598313125218002L);
            try {
                final Object o = call.getOutputStream();
                ((ObjectOutput)o).writeObject(s);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            Object o;
            try {
                o = call.getInputStream().readObject();
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
            return o;
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

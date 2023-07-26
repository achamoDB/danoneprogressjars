// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.io.ObjectOutput;
import java.util.Vector;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.server.RemoteStub;

public final class PropertyManager_Stub extends RemoteStub implements IPropertyManagerRemote
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7278256915521254475L;
    
    static {
        operations = new Operation[] { new Operation("boolean groupExists(java.lang.String)"), new Operation("com.progress.common.property.PropertyTransferObject makePropertyTransferObject(java.lang.String)"), new Operation("void restoreDefaults(java.lang.String, com.progress.common.property.IPropertyValueProvider, java.rmi.server.RemoteStub, java.lang.String, java.util.Vector)"), new Operation("int updateFromClient(java.lang.String, com.progress.common.property.IPropertyValueProvider, java.rmi.server.RemoteStub, java.lang.String, java.util.Vector)") };
    }
    
    public PropertyManager_Stub() {
    }
    
    public PropertyManager_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public boolean groupExists(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, PropertyManager_Stub.operations, 0, -7278256915521254475L);
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
    
    public PropertyTransferObject makePropertyTransferObject(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, PropertyManager_Stub.operations, 1, -7278256915521254475L);
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
            return (PropertyTransferObject)outputStream;
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
    
    public void restoreDefaults(final String s, final IPropertyValueProvider propertyValueProvider, final RemoteStub remoteStub, final String s2, final Vector vector) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, PropertyManager_Stub.operations, 2, -7278256915521254475L);
            try {
                final ObjectOutput outputStream = call.getOutputStream();
                outputStream.writeObject(s);
                outputStream.writeObject(propertyValueProvider);
                outputStream.writeObject(remoteStub);
                outputStream.writeObject(s2);
                outputStream.writeObject(vector);
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
    
    public int updateFromClient(final String s, final IPropertyValueProvider propertyValueProvider, final RemoteStub remoteStub, final String s2, final Vector vector) throws PropertyManager.SaveIOException, RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, PropertyManager_Stub.operations, 3, -7278256915521254475L);
            try {
                final ObjectOutput outputStream = call.getOutputStream();
                outputStream.writeObject(s);
                outputStream.writeObject(propertyValueProvider);
                outputStream.writeObject(remoteStub);
                outputStream.writeObject(s2);
                outputStream.writeObject(vector);
            }
            catch (IOException ex) {
                throw new MarshalException("error marshalling arguments", ex);
            }
            super.ref.invoke(call);
            int int1;
            try {
                int1 = call.getInputStream().readInt();
            }
            catch (IOException ex2) {
                throw new UnmarshalException("error unmarshalling return", ex2);
            }
            finally {
                super.ref.done(call);
            }
            return int1;
        }
        catch (RuntimeException ex3) {
            throw ex3;
        }
        catch (RemoteException ex4) {
            throw ex4;
        }
        catch (PropertyManager.SaveIOException ex5) {
            throw ex5;
        }
        catch (Exception ex6) {
            throw new UnexpectedException("undeclared checked exception", ex6);
        }
    }
}

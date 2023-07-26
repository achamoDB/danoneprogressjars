// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import java.io.DataOutput;
import java.io.ObjectOutput;
import java.util.Hashtable;
import java.rmi.MarshalException;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.ubroker.management.IRemoteManager;
import java.rmi.server.RemoteStub;

public final class NameServer_Stub extends RemoteStub implements INSRemote, IRemoteManager
{
    private static final Operation[] operations;
    private static final long interfaceHash = 816107662656773740L;
    
    static {
        operations = new Operation[] { new Operation("int emergencyShutdown()"), new Operation("java.lang.Object getData(java.lang.String[])"), new Operation("com.progress.nameserver.NameServer.AppService getDetailStatus()[]"), new Operation("java.lang.String getStatusFormatted(int)"), new Operation("java.util.Hashtable getStatusStructured(int, java.lang.Object)"), new Operation("java.lang.Object getSummaryStatus()[][]"), new Operation("int invokeCommand(int, java.lang.Object[])"), new Operation("long ping()"), new Operation("int shutDown()") };
    }
    
    public NameServer_Stub() {
    }
    
    public NameServer_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public int emergencyShutdown() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 0, 816107662656773740L);
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
    
    public Object getData(final String[] array) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 1, 816107662656773740L);
            try {
                final Object o = call.getOutputStream();
                ((ObjectOutput)o).writeObject(array);
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
    
    public NameServer.AppService[] getDetailStatus() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 2, 816107662656773740L);
            super.ref.invoke(call);
            NameServer.AppService[] array;
            try {
                array = (NameServer.AppService[])call.getInputStream().readObject();
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
            return array;
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
    
    public String getStatusFormatted(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 3, 816107662656773740L);
            try {
                final Object outputStream = call.getOutputStream();
                ((DataOutput)outputStream).writeInt(n);
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
            return (String)outputStream;
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
    
    public Hashtable getStatusStructured(final int n, final Object o) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 4, 816107662656773740L);
            try {
                final Object outputStream = call.getOutputStream();
                ((DataOutput)outputStream).writeInt(n);
                ((ObjectOutput)outputStream).writeObject(o);
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
    
    public Object[][] getSummaryStatus() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 5, 816107662656773740L);
            super.ref.invoke(call);
            Object[][] array;
            try {
                array = (Object[][])call.getInputStream().readObject();
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
            return array;
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
    
    public int invokeCommand(final int n, final Object[] array) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 6, 816107662656773740L);
            try {
                final ObjectOutput outputStream = call.getOutputStream();
                outputStream.writeInt(n);
                outputStream.writeObject(array);
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
        catch (Exception ex5) {
            throw new UnexpectedException("undeclared checked exception", ex5);
        }
    }
    
    public long ping() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 7, 816107662656773740L);
            super.ref.invoke(call);
            long long1;
            try {
                long1 = call.getInputStream().readLong();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            finally {
                super.ref.done(call);
            }
            return long1;
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
    
    public int shutDown() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, NameServer_Stub.operations, 8, 816107662656773740L);
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
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import java.io.DataOutput;
import java.io.ObjectOutput;
import java.util.Hashtable;
import java.util.Enumeration;
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
import com.progress.ubroker.tools.IUBRemote;
import java.rmi.server.RemoteStub;

public final class ubListenerThread_Stub extends RemoteStub implements IUBRemote, IRemoteManager
{
    private static final Operation[] operations;
    private static final long interfaceHash = 8236540001301514060L;
    
    static {
        operations = new Operation[] { new Operation("int emergencyShutdown()"), new Operation("java.lang.Object getData(java.lang.String[])"), new Operation("java.util.Enumeration getDetailStatusRSFieldLabels()"), new Operation("java.util.Enumeration getStatusArray(int)"), new Operation("java.lang.String getStatusFormatted(int)"), new Operation("java.util.Hashtable getStatusStructured(int, java.lang.Object)"), new Operation("java.util.Enumeration getSummaryStatusRSData()"), new Operation("java.util.Enumeration getSummaryStatusRSFieldLabels()"), new Operation("int invokeCommand(int, java.lang.Object[])"), new Operation("long ping()"), new Operation("int shutDown()"), new Operation("int startServers(int)"), new Operation("boolean stopServer(int)"), new Operation("int trimBy(int)") };
    }
    
    public ubListenerThread_Stub() {
    }
    
    public ubListenerThread_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public int emergencyShutdown() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 0, 8236540001301514060L);
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
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 1, 8236540001301514060L);
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
    
    public Enumeration getDetailStatusRSFieldLabels() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 2, 8236540001301514060L);
            super.ref.invoke(call);
            Enumeration enumeration;
            try {
                enumeration = (Enumeration)call.getInputStream().readObject();
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
            return enumeration;
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
    
    public Enumeration getStatusArray(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 3, 8236540001301514060L);
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
            return (Enumeration)outputStream;
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
    
    public String getStatusFormatted(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 4, 8236540001301514060L);
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
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 5, 8236540001301514060L);
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
    
    public Enumeration getSummaryStatusRSData() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 6, 8236540001301514060L);
            super.ref.invoke(call);
            Enumeration enumeration;
            try {
                enumeration = (Enumeration)call.getInputStream().readObject();
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
            return enumeration;
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
    
    public Enumeration getSummaryStatusRSFieldLabels() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 7, 8236540001301514060L);
            super.ref.invoke(call);
            Enumeration enumeration;
            try {
                enumeration = (Enumeration)call.getInputStream().readObject();
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
            return enumeration;
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
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 8, 8236540001301514060L);
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
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 9, 8236540001301514060L);
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
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 10, 8236540001301514060L);
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
    
    public int startServers(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 11, 8236540001301514060L);
            try {
                call.getOutputStream().writeInt(n);
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
    
    public boolean stopServer(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 12, 8236540001301514060L);
            try {
                call.getOutputStream().writeInt(n);
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
    
    public int trimBy(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, ubListenerThread_Stub.operations, 13, 8236540001301514060L);
            try {
                call.getOutputStream().writeInt(n);
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
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.io.DataOutput;
import java.rmi.MarshalException;
import java.util.Vector;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.io.File;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import java.rmi.server.RemoteStub;

public final class LogFileRef_Stub extends RemoteStub implements IFileRef, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = 1430537160551055871L;
    
    static {
        operations = new Operation[] { new Operation("void closeFile()"), new Operation("java.io.File getFile()"), new Operation("java.util.Vector getFileData(int)"), new Operation("java.lang.String getFileLength()"), new Operation("java.lang.String getFilePathName()"), new Operation("long getLastReadPosition()"), new Operation("com.progress.common.log.Monitor getMonitor()"), new Operation("long getNextReadPosition()"), new Operation("boolean isBackward()"), new Operation("boolean isFoward()"), new Operation("void setBackward()"), new Operation("void setFile(java.io.File)"), new Operation("void setFilter(java.lang.String)"), new Operation("void setForward()"), new Operation("void setLastReadPosition(long)"), new Operation("void setMonitor(com.progress.common.log.Monitor)"), new Operation("void setNextReadPosition(long)") };
    }
    
    public LogFileRef_Stub() {
    }
    
    public LogFileRef_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public void closeFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 0, 1430537160551055871L);
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
    
    public File getFile() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 1, 1430537160551055871L);
            super.ref.invoke(call);
            File file;
            try {
                file = (File)call.getInputStream().readObject();
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
            return file;
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
    
    public Vector getFileData(final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 2, 1430537160551055871L);
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
            return (Vector)outputStream;
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
    
    public String getFileLength() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 3, 1430537160551055871L);
            super.ref.invoke(call);
            String s;
            try {
                s = (String)call.getInputStream().readObject();
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
            return s;
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
    
    public String getFilePathName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 4, 1430537160551055871L);
            super.ref.invoke(call);
            String s;
            try {
                s = (String)call.getInputStream().readObject();
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
            return s;
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
    
    public long getLastReadPosition() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 5, 1430537160551055871L);
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
    
    public Monitor getMonitor() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 6, 1430537160551055871L);
            super.ref.invoke(call);
            Monitor monitor;
            try {
                monitor = (Monitor)call.getInputStream().readObject();
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
            return monitor;
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
    
    public long getNextReadPosition() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 7, 1430537160551055871L);
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
    
    public boolean isBackward() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 8, 1430537160551055871L);
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
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
    
    public boolean isFoward() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 9, 1430537160551055871L);
            super.ref.invoke(call);
            boolean boolean1;
            try {
                boolean1 = call.getInputStream().readBoolean();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            finally {
                super.ref.done(call);
            }
            return boolean1;
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
    
    public void setBackward() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 10, 1430537160551055871L);
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
    
    public void setFile(final File file) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 11, 1430537160551055871L);
            try {
                call.getOutputStream().writeObject(file);
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
    
    public void setFilter(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 12, 1430537160551055871L);
            try {
                call.getOutputStream().writeObject(s);
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
    
    public void setForward() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 13, 1430537160551055871L);
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
    
    public void setLastReadPosition(final long n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 14, 1430537160551055871L);
            try {
                call.getOutputStream().writeLong(n);
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
    
    public void setMonitor(final Monitor monitor) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 15, 1430537160551055871L);
            try {
                call.getOutputStream().writeObject(monitor);
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
    
    public void setNextReadPosition(final long n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, LogFileRef_Stub.operations, 16, 1430537160551055871L);
            try {
                call.getOutputStream().writeLong(n);
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

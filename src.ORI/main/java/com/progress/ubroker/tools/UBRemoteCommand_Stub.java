// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.rmi.MarshalException;
import java.util.Hashtable;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteObject;
import java.util.Enumeration;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.chimera.common.IChimeraRemoteCommand;
import java.rmi.server.RemoteStub;

public final class UBRemoteCommand_Stub extends RemoteStub implements IChimeraRemoteCommand
{
    private static final Operation[] operations;
    private static final long interfaceHash = -2650217210895588855L;
    
    static {
        operations = new Operation[] { new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDisplayName()"), new Operation("java.util.Hashtable getStructuredSystemOutput()"), new Operation("java.lang.String getSystemError()"), new Operation("java.lang.String getSystemOutput()"), new Operation("int runIt(java.lang.String[])"), new Operation("void setSystemInput(java.lang.String)") };
    }
    
    public UBRemoteCommand_Stub() {
    }
    
    public UBRemoteCommand_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public Enumeration getChildren() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, UBRemoteCommand_Stub.operations, 0, -2650217210895588855L);
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
    
    public String getDisplayName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, UBRemoteCommand_Stub.operations, 1, -2650217210895588855L);
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
    
    public Hashtable getStructuredSystemOutput() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, UBRemoteCommand_Stub.operations, 2, -2650217210895588855L);
            super.ref.invoke(call);
            Hashtable hashtable;
            try {
                hashtable = (Hashtable)call.getInputStream().readObject();
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
            return hashtable;
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
    
    public String getSystemError() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, UBRemoteCommand_Stub.operations, 3, -2650217210895588855L);
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
    
    public String getSystemOutput() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, UBRemoteCommand_Stub.operations, 4, -2650217210895588855L);
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
    
    public int runIt(final String[] array) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, UBRemoteCommand_Stub.operations, 5, -2650217210895588855L);
            try {
                call.getOutputStream().writeObject(array);
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
    
    public void setSystemInput(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, UBRemoteCommand_Stub.operations, 6, -2650217210895588855L);
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
}

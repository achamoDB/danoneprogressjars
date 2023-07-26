// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.io.DataOutput;
import java.io.ObjectOutput;
import com.progress.common.rmiregistry.RegistryManager;
import java.rmi.UnmarshalException;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import java.rmi.Remote;
import com.progress.common.rmiregistry.IPingable;
import java.rmi.server.RemoteStub;

public final class EventBroker_Stub extends RemoteStub implements IEventBroker, IEventBrokerDebug, IPingable, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = -7002897034209665987L;
    
    static {
        operations = new Operation[] { new Operation("void closeEventStream(com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject expressInterest(java.lang.Class, com.progress.common.networkevents.IEventListener, com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject expressInterest(java.lang.Class, com.progress.common.networkevents.IEventListener, java.lang.Class, com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject expressInterest(java.lang.Class, com.progress.common.networkevents.IEventListener, java.rmi.server.RemoteStub, com.progress.common.networkevents.IEventStream)"), new Operation("com.progress.common.networkevents.IEventInterestObject locateObject(java.lang.String, com.progress.common.rmiregistry.RegistryManager, int, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject locateObject(java.lang.String, com.progress.common.rmiregistry.RegistryManager, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject monitorObject(com.progress.common.rmiregistry.IPingable, int, int, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject monitorObject(com.progress.common.rmiregistry.IPingable, int, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventInterestObject monitorObject(com.progress.common.rmiregistry.IPingable, com.progress.common.networkevents.IEventListener)"), new Operation("com.progress.common.networkevents.IEventStream openEventStream(java.lang.String)"), new Operation("com.progress.common.networkevents.IEventStream openEventStream(java.lang.String, int)"), new Operation("void ping()"), new Operation("void postEvent(com.progress.common.networkevents.IEventObject)"), new Operation("void printDispatchTableRemote()"), new Operation("void revokeInterest(com.progress.common.networkevents.IEventInterestObject)") };
    }
    
    public EventBroker_Stub() {
    }
    
    public EventBroker_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public void closeEventStream(final IEventStream eventStream) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 0, -7002897034209665987L);
            try {
                call.getOutputStream().writeObject(eventStream);
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
    
    public IEventInterestObject expressInterest(final Class clazz, final IEventListener eventListener, final IEventStream eventStream) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 1, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(clazz);
                ((ObjectOutput)outputStream).writeObject(eventListener);
                ((ObjectOutput)outputStream).writeObject(eventStream);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventInterestObject expressInterest(final Class clazz, final IEventListener eventListener, final Class clazz2, final IEventStream eventStream) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 2, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(clazz);
                ((ObjectOutput)outputStream).writeObject(eventListener);
                ((ObjectOutput)outputStream).writeObject(clazz2);
                ((ObjectOutput)outputStream).writeObject(eventStream);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventInterestObject expressInterest(final Class clazz, final IEventListener eventListener, final RemoteStub remoteStub, final IEventStream eventStream) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 3, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(clazz);
                ((ObjectOutput)outputStream).writeObject(eventListener);
                ((ObjectOutput)outputStream).writeObject(remoteStub);
                ((ObjectOutput)outputStream).writeObject(eventStream);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventInterestObject locateObject(final String s, final RegistryManager registryManager, final int n, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 4, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(registryManager);
                ((DataOutput)outputStream).writeInt(n);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventInterestObject locateObject(final String s, final RegistryManager registryManager, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 5, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
                ((ObjectOutput)outputStream).writeObject(registryManager);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventInterestObject monitorObject(final IPingable pingable, final int n, final int n2, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 6, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(pingable);
                ((DataOutput)outputStream).writeInt(n);
                ((DataOutput)outputStream).writeInt(n2);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventInterestObject monitorObject(final IPingable pingable, final int n, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 7, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(pingable);
                ((DataOutput)outputStream).writeInt(n);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventInterestObject monitorObject(final IPingable pingable, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 8, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(pingable);
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
            return (IEventInterestObject)outputStream;
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
    
    public IEventStream openEventStream(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 9, -7002897034209665987L);
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
            return (IEventStream)outputStream;
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
    
    public IEventStream openEventStream(final String s, final int n) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 10, -7002897034209665987L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(s);
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
            return (IEventStream)outputStream;
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
    
    public void ping() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 11, -7002897034209665987L);
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
    
    public void postEvent(final IEventObject eventObject) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 12, -7002897034209665987L);
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
    
    public void printDispatchTableRemote() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 13, -7002897034209665987L);
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
    
    public void revokeInterest(final IEventInterestObject eventInterestObject) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EventBroker_Stub.operations, 14, -7002897034209665987L);
            try {
                call.getOutputStream().writeObject(eventInterestObject);
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

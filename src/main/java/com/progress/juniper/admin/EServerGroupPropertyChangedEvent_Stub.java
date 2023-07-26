// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Date;
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
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.util.IEClientSpecificEvent;
import java.rmi.server.RemoteStub;

public final class EServerGroupPropertyChangedEvent_Stub extends RemoteStub implements IEServerGroupEvent, IEClientSpecificEvent, IEJAEvent, INotificationEvent, IEventObject, Remote
{
    private static final Operation[] operations;
    private static final long interfaceHash = 592157051784600683L;
    
    static {
        operations = new Operation[] { new Operation("java.lang.Class classDef()"), new Operation("java.lang.String description()"), new Operation("java.lang.String eventTypeString()"), new Operation("boolean expedite()"), new Operation("short getCategory()"), new Operation("java.lang.String getErrorString()"), new Operation("java.lang.Object getEventData()"), new Operation("java.lang.String getNotificationName()"), new Operation("java.lang.String getNotificationType()"), new Operation("java.lang.Object getObject()"), new Operation("int getSeverityLevel()"), new Operation("java.lang.String getSource()"), new Operation("java.lang.String getSubCategory()"), new Operation("java.lang.Object guiID()"), new Operation("java.lang.Object issuer()"), new Operation("java.lang.String issuerName()"), new Operation("com.progress.juniper.admin.IJAService serverGroup()"), new Operation("void setSource(java.lang.String)"), new Operation("java.util.Date timeIssued()") };
    }
    
    public EServerGroupPropertyChangedEvent_Stub() {
    }
    
    public EServerGroupPropertyChangedEvent_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public Class classDef() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 0, 592157051784600683L);
            super.ref.invoke(call);
            Class clazz;
            try {
                clazz = (Class)call.getInputStream().readObject();
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
            return clazz;
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
    
    public String description() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 1, 592157051784600683L);
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
    
    public String eventTypeString() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 2, 592157051784600683L);
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
    
    public boolean expedite() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 3, 592157051784600683L);
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
    
    public short getCategory() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 4, 592157051784600683L);
            super.ref.invoke(call);
            short short1;
            try {
                short1 = call.getInputStream().readShort();
            }
            catch (IOException ex) {
                throw new UnmarshalException("error unmarshalling return", ex);
            }
            finally {
                super.ref.done(call);
            }
            return short1;
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
    
    public String getErrorString() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 5, 592157051784600683L);
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
    
    public Object getEventData() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 6, 592157051784600683L);
            super.ref.invoke(call);
            Object object;
            try {
                object = call.getInputStream().readObject();
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
            return object;
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
    
    public String getNotificationName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 7, 592157051784600683L);
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
    
    public String getNotificationType() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 8, 592157051784600683L);
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
    
    public Object getObject() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 9, 592157051784600683L);
            super.ref.invoke(call);
            Object object;
            try {
                object = call.getInputStream().readObject();
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
            return object;
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
    
    public int getSeverityLevel() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 10, 592157051784600683L);
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
    
    public String getSource() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 11, 592157051784600683L);
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
    
    public String getSubCategory() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 12, 592157051784600683L);
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
    
    public Object guiID() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 13, 592157051784600683L);
            super.ref.invoke(call);
            Object object;
            try {
                object = call.getInputStream().readObject();
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
            return object;
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
    
    public Object issuer() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 14, 592157051784600683L);
            super.ref.invoke(call);
            Object object;
            try {
                object = call.getInputStream().readObject();
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
            return object;
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
    
    public String issuerName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 15, 592157051784600683L);
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
    
    public IJAService serverGroup() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 16, 592157051784600683L);
            super.ref.invoke(call);
            IJAService ijaService;
            try {
                ijaService = (IJAService)call.getInputStream().readObject();
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
            return ijaService;
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
    
    public void setSource(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 17, 592157051784600683L);
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
    
    public Date timeIssued() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, EServerGroupPropertyChangedEvent_Stub.operations, 18, 592157051784600683L);
            super.ref.invoke(call);
            Date date;
            try {
                date = (Date)call.getInputStream().readObject();
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
            return date;
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

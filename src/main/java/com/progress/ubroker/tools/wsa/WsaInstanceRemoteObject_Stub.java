// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import java.io.ObjectOutput;
import com.progress.common.log.IFileRef;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.property.IPropertyManagerRemote;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventBroker;
import java.util.Enumeration;
import java.rmi.server.RemoteCall;
import java.rmi.UnexpectedException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.server.RemoteObject;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import java.rmi.server.RemoteRef;
import java.rmi.server.Operation;
import com.progress.chimera.common.IChimeraRemoteObject;
import com.progress.common.log.IRemoteFile;
import com.progress.ubroker.tools.IYodaSharedResources;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.chimera.common.IChimeraRemoteCommand;
import com.progress.ubroker.tools.IYodaRMI;
import java.rmi.server.RemoteStub;

public final class WsaInstanceRemoteObject_Stub extends RemoteStub implements IYodaRMI, IChimeraRemoteCommand, IChimeraHierarchy, IYodaSharedResources, IRemoteFile, IChimeraRemoteObject
{
    private static final Operation[] operations;
    private static final long interfaceHash = -5093476835408225809L;
    
    static {
        operations = new Operation[] { new Operation("com.progress.ubroker.util.ToolRemoteCmdStatus doRemoteToolCmd(com.progress.ubroker.util.ToolRemoteCmdDescriptor)"), new Operation("java.lang.String getAdminServerIPAddr()"), new Operation("java.lang.String getAdminSrvrHost()"), new Operation("java.lang.String getAdminSrvrOSName()"), new Operation("java.lang.String getAdminSrvrPort()"), new Operation("java.lang.String getAdminSrvrURL()"), new Operation("java.util.Enumeration getChildren()"), new Operation("java.lang.String getDisplayName()"), new Operation("com.progress.common.networkevents.IEventBroker getEventBroker()"), new Operation("java.util.Hashtable getLogFiles(java.lang.String)"), new Operation("java.lang.String getLoginUserInfo()[]"), new Operation("java.lang.String getMMCClientClass()"), new Operation("java.lang.String getPropGroupPath()"), new Operation("java.lang.String getPropertyFilename()"), new Operation("com.progress.common.property.IPropertyManagerRemote getRPM()"), new Operation("java.rmi.server.RemoteStub getRemStub()"), new Operation("com.progress.ubroker.tools.IYodaRMI getRemoteManageObject()"), new Operation("boolean getRscLookedUp(java.lang.String)"), new Operation("java.lang.String getSchemaFilename()"), new Operation("java.lang.String getSchemaPropFnList()[]"), new Operation("java.util.Hashtable getStructuredSystemOutput()"), new Operation("java.lang.String getSvcName()"), new Operation("java.lang.String getSystemError()"), new Operation("java.lang.String getSystemOutput()"), new Operation("com.progress.common.log.IFileRef openFile(java.lang.String, com.progress.common.networkevents.IEventListener)"), new Operation("void regRscLookedUp(java.lang.String)"), new Operation("int runIt(java.lang.String[])"), new Operation("void setSystemInput(java.lang.String)") };
    }
    
    public WsaInstanceRemoteObject_Stub() {
    }
    
    public WsaInstanceRemoteObject_Stub(final RemoteRef ref) {
        super(ref);
    }
    
    public ToolRemoteCmdStatus doRemoteToolCmd(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 0, -5093476835408225809L);
            try {
                final Object outputStream = call.getOutputStream();
                ((ObjectOutput)outputStream).writeObject(toolRemoteCmdDescriptor);
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
            return (ToolRemoteCmdStatus)outputStream;
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
    
    public String getAdminServerIPAddr() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 1, -5093476835408225809L);
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
    
    public String getAdminSrvrHost() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 2, -5093476835408225809L);
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
    
    public String getAdminSrvrOSName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 3, -5093476835408225809L);
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
    
    public String getAdminSrvrPort() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 4, -5093476835408225809L);
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
    
    public String getAdminSrvrURL() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 5, -5093476835408225809L);
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
    
    public Enumeration getChildren() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 6, -5093476835408225809L);
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
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 7, -5093476835408225809L);
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
    
    public IEventBroker getEventBroker() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 8, -5093476835408225809L);
            super.ref.invoke(call);
            IEventBroker eventBroker;
            try {
                eventBroker = (IEventBroker)call.getInputStream().readObject();
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
            return eventBroker;
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
    
    public Hashtable getLogFiles(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 9, -5093476835408225809L);
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
    
    public String[] getLoginUserInfo() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 10, -5093476835408225809L);
            super.ref.invoke(call);
            String[] array;
            try {
                array = (String[])call.getInputStream().readObject();
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
    
    public String getMMCClientClass() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 11, -5093476835408225809L);
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
    
    public String getPropGroupPath() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 12, -5093476835408225809L);
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
    
    public String getPropertyFilename() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 13, -5093476835408225809L);
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
    
    public IPropertyManagerRemote getRPM() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 14, -5093476835408225809L);
            super.ref.invoke(call);
            IPropertyManagerRemote propertyManagerRemote;
            try {
                propertyManagerRemote = (IPropertyManagerRemote)call.getInputStream().readObject();
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
            return propertyManagerRemote;
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
    
    public RemoteStub getRemStub() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 15, -5093476835408225809L);
            super.ref.invoke(call);
            RemoteStub remoteStub;
            try {
                remoteStub = (RemoteStub)call.getInputStream().readObject();
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
            return remoteStub;
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
    
    public IYodaRMI getRemoteManageObject() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 16, -5093476835408225809L);
            super.ref.invoke(call);
            IYodaRMI yodaRMI;
            try {
                yodaRMI = (IYodaRMI)call.getInputStream().readObject();
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
            return yodaRMI;
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
    
    public boolean getRscLookedUp(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 17, -5093476835408225809L);
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
    
    public String getSchemaFilename() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 18, -5093476835408225809L);
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
    
    public String[] getSchemaPropFnList() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 19, -5093476835408225809L);
            super.ref.invoke(call);
            String[] array;
            try {
                array = (String[])call.getInputStream().readObject();
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
    
    public Hashtable getStructuredSystemOutput() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 20, -5093476835408225809L);
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
    
    public String getSvcName() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 21, -5093476835408225809L);
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
    
    public String getSystemError() throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 22, -5093476835408225809L);
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
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 23, -5093476835408225809L);
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
    
    public IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 24, -5093476835408225809L);
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
    
    public void regRscLookedUp(final String s) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 25, -5093476835408225809L);
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
    
    public int runIt(final String[] array) throws RemoteException {
        try {
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 26, -5093476835408225809L);
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
            final RemoteCall call = super.ref.newCall(this, WsaInstanceRemoteObject_Stub.operations, 27, -5093476835408225809L);
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

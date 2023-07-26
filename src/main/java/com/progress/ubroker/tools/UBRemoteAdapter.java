// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.exception.ProException;
import java.util.Hashtable;
import java.util.Enumeration;
import java.rmi.RemoteException;

public class UBRemoteAdapter implements IUBRemote
{
    private static final String METHOD_NOT_IMPLEMENTED = "Method %s is not implemented";
    private long m_startTime;
    
    public UBRemoteAdapter() {
        this.m_startTime = System.currentTimeMillis();
    }
    
    public int shutDown() throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("shutDown"));
    }
    
    public int trimBy(final int n) throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("trimBy"));
    }
    
    public boolean stopServer(final int n) throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("stopServer"));
    }
    
    public int startServers(final int n) throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("startServers"));
    }
    
    public String getStatusFormatted(final int n) throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("getStatusFormatted"));
    }
    
    public Enumeration getStatusArray(final int n) throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("getStatusArray"));
    }
    
    public long ping() throws RemoteException {
        return System.currentTimeMillis() - this.m_startTime;
    }
    
    public Enumeration getSummaryStatusRSFieldLabels() throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("getSummaryStatusRSFieldLabels"));
    }
    
    public Enumeration getSummaryStatusRSData() throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("getSummaryStatusRSData"));
    }
    
    public Enumeration getDetailStatusRSFieldLabels() throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("getDetailStatusRSFieldLabels"));
    }
    
    public int emergencyShutdown() throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("getDetailStatusRSFieldLabels"));
    }
    
    public Hashtable getStatusStructured(final int n, final Object o) throws RemoteException {
        throw new RemoteException("Method not implemented", new MethodNotImplemented("getStatusStructured"));
    }
    
    public static class MethodNotImplemented extends ProException
    {
        private String m_parentObjectException;
        
        public MethodNotImplemented(final String s) {
            super("Method %s is not implemented", new Object[] { s });
        }
    }
}

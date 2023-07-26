// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Hashtable;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IAdminRemote extends Remote
{
    public static final int SHUTDOWN_COMPLETE = 0;
    public static final int SHUTDOWN_PENDING = 1;
    public static final int SHUTDOWN_ABORTED = 2;
    public static final int STATUS_TERSE = 0;
    public static final int STATUS_VERBOSE = 1;
    public static final int STATUS_DEBUG = 2;
    public static final int ES_COMPLETED = 0;
    public static final int ES_SVC_NOT_ACTIVE = 1;
    public static final int CLIENTSTATUS_SUMMARY = 0;
    public static final int CLIENTSTATUS_DETAIL = 1;
    public static final int CLIENTSTATUS_DEBUG = 2;
    public static final int CLIENTSTATUS_PROPNAME = 3;
    public static final int CLIENTSTATUS_PROPVAL = 4;
    
    int shutDown() throws RemoteException;
    
    int emergencyShutdown() throws RemoteException;
    
    String getStatusFormatted(final int p0) throws RemoteException;
    
    long ping() throws RemoteException;
    
    Hashtable getStatusStructured(final int p0, final Object p1) throws RemoteException;
}

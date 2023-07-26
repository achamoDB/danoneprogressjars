// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Enumeration;
import java.rmi.RemoteException;

public interface IUBRemote extends IAdminRemote
{
    public static final int UB_NAME = 0;
    public static final int UB_MODE = 1;
    public static final int UB_STATE = 2;
    public static final int UB_PORT_NO = 3;
    public static final int UB_PID = 4;
    public static final int NUM_ACTIVE_SRVRS = 5;
    public static final int NUM_BUSY_SRVRS = 6;
    public static final int NUM_LOCKED_SRVRS = 7;
    public static final int NUM_FREE_SRVRS = 8;
    public static final int NUM_CLIENTS = 9;
    public static final int CLIENTWAITQUEUE = 10;
    public static final int NUM_RQS = 11;
    public static final int RQWAIT = 12;
    public static final int RQDURATION = 13;
    public static final int MAX_SUMMARY_STATUS_DATA = 14;
    public static final int DSIDX_SRVR_PID = 0;
    public static final int DSIDX_SRVR_STATE = 1;
    public static final int DSIDX_SRVR_PORT = 2;
    public static final int DSIDX_SRVR_NRQS = 3;
    public static final int DSIDX_SRVR_NRQMSGS = 4;
    public static final int DSIDX_SRVR_NRSPMSGS = 5;
    public static final int DSIDX_SRVR_TS_START = 6;
    public static final int DSIDX_SRVR_TS_STATECHG = 7;
    public static final int MAX_DETAIL_STATUS_DATA = 8;
    public static final int IUBREMOTE_ERC_OK = 0;
    public static final int IUBREMOTE_ERC_ERROR = 1;
    public static final int IUBREMOTE_ERC_PROTOCOL_ERROR = 2;
    public static final int IUBREMOTE_ERC_UNSUPPORTED_RQ = 3;
    public static final int IUBREMOTE_ERC_IOEXCEPTION = 4;
    public static final int IUBREMOTE_ERC_BROKEREXCEPTION = 5;
    public static final int IUBREMOTE_ERC_SERVERIPCEXCEPTION = 6;
    public static final int IUBREMOTE_ERC_NO_AVAILABLE_SERVERS = 7;
    public static final int IUBREMOTE_ERC_MSGFORMATEXCEPTION = 8;
    
    int trimBy(final int p0) throws RemoteException;
    
    boolean stopServer(final int p0) throws RemoteException;
    
    int startServers(final int p0) throws RemoteException;
    
    Enumeration getDetailStatusRSFieldLabels() throws RemoteException;
    
    Enumeration getStatusArray(final int p0) throws RemoteException;
    
    Enumeration getSummaryStatusRSFieldLabels() throws RemoteException;
    
    Enumeration getSummaryStatusRSData() throws RemoteException;
}

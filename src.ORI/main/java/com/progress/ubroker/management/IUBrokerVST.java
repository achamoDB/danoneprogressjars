// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

public interface IUBrokerVST
{
    public static final String UB_NAME = "Broker Name";
    public static final String UB_MODE = "Operating Mode";
    public static final String UB_STATE = "Broker Status";
    public static final String UB_PORT_NO = "Broker Port";
    public static final String UB_PID = "Broker PID";
    public static final String NUM_ACTIVE_AGENTS = "Active Agents";
    public static final String NUM_BUSY_AGENTS = "Busy Agents";
    public static final String NUM_LOCKED_AGENTS = "Locked Agents";
    public static final String NUM_FREE_AGENTS = "Available Agents";
    public static final String NUM_ACTIVE_SRVRS = "Active Servers";
    public static final String NUM_BUSY_SRVRS = "Busy Servers";
    public static final String NUM_LOCKED_SRVRS = "Locked Servers";
    public static final String NUM_FREE_SRVRS = "Available Servers";
    public static final String NUM_CLIENTS = "Active Clients (now, peak)";
    public static final String CLIENTWAITQUEUE = "Client Queue Depth (cur, max)";
    public static final String NUM_RQS = "Total Requests";
    public static final String RQWAIT = "Rq Wait (max, avg)";
    public static final String RQDURATION = "Rq Duration (max, avg)";
    public static final String DETAIL_SRVR_STATE = "State";
    public static final String DETAIL_SRVR_PORT = "Port";
    public static final String DETAIL_SRVR_NRQS = "nRq";
    public static final String DETAIL_SRVR_NRQMSGS = "nRcvd";
    public static final String DETAIL_SRVR_NRSPMSGS = "nSent";
    public static final String DETAIL_SRVR_TS_START = "Started";
    public static final String DETAIL_SRVR_TS_STATECHG = "Last Change";
}

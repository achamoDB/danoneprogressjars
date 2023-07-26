// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

public interface IRemoteManagerConst
{
    public static final int CMD_MIN = 1;
    public static final int CMD_SHUTDOWN = 2;
    public static final int CMD_ADD_AGENTS = 3;
    public static final int CMD_TRIM_AGENT = 4;
    public static final int CMD_TRIM_MULTI_AGENTS = 5;
    public static final int CMD_KILL_AGENT = 6;
    public static final int CMD_GET_COLLECT_STATE = 7;
    public static final int CMD_MAX = 99;
    public static final String ACT_AGENT = "actAgent";
    public static final String ACT_CLIENT = "actClient";
    public static final String ACT_PROC_AS = "actProcAS";
    public static final String ACT_PROC_WS = "actProcWS";
    public static final String ACT_REQ = "actReq";
    public static final String DETAIL_STATUS = "detailStatus";
    public static final String SUMMARY_STATUS = "summaryStatus";
    public static final String NS_CONN = "nameServerConnection";
    public static final String REGISTER_NS = "registerNameServer";
    public static final String COLLECT_STAT = "collectStatsData";
    public static final String BROKER_LOGFILE = "brokerLogFile";
    public static final String SERVER_LOGFILE = "srvrLogFile";
    public static final String LSTCON_TABLENAME = "table name";
    public static final String LSTCON_NUMCONN = "num connections";
    public static final String LSTCON_TABLEDATA = "table data";
    public static final String LSTCON_SUMMARYSTATUS = "client summary";
    public static final String LSTCON_CONNHDL = "conn hdl";
    public static final String LSTCON_USERNAME = "user name";
    public static final String LSTCON_RMTADDR = "remote addr";
    public static final String LSTCON_RMTPORT = "remote port";
    public static final String LSTCON_CONNSTATE = "connection state";
    public static final String LSTCON_DETAILSTATUS = "client detail";
    public static final String LSTCON_CONNID = "conn ID";
    public static final String LSTCON_RQCNT = "request count";
    public static final String LSTCON_AGENTPID = "agent PID";
    public static final String LSTCON_AGENTPORT = "agent port";
}

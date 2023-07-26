// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

public interface IServerFSM
{
    public static final byte STATE_IDLE = 0;
    public static final byte STATE_READY = 1;
    public static final byte STATE_CONNECTING = 2;
    public static final byte STATE_CONNECTED = 3;
    public static final byte STATE_INIT_RQ = 4;
    public static final byte STATE_RECVINPUT = 5;
    public static final byte STATE_SENDOUTPUT = 6;
    public static final byte STATE_FINISH_RQ = 7;
    public static final byte STATE_STOPPING = 8;
    public static final byte STATE_BUSY = 9;
    public static final byte STATE_BOUND = 10;
    public static final byte STATE_OBLIVION = 11;
    public static final byte STATE_NOOP = 12;
    public static final String[] DESC_STATE_EXT = { "STARTING ", "AVAILABLE", "CONNECTNG", "CONNECTED", "START RQ ", "RECEIVING", "SENDING  ", "ENDING RQ", "STOPPING ", "BUSY     ", "LOCKED   ", "ENDING   ", "NOOP     " };
    public static final String[] DESC_STATE = { " STATE_IDLE ", " STATE_READY ", " STATE_CONNECTING ", " STATE_CONNECTED ", " STATE_INIT_RQ ", " STATE_RECVINPUT ", " STATE_SENDOUTPUT ", " STATE_FINISH_RQ ", " STATE_STOPPING ", " STATE_BUSY ", " STATE_BOUND ", " STATE_OBLIVION ", " STATE_NOOP " };
    public static final byte EVENT_NOOP = 0;
    public static final byte EVENT_STARTUP = 1;
    public static final byte EVENT_CONNECT = 2;
    public static final byte EVENT_INIT_RQ = 3;
    public static final byte EVENT_WRITE = 4;
    public static final byte EVENT_WRITELAST = 5;
    public static final byte EVENT_RSP = 6;
    public static final byte EVENT_RSP_LAST = 7;
    public static final byte EVENT_RSP_CONNECT = 8;
    public static final byte EVENT_RSP_DISCONNECT = 9;
    public static final byte EVENT_FINISH_RQ = 10;
    public static final byte EVENT_DISCONNECT = 11;
    public static final byte EVENT_SHUTDOWN = 12;
    public static final byte EVENT_TERMINATE = 13;
    public static final byte EVENT_STOP = 14;
    public static final byte EVENT_ERROR = 15;
    public static final byte EVENT_SERVER_IDLE = 16;
    public static final byte EVENT_SERVER_BUSY = 17;
    public static final byte EVENT_SERVER_BOUND = 18;
    public static final byte EVENT_SERVER_LOG_MSG = 19;
    public static final byte EVENT_TIMEOUT = 20;
    public static final byte EVENT_PROCSTATS = 21;
    public static final byte EVENT_CLIENT_CONNECT = 22;
    public static final byte EVENT_ADMIN = 23;
    public static final byte EVENT_RSP_ADMIN = 24;
    public static final String[] DESC_EVENT = { " EVENT_NOOP ", " EVENT_STARTUP ", " EVENT_CONNECT ", " EVENT_INIT_RQ ", " EVENT_WRITE ", " EVENT_WRITELAST ", " EVENT_RSP ", " EVENT_RSP_LAST ", " EVENT_RSP_CONNECT ", " EVENT_RSP_DISCONNECT ", " EVENT_FINISH_RQ ", " EVENT_DISCONNECT ", " EVENT_SHUTDOWN ", " EVENT_TERMINATE ", " EVENT_STOP ", " EVENT_ERROR ", " EVENT_SERVER_IDLE ", " EVENT_SERVER_BUSY ", " EVENT_SERVER_BOUND ", " EVENT_SERVER_LOG_MSG ", " EVENT_TIMEOUT ", " EVENT_PROCSTATS ", " EVENT_CLIENT_CONNECT ", " EVENT_ADMIN ", " EVENT_RSP_ADMIN " };
    public static final byte ACTION_IGNORE = 0;
    public static final byte ACTION_STARTUP = 1;
    public static final byte ACTION_XID = 2;
    public static final byte ACTION_CONNECT = 3;
    public static final byte ACTION_INIT_RQ = 4;
    public static final byte ACTION_WRITE = 5;
    public static final byte ACTION_WRITE_LAST = 6;
    public static final byte ACTION_READ = 7;
    public static final byte ACTION_FINISH_RQ = 8;
    public static final byte ACTION_DISCONNECT = 9;
    public static final byte ACTION_SHUTDOWN = 10;
    public static final byte ACTION_TERMINATE = 11;
    public static final byte ACTION_ERROR = 12;
    public static final byte ACTION_FATAL_ERROR = 13;
    public static final byte ACTION_STOP = 14;
    public static final byte ACTION_LOG_MSG = 15;
    public static final byte ACTION_UPD_SRVR_STATE = 16;
    public static final byte ACTION_CONNECT_TIMEOUT = 17;
    public static final byte ACTION_PROCSTATS = 18;
    public static final byte ACTION_CLIENT_CONNECTED = 19;
    public static final byte ACTION_INV_STATE = 20;
    public static final byte ACTION_ADMIN = 21;
    public static final byte ACTION_ADMIN_RSP = 22;
    public static final String[] DESC_ACTION = { " ACTION_IGNORE ", " ACTION_STARTUP ", " ACTION_XID ", " ACTION_CONNECT ", " ACTION_INIT_RQ ", " ACTION_WRITE ", " ACTION_WRITE_LAST ", " ACTION_READ ", " ACTION_FINISH_RQ ", " ACTION_DISCONNECT ", " ACTION_SHUTDOWN ", " ACTION_TERMINATE ", " ACTION_ERROR ", " ACTION_FATAL_ERROR ", " ACTION_STOP ", " ACTION_LOG_MSG ", " ACTION_UPD_SRVR_STATE ", " ACTION_CONNECT_TIMEOUT ", " ACTION_PROCSTATS ", " ACTION_CLIENT_CONNECTED ", " ACTION_INV_STATE ", " ACTION_ADMIN ", " ACTION_ADMIN_RSP " };
    public static final int OFST_ACTION = 0;
    public static final int OFST_NEXTSTATE = 1;
}

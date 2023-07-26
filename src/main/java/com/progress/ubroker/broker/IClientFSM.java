// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

public interface IClientFSM
{
    public static final byte STATE_IDLE = 0;
    public static final byte STATE_READY = 1;
    public static final byte STATE_CONNECTED = 2;
    public static final byte STATE_WRITE_DIRECT = 3;
    public static final byte STATE_READ = 4;
    public static final byte STATE_STOP = 5;
    public static final byte STATE_CONN_RSP = 6;
    public static final byte STATE_DISC_RSP = 7;
    public static final byte STATE_FINISH_RQ = 8;
    public static final byte STATE_FLUSH_INPUT_NSA = 9;
    public static final byte STATE_OBLIVION = 10;
    public static final String[] DESC_STATE = { " STATE_IDLE ", " STATE_READY ", " STATE_CONNECTED ", " STATE_WRITE_DIRECT ", " STATE_READ ", " STATE_STOP ", " STATE_CONNECT_RSP ", " STATE_DISCONNECT_RSP ", " STATE_FINISH_RQ ", " STATE_FLUSH_INPUT_NSA ", " STATE_OBLIVION " };
    public static final byte EVENT_CONNECT = 0;
    public static final byte EVENT_CONNECT_DIRECT = 1;
    public static final byte EVENT_WRITE = 2;
    public static final byte EVENT_WRITELAST = 3;
    public static final byte EVENT_RSP = 4;
    public static final byte EVENT_RSPLAST = 5;
    public static final byte EVENT_STOP = 6;
    public static final byte EVENT_DISCONNECT = 7;
    public static final byte EVENT_ERROR = 8;
    public static final byte EVENT_STARTUP = 9;
    public static final byte EVENT_SHUTDOWN = 10;
    public static final byte EVENT_IO_EXCEPTION = 11;
    public static final byte EVENT_RSPCONN = 12;
    public static final byte EVENT_RSPDISC = 13;
    public static final byte EVENT_FINISH_RQ = 14;
    public static final byte EVENT_BROKER_STATUS = 15;
    public static final byte EVENT_XID = 16;
    public static final byte EVENT_ASK_ACT_TIMEOUT = 17;
    public static final byte EVENT_ASK_RSP_TIMEOUT = 18;
    public static final byte EVENT_ASKPING_RQ = 19;
    public static final byte EVENT_ASKPING_RSP = 20;
    public static final byte EVENT_SERVER_TERMINATE = 21;
    public static final String[] DESC_EVENT = { " EVENT_CONNECT ", " EVENT_CONNECT_DIRECT ", " EVENT_WRITE ", " EVENT_WRITELAST ", " EVENT_RSP ", " EVENT_RSPLAST ", " EVENT_STOP ", " EVENT_DISCONNECT ", " EVENT_ERROR ", " EVENT_STARTUP ", " EVENT_SHUTDOWN ", " EVENT_IO_EXCEPTION ", " EVENT_RSPCONN ", " EVENT_RSPDISC ", " EVENT_FINISH_RQ ", " EVENT_BROKER_STATUS ", " EVENT_XID ", " EVENT_ASK_ACT_TIMEOUT ", " EVENT_ASK_RSP_TIMEOUT ", " EVENT_ASKPING_RQ ", " EVENT_ASKPING_RSP ", " EVENT_SERVER_TERMINATE " };
    public static final byte ACTION_XID = 0;
    public static final byte ACTION_CONNECT = 1;
    public static final byte ACTION_CONNECT_DIRECT = 2;
    public static final byte ACTION_ENQUEUE_ANY = 3;
    public static final byte ACTION_ENQUEUE_DIRECT = 4;
    public static final byte ACTION_DEQUEUE = 5;
    public static final byte ACTION_DEQUEUE_LAST = 6;
    public static final byte ACTION_STOP = 7;
    public static final byte ACTION_DISCONNECT = 8;
    public static final byte ACTION_ERROR = 9;
    public static final byte ACTION_INV_STATE = 10;
    public static final byte ACTION_STARTUP = 11;
    public static final byte ACTION_SHUTDOWN = 12;
    public static final byte ACTION_IO_EXCEPTION = 13;
    public static final byte ACTION_CONN_RSP = 14;
    public static final byte ACTION_DISC_RSP = 15;
    public static final byte ACTION_FINISH_RQ = 16;
    public static final byte ACTION_IGNORE = 17;
    public static final byte ACTION_DEFER_SHUTDOWN = 18;
    public static final byte ACTION_DEFER_SHUTDOWN_DSC = 19;
    public static final byte ACTION_SHUTDOWN_WRITE = 20;
    public static final byte ACTION_SHUTDOWN_READ = 21;
    public static final byte ACTION_DEFER_ABEND = 22;
    public static final byte ACTION_DEFER_ABEND_DSC = 23;
    public static final byte ACTION_ABEND_WRITE = 24;
    public static final byte ACTION_ABEND_READ = 25;
    public static final byte ACTION_BROKER_STATUS = 26;
    public static final byte ACTION_CONNECT_ERROR = 27;
    public static final byte ACTION_ASK_ACT_TIMEOUT = 28;
    public static final byte ACTION_ASK_RSP_TIMEOUT = 29;
    public static final byte ACTION_ASKPING_RQ = 30;
    public static final byte ACTION_ASKPING_RSP = 31;
    public static final byte ACTION_NONFATAL_ERROR = 32;
    public static final byte ACTION_SERVER_TERMINATE = 33;
    public static final String[] DESC_ACTION = { " ACTION_XID ", " ACTION_CONNECT ", " ACTION_CONNECT_DIRECT ", " ACTION_ENQUEUE_ANY ", " ACTION_ENQUEUE_DIRECT ", " ACTION_DEQUEUE ", " ACTION_DEQUEUE_LAST ", " ACTION_STOP ", " ACTION_DISCONNECT ", " ACTION_ERROR ", " ACTION_INV_STATE ", " ACTION_STARTUP ", " ACTION_SHUTDOWN ", " ACTION_IO_EXCEPTION ", " ACTION_CONN_RSP ", " ACTION_DISC_RSP ", " ACTION_FINISH_RQ ", " ACTION_IGNORE ", " ACTION_DEFER_SHUTDOWN ", " ACTION_DEFER_SHUTDOWN_DSC ", " ACTION_SHUTDOWN_WRITE ", " ACTION_SHUTDOWN_READ ", " ACTION_DEFER_ABEND ", " ACTION_DEFER_ABEND_DSC ", " ACTION_ABEND_WRITE ", " ACTION_ABEND_READ ", " ACTION_BROKER_STATUS ", " ACTION_CONNECT_ERROR ", " ACTION_ASK_ACT_TIMEOUT ", " ACTION_ASK_RSP_TIMEOUT ", " ACTION_ASKPING_RQ ", " ACTION_ASKPING_RSP ", " ACTION_NONFATAL_ERROR ", " ACTION_SERVER_TERMINATE " };
}

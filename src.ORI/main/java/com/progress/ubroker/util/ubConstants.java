// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

public interface ubConstants
{
    public static final boolean DEBUG = true;
    public static final int SERVICE_PRIORITY = 6;
    public static final int CLIENT_THREAD_PRIORITY = 5;
    public static final int SERVER_THREAD_PRIORITY = 5;
    public static final String WIRECODEPAGE = "UTF8";
    public static final String PROGRESS_WIRECODEPAGE = "utf-8";
    public static final String LOCALHOST = "localhost";
    public static final int SERVERTYPE_APPSERVER = 0;
    public static final int SERVERTYPE_WEBSPEED = 1;
    public static final int SERVERTYPE_DATASERVER_OD = 2;
    public static final int SERVERTYPE_DATASERVER_OR = 3;
    public static final int SERVERTYPE_ADAPTER = 4;
    public static final int SERVERTYPE_DATASERVER_MSS = 5;
    public static final int SERVERTYPE_ADAPTER_CC = 6;
    public static final int SERVERTYPE_ADAPTER_SC = 7;
    public static final int SERVERMODE_STATELESS = 0;
    public static final int SERVERMODE_STATE_AWARE = 1;
    public static final int SERVERMODE_STATE_RESET = 2;
    public static final int SERVERMODE_STATE_FREE = 3;
    public static final int REGMODE_IP = 0;
    public static final int REGMODE_LOCALHOST = 1;
    public static final int REGMODE_HOSTNAME = 2;
    public static final int SELECTIONSCHEME_FIFO = 0;
    public static final int SELECTIONSCHEME_LIFO = 1;
    public static final int SELECTIONSCHEME_AFFINITY = 2;
    public static final String[] STRING_SERVER_TYPES = { "AS", "WS", "OD", "OR", "AD", "MS", "CC", "SC" };
    public static final int MSG_INPUT_STREAM_BUFSIZE = 10240;
    public static final int MSG_OUTPUT_STREAM_BUFSIZE = 1024;
    public static final int MSG_MAX_BUFSIZE = 8192;
    public static final int RQ_CLOSE_OK = 1;
    public static final int RQ_CLOSE_ABEND = 2;
    public static final int RQ_CLOSE_STOP = 3;
    public static final long INVALID_TIMESTAMP = -1L;
    public static final int CSNET_CLIENT_DISCONNECT = -4006;
    public static final int CSNET_ERR_RECEIVE_FAILURE = -4008;
    public static final int SERVER_STATE_IDLE = 0;
    public static final int SERVER_STATE_BUSY = 1;
    public static final int SERVER_STATE_LOCKED = 2;
    public static final int SERVER_STATE_OTHER = 3;
    public static final short[] APPSRVCAPINFO_TYPE = { 3001, 2001, 2002, 2003, 2004, 2005, 2006, 2007 };
    public static final String[] APPSRVCAPINFO_VALUE = { "2", "109", "102", "102", "10", "0", "1", "82" };
    public static final int IPVER_IPV4 = 0;
    public static final int IPVER_IPV6 = 1;
}

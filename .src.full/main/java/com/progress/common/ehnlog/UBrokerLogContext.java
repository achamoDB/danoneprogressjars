// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class UBrokerLogContext extends AbstractLogContext
{
    public static final int SUB_V_UB_BASIC = 0;
    public static final int SUB_V_UB_DEBUG = 1;
    public static final int SUB_V_UB_CLIENTFSM = 2;
    public static final int SUB_V_UB_SERVERFSM = 3;
    public static final int SUB_V_UB_CLIENTMSGSTREAM = 4;
    public static final int SUB_V_UB_SERVERMSGSTREAM = 5;
    public static final int SUB_V_UB_CLIENTMSGQ = 6;
    public static final int SUB_V_UB_SERVERMSGQ = 7;
    public static final int SUB_V_UB_CLIENTMEMTRACE = 8;
    public static final int SUB_V_UB_SERVERMEMTRACE = 9;
    public static final int SUB_V_UB_THREADPOOL = 10;
    public static final int SUB_V_UB_STATS = 11;
    public static final int SUB_V_UB_AUTOTRIM = 12;
    public static final int SUB_V_UB_SSL = 13;
    public static final int SUB_V_UB_IPC = 14;
    public static final int SUB_V_UB_ASK = 15;
    public static final int SUB_V_UB_UBNET = 16;
    public static final int SUB_V_UB_ACTIONAL = 17;
    public static final long SUB_M_UB_BASIC = 1L;
    public static final long SUB_M_UB_DEBUG = 2L;
    public static final long SUB_M_UB_CLIENTFSM = 4L;
    public static final long SUB_M_UB_SERVERFSM = 8L;
    public static final long SUB_M_UB_CLIENTMSGSTREAM = 16L;
    public static final long SUB_M_UB_SERVERMSGSTREAM = 32L;
    public static final long SUB_M_UB_CLIENTMSGQ = 64L;
    public static final long SUB_M_UB_SERVERMSGQ = 128L;
    public static final long SUB_M_UB_CLIENTMEMTRACE = 256L;
    public static final long SUB_M_UB_SERVERMEMTRACE = 512L;
    public static final long SUB_M_UB_THREADPOOL = 1024L;
    public static final long SUB_M_UB_STATS = 2048L;
    public static final long SUB_M_UB_AUTOTRIM = 4096L;
    public static final long SUB_M_UB_SSL = 8192L;
    public static final long SUB_M_UB_IPC = 16384L;
    public static final long SUB_M_UB_ASK = 32768L;
    public static final long SUB_M_UB_UBNET = 65536L;
    public static final long SUB_M_UB_ACTIONAL = 131072L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 1L;
    private static final int STR_IDX_UB_ACTIONAL = 0;
    private static final int STR_IDX_UB_ASK = 1;
    private static final int STR_IDX_UB_AUTOTRIM = 2;
    private static final int STR_IDX_UB_BASIC = 3;
    private static final int STR_IDX_UB_CLIENTFSM = 4;
    private static final int STR_IDX_UB_CLIENTMEMTRACE = 5;
    private static final int STR_IDX_UB_CLIENTMSGQ = 6;
    private static final int STR_IDX_UB_CLIENTMSGSTREAM = 7;
    private static final int STR_IDX_UB_DEBUG = 8;
    private static final int STR_IDX_UB_SERVERFSM = 9;
    private static final int STR_IDX_UB_SERVERMEMTRACE = 10;
    private static final int STR_IDX_UB_SERVERMSGQ = 11;
    private static final int STR_IDX_UB_SERVERMSGSTREAM = 12;
    private static final int STR_IDX_UB_IPC = 13;
    private static final int STR_IDX_UB_SSL = 14;
    private static final int STR_IDX_UB_STATS = 15;
    private static final int STR_IDX_UB_THREADPOOL = 16;
    private static final int STR_IDX_UB_UBNET = 17;
    private static final int STR_IDX_ZALL = 18;
    private static final int STR_IDX_ZDEFAULT = 19;
    protected static final String DEFAULT_SUBSYSTEM = "_UB";
    public static final String DEFAULT_EXEC_ENV_ID = "UB";
    public static final String DEFAULT_ENTRYTYPE_STRING = "UBroker.Basic";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    
    public String getLogContextName() {
        return "UBroker";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_UB", i);
            }
            this.setEntrytypeName("Basic     ", 0);
            this.setEntrytypeName("Debug     ", 1);
            this.setEntrytypeName("C-FSM     ", 2);
            this.setEntrytypeName("S-FSM     ", 3);
            this.setEntrytypeName("C-MsgStrm ", 4);
            this.setEntrytypeName("S-MsgStrm ", 5);
            this.setEntrytypeName("C-MsgQ    ", 6);
            this.setEntrytypeName("S-MsgQ    ", 7);
            this.setEntrytypeName("C-MemTrace", 8);
            this.setEntrytypeName("S-MemTrace", 9);
            this.setEntrytypeName("ThreadPool", 10);
            this.setEntrytypeName("Stats     ", 11);
            this.setEntrytypeName("AutoTrim  ", 12);
            this.setEntrytypeName("SSL Debug ", 13);
            this.setEntrytypeName("Sonic Adpt", 14);
            this.setEntrytypeName("ASK       ", 15);
            this.setEntrytypeName("UBNET     ", 16);
            this.setEntrytypeName("Actional  ", 17);
            this.addToEntrytypeTable("Ubroker.All", 1152921504606846975L, -1, 18);
            this.addToEntrytypeTable("Ubroker.Basic", 1L, 0, 3);
            this.addToEntrytypeTable("Ubroker.Debug", 2L, 1, 8);
            this.addToEntrytypeTable("Ubroker.ClientFSM", 4L, 2, 4);
            this.addToEntrytypeTable("Ubroker.ServerFSM", 8L, 3, 9);
            this.addToEntrytypeTable("Ubroker.ClientMsgStream", 16L, 4, 7);
            this.addToEntrytypeTable("Ubroker.ServerMsgStream", 32L, 5, 12);
            this.addToEntrytypeTable("Ubroker.ClientMsgQueue", 64L, 6, 6);
            this.addToEntrytypeTable("Ubroker.ServerMsgQueue", 128L, 7, 11);
            this.addToEntrytypeTable("Ubroker.ClientMemTrace", 256L, 8, 5);
            this.addToEntrytypeTable("Ubroker.ServerMemTrace", 512L, 9, 10);
            this.addToEntrytypeTable("Ubroker.ThreadPool", 1024L, 10, 16);
            this.addToEntrytypeTable("Ubroker.Stats", 2048L, 11, 15);
            this.addToEntrytypeTable("Ubroker.AutoTrim", 4096L, 12, 2);
            this.addToEntrytypeTable("Ubroker.SSL", 8192L, 13, 14);
            this.addToEntrytypeTable("Ubroker.Sonic", 16384L, 14, 13);
            this.addToEntrytypeTable("Ubroker.ASK", 32768L, 15, 1);
            this.addToEntrytypeTable("Ubroker.UBNET", 65536L, 16, 17);
            this.addToEntrytypeTable("Ubroker.Actional", 131072L, 17, 0);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
    }
}

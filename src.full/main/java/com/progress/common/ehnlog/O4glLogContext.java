// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class O4glLogContext extends AbstractLogContext
{
    public static final int SUB_V_TRACER = 0;
    public static final int SUB_V_PROXYOBJECT = 1;
    public static final int SUB_V_SESSIONPOOL = 2;
    public static final int SUB_V_PROPERTIES = 3;
    public static final int SUB_V_POOLMGMT = 4;
    public static final int SUB_V_REFCOUNTS = 5;
    public static final int SUB_V_RUNPROCS = 6;
    public static final int SUB_V_WATCHDOG = 7;
    public static final int SUB_V_MSG_DEBUG = 8;
    public static final int SUB_V_DYNAMICAPI = 9;
    public static final int SUB_V_UBROKER = 10;
    public static final long SUB_M_TRACER = 1L;
    public static final long SUB_M_PROXYOBJECT = 2L;
    public static final long SUB_M_SESSIONPOOL = 4L;
    public static final long SUB_M_PROPERTIES = 8L;
    public static final long SUB_M_POOLMGMT = 16L;
    public static final long SUB_M_REFCOUNTS = 32L;
    public static final long SUB_M_RUNPROCS = 64L;
    public static final long SUB_M_WATCHDOG = 128L;
    public static final long SUB_M_MSG_DEBUG = 256L;
    public static final long SUB_M_DYNAMICAPI = 4294967296L;
    public static final long SUB_M_UBROKER = 8589934592L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 1L;
    private static final int STR_IDX_UBROKER = 0;
    private static final int STR_IDX_DYNAMICAPI = 1;
    private static final int STR_IDX_MSG_DEBUG = 2;
    private static final int STR_IDX_POOLMGMT = 3;
    private static final int STR_IDX_PROPERTIES = 4;
    private static final int STR_IDX_REFCOUNTS = 5;
    private static final int STR_IDX_RUNPROCS = 6;
    private static final int STR_IDX_SESSIONPOOL = 7;
    private static final int STR_IDX_TRACER = 8;
    private static final int STR_IDX_WATCHDOG = 9;
    private static final int STR_IDX_PROXYOBJECT = 10;
    private static final int STR_IDX_ZDEFAULT = 11;
    protected static final String DEFAULT_SUBSYSTEM = "_O4GL";
    public static final String DEFAULT_EXEC_ENV_ID = "_O4GL";
    public static final String DEFAULT_ENTRYTYPE_STRING = "O4glDefault";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    public String m_execEnvId;
    protected ThreadLocal m_requestID;
    
    public O4glLogContext() {
        this.m_execEnvId = "";
        this.m_requestID = new ThreadLocal() {
            protected Object initialValue() {
                return new String("");
            }
        };
    }
    
    public String getLogContextName() {
        return "O4gl";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_O4GL", i);
            }
            this.setEntrytypeName("Trace      ", 0);
            this.setEntrytypeName("Proxy      ", 1);
            this.setEntrytypeName("SessionPool", 2);
            this.setEntrytypeName("Properties ", 3);
            this.setEntrytypeName("PoolMgmt   ", 4);
            this.setEntrytypeName("RefCounts  ", 5);
            this.setEntrytypeName("RunProcs   ", 6);
            this.setEntrytypeName("WatchDog   ", 7);
            this.setEntrytypeName("MsgDbg     ", 8);
            this.setEntrytypeName("DynamicApi ", 9);
            this.setEntrytypeName("Broker     ", 10);
            this.addToEntrytypeTable("Broker", 8589934592L, 10, 0);
            this.addToEntrytypeTable("DynamicApi", 4294967296L, 9, 1);
            this.addToEntrytypeTable("MsgDebug", 256L, 8, 2);
            this.addToEntrytypeTable("O4glDefault", 1L, -1, 11);
            this.addToEntrytypeTable("PoolMgmt", 16L, 4, 3);
            this.addToEntrytypeTable("Properties", 8L, 3, 4);
            this.addToEntrytypeTable("Proxy", 2L, 1, 10);
            this.addToEntrytypeTable("RefCounts", 32L, 5, 5);
            this.addToEntrytypeTable("RunProcs", 64L, 6, 6);
            this.addToEntrytypeTable("SessionPool", 4L, 2, 7);
            this.addToEntrytypeTable("Trace", 1L, 0, 8);
            this.addToEntrytypeTable("WatchDog", 128L, 7, 9);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
    }
    
    public String getMsgHdr() {
        return this.getFormattedRequestID();
    }
    
    public void setMsgHdr(final String requestID) {
        this.setRequestID(requestID);
    }
    
    private void setRequestID(final int i) {
        this.m_requestID.set(new String("(reqid:" + i + ") "));
    }
    
    private void setRequestID(final String str) {
        this.m_requestID.set(new String("(reqid:" + str + ") "));
    }
    
    private String getFormattedRequestID() {
        return this.m_requestID.get();
    }
}

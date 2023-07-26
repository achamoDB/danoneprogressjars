// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class AppLogContext extends AbstractLogContext
{
    public static final int LOG_IDX_APPOBJECT = 0;
    public static final int LOG_IDX_SESSIONPOOL = 1;
    public static final int LOG_IDX_POOLMGMT = 2;
    public static final int LOG_IDX_REFCOUNTS = 3;
    public static final int LOG_IDX_RUNPROCS = 4;
    public static final long LOG_ENTRY_APPOBJECT = 1L;
    public static final long LOG_ENTRY_SESSIONPOOL = 2L;
    public static final long LOG_ENTRY_POOLMGMT = 4L;
    public static final long LOG_ENTRY_REFCOUNTS = 8L;
    public static final long LOG_ENTRY_RUNPROCS = 16L;
    public static final long LOG_ENTRY_ALL = 31L;
    public static final long LOG_ENTRY_DEFAULT = 31L;
    private static final int STR_IDX_APPOBJECT = 0;
    private static final int STR_IDX_POOLMGMT = 1;
    private static final int STR_IDX_REFCOUNTS = 2;
    private static final int STR_IDX_RUNPROCS = 3;
    private static final int STR_IDX_SESSIONPOOL = 4;
    private static final int STR_IDX_ZDEFAULT = 5;
    protected static final String DEFAULT_ENTRYNAME = "_App";
    public static final String DEFAULT_EXEC_ENV_ID = "_App";
    public static final String DEFAULT_ENTRYTYPE_STRING = "AppDefault";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    public String m_execEnvId;
    protected ThreadLocal m_requestID;
    
    public AppLogContext() {
        this.m_execEnvId = "";
        this.m_requestID = new ThreadLocal() {
            protected Object initialValue() {
                return new String("");
            }
        };
    }
    
    public String getLogContextName() {
        return "App";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_App", i);
            }
            this.setEntrytypeName("App-Object      ", 0);
            this.setEntrytypeName("Session-Pool    ", 1);
            this.setEntrytypeName("Pool-Management ", 2);
            this.setEntrytypeName("Ref-Counts      ", 3);
            this.setEntrytypeName("Run-Procs       ", 4);
            this.addToEntrytypeTable("AppDefault", 31L, -1, 5);
            this.addToEntrytypeTable("AppObject", 1L, 0, 0);
            this.addToEntrytypeTable("PoolMgmt", 4L, 2, 1);
            this.addToEntrytypeTable("RefCounts", 8L, 3, 2);
            this.addToEntrytypeTable("RunProcs", 16L, 4, 3);
            this.addToEntrytypeTable("SessionPool", 2L, 1, 4);
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

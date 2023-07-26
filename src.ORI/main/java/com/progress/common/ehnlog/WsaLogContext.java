// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class WsaLogContext extends AbstractLogContext
{
    public static final int SUB_V_WSA = 0;
    public static final int SUB_V_SOAP = 1;
    public static final int SUB_V_4GLPROV = 2;
    public static final int SUB_V_ADMINPROV = 3;
    public static final int SUB_V_WSAOBJECT = 4;
    public static final int SUB_V_WSAOBJECTPOOL = 5;
    public static final int SUB_V_SESSIONPOOL = 6;
    public static final int SUB_V_WSDL = 7;
    public static final int SUB_V_RESOURCEMGR = 8;
    public static final int SUB_V_PROPERTIES = 9;
    public static final int SUB_V_POOLMGMT = 10;
    public static final int SUB_V_REFCOUNTS = 11;
    public static final int SUB_V_RUNPROCS = 12;
    public static final int SUB_V_WATCHDOG = 13;
    public static final int SUB_V_MSG_DEBUG = 14;
    public static final int SUB_V_ACTIONAL = 15;
    public static final int SUB_V_DYNAMICAPI = 32;
    public static final int SUB_V_UBROKER = 33;
    public static final long SUB_M_WSA = 1L;
    public static final long SUB_M_SOAP = 2L;
    public static final long SUB_M_4GLPROV = 4L;
    public static final long SUB_M_ADMINPROV = 8L;
    public static final long SUB_M_WSAOBJECT = 16L;
    public static final long SUB_M_WSAOBJECTPOOL = 32L;
    public static final long SUB_M_SESSIONPOOL = 64L;
    public static final long SUB_M_WSDL = 128L;
    public static final long SUB_M_RESOURCEMGR = 256L;
    public static final long SUB_M_PROPERTIES = 512L;
    public static final long SUB_M_POOLMGMT = 1024L;
    public static final long SUB_M_REFCOUNTS = 2048L;
    public static final long SUB_M_RUNPROCS = 4096L;
    public static final long SUB_M_WATCHDOG = 8192L;
    public static final long SUB_M_MSG_DEBUG = 16384L;
    public static final long SUB_M_ACTIONAL = 32768L;
    public static final long SUB_M_DYNAMICAPI = 4294967296L;
    public static final long SUB_M_UBROKER = 8589934592L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 525L;
    private static final int STR_IDX_4GLPROV = 0;
    private static final int STR_IDX_ADMINPROV = 1;
    private static final int STR_IDX_UBROKER = 2;
    private static final int STR_IDX_DYNAMICAPI = 3;
    private static final int STR_IDX_MSG_DEBUG = 4;
    private static final int STR_IDX_POOLMGMT = 5;
    private static final int STR_IDX_PROPERTIES = 6;
    private static final int STR_IDX_REFCOUNTS = 7;
    private static final int STR_IDX_RESOURCEMGR = 8;
    private static final int STR_IDX_RUNPROCS = 9;
    private static final int STR_IDX_SESSIONPOOL = 10;
    private static final int STR_IDX_SOAP = 11;
    private static final int STR_IDX_WATCHDOG = 12;
    private static final int STR_IDX_WSA = 13;
    private static final int STR_IDX_WSAOBJECT = 14;
    private static final int STR_IDX_WSAOBJECTPOOL = 15;
    private static final int STR_IDX_WSDL = 16;
    private static final int STR_IDX_ACTIONAL = 17;
    private static final int STR_IDX_ZDEFAULT = 18;
    protected static final String DEFAULT_SUBSYSTEM = "_WSA";
    public static final String DEFAULT_EXEC_ENV_ID = "_WSA";
    public static final String DEFAULT_ENTRYTYPE_STRING = "WSADefault";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    public String m_execEnvId;
    protected ThreadLocal m_requestID;
    
    public WsaLogContext() {
        this.m_execEnvId = "";
        this.m_requestID = new ThreadLocal() {
            protected Object initialValue() {
                return new String("");
            }
        };
    }
    
    public String getLogContextName() {
        return "Wsa";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_WSA", i);
            }
            this.setEntrytypeName("WSA             ", 0);
            this.setEntrytypeName("SOAP-Processor  ", 1);
            this.setEntrytypeName("4GL-Provider    ", 2);
            this.setEntrytypeName("Admin-Provider  ", 3);
            this.setEntrytypeName("WSA-Object      ", 4);
            this.setEntrytypeName("WSA-Object-Pool ", 5);
            this.setEntrytypeName("Session-Pool    ", 6);
            this.setEntrytypeName("WSDL-Document   ", 7);
            this.setEntrytypeName("Resource-Manager", 8);
            this.setEntrytypeName("Properties      ", 9);
            this.setEntrytypeName("Pool-Management ", 10);
            this.setEntrytypeName("Ref-Counts      ", 11);
            this.setEntrytypeName("Run-Procs       ", 12);
            this.setEntrytypeName("Watch-Dog       ", 13);
            this.setEntrytypeName("Message-Debug   ", 14);
            this.setEntrytypeName("Actional        ", 15);
            this.setEntrytypeName("DynamicApi      ", 32);
            this.setEntrytypeName("uBroker-Client  ", 33);
            this.addToEntrytypeTable("4GLProvider", 4L, 2, 0);
            this.addToEntrytypeTable("Actional", 32768L, 15, 17);
            this.addToEntrytypeTable("AdminProvider", 8L, 3, 1);
            this.addToEntrytypeTable("BrokerClient", 8589934592L, 33, 2);
            this.addToEntrytypeTable("DynamicApi", 4294967296L, 32, 3);
            this.addToEntrytypeTable("MsgDebug", 16384L, 14, 4);
            this.addToEntrytypeTable("PoolMgmt", 1024L, 10, 5);
            this.addToEntrytypeTable("Properties", 512L, 9, 6);
            this.addToEntrytypeTable("RefCounts", 2048L, 11, 7);
            this.addToEntrytypeTable("ResourceMgr", 256L, 8, 8);
            this.addToEntrytypeTable("RunProcs", 4096L, 12, 9);
            this.addToEntrytypeTable("SessionPool", 64L, 6, 10);
            this.addToEntrytypeTable("SOAPProc", 2L, 1, 11);
            this.addToEntrytypeTable("WatchDog", 8192L, 13, 12);
            this.addToEntrytypeTable("WSA", 1L, 0, 13);
            this.addToEntrytypeTable("WSADefault", 525L, -1, 18);
            this.addToEntrytypeTable("WSAObject", 16L, 4, 14);
            this.addToEntrytypeTable("WSAObjPool", 32L, 5, 15);
            this.addToEntrytypeTable("WSDLDoc", 128L, 7, 16);
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

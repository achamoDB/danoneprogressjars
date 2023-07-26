// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class AiaLogContext extends AbstractLogContext
{
    public static final int SUB_V_AIAMGMT = 0;
    public static final int SUB_V_AIAPROP = 1;
    public static final int SUB_V_AIARQST = 2;
    public static final int SUB_V_AIAUBROKER = 3;
    public static final int SUB_V_AIAASKPING = 4;
    public static final int SUB_V_AIAUBNET = 5;
    public static final int SUB_V_AIAACTIONAL = 6;
    public static final long SUB_M_AIAMGMT = 1L;
    public static final long SUB_M_AIAPROP = 2L;
    public static final long SUB_M_AIARQST = 4L;
    public static final long SUB_M_AIAUBROKER = 8L;
    public static final long SUB_M_AIAASKPING = 16L;
    public static final long SUB_M_AIAUBNET = 32L;
    public static final long SUB_M_AIAACTIONAL = 64L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 3L;
    private static final int STR_IDX_AIAACTIONAL = 0;
    private static final int STR_IDX_AIAASKPING = 1;
    private static final int STR_IDX_AIAMGMT = 2;
    private static final int STR_IDX_AIAPROP = 3;
    private static final int STR_IDX_AIARQST = 4;
    private static final int STR_IDX_AIAUBROKER = 5;
    private static final int STR_IDX_AIAUBNET = 6;
    private static final int STR_IDX_ZDEFAULT = 7;
    protected static final String DEFAULT_SUBSYSTEM = "_AIA";
    public static final String DEFAULT_EXEC_ENV_ID = "AIA";
    public static final String DEFAULT_ENTRYTYPE_STRING = "AIADefault";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    
    public String getLogContextName() {
        return "Aia";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_AIA", i);
            }
            this.setEntrytypeName("AiaMgmt    ", 0);
            this.setEntrytypeName("AiaProp    ", 1);
            this.setEntrytypeName("AiaRqst    ", 2);
            this.setEntrytypeName("AiaUBroker ", 3);
            this.setEntrytypeName("AiaAskPing ", 4);
            this.setEntrytypeName("AiaUBNET   ", 5);
            this.setEntrytypeName("Actional   ", 6);
            this.addToEntrytypeTable("AiaDefault", 3L, -1, 7);
            this.addToEntrytypeTable("AiaMgmt", 1L, 0, 2);
            this.addToEntrytypeTable("AiaProp", 2L, 1, 3);
            this.addToEntrytypeTable("AiaRqst", 4L, 2, 4);
            this.addToEntrytypeTable("AiaUBroker", 8L, 3, 5);
            this.addToEntrytypeTable("AiaAskPing", 16L, 4, 1);
            this.addToEntrytypeTable("AiaUBNET", 32L, 5, 6);
            this.addToEntrytypeTable("Actional", 64L, 6, 0);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
    }
}

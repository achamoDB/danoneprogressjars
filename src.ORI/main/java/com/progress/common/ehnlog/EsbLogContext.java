// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class EsbLogContext extends AbstractLogContext
{
    public static final int SUB_V_NATIVE = 0;
    public static final int SUB_V_WEB = 1;
    public static final int SUB_V_O4GL = 2;
    public static final int SUB_V_OUTER = 3;
    public static final long SUB_M_NATIVE = 1L;
    public static final long SUB_M_WEB = 2L;
    public static final long SUB_M_O4GL = 4L;
    public static final long SUB_M_OUTER = 4294967296L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 3L;
    private static final int STR_IDX_NATIVE = 0;
    private static final int STR_IDX_WEB = 1;
    private static final int STR_IDX_O4GL = 2;
    private static final int STR_IDX_ZDEFAULT = 3;
    public static final String DEFAULT_ENTRYNAME = "_ESB";
    public static final String DEFAULT_EXEC_ENV_ID = "OpenEdge Adapter";
    public static final String DEFAULT_ENTRYTYPE_STRING = "ESBDefault";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    
    public String getLogContextName() {
        return "Esb";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_ESB", i);
            }
            this.setEntrytypeName("Native Invocation", 0);
            this.setEntrytypeName("Web Service", 1);
            this.setEntrytypeName("4GL-Provider", 2);
            this.setEntrytypeName("OUTER     ", 3);
            this.addToEntrytypeTable("ESBDefault", 3L, -1, 3);
            this.addToEntrytypeTable("Native", 1L, 0, 0);
            this.addToEntrytypeTable("WebServices", 2L, 1, 1);
            this.addToEntrytypeTable("4GLProvider", 4L, 2, 2);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
    }
}

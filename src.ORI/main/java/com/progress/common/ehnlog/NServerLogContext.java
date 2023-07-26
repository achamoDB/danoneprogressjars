// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class NServerLogContext extends AbstractLogContext
{
    public static final int SUB_V_NSPLUMBING = 0;
    public static final long SUB_M_NSPLUMBING = 1L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 1L;
    private static final int STR_IDX_NSPLUMBING = 0;
    private static final int STR_IDX_ZDEFAULT = 1;
    protected static final String DEFAULT_SUBSYSTEM = "_NS";
    public static final String DEFAULT_EXEC_ENV_ID = "NS";
    public static final String DEFAULT_ENTRYTYPE_STRING = "NSPlumbing";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    
    public String getLogContextName() {
        return "NServer";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_NS", i);
            }
            this.setEntrytypeName("NSPlumbing", 0);
            this.addToEntrytypeTable("NSPlumbing", 1L, 0, 0);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
    }
}

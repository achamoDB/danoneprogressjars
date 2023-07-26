// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class SampleLogContext extends AbstractLogContext
{
    public static final int SUB_V_ENT1 = 0;
    public static final int SUB_V_ENT2 = 1;
    public static final int SUB_V_ENT3 = 2;
    public static final int SUB_V_ENT4 = 3;
    public static final int SUB_V_ENT5 = 4;
    public static final int SUB_V_ENT6 = 5;
    public static final int SUB_V_ENT7 = 6;
    public static final int SUB_V_ENT8 = 7;
    public static final int SUB_V_ENT9 = 8;
    public static final int SUB_V_OUTER = 32;
    public static final long SUB_M_ENT1 = 1L;
    public static final long SUB_M_ENT2 = 2L;
    public static final long SUB_M_ENT3 = 4L;
    public static final long SUB_M_ENT4 = 8L;
    public static final long SUB_M_ENT5 = 16L;
    public static final long SUB_M_ENT6 = 32L;
    public static final long SUB_M_ENT7 = 64L;
    public static final long SUB_M_ENT8 = 128L;
    public static final long SUB_M_ENT9 = 256L;
    public static final long SUB_M_OUTER = 4294967296L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 15L;
    private static final int STR_IDX_ENT1 = 0;
    private static final int STR_IDX_ENT2 = 1;
    private static final int STR_IDX_ENT3 = 2;
    private static final int STR_IDX_ENT4 = 3;
    private static final int STR_IDX_ENT5 = 4;
    private static final int STR_IDX_ENT6 = 5;
    private static final int STR_IDX_ENT7 = 6;
    private static final int STR_IDX_ENT8 = 7;
    private static final int STR_IDX_ENT9 = 8;
    private static final int STR_IDX_ZDEFAULT = 9;
    public static final String DEFAULT_ENTRYNAME = "_APPNAME";
    public static final String DEFAULT_EXEC_ENV_ID = "_APPNAME";
    public static final String DEFAULT_ENTRYTYPE_STRING = "Default";
    protected static final long DEFAULT_ENTRYTYPE_BIT = 0L;
    
    public String getLogContextName() {
        return "Sample";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_APPNAME", i);
            }
            this.setEntrytypeName("My Entry 1", 0);
            this.setEntrytypeName("My Entry 2", 1);
            this.setEntrytypeName("My Entry 3", 2);
            this.setEntrytypeName("My Entry 4", 3);
            this.setEntrytypeName("My Entry 5", 4);
            this.setEntrytypeName("My Entry 6", 5);
            this.setEntrytypeName("My Entry 7", 6);
            this.setEntrytypeName("My Entry 8", 7);
            this.setEntrytypeName("My Entry 9", 8);
            this.setEntrytypeName("OUTER     ", 32);
            this.addToEntrytypeTable("Default", 15L, -1, 9);
            this.addToEntrytypeTable("Ent1", 1L, 0, 0);
            this.addToEntrytypeTable("Ent2", 2L, 1, 1);
            this.addToEntrytypeTable("Ent3", 4L, 2, 2);
            this.addToEntrytypeTable("Ent4", 8L, 3, 3);
            this.addToEntrytypeTable("Ent5", 16L, 4, 4);
            this.addToEntrytypeTable("Ent6", 32L, 5, 5);
            this.addToEntrytypeTable("Ent7", 64L, 6, 6);
            this.addToEntrytypeTable("Ent8", 128L, 7, 7);
            this.addToEntrytypeTable("Ent9", 256L, 8, 8);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
    }
}

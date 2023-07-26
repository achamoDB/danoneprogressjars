// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

public class GenericLogContext extends AbstractLogContext
{
    public static final int SUB_V_MAIN = 0;
    public static final int SUB_V_SUBSYS1 = 1;
    public static final int SUB_V_SUBSYS2 = 2;
    public static final int SUB_V_OUTERSYS = 3;
    public static final long SUB_M_MAIN = 1L;
    public static final long SUB_M_SUBSYS1 = 2L;
    public static final long SUB_M_SUBSYS2 = 4L;
    public static final long SUB_M_OUTERSYS = 4294967296L;
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 4294967295L;
    protected static final String DEFAULT_ENTRYNAME = "_APPNAME";
    protected static final String DEFAULT_EXEC_ENV_ID = "_APPNAME";
    
    public String getLogContextName() {
        return "Generic";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("_APPNAME", i);
            }
            this.setEntrytypeName("Application Name", 0);
            this.setEntrytypeName("Entrytype-1     ", 1);
            this.setEntrytypeName("Entrytype-2     ", 2);
            this.setEntrytypeName("outer-entrytype ", 3);
            this.addToEntrytypeTable("", -1L, -1, 0);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
    }
}

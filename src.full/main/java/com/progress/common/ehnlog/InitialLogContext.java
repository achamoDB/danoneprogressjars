// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.ehnlog;

import java.io.Serializable;

public class InitialLogContext extends AbstractLogContext implements Serializable
{
    public static final int SUB_V_INIT = 0;
    public static final long SUB_M_INIT = 1L;
    public static final String ENTRY_D_INIT = "App-Initializing";
    public static final long SUB_M_ALL = 1152921504606846975L;
    public static final long SUB_M_DEFAULT = 15L;
    protected static final String DEFAULT_ENTRYNAME = "App-Initializing";
    protected static final String DEFAULT_EXEC_ENV_ID = "App-Initializing";
    
    public String getLogContextName() {
        return "Initial";
    }
    
    public void initEntrytypeNames() throws LogException {
        final int entrytypesCapacity = this.getEntrytypesCapacity();
        try {
            for (int i = 0; i < entrytypesCapacity; ++i) {
                this.insertEntrytypeName("App-Initializing", i);
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new LogException("Entrytype vector was overrun");
        }
        this.addToEntrytypeTable("", -1L, -1, 0);
    }
}

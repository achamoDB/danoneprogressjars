// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.sql.SQLException;

public class ProSQLException extends SQLException
{
    private OutputSetException proException;
    public static final String state_S1010 = "S1010";
    public static final String state_S1008 = "S1008";
    public static final String state_S1002 = "S1002";
    public static final String state_S1000 = "S1000";
    public static final String state_S1C00 = "S1C00";
    public static final String state_S1T00 = "S1T00";
    public static final String state_08S01 = "08S01";
    
    public ProSQLException(final OutputSetException proException, final String sqlState) {
        super(proException.getMessage(), sqlState);
        this.proException = proException;
    }
    
    public ProSQLException() {
        super("ERROR", "S1000");
    }
    
    public ProSQLException(final String reason) {
        super(reason, "S1000");
    }
    
    public OutputSetException getProException() {
        if (this.proException == null) {
            this.proException = new OutputSetException();
        }
        return this.proException;
    }
}

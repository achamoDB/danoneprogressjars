// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.sql.ResultSet;

public final class ResultSetHolder extends Holder
{
    public ResultSetHolder() {
    }
    
    public ResultSetHolder(final ResultSet value) {
        super.setValue(value);
    }
    
    public void setResultSetValue(final ResultSet value) {
        super.setValue(value);
    }
    
    public ResultSet getResultSetValue() {
        return (ResultSet)super.getValue();
    }
}

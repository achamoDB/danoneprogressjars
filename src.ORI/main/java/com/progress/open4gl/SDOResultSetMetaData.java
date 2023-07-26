// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public interface SDOResultSetMetaData extends ProResultSetMetaData
{
    String getColumnValExp(final int p0) throws ProSQLException;
    
    String getColumnValMsg(final int p0) throws ProSQLException;
    
    String getColumnFormat(final int p0) throws ProSQLException;
    
    String getColumnInitialValue(final int p0) throws ProSQLException;
}

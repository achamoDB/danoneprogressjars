// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.sql.ResultSetMetaData;

public interface ProResultSetMetaData extends ResultSetMetaData
{
    int getFieldCount() throws ProSQLException;
    
    int getFieldProType(final int p0) throws ProSQLException;
    
    int getColumnProType(final int p0) throws ProSQLException;
    
    String getFieldJavaTypeName(final int p0) throws ProSQLException;
    
    String getColumnJavaTypeName(final int p0) throws ProSQLException;
    
    int getFieldExtent(final int p0) throws ProSQLException;
    
    String getFieldName(final int p0) throws ProSQLException;
    
    String getFieldTypeName(final int p0) throws ProSQLException;
}

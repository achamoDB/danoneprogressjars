// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.open4gl.dynamicapi.ResultSetMetaData;

public class ProResultSetMetaDataImpl extends ResultSetMetaData
{
    private int m_numFields;
    
    public ProResultSetMetaDataImpl(final int numFields) {
        super(0, numFields);
        this.m_numFields = numFields;
    }
    
    public ProResultSetMetaDataImpl(final ResultSetMetaData resultSetMetaData) throws ProSQLException {
        super(resultSetMetaData);
        this.m_numFields = resultSetMetaData.getFieldCount();
    }
    
    public void setFieldMetaData(final int value, final String s, final int n, final int n2) throws ProSQLException {
        if (value < 1 || value > this.m_numFields) {
            throw new ProSQLException(new Open4GLException(7665970990714723343L, new Object[] { new Integer(value) }).getMessage());
        }
        if (s == null) {
            throw new ProSQLException(new Open4GLException(7665970990714725434L, null).getMessage());
        }
        if (n < 0) {
            throw new ProSQLException(new Open4GLException(7665970990714725435L, null).getMessage());
        }
        if (n2 < 1 || n2 > 41) {
            throw new ProSQLException(new Open4GLException(7665970990714725436L, null).getMessage());
        }
        try {
            super.setFieldDesc(value, s, n, n2);
        }
        catch (Open4GLError open4GLError) {
            if (open4GLError.getMessageId() == 7221L) {
                throw new ProSQLException(new Open4GLException(7665970990714725437L, null).getMessage());
            }
        }
    }
}

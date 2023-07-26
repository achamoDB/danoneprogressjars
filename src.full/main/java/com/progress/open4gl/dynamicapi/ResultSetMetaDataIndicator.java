// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

public class ResultSetMetaDataIndicator extends MetaDataIndicator
{
    public ResultSetMetaDataIndicator(final ResultSetMetaData resultSetMetaData, final int n, final int n2) {
        super(resultSetMetaData, n, n2, 0);
    }
    
    public void print(final Tracer tracer) {
        if (super.m_metaData != null) {
            tracer.print("\n    Schema of " + ParameterSet.inOutString(super.m_inOut) + " DataTable,   Parameter " + new Integer(super.m_paramNum).toString());
            ((ResultSetMetaData)super.m_metaData).print(tracer);
        }
    }
}

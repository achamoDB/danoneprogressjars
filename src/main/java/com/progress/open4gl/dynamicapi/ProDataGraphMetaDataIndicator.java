// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProDataGraphMetaData;

public class ProDataGraphMetaDataIndicator extends MetaDataIndicator
{
    private boolean m_mappedTT;
    
    public ProDataGraphMetaDataIndicator(final ProDataGraphMetaData proDataGraphMetaData, final int n, final int n2, final boolean mappedTT) {
        super(proDataGraphMetaData, n, n2, 1);
        this.m_mappedTT = mappedTT;
    }
    
    public boolean isMappedTempTable() {
        return this.m_mappedTT;
    }
    
    public void print(final Tracer tracer) {
        if (super.m_metaData != null) {
            tracer.print("\n    Schema of " + ParameterSet.inOutString(super.m_inOut) + " DataSet,   Parameter " + new Integer(super.m_paramNum).toString());
            final ProDataGraphMetaData proDataGraphMetaData = (ProDataGraphMetaData)super.m_metaData;
        }
    }
}

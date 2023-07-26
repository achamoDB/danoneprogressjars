// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProDataObjectMetaData;

public class ProDataObjectMetaDataIndicator extends MetaDataIndicator
{
    public ProDataObjectMetaDataIndicator(final ProDataObjectMetaData proDataObjectMetaData, final int n, final int n2) {
        super(proDataObjectMetaData, n, n2, 2);
    }
    
    public void print(final Tracer tracer) {
        if (super.m_metaData != null) {
            tracer.print("\n    Schema of " + ParameterSet.inOutString(super.m_inOut) + " ProDataObject,   Parameter " + new Integer(super.m_paramNum).toString());
            ((ProDataObjectMetaData)super.m_metaData).print(tracer);
        }
    }
}

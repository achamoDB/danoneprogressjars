// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

public class MetaDataIndicator
{
    protected MetaDataBase m_metaData;
    protected int m_paramNum;
    protected int m_inOut;
    protected int m_typeCode;
    
    public MetaDataIndicator(final MetaDataBase metaData, final int paramNum, final int inOut, final int typeCode) {
        this.m_metaData = metaData;
        this.m_paramNum = paramNum;
        this.m_inOut = inOut;
        this.m_typeCode = typeCode;
    }
    
    public MetaDataIndicator(final ResultSetMetaData metaData, final int paramNum, final int inOut) {
        this.m_metaData = metaData;
        this.m_paramNum = paramNum;
        this.m_inOut = inOut;
        this.m_typeCode = 0;
    }
    
    public int getParamNum() {
        return this.m_paramNum;
    }
    
    public int getInOut() {
        return this.m_inOut;
    }
    
    protected void setParamNum(final int paramNum) {
        this.m_paramNum = paramNum;
    }
    
    protected void setInOut(final int inOut) {
        this.m_inOut = inOut;
    }
    
    public MetaDataBase getMetaData() {
        return this.m_metaData;
    }
    
    void setMetaData(final MetaDataBase metaData) {
        this.m_metaData = metaData;
    }
    
    void print(final Tracer tracer) {
        if (this.m_metaData != null) {
            tracer.print("\n    Schema of " + ParameterSet.inOutString(this.getInOut()) + " result set,   Parameter " + new Integer(this.getParamNum()).toString());
        }
    }
}

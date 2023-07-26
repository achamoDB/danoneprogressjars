// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

public class ArrayParamHolder
{
    private Object[] m_arrayData;
    private Class elementClassType;
    
    public ArrayParamHolder(final Object[] arrayData) {
        this.m_arrayData = null;
        this.elementClassType = null;
        this.m_arrayData = arrayData;
    }
    
    public ArrayParamHolder(final Object[] arrayData, final Class elementClassType) {
        this.m_arrayData = null;
        this.elementClassType = null;
        this.m_arrayData = arrayData;
        this.elementClassType = elementClassType;
    }
    
    public Object[] getArray() {
        return this.m_arrayData;
    }
    
    public void setElementClassType(final Class elementClassType) {
        this.elementClassType = elementClassType;
    }
    
    public Class getElementClassType() {
        return this.elementClassType;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

abstract class DatatypeSurrogate implements IPropertyDatatype
{
    protected Object value;
    
    protected void setBaseValueField(final Object value) throws XPropertyValueIsNotValid {
        this.value = value;
    }
    
    protected Object value() {
        return this.value;
    }
    
    abstract Object makeInstance();
}

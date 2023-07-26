// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

abstract class DatatypeSingular extends DatatypeSurrogate implements IDatatypeSingular
{
    public Object getValue() throws XPropertyException {
        return super.value;
    }
    
    public Object[] getValues() throws XPropertyException {
        return new Object[] { super.value };
    }
    
    public void setValues(final Object[] array) throws XPropertyException {
        if (array.length > 1) {
            throw new XPropertyException("Too many values.");
        }
        this.setValue(array[0]);
    }
}

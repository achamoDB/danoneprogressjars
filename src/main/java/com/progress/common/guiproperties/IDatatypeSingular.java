// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IDatatypeSingular extends IPropertyDatatype
{
    void setValue(final Object p0) throws XPropertyException;
    
    Object getValue() throws XPropertyException;
    
    String getValueAsString() throws XPropertyException;
}

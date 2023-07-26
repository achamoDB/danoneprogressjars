// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IPropertyDatatype
{
    Object toObject(final String p0) throws XPropertyException;
    
    boolean isValidValue(final Object p0) throws XPropertyException;
    
    void setValues(final Object[] p0) throws XPropertyException;
    
    String[] getValuesAsString() throws XPropertyException;
    
    Object[] getValues() throws XPropertyException;
}

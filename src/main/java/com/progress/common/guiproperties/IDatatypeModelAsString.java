// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IDatatypeModelAsString extends IDatatypeSingular
{
    String getValueAsString() throws XPropertyException;
    
    void setValueAsString(final String p0) throws XPropertyException;
    
    boolean isValidValueAsString(final String p0) throws XPropertyException;
    
    Object toObject(final String p0) throws XPropertyException;
    
    String normalize(final String p0) throws XPropertyException;
}

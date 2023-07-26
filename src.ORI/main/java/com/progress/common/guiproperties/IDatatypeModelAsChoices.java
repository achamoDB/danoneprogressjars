// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;

public interface IDatatypeModelAsChoices extends IDatatypeComposite
{
    int findIndex(final Object p0) throws XPropertyException;
    
    Enumeration getChoices() throws XPropertyException;
    
    Object[] getValues() throws XPropertyException;
    
    Integer[] getValuesByIndices() throws XPropertyException;
    
    void setValuesByIndices(final Integer[] p0) throws XPropertyException;
    
    Object toObject(final Object p0) throws XPropertyException;
}

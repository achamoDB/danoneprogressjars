// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IDatatypeModelAsDynamicList extends IDatatypeModelAsList
{
    void setValues(final Object[] p0) throws XPropertyException;
    
    void addValue(final Object p0) throws XPropertyException;
    
    void addValue(final Object p0, final int p1) throws XPropertyException;
    
    void removeValue(final int p0) throws XPropertyException;
    
    void removeValue(final Object p0) throws XPropertyException;
    
    void removeAll() throws XPropertyException;
}

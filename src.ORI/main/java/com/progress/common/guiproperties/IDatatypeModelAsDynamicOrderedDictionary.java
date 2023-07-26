// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;

public interface IDatatypeModelAsDynamicOrderedDictionary
{
    Enumeration elements();
    
    Enumeration keys();
    
    Enumeration values();
    
    Object get(final Object p0);
    
    boolean isEmpty();
    
    Object put(final Object p0, final Object p1) throws XPropertyException;
    
    Object remove(final Object p0);
    
    int size();
    
    void removeAll();
    
    public interface Element
    {
        Object key();
        
        Object value();
    }
}

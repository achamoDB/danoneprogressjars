// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;

public interface IDatatypeModelAsExtendibleDictionary
{
    Enumeration elements();
    
    Enumeration keys();
    
    Object get(final Object p0);
    
    boolean isEmpty();
    
    Object put(final Object p0, final Object p1) throws XPropertyValueIsNotValid;
    
    Object remove(final Object p0);
    
    int size();
}

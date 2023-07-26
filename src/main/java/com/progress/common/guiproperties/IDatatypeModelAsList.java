// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IDatatypeModelAsList extends IDatatypeComposite
{
    int findIndex(final Object p0) throws XPropertyException;
    
    Object[] getValues() throws XPropertyException;
}

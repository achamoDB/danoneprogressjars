// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

abstract class OrderedCollection
{
    public abstract long elementCount();
    
    public abstract boolean addFirst(final Object p0);
    
    public abstract boolean addLast(final Object p0);
    
    public abstract boolean addAfter(final Object p0, final Object p1);
    
    public abstract boolean addBefore(final Object p0, final Object p1);
    
    public abstract boolean objectExists(final Object p0);
    
    public abstract boolean remove(final Object p0);
    
    protected abstract Cursor makeCursor(final boolean p0);
}

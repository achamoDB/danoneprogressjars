// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

public abstract class Cursor
{
    boolean diveIntoCollections;
    
    public Cursor() {
        this.diveIntoCollections = false;
    }
    
    public abstract Object first();
    
    public abstract Object last();
    
    public abstract Object next();
    
    public abstract Object prev();
    
    public abstract Object current();
    
    public static Cursor create(final OrderedCollection collection) {
        return collection.makeCursor(true);
    }
}

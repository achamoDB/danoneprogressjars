// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

public class ChainCursor extends Cursor
{
    Chain list;
    cElem elem;
    
    public ChainCursor(final Chain list) {
        this.list = list;
        this.elem = null;
    }
    
    public ChainCursor(final Chain list, final boolean diveIntoCollections) {
        this.list = list;
        this.elem = null;
        super.diveIntoCollections = diveIntoCollections;
    }
    
    public ChainCursor(final OrderedCollection collection) {
        this.list = (Chain)collection;
        this.elem = null;
    }
    
    public ChainCursor(final OrderedCollection collection, final boolean diveIntoCollections) {
        this.list = (Chain)collection;
        this.elem = null;
        super.diveIntoCollections = diveIntoCollections;
    }
    
    public Object first() {
        if (this.list == null) {
            return null;
        }
        this.elem = this.list.first;
        while (this.elem != null && this.elem.removed) {
            this.elem = this.elem.next;
        }
        if (this.elem != null) {
            return this.elem.value;
        }
        return null;
    }
    
    public Object last() {
        if (this.list == null) {
            return null;
        }
        cElem elem = this.list.first;
        if (elem == null) {
            return null;
        }
        while (elem.next != null) {
            elem = elem.next;
        }
        this.elem = elem;
        if (this.elem.removed) {
            return this.prev();
        }
        return elem.value;
    }
    
    public Object next() {
        if (this.elem != null) {
            this.elem = this.elem.next;
        }
        while (this.elem != null && this.elem.removed) {
            this.elem = this.elem.next;
        }
        if (this.elem != null) {
            return this.elem.value;
        }
        return null;
    }
    
    public Object prev() {
        cElem elem = null;
        for (cElem cElem = this.list.first; cElem != this.elem; cElem = elem.next) {
            elem = cElem;
        }
        this.elem = elem;
        if (this.elem == null) {
            return null;
        }
        if (this.elem.removed) {
            return this.prev();
        }
        return elem.value;
    }
    
    public Object current() {
        if (this.elem == null) {
            return null;
        }
        if (this.elem.removed) {
            return null;
        }
        return this.elem.value;
    }
    
    public boolean removeCurrent() {
        return this.elem != null && this.list.remove(this.elem);
    }
}

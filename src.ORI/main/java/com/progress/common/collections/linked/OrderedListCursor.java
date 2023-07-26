// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

public class OrderedListCursor extends Cursor
{
    OrderedList list;
    OlElem elem;
    
    public OrderedListCursor(final OrderedList list) {
        this.list = list;
        this.elem = null;
    }
    
    public OrderedListCursor(final OrderedList list, final boolean diveIntoCollections) {
        this.list = list;
        this.elem = null;
        super.diveIntoCollections = diveIntoCollections;
    }
    
    public OrderedListCursor(final OrderedCollection collection) {
        this.list = (OrderedList)collection;
        this.elem = null;
    }
    
    public OrderedListCursor(final OrderedCollection collection, final boolean diveIntoCollections) {
        this.list = (OrderedList)collection;
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
        this.elem = this.list.last;
        while (this.elem != null && this.elem.removed) {
            this.elem = this.elem.prev;
        }
        if (this.elem != null) {
            return this.elem.value;
        }
        return null;
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
        if (this.elem != null) {
            this.elem = this.elem.prev;
        }
        while (this.elem != null && this.elem.removed) {
            this.elem = this.elem.prev;
        }
        if (this.elem != null) {
            return this.elem.value;
        }
        return null;
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

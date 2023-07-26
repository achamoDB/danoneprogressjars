// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

public class OrderedList extends OrderedCollection
{
    OlElem first;
    OlElem last;
    long count;
    long removeCount;
    
    public OrderedList() {
        this.first = null;
        this.last = null;
        this.count = 0L;
        this.removeCount = 0L;
    }
    
    public OrderedList(final Object o) {
        this.first = null;
        this.last = null;
        this.count = 0L;
        this.removeCount = 0L;
        this.add(o);
    }
    
    public long elementCount() {
        return this.count;
    }
    
    protected Cursor makeCursor(final boolean b) {
        return new OrderedListCursor(this, b);
    }
    
    public boolean add(final Object o) {
        return this.addFirst(o);
    }
    
    public boolean addFirst(final Object o) {
        if (this.first == null) {
            final OlElem olElem = new OlElem(o);
            this.last = olElem;
            this.first = olElem;
        }
        else {
            final OlElem olElem2 = new OlElem(o);
            this.first.prev = olElem2;
            olElem2.next = this.first;
            this.first = olElem2;
        }
        ++this.count;
        return true;
    }
    
    public boolean addLast(final Object o) {
        if (this.last == null) {
            final OlElem olElem = new OlElem(o);
            this.first = olElem;
            this.last = olElem;
        }
        else {
            final OlElem olElem2 = new OlElem(o);
            this.last.next = olElem2;
            olElem2.prev = this.last;
            this.last = olElem2;
        }
        ++this.count;
        return true;
    }
    
    protected boolean addAfter(final Object o, final OlElem prev) {
        final OlElem last = new OlElem(o);
        final OlElem next = prev.next;
        if (next != null) {
            next.prev = last;
        }
        prev.next = last;
        last.next = next;
        if ((last.prev = prev) == this.last) {
            this.last = last;
        }
        ++this.count;
        return true;
    }
    
    public boolean addAfter(final Object o, final Object o2) {
        OlElem olElem = null;
        for (OlElem olElem2 = this.first; olElem2 != null; olElem2 = olElem2.next) {
            if (olElem2.value == o2) {
                olElem = olElem2;
                break;
            }
        }
        return olElem != null && this.addAfter(o, olElem);
    }
    
    protected boolean addBefore(final Object o, final OlElem next) {
        final OlElem first = new OlElem(o);
        final OlElem prev = next.prev;
        if (prev != null) {
            prev.next = first;
        }
        next.prev = first;
        first.prev = prev;
        if ((first.next = next) == this.first) {
            this.first = first;
        }
        ++this.count;
        return true;
    }
    
    public boolean addBefore(final Object o, final Object o2) {
        OlElem olElem = null;
        for (OlElem olElem2 = this.first; olElem2 != null; olElem2 = olElem2.next) {
            if (olElem2.value == o2) {
                olElem = olElem2;
                break;
            }
        }
        return olElem != null && this.addBefore(o, olElem);
    }
    
    OlElem find(final Object o) {
        for (OlElem olElem = this.first; olElem != null; olElem = olElem.next) {
            if (o == olElem.value) {
                return olElem;
            }
        }
        return null;
    }
    
    public boolean objectExists(final Object o) {
        return this.find(o) != null;
    }
    
    public boolean remove(final OlElem olElem) {
        if (olElem == null) {
            return false;
        }
        olElem.removed = true;
        if (olElem.prev != null) {
            olElem.prev.next = olElem.next;
        }
        else {
            this.first = olElem.next;
        }
        if (olElem.next != null) {
            olElem.next.prev = olElem.prev;
        }
        else {
            this.last = olElem.prev;
        }
        --this.count;
        ++this.removeCount;
        return true;
    }
    
    public boolean remove(final Object o) {
        for (OlElem olElem = this.first; olElem != null; olElem = olElem.next) {
            if (o == olElem.value) {
                return this.remove(olElem);
            }
        }
        return false;
    }
}

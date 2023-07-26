// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

public class Chain extends OrderedCollection
{
    cElem first;
    long count;
    long removeCount;
    
    public Chain() {
        this.first = null;
        this.count = 0L;
        this.removeCount = 0L;
    }
    
    public Chain(final Object o) {
        this.first = null;
        this.count = 0L;
        this.removeCount = 0L;
        this.add(o);
    }
    
    public long elementCount() {
        return this.count;
    }
    
    protected Cursor makeCursor(final boolean b) {
        return new ChainCursor(this, b);
    }
    
    public boolean add(final Object o) {
        return this.addFirst(o);
    }
    
    public boolean addFirst(final Object o) {
        final cElem first = new cElem(o);
        first.next = this.first;
        this.first = first;
        ++this.count;
        return true;
    }
    
    public boolean addLast(final Object o) {
        final cElem cElem = new cElem(o);
        ++this.count;
        if (this.first == null) {
            this.first = cElem;
            return true;
        }
        cElem cElem2 = this.first;
        for (cElem next = cElem2.next; next != null; next = (cElem2 = cElem2.next)) {}
        cElem2.next = cElem;
        return true;
    }
    
    protected boolean addAfter(final Object o, final cElem cElem) {
        final cElem next = new cElem(o);
        ++this.count;
        cElem.next = next;
        return true;
    }
    
    public boolean addAfter(final Object o, final Object o2) {
        cElem cElem;
        for (cElem = this.first; cElem != null && cElem.value != o2; cElem = cElem.next) {}
        return cElem != null && this.addAfter(o, cElem);
    }
    
    public boolean addBefore(final Object o, final Object o2) {
        cElem cElem = this.first;
        cElem next = cElem.next;
        while (true) {
            while (next != null) {
                if (next.value == o2) {
                    if (next == null) {
                        return false;
                    }
                    final cElem cElem2 = new cElem(o);
                    ++this.count;
                    if (cElem == null) {
                        this.first = cElem2;
                    }
                    else {
                        cElem.next = cElem2;
                    }
                    return true;
                }
                else {
                    next = (cElem = cElem.next);
                }
            }
            continue;
        }
    }
    
    public boolean objectExists(final Object o) {
        for (cElem cElem = this.first; cElem != null; cElem = cElem.next) {
            if (o == cElem.value) {
                return true;
            }
        }
        return false;
    }
    
    public boolean remove(final cElem cElem) {
        if (this.first == null) {
            return false;
        }
        Label_0080: {
            if (cElem != this.first) {
                cElem cElem2 = this.first;
                cElem next = cElem2.next;
                while (true) {
                    while (next != null) {
                        if (next == cElem) {
                            if (next == null) {
                                return false;
                            }
                            cElem2.next = next.next;
                            break Label_0080;
                        }
                        else {
                            next = (cElem2 = cElem2.next);
                        }
                    }
                    continue;
                }
            }
            this.first = this.first.next;
        }
        cElem.removed = true;
        --this.count;
        ++this.removeCount;
        return true;
    }
    
    public boolean remove(final Object o) {
        if (this.first == null) {
            return false;
        }
        cElem first = null;
        Label_0101: {
            if (o != this.first.value) {
                cElem cElem = this.first;
                cElem next = cElem.next;
                while (true) {
                    while (next != null) {
                        if (next.value == o) {
                            if (next == null) {
                                return false;
                            }
                            cElem.next = next.next;
                            first = next;
                            break Label_0101;
                        }
                        else {
                            next = (cElem = cElem.next);
                        }
                    }
                    continue;
                }
            }
            first = this.first;
            this.first = this.first.next;
        }
        first.removed = true;
        --this.count;
        ++this.removeCount;
        return true;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

public class OrderedListUnique extends OrderedList
{
    public OrderedListUnique() {
    }
    
    public OrderedListUnique(final Object o) {
        this.add(o);
    }
    
    public boolean addFirst(final Object o) {
        return !this.objectExists(o) && super.addFirst(o);
    }
    
    public boolean addLast(final Object o) {
        return !this.objectExists(o) && super.addLast(o);
    }
    
    public boolean addAfter(final Object o, final Object o2) {
        OlElem olElem = null;
        for (OlElem olElem2 = super.first; olElem2 != null; olElem2 = olElem2.next) {
            if (olElem2.value == o) {
                return false;
            }
            if (olElem == null && olElem2.value == o2) {
                olElem = olElem2;
            }
        }
        return olElem != null && super.addAfter(o, olElem);
    }
    
    public boolean addBefore(final Object o, final Object o2) {
        OlElem olElem = null;
        for (OlElem olElem2 = super.first; olElem2 != null; olElem2 = olElem2.next) {
            if (olElem2.value == o) {
                return false;
            }
            if (olElem == null && olElem2.value == o2) {
                olElem = olElem2;
            }
        }
        return olElem != null && super.addBefore(o, olElem);
    }
}

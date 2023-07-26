// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

class OlElem
{
    Object value;
    OlElem next;
    OlElem prev;
    boolean removed;
    
    OlElem(final Object value) {
        this.next = null;
        this.prev = null;
        this.removed = false;
        this.value = value;
    }
}

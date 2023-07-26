// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.collections.linked;

class cElem
{
    Object value;
    cElem next;
    boolean removed;
    
    cElem(final Object value) {
        this.next = null;
        this.removed = false;
        this.value = value;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

class ListNode
{
    Object data;
    ListNode next;
    
    ListNode(final Object o) {
        this(o, null);
    }
    
    ListNode(final Object data, final ListNode next) {
        this.data = data;
        this.next = next;
    }
    
    Object getObject() {
        return this.data;
    }
    
    ListNode getnext() {
        return this.next;
    }
}

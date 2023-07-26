// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.io.Serializable;

public class PropertyTransferObject implements Serializable
{
    private int lastElement;
    protected Element[] elements;
    
    PropertyTransferObject(final int n) {
        this.lastElement = -1;
        this.elements = new Element[n];
    }
    
    public Element[] elements() {
        return this.elements;
    }
    
    public int size() {
        return this.elements.length;
    }
    
    public void put(final Object o, final Object o2) {
        ++this.lastElement;
        this.elements[this.lastElement] = new Element(o, o2);
    }
    
    class Element implements Serializable
    {
        private Object key;
        private Object value;
        
        Element(final Object key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        public Object key() {
            return this.key;
        }
        
        public Object value() {
            return this.value;
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.danone2.library;

public class Holder
{
    private Object value;
    
    public Holder() {
        this.value = null;
    }
    
    public Holder(final Object value) {
        this.value = value;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public boolean isNull() {
        return this.value == null;
    }
}

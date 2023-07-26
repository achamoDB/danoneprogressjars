// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class MemptrHolder extends Holder
{
    public MemptrHolder() {
    }
    
    public MemptrHolder(final Memptr value) {
        super.setValue(value);
    }
    
    public void setMemptrValue(final Memptr value) {
        super.setValue(value);
    }
    
    public Memptr getMemptrValue() {
        return (Memptr)super.getValue();
    }
}

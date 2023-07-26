// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class MemptrArrayHolder extends Holder
{
    public MemptrArrayHolder() {
    }
    
    public MemptrArrayHolder(final Memptr[] value) {
        super.setValue(value);
    }
    
    public void setMemptrArrayValue(final Memptr[] value) {
        super.setValue(value);
    }
    
    public Memptr[] getMemptrArrayValue() {
        return (Memptr[])super.getValue();
    }
}

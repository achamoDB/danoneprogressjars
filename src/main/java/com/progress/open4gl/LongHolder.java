// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class LongHolder extends Holder
{
    public LongHolder() {
    }
    
    public LongHolder(final long value) {
        super.setValue(new Long(value));
    }
    
    public void setLongValue(final long value) {
        super.setValue(new Long(value));
    }
    
    public long getLongValue() {
        return (long)super.getValue();
    }
}

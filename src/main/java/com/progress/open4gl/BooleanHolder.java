// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class BooleanHolder extends Holder
{
    public BooleanHolder() {
    }
    
    public BooleanHolder(final boolean value) {
        super.setValue(new Boolean(value));
    }
    
    public void setBooleanValue(final boolean value) {
        super.setValue(new Boolean(value));
    }
    
    public boolean getBooleanValue() {
        return (boolean)super.getValue();
    }
}

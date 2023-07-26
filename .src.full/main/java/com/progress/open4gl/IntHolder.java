// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class IntHolder extends Holder
{
    public IntHolder() {
    }
    
    public IntHolder(final int value) {
        super.setValue(new Integer(value));
    }
    
    public void setIntValue(final int value) {
        super.setValue(new Integer(value));
    }
    
    public int getIntValue() {
        return (int)super.getValue();
    }
}

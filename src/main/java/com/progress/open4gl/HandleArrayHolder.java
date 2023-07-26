// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class HandleArrayHolder extends Holder
{
    public HandleArrayHolder() {
    }
    
    public HandleArrayHolder(final Handle[] value) {
        super.setValue(value);
    }
    
    public void setHandleArrayValue(final Handle[] value) {
        super.setValue(value);
    }
    
    public Handle[] getHandleArrayValue() {
        return (Handle[])super.getValue();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class HandleHolder extends Holder
{
    public HandleHolder() {
    }
    
    public HandleHolder(final Handle value) {
        super.setValue(value);
    }
    
    public void setHandleValue(final Handle value) {
        super.setValue(value);
    }
    
    public Handle getHandleValue() {
        return (Handle)super.getValue();
    }
}

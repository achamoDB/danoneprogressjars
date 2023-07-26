// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class StringHolder extends Holder
{
    public StringHolder() {
    }
    
    public StringHolder(final String value) {
        super.setValue(value);
    }
    
    public void setStringValue(final String value) {
        super.setValue(value);
    }
    
    public String getStringValue() {
        return (String)super.getValue();
    }
}

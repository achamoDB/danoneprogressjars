// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class StringArrayHolder extends Holder
{
    public StringArrayHolder() {
    }
    
    public StringArrayHolder(final String[] value) {
        super.setValue(value);
    }
    
    public void setStringArrayValue(final String[] value) {
        super.setValue(value);
    }
    
    public String[] getStringArrayValue() {
        return (String[])super.getValue();
    }
}

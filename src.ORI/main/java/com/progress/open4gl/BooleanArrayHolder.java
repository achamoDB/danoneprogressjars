// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class BooleanArrayHolder extends Holder
{
    public BooleanArrayHolder() {
    }
    
    public BooleanArrayHolder(final boolean[] value) {
        if (value == null) {
            super.setValue(value);
        }
        else {
            final Boolean[] value2 = new Boolean[value.length];
            for (int i = 0; i < value.length; ++i) {
                value2[i] = new Boolean(value[i]);
            }
            super.setValue(value2);
        }
    }
    
    public BooleanArrayHolder(final Boolean[] value) {
        super.setValue(value);
    }
    
    public void setBooleanArrayValue(final boolean[] value) {
        if (value == null) {
            super.setValue(value);
        }
        else {
            final Boolean[] value2 = new Boolean[value.length];
            for (int i = 0; i < value.length; ++i) {
                value2[i] = new Boolean(value[i]);
            }
            super.setValue(value2);
        }
    }
    
    public void setBooleanArrayValue(final Boolean[] value) {
        super.setValue(value);
    }
    
    public Boolean[] getBooleanArrayValue() {
        return (Boolean[])super.getValue();
    }
}

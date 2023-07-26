// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class LongArrayHolder extends Holder
{
    public LongArrayHolder() {
    }
    
    public LongArrayHolder(final long[] value) {
        if (value == null) {
            super.setValue(value);
        }
        else {
            final Long[] value2 = new Long[value.length];
            for (int i = 0; i < value.length; ++i) {
                value2[i] = new Long(value[i]);
            }
            super.setValue(value2);
        }
    }
    
    public LongArrayHolder(final Long[] value) {
        super.setValue(value);
    }
    
    public void setLongArrayValue(final long[] value) {
        if (value == null) {
            super.setValue(value);
        }
        else {
            final Long[] value2 = new Long[value.length];
            for (int i = 0; i < value.length; ++i) {
                value2[i] = new Long(value[i]);
            }
            super.setValue(value2);
        }
    }
    
    public void setLongArrayValue(final Long[] value) {
        super.setValue(value);
    }
    
    public Long[] getLongArrayValue() {
        return (Long[])super.getValue();
    }
}

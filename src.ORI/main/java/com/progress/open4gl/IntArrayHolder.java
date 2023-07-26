// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class IntArrayHolder extends Holder
{
    public IntArrayHolder() {
    }
    
    public IntArrayHolder(final int[] value) {
        if (value == null) {
            super.setValue(value);
        }
        else {
            final Integer[] value2 = new Integer[value.length];
            for (int i = 0; i < value.length; ++i) {
                value2[i] = new Integer(value[i]);
            }
            super.setValue(value2);
        }
    }
    
    public IntArrayHolder(final Integer[] value) {
        super.setValue(value);
    }
    
    public void setIntArrayValue(final int[] value) {
        if (value == null) {
            super.setValue(value);
        }
        else {
            final Integer[] value2 = new Integer[value.length];
            for (int i = 0; i < value.length; ++i) {
                value2[i] = new Integer(value[i]);
            }
            super.setValue(value2);
        }
    }
    
    public void setIntArrayValue(final Integer[] value) {
        super.setValue(value);
    }
    
    public Integer[] getIntArrayValue() {
        return (Integer[])super.getValue();
    }
}

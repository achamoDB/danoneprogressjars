// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class ByteArrayArrayHolder extends Holder
{
    public ByteArrayArrayHolder() {
    }
    
    public ByteArrayArrayHolder(final byte[][] value) {
        super.setValue(value);
    }
    
    public void setByteArrayArrayValue(final byte[][] value) {
        super.setValue(value);
    }
    
    public byte[][] getByteArrayArrayValue() {
        return (byte[][])super.getValue();
    }
}

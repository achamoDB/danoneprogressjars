// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class ByteArrayHolder extends Holder
{
    public ByteArrayHolder() {
    }
    
    public ByteArrayHolder(final byte[] value) {
        super.setValue(value);
    }
    
    public void setByteArrayValue(final byte[] value) {
        super.setValue(value);
    }
    
    public byte[] getByteArrayValue() {
        return (byte[])super.getValue();
    }
}

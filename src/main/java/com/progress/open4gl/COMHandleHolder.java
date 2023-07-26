// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class COMHandleHolder extends Holder
{
    public COMHandleHolder() {
    }
    
    public COMHandleHolder(final COMHandle value) {
        super.setValue(value);
    }
    
    public void setCOMHandleValue(final COMHandle value) {
        super.setValue(value);
    }
    
    public COMHandle getCOMHandleValue() {
        return (COMHandle)super.getValue();
    }
}

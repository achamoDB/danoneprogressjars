// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class COMHandleArrayHolder extends Holder
{
    public COMHandleArrayHolder() {
    }
    
    public COMHandleArrayHolder(final COMHandle[] value) {
        super.setValue(value);
    }
    
    public void setCOMHandleArrayValue(final COMHandle[] value) {
        super.setValue(value);
    }
    
    public COMHandle[] getCOMHandleArrayValue() {
        return (COMHandle[])super.getValue();
    }
}

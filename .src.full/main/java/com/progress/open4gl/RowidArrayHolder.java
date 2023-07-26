// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class RowidArrayHolder extends Holder
{
    public RowidArrayHolder() {
    }
    
    public RowidArrayHolder(final Rowid[] value) {
        super.setValue(value);
    }
    
    public void setRowidArrayValue(final Rowid[] value) {
        super.setValue(value);
    }
    
    public Rowid[] getRowidArrayValue() {
        return (Rowid[])super.getValue();
    }
}

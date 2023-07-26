// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.util.GregorianCalendar;

public class DateArrayHolder extends Holder
{
    public DateArrayHolder() {
    }
    
    public DateArrayHolder(final GregorianCalendar[] value) {
        super.setValue(value);
    }
    
    public void setDateArrayValue(final GregorianCalendar[] value) {
        super.setValue(value);
    }
    
    public GregorianCalendar[] getDateArrayValue() {
        return (GregorianCalendar[])super.getValue();
    }
}

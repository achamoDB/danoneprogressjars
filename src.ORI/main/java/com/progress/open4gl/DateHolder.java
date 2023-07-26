// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.util.GregorianCalendar;

public class DateHolder extends Holder
{
    public DateHolder() {
    }
    
    public DateHolder(final GregorianCalendar value) {
        super.setValue(value);
    }
    
    public void setDateValue(final GregorianCalendar value) {
        super.setValue(value);
    }
    
    public GregorianCalendar getDateValue() {
        return (GregorianCalendar)super.getValue();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.util.GregorianCalendar;

public class DateTimeTZHolder
{
    private GregorianCalendar m_calData;
    
    public DateTimeTZHolder(final GregorianCalendar calData) {
        this.m_calData = null;
        this.m_calData = calData;
    }
    
    public GregorianCalendar getCalData() {
        return this.m_calData;
    }
}

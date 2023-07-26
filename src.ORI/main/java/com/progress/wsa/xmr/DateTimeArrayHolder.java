// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.util.GregorianCalendar;

public class DateTimeArrayHolder
{
    private GregorianCalendar[] m_calArray;
    
    public DateTimeArrayHolder(final GregorianCalendar[] calArray) {
        this.m_calArray = null;
        this.m_calArray = calArray;
    }
    
    public GregorianCalendar[] getCalArray() {
        return this.m_calArray;
    }
}

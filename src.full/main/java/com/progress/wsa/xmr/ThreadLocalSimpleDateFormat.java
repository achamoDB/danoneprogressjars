// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class ThreadLocalSimpleDateFormat extends ThreadLocal
{
    private String format;
    
    public ThreadLocalSimpleDateFormat(final String format) {
        this.format = format;
    }
    
    public String format(final Date date) {
        return this.get().format(date);
    }
    
    public Date parse(final String source) throws ParseException {
        return this.get().parse(source);
    }
    
    protected Object initialValue() {
        return new SimpleDateFormat(this.format);
    }
}

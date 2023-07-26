// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.event;

import com.progress.common.exception.ProException;

public class ExceptionEvent extends LogEvent
{
    public ExceptionEvent(final Object o, final Throwable t) {
        super(o, "%s", new Object[] { (t instanceof ProException) ? t.getMessage() : (t.toString() + " " + t.getMessage()) });
    }
    
    public String getExceptionMessage() {
        return (String)super.getArgument(0);
    }
}

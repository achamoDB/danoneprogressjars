// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import com.progress.common.exception.ProError;

public class Open4GLError extends ProError
{
    public Open4GLError(final long n, final Object[] array) {
        super((n <= 70L) ? MessageMap.getMessage(n) : n, array);
        RunTimeProperties.tracer.print(this, 1);
    }
    
    public Open4GLError() {
        super(MessageMap.getMessage(1L), null);
        RunTimeProperties.tracer.print(this, 1);
    }
    
    public long getMessageId() {
        return 0xFFFFFFFFFFFFL & super.getMessageId();
    }
}

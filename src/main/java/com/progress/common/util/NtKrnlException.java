// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import com.progress.common.exception.ProException;

public class NtKrnlException extends ProException
{
    public NtKrnlException(final String s) {
        super("NtKrnlException", new Object[] { s });
    }
    
    public String getDetail() {
        return (String)this.getArgument(0);
    }
}

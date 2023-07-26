// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import com.progress.common.exception.ProException;

public class XChimeraException extends ProException
{
    public XChimeraException() {
        this("");
    }
    
    public XChimeraException(final String s) {
        super(s, new Object[0]);
    }
}

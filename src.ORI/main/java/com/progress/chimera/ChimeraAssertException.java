// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera;

public class ChimeraAssertException extends ChimeraException
{
    public ChimeraAssertException() {
    }
    
    public ChimeraAssertException(final String str) {
        super("ASSERT FAILED: " + str);
    }
}

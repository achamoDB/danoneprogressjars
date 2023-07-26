// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera;

public class ChimeraNotImplementedException extends ChimeraException
{
    public ChimeraNotImplementedException() {
    }
    
    public ChimeraNotImplementedException(final String str) {
        super("NOT IMPLEMENTED: " + str);
    }
}

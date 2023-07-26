// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class SchemaValidationException extends Open4GLException
{
    public SchemaValidationException(final long n, final Object[] array) {
        super((n <= 70L) ? MessageMap.getMessage(n) : n, array);
        RunTimeProperties.tracer.print(this, 1);
    }
}

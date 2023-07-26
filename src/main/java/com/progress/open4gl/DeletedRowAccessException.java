// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

public class DeletedRowAccessException extends ProSQLException
{
    public DeletedRowAccessException(final OutputSetException ex, final String s) {
        super(ex, s);
    }
    
    public DeletedRowAccessException() {
    }
    
    public DeletedRowAccessException(final String s) {
        super(s);
    }
}

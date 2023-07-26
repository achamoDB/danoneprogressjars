// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.InputStream;

public class SchemaBuilder
{
    private ResultSetSchema schemas;
    
    public SchemaBuilder(final InputStream inputStream, final int n) throws ClientException, FGLErrorException {
        this.schemas = new BuildDynamicSchema(inputStream).build(n);
    }
    
    public ResultSetSchema getSchema() {
        return this.schemas;
    }
}

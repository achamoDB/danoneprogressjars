// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.IOException;
import java.io.InputStream;

class BuildDynamicSchema
{
    private SchemaReader reader;
    
    BuildDynamicSchema(final InputStream inputStream) throws ClientException, FGLErrorException {
        Object o = null;
        try {
            final int read = inputStream.read();
            if (read == 7) {
                o = new FGLErrorException();
            }
            else if (read == 10) {
                o = new VersionErrorException(inputStream.read());
            }
            else if (read != 8) {
                throw new ClientException(2, 16L, new Object[] { new Integer(read).toString() });
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
        if (o != null) {
            throw o;
        }
        this.reader = new SchemaReader(inputStream);
    }
    
    ResultSetSchema build(final int n) throws ClientException {
        final ResultSetSchema resultSetSchema = new ResultSetSchema();
        for (int i = 0; i < n; ++i) {
            final MetaDataIndicator next = this.reader.next();
            if (next == null) {
                return null;
            }
            resultSetSchema.addSchema(next);
        }
        return resultSetSchema;
    }
}

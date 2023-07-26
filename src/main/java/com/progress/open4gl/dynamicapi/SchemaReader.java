// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import com.progress.open4gl.ProSQLException;
import java.io.InputStream;

class SchemaReader
{
    private InputStream stream;
    
    SchemaReader(final InputStream stream) {
        this.stream = stream;
    }
    
    MetaDataIndicator next() throws ClientException {
        if (this.stream == null) {
            return null;
        }
        try {
            final ResultSet set = new ResultSet(TableParamMetaSchema.metaData, new StreamReader(this.stream, 9));
            if (!set.next()) {
                return null;
            }
            final int int1 = set.getInt(1);
            final int int2 = set.getInt(2);
            final int int3 = set.getInt(3);
            final ResultSetMetaData resultSetMetaData = new ResultSetMetaData(int1, set.getInt(4));
            final MetaDataIndicator metaDataIndicator = new MetaDataIndicator(resultSetMetaData, int2, int3);
            if (set.next()) {
                throw new ClientException(2, 17L, null);
            }
            final ResultSet set2 = new ResultSet(FieldParamMetaSchema.metaData, new StreamReader(this.stream, 9));
            int n = 1;
            while (set2.next()) {
                resultSetMetaData.setFieldDesc(n, set2.getString(3), set2.getInt(2), set2.getInt(1));
                ++n;
            }
            return metaDataIndicator;
        }
        catch (ProSQLException ex) {
            throw ExceptionConverter.convertFromProSQLException(ex);
        }
    }
}

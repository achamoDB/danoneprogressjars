// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.IOException;
import java.sql.ResultSet;
import com.progress.open4gl.Open4GLError;
import java.util.Vector;
import java.io.OutputStream;

public class InputTableStreamerNoSchema extends InputTableStreamer
{
    public InputTableStreamerNoSchema(final OutputStream outputStream) {
        super(outputStream);
    }
    
    public void streamOut(final ParameterSet set, final Vector vector, final int n) {
        throw new Open4GLError();
    }
    
    public void streamOut(final ParameterSet set) throws ClientException {
        for (int i = 1; i <= set.getNumParams(); ++i) {
            if (set.isResultSet(i) && set.isInput(i)) {
                this.DoStreamResultSet((ResultSet)set.getValue(i), null, i - 1, false);
            }
        }
    }
    
    public void streamResultSet(final ResultSet set, final ResultSetMetaData resultSetMetaData, final int n, final int n2) {
        throw new Open4GLError();
    }
    
    protected void streamRow(final ResultSet set, final ResultSetMetaData resultSetMetaData, final boolean b, final int n) throws ClientException {
        try {
            super.stream.write(2);
            int n2 = 1;
            while (true) {
                Object object;
                try {
                    object = set.getObject(n2);
                }
                catch (Exception ex2) {
                    break;
                }
                catch (Error error) {
                    break;
                }
                final int validateInputValue = JdbcDataType.validateInputValue(object);
                super.streamer.streamColumn(JdbcDataType.convertInputObject(object, validateInputValue), validateInputValue);
                ++n2;
            }
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.dynamicapi.JdbcDataType;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import java.sql.ResultSet;
import java.io.OutputStream;
import com.progress.open4gl.dynamicapi.InputTableStreamer;

public class OutputParameterStreamer extends InputTableStreamer
{
    public OutputParameterStreamer(final OutputStream outputStream) {
        super(outputStream);
    }
    
    protected void streamRow(final ResultSet set, final ResultSetMetaData resultSetMetaData, final boolean b, final int n) {
        try {
            super.stream.write(2);
            Object object = null;
            Integer n2 = null;
            Object object2 = null;
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); ++i) {
                switch (i) {
                    case 1: {
                        n2 = (Integer)set.getObject(i);
                        break;
                    }
                    case 2: {
                        object = set.getObject(i);
                        break;
                    }
                    case 3: {
                        object2 = set.getObject(i);
                        break;
                    }
                    case 4: {
                        break;
                    }
                    case 5: {
                        break;
                    }
                    case 6: {
                        break;
                    }
                    default: {
                        throw new Open4GLError();
                    }
                }
            }
            super.streamer.streamColumn(n2, resultSetMetaData.getProColumnType(1));
            super.streamer.streamColumn(object, JdbcDataType.defaultType(n2));
            super.streamer.streamColumn(object2, resultSetMetaData.getProColumnType(3));
        }
        catch (Exception ex) {}
    }
}

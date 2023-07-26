// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.util.GregorianCalendar;
import com.progress.open4gl.Handle;
import com.progress.open4gl.COMHandle;
import java.io.IOException;
import com.progress.open4gl.ProSQLException;
import com.progress.open4gl.Parameter;
import java.lang.reflect.Array;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.Open4GLError;
import java.sql.ResultSet;
import java.io.OutputStream;

public class InputParameterStreamer extends InputTableStreamer
{
    public InputParameterStreamer(final OutputStream outputStream, final boolean b) {
        super(outputStream, b);
    }
    
    protected void streamRow(final ResultSet set, final ResultSetMetaData resultSetMetaData, final boolean b, final int i) throws ClientException {
        final ParameterResultSet set2 = (ParameterResultSet)set;
        try {
            Object object = null;
            Integer n = null;
            Object object2 = null;
            Boolean b2 = null;
            Integer n2 = null;
            Integer n3 = null;
            for (int j = 1; j <= resultSetMetaData.getColumnCount(); ++j) {
                switch (j) {
                    case 1: {
                        n = (Integer)set2.getObject(j);
                        break;
                    }
                    case 2: {
                        object = set2.getObject(j);
                        break;
                    }
                    case 3: {
                        object2 = set2.getObject(j);
                        break;
                    }
                    case 4: {
                        b2 = (Boolean)set2.getObject(j);
                        break;
                    }
                    case 5: {
                        n2 = (Integer)set2.getObject(j);
                        break;
                    }
                    case 6: {
                        n3 = (Integer)set2.getObject(j);
                        break;
                    }
                    default: {
                        throw new Open4GLError();
                    }
                }
            }
            if (set2.isMappedTable()) {
                n = new Integer(set2.getMappedTableType());
            }
            if (b2) {
                super.stream.write(16);
            }
            else {
                super.stream.write(2);
            }
            if (RunTimeProperties.isTracing()) {
                String s = null;
                if (b2 && object != null) {
                    for (int k = 0; k < Array.getLength(object); ++k) {
                        s = s + this.getValueString(Array.get(object, k)) + " ";
                    }
                }
                else {
                    s = this.getValueString(object);
                }
                RunTimeProperties.tracer.print("\t" + ParameterSet.inOutString((int)object2) + " " + i + ":\tType: " + Parameter.proToName(n) + "   Value: " + s, 3);
            }
            super.streamer.streamColumn(n, resultSetMetaData.getProColumnType(1), n3);
            if (b2) {
                int value = n2;
                if (value > 0 && object != null) {
                    final int length = Array.getLength(object);
                    if (value != length) {
                        throw new ClientException(4, 7665970990714728441L, new Object[] { new Integer(length).toString(), new Integer(value).toString() });
                    }
                }
                if (value == 0) {
                    if (object != null) {
                        n2 = new Integer(Array.getLength(object));
                    }
                    else {
                        n2 = new Integer(-32767);
                    }
                    value = n2;
                }
                super.streamer.streamColumn(n2, 4);
                Object value2 = null;
                if (value > 0) {
                    for (int l = 0; l < value; ++l) {
                        if (object != null) {
                            value2 = Array.get(object, l);
                        }
                        super.streamer.streamColumn(value2, JdbcDataType.defaultType(n));
                    }
                }
                else {
                    super.stream.write(3);
                }
            }
            else {
                super.streamer.streamColumn(object, JdbcDataType.defaultType(n), n3);
            }
            super.streamer.streamColumn(object2, resultSetMetaData.getProColumnType(3));
        }
        catch (ProSQLException ex2) {
            throw new Open4GLError();
        }
        catch (IOException ex) {
            ExceptionConverter.convertIOException(ex);
        }
    }
    
    private String getValueString(final Object o) {
        String s;
        if (o != null) {
            s = o.toString();
        }
        else {
            s = "null";
        }
        if (o != null) {
            if (o instanceof COMHandle) {
                s = new Long(((COMHandle)o).getLong()).toString();
            }
            if (o instanceof Handle) {
                s = new Long(((Handle)o).getLong()).toString();
            }
            if (o instanceof GregorianCalendar) {
                s = ((GregorianCalendar)o).getTime().toString();
            }
        }
        return s;
    }
}

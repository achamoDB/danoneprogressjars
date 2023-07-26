// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Handle;
import com.progress.open4gl.Memptr;
import java.util.GregorianCalendar;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.ProSQLException;
import java.io.IOException;
import java.io.InputStream;

class OutputParamReader
{
    private InputStream stream;
    private ResultSet outputSet;
    private int proType;
    private Object value;
    private boolean noValue;
    private Session m_session;
    
    OutputParamReader(final InputStream stream, final Session session) throws ClientException, FGLErrorException {
        this.stream = stream;
        this.m_session = session;
        Object o = null;
        try {
            this.stream.read();
            this.stream.read();
            this.stream.read();
            final int read = this.stream.read();
            final int read2 = this.stream.read();
            if ((read == 7 || read == 8) && read2 != 1) {
                throw new ClientException(2, 16L, new Object[] { new Integer(read2).toString() });
            }
            if (read2 == 1) {
                this.stream.read();
                this.stream.read();
            }
            if (read == 7) {
                o = new FGLErrorException();
            }
            else if (read == 10) {
                o = new VersionErrorException(read2);
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
        this.outputSet = new ResultSet(ParameterMetaData.metaData, new StreamReader(this.stream, this.m_session.getServerMajorVersion()));
        try {
            this.outputSet.next();
            final Integer n = (Integer)this.outputSet.getParamObject(2, 4);
            this.outputSet.next();
            final Integer n2 = (Integer)this.outputSet.getParamObject(2, 4);
        }
        catch (ProSQLException ex2) {
            throw ExceptionConverter.convertFromProSQLException(ex2);
        }
    }
    
    boolean nextOutputParam(final int proType, final boolean b, final boolean b2, final boolean b3, final int n) throws ClientException {
        this.proType = proType;
        this.noValue = false;
        try {
            if (!this.outputSet.next()) {
                return false;
            }
            final int int1 = this.outputSet.getInt(1);
            if (b2) {
                int int2 = this.outputSet.getInt(2);
                if (int2 == -32767) {
                    int2 = 1;
                }
                else if (int2 < 0) {
                    int2 = -int2;
                }
                this.value = this.createArrayValue(int1, int2, b3);
                if (b && n != 0 && n != int2) {
                    throw new ClientException(4, 7665970990714730932L, null);
                }
            }
            else {
                this.value = this.outputSet.getParamObject(2, int1);
            }
            if (this.value == null && this.outputSet.noReturnValue()) {
                this.noValue = true;
            }
            if (b && int1 != 0 && int1 != 16 && this.proType != int1) {
                throw new Open4GLError(65L, null);
            }
            return true;
        }
        catch (ProSQLException ex) {
            throw ExceptionConverter.convertFromProSQLException(ex);
        }
    }
    
    int getProType() {
        return this.proType;
    }
    
    ResultSet getOutputSet() {
        return this.outputSet;
    }
    
    void setOutputSet(final ResultSet outputSet) {
        this.outputSet = outputSet;
    }
    
    InputStream getStream() {
        return this.stream;
    }
    
    Session getSession() {
        return this.m_session;
    }
    
    Object getValue() {
        return this.value;
    }
    
    boolean noReturnValue() {
        return this.noValue;
    }
    
    private Object createArrayValue(final int n, final int n2, final boolean b) throws ClientException {
        int[] array = null;
        long[] array2 = null;
        boolean[] array3 = null;
        Object o = null;
        switch (n) {
            case 2:
            case 34:
            case 40: {
                o = new GregorianCalendar[n2];
                break;
            }
            case 4: {
                if (b) {
                    array = (int[])(o = new int[n2]);
                    break;
                }
                o = new Integer[n2];
                break;
            }
            case 11: {
                o = new Memptr[n2];
                break;
            }
            case 7:
            case 41: {
                if (b) {
                    array2 = (long[])(o = new long[n2]);
                    break;
                }
                o = new Long[n2];
                break;
            }
            case 10: {
                o = new Handle[n2];
                break;
            }
            case 14: {
                o = new COMHandle[n2];
                break;
            }
            case 8: {
                o = new byte[n2][];
                break;
            }
            case 13: {
                o = new Rowid[n2];
                break;
            }
            case 1:
            case 39: {
                o = new String[n2];
                break;
            }
            case 3: {
                if (b) {
                    array3 = (boolean[])(o = new boolean[n2]);
                    break;
                }
                o = new Boolean[n2];
                break;
            }
            case 5: {
                o = new BigDecimal[n2];
                break;
            }
            default: {
                throw new Open4GLError(65L, null);
            }
        }
        try {
            for (int i = 1; i <= n2; ++i) {
                final Object paramObject = this.outputSet.getParamObject(i + 2, n);
                if (paramObject != null) {
                    if (b) {
                        switch (n) {
                            case 4: {
                                array[i - 1] = (int)paramObject;
                                break;
                            }
                            case 7:
                            case 41: {
                                array2[i - 1] = (long)paramObject;
                                break;
                            }
                            case 3: {
                                array3[i - 1] = (boolean)paramObject;
                                break;
                            }
                        }
                    }
                    else {
                        Array.set(o, i - 1, paramObject);
                    }
                }
            }
        }
        catch (ProSQLException ex) {
            throw ExceptionConverter.convertFromProSQLException(ex);
        }
        return o;
    }
}

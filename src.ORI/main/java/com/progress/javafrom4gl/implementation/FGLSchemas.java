// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.dynamicapi.FieldParamMetaSchema;
import com.progress.open4gl.dynamicapi.Util;
import com.progress.open4gl.dynamicapi.ResultSet;
import com.progress.open4gl.dynamicapi.StreamReader;
import com.progress.open4gl.dynamicapi.TableParamMetaSchema;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import java.io.InputStream;
import com.progress.javafrom4gl.Log;
import com.progress.open4gl.dynamicapi.ResultSetSchema;

class FGLSchemas
{
    private static final int MARSHAL_VERS9 = 1;
    private static final int MARSHAL_ADO = 2;
    private static final int MARSHAL_MAX = 3;
    private static final int MARSHAL_NOSCH = 4;
    private static final int MARSHAL_NOUNDO = 1;
    private static final int MARSHAL_HASBIMAGE = 2;
    private static final int MARSHAL_HASERROR = 4;
    private static final int MARSHAL_REJECTED = 8;
    private static final int MARSHAL_DATASOURCE_MOD = 16;
    private static final int MARSHAL_LAST_BATCH = 32;
    private ResultSetSchema schemas;
    private int clntVer;
    private int numTTOutParms;
    private Log log;
    
    FGLSchemas(final InputStream inputStream, final FGLParameters fglParameters, final int clntVer, final Log log) throws Exception {
        this.schemas = new ResultSetSchema();
        this.clntVer = clntVer;
        this.log = log;
        this.numTTOutParms = 0;
        for (int num4GLParameters = fglParameters.getNum4GLParameters(), i = 1; i <= num4GLParameters; ++i) {
            if (fglParameters.getOutputProType(i) == 15) {
                this.schemas.addSchema(this.createMetaData(inputStream), i, fglParameters.getOutputInOut(i));
            }
        }
    }
    
    int getNumTTOutParms() {
        return this.numTTOutParms;
    }
    
    ResultSetSchema getSchemas() {
        return this.schemas;
    }
    
    private ResultSetMetaData createMetaData(final InputStream inputStream) throws Exception {
        inputStream.read();
        final byte b = (byte)inputStream.read();
        final byte b2 = (byte)inputStream.read();
        if (inputStream.read() != 1) {
            throw new Error("FGLSchemas: Protocol error 0");
        }
        if ((byte)inputStream.read() != 0) {
            throw new Error("FGLSchemas: Protocol error 7");
        }
        final int read = inputStream.read();
        int n;
        ResultSetMetaData resultSetMetaData;
        if (this.clntVer > 9 && read != 1) {
            ResultSet set = null;
            switch (read) {
                case 2: {
                    set = new ResultSet(TableParamMetaSchema.metaDataV2, new StreamReader(inputStream, this.clntVer));
                    break;
                }
                case 3: {
                    set = new ResultSet(TableParamMetaSchema.metaDataV3, new StreamReader(inputStream, this.clntVer));
                    break;
                }
                case 4: {
                    set = new ResultSet(TableParamMetaSchema.metaDataV4, new StreamReader(inputStream, this.clntVer));
                    break;
                }
                default: {
                    throw new Error("FGLSchemas: Protocol error 5");
                }
            }
            if (!set.next()) {
                throw new Error("FGLSchemas: Protocol error 1");
            }
            final int int1 = set.getInt(1);
            set.getInt(2);
            if (set.getInt(3) != 1) {
                ++this.numTTOutParms;
            }
            n = set.getInt(4);
            set.getString(5);
            set.getString(6);
            set.getInt(7);
            set.getInt(8);
            set.getString(9);
            set.getInt(10);
            if (read != 4) {
                set.getString(11);
                set.getString(12);
                set.getString(13);
                set.getString(14);
                if (read == 3) {
                    set.getString(15);
                }
                if (inputStream.read() != 1) {
                    throw new Error("FGLSchemas: Protocol error 4");
                }
                for (int short1 = Util.extractShort((byte)inputStream.read(), (byte)inputStream.read()), short2 = Util.extractShort((byte)inputStream.read(), (byte)inputStream.read()), i = 0; i < short1 + short2; ++i) {
                    inputStream.read();
                }
                if (inputStream.read() != 1) {
                    throw new Error("FGLSchemas: Protocol error 6");
                }
                final byte b3 = (byte)inputStream.read();
                final byte b4 = (byte)inputStream.read();
            }
            resultSetMetaData = new ResultSetMetaData(int1, n);
        }
        else {
            final ResultSet set2 = new ResultSet(TableParamMetaSchema.metaData, new StreamReader(inputStream, 9));
            if (!set2.next()) {
                throw new Error("FGLSchemas: Protocol error 1");
            }
            final int int2 = set2.getInt(1);
            set2.getInt(2);
            set2.getInt(3);
            n = set2.getInt(4);
            resultSetMetaData = new ResultSetMetaData(int2, n);
            if (set2.next()) {
                throw new Error("FGLSchemas: Protocol error 2");
            }
        }
        if (this.clntVer > 9 && read != 1) {
            ResultSet set3 = null;
            switch (read) {
                case 2: {
                    set3 = new ResultSet(FieldParamMetaSchema.metaDataV2, new StreamReader(inputStream, this.clntVer));
                    break;
                }
                case 3: {
                    set3 = new ResultSet(FieldParamMetaSchema.metaDataV3, new StreamReader(inputStream, this.clntVer));
                    break;
                }
                case 4: {
                    return resultSetMetaData;
                }
                default: {
                    throw new Error("FGLSchemas: Protocol error 3");
                }
            }
            for (int j = 0; j < n; ++j) {
                set3.next();
                final int int3 = set3.getInt(1);
                final int int4 = set3.getInt(2);
                String s = set3.getString(3);
                if (s == null) {
                    s = "unknown" + j;
                }
                set3.getInt(4);
                final int int5 = set3.getInt(5);
                set3.getInt(6);
                set3.getString(7);
                set3.getString(8);
                final int int6 = set3.getInt(9);
                set3.getString(10);
                set3.getString(11);
                if (read == 3) {
                    set3.getString(12);
                    set3.getString(13);
                    set3.getString(14);
                }
                resultSetMetaData.setFieldDesc(j + 1, s, int4, int3, int5, int6);
            }
        }
        else {
            final ResultSet set4 = new ResultSet(FieldParamMetaSchema.metaData, new StreamReader(inputStream, 9));
            for (int k = 0; k < n; ++k) {
                if (!set4.next()) {
                    throw new Error("FGLSchemas: Protocol error 3");
                }
                final int int7 = set4.getInt(1);
                final int int8 = set4.getInt(2);
                String s2 = set4.getString(3);
                if (s2 == null) {
                    s2 = "unknown" + k;
                }
                resultSetMetaData.setFieldDesc(k + 1, s2, int8, int7);
            }
        }
        return resultSetMetaData;
    }
}

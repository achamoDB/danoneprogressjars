// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import java.sql.ResultSet;
import com.progress.open4gl.Open4GLError;
import com.progress.open4gl.dynamicapi.ParameterSet;
import java.io.OutputStream;
import com.progress.open4gl.dynamicapi.InputTableStreamerNoSchema;

class OutputSetsStreamer extends InputTableStreamerNoSchema
{
    OutputSetsStreamer(final OutputStream outputStream) {
        super(outputStream);
    }
    
    public void streamOut(final ParameterSet set) {
        throw new Open4GLError();
    }
    
    void streamOut(final FGLParameters fglParameters) throws Exception {
        for (int num4GLParameters = fglParameters.getNum4GLParameters(), i = 0; i < num4GLParameters; ++i) {
            final int n = i + 1;
            final int outputProType = fglParameters.getOutputProType(n);
            final boolean b = fglParameters.getOutputInOut(n) != 1;
            if (outputProType == 15 && b) {
                this.DoStreamResultSet((ResultSet)fglParameters.getOutputValue(n), null, i, true);
            }
        }
    }
}

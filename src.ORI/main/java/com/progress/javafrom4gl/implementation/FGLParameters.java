// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.Rowid;
import com.progress.open4gl.Holder;
import com.progress.open4gl.dynamicapi.ResultSet;
import com.progress.open4gl.dynamicapi.StreamReader;
import com.progress.open4gl.dynamicapi.ParameterMetaData;
import com.progress.javafrom4gl.Log;
import java.io.InputStream;

class FGLParameters
{
    static final int NOTYPE = 111;
    private Parameters parameters;
    private boolean hasTargetObject;
    private boolean hasReturnValue;
    private boolean hasOutputTableParam;
    private boolean hasInputTableParam;
    private int firstJavaParameter;
    
    FGLParameters(final InputStream inputStream, final int n, final boolean hasTargetObject, final boolean hasReturnValue, final int n2, final Log log) throws Exception {
        this.hasTargetObject = hasTargetObject;
        this.hasReturnValue = hasReturnValue;
        this.hasOutputTableParam = false;
        this.hasInputTableParam = false;
        if (hasTargetObject & hasReturnValue) {
            this.firstJavaParameter = 2;
        }
        else if (hasTargetObject || hasReturnValue) {
            this.firstJavaParameter = 1;
        }
        else {
            this.firstJavaParameter = 0;
        }
        final ResultSet set = new ResultSet(ParameterMetaData.metaData, new StreamReader(inputStream, n2));
        this.parameters = new Parameters(n, this.firstJavaParameter);
        for (int i = 0; i < n; ++i) {
            if (!set.next()) {
                throw new Error("Not enough parameters");
            }
            Object paramObject = null;
            final int int1 = set.getInt(1);
            if (int1 != 15) {
                paramObject = set.getParamObject(2, int1);
            }
            final int int2 = set.getInt(3);
            if (int1 == 15) {
                if (int2 != 1) {
                    this.hasOutputTableParam = true;
                }
                if (int2 != 2) {
                    this.hasInputTableParam = true;
                }
            }
            final boolean b = hasReturnValue && this.firstJavaParameter - 1 == i;
            if (int2 != 1) {
                final Holder objectForProOutType = DataTypeMap.objectForProOutType(int1);
                objectForProOutType.setValue(paramObject);
                paramObject = objectForProOutType;
            }
            this.parameters.setInputVal(i, int1, paramObject, int2, b);
        }
    }
    
    boolean hasOutTableParameter() {
        return this.hasOutputTableParam;
    }
    
    boolean hasInTableParameter() {
        return this.hasInputTableParam;
    }
    
    boolean hasTableParameter() {
        return this.hasOutputTableParam || this.hasInputTableParam;
    }
    
    int getNumJavaParameters() {
        return this.parameters.getNumJavaParameters();
    }
    
    int getNum4GLParameters() {
        return this.parameters.getNum4GLParameters();
    }
    
    Rowid getTargetreference() {
        if (this.hasTargetObject) {
            return (Rowid)this.parameters.getTargetObjectValue().inputValue;
        }
        return null;
    }
    
    boolean returnedRef() {
        return false;
    }
    
    int getReturnProType() {
        if (this.hasReturnValue) {
            return this.parameters.getReturnParameter(this.hasTargetObject).inputProType;
        }
        throw new Error("No return value");
    }
    
    int getInputProType(final int n) {
        return this.parameters.getInputParamValue(n).inputProType;
    }
    
    Object getInputValue(final int n) {
        return this.parameters.getInputParamValue(n).inputValue;
    }
    
    int getInputInOut(final int n) {
        return this.parameters.getInputParamValue(n).inputInOut;
    }
    
    int getOutputProType(final int n) {
        return this.parameters.getOutputParamValue(n).inputProType;
    }
    
    Object getOutputValue(final int n) {
        return this.parameters.getOutputParamValue(n).outputValue;
    }
    
    int getOutputInOut(final int n) {
        return this.parameters.getOutputParamValue(n).inputInOut;
    }
    
    void setOutputData(final Object[] array, final Object o) {
        this.parameters.setOutputVal(this.firstJavaParameter - 1, o, true);
        int firstJavaParameter = this.firstJavaParameter;
        for (int i = 0; i < array.length; ++i) {
            this.parameters.setOutputVal(firstJavaParameter, array[i], false);
            ++firstJavaParameter;
        }
    }
    
    static class Parameters
    {
        private ParamValue[] values;
        private int firstJavaParameter;
        
        Parameters(final int n, final int firstJavaParameter) {
            this.firstJavaParameter = firstJavaParameter;
            this.values = new ParamValue[n];
        }
        
        int getNumJavaParameters() {
            return this.values.length - this.firstJavaParameter;
        }
        
        int getNum4GLParameters() {
            return this.values.length;
        }
        
        void setOutputVal(final int n, final Object outputValue, final boolean b) {
            final ParamValue paramValue = this.values[n];
            if (b && paramValue.inputProType == 16) {
                return;
            }
            paramValue.outputValue = outputValue;
        }
        
        void setInputVal(final int n, int n2, final Object o, final int n3, final boolean b) throws Exception {
            if (n2 == 111) {
                n2 = 16;
            }
            this.values[n] = new ParamValue(n2, o, n3);
            if (b && n3 != 2 && n2 != 16) {
                throw new Exception("The parameter for passing the return value must be OUTPUT (or UNKNOWN when calling a void method).");
            }
        }
        
        ParamValue getInputParamValue(final int n) {
            return this.values[n - 1 + this.firstJavaParameter];
        }
        
        ParamValue getOutputParamValue(final int n) {
            return this.values[n - 1];
        }
        
        ParamValue getTargetObjectValue() {
            return this.values[0];
        }
        
        ParamValue getReturnParameter(final boolean b) {
            if (b) {
                return this.values[1];
            }
            return this.values[0];
        }
    }
    
    static class ParamValue
    {
        int inputProType;
        Object inputValue;
        int inputInOut;
        Object outputValue;
        
        ParamValue(final int inputProType, final Object inputValue, final int inputInOut) {
            this.inputProType = inputProType;
            this.inputValue = inputValue;
            this.inputInOut = inputInOut;
            this.outputValue = null;
        }
    }
}

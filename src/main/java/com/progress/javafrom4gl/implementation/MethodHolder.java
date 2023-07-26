// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import com.progress.open4gl.dynamicapi.Util;
import java.lang.reflect.InvocationTargetException;
import com.progress.open4gl.Holder;
import com.progress.open4gl.dynamicapi.ResultSet;
import com.progress.open4gl.dynamicapi.StreamReader;
import com.progress.javafrom4gl.ServiceRuntime;
import java.io.InputStream;
import com.progress.javafrom4gl.Log;
import java.lang.reflect.Method;

class MethodHolder
{
    private String methodName;
    private Method method;
    private Object[] inputValues;
    private Class[] typesArray;
    private Class returnType;
    private Object[] outputValues;
    private Object returnValue;
    private Log log;
    private static Class holderClass;
    
    MethodHolder(final String methodName, final FGLParameters fglParameters, final ReferenceList list, final FGLSchemas fglSchemas, final InputStream inputStream, final int n) throws Exception {
        this.log = ServiceRuntime.getLog();
        this.methodName = methodName;
        final int numJavaParameters = fglParameters.getNumJavaParameters();
        final int n2 = fglParameters.getNum4GLParameters() - numJavaParameters + 1;
        this.typesArray = new Class[numJavaParameters];
        this.inputValues = new Object[numJavaParameters];
        ResultSet prev = null;
        if (fglParameters.hasInTableParameter()) {
            if (inputStream.read() != 1) {
                throw new Error("MethodHolder: Protocol error 0");
            }
            inputStream.read();
            inputStream.read();
        }
        for (int i = 0; i < numJavaParameters; ++i) {
            final int n3 = i + 1;
            final int inputProType = fglParameters.getInputProType(n3);
            final boolean b = fglParameters.getInputInOut(n3) == 1;
            final boolean b2 = fglParameters.getInputInOut(n3) == 2;
            this.typesArray[i] = DataTypeMap.classForProType(inputProType, b);
            Object inputValue = fglParameters.getInputValue(n3);
            if (inputProType == 15 && !b2) {
                final ResultSet set = new ResultSet(fglSchemas.getSchemas().getSchema(n3 + n2 - 1), new StreamReader(inputStream, 9), n3);
                if (prev != null) {
                    prev.setNext(set);
                }
                set.setPrev(prev);
                prev = set;
                if (!b) {
                    ((Holder)inputValue).setValue(set);
                }
                else {
                    inputValue = set;
                }
            }
            this.inputValues[i] = inputValue;
        }
    }
    
    void setMethod(final Method method) {
        this.method = method;
    }
    
    void invoke(final Object obj) throws Exception {
        try {
            this.returnValue = this.method.invoke(obj, this.inputValues);
        }
        catch (Throwable t) {
            Throwable targetException = null;
            if (t instanceof InvocationTargetException) {
                targetException = ((InvocationTargetException)t).getTargetException();
                this.log.LogStackTrace(1, true, "invoke(): ", targetException);
            }
            this.log.LogStackTrace(1, true, "invoke(): ", t);
            if (targetException != null && targetException instanceof OutOfMemoryError) {
                final String messageText = Util.getMessageText(7017734119350084755L);
                final Log log = this.log;
                final Log log2 = this.log;
                log.LogMsgln(1, true, "", messageText);
                System.err.println(messageText);
                System.exit(-1);
            }
            String message;
            if (targetException != null) {
                message = targetException.toString();
            }
            else {
                message = t.toString();
            }
            throw new Exception(message);
        }
        this.outputValues = new Object[this.inputValues.length];
        for (int i = 0; i < this.inputValues.length; ++i) {
            if (this.inputValues[i] != null && MethodHolder.holderClass.isAssignableFrom(this.inputValues[i].getClass())) {
                this.outputValues[i] = ((Holder)this.inputValues[i]).getValue();
            }
        }
    }
    
    Class getReturnType() {
        if (this.method != null) {
            return this.method.getReturnType();
        }
        return null;
    }
    
    String getName() {
        return this.methodName;
    }
    
    Object getReturnedObject() {
        return this.returnValue;
    }
    
    Class[] getTypeArray() {
        return this.typesArray;
    }
    
    Object[] getOutputValues() {
        return this.outputValues;
    }
    
    static {
        try {
            MethodHolder.holderClass = Class.forName("com.progress.open4gl.Holder");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Error(ex.getMessage());
        }
    }
}

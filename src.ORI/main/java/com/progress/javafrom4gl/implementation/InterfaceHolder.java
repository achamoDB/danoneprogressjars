// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.lang.reflect.Method;

class InterfaceHolder
{
    private InterfaceTable interfaceTable;
    private Class interfaceClass;
    
    InterfaceHolder() {
        this.interfaceTable = InterfaceTable.getTable();
    }
    
    static void deleteClass() {
    }
    
    InterfaceHolder(final String s, final Object o) {
        this.interfaceClass = o.getClass();
    }
    
    void populateMethod(final MethodHolder methodHolder, final int n) throws Exception {
        Method method;
        try {
            method = this.interfaceClass.getMethod(methodHolder.getName(), (Class[])methodHolder.getTypeArray());
        }
        catch (NoSuchMethodException ex) {
            throw new Exception(ex.toString());
        }
        catch (SecurityException ex2) {
            throw new Exception(ex2.toString());
        }
        DataTypeMap.checkReturnType(n, method.getReturnType());
        methodHolder.setMethod(method);
    }
    
    String getName() {
        return null;
    }
}

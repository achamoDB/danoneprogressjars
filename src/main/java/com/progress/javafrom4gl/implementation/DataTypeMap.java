// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.sql.ResultSet;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.Holder;

class DataTypeMap
{
    static Holder objectForProOutType(final int n) {
        try {
            return classForProOutType(n).newInstance();
        }
        catch (Exception ex) {
            throw new Error(ex.getMessage());
        }
    }
    
    static Class classForProType(final int n, final boolean b) {
        if (b) {
            return classForProType(n);
        }
        return classForProOutType(n);
    }
    
    static Class classForProOutType(final int n) {
        try {
            switch (n) {
                case 4: {
                    return Class.forName("com.progress.open4gl.IntHolder");
                }
                case 2: {
                    return Class.forName("com.progress.open4gl.DateHolder");
                }
                case 3: {
                    return Class.forName("com.progress.open4gl.BooleanHolder");
                }
                case 1: {
                    return Class.forName("com.progress.open4gl.StringHolder");
                }
                case 5: {
                    return Class.forName("com.progress.open4gl.BigDecimalHolder");
                }
                case 7: {
                    return Class.forName("com.progress.open4gl.LongHolder");
                }
                case 8: {
                    return Class.forName("com.progress.open4gl.ByteArrayHolder");
                }
                case 10: {
                    return Class.forName("com.progress.open4gl.HandleHolder");
                }
                case 11: {
                    return Class.forName("com.progress.open4gl.MemptrHolder");
                }
                case 13: {
                    return Class.forName("com.progress.open4gl.RowidHolder");
                }
                case 14: {
                    return Class.forName("com.progress.open4gl.COMHandleHolder");
                }
                case 15: {
                    return Class.forName("com.progress.open4gl.ResultSetHolder");
                }
            }
        }
        catch (ClassNotFoundException ex) {
            throw new Error("Could not find a data type class.");
        }
        throw new Error("classForProOutType: Unknown type.");
    }
    
    static Class classForProType(final int n) {
        try {
            switch (n) {
                case 4: {
                    return Integer.TYPE;
                }
                case 2: {
                    return Class.forName("java.util.GregorianCalendar");
                }
                case 3: {
                    return Boolean.TYPE;
                }
                case 1: {
                    return Class.forName("java.lang.String");
                }
                case 5: {
                    return Class.forName("java.math.BigDecimal");
                }
                case 7: {
                    return Long.TYPE;
                }
                case 8: {
                    return byte[].class;
                }
                case 10: {
                    return Integer.TYPE;
                }
                case 11: {
                    return Integer.TYPE;
                }
                case 13: {
                    return null;
                }
                case 14: {
                    return Integer.TYPE;
                }
                case 15: {
                    return Class.forName("java.sql.ResultSet");
                }
                case 16: {
                    return null;
                }
            }
        }
        catch (ClassNotFoundException ex) {
            throw new Error("Could not find a data type class.");
        }
        throw new Error("classForProType: Unknown type.");
    }
    
    static void checkReturnType(final int n, final Class clazz) throws Exception {
        switch (n) {
            case 4: {
                if (clazz.getName().equals("int")) {
                    return;
                }
                if (clazz.getName().equals("java.lang.Integer")) {
                    return;
                }
                break;
            }
            case 1: {
                if (clazz.getName().equals("java.lang.String")) {
                    return;
                }
                break;
            }
            case 15: {
                if (clazz.getName().equals("java.sql.ResultSet")) {
                    return;
                }
                break;
            }
            case 16: {
                if (clazz.getName().equals("void")) {
                    return;
                }
                if (clazz.getName().equals("java.lang.Void")) {
                    return;
                }
                break;
            }
        }
        throw new Exception("return data type mismatch. 4GL return data type: " + Parameter.proToName(n) + ". " + "Method return type: " + clazz.getName());
    }
    
    static Object convertRetVal(final int n, final Object o, final Class clazz) throws Exception {
        if (o == null) {
            return null;
        }
        switch (n) {
            case 4: {
                if (o instanceof Integer) {
                    return o;
                }
                break;
            }
            case 1: {
                if (o instanceof String) {
                    return o;
                }
                break;
            }
            case 15: {
                if (o instanceof ResultSet) {
                    return o;
                }
                break;
            }
            case 16: {
                return null;
            }
        }
        throw new Exception("Conversion failure");
    }
}

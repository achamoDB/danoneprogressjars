// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

class JMSDataTypes
{
    static final int UNKNOWN_DATA_TYPE = 0;
    static final int BOOLEAN_DATA_TYPE = 1;
    static final int BYTE_DATA_TYPE = 2;
    static final int SHORT_DATA_TYPE = 3;
    static final int CHAR_DATA_TYPE = 4;
    static final int INT_DATA_TYPE = 5;
    static final int LONG_DATA_TYPE = 6;
    static final int FLOAT_DATA_TYPE = 7;
    static final int DOUBLE_DATA_TYPE = 8;
    static final int STRING_DATA_TYPE = 9;
    static final int BYTES_DATA_TYPE = 10;
    static final int LONGCHAR_DATA_TYPE = 11;
    static final int DATETIME_DATA_TYPE = 12;
    static final int INT64_DATA_TYPE = 14;
    
    static int getItemType(final Object o) {
        if (o == null) {
            return 0;
        }
        if (o instanceof Boolean) {
            return 1;
        }
        if (o instanceof Byte) {
            return 2;
        }
        if (o instanceof Short) {
            return 3;
        }
        if (o instanceof Character) {
            return 4;
        }
        if (o instanceof Integer) {
            return 5;
        }
        if (o instanceof Long) {
            return 6;
        }
        if (o instanceof Float) {
            return 7;
        }
        if (o instanceof Double) {
            return 8;
        }
        if (o instanceof String) {
            return 9;
        }
        if (o instanceof byte[]) {
            return 10;
        }
        throw new Error("Unknown data type.");
    }
    
    static Object convertFrom4gl(final Object o, final int n) {
        if (o == null) {
            return null;
        }
        String s = null;
        if (n != 10) {
            s = (String)o;
        }
        switch (n) {
            case 1: {
                return new Boolean(s);
            }
            case 2: {
                return new Byte(s);
            }
            case 3: {
                return new Short(s);
            }
            case 4: {
                return new Character(s.charAt(0));
            }
            case 5: {
                return new Integer(s);
            }
            case 6: {
                return new Long(s);
            }
            case 7: {
                return new Float(s);
            }
            case 8: {
                return new Double(s);
            }
            case 9: {
                return s;
            }
            case 10: {
                return o;
            }
            case 11: {
                return s;
            }
            case 12: {
                return s;
            }
            case 14: {
                return new Long(s);
            }
            default: {
                throw new Error("Bad data type");
            }
        }
    }
}

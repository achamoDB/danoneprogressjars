// 
// Decompiled by Procyon v0.5.36
// 

package com.mim.wfc.data;

import com.ms.wfc.data.AdoException;

public class DataException extends RuntimeException
{
    public static final int GENERAL_ERROR = 0;
    public static final int NO_DATASOURCE = 1;
    public static final int NO_COMMAND = 2;
    public static final int NO_RECORDSET = 3;
    public static final int ADO_DATA_TYPE_NOT_SUPPORTED = 4;
    public static final int ADO_DATA_CONVERSION = 5;
    public static final int VARIANT_DATA_CONVERSION = 6;
    public static final int FIELDLIST_NOT_INITIALIZED = 7;
    public static final int UNKNOWN_PARAMETER = 8;
    public static final int INVALID_PARAMETER_TYPE = 9;
    public static final int TABLE_NAME_DOES_NOT_MATCH = 11;
    public static final int FIELD_TYPE_DOES_NOT_MATCH = 12;
    public static final int NO_CHILD_DATASOURCE = 12;
    public static final int NO_CONNECTION = 13;
    public static final int ADO_ERROR = 14;
    private int \u00c0;
    
    public DataException() {
        super("General error");
        this.\u00c0 = 0;
    }
    
    public DataException(final int \u00e0, final String message) {
        super(message);
        this.\u00c0 = 0;
        this.\u00c0 = \u00e0;
    }
    
    protected static void failVariantType(final int n, final int n2, final String s) {
        throw new DataException(6, "Data conversion failed on field " + ((s == null) ? "???" : s) + ": Target type is " + vtToString(n2) + ", current type is " + vtToString(n) + ". Check also if the field is updateable.");
    }
    
    public int getCause() {
        return this.\u00c0;
    }
    
    protected static void failAdoType(final int n, final int n2, final String str, final AdoException obj) {
        boolean b = false;
        String s = null;
        switch (n2) {
            case 20: {
                s = "BIGINT";
                break;
            }
            case 128: {
                s = "BINARY";
                break;
            }
            case 11: {
                s = "BOOLEAN";
                break;
            }
            case 8: {
                s = "BSTR";
                break;
            }
            case 136: {
                b = true;
                s = "CHAPTER";
                break;
            }
            case 129: {
                s = "CHAR";
                break;
            }
            case 6: {
                s = "CURRENCY";
                break;
            }
            case 7: {
                s = "DATE";
                break;
            }
            case 133: {
                s = "DBDATE";
                break;
            }
            case 137: {
                b = true;
                s = "DBFILETIME";
                break;
            }
            case 134: {
                s = "DBTIME";
                break;
            }
            case 135: {
                b = true;
                s = "DBTIMESTAMP";
                break;
            }
            case 14: {
                s = "DECIMAL";
                break;
            }
            case 5: {
                s = "DOUBLE";
                break;
            }
            case 0: {
                b = true;
                s = "EMPTY";
                break;
            }
            case 10: {
                b = true;
                s = "ERROR";
                break;
            }
            case 64: {
                b = true;
                s = "FILETIME";
                break;
            }
            case 72: {
                b = true;
                s = "GUID";
                break;
            }
            case 9: {
                s = "IDISPATCH";
                break;
            }
            case 3: {
                s = "INTEGER";
                break;
            }
            case 13: {
                b = true;
                s = "IUNKNOWN";
                break;
            }
            case 205: {
                s = "LONGVARBINARY";
                break;
            }
            case 201: {
                s = "LONGVARCHAR";
                break;
            }
            case 203: {
                s = "LONGVARWCHAR";
                break;
            }
            case 131: {
                s = "NUMERIC";
                break;
            }
            case 138: {
                b = true;
                s = "PROPVARIANT";
                break;
            }
            case 4: {
                s = "SINGLE";
                break;
            }
            case 2: {
                s = "SMALLINT";
                break;
            }
            case 16: {
                s = "TINYINT";
                break;
            }
            case 21: {
                s = "UNSIGNEDBIGINT";
                break;
            }
            case 19: {
                s = "UNSIGNEDINT";
                break;
            }
            case 18: {
                s = "UNSIGNEDSMALLINT";
                break;
            }
            case 17: {
                s = "UNSIGNEDTINYINT";
                break;
            }
            case 132: {
                b = true;
                s = "USERDEFINED";
                break;
            }
            case 204: {
                s = "VARBINARY";
                break;
            }
            case 200: {
                s = "VARCHAR";
                break;
            }
            case 12: {
                b = true;
                s = "VARIANT";
                break;
            }
            case 139: {
                s = "VARNUMERIC";
                break;
            }
            case 202: {
                s = "VARWCHAR";
                break;
            }
            case 130: {
                s = "WCHAR";
                break;
            }
            default: {
                b = true;
                s = "???";
                break;
            }
        }
        if (b) {
            throw new DataException(4, "Data conversion failed in field " + str + ": Data type " + s + " not supported");
        }
        throw new DataException(5, "Ado error while updating value of field " + ((str == null) ? "???" : str) + ": ADO target type is " + s + ", current type is " + vtToString(n) + ". ADO error:\n" + obj);
    }
    
    public static String vtToString(final int n) {
        String string = null;
        switch (n & 0xFFF) {
            case 11: {
                string = "boolean";
                break;
            }
            case 16384: {
                string = "byref";
                break;
            }
            case 17: {
                string = "byte";
                break;
            }
            case 6: {
                string = "currency";
                break;
            }
            case 7: {
                string = "date";
                break;
            }
            case 9: {
                string = "dispatch";
                break;
            }
            case 5: {
                string = "double";
                break;
            }
            case 0: {
                string = "empty";
                break;
            }
            case 10: {
                string = "error";
                break;
            }
            case 4: {
                string = "float";
                break;
            }
            case 3: {
                string = "int";
                break;
            }
            case 1: {
                string = "null";
                break;
            }
            case 13: {
                string = "object";
                break;
            }
            case 2: {
                string = "short";
                break;
            }
            case 8: {
                string = "string";
                break;
            }
            case 12: {
                string = "variant";
                break;
            }
            default: {
                string = "???";
                break;
            }
        }
        if ((n & 0x2000) != 0x0) {
            string += " array";
        }
        return string;
    }
}

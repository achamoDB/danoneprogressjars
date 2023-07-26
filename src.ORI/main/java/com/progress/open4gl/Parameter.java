// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl;

import java.sql.ResultSet;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.lang.reflect.Array;

public final class Parameter
{
    public static final int MAX_4GL_DECIMALS = 15;
    public static final int MAX_4GL_DEC_DIGITS = 50;
    public static final int PERSISTENT = 1;
    public static final int NON_PERSISTENT = 3;
    public static final int INTERNAL = 2;
    public static final int FUNCTION = 7;
    public static final int DELETE_PERSISTENT = 4;
    public static final int SINGLE_RUN = 9;
    public static final int SINGLE_RUN_FUNCTION = 10;
    public static final int SINGLETON = 11;
    public static final int SINGLETON_FUNCTION = 12;
    public static final int PRO_UNSPECIFIED = 0;
    public static final int PRO_CHARACTER = 1;
    public static final int PRO_DATE = 2;
    public static final int PRO_LOGICAL = 3;
    public static final int PRO_INTEGER = 4;
    public static final int PRO_DECIMAL = 5;
    public static final int PRO_RECID = 7;
    public static final int PRO_RAW = 8;
    public static final int PRO_WIDGETHANDLE = 10;
    public static final int PRO_MEMPTR = 11;
    public static final int PRO_ROWID = 13;
    public static final int PRO_COMHANDLE = 14;
    public static final int PRO_TEMPTABLE = 15;
    public static final int PRO_UNKNOWN = 16;
    public static final int PRO_TABLEHANDLE = 17;
    public static final int PRO_BLOB = 18;
    public static final int PRO_CLOB = 19;
    public static final int PRO_DATETIME = 34;
    public static final int PRO_DATASET = 36;
    public static final int PRO_DATASETHANDLE = 37;
    public static final int PRO_LONGCHAR = 39;
    public static final int PRO_DATETIMETZ = 40;
    public static final int PRO_INT64 = 41;
    public static final int XPXG_DATASET = 18;
    public static final int XPXG_DATASETHANDLE = 19;
    public static final int XPXG_BLOB = 23;
    public static final int XPXG_CLOB = 24;
    public static final int PRO_LASTTYPE = 41;
    public static final String PRO_UNSPECIFIED_NAME = "UNSPECIFIED";
    public static final String PRO_CHARACTER_NAME = "CHARACTER";
    public static final String PRO_DATE_NAME = "DATE";
    public static final String PRO_LOGICAL_NAME = "LOGICAL";
    public static final String PRO_INTEGER_NAME = "INTEGER";
    public static final String PRO_DECIMAL_NAME = "DECIMAL";
    public static final String PRO_RECID_NAME = "RECID";
    public static final String PRO_RAW_NAME = "RAW";
    public static final String PRO_WIDGETHANDLE_NAME = "WIDGET-HANDLE";
    public static final String PRO_HANDLE_NAME = "HANDLE";
    public static final String PRO_MEMPTR_NAME = "MEMPTR";
    public static final String PRO_ROWID_NAME = "ROWID";
    public static final String PRO_COMHANDLE_NAME = "COM-HANDLE";
    public static final String PRO_TEMPTABLE_NAME = "TEMPTABLE";
    public static final String PRO_UNKNOWN_NAME = "UNKNOWN";
    public static final String PRO_TABLEHANDLE_NAME = "TABLEHANDLE";
    public static final String PRO_DATASET_NAME = "DATASET";
    public static final String PRO_DATASETHANDLE_NAME = "DATASET-HANDLE";
    public static final String PRO_BLOB_NAME = "BLOB";
    public static final String PRO_CLOB_NAME = "CLOB";
    public static final String PRO_DATETIME_NAME = "DATETIME";
    public static final String PRO_DATETIMETZ_NAME = "DATETIME-TZ";
    public static final String PRO_LONGCHAR_NAME = "LONGCHAR";
    public static final String PRO_INT64_NAME = "INT64";
    public static final String JAVA_UNSPECIFIED_NAME = "";
    public static final String JAVA_CHARACTER_NAME = "java.lang.String";
    public static final String JAVA_DATE_NAME = "java.sql.Date";
    public static final String JAVA_LOGICAL_NAME = "java.lang.Boolean";
    public static final String JAVA_INTEGER_NAME = "java.lang.Integer";
    public static final String JAVA_DECIMAL_NAME = "java.math.BigDecimal";
    public static final String JAVA_RECID_NAME = "java.lang.Long";
    public static final String JAVA_RAW_NAME = "byte[]";
    public static final String JAVA_WIDGETHANDLE_NAME = "java.lang.Long";
    public static final String JAVA_MEMPTR_NAME = "byte[]";
    public static final String JAVA_ROWID_NAME = "byte[]";
    public static final String JAVA_COMHANDLE_NAME = "java.lang.Long";
    public static final String JAVA_TEMPTABLE_NAME = "java.sql.ResultSet";
    public static final String JAVA_UNKNOWN_NAME = "UNKNOWN";
    public static final String JAVA_TABLEHANDLE_NAME = "java.sql.ResultSet";
    public static final String JAVA_BLOB_NAME = "java.sql.Blob";
    public static final String JAVA_CLOB_NAME = "java.sql.Clob";
    public static final String JAVA_DATETIME_NAME = "java.sql.Timestamp";
    public static final String JAVA_DATETIMETZ_NAME = "java.sql.Timestamp";
    public static final String JAVA_LONGCHAR_NAME = "java.lang.String";
    public static final String JAVA_INT64_NAME = "java.lang.Long";
    public static final String DOTNET_UNSPECIFIED_NAME = "";
    public static final String DOTNET_CHARACTER_NAME = "System.String";
    public static final String DOTNET_DATE_NAME = "System.DateTime";
    public static final String DOTNET_LOGICAL_NAME = "bool";
    public static final String DOTNET_INTEGER_NAME = "int";
    public static final String DOTNET_DECIMAL_NAME = "decimal";
    public static final String DOTNET_RECID_NAME = "long";
    public static final String DOTNET_RAW_NAME = "byte[]";
    public static final String DOTNET_WIDGETHANDLE_NAME = "long";
    public static final String DOTNET_MEMPTR_NAME = "byte[]";
    public static final String DOTNET_ROWID_NAME = "byte[]";
    public static final String DOTNET_COMHANDLE_NAME = "long";
    public static final String DOTNET_TEMPTABLE_NAME = "System.Data.DataTable";
    public static final String DOTNET_UNKNOWN_NAME = "UNKNOWN";
    public static final String DOTNET_TABLEHANDLE_NAME = "System.Data.DataTable";
    public static final String DOTNET_DATASET_NAME = "System.Data.DataSet";
    public static final String DOTNET_DATASETHANDLE_NAME = "System.Data.DataSet";
    public static final String DOTNET_BLOB_NAME = "System.Byte[]";
    public static final String DOTNET_CLOB_NAME = "System.String";
    public static final String DOTNET_DATETIME_NAME = "System.DateTime";
    public static final String DOTNET_DATETIMETZ_NAME = "System.DateTime";
    public static final String DOTNET_LONGCHAR_NAME = "System.String";
    public static final String DOTNET_INT64_NAME = "long";
    public static final int DATEBASE_DELTA = -7183;
    
    public static String proToJavaClass(final int n) {
        switch (n) {
            case 0: {
                return "";
            }
            case 1: {
                return "java.lang.String";
            }
            case 2: {
                return "java.sql.Date";
            }
            case 3: {
                return "java.lang.Boolean";
            }
            case 4: {
                return "java.lang.Integer";
            }
            case 5: {
                return "java.math.BigDecimal";
            }
            case 7: {
                return "java.lang.Long";
            }
            case 8: {
                return "byte[]";
            }
            case 10: {
                return "java.lang.Long";
            }
            case 11: {
                return "byte[]";
            }
            case 13: {
                return "byte[]";
            }
            case 14: {
                return "java.lang.Long";
            }
            case 15: {
                return "java.sql.ResultSet";
            }
            case 16: {
                return "UNKNOWN";
            }
            case 17: {
                return "java.sql.ResultSet";
            }
            case 18: {
                return "java.sql.Blob";
            }
            case 19: {
                return "java.sql.Clob";
            }
            case 34: {
                return "java.sql.Timestamp";
            }
            case 40: {
                return "java.sql.Timestamp";
            }
            case 39: {
                return "java.lang.String";
            }
            case 41: {
                return "java.lang.Long";
            }
            default: {
                return null;
            }
        }
    }
    
    public static String proToDotNetClass(final int n) {
        switch (n) {
            case 0: {
                return "";
            }
            case 1: {
                return "System.String";
            }
            case 2: {
                return "System.DateTime";
            }
            case 3: {
                return "bool";
            }
            case 4: {
                return "int";
            }
            case 5: {
                return "decimal";
            }
            case 7: {
                return "long";
            }
            case 8: {
                return "byte[]";
            }
            case 10: {
                return "long";
            }
            case 11: {
                return "byte[]";
            }
            case 13: {
                return "byte[]";
            }
            case 14: {
                return "long";
            }
            case 15: {
                return "System.Data.DataTable";
            }
            case 16: {
                return "UNKNOWN";
            }
            case 17: {
                return "System.Data.DataTable";
            }
            case 36: {
                return "System.Data.DataSet";
            }
            case 37: {
                return "System.Data.DataSet";
            }
            case 18: {
                return "System.Byte[]";
            }
            case 19: {
                return "System.String";
            }
            case 34: {
                return "System.DateTime";
            }
            case 40: {
                return "System.DateTime";
            }
            case 39: {
                return "System.String";
            }
            case 41: {
                return "long";
            }
            default: {
                return null;
            }
        }
    }
    
    public static Class proToJavaClassObject(final int n) {
        switch (n) {
            case 0: {
                return null;
            }
            case 1: {
                return String.class;
            }
            case 2: {
                return GregorianCalendar.class;
            }
            case 3: {
                return Boolean.class;
            }
            case 4: {
                return Integer.class;
            }
            case 5: {
                return BigDecimal.class;
            }
            case 7: {
                return Long.class;
            }
            case 8: {
                return Array.newInstance(Byte.TYPE, 0).getClass();
            }
            case 10: {
                return Handle.class;
            }
            case 11: {
                return Memptr.class;
            }
            case 13: {
                return Rowid.class;
            }
            case 14: {
                return COMHandle.class;
            }
            case 15: {
                return ResultSet.class;
            }
            case 16: {
                return null;
            }
            case 17: {
                return ResultSet.class;
            }
            case 34: {
                return GregorianCalendar.class;
            }
            case 40: {
                return GregorianCalendar.class;
            }
            case 39: {
                return String.class;
            }
            case 41: {
                return Long.class;
            }
            case 36: {
                return DatasetAsXml.class;
            }
            case 37: {
                return DatasetHandleAsXml.class;
            }
            default: {
                return null;
            }
        }
    }
    
    public static String proToName(final int n) {
        switch (n) {
            case 0: {
                return "UNSPECIFIED";
            }
            case 1: {
                return "CHARACTER";
            }
            case 2: {
                return "DATE";
            }
            case 3: {
                return "LOGICAL";
            }
            case 4: {
                return "INTEGER";
            }
            case 5: {
                return "DECIMAL";
            }
            case 7: {
                return "RECID";
            }
            case 8: {
                return "RAW";
            }
            case 10: {
                return "WIDGET-HANDLE";
            }
            case 11: {
                return "MEMPTR";
            }
            case 13: {
                return "ROWID";
            }
            case 14: {
                return "COM-HANDLE";
            }
            case 15: {
                return "TEMPTABLE";
            }
            case 16: {
                return "UNKNOWN";
            }
            case 17: {
                return "TABLEHANDLE";
            }
            case 18: {
                return "BLOB";
            }
            case 19: {
                return "CLOB";
            }
            case 34: {
                return "DATETIME";
            }
            case 40: {
                return "DATETIME-TZ";
            }
            case 39: {
                return "LONGCHAR";
            }
            case 41: {
                return "INT64";
            }
            case 36: {
                return "DATASET";
            }
            case 37: {
                return "DATASET-HANDLE";
            }
            default: {
                return null;
            }
        }
    }
    
    public static int NameToPro(final String s) {
        if (s.equalsIgnoreCase("UNSPECIFIED")) {
            return 0;
        }
        if (s.equalsIgnoreCase("CHARACTER")) {
            return 1;
        }
        if (s.equalsIgnoreCase("DATE")) {
            return 2;
        }
        if (s.equalsIgnoreCase("LOGICAL")) {
            return 3;
        }
        if (s.equalsIgnoreCase("INTEGER")) {
            return 4;
        }
        if (s.equalsIgnoreCase("DECIMAL")) {
            return 5;
        }
        if (s.equalsIgnoreCase("RECID")) {
            return 7;
        }
        if (s.equalsIgnoreCase("RAW")) {
            return 8;
        }
        if (s.equalsIgnoreCase("WIDGET-HANDLE")) {
            return 10;
        }
        if (s.equalsIgnoreCase("HANDLE")) {
            return 10;
        }
        if (s.equalsIgnoreCase("MEMPTR")) {
            return 11;
        }
        if (s.equalsIgnoreCase("ROWID")) {
            return 13;
        }
        if (s.equalsIgnoreCase("COM-HANDLE")) {
            return 14;
        }
        if (s.equalsIgnoreCase("TEMPTABLE")) {
            return 15;
        }
        if (s.equalsIgnoreCase("UNKNOWN")) {
            return 16;
        }
        if (s.equalsIgnoreCase("TABLEHANDLE")) {
            return 17;
        }
        if (s.equalsIgnoreCase("BLOB")) {
            return 18;
        }
        if (s.equalsIgnoreCase("CLOB")) {
            return 19;
        }
        if (s.equalsIgnoreCase("DATETIME")) {
            return 34;
        }
        if (s.equalsIgnoreCase("DATASET")) {
            return 36;
        }
        if (s.equalsIgnoreCase("DATASET-HANDLE")) {
            return 37;
        }
        if (s.equalsIgnoreCase("LONGCHAR")) {
            return 39;
        }
        if (s.equalsIgnoreCase("DATETIME-TZ")) {
            return 40;
        }
        if (s.equalsIgnoreCase("INT64")) {
            return 41;
        }
        return 42;
    }
    
    public static String proToProTypeName(final int n) {
        String s = null;
        switch (n) {
            case 1: {
                s = "Parameter.PRO_CHARACTER";
                break;
            }
            case 2: {
                s = "Parameter.PRO_DATE";
                break;
            }
            case 34: {
                s = "Parameter.PRO_DATETIME";
                break;
            }
            case 40: {
                s = "Parameter.PRO_DATETIMETZ";
                break;
            }
            case 3: {
                s = "Parameter.PRO_LOGICAL";
                break;
            }
            case 4: {
                s = "Parameter.PRO_INTEGER";
                break;
            }
            case 5: {
                s = "Parameter.PRO_DECIMAL";
                break;
            }
            case 7: {
                s = "Parameter.PRO_RECID";
                break;
            }
            case 8: {
                s = "Parameter.PRO_RAW";
                break;
            }
            case 10: {
                s = "Parameter.PRO_WIDGETHANDLE";
                break;
            }
            case 11: {
                s = "Parameter.PRO_MEMPTR";
                break;
            }
            case 13: {
                s = "Parameter.PRO_ROWID";
                break;
            }
            case 14: {
                s = "Parameter.PRO_COMHANDLE";
                break;
            }
            case 36: {
                s = "Parameter.PRO_DATASET";
                break;
            }
            case 15: {
                s = "Parameter.PRO_TEMPTABLE";
                break;
            }
            case 17: {
                s = "Parameter.PRO_TABLEHANDLE";
                break;
            }
            case 37: {
                s = "Parameter.PRO_DATASETHANDLE";
                break;
            }
            case 18: {
                s = "Parameter.PRO_BLOB";
                break;
            }
            case 19: {
                s = "Parameter.PRO_CLOB";
                break;
            }
            case 39: {
                s = "Parameter.PRO_LONGCHAR";
                break;
            }
            case 41: {
                s = "Parameter.PRO_INT64";
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return s;
    }
    
    public static int proToSql(final int n) {
        switch (n) {
            case 1: {
                return -1;
            }
            case 2: {
                return 91;
            }
            case 3: {
                return -7;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 3;
            }
            case 7: {
                return -5;
            }
            case 8: {
                return -4;
            }
            case 10: {
                return -5;
            }
            case 14: {
                return -5;
            }
            case 11: {
                return -4;
            }
            case 13: {
                return -4;
            }
            case 18: {
                return 2004;
            }
            case 19: {
                return 2005;
            }
            case 34: {
                return 93;
            }
            case 40: {
                return 93;
            }
            case 39: {
                return -1;
            }
            case 41: {
                return -5;
            }
            default: {
                return 0;
            }
        }
    }
    
    public static int fromProNameToProNum(final String s) {
        if (s.equalsIgnoreCase("CHARACTER")) {
            return 1;
        }
        if (s.equalsIgnoreCase("INTEGER")) {
            return 4;
        }
        if (s.equalsIgnoreCase("DATE")) {
            return 2;
        }
        if (s.equalsIgnoreCase("DECIMAL")) {
            return 5;
        }
        if (s.equalsIgnoreCase("LOGICAL")) {
            return 3;
        }
        if (s.equalsIgnoreCase("ROWID")) {
            return 13;
        }
        if (s.equalsIgnoreCase("RECID")) {
            return 7;
        }
        if (s.equalsIgnoreCase("RAW")) {
            return 8;
        }
        if (s.equalsIgnoreCase("MEMPTR")) {
            return 11;
        }
        if (s.equalsIgnoreCase("COM-HANDLE")) {
            return 14;
        }
        if (s.equalsIgnoreCase("WIDGET-HANDLE")) {
            return 10;
        }
        if (s.equalsIgnoreCase("HANDLE")) {
            return 10;
        }
        if (s.equalsIgnoreCase("BLOB")) {
            return 18;
        }
        if (s.equalsIgnoreCase("CLOB")) {
            return 19;
        }
        if (s.equalsIgnoreCase("DATETIME")) {
            return 34;
        }
        if (s.equalsIgnoreCase("DATETIME-TZ")) {
            return 40;
        }
        if (s.equalsIgnoreCase("LONGCHAR")) {
            return 39;
        }
        if (s.equalsIgnoreCase("INT64")) {
            return 41;
        }
        if (s.equalsIgnoreCase("DATASET")) {
            return 36;
        }
        if (s.equalsIgnoreCase("DATASET-HANDLE")) {
            return 37;
        }
        throw new Open4GLError();
    }
    
    public static int XPXGTypeToProType(final int n) {
        int n2 = 0;
        switch (n) {
            case 18: {
                n2 = 36;
                break;
            }
            case 19: {
                n2 = 37;
                break;
            }
            case 23: {
                n2 = 18;
                break;
            }
            case 24: {
                n2 = 19;
                break;
            }
            default: {
                n2 = n;
                break;
            }
        }
        return n2;
    }
}

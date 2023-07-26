// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

public class WSDLDataTypes
{
    public static final String XML_UNSPECIFIED_STR_TYPE = "";
    public static final String XML_CHARACTER_STR_TYPE = "string";
    public static final String XML_DATE_STR_TYPE = "date";
    public static final String XML_LOGICAL_STR_TYPE = "boolean";
    public static final String XML_INTEGER_STR_TYPE = "int";
    public static final String XML_DECIMAL_STR_TYPE = "decimal";
    public static final String XML_RECID_STR_TYPE = "long";
    public static final String XML_RAW_STR_TYPE = "base64Binary";
    public static final String XML_WIDGETHANDLE_STR_TYPE = "long";
    public static final String XML_MEMPTR_STR_TYPE = "base64Binary";
    public static final String XML_ROWID_STR_TYPE = "base64Binary";
    public static final String XML_COMHANDLE_STR_TYPE = "long";
    public static final String XML_TEMPTABLE_STR_TYPE = "";
    public static final String XML_UNKNOWN_STR_TYPE = "Unknown";
    public static final String XML_TABLEHANDLE_STR_TYPE = "";
    public static final String XML_LONGCHAR_STR_TYPE = "string";
    public static final String XML_DATETIME_STR_TYPE = "dateTime";
    public static final String XML_DATETIMETZ_STR_TYPE = "dateTime";
    public static final String XML_CLOB_STR_TYPE = "string";
    public static final String XML_BLOB_STR_TYPE = "base64Binary";
    public static final String XML_INT64_STR_TYPE = "long";
    public static final String XML_CHARACTER_STR_ARRAY_TYPE = "ArrayOfString";
    public static final String XML_DATE_STR_ARRAY_TYPE = "ArrayOfDate";
    public static final String XML_LOGICAL_STR_ARRAY_TYPE = "ArrayOfBoolean";
    public static final String XML_INTEGER_STR_ARRAY_TYPE = "ArrayOfInt";
    public static final String XML_DECIMAL_STR_ARRAY_TYPE = "ArrayOfDecimal";
    public static final String XML_RECID_STR_ARRAY_TYPE = "ArrayOfLong";
    public static final String XML_RAW_STR_ARRAY_TYPE = "ArrayOfBase64Binary";
    public static final String XML_WIDGETHANDLE_STR_ARRAY_TYPE = "ArrayOfLong";
    public static final String XML_MEMPTR_STR_ARRAY_TYPE = "ArrayOfBase64Binary";
    public static final String XML_ROWID_STR_ARRAY_TYPE = "ArrayOfBase64Binary";
    public static final String XML_COMHANDLE_STR_ARRAY_TYPE = "ArrayOfLong";
    public static final String XML_DATETIME_STR_ARRAY_TYPE = "ArrayOfDatetime";
    public static final String XML_DATETIMETZ_STR_ARRAY_TYPE = "ArrayOfDatetime";
    public static final String XML_LONGCHAR_STR_ARRAY_TYPE = "ArrayOfString";
    public static final String XML_INT64_STR_ARRAY_TYPE = "ArrayOfLong";
    
    public static String proToXMLType(final int n) {
        switch (n) {
            case 0: {
                return "";
            }
            case 1: {
                return "string";
            }
            case 2: {
                return "date";
            }
            case 3: {
                return "boolean";
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
                return "base64Binary";
            }
            case 10: {
                return "long";
            }
            case 11: {
                return "base64Binary";
            }
            case 13: {
                return "base64Binary";
            }
            case 14: {
                return "long";
            }
            case 15: {
                return "";
            }
            case 16: {
                return "Unknown";
            }
            case 17: {
                return "";
            }
            case 34: {
                return "dateTime";
            }
            case 40: {
                return "dateTime";
            }
            case 39: {
                return "string";
            }
            case 18: {
                return "base64Binary";
            }
            case 19: {
                return "string";
            }
            case 41: {
                return "long";
            }
            default: {
                return null;
            }
        }
    }
    
    public static String proToXMLArrayType(final int n) {
        switch (n) {
            case 0: {
                return "";
            }
            case 1: {
                return "ArrayOfString";
            }
            case 2: {
                return "ArrayOfDate";
            }
            case 3: {
                return "ArrayOfBoolean";
            }
            case 4: {
                return "ArrayOfInt";
            }
            case 5: {
                return "ArrayOfDecimal";
            }
            case 7: {
                return "ArrayOfLong";
            }
            case 8: {
                return "ArrayOfBase64Binary";
            }
            case 10: {
                return "ArrayOfLong";
            }
            case 11: {
                return "ArrayOfBase64Binary";
            }
            case 13: {
                return "ArrayOfBase64Binary";
            }
            case 14: {
                return "ArrayOfLong";
            }
            case 15: {
                return "Unknown";
            }
            case 16: {
                return "Unknown";
            }
            case 17: {
                return "Unknown";
            }
            case 34: {
                return "ArrayOfDatetime";
            }
            case 40: {
                return "ArrayOfDatetime";
            }
            case 39: {
                return "ArrayOfString";
            }
            case 41: {
                return "ArrayOfLong";
            }
            default: {
                return null;
            }
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

class JavaDataMapper implements IDataTypeMapper
{
    private static final String lineSep;
    
    public String proToNative(final PGParam pgParam) {
        return this.proToNative(pgParam, null);
    }
    
    public String proToNative(final PGParam pgParam, final boolean b) {
        return this.proToNative(pgParam, null);
    }
    
    public String proToNative(final PGParam pgParam, final String s) {
        return this.proToNative(pgParam.m_enumType, pgParam.m_bIsExtentField, pgParam.effectiveAllowUnknown(), pgParam.isEffectiveTTResultSet());
    }
    
    private String proToNative(final int n, final boolean b, final boolean b2, final boolean b3) {
        final int n2 = n & 0xFF;
        final int n3 = n >> 8;
        String str = null;
        switch (n2) {
            case 1:
            case 39: {
                if (n3 == 1 || n3 == 0) {
                    str = "String";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "StringArrayHolder";
                        break;
                    }
                    str = "StringHolder";
                    break;
                }
                break;
            }
            case 2:
            case 34:
            case 40: {
                if (n3 == 1 || n3 == 0) {
                    str = "java.util.GregorianCalendar";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "DateArrayHolder";
                        break;
                    }
                    str = "DateHolder";
                    break;
                }
                break;
            }
            case 3: {
                if (n3 == 1 || n3 == 0) {
                    if (b2) {
                        str = "Boolean";
                    }
                    else {
                        str = "boolean";
                    }
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "BooleanArrayHolder";
                        break;
                    }
                    str = "BooleanHolder";
                    break;
                }
                break;
            }
            case 4: {
                if (n3 == 1 || n3 == 0) {
                    if (b2) {
                        str = "Integer";
                    }
                    else {
                        str = "int";
                    }
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "IntArrayHolder";
                        break;
                    }
                    str = "IntHolder";
                    break;
                }
                break;
            }
            case 5: {
                if (n3 == 1 || n3 == 0) {
                    str = "java.math.BigDecimal";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "BigDecimalArrayHolder";
                        break;
                    }
                    str = "BigDecimalHolder";
                    break;
                }
                break;
            }
            case 7:
            case 41: {
                if (n3 == 1 || n3 == 0) {
                    if (b2) {
                        str = "Long";
                    }
                    else {
                        str = "long";
                    }
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "LongArrayHolder";
                        break;
                    }
                    str = "LongHolder";
                    break;
                }
                break;
            }
            case 8: {
                if (n3 == 1 || n3 == 0) {
                    str = "byte[]";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "ByteArrayArrayHolder";
                        break;
                    }
                    str = "ByteArrayHolder";
                    break;
                }
                break;
            }
            case 10: {
                if (n3 == 1 || n3 == 0) {
                    str = "Handle";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "HandleArrayHolder";
                        break;
                    }
                    str = "HandleHolder";
                    break;
                }
                break;
            }
            case 11: {
                if (n3 == 1 || n3 == 0) {
                    str = "Memptr";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "MemptrArrayHolder";
                        break;
                    }
                    str = "MemptrHolder";
                    break;
                }
                break;
            }
            case 13: {
                if (n3 == 1 || n3 == 0) {
                    str = "Rowid";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "RowidArrayHolder";
                        break;
                    }
                    str = "RowidHolder";
                    break;
                }
                break;
            }
            case 14: {
                if (n3 == 1 || n3 == 0) {
                    str = "COMHandle";
                    if (b) {
                        str += "[]";
                        break;
                    }
                    break;
                }
                else {
                    if (b) {
                        str = "COMHandleArrayHolder";
                        break;
                    }
                    str = "COMHandleHolder";
                    break;
                }
                break;
            }
            case 15:
            case 17: {
                if (b3) {
                    if (n3 == 1 || n3 == 0) {
                        str = "java.sql.ResultSet";
                        break;
                    }
                    str = "ResultSetHolder";
                    break;
                }
                else {
                    if (n3 == 1 || n3 == 0) {
                        str = "ProDataGraph";
                        break;
                    }
                    str = "ProDataGraphHolder";
                    break;
                }
                break;
            }
            case 36:
            case 37: {
                if (n3 == 1 || n3 == 0) {
                    str = "ProDataGraph";
                    break;
                }
                str = "ProDataGraphHolder";
                break;
            }
            default: {
                str = "";
                break;
            }
        }
        return str;
    }
    
    public String setParameter(final PGParam pgParam) {
        String str = "params.";
        final boolean bIsExtentField = pgParam.m_bIsExtentField;
        final int effectiveParamType = pgParam.getEffectiveParamType(true);
        final int n = pgParam.m_enumType >> 8;
        switch (effectiveParamType) {
            case 1: {
                if (bIsExtentField) {
                    str += "setStringArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setStringParameter(%Position%, %Name%,";
                break;
            }
            case 39: {
                if (bIsExtentField) {
                    str += "setLongcharArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setLongcharParameter(%Position%, %Name%,";
                break;
            }
            case 2: {
                if (bIsExtentField) {
                    str += "setDateArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setDateParameter(%Position%, %Name%,";
                break;
            }
            case 34: {
                if (bIsExtentField) {
                    str += "setDateTimeArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setDateTimeParameter(%Position%, %Name%,";
                break;
            }
            case 40: {
                if (bIsExtentField) {
                    str += "setDateTimeTzArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setDateTimeTzParameter(%Position%, %Name%,";
                break;
            }
            case 3: {
                if (bIsExtentField) {
                    str += "setBooleanArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setBooleanParameter(%Position%, %Name%,";
                break;
            }
            case 4: {
                if (bIsExtentField) {
                    str += "setIntegerArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setIntegerParameter(%Position%, %Name%,";
                break;
            }
            case 41: {
                if (bIsExtentField) {
                    str += "setInt64ArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setInt64Parameter(%Position%, %Name%,";
                break;
            }
            case 5: {
                if (bIsExtentField) {
                    str += "setDecimalArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setDecimalParameter(%Position%, %Name%,";
                break;
            }
            case 7: {
                if (bIsExtentField) {
                    str += "setLongArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setLongParameter(%Position%, %Name%,";
                break;
            }
            case 8: {
                if (bIsExtentField) {
                    str += "setByteArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setByteParameter(%Position%, %Name%,";
                break;
            }
            case 10: {
                if (bIsExtentField) {
                    str += "setHandleArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setHandleParameter(%Position%, %Name%,";
                break;
            }
            case 11: {
                if (bIsExtentField) {
                    str += "setMemptrArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setMemptrParameter(%Position%, %Name%,";
                break;
            }
            case 13: {
                if (bIsExtentField) {
                    str += "setRowidArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setRowidParameter(%Position%, %Name%,";
                break;
            }
            case 14: {
                if (bIsExtentField) {
                    str += "setCOMHandleArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setCOMHandleParameter(%Position%, %Name%,";
                break;
            }
            case 15: {
                str += "setResultSetParameter(%Position%, %Name%,";
                break;
            }
            case 17: {
                str += "setDynResultSetParameter(%Position%, %Name%,";
                break;
            }
            case 36: {
                str += "setDataGraphParameter(%Position%, %Name%,";
                break;
            }
            case 37: {
                str += "setDynDataGraphParameter(%Position%, %Name%,";
                break;
            }
        }
        String str2 = null;
        switch (n) {
            case 1: {
                str2 = str + " ParameterSet.INPUT";
                break;
            }
            case 2: {
                str2 = str + " ParameterSet.OUTPUT";
                break;
            }
            default: {
                str2 = str + " ParameterSet.INPUT_OUTPUT";
                break;
            }
        }
        String s;
        if (bIsExtentField) {
            s = str2 + ", %ExtentValue%);";
        }
        else if (effectiveParamType == 36 || effectiveParamType == 37) {
            s = str2 + ", %MappedTT%);";
        }
        else {
            s = str2 + ");";
        }
        return s;
    }
    
    public String setParameter(final PGParam pgParam, final String s, final boolean b, final boolean b2) {
        return this.setParameter(s, (pgParam.m_enumType & 0xFF00) + pgParam.getEffectiveParamType(true), pgParam.m_bIsExtentField, b);
    }
    
    private String setParameter(final String s, final int n, final boolean b, final boolean b2) {
        final int n2 = n & 0xFF;
        String str = null;
        if (n >> 8 == 1) {
            str = s;
        }
        else {
            switch (n2) {
                case 1:
                case 39: {
                    if (b) {
                        str = "(String[])" + s;
                        break;
                    }
                    str = "(String)" + s;
                    break;
                }
                case 2:
                case 34:
                case 40: {
                    if (b) {
                        str = "(java.util.GregorianCalendar[])" + s;
                        break;
                    }
                    str = "(java.util.GregorianCalendar)" + s;
                    break;
                }
                case 3: {
                    if (b) {
                        str = "(Boolean[])" + s;
                        break;
                    }
                    str = "(Boolean)" + s;
                    break;
                }
                case 4: {
                    if (b) {
                        str = "(Integer[])" + s;
                        break;
                    }
                    str = "(Integer)" + s;
                    break;
                }
                case 5: {
                    if (b) {
                        str = "(java.math.BigDecimal[])" + s;
                        break;
                    }
                    str = "(java.math.BigDecimal)" + s;
                    break;
                }
                case 7:
                case 41: {
                    if (b) {
                        str = "(Long[])" + s;
                        break;
                    }
                    str = "(Long)" + s;
                    break;
                }
                case 8: {
                    if (b) {
                        str = "(byte[][])" + s;
                        break;
                    }
                    str = "(byte[])" + s;
                    break;
                }
                case 10: {
                    if (b) {
                        str = "(Handle[])" + s;
                        break;
                    }
                    str = "(Handle)" + s;
                    break;
                }
                case 11: {
                    if (b) {
                        str = "(Memptr[])" + s;
                        break;
                    }
                    str = "(Memptr)" + s;
                    break;
                }
                case 13: {
                    if (b) {
                        str = "(Rowid[])" + s;
                        break;
                    }
                    str = "(Rowid)" + s;
                    break;
                }
                case 14: {
                    if (b) {
                        str = "(COMHandle[])" + s;
                        break;
                    }
                    str = "(COMHandle)" + s;
                    break;
                }
                case 15:
                case 17: {
                    str = "(java.sql.ResultSet)" + s;
                    break;
                }
                case 36:
                case 37: {
                    str = "(ProDataGraph)" + s;
                    break;
                }
                default: {
                    str = "";
                    break;
                }
            }
            if (b2) {
                str += ".getValue()";
            }
        }
        return str;
    }
    
    public String getOutputNameParameter(final int n, final boolean b) {
        return "null,";
    }
    
    public String getParameter(final PGParam pgParam, final boolean b) {
        return pgParam.m_strName + ".setValue(outValue);";
    }
    
    public String setFunctionType(final PGParam pgParam) {
        final int paramType = pgParam.getParamType();
        String str = new String("params.set");
        switch (paramType) {
            case 1: {
                str = str + "StringFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 39: {
                str += "LongcharFunction();";
                break;
            }
            case 2: {
                str = str + "DateFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 34: {
                str = str + "DateTimeFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 40: {
                str = str + "DateTimeTzFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 3: {
                str = str + "BooleanFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 4: {
                str = str + "IntegerFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 41: {
                str = str + "Int64Function(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 5: {
                str = str + "DecimalFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 7: {
                str = str + "LongFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 8: {
                str = str + "ByteFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 10: {
                str = str + "HandleFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 11: {
                str += "MemptrFunction();";
                break;
            }
            case 13: {
                str = str + "RowidFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 14: {
                str = str + "COMHandleFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
        }
        return str;
    }
    
    private String getEndOfFuncTypeString(final PGParam pgParam) {
        String string = "";
        if (pgParam.isExtentField()) {
            String str;
            if (pgParam.effectiveAllowUnknown()) {
                str = "true, true, ";
            }
            else {
                str = "true, false, ";
            }
            string = str + pgParam.getExtent();
        }
        return string + ");";
    }
    
    public String setMetaData(final int n) {
        String s = "";
        switch (n) {
            case 1: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_CHARACTER%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 2: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DATE%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 34: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DATETIME%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 40: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DATETIMETZ%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 3: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_LOGICAL%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 4: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_INTEGER%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 41: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_INT64%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 5: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DECIMAL%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 7: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_RECID%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 8: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_RAW%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 10: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_WIDGETHANDLE%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 11: {
                break;
            }
            case 39: {
                break;
            }
            case 13: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_ROWID%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 14: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_COMHANDLE%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 18: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_BLOB%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            case 19: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_CLOB%UserOrder%%XMLMapping%);" + JavaDataMapper.lineSep;
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return s;
    }
    
    public String getTraceVal(final PGParam pgParam) {
        return "";
    }
    
    static {
        lineSep = System.getProperty("line.separator");
    }
}

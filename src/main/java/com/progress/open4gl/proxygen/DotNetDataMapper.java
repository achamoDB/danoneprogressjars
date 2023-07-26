// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

class DotNetDataMapper implements IDataTypeMapper
{
    private static final String lineSep;
    protected static final int NO_UNKNOWN = 0;
    protected static final int HOLDER_CLASS = 1;
    protected static final int NULLABLE_TYPE = 2;
    
    protected static int getUnknownType(final PGParam pgParam, final boolean b) {
        int n = 0;
        if (pgParam.effectiveAllowUnknown()) {
            if (b) {
                n = 2;
            }
            else if (dotNetTypeNeedsHolderClass(pgParam)) {
                n = 1;
            }
        }
        return n;
    }
    
    public String proToNative(final PGParam pgParam) {
        return this.proToNative(pgParam, null);
    }
    
    public String proToNative(final PGParam pgParam, final boolean b) {
        return this.proToNative(pgParam.m_enumType, pgParam.m_bIsExtentField, getUnknownType(pgParam, b), null);
    }
    
    public String proToNative(final PGParam pgParam, final String s) {
        return this.proToNative(pgParam.m_enumType, pgParam.m_bIsExtentField, getUnknownType(pgParam, false), s);
    }
    
    private String proToNative(final int n, final boolean b, final int n2, final String str) {
        String s = "";
        final int n3 = n & 0xFF;
        final int n4 = n >> 8;
        if (n4 == 3) {
            s = "ref ";
        }
        else if (n4 == 2) {
            s = "out ";
        }
        String str2 = null;
        switch (n3) {
            case 1: {
                str2 = s + "string";
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 2:
            case 34:
            case 40: {
                if (n2 == 1) {
                    str2 = s + "DateHolder";
                }
                else {
                    str2 = s + "System.DateTime";
                }
                if (n2 == 2) {
                    str2 += "?";
                }
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 3: {
                if (n2 == 1) {
                    str2 = s + "BooleanHolder";
                }
                else {
                    str2 = s + "bool";
                }
                if (n2 == 2) {
                    str2 += "?";
                }
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 4: {
                if (n2 == 1) {
                    str2 = s + "IntHolder";
                }
                else {
                    str2 = s + "int";
                }
                if (n2 == 2) {
                    str2 += "?";
                }
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 5: {
                if (n2 == 1) {
                    str2 = s + "DecimalHolder";
                }
                else {
                    str2 = s + "decimal";
                }
                if (n2 == 2) {
                    str2 += "?";
                }
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 7:
            case 41: {
                if (n2 == 1) {
                    str2 = s + "LongHolder";
                }
                else {
                    str2 = s + "long";
                }
                if (n2 == 2) {
                    str2 += "?";
                }
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 8: {
                str2 = s + "byte[]";
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 10: {
                str2 = s + "Handle";
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 11: {
                str2 = s + "Memptr";
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 13: {
                str2 = s + "Rowid";
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 14: {
                str2 = s + "COMHandle";
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            case 15:
            case 36: {
                str2 = s + str;
                break;
            }
            case 17: {
                str2 = s + "System.Data.DataTable";
                break;
            }
            case 37: {
                str2 = s + "System.Data.DataSet";
                break;
            }
            case 39: {
                str2 = s + "string";
                if (b) {
                    str2 += "[]";
                    break;
                }
                break;
            }
            default: {
                str2 = "";
                break;
            }
        }
        return str2;
    }
    
    public String getModifier(final int n) {
        String s = "";
        final int n2 = n >> 8;
        if (n2 == 3) {
            s = "ref ";
        }
        else if (n2 == 2) {
            s = "out ";
        }
        return s;
    }
    
    public String setParameter(final PGParam pgParam) {
        String str = "parms.";
        String str2 = "";
        final boolean bIsExtentField = pgParam.m_bIsExtentField;
        final int n = pgParam.m_enumType & 0xFF;
        final int n2 = pgParam.m_enumType >> 8;
        switch (n) {
            case 1: {
                if (bIsExtentField) {
                    str += "setStringArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setStringParameter(%Position%, %Name%,";
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
                str += "setDataTableParameter(%Position%, %Name%,";
                str2 = ((PGDataTableParam)pgParam).getStrongName();
                break;
            }
            case 17: {
                str += "setDynDataTableParameter(%Position%, %Name%,";
                break;
            }
            case 36: {
                str += "setDataSetParameter(%Position%, %Name%,";
                str2 = ((PGDataSetParam)pgParam).getStrongName();
                break;
            }
            case 37: {
                str += "setDynDataSetParameter(%Position%, %Name%,";
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
                    str += "setDateTimeTZArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setDateTimeTZParameter(%Position%, %Name%,";
                break;
            }
            case 39: {
                if (bIsExtentField) {
                    str += "setLongCharArrayParameter(%Position%, %Name%,";
                    break;
                }
                str += "setLongCharParameter(%Position%, %Name%,";
                break;
            }
        }
        String s = null;
        switch (n2) {
            case 1: {
                s = str + " ParameterSet.INPUT";
                break;
            }
            case 2: {
                s = str + " ParameterSet.OUTPUT";
                break;
            }
            default: {
                s = str + " ParameterSet.INPUT_OUTPUT";
                break;
            }
        }
        if (str2 != "") {
            s = s + ", \"" + str2 + "\"";
        }
        else if (bIsExtentField) {
            s += ", %ExtentValue%";
        }
        if (n2 == 2) {
            switch (n) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 34:
                case 40:
                case 41: {
                    s += ", %IsHolderClass%";
                    break;
                }
            }
        }
        return s + ");";
    }
    
    public String setParameter(final PGParam pgParam, final String s, final boolean b, final boolean b2) {
        String strongName = null;
        final int n = pgParam.m_enumType & 0xFF;
        if (n == 36 || n == 15) {
            strongName = ((IPGStrongNameParam)pgParam).getStrongName();
        }
        return this.setParameter(s, pgParam.m_enumType, pgParam.m_bIsExtentField, b, strongName, getUnknownType(pgParam, b2));
    }
    
    private String setParameter(final String s, final int n, final boolean b, final boolean b2, final String str, final int n2) {
        final int n3 = n & 0xFF;
        String s2 = null;
        if (n >> 8 == 1) {
            s2 = s;
        }
        else {
            switch (n3) {
                case 1:
                case 39: {
                    if (b) {
                        s2 = "(string[])" + s;
                        break;
                    }
                    s2 = "(string)" + s;
                    break;
                }
                case 2:
                case 34:
                case 40: {
                    if (n2 == 1) {
                        s2 = s;
                        break;
                    }
                    String str2 = "(System.DateTime";
                    if (n2 == 2) {
                        str2 += "?";
                    }
                    if (b) {
                        str2 += "[]";
                    }
                    s2 = str2 + ")" + s;
                    break;
                }
                case 3: {
                    if (n2 == 1) {
                        s2 = s;
                        break;
                    }
                    String str3 = "(bool";
                    if (n2 == 2) {
                        str3 += "?";
                    }
                    if (b) {
                        str3 += "[]";
                    }
                    s2 = str3 + ")" + s;
                    break;
                }
                case 4: {
                    if (n2 == 1) {
                        s2 = s;
                        break;
                    }
                    String str4 = "(int";
                    if (n2 == 2) {
                        str4 += "?";
                    }
                    if (b) {
                        str4 += "[]";
                    }
                    s2 = str4 + ")" + s;
                    break;
                }
                case 5: {
                    if (n2 == 1) {
                        s2 = s;
                        break;
                    }
                    String str5 = "(decimal";
                    if (n2 == 2) {
                        str5 += "?";
                    }
                    if (b) {
                        str5 += "[]";
                    }
                    s2 = str5 + ")" + s;
                    break;
                }
                case 7:
                case 41: {
                    if (n2 == 1) {
                        s2 = s;
                        break;
                    }
                    String str6 = "(long";
                    if (n2 == 2) {
                        str6 += "?";
                    }
                    if (b) {
                        str6 += "[]";
                    }
                    s2 = str6 + ")" + s;
                    break;
                }
                case 8: {
                    if (b) {
                        s2 = "(byte[][])" + s;
                        break;
                    }
                    s2 = "(byte[])" + s;
                    break;
                }
                case 10: {
                    if (b) {
                        s2 = "(Handle[])" + s;
                        break;
                    }
                    s2 = "(Handle)" + s;
                    break;
                }
                case 11: {
                    if (b) {
                        s2 = "(Memptr[])" + s;
                        break;
                    }
                    s2 = "(Memptr)" + s;
                    break;
                }
                case 13: {
                    if (b) {
                        s2 = "(Rowid[])" + s;
                        break;
                    }
                    s2 = "(Rowid)" + s;
                    break;
                }
                case 14: {
                    if (b) {
                        s2 = "(COMHandle[])" + s;
                        break;
                    }
                    s2 = "(COMHandle)" + s;
                    break;
                }
                case 15:
                case 36: {
                    s2 = "(" + str + ")" + s;
                    break;
                }
                case 37: {
                    s2 = "(System.Data.DataSet)" + s;
                    break;
                }
                case 17: {
                    s2 = "(System.Data.DataTable)" + s;
                    break;
                }
                default: {
                    s2 = "";
                    break;
                }
            }
        }
        return s2;
    }
    
    public String getOutputNameParameter(final int n, final boolean b) {
        final int n2 = n & 0xFF;
        String s = null;
        if (b) {
            switch (n2) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 7:
                case 34:
                case 40:
                case 41: {
                    s = "(object)null,";
                    break;
                }
                default: {
                    s = "null,";
                    break;
                }
            }
        }
        else {
            switch (n2) {
                case 1:
                case 2:
                case 8:
                case 10:
                case 11:
                case 13:
                case 14:
                case 15:
                case 17:
                case 34:
                case 36:
                case 37:
                case 39:
                case 40: {
                    s = "null,";
                    break;
                }
                case 3: {
                    s = "false,";
                    break;
                }
                case 4: {
                    s = "0,";
                    break;
                }
                case 7:
                case 41: {
                    s = "0L,";
                    break;
                }
                case 5: {
                    s = "0.0,";
                    break;
                }
                default: {
                    s = "";
                    break;
                }
            }
        }
        return s;
    }
    
    public String getParameter(final PGParam pgParam, final boolean b) {
        String s = "";
        String s2 = null;
        final int n = pgParam.m_enumType & 0xFF;
        if (n == 36) {
            s2 = ((PGDataSetParam)pgParam).getStrongName();
        }
        else if (n == 15) {
            s2 = ((PGDataTableParam)pgParam).getStrongName();
        }
        final int unknownType = getUnknownType(pgParam, b);
        if (unknownType == 1 && pgParam.isExtentField()) {
            switch (n) {
                case 2:
                case 34:
                case 40: {
                    s = pgParam.m_strName + " = (DateHolder[]) outValue;" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 3: {
                    s = pgParam.m_strName + " = (BooleanHolder[]) outValue;" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 4: {
                    s = pgParam.m_strName + " = (IntHolder[]) outValue;" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 5: {
                    s = pgParam.m_strName + " = (DecimalHolder[]) outValue;" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 7:
                case 41: {
                    s = pgParam.m_strName + " = (LongHolder[]) outValue;" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
            }
        }
        else if (unknownType == 1) {
            String str = "";
            switch (n) {
                case 2:
                case 34:
                case 40: {
                    str = pgParam.m_strName + " = new DateHolder();" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 3: {
                    str = pgParam.m_strName + " = new BooleanHolder();" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 4: {
                    str = pgParam.m_strName + " = new IntHolder();" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 5: {
                    str = pgParam.m_strName + " = new DecimalHolder();" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
                case 7:
                case 41: {
                    str = pgParam.m_strName + " = new LongHolder();" + DotNetDataMapper.lineSep + "\t\t";
                    break;
                }
            }
            s = str + pgParam.m_strName + ".Value = outValue;";
        }
        else {
            s = pgParam.m_strName + " = " + this.setParameter("outValue;", pgParam.m_enumType, pgParam.m_bIsExtentField, false, s2, unknownType);
        }
        return s;
    }
    
    public String setFunctionType(final PGParam pgParam) {
        final int paramType = pgParam.getParamType();
        String str = new String("parms.set");
        switch (paramType) {
            case 1: {
                str = str + "StringFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 2: {
                str = str + "DateFunction(" + this.getEndOfFuncTypeString(pgParam);
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
            case 39: {
                str += "LongCharFunction();";
                break;
            }
            case 34: {
                str = str + "DateTimeFunction(" + this.getEndOfFuncTypeString(pgParam);
                break;
            }
            case 40: {
                str = str + "DateTimeTZFunction(" + this.getEndOfFuncTypeString(pgParam);
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
        String s = null;
        switch (n) {
            case 1: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_CHARACTER, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 2: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DATE, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 34: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DATETIME, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 40: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DATETIMETZ, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 3: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_LOGICAL, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 4: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_INTEGER, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 41: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_INT64, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 5: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_DECIMAL, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 7: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_RECID, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 8: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_RAW, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 10: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_WIDGETHANDLE, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 11: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_MEMPTR, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 13: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_ROWID, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 14: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_COMHANDLE, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 18: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_BLOB, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            case 19: {
                s = "\t\t%MetaDataName%.setFieldDesc(%Position%, \"%FieldName%\", %Extent%, Parameter.PRO_CLOB, %Position_2Based%, %UserOrder%, %Flag%, \"%InitValue%\", \"%Caption%\", %XMLMapping%, null, \"%HelpStr%\");" + DotNetDataMapper.lineSep;
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return s;
    }
    
    public String setDataTableColumnSchema(final int n) {
        String s = null;
        switch (n) {
            case 1:
            case 19: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(string));" + DotNetDataMapper.lineSep;
                break;
            }
            case 2:
            case 34:
            case 40: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(System.DateTime));" + DotNetDataMapper.lineSep;
                break;
            }
            case 3: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(bool));" + DotNetDataMapper.lineSep;
                break;
            }
            case 4: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(int));" + DotNetDataMapper.lineSep;
                break;
            }
            case 5: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(decimal));" + DotNetDataMapper.lineSep;
                break;
            }
            case 7:
            case 41: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(long));" + DotNetDataMapper.lineSep;
                break;
            }
            case 8:
            case 18: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(byte[]));" + DotNetDataMapper.lineSep;
                break;
            }
            case 10: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(long));" + DotNetDataMapper.lineSep;
                break;
            }
            case 11: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(long));" + DotNetDataMapper.lineSep;
                break;
            }
            case 13: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(byte[]));" + DotNetDataMapper.lineSep;
                break;
            }
            case 14: {
                s = "\t    %TableName%.Columns.Add(\"%ColumnName%\", typeof(long));" + DotNetDataMapper.lineSep;
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
    
    public static boolean dotNetTypeNeedsHolderClass(final PGParam pgParam) {
        final int n = pgParam.m_enumType & 0xFF;
        boolean b = false;
        switch (n) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 34:
            case 40:
            case 41: {
                b = true;
                break;
            }
        }
        return b;
    }
    
    static {
        lineSep = System.getProperty("line.separator");
    }
}

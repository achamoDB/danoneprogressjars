// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import com.progress.international.resources.ProgressResources;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.Open4GLException;
import com.progress.message.pgMsg;

public class Generator implements StdTemplate, pgMsg
{
    protected static final int AOTEMPLATE = 1;
    protected static final int AOIMPLTEMPLATE = 2;
    protected static final int POTEMPLATE = 3;
    protected static final int POIMPLTEMPLATE = 4;
    protected static final int SOTEMPLATE = 5;
    protected static final int SOIMPLTEMPLATE = 6;
    protected static final int DN_AOTEMPLATE = 7;
    protected static final int DN_POTEMPLATE = 8;
    protected static final int DN_SOTEMPLATE = 9;
    protected static final int DN_DSTEMPLATE = 10;
    protected static final int DN_AITEMPLATE = 11;
    protected static final String AOTEMPLATENAME = "JavaAO.tpl";
    protected static final String AOIMPLTEMPLATENAME = "JavaAOImpl.tpl";
    protected static final String POTEMPLATENAME = "JavaPO.tpl";
    protected static final String POIMPLTEMPLATENAME = "JavaPOImpl.tpl";
    protected static final String SOTEMPLATENAME = "JavaSO.tpl";
    protected static final String SOIMPLTEMPLATENAME = "JavaSOImpl.tpl";
    protected static final String DN_AOTEMPLATENAME = "DotNetAO.tpl";
    protected static final String DN_POTEMPLATENAME = "DotNetPO.tpl";
    protected static final String DN_SOTEMPLATENAME = "DotNetSO.tpl";
    protected static final String DN_DSTEMPLATENAME = "DotNetDS.tpl";
    protected static final String DN_AITEMPLATENAME = "DotNetAI.tpl";
    protected static final String[] templateArray;
    private static final String separator;
    protected static final String RESOURCESZIPFILE = "proxygen.zip";
    protected static final String RESOURCESPATH;
    private static final String lineSep;
    protected static IDataTypeMapper mapper;
    protected static ISchemaTemplate schemaTemplate;
    
    protected static String buildMetaData(final PGParam[] array, final String str, final StringBuffer sb, final StringBuffer sb2, final boolean b) {
        String string = null;
        int n = 1;
        String s = null;
        final boolean anyTempTables = anyTempTables(array);
        for (int i = 0; i < array.length; ++i) {
            final PGParam pgParam = array[i];
            final int effectiveParamType = pgParam.getEffectiveParamType(!b);
            final boolean b2 = effectiveParamType == 15 || effectiveParamType == 17;
            final boolean b3 = effectiveParamType == 36 || effectiveParamType == 37;
            if (b2 || b3) {
                if (string == null && !anyTempTables) {
                    string = str + "_MetaSchema";
                    sb.append("\tstatic MetaSchema ");
                    sb.append(string);
                    sb.append(";");
                    sb2.append(string);
                    sb2.append(" = new MetaSchema();" + Generator.lineSep);
                }
                if (b2) {
                    final String metaSchemaSet = Generator.schemaTemplate.getMetaSchemaSet();
                    if (effectiveParamType == 15) {
                        s = buildTempTableMetaData(pgParam, 0, n++, str, sb, sb2, b, string);
                    }
                    if (!anyTempTables) {
                        addMetaDataToMetaSchema(pgParam, sb2, string, s, metaSchemaSet);
                    }
                }
                else {
                    final String dsMetaSchemaSet = Generator.schemaTemplate.getDSMetaSchemaSet();
                    if (effectiveParamType == 36) {
                        s = buildDataSetMetaData(pgParam, n++, str, sb, sb2, b, string);
                    }
                    if (!anyTempTables) {
                        addMetaDataToMetaSchema(pgParam, sb2, string, s, dsMetaSchemaSet);
                    }
                }
            }
        }
        return string;
    }
    
    private static String buildTempTableMetaData(final PGParam pgParam, final int i, final int n, final String s, final StringBuffer sb, final StringBuffer sb2, final boolean b, final String s2) {
        String str = null;
        boolean b2 = false;
        if (pgParam.m_pMetaData.length > 0) {
            if (i > 0) {
                str = s + "_MetaData" + Integer.toString(i) + Integer.toString(n);
            }
            else {
                str = s + "_MetaData" + Integer.toString(n);
                b2 = true;
            }
            sb.append(Generator.schemaTemplate.getStaticTTMetaData(b2));
            sb.append(str);
            sb.append(";" + Generator.lineSep);
            sb2.append("\t\t");
            sb2.append(str);
            sb2.append(Generator.schemaTemplate.getNewTTMetaData(b2));
            if (b || !b2) {
                final PGDataTableParam pgDataTableParam = (PGDataTableParam)pgParam;
                sb2.append("\"" + pgDataTableParam.getParamName());
                sb2.append("\", ");
                sb2.append(Integer.toString(pgParam.m_pMetaData.length));
                sb2.append(", ");
                if (pgDataTableParam.hasBeforeTable()) {
                    sb2.append("true, ");
                }
                else {
                    sb2.append("false, ");
                }
                int j = 0;
                final String multiIndexCols = pgDataTableParam.getMultiIndexCols();
                if (multiIndexCols != null) {
                    int k = 0;
                    j = 1;
                    while (k < multiIndexCols.length()) {
                        if (multiIndexCols.charAt(k) == '.') {
                            ++j;
                        }
                        ++k;
                    }
                }
                sb2.append(Integer.toString(j));
                sb2.append(", ");
                if (multiIndexCols == null) {
                    sb2.append("null, ");
                }
                else {
                    sb2.append("\"");
                    sb2.append(multiIndexCols);
                    sb2.append("\", ");
                }
                sb2.append("null, null");
                if (b) {
                    sb2.append(", \"" + pgDataTableParam.getStrongName() + "\"");
                }
            }
            else {
                sb2.append(Integer.toString(pgParam.m_pMetaData.length));
            }
            sb2.append(");" + Generator.lineSep);
            if (!b && !b2) {
                final PGDataTableParam pgDataTableParam2 = (PGDataTableParam)pgParam;
                if (pgDataTableParam2.hasUniquePrimaryKey()) {
                    sb2.append(insertVariable("\t\t" + str + Generator.schemaTemplate.getPrimeIdxNaming(b2), "%PrimeIdxName%", pgDataTableParam2.getPrimeKeyIndexName()));
                }
            }
            for (int l = 0; l < pgParam.m_pMetaData.length; ++l) {
                final PGMetaData pgMetaData = pgParam.m_pMetaData[l];
                final String insertVariable = insertVariable(insertVariable(insertVariable(insertVariable(Generator.mapper.setMetaData(pgMetaData.m_enumType), "%MetaDataName%", str), "%Position%", Integer.toString(l + 1)), "%FieldName%", pgMetaData.m_strFieldName), "%Extent%", Integer.toString(pgMetaData.m_nExtent));
                String str2;
                if (b) {
                    str2 = insertVariable(insertVariable(insertVariable(insertVariable(insertVariable(insertVariable(insertVariable(insertVariable, "%Position_2Based%", Integer.toString(pgMetaData.getPosition())), "%UserOrder%", Integer.toString(pgMetaData.getUserOrder())), "%Flag%", Integer.toString(pgMetaData.getFlag())), "%InitValue%", pgMetaData.getInitialValue()), "%Caption%", pgMetaData.getCaption()), "%XMLMapping%", Integer.toString(pgMetaData.getXMLMapping())), "%HelpStr%", pgMetaData.getHelpString());
                }
                else if (!b2) {
                    final PGDataTableParam pgDataTableParam3 = (PGDataTableParam)pgParam;
                    str2 = insertVariable(insertVariable(insertVariable, "%UserOrder%", "," + Integer.toString(pgMetaData.getUserOrder())), "%XMLMapping%", "," + Integer.toString(pgMetaData.getXMLMapping()));
                }
                else {
                    str2 = insertVariable(insertVariable, "%UserOrder%%XMLMapping%", "");
                }
                sb2.append(str2);
            }
        }
        return str;
    }
    
    protected static void buildDataLinkMetaData(final PGDataLink pgDataLink, final int i, final int j, final String str, final StringBuffer sb, final StringBuffer sb2, final String str2) {
        final String string = str + "_MetaLink" + Integer.toString(i) + Integer.toString(j);
        sb.append(Generator.schemaTemplate.getStaticDataRelation());
        sb.append(string);
        sb.append(";" + Generator.lineSep);
        sb2.append("\t\t");
        sb2.append(string);
        sb2.append(Generator.schemaTemplate.getNewDataRelation());
        final String linkName = pgDataLink.getLinkName();
        if (linkName != null && linkName.length() > 0) {
            sb2.append("\"" + linkName + "\", ");
        }
        else {
            sb2.append("null , ");
        }
        sb2.append(pgDataLink.getParentIndex());
        sb2.append(", ");
        sb2.append(pgDataLink.getChildIndex());
        sb2.append(", ");
        sb2.append(pgDataLink.numFieldPairs());
        sb2.append(", ");
        final String pairsList = pgDataLink.getPairsList();
        if (pairsList != null && pairsList.length() > 0) {
            sb2.append("\"" + pairsList + "\", ");
        }
        else {
            sb2.append("null , ");
        }
        sb2.append(pgDataLink.getFlag());
        sb2.append(", null);");
        sb2.append(Generator.lineSep + "\t\t");
        sb2.append(str2);
        sb2.append(".addDataRelation(");
        sb2.append(string);
        sb2.append(");" + Generator.lineSep);
    }
    
    protected static void addMetaDataToMetaSchema(final PGParam pgParam, final StringBuffer sb, final String s, final String s2, final String s3) {
        final String insertVariable = insertVariable(s3, "%MetaSchemaName%", s);
        String s4;
        if (pgParam.getParamType() == 36 || pgParam.getParamType() == 15) {
            s4 = insertVariable(insertVariable, "%MetaDataName%", s2);
        }
        else {
            s4 = insertVariable(insertVariable, "%MetaDataName%", "null");
        }
        final String insertVariable2 = insertVariable(s4, "%Position%", Integer.toString(pgParam.m_nParamNum));
        final int paramMode = pgParam.getParamMode();
        String s5;
        if (paramMode == 1) {
            s5 = insertVariable(insertVariable2, "%ParamType%", "ParameterSet.INPUT");
        }
        else if (paramMode == 3) {
            s5 = insertVariable(insertVariable2, "%ParamType%", "ParameterSet.INPUT_OUTPUT");
        }
        else {
            s5 = insertVariable(insertVariable2, "%ParamType%", "ParameterSet.OUTPUT");
        }
        String str;
        if (pgParam.isEffectiveTTDataSet()) {
            str = insertVariable(s5, "%mappedTT%", ", true");
        }
        else {
            str = insertVariable(s5, "%mappedTT%", ", false");
        }
        sb.append(str);
    }
    
    protected static String buildDataSetMetaData(final PGParam pgParam, final int i, final String str, final StringBuffer sb, final StringBuffer sb2, final boolean b, final String s) {
        String string = null;
        int n = 1;
        int n2 = 1;
        if (pgParam.getEffectiveParamType(!b) == 36) {
            PGDataLink[] dataLinks;
            PGDataTableParam[] dataSetTables;
            if (pgParam.getParamType() == 15) {
                dataLinks = new PGDataLink[0];
                dataSetTables = new PGDataTableParam[] { (PGDataTableParam)pgParam };
            }
            else {
                dataLinks = ((PGDataSetParam)pgParam).getDataLinks();
                dataSetTables = ((PGDataSetParam)pgParam).getDataSetTables();
            }
            string = str + "_DSMetaData" + Integer.toString(i);
            sb.append(Generator.schemaTemplate.getStaticDSMetaData());
            sb.append(string);
            sb.append(";" + Generator.lineSep);
            sb2.append("\t\t");
            sb2.append(string);
            sb2.append(Generator.schemaTemplate.getNewDSMetaData());
            sb2.append(pgParam.getParamName());
            sb2.append("\", ");
            sb2.append(Integer.toString(pgParam.m_nParamNum));
            switch (pgParam.getParamMode()) {
                case 1: {
                    sb2.append(", ParameterSet.INPUT");
                    break;
                }
                case 2: {
                    sb2.append(", ParameterSet.OUTPUT");
                    break;
                }
                default: {
                    sb2.append(", ParameterSet.INPUT_OUTPUT");
                    break;
                }
            }
            if (b) {
                sb2.append(", \"" + ((PGDataSetParam)pgParam).getStrongName() + "\");" + Generator.lineSep);
            }
            else {
                sb2.append(");" + Generator.lineSep);
            }
            for (int j = 0; j < dataSetTables.length; ++j) {
                addTTMetaDataToDSMetaSchema(sb2, string, buildTempTableMetaData(dataSetTables[j], i, n++, str, sb, sb2, b, s));
            }
            for (int k = 0; k < dataLinks.length; ++k) {
                buildDataLinkMetaData(dataLinks[k], i, n2++, str, sb, sb2, string);
            }
        }
        return string;
    }
    
    protected static void addTTMetaDataToDSMetaSchema(final StringBuffer sb, final String s, final String s2) {
        sb.append(insertVariable(insertVariable(Generator.schemaTemplate.getDTToDSMetaSchemaSet(), "%DSMetaSchemaName%", s), "%DTMetaDataName%", s2));
    }
    
    protected static String buildMethod(final String s, final String s2, final boolean b, final boolean b2, final IPGDetail ipgDetail, final String s3, final StringBuffer sb) {
        return buildMethod(s, s2, b, b2, ipgDetail, s3, sb, null);
    }
    
    protected static String buildMethod(final String s, final String s2, final boolean b, final boolean b2, final IPGDetail ipgDetail, final String s3, final StringBuffer sb, final StringBuffer sb2) {
        final String helpString = ipgDetail.getHelpString();
        final String methodName = ipgDetail.getMethodName();
        String paramName = null;
        PGGenInfo.getCurrentAppObj();
        final boolean dnUseNullableTypes = PGAppObj.getGenInfo().getDNUseNullableTypes();
        final PGParam[] parameters = ipgDetail.getParameters();
        final PGParam returnValue = ipgDetail.getReturnValue();
        int n = 0;
        final StringBuffer sb3 = new StringBuffer(4096);
        final StringBuffer sb4 = new StringBuffer(4096);
        final StringBuffer sb5 = new StringBuffer(128);
        String s4 = s;
        if (!b) {
            final String insertVariable = insertVariable(s4, "%HelpString%", helpString);
            if (returnValue == null) {
                s4 = insertVariable(insertVariable, "%Return%", "void");
            }
            else {
                s4 = insertVariable(insertVariable, "%Return%", Generator.mapper.proToNative(returnValue, dnUseNullableTypes));
            }
        }
        final String insertVariable2 = insertVariable(s4, "%Name%", methodName);
        String str = buildMetaData(parameters, methodName, sb3, sb4, b2);
        if (sb3.length() != 0 && sb4.length() != 0) {
            if (b2) {
                sb.append(insertVariable(DNStdTemplate.szMetaDeclareInit, "%MetaDeclare%", sb3.toString()));
                sb2.append(insertVariable(DNStdTemplate.szMetaStaticInit, "%MetaStatic%", sb4.toString()));
            }
            else {
                sb.append(insertVariable(insertVariable(StdTemplate.szStaticInit, "%MetaDeclare%", sb3.toString()), "%MetaStatic%", sb4.toString()));
            }
        }
        for (int i = 0; i < parameters.length; ++i) {
            final PGParam pgParam = parameters[i];
            if (b || i > 0) {
                sb5.append(", ");
            }
            if (i > 0 && i % 5 == 0) {
                sb5.append(Generator.lineSep);
            }
            final String paramName2 = pgParam.getParamName();
            final int n2 = pgParam.m_enumType & 0xFF;
            final int n3 = pgParam.m_enumType >> 8;
            if (!b2 && pgParam.isEffectiveTTResultSet() && (n3 == 3 || n3 == 2)) {
                paramName = pgParam.getParamName();
            }
            if (b2 && (n2 == 36 || n2 == 37) && (n3 == 3 || n3 == 2)) {
                n = 1;
            }
            String str2;
            if (n2 == 36 || n2 == 15) {
                str2 = Generator.mapper.proToNative(pgParam, ((IPGStrongNameParam)pgParam).getStrongName());
            }
            else {
                str2 = Generator.mapper.proToNative(pgParam, dnUseNullableTypes);
            }
            sb5.append(str2 + " " + paramName2);
        }
        String s5 = insertVariable(insertVariable(insertVariable(insertVariable(insertVariable(insertVariable2, "%Params%", sb5.toString()), "%NumParams%", Integer.toString(parameters.length)), "%InParams%", setParameters(parameters, 1)), "%InOutParams%", setParameters(parameters, 3)), "%OutParams%", setParameters(parameters, 2));
        if (!b) {
            if (returnValue == null || ipgDetail.getProcType() == 1) {
                s5 = insertVariable(s5, "%SetRetType%", "");
            }
            else {
                s5 = insertVariable(s5, "%SetRetType%", Generator.mapper.setFunctionType(returnValue));
            }
        }
        String s6;
        if (anyTempTables(parameters)) {
            str = methodName + "_MetaSchema";
            s6 = insertVariable(insertVariable(s5, "%NewMetaSchema%", insertVariable("\t\tMetaSchema %SchemaName% = new MetaSchema();", "%SchemaName%", str)), "%AddSchemas%", addAllMetaDataToMetaSchema(str, methodName, parameters, b2));
        }
        else {
            s6 = insertVariable(insertVariable(s5, "%NewMetaSchema%", ""), "%AddSchemas%", "");
        }
        String s7 = insertVariable(s2, "%ProProcName%", s3);
        if (b2) {
            if (str == null) {
                s7 = insertVariable(s7, "%parms%", "parms");
                str = "";
            }
            else {
                s7 = insertVariable(s7, "%parms%", "parms, ");
            }
            if (n == 1) {
                s6 = insertVariable(s6, "%DSFillErrorString%", DNImplTemplate.szDSFillErrorString);
            }
            else {
                s6 = insertVariable(s6, "%DSFillErrorString%", "");
            }
        }
        else if (str == null) {
            str = "";
        }
        else {
            str = ", " + str;
        }
        String s8 = insertVariable(insertVariable(s6, "%RunProc%", insertVariable(s7, "%MetaSchema%", str)), "%GetOut%", getParameters(parameters, b2));
        if (!b2) {
            if (paramName != null) {
                s8 = insertVariable(s8, "%LastResultSetHolder%", "(com.progress.open4gl.dynamicapi.ResultSet) " + paramName + ".getResultSetValue()");
            }
            else {
                s8 = insertVariable(s8, "%LastResultSetHolder%", "null");
            }
        }
        return s8;
    }
    
    protected static String setParameters(final PGParam[] array, final int n) {
        final StringBuffer sb = new StringBuffer(512);
        PGGenInfo.getCurrentAppObj();
        final boolean dnUseNullableTypes = PGAppObj.getGenInfo().getDNUseNullableTypes();
        for (int i = 0; i < array.length; ++i) {
            final PGParam parameter = array[i];
            final int n2 = parameter.m_enumType & 0xFF;
            final int n3 = parameter.m_enumType >> 8;
            final int n4 = (parameter.m_enumType & 0xFF00) + n2;
            if (n == n3) {
                sb.append("\t\t");
                String s = insertVariable(Generator.mapper.setParameter(parameter), "%Position%", Integer.toString(parameter.m_nParamNum));
                if (parameter.m_bIsExtentField) {
                    s = insertVariable(s, "%ExtentValue%", Integer.toString(parameter.m_nExtent));
                }
                String str;
                if (parameter.isEffectiveTTDataSet()) {
                    str = insertVariable(s, "%MappedTT%", "true");
                }
                else {
                    str = insertVariable(s, "%MappedTT%", "false");
                }
                switch (n) {
                    case 1: {
                        str = insertVariable(str, "%Name%", Generator.mapper.setParameter(parameter, parameter.m_strName, true, dnUseNullableTypes));
                        break;
                    }
                    case 3: {
                        str = insertVariable(str, "%Name%", Generator.mapper.setParameter(parameter, parameter.m_strName, true, dnUseNullableTypes));
                        break;
                    }
                    case 2: {
                        String s2;
                        if (parameter.m_bIsExtentField && (n2 == 7 || n2 == 4 || n2 == 3 || n2 == 41)) {
                            s2 = insertVariable(str, "%Name%", Generator.mapper.setParameter(parameter, "null", false, dnUseNullableTypes));
                        }
                        else {
                            s2 = insertVariable(str, "%Name%,", Generator.mapper.getOutputNameParameter(n4, parameter.m_bIsExtentField));
                        }
                        if (DotNetDataMapper.getUnknownType(parameter, dnUseNullableTypes) == 1) {
                            str = insertVariable(s2, "%IsHolderClass%", "true");
                            break;
                        }
                        str = insertVariable(s2, "%IsHolderClass%", "false");
                        break;
                    }
                }
                sb.append(str);
                sb.append(Generator.lineSep);
            }
        }
        return sb.toString();
    }
    
    protected static String getParameters(final PGParam[] array, final boolean b) {
        final StringBuffer sb = new StringBuffer(512);
        PGGenInfo.getCurrentAppObj();
        final boolean dnUseNullableTypes = PGAppObj.getGenInfo().getDNUseNullableTypes();
        final String getOutParam = Generator.schemaTemplate.getGetOutParam();
        for (int i = 0; i < array.length; ++i) {
            final PGParam pgParam = array[i];
            if (pgParam.m_enumType >> 8 != 1) {
                sb.append(insertVariable(insertVariable(getOutParam, "%Position%", Integer.toString(pgParam.m_nParamNum)), "%ParamMap%", Generator.mapper.getParameter(pgParam, dnUseNullableTypes)));
            }
        }
        return sb.toString();
    }
    
    protected static String extractTemplate(final int n) throws Open4GLException {
        return Zipper.extractFromZip(Generator.RESOURCESPATH + "proxygen.zip", Generator.templateArray[n - 1], null);
    }
    
    protected static String insertVariable(final String s, final String s2, final String s3) {
        final StringBuffer sb = new StringBuffer(512);
        int beginIndex = 0;
        int i = s.indexOf(s2);
        final String str = (s3 == null) ? "" : s3;
        while (i >= 0) {
            sb.append(s.substring(beginIndex, i));
            beginIndex = i + s2.length();
            sb.append(str);
            i = s.indexOf(s2, beginIndex);
        }
        if (beginIndex < s.length()) {
            sb.append(s.substring(beginIndex));
        }
        return sb.toString();
    }
    
    protected static String buildSchemaDoc(final PGParam[] array, final boolean b) {
        final ProgressResources resources = PGGenInfo.getResources();
        final StringBuffer sb = new StringBuffer(512);
        for (int i = 0; i < array.length; ++i) {
            final PGMetaData[] metaData = array[i].getMetaData();
            if (metaData != null) {
                if (metaData.length != 0) {
                    String str;
                    if (b) {
                        str = Generator.lineSep + "\t*\t" + resources.getTranString("PGPROXY_DotNetSchema");
                    }
                    else {
                        str = Generator.lineSep + "\t*\t" + resources.getTranString("PGPROXY_Schema");
                    }
                    switch (array[i].getParamMode()) {
                        case 1: {
                            str = insertVariable(str, "%mode%", resources.getTranString("PGPROXY_Input"));
                            break;
                        }
                        case 2: {
                            str = insertVariable(str, "%mode%", resources.getTranString("PGPROXY_Output"));
                            break;
                        }
                        case 3: {
                            str = insertVariable(str, "%mode%", resources.getTranString("PGPROXY_InputOutput"));
                            break;
                        }
                    }
                    sb.append(str);
                    sb.append(array[i].getParamOrdinal());
                    for (int j = 0; j < metaData.length; ++j) {
                        sb.append(Generator.lineSep + "\t*\t\tField:" + metaData[j].getFieldName() + " ");
                        sb.append(metaData[j].getTypeName() + " ");
                        if (b) {
                            sb.append("(" + Parameter.proToDotNetClass(metaData[j].getType()) + ")");
                        }
                        else {
                            sb.append("(" + Parameter.proToJavaClass(metaData[j].getType()) + ")");
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
    
    protected static String traceInParams(final String str, final PGParam[] array) {
        final StringBuffer sb = new StringBuffer(array.length * (StdTemplate.szTraceParam.length() + 30));
        int n = 1;
        for (int i = 0; i < array.length; ++i) {
            final PGParam pgParam = array[i];
            final int paramType = pgParam.getParamType();
            final boolean b = paramType == 15 || paramType == 17;
            if (pgParam.getParamMode() != 2 && !b && paramType != 8 && paramType != 11 && paramType != 13) {
                if (n != 0) {
                    sb.append("\t\t\ttracer.print(\"\");\n");
                    sb.append(insertVariable(insertVariable(StdTemplate.szTraceParam, "%traceName%", "Input parameter values for procedure"), "%traceVal%", "\"" + str + "\""));
                    n = 0;
                }
                sb.append(insertVariable(insertVariable(StdTemplate.szTraceParam, "%traceName%", pgParam.getParamName()), "%traceVal%", Generator.mapper.getTraceVal(pgParam)));
            }
        }
        return sb.toString();
    }
    
    protected static boolean anyTempTables(final PGParam[] array) {
        for (int i = 0; i < array.length; ++i) {
            final int paramType = array[i].getParamType();
            if (paramType == 15 || paramType == 36 || paramType == 17 || paramType == 37) {
                return true;
            }
        }
        return false;
    }
    
    protected static String addAllMetaDataToMetaSchema(final String s, final String s2, final PGParam[] array, final boolean b) {
        int n = 1;
        final StringBuffer sb = new StringBuffer(4096);
        for (int i = 0; i < array.length; ++i) {
            final PGParam pgParam = array[i];
            final int effectiveParamType = pgParam.getEffectiveParamType(!b);
            if (effectiveParamType == 15 || effectiveParamType == 17 || effectiveParamType == 36 || effectiveParamType == 37) {
                String s3;
                if (effectiveParamType == 15 || effectiveParamType == 17) {
                    s3 = Generator.schemaTemplate.getMetaSchemaSet();
                }
                else {
                    s3 = Generator.schemaTemplate.getDSMetaSchemaSet();
                }
                String s4;
                if (effectiveParamType == 15) {
                    s4 = s2 + "_MetaData" + Integer.toString(n++);
                }
                else if (effectiveParamType == 36) {
                    s4 = s2 + "_DSMetaData" + Integer.toString(n++);
                }
                else {
                    s4 = "null";
                }
                addMetaDataToMetaSchema(pgParam, sb, s, s4, s3);
            }
        }
        return sb.toString();
    }
    
    static {
        templateArray = new String[] { "JavaAO.tpl", "JavaAOImpl.tpl", "JavaPO.tpl", "JavaPOImpl.tpl", "JavaSO.tpl", "JavaSOImpl.tpl", "DotNetAO.tpl", "DotNetPO.tpl", "DotNetSO.tpl", "DotNetDS.tpl", "DotNetAI.tpl" };
        separator = System.getProperty("file.separator");
        RESOURCESPATH = PGGenInfo.INSTALLDIR + Generator.separator + "java" + Generator.separator;
        lineSep = System.getProperty("line.separator");
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import com.progress.open4gl.Open4GLException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class BuildSchemaDN extends Generator implements DNStdTemplate
{
    private static final String lineSep;
    
    protected static void buildSchemaFile(final PGParam pgParam) throws Exception, Open4GLException {
        final PGGenInfo genInfo = PGAppObj.getGenInfo();
        PrintWriter printWriter = null;
        final IPGStrongNameParam ipgStrongNameParam = (IPGStrongNameParam)pgParam;
        final String workDir = genInfo.getWorkDir();
        final String namespace = ipgStrongNameParam.getNamespace();
        final String className = ipgStrongNameParam.getClassName();
        final String dnPathFromNamespace = PGAppObj.getDNPathFromNamespace(workDir, namespace, true);
        final String string = className + "Schema.cs";
        genInfo.logIt(1, "PGLOG_Creating", string + " ...", 1);
        try {
            printWriter = new PrintWriter(new FileOutputStream(PGAppObj.getAbsFilename(dnPathFromNamespace, string)));
            final String template = Generator.extractTemplate(10);
            final StringBuffer buildSchemaDef = buildSchemaDef(pgParam, className);
            String s;
            if (namespace != null && namespace.length() > 0) {
                s = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(template, "%Namespace%", "namespace " + namespace), "%StartNSParen%", "{"), "%EndNSParen%", "}");
            }
            else {
                s = Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(template, "%Namespace%", ""), "%StartNSParen%", ""), "%EndNSParen%", "");
            }
            printWriter.print(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(s, "%DataSetName%", className), "%DataSetNameExt%", "DataSet"), "%MetaData%", buildSchemaDef.toString()));
            printWriter.close();
            printWriter = null;
        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
    
    private static StringBuffer buildSchemaDef(final PGParam pgParam, final String s) throws Open4GLException {
        final StringBuffer sb = new StringBuffer(8192);
        PGDataLink[] dataLinks = null;
        final DotNetDataMapper dotNetDataMapper = new DotNetDataMapper();
        final String property = System.getProperty("line.separator");
        sb.append(property);
        PGDataTableParam[] dataSetTables;
        if (pgParam.getParamType() == 36) {
            final PGDataSetParam pgDataSetParam = (PGDataSetParam)pgParam;
            dataSetTables = pgDataSetParam.getDataSetTables();
            dataLinks = pgDataSetParam.getDataLinks();
            final int length = dataSetTables.length;
            final int length2 = dataLinks.length;
        }
        else {
            final PGDataTableParam pgDataTableParam = (PGDataTableParam)pgParam;
            final int length = 1;
            final int length2 = 0;
            dataSetTables = new PGDataTableParam[] { pgDataTableParam };
        }
        for (final PGDataTableParam pgDataTableParam2 : dataSetTables) {
            String paramName;
            if (pgParam.getParamType() == 36) {
                paramName = pgDataTableParam2.getParamName();
            }
            else {
                paramName = s;
            }
            sb.append(Generator.insertVariable("\t    DataTable %TableName% = ds.Tables.Add(\"%TableName%\");" + property, "%TableName%", paramName));
            for (int j = 0; j < pgDataTableParam2.m_pMetaData.length; ++j) {
                final PGMetaData pgMetaData = pgDataTableParam2.m_pMetaData[j];
                final int extent = pgMetaData.getExtent();
                if (extent > 1) {
                    for (int k = 1; k <= extent; ++k) {
                        sb.append(Generator.insertVariable(Generator.insertVariable(dotNetDataMapper.setDataTableColumnSchema(pgMetaData.m_enumType), "%TableName%", paramName), "%ColumnName%", pgMetaData.m_strFieldName + Integer.toString(k)));
                    }
                }
                else {
                    sb.append(Generator.insertVariable(Generator.insertVariable(dotNetDataMapper.setDataTableColumnSchema(pgMetaData.m_enumType), "%TableName%", paramName), "%ColumnName%", pgMetaData.m_strFieldName));
                }
            }
            final String[] primeKeyColNames = pgDataTableParam2.getPrimeKeyColNames();
            if (pgDataTableParam2.hasUniquePrimaryKey() && primeKeyColNames != null && primeKeyColNames.length > 0) {
                final int length3 = primeKeyColNames.length;
                sb.append(Generator.insertVariable(property + "\t    keyCols = new DataColumn[%keyColsStr%];" + property, "%keyColsStr%", Integer.toString(length3)));
                for (int l = 0; l < length3; ++l) {
                    final String string = "\t    keyCols[%k%] = %TableName%.Columns[\"%colName%\"];" + property;
                    validateIndexType(pgDataTableParam2, primeKeyColNames[l]);
                    sb.append(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(string, "%k%", Integer.toString(l)), "%TableName%", paramName), "%colName%", primeKeyColNames[l]));
                }
                sb.append(Generator.insertVariable(Generator.insertVariable("\t    %TableName%.Constraints.Add(new System.Data.UniqueConstraint(\"%IndexName%\", keyCols, true));" + property, "%TableName%", paramName), "%IndexName%", pgDataTableParam2.getPrimeKeyIndexName()));
            }
            final String[][] nonPrimeKeyIndexes = pgDataTableParam2.getNonPrimeKeyIndexes();
            if (nonPrimeKeyIndexes != null && nonPrimeKeyIndexes.length > 0) {
                final String string2 = "\t    keyCols = new DataColumn[%colCntStr%];" + property;
                final String string3 = "\t    keyCols[%x%] = %TableName%.Columns[\"%colName%\"];" + property;
                final String string4 = "\t    %TableName%.Constraints.Add(new UniqueConstraint(\"%IndexName%\", keyCols, false));" + property;
                for (int n = 0; n < nonPrimeKeyIndexes.length; ++n) {
                    final String[] array = nonPrimeKeyIndexes[n];
                    if (!foundMatchingIndex(array, primeKeyColNames, nonPrimeKeyIndexes, n)) {
                        final String s2 = string2;
                        if (array.length == 1) {
                            final String string5 = "\t    %TableName%.Columns[\"%colName%\"].Unique = true;" + property;
                            validateIndexType(pgDataTableParam2, array[0]);
                            sb.append(Generator.insertVariable(Generator.insertVariable(string5, "%TableName%", paramName), "%colName%", array[0]));
                        }
                        else {
                            sb.append(Generator.insertVariable(s2, "%colCntStr%", Integer.toString(array.length)));
                            for (int m = 0; m < array.length; ++m) {
                                validateIndexType(pgDataTableParam2, array[m]);
                                sb.append(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(string3, "%x%", Integer.toString(m)), "%TableName%", paramName), "%colName%", array[m]));
                            }
                            sb.append(Generator.insertVariable(Generator.insertVariable(string4, "%TableName%", paramName), "%IndexName%", pgDataTableParam2.getNonPrimeKeyIndexName(n)));
                        }
                    }
                }
            }
        }
        for (final PGDataLink pgDataLink : dataLinks) {
            final int numFieldPairs = pgDataLink.numFieldPairs();
            final String parentBuffer = pgDataLink.getParentBuffer();
            final String childBuffer = pgDataLink.getChildBuffer();
            sb.append(Generator.insertVariable(DNStdTemplate.szDataColumns, "%NumFieldPairs%", Integer.toString(numFieldPairs)));
            for (int n3 = 0; n3 < numFieldPairs; ++n3) {
                sb.append(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(DNStdTemplate.szParentCol, "%Index%", Integer.toString(n3)), "%ParentBuffer%", parentBuffer), "%ParentColumnName%", pgDataLink.getParentField(n3)));
                sb.append(Generator.insertVariable(Generator.insertVariable(Generator.insertVariable(DNStdTemplate.szChildCol, "%Index%", Integer.toString(n3)), "%ChildBuffer%", childBuffer), "%ChildColumnName%", pgDataLink.getChildField(n3)));
            }
            sb.append(Generator.insertVariable(DNStdTemplate.szDataLink, "%RelationName%", pgDataLink.getLinkName()));
        }
        return sb;
    }
    
    private static void validateIndexType(final PGDataTableParam pgDataTableParam, final String anotherString) throws Open4GLException {
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PGBundle");
        int i = 0;
        while (i < pgDataTableParam.m_pMetaData.length) {
            final PGMetaData pgMetaData = pgDataTableParam.m_pMetaData[i];
            if (pgMetaData.getFieldName().equalsIgnoreCase(anotherString)) {
                if (pgMetaData.getType() == 13 || pgMetaData.getType() == 8) {
                    throw new Open4GLException(progressResources.getTranString("PGLOG_InvalidDotNETIndex"), new Object[] { anotherString, pgDataTableParam.getParamName() });
                }
                break;
            }
            else {
                ++i;
            }
        }
    }
    
    private static boolean foundMatchingIndex(final String[] array, final String[] array2, final String[][] array3, final int n) {
        final int length = array.length;
        int n2 = 0;
        if (length == array2.length) {
            for (int i = 0; i < length; ++i) {
                int j = 0;
                n2 = 0;
                while (j < length) {
                    if (array[i].compareTo(array2[j]) == 0) {
                        n2 = 1;
                        break;
                    }
                    ++j;
                }
                if (n2 == 0) {
                    break;
                }
            }
            if (n2 == 1) {
                return true;
            }
        }
        for (final String[] array4 : array3) {
            if (length == array4.length) {
                for (int l = 0; l < length; ++l) {
                    int n3 = 0;
                    n2 = 0;
                    while (n3 < length) {
                        if (array[l].compareTo(array4[n3]) == 0) {
                            n2 = 1;
                            break;
                        }
                        ++n3;
                    }
                    if (n2 == 0) {
                        break;
                    }
                }
                if (n2 == 1) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        lineSep = System.getProperty("line.separator");
    }
}

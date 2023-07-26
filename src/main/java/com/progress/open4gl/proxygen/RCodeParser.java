// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import com.progress.common.exception.ProException;
import com.progress.open4gl.Open4GLException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import sun.io.MalformedInputException;
import com.progress.international.Codepage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import com.progress.open4gl.Holder;
import java.util.Vector;
import com.progress.message.pgMsg;

public class RCodeParser implements pgMsg
{
    private static final int m_numRelevantKinds = 4;
    private static final String[] m_kind;
    private static final int m_numModes = 3;
    private static final String[] m_strMode;
    private static final int[] m_intMode;
    private static final int m_numDataTypes = 23;
    private static final String[] m_strDataType;
    private static final int[] m_intDataType;
    private static char[] m_buffer;
    private static int m_bufPos;
    private static int m_numElements;
    private static int m_numTempTables;
    private static PGGenInfo m_genInfo;
    private static Vector m_ttParams;
    private static PGMethod[] m_unsuppMethods;
    private static boolean m_suppressLogError;
    private static long[] m_warnings;
    private static int m_headerVersion;
    
    public static void parseFile(final String s, final boolean b, final PGMethod[] unsuppMethods, final PGGenInfo genInfo, final String[] array, final Holder holder, final Holder holder2) throws Open4GLException {
        String s2 = null;
        final String absFilename = PGAppObj.getAbsFilename(null, s);
        RCodeParser.m_genInfo = genInfo;
        RCodeParser.m_unsuppMethods = unsuppMethods;
        try {
            if (RCodeParser.m_genInfo != null) {
                RCodeParser.m_genInfo.logIt(5, "PGLOG_Parsing", "...", 1);
            }
            RCodeParser.m_buffer = null;
            RCodeParser.m_bufPos = 0;
            RCodeParser.m_ttParams = null;
            RCodeParser.m_numTempTables = 0;
            boolean b2 = false;
            final FileInputStream in = new FileInputStream(absFilename);
            in.skip(14L);
            final int n = in.read() + in.read() * 256;
            if (n < 908) {
                handleError(8099442454849134352L, null);
            }
            if (n < 1003) {
                b2 = true;
            }
            in.skip(52L);
            final InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF8");
            final char[] cbuf = new char[4];
            inputStreamReader.read(cbuf);
            int asciiHexToInt = asciiHexToInt(cbuf);
            final char[] cbuf2 = new char[4];
            inputStreamReader.read(cbuf2);
            RCodeParser.m_numElements = asciiHexToInt(cbuf2);
            if (RCodeParser.m_numElements == 0) {
                handleError(8099442454849135752L, null);
            }
            --RCodeParser.m_numElements;
            asciiHexToInt -= 8;
            final char[] value = new char[50];
            int n2 = 0;
            while (true) {
                final char[] array2 = value;
                final int n3 = n2;
                final char c = (char)inputStreamReader.read();
                array2[n3] = c;
                if (c == '\0') {
                    break;
                }
                ++n2;
            }
            int count = n2;
            s2 = new String(value, 0, count);
            ++count;
            final int n4 = asciiHexToInt - count;
            String charsetName = Codepage.getJavaCodepageName(s2);
            if (charsetName != null) {
                RCodeParser.m_headerVersion = 0;
            }
            else {
                if (s2.length() > 4) {
                    charsetName = Codepage.getJavaCodepageName(s2.substring(4));
                }
                if (charsetName == null) {
                    handleError(8099442454849135198L, new Object[] { s2, PGGenInfo.getResources().getTranString("PGERROR_NoCodepage") });
                }
                final char[] value2 = new char[4];
                for (int i = 0; i < 4; ++i) {
                    value2[i] = value[i];
                }
                RCodeParser.m_headerVersion = asciiHexToInt(value2);
                if (RCodeParser.m_headerVersion > 3) {
                    handleError(8099442454849140513L, new Object[] { new String(value2), "0003", absFilename });
                }
            }
            inputStreamReader.close();
            final FileInputStream in2 = new FileInputStream(absFilename);
            in2.skip(76 + count);
            final InputStreamReader inputStreamReader2 = new InputStreamReader(in2, charsetName);
            RCodeParser.m_buffer = new char[n4];
            try {
                for (int read = 0, offset = 0; offset < n4 && read != -1; read = inputStreamReader2.read(RCodeParser.m_buffer, offset, 1), ++offset) {}
            }
            catch (MalformedInputException ex4) {}
            inputStreamReader2.close();
            holder.setValue(getMainSignature(array));
            if (RCodeParser.m_numElements > 0) {
                if (!tempTableSection()) {
                    if (b) {
                        holder2.setValue(getInternalSignatures());
                    }
                    else {
                        skipInternalSignatures();
                        holder2.setValue(new PGMethod[0]);
                    }
                }
                else {
                    RCodeParser.m_numTempTables = RCodeParser.m_numElements;
                    holder2.setValue(new PGMethod[0]);
                }
            }
            else {
                holder2.setValue(new PGMethod[0]);
            }
            for (int j = 0; j < RCodeParser.m_numTempTables; ++j) {
                fillTempTableSchema(b2);
            }
            if (RCodeParser.m_genInfo != null) {
                RCodeParser.m_genInfo = null;
            }
        }
        catch (FileNotFoundException ex) {
            handleError(8099442454849134353L, new Object[] { ex.getMessage() });
        }
        catch (UnsupportedEncodingException ex5) {
            handleError(8099442454849135198L, new Object[] { s2, PGGenInfo.getResources().getTranString("PGERROR_UnsupportedCodepage") });
        }
        catch (IOException ex2) {
            handleError(8099442454849134357L, new Object[] { ex2.getClass().getName() + " " + ex2.getMessage() });
        }
        catch (ProException ex3) {
            if (ex3 instanceof Open4GLException) {
                throw (Open4GLException)ex3;
            }
            handleError(8099442454849135198L, new Object[] { s2, ex3.getMessage() });
        }
    }
    
    private static PGParam[] getMainSignature(final String[] array) throws Open4GLException {
        final Holder holder = new Holder(null);
        final String methodSignature = getMethodSignature(holder, new PGParam[1], null);
        String substring;
        if (methodSignature == null) {
            substring = null;
        }
        else {
            substring = methodSignature.substring(methodSignature.lastIndexOf(".") + 1);
        }
        array[0] = substring;
        return (PGParam[])holder.getValue();
    }
    
    private static PGMethod[] getInternalSignatures() throws Open4GLException {
        int n = 0;
        final Holder holder = new Holder(null);
        final PGParam[] array = { null };
        final Vector<PGMethod> vector = new Vector<PGMethod>(RCodeParser.m_numElements);
        final boolean[] array2 = { false };
        int n2;
        for (n2 = 0; n2 < RCodeParser.m_numElements && !tempTableSection(); ++n2) {
            final String methodSignature = getMethodSignature(holder, array, array2);
            if (methodSignature != null) {
                final PGMethod obj = new PGMethod();
                obj.setInternalProc(methodSignature);
                if (array[0] == null) {
                    obj.setProcType(1);
                }
                else {
                    obj.setProcType(2);
                }
                if (array2[0]) {
                    obj.setExcluded(true);
                    obj.setBadParams(true);
                    array2[0] = false;
                }
                else {
                    final PGMethodDetail methodDetail = new PGMethodDetail();
                    obj.setMethodDetail(methodDetail);
                    methodDetail.setMethodName(PGAppObj.ProNameToProxyName(methodSignature));
                    methodDetail.setParameters((PGParam[])holder.getValue());
                    methodDetail.setReturnValue(array[0]);
                    methodDetail.updateTTMappings();
                }
                vector.addElement(obj);
                ++n;
            }
        }
        RCodeParser.m_numTempTables = RCodeParser.m_numElements - n2;
        final PGMethod[] array3 = new PGMethod[n];
        for (int i = 0; i < n; ++i) {
            array3[i] = vector.elementAt(n - i - 1);
        }
        return array3;
    }
    
    private static void skipInternalSignatures() throws Open4GLException {
        int n;
        for (n = 0; n < RCodeParser.m_numElements && !tempTableSection(); ++n) {
            skipSignature();
        }
        RCodeParser.m_numTempTables = RCodeParser.m_numElements - n;
    }
    
    private static String getMethodSignature(final Holder holder, final PGParam[] array, final boolean[] array2) throws Open4GLException {
        int typeToInteger = 0;
        String paramExtentInfo = "";
        holder.setValue(new PGParam[0]);
        array[0] = null;
        String string;
        int n;
        for (string = getString(null), n = 0; n < 4 && !string.equalsIgnoreCase(RCodeParser.m_kind[n]); ++n) {}
        if (n >= 4) {
            skipSignature();
            return null;
        }
        final String string2 = getString(",");
        if (string.equalsIgnoreCase("MAIN") && string2 != null && string2.substring(string2.lastIndexOf(".") + 1).equalsIgnoreCase("cls")) {
            handleError(8099442454849139583L, null);
        }
        final String string3 = getString(null);
        if (string3 != null) {
            typeToInteger = typeToInteger(string3);
            final PGParam pgParam = new PGParam();
            pgParam.setParamName("result");
            pgParam.setParamType(typeToInteger);
            array[0] = pgParam;
            if (RCodeParser.m_buffer[RCodeParser.m_bufPos - 1] == ' ' && RCodeParser.m_buffer[RCodeParser.m_bufPos] == 'E') {
                paramExtentInfo = getParamExtentInfo(pgParam);
            }
        }
        if (RCodeParser.m_genInfo != null) {
            RCodeParser.m_genInfo.logIt(2, null, string + " " + string2 + ((string3 == null) ? " " : (" Returns: " + string3 + paramExtentInfo)), 2);
        }
        if (weKnewTypesWereBad(string2)) {
            RCodeParser.m_suppressLogError = true;
        }
        if ((string3 != null && typeToInteger == 0) || typeToInteger == 11 || typeToInteger == 39) {
            skipSignature();
            final Object[] array3 = { string3 };
            try {
                if (typeToInteger == 0) {
                    handleError(8099442454849133663L, array3);
                }
                else {
                    handleError(8099442454849135813L, array3);
                }
            }
            catch (Open4GLException ex) {
                if (array2 != null) {
                    if (!RCodeParser.m_suppressLogError && RCodeParser.m_genInfo != null) {
                        RCodeParser.m_genInfo.logIt(2, "PGLOG_ProcExcluded", string2, 4);
                    }
                    array2[0] = true;
                    RCodeParser.m_suppressLogError = false;
                    return string2;
                }
                if (!RCodeParser.m_suppressLogError && RCodeParser.m_genInfo != null) {
                    RCodeParser.m_genInfo.logIt(2, "PGLOG_MustRemoveProc", string2, 3);
                }
                RCodeParser.m_suppressLogError = false;
                throw ex;
            }
        }
        if (anyParams()) {
            final PGParam[] array4 = { null };
            final Vector vector = new Vector<PGParam>(20, 10);
            try {
                while (getParameter(array4)) {
                    vector.addElement(array4[0]);
                }
                vector.addElement(array4[0]);
                RCodeParser.m_suppressLogError = false;
            }
            catch (Open4GLException ex2) {
                if (array2 != null) {
                    if (!RCodeParser.m_suppressLogError && RCodeParser.m_genInfo != null) {
                        RCodeParser.m_genInfo.logIt(2, "PGLOG_ProcExcluded", string2, 4);
                    }
                    array2[0] = true;
                    RCodeParser.m_suppressLogError = false;
                    return string2;
                }
                if (!RCodeParser.m_suppressLogError && RCodeParser.m_genInfo != null) {
                    RCodeParser.m_genInfo.logIt(2, "PGLOG_MustRemoveProc", string2, 3);
                }
                RCodeParser.m_suppressLogError = false;
                throw ex2;
            }
            final int size = vector.size();
            final PGParam[] value = new PGParam[size];
            for (int i = 0; i < size; ++i) {
                value[i] = new PGParam();
                (value[i] = vector.elementAt(i)).setParamOrdinal(i + 1);
            }
            holder.setValue(value);
        }
        RCodeParser.m_suppressLogError = false;
        return string2;
    }
    
    private static boolean getParameter(final PGParam[] array) throws Open4GLException {
        final boolean b = RCodeParser.m_genInfo != null && RCodeParser.m_genInfo.genWebServicesProxy();
        final String string = getString(null);
        if (string.endsWith("*")) {
            handleError(8099442454849136058L, null);
        }
        int n;
        for (n = 0; n < 3 && !string.equalsIgnoreCase(RCodeParser.m_strMode[n]); ++n) {}
        if (n == 3) {
            skipSignature();
            handleError(8099442454849133663L, new Object[] { string });
        }
        final int paramMode = RCodeParser.m_intMode[n];
        final String string2 = getString(null);
        if (string2.endsWith("*")) {
            handleError(8099442454849136058L, null);
        }
        final boolean char1 = findChar(',');
        final String string3 = getString(null);
        if (string3.endsWith("*")) {
            handleError(8099442454849136058L, null);
        }
        final int typeToInteger = typeToInteger(string3);
        if (typeToInteger == 0) {
            if (char1) {
                skipSignature();
            }
            handleError(8099442454849133663L, new Object[] { string3 });
        }
        if ((RCodeParser.m_buffer[RCodeParser.m_bufPos - 1] != ' ' || RCodeParser.m_buffer[RCodeParser.m_bufPos] != 'E') && RCodeParser.m_genInfo != null) {
            RCodeParser.m_genInfo.logIt(3, null, string + " " + string2 + " " + string3, 2);
        }
        Object obj;
        if (string3.equalsIgnoreCase("TABLE")) {
            obj = new PGDataTableParam();
            if (RCodeParser.m_ttParams == null) {
                RCodeParser.m_ttParams = new Vector();
            }
            RCodeParser.m_ttParams.addElement(obj);
            if (RCodeParser.m_buffer[RCodeParser.m_bufPos - 1] == ' ' && getString(null).endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
        }
        else if (string3.equalsIgnoreCase("DATASET")) {
            obj = new PGDataSetParam();
            buildDataSetParam((PGDataSetParam)obj);
            if (!RCodeParser.m_suppressLogError && b && dsetHasForKeyHiddenLinks((PGDataSetParam)obj)) {
                RCodeParser.m_genInfo.logIt(2, "PGLOG_ForeignKeyHiddenIgnored", ((PGParam)obj).getParamName(), 4);
            }
        }
        else {
            obj = new PGParam();
            if (RCodeParser.m_buffer[RCodeParser.m_bufPos - 1] == ' ' && RCodeParser.m_buffer[RCodeParser.m_bufPos] == 'E') {
                final String paramExtentInfo = getParamExtentInfo((PGParam)obj);
                if (RCodeParser.m_genInfo != null) {
                    RCodeParser.m_genInfo.logIt(3, null, string + " " + string2 + " " + string3 + paramExtentInfo, 2);
                }
            }
        }
        ((PGParam)obj).setParamMode(paramMode);
        ((PGParam)obj).setParamName(string2);
        ((PGParam)obj).setParamType(typeToInteger);
        if (!char1) {
            Object string4 = null;
            if (RCodeParser.m_buffer[RCodeParser.m_bufPos - 1] == ' ') {
                string4 = getString(null);
            }
            if (string4 != null) {
                skipSignature();
                handleError(8099442454849133663L, new Object[] { string4 });
            }
        }
        array[0] = (PGParam)obj;
        return char1;
    }
    
    private static String getParamExtentInfo(final PGParam pgParam) throws Open4GLException {
        String string = null;
        if (getString(null).endsWith("*")) {
            handleError(8099442454849136058L, null);
        }
        pgParam.setIsExtentField(true);
        if (RCodeParser.m_buffer[RCodeParser.m_bufPos - 1] == ' ') {
            string = getString(null);
            pgParam.setExtent(Integer.parseInt(string));
        }
        String string2;
        if (string != null) {
            string2 = " EXTENT " + string;
        }
        else {
            string2 = " EXTENT";
        }
        return string2;
    }
    
    private static void buildDataSetParam(final PGDataSetParam pgDataSetParam) throws Open4GLException {
        final Vector vector = new Vector<String>();
        final Vector vector2 = new Vector<PGDataLink>();
        while (true) {
            final String string = getString(null);
            if (string == null) {
                handleError(8099442454849133664L, null);
            }
            if (string.equalsIgnoreCase("DATALINKS")) {
                break;
            }
            if (string.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            vector.addElement(string);
        }
        final int size = vector.size();
        int n = 0;
        if (RCodeParser.m_headerVersion > 0) {
            n = 1;
            String xmlNamespace = vector.elementAt(0);
            if (xmlNamespace.equals("0")) {
                xmlNamespace = new String("");
            }
            else if (xmlNamespace.equals("1")) {
                xmlNamespace = null;
            }
            if (xmlNamespace != null) {
                pgDataSetParam.setXmlNamespace(xmlNamespace);
            }
        }
        if (RCodeParser.m_headerVersion >= 3) {
            n = 2;
            final String xmlNodeName = vector.elementAt(1);
            if (!xmlNodeName.equals("1")) {
                pgDataSetParam.setXmlNodeName(xmlNodeName);
            }
        }
        final PGDataTableParam[] dataSetTables = new PGDataTableParam[size - n];
        for (int i = n; i < size; ++i) {
            (dataSetTables[i - n] = new PGDataTableParam()).setParamName(vector.elementAt(i));
            dataSetTables[i - n].setParamType(15);
            if (RCodeParser.m_ttParams == null) {
                RCodeParser.m_ttParams = new Vector();
            }
            RCodeParser.m_ttParams.addElement(dataSetTables[i - n]);
        }
        pgDataSetParam.setDataSetTables(dataSetTables);
        final String string2 = getString(null);
        if (string2 == null) {
            handleError(8099442454849133664L, null);
        }
        else if (string2.endsWith("*")) {
            handleError(8099442454849136058L, null);
        }
        int int1 = 0;
        try {
            int1 = Integer.parseInt(string2);
        }
        catch (NumberFormatException ex) {
            handleError(8099442454849133664L, null);
        }
        for (int j = 0; j < int1; ++j) {
            final String string3 = getString(null);
            if (string3 == null) {
                handleError(8099442454849133664L, null);
            }
            else if (string3.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            final PGDataLink obj = new PGDataLink();
            obj.setLinkName(string3);
            final String string4 = getString(null);
            if (string4 == null) {
                handleError(8099442454849133664L, null);
            }
            else if (string4.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            int int2 = 0;
            try {
                int2 = Integer.parseInt(string4);
            }
            catch (NumberFormatException ex2) {
                handleError(8099442454849133664L, null);
            }
            obj.setFlag(int2);
            final String string5 = getString(null);
            if (string5 == null) {
                handleError(8099442454849133664L, null);
            }
            else if (string5.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            obj.setParentBuffer(string5, dataSetTables);
            final String string6 = getString(null);
            if (string6 == null) {
                handleError(8099442454849133664L, null);
            }
            else if (string6.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            obj.setChildBuffer(string6, dataSetTables);
            final String string7 = getString(null);
            if (string7.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            int int3 = 0;
            try {
                int3 = Integer.parseInt(string7);
            }
            catch (NumberFormatException ex3) {
                handleError(8099442454849133664L, null);
            }
            for (int k = 0; k < int3; ++k) {
                final String string8 = getString(null);
                if (string8 == null) {
                    handleError(8099442454849133664L, null);
                }
                else if (string8.endsWith("*")) {
                    handleError(8099442454849136058L, null);
                }
                obj.addToParentFieldList(string8);
                final String string9 = getString(null);
                if (string9 == null) {
                    handleError(8099442454849133664L, null);
                }
                else if (string9.endsWith("*")) {
                    handleError(8099442454849136058L, null);
                }
                obj.addToChildFieldList(string9);
            }
            vector2.addElement(obj);
        }
        final int size2 = vector2.size();
        final PGDataLink[] dataLinks = new PGDataLink[size2];
        for (int l = 0; l < size2; ++l) {
            dataLinks[l] = vector2.elementAt(l);
        }
        pgDataSetParam.setDataLinks(dataLinks);
    }
    
    private static void fillTempTableSchema(final boolean b) throws Open4GLException {
        final Vector vector = new Vector<PGMetaData>();
        final PGMetaData[] array = { null };
        PGDataTableParam dataTableMetaData = null;
        int n = 0;
        boolean b2 = false;
        String string = null;
        getString(null);
        final String string2 = getString(null);
        for (int index = 0; RCodeParser.m_ttParams != null && index < RCodeParser.m_ttParams.size(); ++index) {
            dataTableMetaData = (PGDataTableParam)RCodeParser.m_ttParams.elementAt(index);
            if (dataTableMetaData.getParamOrigName().equalsIgnoreCase(string2)) {
                n = index;
                b2 = true;
                break;
            }
        }
        if (!b2) {
            skipSignature();
        }
        else {
            if (RCodeParser.m_genInfo != null) {
                RCodeParser.m_genInfo.logIt(2, null, "TABLE: " + string2, 2);
            }
            if (!b) {
                if (RCodeParser.m_headerVersion > 1) {
                    String string3 = getString(null);
                    if (string3.equals("0")) {
                        string3 = new String("");
                    }
                    else if (string3.equals("1")) {
                        string3 = null;
                    }
                    if (string3 != null) {
                        dataTableMetaData.setXmlNamespace(string3);
                    }
                }
                if (RCodeParser.m_headerVersion >= 3) {
                    final String string4 = getString(null);
                    if (!string4.equals("1")) {
                        dataTableMetaData.setXmlNodeName(string4);
                    }
                }
                string = getString(" ");
                if (string == null) {
                    handleError(8099442454849133664L, null);
                }
                else if (string.endsWith("*")) {
                    handleError(8099442454849136058L, null);
                }
                else if (string.equals("0,0")) {
                    string = null;
                }
                if (getString(null).equalsIgnoreCase("1")) {
                    dataTableMetaData.setReferenceOnly(true);
                }
                else {
                    dataTableMetaData.setReferenceOnly(false);
                }
                final String string5 = getString(null);
                if (string5 == null) {
                    handleError(8099442454849133664L, null);
                }
                else if (string5.endsWith("*")) {
                    handleError(8099442454849136058L, null);
                }
                if (string5.equalsIgnoreCase("YES")) {
                    dataTableMetaData.setBeforeTable(true);
                }
                else {
                    dataTableMetaData.setBeforeTable(false);
                }
            }
            while (getField(array, b)) {
                vector.addElement(array[0]);
            }
            vector.addElement(array[0]);
            final int size = vector.size();
            final PGMetaData[] metaData = new PGMetaData[size];
            for (int i = 0; i < size; ++i) {
                metaData[i] = new PGMetaData();
                metaData[i] = vector.elementAt(i);
            }
            dataTableMetaData.setMetaData(metaData);
            dataTableMetaData.setIndexCols(string);
            for (int j = n; j < RCodeParser.m_ttParams.size(); ++j) {
                final PGDataTableParam pgDataTableParam = RCodeParser.m_ttParams.elementAt(j);
                if (pgDataTableParam.getParamOrigName().equalsIgnoreCase(string2)) {
                    pgDataTableParam.setDataTableMetaData(dataTableMetaData);
                }
            }
        }
    }
    
    private static boolean getField(final PGMetaData[] array, final boolean b) throws Open4GLException {
        final PGMetaData pgMetaData = new PGMetaData();
        final String string = getString(null);
        if (string.endsWith("*")) {
            handleError(8099442454849136058L, null);
        }
        pgMetaData.setFieldName(string);
        final boolean char1 = findChar(',');
        String str = getString(null);
        if (str.endsWith("*")) {
            handleError(8099442454849136058L, null);
        }
        pgMetaData.setTypeName(str);
        final int index = str.indexOf(91);
        if (index != -1) {
            final String substring = str.substring(index + 1, str.length() - 1);
            str = str.substring(0, index);
            final int int1 = Integer.parseInt(substring);
            pgMetaData.setExtent(int1);
            if (RCodeParser.m_genInfo != null) {
                RCodeParser.m_genInfo.logIt(3, null, string + " " + str + "[" + int1 + "]", 2);
            }
        }
        else if (RCodeParser.m_genInfo != null) {
            RCodeParser.m_genInfo.logIt(3, null, string + " " + str, 2);
        }
        final int typeToInteger = typeToInteger(str);
        if (typeToInteger == 0) {
            handleError(8099442454849133663L, new Object[] { str });
        }
        pgMetaData.setType(typeToInteger);
        if (!b) {
            final String string2 = getString(null);
            if (string2 == null) {
                handleError(8099442454849133664L, null);
            }
            else if (string2.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            int int2 = 0;
            try {
                int2 = Integer.parseInt(string2);
            }
            catch (NumberFormatException ex) {
                handleError(8099442454849133664L, null);
            }
            pgMetaData.setUserOrder(int2);
            final String string3 = getString(null);
            if (string3 == null) {
                handleError(8099442454849133664L, null);
            }
            else if (string3.endsWith("*")) {
                handleError(8099442454849136058L, null);
            }
            int int3 = 0;
            try {
                int3 = Integer.parseInt(string3);
            }
            catch (NumberFormatException ex2) {
                handleError(8099442454849133664L, null);
            }
            pgMetaData.setXMLMapping(int3);
            if (RCodeParser.m_headerVersion >= 3) {
                final String string4 = getString(null);
                if (string4 == null) {
                    handleError(8099442454849133664L, null);
                }
                else if (string4.endsWith("*")) {
                    handleError(8099442454849136058L, null);
                }
                if (!string4.equals("1")) {
                    pgMetaData.setXmlNodeName(string4);
                }
            }
        }
        array[0] = pgMetaData;
        return char1;
    }
    
    private static int asciiHexToInt(final char[] array) throws Open4GLException {
        return hexCharToInt(array[0]) * 4096 + hexCharToInt(array[1]) * 256 + hexCharToInt(array[2]) * 16 + hexCharToInt(array[3]);
    }
    
    private static int hexCharToInt(final char c) throws Open4GLException {
        switch (c) {
            case 'A': {
                return 10;
            }
            case 'B': {
                return 11;
            }
            case 'C': {
                return 12;
            }
            case 'D': {
                return 13;
            }
            case 'E': {
                return 14;
            }
            case 'F': {
                return 15;
            }
            default: {
                int int1 = 0;
                try {
                    int1 = Integer.parseInt(String.valueOf(c));
                }
                catch (NumberFormatException ex) {
                    handleError(8099442454849133664L, null);
                }
                return int1;
            }
        }
    }
    
    private static String getString(final String s) {
        final int bufPos = RCodeParser.m_bufPos;
        if (s == null) {
            while (RCodeParser.m_buffer[RCodeParser.m_bufPos] != ' ' && RCodeParser.m_buffer[RCodeParser.m_bufPos] != ',' && RCodeParser.m_buffer[RCodeParser.m_bufPos] != '\0') {
                ++RCodeParser.m_bufPos;
            }
        }
        else {
            while (RCodeParser.m_buffer[RCodeParser.m_bufPos] != s.charAt(0)) {
                ++RCodeParser.m_bufPos;
            }
        }
        final int count = RCodeParser.m_bufPos - bufPos;
        ++RCodeParser.m_bufPos;
        if (count == 0) {
            return null;
        }
        return String.valueOf(RCodeParser.m_buffer, bufPos, count);
    }
    
    private static boolean findChar(final char c) {
        int bufPos;
        for (bufPos = RCodeParser.m_bufPos; RCodeParser.m_buffer[bufPos] != '\0' && RCodeParser.m_buffer[bufPos] != c; ++bufPos) {}
        return RCodeParser.m_buffer[bufPos] == c;
    }
    
    private static boolean anyParams() {
        if (RCodeParser.m_buffer[RCodeParser.m_bufPos] == '\0') {
            ++RCodeParser.m_bufPos;
            return false;
        }
        return true;
    }
    
    private static void skipSignature() {
        while (RCodeParser.m_buffer[RCodeParser.m_bufPos] != '\0') {
            ++RCodeParser.m_bufPos;
        }
        ++RCodeParser.m_bufPos;
    }
    
    private static int typeToInteger(final String s) {
        for (int i = 0; i < 23; ++i) {
            if (s.equalsIgnoreCase(RCodeParser.m_strDataType[i])) {
                return RCodeParser.m_intDataType[i];
            }
        }
        return 0;
    }
    
    private static boolean tempTableSection() {
        final int bufPos = RCodeParser.m_bufPos;
        final String string = getString(null);
        RCodeParser.m_bufPos = bufPos;
        return string.equalsIgnoreCase("TEMP-TABLE");
    }
    
    private static boolean weKnewTypesWereBad(final String anotherString) {
        if (RCodeParser.m_unsuppMethods != null) {
            for (int i = 0; i < RCodeParser.m_unsuppMethods.length; ++i) {
                final PGMethod pgMethod = RCodeParser.m_unsuppMethods[i];
                if (pgMethod.hasBadParams() && pgMethod.getInternalProc().equalsIgnoreCase(anotherString)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static void handleError(final long n, final Object[] array) throws Open4GLException {
        int n2 = 0;
        final Open4GLException ex = new Open4GLException(n, array);
        if (RCodeParser.m_genInfo != null && !RCodeParser.m_suppressLogError) {
            for (int n3 = 0; n3 < RCodeParser.m_warnings.length && n2 == 0; ++n3) {
                if (n == RCodeParser.m_warnings[n3]) {
                    n2 = 1;
                }
            }
            if (n2 != 0) {
                RCodeParser.m_genInfo.logIt(2, "PGLOG_ParsingWarning", null, 3);
            }
            else {
                RCodeParser.m_genInfo.logIt(2, "PGLOG_ParsingError", null, 3);
            }
            RCodeParser.m_genInfo.logIt(2, null, ex.getMessage(), 3);
        }
        throw ex;
    }
    
    private static boolean dsetHasForKeyHiddenLinks(final PGDataSetParam pgDataSetParam) {
        final PGDataLink[] dataLinks = pgDataSetParam.getDataLinks();
        for (int i = 0; i < dataLinks.length; ++i) {
            if ((dataLinks[i].getFlag() & 0x10) == 0x10) {
                return true;
            }
        }
        return false;
    }
    
    static {
        m_kind = new String[] { "MAIN", "FUNCTION", "PROCEDURE", "EXTERN" };
        m_strMode = new String[] { "INPUT", "INPUT-OUTPUT", "OUTPUT" };
        m_intMode = new int[] { 1, 3, 2 };
        m_strDataType = new String[] { "CHARACTER", "DATE", "LOGICAL", "INTEGER", "DECIMAL", "RECID", "RAW", "HANDLE", "WIDGET-HANDLE", "ROWID", "COM-HANDLE", "COMPONENT-HANDLE", "TABLE", "TABLE-HANDLE", "MEMPTR", "DATASET", "DATASET-HANDLE", "BLOB", "CLOB", "DATETIME", "DATETIME-TZ", "LONGCHAR", "INT64" };
        m_intDataType = new int[] { 1, 2, 3, 4, 5, 7, 8, 10, 10, 13, 14, 14, 15, 17, 11, 36, 37, 18, 19, 34, 40, 39, 41 };
        RCodeParser.m_suppressLogError = false;
        RCodeParser.m_warnings = new long[] { 8099442454849133663L, 8099442454849135813L };
        RCodeParser.m_headerVersion = 0;
    }
}

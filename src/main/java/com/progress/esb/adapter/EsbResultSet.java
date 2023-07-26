// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.adapter;

import java.sql.SQLException;
import com.progress.open4gl.Handle;
import com.progress.open4gl.COMHandle;
import com.progress.open4gl.Rowid;
import com.progress.open4gl.Memptr;
import com.progress.open4gl.proxygen.PGMetaData;
import java.util.Vector;
import org.apache.soap.util.xml.DOMUtils;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import com.progress.open4gl.ProSQLException;
import com.progress.wsa.xmr.XmrResultSet;
import com.progress.open4gl.ProResultSetMetaDataImpl;
import org.w3c.dom.Element;
import com.progress.open4gl.ProResultSetMetaData;
import com.progress.open4gl.ProResultSet;
import com.sonicsw.xqimpl.script.ScriptEngineException;
import com.progress.open4gl.Parameter;
import com.progress.open4gl.proxygen.PGParam;

public class EsbResultSet
{
    public static String serializeParameter(final Object o, final PGParam pgParam) throws ScriptEngineException {
        final int paramType = pgParam.getParamType();
        final String paramName = pgParam.getParamName();
        final String xmlNamespace = pgParam.getXmlNamespace();
        boolean b = false;
        if (paramType != 15 && paramType != 17) {
            throw new ScriptEngineException("Incorrect parameter type - Expected TEMPTABLE, got " + Parameter.proToName(paramType), 0);
        }
        if (pgParam.isExtentField()) {
            throw new ScriptEngineException("Incorrect parameter type - Expected simple value, got array", 0);
        }
        if (paramType == 17) {
            b = true;
        }
        String string;
        try {
            final StringBuffer sb = new StringBuffer();
            final ProResultSet set = (ProResultSet)o;
            final ProResultSetMetaData proResultSetMetaData = (ProResultSetMetaData)set.getMetaData();
            final int columnCount = proResultSetMetaData.getColumnCount();
            if (b) {
                sb.append("<DataSet>");
                serializeSchema(proResultSetMetaData, sb);
                sb.append("<Data>");
            }
            else {
                sb.append("<");
                sb.append(paramName);
                sb.append(" xmlns:");
                sb.append("xsi");
                sb.append("=\"");
                sb.append("http://www.w3.org/2001/XMLSchema-instance");
                sb.append("\"");
                if (null != xmlNamespace) {
                    sb.append(" xmlns=\"");
                    sb.append(xmlNamespace);
                    sb.append("\"");
                }
                sb.append(">");
            }
            while (set.next()) {
                if (b) {
                    sb.append("<Item>");
                }
                else {
                    sb.append("<");
                    sb.append(paramName);
                    sb.append("Row>");
                }
                for (int i = 1; i <= columnCount; ++i) {
                    final String columnName = proResultSetMetaData.getColumnName(i);
                    final int columnProType = proResultSetMetaData.getColumnProType(i);
                    Object o2 = null;
                    switch (columnProType) {
                        case 19: {
                            o2 = set.getString(i);
                            break;
                        }
                        case 18: {
                            o2 = set.getBytes(i);
                            break;
                        }
                        case 2:
                        case 34:
                        case 40: {
                            o2 = set.getGregorianCalendar(i);
                            break;
                        }
                        default: {
                            o2 = set.getObject(i);
                            break;
                        }
                    }
                    sb.append("<");
                    sb.append(columnName);
                    if (null != o2) {
                        sb.append(">");
                        switch (columnProType) {
                            case 1:
                            case 19: {
                                final String serializeParameter = EsbChar.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter) {
                                    sb.append(serializeParameter);
                                    break;
                                }
                                break;
                            }
                            case 2: {
                                final String serializeParameter2 = EsbDate.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter2) {
                                    sb.append(serializeParameter2);
                                    break;
                                }
                                break;
                            }
                            case 3: {
                                final String serializeParameter3 = EsbLogical.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter3) {
                                    sb.append(serializeParameter3);
                                    break;
                                }
                                break;
                            }
                            case 4: {
                                final String serializeParameter4 = EsbInteger.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter4) {
                                    sb.append(serializeParameter4);
                                    break;
                                }
                                break;
                            }
                            case 5: {
                                final String serializeParameter5 = EsbDecimal.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter5) {
                                    sb.append(serializeParameter5);
                                    break;
                                }
                                break;
                            }
                            case 7:
                            case 10:
                            case 14:
                            case 41: {
                                final String serializeParameter6 = EsbInt64.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter6) {
                                    sb.append(serializeParameter6);
                                    break;
                                }
                                break;
                            }
                            case 8:
                            case 13:
                            case 18: {
                                final String serializeParameter7 = EsbMemptr.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter7) {
                                    sb.append(serializeParameter7);
                                    break;
                                }
                                break;
                            }
                            case 34: {
                                final String serializeParameter8 = EsbDateTime.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter8) {
                                    sb.append(serializeParameter8);
                                    break;
                                }
                                break;
                            }
                            case 40: {
                                final String serializeParameter9 = EsbDateTimeTZ.serializeParameter(o2, columnName, columnProType);
                                if (null != serializeParameter9) {
                                    sb.append(serializeParameter9);
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    else {
                        sb.append(" ");
                        sb.append("xsi");
                        sb.append(":");
                        sb.append("nil");
                        sb.append("=\"true\">");
                    }
                    sb.append("</");
                    sb.append(columnName);
                    sb.append(">");
                }
                if (b) {
                    sb.append("</Item>");
                }
                else {
                    sb.append("</");
                    sb.append(paramName);
                    sb.append("Row>");
                }
            }
            if (b) {
                sb.append("</Data>");
                sb.append("</DataSet>");
            }
            else {
                sb.append("</");
                sb.append(paramName);
                sb.append(">");
            }
            string = sb.toString();
        }
        catch (Exception linkedException) {
            final ScriptEngineException ex = new ScriptEngineException("Resultset Serialization Error", 0);
            ex.setLinkedException((Throwable)linkedException);
            throw ex;
        }
        return string;
    }
    
    public static Object parseParameter(final Element element, final PGParam pgParam, final ProResultSetMetaDataImpl metaData) throws ScriptEngineException {
        final XmrResultSet set = new XmrResultSet();
        final int paramType = pgParam.getParamType();
        PGMetaData[] array = pgParam.getMetaData();
        if (null == array) {
            try {
                array = genMetaData(metaData);
            }
            catch (ProSQLException ex) {
                throw new ScriptEngineException("Unable to generate Temptable Metadata: " + ex.toString(), 0);
            }
        }
        if (paramType != 15 && paramType != 17) {
            throw new ScriptEngineException("Incorrect parameter type - Expected TEMPTABLE, got " + Parameter.proToName(paramType), 0);
        }
        set.setMetaData(metaData);
        if (null == element) {
            return set;
        }
        final int fieldCount = metaData.getFieldCount();
        final int columnCount = metaData.getColumnCount();
        final int countKids = DOMUtils.countKids(element, (short)1);
        Element element2 = DOMUtils.getFirstChildElement(element);
        for (int i = 0; i < countKids; ++i) {
            final int countKids2 = DOMUtils.countKids(element2, (short)1);
            try {
                if (columnCount != countKids2) {
                    throw new ScriptEngineException("TempTable schema-data mismatch", 0);
                }
            }
            catch (Exception linkedException) {
                final ScriptEngineException ex2 = new ScriptEngineException(linkedException.toString(), 0);
                ex2.setLinkedException((Throwable)linkedException);
                throw ex2;
            }
            Element element3 = DOMUtils.getFirstChildElement(element2);
            final Vector<Object> vector = new Vector<Object>();
            int n = 0;
            int n2 = 0;
            while (null != element3) {
                ++n;
                ++n2;
                if (n > columnCount) {
                    throw new ScriptEngineException("Invalid number of columns", 0);
                }
                if (n2 > fieldCount) {
                    throw new ScriptEngineException("Invalid number of fields", 0);
                }
                if (null != DOMUtils.getFirstChildElement(element3)) {
                    continue;
                }
                int fieldExtent;
                try {
                    final String columnName = metaData.getColumnName(n);
                    fieldExtent = metaData.getFieldExtent(n2);
                    if (!columnName.equals(element3.getLocalName())) {
                        throw new ScriptEngineException("Invalid column name", 0);
                    }
                }
                catch (Exception linkedException2) {
                    final ScriptEngineException ex3 = new ScriptEngineException(linkedException2.toString(), 0);
                    ex3.setLinkedException((Throwable)linkedException2);
                    throw ex3;
                }
                if (fieldExtent > 0) {
                    for (int j = 0; j < fieldExtent; ++j) {
                        vector.addElement(parseColumn(element3, array[n2 - 1]));
                        element3 = DOMUtils.getNextSiblingElement(element3);
                    }
                    n = n + fieldExtent - 1;
                }
                else {
                    vector.addElement(parseColumn(element3, array[n2 - 1]));
                    element3 = DOMUtils.getNextSiblingElement(element3);
                }
            }
            set.addRow(vector);
            element2 = DOMUtils.getNextSiblingElement(element2);
        }
        return set;
    }
    
    static Object parseColumn(final Element element, final PGMetaData pgMetaData) throws ScriptEngineException {
        Object o = "";
        final int type = pgMetaData.getType();
        final String firstTextChildValue = com.sonicsw.xqimpl.util.DOMUtils.getFirstTextChildValue(element);
        if (null != firstTextChildValue) {
            switch (type) {
                case 1:
                case 19: {
                    o = EsbChar.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 2: {
                    o = EsbDate.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 3: {
                    o = EsbLogical.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 4: {
                    o = EsbInteger.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 5: {
                    o = EsbDecimal.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 7:
                case 41: {
                    o = EsbInt64.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 8:
                case 18: {
                    o = ((Memptr)EsbMemptr.parseParameter(firstTextChildValue, type)).getBytes();
                    return o;
                }
                case 13: {
                    o = new Rowid(((Memptr)EsbMemptr.parseParameter(firstTextChildValue, type)).getBytes());
                    return o;
                }
                case 14: {
                    o = new COMHandle((long)EsbInt64.parseParameter(firstTextChildValue, type));
                    return o;
                }
                case 34: {
                    o = EsbDateTime.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 40: {
                    o = EsbDateTimeTZ.parseParameter(firstTextChildValue, type);
                    return o;
                }
                case 10: {
                    final Handle handle = new Handle((long)EsbInt64.parseParameter(firstTextChildValue, type));
                    break;
                }
            }
            o = "";
        }
        else {
            final String attributeNS = DOMUtils.getAttributeNS(element, "http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (null != attributeNS && (attributeNS.equals("true") || attributeNS.equals("1"))) {
                o = null;
            }
        }
        return o;
    }
    
    private static void serializeSchema(final ProResultSetMetaData proResultSetMetaData, final StringBuffer sb) throws ScriptEngineException {
        try {
            sb.append("<schema xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
            sb.append("<element name=\"Data\">");
            sb.append("<complexType>");
            sb.append("<sequence>");
            sb.append("<element name=\"Item\" minOccurs=\"0\" maxOccurs=\"unbounded\">");
            sb.append("<complexType>");
            sb.append("<sequence>");
            for (int fieldCount = proResultSetMetaData.getFieldCount(), i = 1; i <= fieldCount; ++i) {
                final String fieldName = proResultSetMetaData.getFieldName(i);
                final String fieldXmlType = getFieldXmlType(proResultSetMetaData.getFieldProType(i));
                final int fieldExtent = proResultSetMetaData.getFieldExtent(i);
                sb.append("<element name=\"" + fieldName + "\" type=\"" + "xsd" + ":" + fieldXmlType + "\"");
                if (fieldExtent > 1) {
                    sb.append(" minOccurs=\"" + fieldExtent + "\" maxOccurs=\"" + fieldExtent + "\"");
                }
                sb.append(" nillable=\"true\"/>");
            }
            sb.append("</sequence>");
            sb.append("</complexType>");
            sb.append("</element>");
            sb.append("</sequence>");
            sb.append("</complexType>");
            sb.append("</element>");
            sb.append("</schema>");
        }
        catch (SQLException linkedException) {
            final ScriptEngineException ex = new ScriptEngineException("Unable to serialize schema - " + linkedException.toString(), 0);
            ex.setLinkedException((Throwable)linkedException);
            throw ex;
        }
    }
    
    private static String getFieldXmlType(final int n) {
        String s = null;
        switch (n) {
            case 1:
            case 19: {
                s = "string";
                break;
            }
            case 2: {
                s = "date";
                break;
            }
            case 3: {
                s = "boolean";
                break;
            }
            case 4: {
                s = "int";
                break;
            }
            case 5: {
                s = "decimal";
                break;
            }
            case 7:
            case 10:
            case 14:
            case 41: {
                s = "long";
                break;
            }
            case 8:
            case 13:
            case 18: {
                s = "base64Binary";
                break;
            }
            case 34:
            case 40: {
                s = "dateTime";
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return s;
    }
    
    private static PGMetaData[] genMetaData(final ProResultSetMetaDataImpl proResultSetMetaDataImpl) throws ProSQLException {
        final int fieldCount = proResultSetMetaDataImpl.getFieldCount();
        final PGMetaData[] array = new PGMetaData[fieldCount];
        for (int i = 0; i < fieldCount; ++i) {
            final PGMetaData pgMetaData = new PGMetaData();
            pgMetaData.setType(proResultSetMetaDataImpl.getFieldProType(i + 1));
            pgMetaData.setExtent(proResultSetMetaDataImpl.getFieldExtent(i + 1));
            array[i] = pgMetaData;
        }
        return array;
    }
}

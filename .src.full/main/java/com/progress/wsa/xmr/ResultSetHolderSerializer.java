// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.io.IOException;
import com.progress.open4gl.ProResultSet;
import java.sql.SQLException;
import com.progress.wsa.open4gl.ParamHolderDetail;
import org.apache.soap.util.StringUtils;
import com.progress.open4gl.ProResultSetMetaData;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.apache.soap.util.xml.NSStack;
import java.io.Writer;
import org.apache.soap.util.xml.Serializer;

public class ResultSetHolderSerializer implements Serializer
{
    public void marshall(final String s, final Class clazz, final Object o, final Object o2, final Writer writer, final NSStack nsStack, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException, IOException {
        nsStack.pushScope();
        try {
            final XmrResultSetHolder xmrResultSetHolder = (XmrResultSetHolder)o;
            final ProResultSet resultSet = xmrResultSetHolder.getResultSet();
            final String procedureName = xmrResultSetHolder.getProcedureName();
            final String namespaceURI = xmrResultSetHolder.getNamespaceURI();
            final boolean static1 = xmrResultSetHolder.isStatic();
            final String prefixFromURI = nsStack.getPrefixFromURI(namespaceURI);
            String string;
            if (procedureName == null) {
                string = "";
            }
            else {
                string = procedureName + "_";
            }
            writer.write("<" + o2.toString());
            if (resultSet == null) {
                if (static1) {
                    nsStack.popScope();
                    throw new IllegalArgumentException("Null ResultSet passed to serializer for static temp-table " + o2.toString() + ".");
                }
                writer.write(" xsi:nil=\"true\"/>");
                nsStack.popScope();
                return;
            }
            else {
                final ProResultSetMetaData proResultSetMetaData = (ProResultSetMetaData)resultSet.getMetaData();
                if (static1) {
                    if (null != s && 0 == s.compareTo("http://schemas.xmlsoap.org/soap/encoding/")) {
                        writer.write(" xsi:type=\"" + prefixFromURI + ":ArrayOf" + string + o2.toString() + "Row\" " + "SOAP-ENC" + ":" + "arrayType" + "=\"" + prefixFromURI + ":" + string + o2.toString() + "Row[]\" " + "xmlns" + ":" + "SOAP-ENC" + "=\"" + "http://schemas.xmlsoap.org/soap/encoding/" + "\">" + StringUtils.lineSeparator);
                    }
                    else {
                        writer.write(">" + StringUtils.lineSeparator);
                    }
                }
                else {
                    if (null != s && 0 == s.compareTo("http://schemas.xmlsoap.org/soap/encoding/")) {
                        writer.write(" xsi:type=\"" + prefixFromURI + ":" + "TableHandleParam\">" + StringUtils.lineSeparator);
                    }
                    else {
                        writer.write(">" + StringUtils.lineSeparator);
                    }
                    writer.write("<DataSet xmlns=\"\">" + StringUtils.lineSeparator);
                    this.marshallSchema(proResultSetMetaData, writer);
                    writer.write("<Data>" + StringUtils.lineSeparator);
                }
                final int columnCount = proResultSetMetaData.getColumnCount();
                while (resultSet.next()) {
                    if (static1) {
                        if (null != s && 0 == s.compareTo("http://schemas.xmlsoap.org/soap/encoding/")) {
                            writer.write("<Item xsi:type=\"" + prefixFromURI + ":" + string + o2.toString() + "Row\">" + StringUtils.lineSeparator);
                        }
                        else {
                            writer.write("<" + o2.toString() + "Row>" + StringUtils.lineSeparator);
                        }
                    }
                    else {
                        writer.write("<Item>" + StringUtils.lineSeparator);
                    }
                    for (int i = 1; i <= columnCount; ++i) {
                        final String columnName = proResultSetMetaData.getColumnName(i);
                        final int columnProType = proResultSetMetaData.getColumnProType(i);
                        Object o3 = null;
                        switch (columnProType) {
                            case 2: {
                                o3 = resultSet.getGregorianCalendar(i);
                                break;
                            }
                            case 34:
                            case 40: {
                                o3 = new ParamHolderDetail(columnProType, resultSet.getGregorianCalendar(i)).getHolder();
                                break;
                            }
                            case 18: {
                                o3 = resultSet.getBytes(i);
                                break;
                            }
                            case 19: {
                                o3 = resultSet.getString(i);
                                break;
                            }
                            default: {
                                o3 = resultSet.getObject(i);
                                break;
                            }
                        }
                        final Class columnJavaType = this.getColumnJavaType(columnProType);
                        if (null == columnJavaType) {
                            throw new IllegalArgumentException("Invalid PRO column type for result set.");
                        }
                        String s2 = s;
                        if (!static1) {
                            s2 = null;
                        }
                        xmlJavaMappingRegistry.marshall(s2, columnJavaType, o3, (Object)columnName, writer, nsStack, soapContext);
                        writer.write(StringUtils.lineSeparator);
                    }
                    if (static1) {
                        if (null != s && 0 == s.compareTo("http://schemas.xmlsoap.org/soap/encoding/")) {
                            writer.write("</Item>" + StringUtils.lineSeparator);
                        }
                        else {
                            writer.write("</" + o2.toString() + "Row>" + StringUtils.lineSeparator);
                        }
                    }
                    else {
                        writer.write("</Item>" + StringUtils.lineSeparator);
                    }
                }
                if (!static1) {
                    writer.write("</Data>" + StringUtils.lineSeparator);
                    writer.write("</DataSet>" + StringUtils.lineSeparator);
                }
                writer.write("</" + o2.toString() + ">" + StringUtils.lineSeparator);
            }
        }
        catch (SQLException ex) {
            throw new IllegalArgumentException("Unable to marshall output ResultSet");
        }
        nsStack.popScope();
    }
    
    private String getFieldXmlType(final int n) {
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
    
    private Class getColumnJavaType(final int n) {
        Class clazz = null;
        switch (n) {
            case 1:
            case 19: {
                clazz = String.class;
                break;
            }
            case 2: {
                clazz = GregorianCalendar.class;
                break;
            }
            case 3: {
                clazz = Boolean.class;
                break;
            }
            case 4: {
                clazz = Integer.class;
                break;
            }
            case 5: {
                clazz = BigDecimal.class;
                break;
            }
            case 7:
            case 10:
            case 14:
            case 41: {
                clazz = Long.class;
                break;
            }
            case 8:
            case 13:
            case 18: {
                clazz = byte[].class;
                break;
            }
            case 34: {
                clazz = DateTimeHolder.class;
                break;
            }
            case 40: {
                clazz = DateTimeTZHolder.class;
                break;
            }
            default: {
                clazz = null;
                break;
            }
        }
        return clazz;
    }
    
    private void marshallSchema(final ProResultSetMetaData proResultSetMetaData, final Writer writer) throws IllegalArgumentException, IOException {
        try {
            writer.write("<schema xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" + StringUtils.lineSeparator);
            writer.write("<element name=\"Data\">" + StringUtils.lineSeparator);
            writer.write("<complexType>" + StringUtils.lineSeparator);
            writer.write("<sequence>" + StringUtils.lineSeparator);
            writer.write("<element name=\"Item\" minOccurs=\"0\" maxOccurs=\"unbounded\">" + StringUtils.lineSeparator);
            writer.write("<complexType>" + StringUtils.lineSeparator);
            writer.write("<sequence>" + StringUtils.lineSeparator);
            for (int fieldCount = proResultSetMetaData.getFieldCount(), i = 1; i <= fieldCount; ++i) {
                final String fieldName = proResultSetMetaData.getFieldName(i);
                final String fieldXmlType = this.getFieldXmlType(proResultSetMetaData.getFieldProType(i));
                final int fieldExtent = proResultSetMetaData.getFieldExtent(i);
                writer.write("<element name=\"" + fieldName + "\" type=\"" + "xsd" + ":" + fieldXmlType + "\"");
                if (fieldExtent > 1) {
                    writer.write(" minOccurs=\"" + fieldExtent + "\" maxOccurs=\"" + fieldExtent + "\"");
                }
                writer.write(" nillable=\"true\"/>" + StringUtils.lineSeparator);
            }
            writer.write("</sequence>" + StringUtils.lineSeparator);
            writer.write("</complexType>" + StringUtils.lineSeparator);
            writer.write("</element>" + StringUtils.lineSeparator);
            writer.write("</sequence>" + StringUtils.lineSeparator);
            writer.write("</complexType>" + StringUtils.lineSeparator);
            writer.write("</element>" + StringUtils.lineSeparator);
            writer.write("</schema>" + StringUtils.lineSeparator);
        }
        catch (SQLException ex) {
            throw new IllegalArgumentException("Unable to marshall output ResultSet");
        }
    }
}

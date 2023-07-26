// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.soap.util.xml.DOMUtils;
import org.w3c.dom.Element;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;
import org.apache.soap.util.Bean;
import org.apache.soap.rpc.SOAPContext;
import org.apache.soap.util.xml.XMLJavaMappingRegistry;
import org.w3c.dom.Node;
import org.apache.soap.util.xml.QName;
import org.apache.soap.util.xml.Deserializer;

public class ResultSetSerializer implements Deserializer
{
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext) throws IllegalArgumentException {
        return this.unmarshall(s, qName, node, xmlJavaMappingRegistry, soapContext, null);
    }
    
    public Bean unmarshall(final String s, final QName qName, final Node node, final XMLJavaMappingRegistry xmlJavaMappingRegistry, final SOAPContext soapContext, ResultSetMetaData dataElement) throws IllegalArgumentException {
        final Element element = (Element)node;
        final String attributeNS = DOMUtils.getAttributeNS(element, "http://www.w3.org/2001/XMLSchema-instance", "nil");
        if (null != attributeNS && (attributeNS.equals("true") || attributeNS.equals("1"))) {
            return new Bean(ResultSet.class, (Object)null);
        }
        final XmrResultSet set = new XmrResultSet();
        Element elementByID = element;
        if (s != null && s.equals("http://schemas.xmlsoap.org/soap/encoding/")) {
            final String attribute = element.getAttribute("href");
            if (attribute != null && !attribute.equals("") && attribute.charAt(0) == '#') {
                final String substring = attribute.substring(1);
                elementByID = DOMUtils.getElementByID(node.getOwnerDocument().getDocumentElement(), substring);
                if (elementByID == null) {
                    throw new IllegalArgumentException("No such ID '" + substring + "' found in ResultSet deserializer");
                }
            }
        }
        Element nextSiblingElement;
        if (null == dataElement) {
            final Element firstChildElement = DOMUtils.getFirstChildElement(DOMUtils.getFirstChildElement(elementByID));
            nextSiblingElement = DOMUtils.getNextSiblingElement(firstChildElement);
            try {
                dataElement = new SchemaParser().parseDataElement(firstChildElement);
            }
            catch (IllegalArgumentException ex) {
                throw ex;
            }
            if (dataElement == null) {
                throw new IllegalArgumentException("Unable to parse schema for dynamic temp-table");
            }
        }
        else {
            nextSiblingElement = elementByID;
        }
        set.setMetaData(dataElement);
        final int columnCount = dataElement.getColumnCount();
        final int countKids = DOMUtils.countKids(nextSiblingElement, (short)1);
        Element element2 = DOMUtils.getFirstChildElement(nextSiblingElement);
        for (int i = 0; i < countKids; ++i) {
            final String attributeNS2 = DOMUtils.getAttributeNS(element2, "http://www.w3.org/2001/XMLSchema-instance", "nil");
            if (null != attributeNS2 && (attributeNS2.equals("true") || attributeNS2.equals("1"))) {
                throw new IllegalArgumentException("Illegal nil row passed to ResultSet deserializer");
            }
            final String attribute2 = element2.getAttribute("href");
            Element elementByID2 = element2;
            if (attribute2 != null && !attribute2.equals("") && attribute2.charAt(0) == '#') {
                final String substring2 = attribute2.substring(1);
                elementByID2 = DOMUtils.getElementByID(node.getOwnerDocument().getDocumentElement(), substring2);
                if (elementByID2 == null) {
                    throw new IllegalArgumentException("No such ID '" + substring2 + "' found in ResultSet deserializer");
                }
            }
            Element element3 = DOMUtils.getFirstChildElement(elementByID2);
            final Vector<Object> vector = new Vector<Object>();
            int n = 0;
            while (element3 != null) {
                if (++n > columnCount) {
                    throw new IllegalArgumentException("Invalid number of columns in \"" + qName.getLocalPart() + "\" row.");
                }
                final String attribute3 = element3.getAttribute("href");
                Element elementByID3 = element3;
                if (attribute3 != null && !attribute3.equals("") && attribute3.charAt(0) == '#') {
                    final String substring3 = attribute3.substring(1);
                    elementByID3 = DOMUtils.getElementByID(node.getOwnerDocument().getDocumentElement(), substring3);
                    if (elementByID3 == null) {
                        throw new IllegalArgumentException("No such ID '" + substring3 + "' found in ResultSet deserializer");
                    }
                }
                Element element4 = DOMUtils.getFirstChildElement(elementByID3);
                if (element4 == null) {
                    try {
                        final String columnName = dataElement.getColumnName(n);
                        if (!columnName.equals(element3.getLocalName())) {
                            throw new IllegalArgumentException("Column \"" + columnName + "\" missing from \"" + qName.getLocalPart() + "\" row");
                        }
                    }
                    catch (SQLException ex2) {}
                    final String attributeNS3 = DOMUtils.getAttributeNS(elementByID3, "http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle");
                    vector.addElement(xmlJavaMappingRegistry.unmarshall((attributeNS3 != null) ? attributeNS3 : s, this.getColumnTypeQName(dataElement, n), (Node)elementByID3, soapContext).value);
                }
                else {
                    --n;
                    while (element4 != null) {
                        if (++n > columnCount) {
                            throw new IllegalArgumentException("Invalid number of columns in \"" + qName.getLocalPart() + "\" row.");
                        }
                        final String attributeNS4 = DOMUtils.getAttributeNS(element4, "http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle");
                        vector.addElement(xmlJavaMappingRegistry.unmarshall((attributeNS4 != null) ? attributeNS4 : s, this.getColumnTypeQName(dataElement, n), (Node)element4, soapContext).value);
                        element4 = DOMUtils.getNextSiblingElement(element4);
                    }
                }
                element3 = DOMUtils.getNextSiblingElement(element3);
            }
            if (n < columnCount) {
                ++n;
                try {
                    throw new IllegalArgumentException("Column \"" + dataElement.getColumnName(n) + "\" missing from \"" + qName.getLocalPart() + "\" row");
                }
                catch (SQLException ex3) {}
            }
            set.addRow(vector);
            element2 = DOMUtils.getNextSiblingElement(element2);
        }
        return new Bean(ResultSet.class, (Object)set);
    }
    
    private QName getColumnTypeQName(final ResultSetMetaData resultSetMetaData, final int n) {
        int columnProType = 0;
        try {
            columnProType = resultSetMetaData.getColumnProType(n);
        }
        catch (SQLException ex) {}
        String s = null;
        switch (columnProType) {
            case 1:
            case 19: {
                s = "stringPrgs";
                break;
            }
            case 2: {
                s = "datePrgs";
                break;
            }
            case 3: {
                s = "booleanPrgs";
                break;
            }
            case 4: {
                s = "intPrgs";
                break;
            }
            case 5: {
                s = "decimalPrgs";
                break;
            }
            case 7:
            case 10:
            case 14:
            case 41: {
                s = "recid";
                break;
            }
            case 8:
            case 13:
            case 18: {
                s = "raw";
                break;
            }
            case 34: {
                s = "dateTimeS";
                break;
            }
            case 40: {
                s = "dateTimeTZ";
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return new QName("http://www.w3.org/2001/XMLSchema", s);
    }
}

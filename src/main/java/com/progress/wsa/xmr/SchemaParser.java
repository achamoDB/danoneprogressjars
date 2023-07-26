// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.xmr;

import org.apache.soap.util.xml.DOMUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import com.progress.open4gl.dynamicapi.ResultSetMetaData;

public class SchemaParser
{
    protected ResultSetMetaData m_rsmd;
    
    public SchemaParser() {
        this.m_rsmd = null;
    }
    
    public ResultSetMetaData parseDataElement(final Node node) throws IllegalArgumentException {
        Node node2 = null;
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                node2 = item;
                break;
            }
        }
        if (node2 != null) {
            final NodeList childNodes2 = node2.getChildNodes();
            for (int length2 = childNodes2.getLength(), j = 0; j < length2; ++j) {
                final Node item2 = childNodes2.item(j);
                if (item2.getNodeType() == 1) {
                    final String localName = item2.getLocalName();
                    this.extractNodeValue(item2);
                    if (localName.equals("complexType")) {
                        try {
                            this.parseComplexType(item2);
                        }
                        catch (IllegalArgumentException ex) {
                            throw ex;
                        }
                    }
                }
            }
        }
        return this.m_rsmd;
    }
    
    void parseComplexType(final Node node) throws IllegalArgumentException {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                if (localName.equals("sequence")) {
                    final int checkSequence = this.checkSequence(item);
                    Label_0191: {
                        if (checkSequence != -1) {
                            if (this.m_rsmd != null) {
                                throw new IllegalArgumentException("Unable to parse schema for dynamic temp-table.  Multiple sequences found.");
                            }
                            try {
                                this.createRsMetaData(item, checkSequence);
                                break Label_0191;
                            }
                            catch (IllegalArgumentException ex) {
                                throw ex;
                            }
                        }
                        final NodeList childNodes2 = item.getChildNodes();
                        for (int length2 = childNodes2.getLength(), j = 0; j < length2; ++j) {
                            final Node item2 = childNodes2.item(j);
                            if (item2.getNodeType() == 1 && item2.getLocalName().equals("element")) {
                                this.parseElement(item2);
                            }
                        }
                    }
                }
                else if (localName.equals("choice")) {
                    final NodeList childNodes3 = item.getChildNodes();
                    for (int length3 = childNodes3.getLength(), k = 0; k < length3; ++k) {
                        final Node item3 = childNodes3.item(k);
                        if (item3.getNodeType() == 1 && item3.getLocalName().equals("element")) {
                            this.parseElement(item3);
                        }
                    }
                }
                else if (localName.equals("all")) {
                    throw new IllegalArgumentException("Unable to parse schema for dynamic temp-table.  <all> element encountered.");
                }
            }
        }
    }
    
    void parseElement(final Node node) {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1 && item.getLocalName().equals("complexType")) {
                this.parseComplexType(item);
            }
        }
    }
    
    String extractNodeValue(final Node node) {
        String nodeValue = "";
        if (node.getNodeType() == 1 && node.hasChildNodes()) {
            final Node firstChild = node.getFirstChild();
            if (firstChild.getNodeType() == 3) {
                nodeValue = firstChild.getNodeValue();
            }
        }
        return nodeValue;
    }
    
    int checkSequence(final Node node) {
        int n = 0;
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                if (!item.getLocalName().equals("element")) {
                    return -1;
                }
                if (item.getFirstChild() != null) {
                    return -1;
                }
                ++n;
            }
        }
        return n;
    }
    
    void createRsMetaData(final Node node, final int n) throws IllegalArgumentException {
        final NodeList childNodes = node.getChildNodes();
        final int length = childNodes.getLength();
        this.m_rsmd = new ResultSetMetaData(0, n);
        int n2 = 0;
        for (int i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                ++n2;
                final String attribute = DOMUtils.getAttribute((Element)item, "name");
                if (attribute == null) {
                    throw new IllegalArgumentException("Unable to create MetaData for dynamic temp-table.  'name' attribute missing from schema element.");
                }
                String str = DOMUtils.getAttribute((Element)item, "type");
                if (str == null) {
                    throw new IllegalArgumentException("Unable to create MetaData for dynamic temp-table.  'type' attribute missing from schema element.");
                }
                final int index = str.indexOf(58);
                if (index != -1) {
                    str = str.substring(index + 1);
                }
                int n3;
                if (str.equals("string")) {
                    n3 = 1;
                }
                else if (str.equals("int")) {
                    n3 = 4;
                }
                else if (str.equals("boolean")) {
                    n3 = 3;
                }
                else if (str.equals("decimal")) {
                    n3 = 5;
                }
                else if (str.equals("date")) {
                    n3 = 2;
                }
                else if (str.equals("dateTime")) {
                    n3 = 40;
                }
                else if (str.equals("base64Binary")) {
                    n3 = 8;
                }
                else {
                    if (!str.equals("long")) {
                        throw new IllegalArgumentException("Unable to set MetaData for dynamic temp-table.  Column datatype '" + str + "'not supported.");
                    }
                    n3 = 41;
                }
                final String attribute2 = DOMUtils.getAttribute((Element)item, "minOccurs");
                final String attribute3 = DOMUtils.getAttribute((Element)item, "maxOccurs");
                if (attribute2 != null && attribute3 != null && !attribute2.equals(attribute3)) {
                    throw new IllegalArgumentException("Unable to create MetaData for dynamic temp-table.  'minOccurs' and 'maxOccurs' attributes in schema element must match.");
                }
                int intValue = 0;
                if (attribute3 != null && !attribute3.equals("1")) {
                    intValue = Integer.decode(attribute3);
                }
                this.m_rsmd.setFieldDesc(n2, attribute, intValue, n3);
            }
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.NodeList;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import com.progress.wsa.open4gl.XMLSerializable;
import java.io.Serializable;

public class PGMetaData implements Serializable, XMLSerializable
{
    private static final long serialVersionUID = -353150819831076768L;
    String m_strFieldName;
    int m_enumType;
    int m_nExtent;
    String m_strTypeName;
    int m_position;
    int m_userOrder;
    int m_flag;
    int m_xmlMapping;
    String m_strInitialValue;
    String m_strCaption;
    String m_strHelpString;
    String m_strXmlNodeName;
    static int CAN_READ;
    static int CAN_WRITE;
    static int CASE_SENSITIVE;
    static int KEY_FLD;
    static int READ_ONLY;
    static int TTAB;
    static int MANDATORY;
    static int INI_DITEM;
    static int DEFAULT_VALS;
    static int LCL_INIT;
    static int LIT_Q;
    static int COLUMN_CODEPAGE;
    static int IS_UNIQUE;
    
    public PGMetaData() {
        this.m_strFieldName = null;
        this.m_enumType = 0;
        this.m_nExtent = 0;
        this.m_strTypeName = null;
        this.m_position = 0;
        this.m_userOrder = 0;
        this.m_flag = 0;
        this.m_xmlMapping = 0;
        this.m_strInitialValue = null;
        this.m_strCaption = null;
        this.m_strHelpString = null;
        this.m_strXmlNodeName = null;
    }
    
    public String getFieldName() {
        return this.m_strFieldName;
    }
    
    public void setFieldName(final String strFieldName) {
        this.m_strFieldName = strFieldName;
    }
    
    public int getType() {
        return this.m_enumType;
    }
    
    public void setType(final int enumType) {
        this.m_enumType = enumType;
    }
    
    public int getExtent() {
        return this.m_nExtent;
    }
    
    public void setExtent(final int nExtent) {
        this.m_nExtent = nExtent;
    }
    
    public String getTypeName() {
        return this.m_strTypeName;
    }
    
    public void setTypeName(final String strTypeName) {
        this.m_strTypeName = strTypeName;
    }
    
    public int getPosition() {
        return this.m_position;
    }
    
    public void setPosition(final int position) {
        this.m_position = position;
    }
    
    public int getUserOrder() {
        return this.m_userOrder;
    }
    
    public void setUserOrder(final int userOrder) {
        this.m_userOrder = userOrder;
    }
    
    public int getFlag() {
        return this.m_flag;
    }
    
    public void setFlag(final int flag) {
        this.m_flag = flag;
    }
    
    public boolean isUnique() {
        return (this.m_flag & PGMetaData.IS_UNIQUE) > 0;
    }
    
    public void setIsUnique(final boolean b) {
        if (b) {
            this.m_flag |= PGMetaData.IS_UNIQUE;
        }
        else {
            this.m_flag &= ~PGMetaData.IS_UNIQUE;
        }
    }
    
    public int getXMLMapping() {
        return this.m_xmlMapping;
    }
    
    public void setXMLMapping(final int xmlMapping) {
        this.m_xmlMapping = xmlMapping;
    }
    
    public String getInitialValue() {
        return this.m_strInitialValue;
    }
    
    public void setInitialValue(final String strInitialValue) {
        this.m_strInitialValue = strInitialValue;
    }
    
    public String getCaption() {
        return this.m_strCaption;
    }
    
    public void setCaption(final String strCaption) {
        this.m_strCaption = strCaption;
    }
    
    public String getHelpString() {
        return this.m_strHelpString;
    }
    
    public void setHelpString(final String strHelpString) {
        this.m_strHelpString = strHelpString;
    }
    
    public boolean hasSameSchema(final PGMetaData pgMetaData) {
        return this.m_strFieldName.equals(pgMetaData.getFieldName()) && this.m_enumType == pgMetaData.getType() && this.m_nExtent == pgMetaData.getExtent() && this.m_strTypeName.equals(pgMetaData.getTypeName());
    }
    
    public boolean hasSameSchemaWS(final PGMetaData pgMetaData) {
        if (!this.m_strFieldName.equals(pgMetaData.getFieldName()) || this.m_enumType != pgMetaData.getType() || this.m_nExtent != pgMetaData.getExtent() || !this.m_strTypeName.equals(pgMetaData.getTypeName())) {
            return false;
        }
        final String xmlNodeName = pgMetaData.getXmlNodeName();
        return (xmlNodeName == null && this.m_strXmlNodeName == null) || (xmlNodeName != null && this.m_strXmlNodeName != null && xmlNodeName.equals(this.m_strXmlNodeName));
    }
    
    public String getXmlNodeName() {
        return this.m_strXmlNodeName;
    }
    
    public void setXmlNodeName(final String strXmlNodeName) {
        this.m_strXmlNodeName = strXmlNodeName;
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String s2) throws SAXException {
        boolean b = false;
        if (s2 != null && s2.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strFieldName != null) {
                String string;
                if (b) {
                    string = s2 + ":FieldName";
                }
                else {
                    string = "FieldName";
                }
                xmlSerializer.startElement(s, "FieldName", string, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strFieldName.toCharArray(), 0, this.m_strFieldName.length());
                xmlSerializer.endElement(s, "FieldName", string);
            }
            String string2;
            if (b) {
                string2 = s2 + ":Type";
            }
            else {
                string2 = "Type";
            }
            xmlSerializer.startElement(s, "Type", string2, (Attributes)null);
            final String string3 = Integer.toString(this.m_enumType);
            ((BaseMarkupSerializer)xmlSerializer).characters(string3.toCharArray(), 0, string3.length());
            xmlSerializer.endElement(s, "Type", string2);
            String string4;
            if (b) {
                string4 = s2 + ":Extent";
            }
            else {
                string4 = "Extent";
            }
            xmlSerializer.startElement(s, "Extent", string4, (Attributes)null);
            final String string5 = Integer.toString(this.m_nExtent);
            ((BaseMarkupSerializer)xmlSerializer).characters(string5.toCharArray(), 0, string5.length());
            xmlSerializer.endElement(s, "Extent", string4);
            if (this.m_strTypeName != null) {
                String string6;
                if (b) {
                    string6 = s2 + ":TypeName";
                }
                else {
                    string6 = "TypeName";
                }
                xmlSerializer.startElement(s, "TypeName", string6, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strTypeName.toCharArray(), 0, this.m_strTypeName.length());
                xmlSerializer.endElement(s, "TypeName", string6);
            }
            String string7;
            if (b) {
                string7 = s2 + ":XmlNodeType";
            }
            else {
                string7 = "XmlNodeType";
            }
            xmlSerializer.startElement(s, "XmlNodeType", string7, (Attributes)null);
            final String string8 = Integer.toString(this.m_xmlMapping);
            ((BaseMarkupSerializer)xmlSerializer).characters(string8.toCharArray(), 0, string8.length());
            xmlSerializer.endElement(s, "XmlNodeType", string7);
            if (this.m_strXmlNodeName != null) {
                String string9;
                if (b) {
                    string9 = s2 + ":XmlNodeName";
                }
                else {
                    string9 = "XmlNodeName";
                }
                xmlSerializer.startElement(s, "XmlNodeName", string9, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strXmlNodeName.toCharArray(), 0, this.m_strXmlNodeName.length());
                xmlSerializer.endElement(s, "XmlNodeName", string9);
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue = WsaParser.extractNodeValue(item);
                if (localName.equals("FieldName")) {
                    this.m_strFieldName = nodeValue;
                }
                else if (localName.equals("Type")) {
                    this.m_enumType = Integer.parseInt(nodeValue);
                }
                else if (localName.equals("Extent")) {
                    this.m_nExtent = Integer.parseInt(nodeValue);
                }
                else if (localName.equals("TypeName")) {
                    this.m_strTypeName = nodeValue;
                }
                else if (localName.equals("XmlNodeType")) {
                    this.m_xmlMapping = Integer.parseInt(nodeValue);
                }
                else if (localName.equals("XmlNodeName")) {
                    this.m_strXmlNodeName = nodeValue;
                }
            }
        }
    }
    
    static {
        PGMetaData.CAN_READ = 1;
        PGMetaData.CAN_WRITE = 2;
        PGMetaData.CASE_SENSITIVE = 4;
        PGMetaData.KEY_FLD = 8;
        PGMetaData.READ_ONLY = 16;
        PGMetaData.TTAB = 32;
        PGMetaData.MANDATORY = 64;
        PGMetaData.INI_DITEM = 128;
        PGMetaData.DEFAULT_VALS = 256;
        PGMetaData.LCL_INIT = 512;
        PGMetaData.LIT_Q = 1024;
        PGMetaData.COLUMN_CODEPAGE = 2048;
        PGMetaData.IS_UNIQUE = 4096;
    }
}

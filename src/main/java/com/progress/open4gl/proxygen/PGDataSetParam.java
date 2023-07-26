// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.NodeList;
import com.progress.wsa.admin.WsaParser;
import java.util.Vector;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;

public class PGDataSetParam extends PGParam implements IPGStrongNameParam
{
    PGDataTableParam[] m_pDataSetTables;
    PGDataLink[] m_pDataLinks;
    String m_strClassName;
    String m_strNamespace;
    String m_strProcName;
    String m_strXmlNamespace;
    String m_strXmlNamespacePrefix;
    String m_strXmlNodeName;
    int m_ReferenceOnly;
    boolean m_bTopMatch;
    int m_sameParamNameIndex;
    
    public PGDataSetParam() {
        this.m_pDataSetTables = new PGDataTableParam[0];
        this.m_pDataLinks = new PGDataLink[0];
        this.m_strClassName = null;
        this.m_strNamespace = null;
        this.m_strProcName = null;
        this.m_strXmlNamespace = null;
        this.m_bTopMatch = false;
        this.m_sameParamNameIndex = 0;
        this.m_strXmlNodeName = null;
        this.m_ReferenceOnly = -1;
    }
    
    public PGDataSetParam(final PGDataSetParam pgDataSetParam) {
        super(pgDataSetParam);
        this.m_pDataSetTables = pgDataSetParam.m_pDataSetTables;
        this.m_pDataLinks = pgDataSetParam.m_pDataLinks;
        this.m_strClassName = pgDataSetParam.m_strClassName;
        this.m_strNamespace = pgDataSetParam.m_strNamespace;
        this.m_strXmlNamespace = pgDataSetParam.m_strXmlNamespace;
        this.m_strProcName = pgDataSetParam.m_strProcName;
        this.m_bTopMatch = pgDataSetParam.m_bTopMatch;
        this.m_sameParamNameIndex = pgDataSetParam.m_sameParamNameIndex;
        this.m_strXmlNodeName = pgDataSetParam.m_strXmlNodeName;
        this.m_ReferenceOnly = pgDataSetParam.m_ReferenceOnly;
    }
    
    public PGDataSetParam(final PGParam pgParam) {
        super(pgParam);
    }
    
    public PGDataTableParam[] getDataSetTables() {
        return this.m_pDataSetTables;
    }
    
    void setDataSetTables(final PGDataTableParam[] pDataSetTables) {
        this.m_pDataSetTables = pDataSetTables;
    }
    
    public PGDataTableParam getDataSetTable(final int n) {
        if (n >= this.m_pDataSetTables.length) {
            return null;
        }
        return this.m_pDataSetTables[n];
    }
    
    public PGDataTableParam getDataSetTable(final String anotherString) {
        for (int i = 0; i < this.m_pDataSetTables.length; ++i) {
            if (this.m_pDataSetTables[i].getParamName().equalsIgnoreCase(anotherString)) {
                return this.m_pDataSetTables[i];
            }
        }
        return null;
    }
    
    void setDataSetTable(final int n, final PGDataTableParam pgDataTableParam) {
        if (n < this.m_pDataSetTables.length) {
            this.m_pDataSetTables[n] = pgDataTableParam;
        }
    }
    
    public PGDataLink[] getDataLinks() {
        return this.m_pDataLinks;
    }
    
    void setDataLinks(final PGDataLink[] pDataLinks) {
        this.m_pDataLinks = pDataLinks;
    }
    
    public PGDataLink getDataLink(final int n) {
        if (n >= this.m_pDataLinks.length) {
            return null;
        }
        return this.m_pDataLinks[n];
    }
    
    void setDataLink(final int n, final PGDataLink pgDataLink) {
        if (n < this.m_pDataLinks.length) {
            this.m_pDataLinks[n] = pgDataLink;
        }
    }
    
    public String getClassName() {
        return this.m_strClassName;
    }
    
    public void setClassName(final String s) {
        this.m_strClassName = s;
        for (int i = 0; i < this.m_pDataSetTables.length; ++i) {
            this.m_pDataSetTables[i].setClassName(s + "DataSet+" + this.m_pDataSetTables[i].getParamName());
        }
    }
    
    public void setClassNameForWS(final String strClassName) {
        this.m_strClassName = strClassName;
    }
    
    public String getProcName() {
        return this.m_strProcName;
    }
    
    public void setProcName(final String strProcName) {
        this.m_strProcName = strProcName;
    }
    
    public String getNamespace() {
        return this.m_strNamespace;
    }
    
    public void setNamespace(final String s) {
        this.m_strNamespace = s;
        for (int i = 0; i < this.m_pDataSetTables.length; ++i) {
            this.m_pDataSetTables[i].setNamespace(s);
        }
    }
    
    public String getXmlNamespace() {
        return this.m_strXmlNamespace;
    }
    
    public void setXmlNamespace(final String strXmlNamespace) {
        this.m_strXmlNamespace = strXmlNamespace;
    }
    
    public String getXmlNamespacePrefix() {
        return this.m_strXmlNamespacePrefix;
    }
    
    public void setXmlNamespacePrefix(final String strXmlNamespacePrefix) {
        this.m_strXmlNamespacePrefix = strXmlNamespacePrefix;
    }
    
    public String getXmlNodeName() {
        return this.m_strXmlNodeName;
    }
    
    public void setXmlNodeName(final String strXmlNodeName) {
        this.m_strXmlNodeName = strXmlNodeName;
    }
    
    public void setTopMatch(final boolean bTopMatch) {
        this.m_bTopMatch = bTopMatch;
    }
    
    public boolean isTopMatch() {
        return this.m_bTopMatch;
    }
    
    public void setSameParamNameIndex(final int sameParamNameIndex) {
        this.m_sameParamNameIndex = sameParamNameIndex;
    }
    
    public int getSameParamNameIndex() {
        return this.m_sameParamNameIndex;
    }
    
    public boolean getReferenceOnly() {
        boolean b = false;
        if (this.m_ReferenceOnly < 0) {
            this.m_ReferenceOnly = 0;
            for (int i = 0; i < this.m_pDataSetTables.length; ++i) {
                if (this.m_pDataSetTables[i].getReferenceOnly()) {
                    this.m_ReferenceOnly = 1;
                    b = true;
                    break;
                }
            }
        }
        else {
            b = (this.m_ReferenceOnly != 0);
        }
        return b;
    }
    
    public void setReferenceOnly(final boolean b) {
        if (b) {
            this.m_ReferenceOnly = 1;
        }
        else {
            this.m_ReferenceOnly = 0;
        }
    }
    
    public String getStrongName() {
        String str;
        if (this.m_strNamespace != null && this.m_strNamespace != "") {
            str = this.m_strNamespace + "." + this.m_strClassName;
        }
        else {
            str = this.m_strClassName;
        }
        return str + "DataSet";
    }
    
    public boolean hasSameSchema(final IPGStrongNameParam ipgStrongNameParam) {
        final PGDataSetParam pgDataSetParam = (PGDataSetParam)ipgStrongNameParam;
        final PGDataTableParam[] dataSetTables = pgDataSetParam.getDataSetTables();
        final PGDataLink[] dataLinks = pgDataSetParam.getDataLinks();
        if (this.m_pDataSetTables.length != dataSetTables.length) {
            return false;
        }
        if ((this.m_pDataLinks == null && dataLinks != null) || (this.m_pDataLinks != null && dataLinks == null)) {
            return false;
        }
        if (this.m_pDataLinks != null && dataLinks != null && this.m_pDataLinks.length != dataLinks.length) {
            return false;
        }
        for (int i = 0; i < this.m_pDataSetTables.length; ++i) {
            if (!this.m_pDataSetTables[i].hasSameSchema(dataSetTables[i])) {
                return false;
            }
        }
        if (this.m_pDataLinks != null && dataLinks != null) {
            for (int j = 0; j < this.m_pDataLinks.length; ++j) {
                if (!this.m_pDataLinks[j].hasSameSchema(dataLinks[j])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasSameSchemaWS(final IPGStrongNameParam ipgStrongNameParam) {
        final PGDataSetParam pgDataSetParam = (PGDataSetParam)ipgStrongNameParam;
        final PGDataTableParam[] dataSetTables = pgDataSetParam.getDataSetTables();
        final PGDataLink[] dataLinks = pgDataSetParam.getDataLinks();
        if (!this.getParamName().equals(pgDataSetParam.getParamName())) {
            return false;
        }
        final String xmlNodeName = pgDataSetParam.getXmlNodeName();
        boolean b = false;
        if (xmlNodeName == null && this.m_strXmlNodeName == null) {
            b = true;
        }
        else if (xmlNodeName == null || this.m_strXmlNodeName == null) {
            b = false;
        }
        else if (xmlNodeName.equals(this.m_strXmlNodeName)) {
            b = true;
        }
        if (!b) {
            return false;
        }
        if (this.m_pDataSetTables.length != dataSetTables.length) {
            return false;
        }
        if ((this.m_pDataLinks == null && dataLinks != null) || (this.m_pDataLinks != null && dataLinks == null)) {
            return false;
        }
        if (this.m_pDataLinks != null && dataLinks != null && this.m_pDataLinks.length != dataLinks.length) {
            return false;
        }
        for (int i = 0; i < this.m_pDataSetTables.length; ++i) {
            if (!this.m_pDataSetTables[i].hasSameSchemaWS(dataSetTables[i])) {
                return false;
            }
        }
        if (this.m_pDataLinks != null && dataLinks != null) {
            for (int j = 0; j < this.m_pDataLinks.length; ++j) {
                if (!this.m_pDataLinks[j].hasSameSchema(dataLinks[j])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean hasDatasetAttributeFields() {
        final PGDataTableParam[] dataSetTables = this.getDataSetTables();
        for (int i = 0; i < dataSetTables.length; ++i) {
            final PGMetaData[] metaData = dataSetTables[i].getMetaData();
            for (int j = 0; j < metaData.length; ++j) {
                final int xmlMapping = metaData[j].getXMLMapping();
                if (xmlMapping != 0 && xmlMapping != 3) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetTtabNamespaceConflict() {
        final PGDataTableParam[] dataSetTables = this.getDataSetTables();
        for (int i = 0; i < dataSetTables.length; ++i) {
            final String xmlNamespace = dataSetTables[i].getXmlNamespace();
            if (this.m_strXmlNamespace != null || xmlNamespace != null) {
                if (this.m_strXmlNamespace == null && xmlNamespace != null) {
                    return true;
                }
                if (this.m_strXmlNamespace != null && xmlNamespace != null && !this.m_strXmlNamespace.equals(xmlNamespace)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void writeDataSetXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        String string;
        if (b) {
            string = str + ":DataSetMetaData";
        }
        else {
            string = "DataSetMetaData";
        }
        try {
            xmlSerializer.startElement(s, "DataSetMetaData", string, (Attributes)null);
            final String xmlNamespace = this.getXmlNamespace();
            AttributesImpl attributesImpl;
            if (xmlNamespace == null) {
                attributesImpl = new AttributesImpl();
                attributesImpl.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi:nil", "CDATA", "true");
            }
            else {
                attributesImpl = null;
            }
            String string2;
            if (b) {
                string2 = str + ":NamespaceUri";
            }
            else {
                string2 = "NamespaceUri";
            }
            xmlSerializer.startElement(s, "NamespaceUri", string2, (Attributes)attributesImpl);
            if (xmlNamespace != null) {
                ((BaseMarkupSerializer)xmlSerializer).characters(xmlNamespace.toCharArray(), 0, xmlNamespace.length());
            }
            xmlSerializer.endElement(s, "NamespaceUri", string2);
            final String className = this.getClassName();
            if (className != null && !className.equals(this.getParamName())) {
                String string3;
                if (b) {
                    string3 = str + ":WSDLName";
                }
                else {
                    string3 = "WSDLName";
                }
                xmlSerializer.startElement(s, "WSDLName", string3, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(className.toCharArray(), 0, className.length());
                xmlSerializer.endElement(s, "WSDLName", string3);
            }
            if (this.m_strXmlNodeName != null) {
                String string4;
                if (b) {
                    string4 = str + ":XmlNodeName";
                }
                else {
                    string4 = "XmlNodeName";
                }
                xmlSerializer.startElement(s, "XmlNodeName", string4, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strXmlNodeName.toCharArray(), 0, this.m_strXmlNodeName.length());
                xmlSerializer.endElement(s, "XmlNodeName", string4);
            }
            final PGDataTableParam[] dataSetTables = this.getDataSetTables();
            if (dataSetTables != null) {
                for (int i = 0; i < dataSetTables.length; ++i) {
                    dataSetTables[i].writeDataTableXML(xmlSerializer, s, str);
                }
            }
            final PGDataLink[] dataLinks = this.getDataLinks();
            if (dataLinks != null) {
                for (int j = 0; j < dataLinks.length; ++j) {
                    dataLinks[j].writeDataLinkXML(xmlSerializer, s, str);
                }
            }
            String string5;
            if (b) {
                string5 = str + ":DataSetMetaData";
            }
            else {
                string5 = "DataSetMetaData";
            }
            xmlSerializer.endElement(s, "DataSetMetaData", string5);
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readDataSetXML(final Node node) {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1 && item.getLocalName().equals("DataSetMetaData")) {
                final Vector<Node> vector = new Vector<Node>();
                final Vector<Node> vector2 = new Vector<Node>();
                final NodeList childNodes2 = item.getChildNodes();
                final int length2 = childNodes2.getLength();
                int n = 0;
                int n2 = 0;
                PGDataTableParam[] dataSetTables = null;
                for (int j = 0; j < length2; ++j) {
                    final Node item2 = childNodes2.item(j);
                    if (item2.getNodeType() == 1) {
                        final String localName = item2.getLocalName();
                        if (localName.equals("DataTable")) {
                            vector.addElement(item2);
                            ++n;
                        }
                        else if (localName.equals("DataLink")) {
                            vector2.addElement(item2);
                            ++n2;
                        }
                        else if (localName.equals("NamespaceUri")) {
                            final Node namedItemNS = item2.getAttributes().getNamedItemNS("http://www.w3.org/2001/XMLSchema-instance", "nil");
                            if (namedItemNS != null) {
                                final String nodeValue = namedItemNS.getNodeValue();
                                if (!nodeValue.equals("true") && !nodeValue.equals("1")) {
                                    this.setXmlNamespace(WsaParser.extractNodeValue(item2));
                                }
                            }
                            else {
                                this.setXmlNamespace(WsaParser.extractNodeValue(item2));
                            }
                        }
                        else if (localName.equals("XmlNodeName")) {
                            this.setXmlNodeName(WsaParser.extractNodeValue(item2));
                        }
                        else if (localName.equals("WSDLName")) {
                            this.setClassNameForWS(WsaParser.extractNodeValue(item2));
                        }
                    }
                }
                if (this.m_strClassName == null) {
                    this.setClassNameForWS(this.getParamName());
                }
                if (n > 0) {
                    dataSetTables = new PGDataTableParam[n];
                    this.setDataSetTables(dataSetTables);
                    for (int k = 0; k < n; ++k) {
                        (dataSetTables[k] = new PGDataTableParam()).setParamType(15);
                        dataSetTables[k].readDataTableXML(vector.elementAt(k));
                    }
                }
                if (n2 > 0) {
                    final PGDataLink[] dataLinks = new PGDataLink[n2];
                    this.setDataLinks(dataLinks);
                    for (int l = 0; l < n2; ++l) {
                        (dataLinks[l] = new PGDataLink()).readDataLinkXML(vector2.elementAt(l), dataSetTables);
                    }
                }
            }
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import com.progress.wsa.admin.WsaParser;
import java.util.Vector;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import com.progress.wsa.open4gl.XMLSerializable;
import java.io.Serializable;

public class PGParam implements Serializable, XMLSerializable
{
    private static final long serialVersionUID = -353150811241142176L;
    String m_strName;
    int m_enumType;
    private boolean m_bAllowUnknown;
    int m_nParamNum;
    PGMetaData[] m_pMetaData;
    private String m_strOrigName;
    boolean m_bIsExtentField;
    int m_nExtent;
    boolean m_bWriteXmlBeforeImage;
    String m_strXmlNamespace;
    
    public PGParam() {
        this.m_strName = null;
        this.m_enumType = 0;
        if (PGGenInfo.getCurrentAppObj() != null) {
            this.m_bAllowUnknown = PGGenInfo.getCurrentAppObj().allowUnknown();
            this.m_bWriteXmlBeforeImage = PGGenInfo.getCurrentAppObj().getWriteBeforeImage();
        }
        else {
            this.m_bAllowUnknown = false;
            this.m_bWriteXmlBeforeImage = false;
        }
        this.m_nParamNum = 0;
        this.m_pMetaData = new PGMetaData[0];
        this.m_nExtent = 0;
        this.m_bIsExtentField = false;
        this.m_strXmlNamespace = null;
    }
    
    public PGParam(final PGParam pgParam) {
        this.m_strName = pgParam.m_strName;
        this.m_enumType = pgParam.m_enumType;
        this.m_bAllowUnknown = pgParam.m_bAllowUnknown;
        this.m_nParamNum = pgParam.m_nParamNum;
        this.m_nExtent = pgParam.m_nExtent;
        this.m_bIsExtentField = pgParam.m_bIsExtentField;
        this.m_strOrigName = pgParam.m_strOrigName;
        this.m_bWriteXmlBeforeImage = pgParam.m_bWriteXmlBeforeImage;
        this.m_strXmlNamespace = pgParam.m_strXmlNamespace;
        this.m_pMetaData = pgParam.m_pMetaData;
    }
    
    protected boolean isDataSetParam() {
        final int n = this.m_enumType & 0xFF;
        return n == 36 || n == 37;
    }
    
    public String getParamName() {
        return this.m_strName;
    }
    
    protected String getOrigParamName() {
        return this.m_strOrigName;
    }
    
    public void setParamName(final String strOrigName) {
        this.m_strOrigName = strOrigName;
        this.m_strName = PGAppObj.ProNameToProxyName(strOrigName);
    }
    
    String getParamOrigName() {
        return this.m_strOrigName;
    }
    
    public int getParamType() {
        return this.m_enumType & 0xFF;
    }
    
    protected int getEffectiveParamType(final boolean b) {
        int n = this.m_enumType & 0xFF;
        if (!b || (n != 15 && n != 17)) {
            return n;
        }
        if (this.isEffectiveTTDataSet()) {
            if (n == 15) {
                n = 36;
            }
            else if (n == 17) {
                n = 37;
            }
        }
        return n;
    }
    
    public void setParamType(final int n) {
        this.m_enumType = (this.m_enumType & 0xFF00) + n;
    }
    
    public void setParamMode(final int n) {
        this.m_enumType = (this.m_enumType & 0xFF) + (n << 8);
    }
    
    public int getParamMode() {
        return this.m_enumType >> 8;
    }
    
    public int getExtent() {
        return this.m_nExtent;
    }
    
    public void setExtent(final int nExtent) {
        this.m_nExtent = nExtent;
    }
    
    public boolean isExtentField() {
        return this.m_bIsExtentField;
    }
    
    public void setIsExtentField(final boolean bIsExtentField) {
        this.m_bIsExtentField = bIsExtentField;
    }
    
    protected boolean isEffectiveTTDataSet() {
        boolean b = false;
        final int n = this.m_enumType & 0xFF;
        boolean b2 = true;
        if (n != 15 && n != 17) {
            return false;
        }
        final PGMethodDetail currentMethodDetail = PGGenInfo.getCurrentMethodDetail();
        if (currentMethodDetail != null) {
            b2 = currentMethodDetail.isTTResultSet();
            b = currentMethodDetail.usesTTMappingDefault();
        }
        else {
            final PGProcDetail currentProcDetail = PGGenInfo.getCurrentProcDetail();
            if (currentProcDetail != null) {
                b2 = currentProcDetail.isTTResultSet();
                b = currentProcDetail.usesTTMappingDefault();
            }
        }
        if (b && PGGenInfo.getCurrentAppObj() != null) {
            b2 = PGGenInfo.getCurrentAppObj().isTTResultSet();
        }
        return !b2;
    }
    
    protected boolean isEffectiveTTResultSet() {
        final int n = this.m_enumType & 0xFF;
        return (n == 15 || n == 17) && !this.isEffectiveTTDataSet();
    }
    
    public boolean effectiveAllowUnknown() {
        boolean b = false;
        final PGMethodDetail currentMethodDetail = PGGenInfo.getCurrentMethodDetail();
        if (currentMethodDetail != null) {
            b = currentMethodDetail.usesUnknownDefault();
        }
        else {
            final PGProcDetail currentProcDetail = PGGenInfo.getCurrentProcDetail();
            if (currentProcDetail != null) {
                b = currentProcDetail.usesUnknownDefault();
            }
        }
        if (b && PGGenInfo.getCurrentAppObj() != null) {
            return PGGenInfo.getCurrentAppObj().allowUnknown();
        }
        return this.m_bAllowUnknown;
    }
    
    public boolean allowUnknown() {
        return this.m_bAllowUnknown;
    }
    
    public void setAllowUnknown(final boolean bAllowUnknown) {
        this.m_bAllowUnknown = bAllowUnknown;
    }
    
    public int getParamOrdinal() {
        return this.m_nParamNum;
    }
    
    public void setParamOrdinal(final int nParamNum) {
        this.m_nParamNum = nParamNum;
    }
    
    public PGMetaData[] getMetaData() {
        return this.m_pMetaData;
    }
    
    public void setMetaData(final PGMetaData[] pMetaData) {
        this.m_pMetaData = pMetaData;
    }
    
    public PGMetaData getMetaData(final int n) {
        if (n >= this.m_pMetaData.length) {
            return null;
        }
        return this.m_pMetaData[n];
    }
    
    void setMetaData(final int n, final PGMetaData pgMetaData) {
        if (n < this.m_pMetaData.length) {
            this.m_pMetaData[n] = pgMetaData;
        }
    }
    
    public void setWriteBeforeImage(final boolean bWriteXmlBeforeImage) {
        this.m_bWriteXmlBeforeImage = bWriteXmlBeforeImage;
    }
    
    public boolean getWriteBeforeImage() {
        return this.m_bWriteXmlBeforeImage;
    }
    
    public String getXmlNamespace() {
        return this.m_strXmlNamespace;
    }
    
    public void setXmlNamespace(final String strXmlNamespace) {
        this.m_strXmlNamespace = strXmlNamespace;
    }
    
    public boolean getReferenceOnly() {
        return false;
    }
    
    public void setReferenceOnly(final boolean b) {
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strName != null) {
                String string;
                if (b) {
                    string = str + ":Name";
                }
                else {
                    string = "Name";
                }
                xmlSerializer.startElement(s, "Name", string, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strName.toCharArray(), 0, this.m_strName.length());
                xmlSerializer.endElement(s, "Name", string);
            }
            if (this.m_strOrigName != null) {
                String string2;
                if (b) {
                    string2 = str + ":OrigName";
                }
                else {
                    string2 = "OrigName";
                }
                xmlSerializer.startElement(s, "OrigName", string2, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strOrigName.toCharArray(), 0, this.m_strOrigName.length());
                xmlSerializer.endElement(s, "OrigName", string2);
            }
            String string3;
            if (b) {
                string3 = str + ":Type";
            }
            else {
                string3 = "Type";
            }
            xmlSerializer.startElement(s, "Type", string3, (Attributes)null);
            final String string4 = Integer.toString(this.m_enumType & 0xFF);
            ((BaseMarkupSerializer)xmlSerializer).characters(string4.toCharArray(), 0, string4.length());
            xmlSerializer.endElement(s, "Type", string3);
            String string5;
            if (b) {
                string5 = str + ":Mode";
            }
            else {
                string5 = "Mode";
            }
            xmlSerializer.startElement(s, "Mode", string5, (Attributes)null);
            final String string6 = Integer.toString(this.m_enumType >> 8);
            ((BaseMarkupSerializer)xmlSerializer).characters(string6.toCharArray(), 0, string6.length());
            xmlSerializer.endElement(s, "Mode", string5);
            if (this.m_bIsExtentField) {
                String string7;
                if (b) {
                    string7 = str + ":Extent";
                }
                else {
                    string7 = "Extent";
                }
                xmlSerializer.startElement(s, "Extent", string7, (Attributes)null);
                if (this.m_nExtent != 0) {
                    Integer.toString(this.m_nExtent);
                }
                final String string8 = Integer.toString(this.m_nExtent);
                ((BaseMarkupSerializer)xmlSerializer).characters(string8.toCharArray(), 0, string8.length());
                xmlSerializer.endElement(s, "Extent", string7);
            }
            if (this.getParamType() == 15) {
                final String xmlNamespace = this.getXmlNamespace();
                AttributesImpl attributesImpl;
                if (xmlNamespace == null) {
                    attributesImpl = new AttributesImpl();
                    attributesImpl.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi:nil", "CDATA", "true");
                }
                else {
                    attributesImpl = null;
                }
                String string9;
                if (b) {
                    string9 = str + ":NamespaceUri";
                }
                else {
                    string9 = "NamespaceUri";
                }
                xmlSerializer.startElement(s, "NamespaceUri", string9, (Attributes)attributesImpl);
                if (xmlNamespace != null) {
                    ((BaseMarkupSerializer)xmlSerializer).characters(xmlNamespace.toCharArray(), 0, xmlNamespace.length());
                }
                xmlSerializer.endElement(s, "NamespaceUri", string9);
            }
            for (int i = 0; i < this.m_pMetaData.length; ++i) {
                String string10;
                if (b) {
                    string10 = str + ":MetaData";
                }
                else {
                    string10 = "MetaData";
                }
                xmlSerializer.startElement(s, "MetaData", string10, (Attributes)null);
                this.m_pMetaData[i].writeXML(xmlSerializer, s, str);
                xmlSerializer.endElement(s, "MetaData", string10);
            }
            if (this.getParamType() == 36 && this instanceof PGDataSetParam) {
                ((PGDataSetParam)this).writeDataSetXML(xmlSerializer, s, str);
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final Vector vector = new Vector<PGMetaData>();
        final NamedNodeMap attributes = node.getAttributes();
        final String nodeValue = attributes.getNamedItem("allowUnknown").getNodeValue();
        if (nodeValue.equals("true") || nodeValue.equals("1")) {
            this.m_bAllowUnknown = true;
        }
        else {
            this.m_bAllowUnknown = false;
        }
        this.m_nParamNum = Integer.parseInt(attributes.getNamedItem("ordinal").getNodeValue());
        final Node namedItem = attributes.getNamedItem("writeXmlBeforeImage");
        if (namedItem != null) {
            final String nodeValue2 = namedItem.getNodeValue();
            if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
                this.m_bWriteXmlBeforeImage = true;
            }
            else {
                this.m_bWriteXmlBeforeImage = false;
            }
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue3 = WsaParser.extractNodeValue(item);
                if (localName.equals("Name")) {
                    this.m_strName = nodeValue3;
                }
                else if (localName.equals("OrigName")) {
                    this.m_strOrigName = nodeValue3;
                }
                else if (localName.equals("Type")) {
                    this.m_enumType += Integer.parseInt(nodeValue3);
                }
                else if (localName.equals("Mode")) {
                    this.m_enumType += Integer.parseInt(nodeValue3) << 8;
                }
                else if (localName.equals("Extent")) {
                    this.m_bIsExtentField = true;
                    this.m_nExtent = Integer.parseInt(nodeValue3);
                    if (this.m_nExtent == -1) {
                        this.m_nExtent = 0;
                    }
                }
                else if (localName.equals("NamespaceUri")) {
                    final Node namedItemNS = item.getAttributes().getNamedItemNS("http://www.w3.org/2001/XMLSchema-instance", "nil");
                    if (namedItemNS != null) {
                        final String nodeValue4 = namedItemNS.getNodeValue();
                        if (!nodeValue4.equals("true") && !nodeValue4.equals("1")) {
                            this.setXmlNamespace(nodeValue3);
                        }
                    }
                    else {
                        this.setXmlNamespace(nodeValue3);
                    }
                }
                else if (localName.equals("MetaData")) {
                    final PGMetaData obj = new PGMetaData();
                    obj.readXML(item);
                    vector.addElement(obj);
                }
            }
        }
        if (!vector.isEmpty()) {
            vector.copyInto(this.m_pMetaData = new PGMetaData[vector.size()]);
        }
    }
}

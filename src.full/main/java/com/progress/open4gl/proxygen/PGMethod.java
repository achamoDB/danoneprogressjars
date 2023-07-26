// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import com.progress.wsa.open4gl.XMLSerializable;
import java.io.Serializable;

public class PGMethod implements Serializable, XMLSerializable
{
    private static final long serialVersionUID = -353150815536109472L;
    public static final int METHODTYPE_PROCEDURE = 1;
    public static final int METHODTYPE_FUNCTION = 2;
    String m_strInternalProc;
    boolean m_bExcluded;
    int m_nProcType;
    private PGMethodDetail m_pMethodDetail;
    boolean m_bBadParams;
    
    public PGMethod() {
        this.m_strInternalProc = null;
        this.m_bExcluded = false;
        this.m_bBadParams = false;
        this.m_nProcType = 1;
        this.m_pMethodDetail = null;
    }
    
    public PGMethod(final PGMethod pgMethod) {
        this.m_strInternalProc = pgMethod.m_strInternalProc;
        this.m_bExcluded = pgMethod.m_bExcluded;
        this.m_bBadParams = pgMethod.m_bBadParams;
        this.m_nProcType = pgMethod.m_nProcType;
        if (pgMethod.m_pMethodDetail != null) {
            this.m_pMethodDetail = new PGMethodDetail(pgMethod.m_pMethodDetail);
        }
        else {
            this.m_pMethodDetail = null;
        }
    }
    
    public String getInternalProc() {
        return this.m_strInternalProc;
    }
    
    public void setInternalProc(final String strInternalProc) {
        this.m_strInternalProc = strInternalProc;
    }
    
    public boolean isExcluded() {
        return this.m_bExcluded;
    }
    
    public void setExcluded(final boolean bExcluded) {
        this.m_bExcluded = bExcluded;
    }
    
    public boolean hasBadParams() {
        return this.m_bBadParams;
    }
    
    public void setBadParams(final boolean bBadParams) {
        this.m_bBadParams = bBadParams;
    }
    
    public int getProcType() {
        return this.m_nProcType;
    }
    
    public void setProcType(final int n) {
        this.m_nProcType = n;
        if (this.m_pMethodDetail != null) {
            if (n == 2) {
                this.m_pMethodDetail.setUseReturnValue(true);
            }
            this.m_pMethodDetail.setProcType(n);
        }
    }
    
    public PGMethodDetail getMethodDetail() {
        PGGenInfo.setCurrentMethodDetail(this.m_pMethodDetail);
        return this.m_pMethodDetail;
    }
    
    public void setMethodDetail(final PGMethodDetail pMethodDetail) {
        (this.m_pMethodDetail = pMethodDetail).setProcType(this.m_nProcType);
    }
    
    public boolean hasReferenceOnlyParameter() {
        return this.m_pMethodDetail.hasReferenceOnlyParameter();
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strInternalProc != null) {
                String string;
                if (b) {
                    string = str + ":Name";
                }
                else {
                    string = "Name";
                }
                xmlSerializer.startElement(s, "Name", string, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strInternalProc.toCharArray(), 0, this.m_strInternalProc.length());
                xmlSerializer.endElement(s, "Name", string);
            }
            String string2;
            if (b) {
                string2 = str + ":ProcType";
            }
            else {
                string2 = "ProcType";
            }
            xmlSerializer.startElement(s, "ProcType", string2, (Attributes)null);
            final String string3 = Integer.toString(this.m_nProcType);
            ((BaseMarkupSerializer)xmlSerializer).characters(string3.toCharArray(), 0, string3.length());
            xmlSerializer.endElement(s, "ProcType", string2);
            if (this.m_pMethodDetail != null) {
                final AttributesImpl attributesImpl = new AttributesImpl();
                attributesImpl.addAttribute("", "isCustomized", "isCustomized", "CDATA", this.m_pMethodDetail.isCustomized() ? "true" : "false");
                attributesImpl.addAttribute("", "usesUnknownDefault", "usesUnknownDefault", "CDATA", this.m_pMethodDetail.usesUnknownDefault() ? "true" : "false");
                attributesImpl.addAttribute("", "useRetVal", "useRetVal", "CDATA", this.m_pMethodDetail.usesReturnValue() ? "true" : "false");
                attributesImpl.addAttribute("", "usesTTMappingDefault", "usesTTMappingDefault", "CDATA", this.m_pMethodDetail.usesTTMappingDefault() ? "true" : "false");
                attributesImpl.addAttribute("", "IsTTResultSet", "IsTTResultSet", "CDATA", this.m_pMethodDetail.isTTResultSet() ? "true" : "false");
                attributesImpl.addAttribute("", "usesBeforeImageDefault", "usesBeforeImageDefault", "CDATA", this.m_pMethodDetail.usesBeforeImageDefault() ? "true" : "false");
                String string4;
                if (b) {
                    string4 = str + ":MethodDetail";
                }
                else {
                    string4 = "MethodDetail";
                }
                xmlSerializer.startElement(s, "MethodDetail", string4, (Attributes)attributesImpl);
                this.m_pMethodDetail.writeXML(xmlSerializer, s, str);
                xmlSerializer.endElement(s, "MethodDetail", string4);
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final NamedNodeMap attributes = node.getAttributes();
        final String nodeValue = attributes.getNamedItem("isExcluded").getNodeValue();
        if (nodeValue.equals("true") || nodeValue.equals("1")) {
            this.m_bExcluded = true;
        }
        else {
            this.m_bExcluded = false;
        }
        final String nodeValue2 = attributes.getNamedItem("hasBadParams").getNodeValue();
        if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
            this.m_bBadParams = true;
        }
        else {
            this.m_bBadParams = false;
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue3 = WsaParser.extractNodeValue(item);
                if (localName.equals("Name")) {
                    this.m_strInternalProc = nodeValue3;
                }
                else if (localName.equals("ProcType")) {
                    this.m_nProcType = Integer.parseInt(nodeValue3);
                }
                else if (localName.equals("MethodDetail")) {
                    (this.m_pMethodDetail = new PGMethodDetail()).readXML(item);
                    this.m_pMethodDetail.setProcType(this.m_nProcType);
                }
            }
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.m_pMethodDetail != null && !this.m_pMethodDetail.isCustomized()) {
            final PGMethodDetail pMethodDetail = this.m_pMethodDetail;
            this.m_pMethodDetail = null;
            objectOutputStream.defaultWriteObject();
            this.m_pMethodDetail = pMethodDetail;
        }
        else {
            objectOutputStream.defaultWriteObject();
        }
    }
}

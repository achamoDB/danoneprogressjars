// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import com.progress.open4gl.Parameter;
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

public class PGMethodDetail implements IPGDetail, Serializable, XMLSerializable
{
    private static final long serialVersionUID = -353150815536109472L;
    String m_strMethod;
    String m_strODLHelp;
    String m_strHelp;
    PGParam m_pRetVal;
    boolean m_bCustomized;
    boolean m_bUseUnkDefault;
    PGParam[] m_pParams;
    boolean m_bUseRetVal;
    int m_nProcType;
    boolean m_bIsTTResultSet;
    boolean m_bUseTTMappingDefault;
    boolean m_bUseBeforeImageDefault;
    
    public PGMethodDetail() {
        this.m_strMethod = null;
        this.m_strODLHelp = null;
        this.m_strHelp = null;
        this.m_pRetVal = null;
        this.m_bCustomized = false;
        this.m_bUseUnkDefault = true;
        this.m_pParams = new PGParam[0];
        this.m_bUseRetVal = false;
        this.m_nProcType = 1;
        this.m_bIsTTResultSet = true;
        this.m_bUseTTMappingDefault = true;
        this.m_bUseBeforeImageDefault = true;
    }
    
    public PGMethodDetail(final PGMethodDetail pgMethodDetail) {
        this.m_strMethod = pgMethodDetail.m_strMethod;
        this.m_strODLHelp = pgMethodDetail.m_strODLHelp;
        this.m_strHelp = pgMethodDetail.m_strHelp;
        if (pgMethodDetail.m_pRetVal == null) {
            this.m_pRetVal = null;
        }
        else {
            this.m_pRetVal = new PGParam(pgMethodDetail.m_pRetVal);
        }
        this.m_bCustomized = pgMethodDetail.m_bCustomized;
        this.m_bUseUnkDefault = pgMethodDetail.m_bUseUnkDefault;
        this.m_bUseRetVal = pgMethodDetail.m_bUseRetVal;
        this.m_nProcType = pgMethodDetail.m_nProcType;
        this.m_bIsTTResultSet = pgMethodDetail.m_bIsTTResultSet;
        this.m_bUseTTMappingDefault = pgMethodDetail.m_bUseTTMappingDefault;
        this.m_bUseBeforeImageDefault = pgMethodDetail.m_bUseBeforeImageDefault;
        final int length = pgMethodDetail.m_pParams.length;
        this.m_pParams = new PGParam[length];
        for (int i = 0; i < length; ++i) {
            this.m_pParams[i] = new PGParam(pgMethodDetail.m_pParams[i]);
        }
    }
    
    protected void updateTTMappings() {
        if (this.m_pParams == null || this.m_pParams.length == 0) {
            return;
        }
        if (this.anyDataSetParams()) {
            this.m_bUseTTMappingDefault = false;
            this.m_bIsTTResultSet = false;
        }
    }
    
    protected boolean anyDataSetParams() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == 36 || this.m_pParams[i].getParamType() == 37) {
                return true;
            }
        }
        return false;
    }
    
    public String getMethodName() {
        return this.m_strMethod;
    }
    
    public void setMethodName(final String s) {
        if (this.m_strMethod == null || !this.m_strMethod.equals(s)) {
            this.m_strMethod = s;
        }
    }
    
    public String valMethodName(final String s, final boolean b, final boolean b2, final boolean b3) {
        String s2 = null;
        if (s == null || s.length() == 0) {
            s2 = PGGenInfo.getMsg(8099442454849133699L, new Object[] { PGGenInfo.getResources().getTranString("PG_MethodName") });
        }
        else if (!PGAppObj.validateName(s, b, b2, b3)) {
            s2 = PGGenInfo.getMsg(8099442454849133701L, new Object[] { PGGenInfo.getResources().getTranString("PG_MethodName") });
        }
        return s2;
    }
    
    public String getODLHelpString() {
        return this.m_strODLHelp;
    }
    
    public void setODLHelpString(String removeReturns) {
        removeReturns = PGAppObj.removeReturns(removeReturns);
        this.m_strODLHelp = removeReturns;
    }
    
    public String getHelpString() {
        return this.m_strHelp;
    }
    
    public void setHelpString(final String strHelp) {
        this.m_strHelp = strHelp;
    }
    
    public PGParam getReturnValue() {
        return this.m_pRetVal;
    }
    
    public void setReturnValue(final PGParam pRetVal) {
        this.m_pRetVal = pRetVal;
    }
    
    public boolean isCustomized() {
        return this.m_bCustomized;
    }
    
    public void setCustomized(final boolean bCustomized) {
        this.m_bCustomized = bCustomized;
    }
    
    public boolean usesUnknownDefault() {
        return this.m_bUseUnkDefault;
    }
    
    public void setUnknownDefault(final boolean bUseUnkDefault) {
        this.m_bUseUnkDefault = bUseUnkDefault;
    }
    
    public boolean usesBeforeImageDefault() {
        return this.m_bUseBeforeImageDefault;
    }
    
    public void setBeforeImageDefault(final boolean bUseBeforeImageDefault) {
        this.m_bUseBeforeImageDefault = bUseBeforeImageDefault;
    }
    
    public boolean usesTTMappingDefault() {
        return this.m_bUseTTMappingDefault;
    }
    
    public void setTTMappingDefault(final boolean bUseTTMappingDefault) {
        this.m_bUseTTMappingDefault = bUseTTMappingDefault;
    }
    
    public boolean usesReturnValue() {
        return this.m_bUseRetVal;
    }
    
    public void setUseReturnValue(final boolean bUseRetVal) {
        this.m_bUseRetVal = bUseRetVal;
    }
    
    public PGParam[] getParameters() {
        return this.m_pParams;
    }
    
    public boolean isTTResultSet() {
        return this.m_bIsTTResultSet;
    }
    
    public void setParameters(final PGParam[] pParams) {
        this.m_pParams = pParams;
    }
    
    public PGParam getParameters(final int n) {
        if (n >= this.m_pParams.length) {
            return null;
        }
        return this.m_pParams[n];
    }
    
    public void setParameters(final int n, final PGParam pgParam) {
        if (n < this.m_pParams.length) {
            this.m_pParams[n] = pgParam;
        }
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String s2) throws SAXException {
        boolean b = false;
        if (s2 != null && s2.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strMethod != null) {
                String string;
                if (b) {
                    string = s2 + ":Name";
                }
                else {
                    string = "Name";
                }
                xmlSerializer.startElement(s, "Name", string, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strMethod.toCharArray(), 0, this.m_strMethod.length());
                xmlSerializer.endElement(s, "Name", string);
            }
            if (this.m_strHelp != null) {
                String string2;
                if (b) {
                    string2 = s2 + ":HelpString";
                }
                else {
                    string2 = "HelpString";
                }
                xmlSerializer.startElement(s, "HelpString", string2, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strHelp.toCharArray(), 0, this.m_strHelp.length());
                xmlSerializer.endElement(s, "HelpString", string2);
            }
            if (this.m_pRetVal != null) {
                final AttributesImpl attributesImpl = new AttributesImpl();
                attributesImpl.addAttribute("", "allowUnknown", "allowUnknown", "CDATA", this.m_pRetVal.allowUnknown() ? "true" : "false");
                attributesImpl.addAttribute("", "ordinal", "ordinal", "CDATA", Integer.toString(this.m_pRetVal.getParamOrdinal()));
                attributesImpl.addAttribute("", "writeXmlBeforeImage", "writeXmlBeforeImage", "CDATA", "false");
                String string3;
                if (b) {
                    string3 = s2 + ":ReturnValue";
                }
                else {
                    string3 = "ReturnValue";
                }
                xmlSerializer.startElement(s, "ReturnValue", string3, (Attributes)attributesImpl);
                this.m_pRetVal.writeXML(xmlSerializer, s, s2);
                xmlSerializer.endElement(s, "ReturnValue", string3);
            }
            for (int i = 0; i < this.m_pParams.length; ++i) {
                final AttributesImpl attributesImpl2 = new AttributesImpl();
                attributesImpl2.addAttribute("", "allowUnknown", "allowUnknown", "CDATA", this.m_pParams[i].allowUnknown() ? "true" : "false");
                attributesImpl2.addAttribute("", "ordinal", "ordinal", "CDATA", Integer.toString(this.m_pParams[i].getParamOrdinal()));
                attributesImpl2.addAttribute("", "writeXmlBeforeImage", "writeXmlBeforeImage", "CDATA", this.m_pParams[i].getWriteBeforeImage() ? "true" : "false");
                String string4;
                if (b) {
                    string4 = s2 + ":Parameter";
                }
                else {
                    string4 = "Parameter";
                }
                xmlSerializer.startElement(s, "Parameter", string4, (Attributes)attributesImpl2);
                this.m_pParams[i].writeXML(xmlSerializer, s, s2);
                xmlSerializer.endElement(s, "Parameter", string4);
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final Vector vector = new Vector<PGDataSetParam>();
        final NamedNodeMap attributes = node.getAttributes();
        final String nodeValue = attributes.getNamedItem("isCustomized").getNodeValue();
        if (nodeValue.equals("true") || nodeValue.equals("1")) {
            this.m_bCustomized = true;
        }
        else {
            this.m_bCustomized = false;
        }
        final String nodeValue2 = attributes.getNamedItem("usesUnknownDefault").getNodeValue();
        if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
            this.m_bUseUnkDefault = true;
        }
        else {
            this.m_bUseUnkDefault = false;
        }
        final Node namedItem = attributes.getNamedItem("usesTTMappingDefault");
        if (namedItem != null) {
            final String nodeValue3 = namedItem.getNodeValue();
            if (nodeValue3.equals("true") || nodeValue3.equals("1")) {
                this.m_bUseTTMappingDefault = true;
            }
            else {
                this.m_bUseTTMappingDefault = false;
            }
        }
        final Node namedItem2 = attributes.getNamedItem("IsTTResultSet");
        if (namedItem2 != null) {
            final String nodeValue4 = namedItem2.getNodeValue();
            if (nodeValue4.equals("true") || nodeValue4.equals("1")) {
                this.m_bIsTTResultSet = true;
            }
            else {
                this.m_bIsTTResultSet = false;
            }
        }
        final Node namedItem3 = attributes.getNamedItem("usesBeforeImageDefault");
        if (namedItem3 != null) {
            final String nodeValue5 = namedItem3.getNodeValue();
            if (nodeValue5.equals("true") || nodeValue5.equals("1")) {
                this.m_bUseBeforeImageDefault = true;
            }
            else {
                this.m_bUseBeforeImageDefault = false;
            }
        }
        final String nodeValue6 = attributes.getNamedItem("useRetVal").getNodeValue();
        if (nodeValue6.equals("true") || nodeValue6.equals("1")) {
            this.m_bUseRetVal = true;
        }
        else {
            this.m_bUseRetVal = false;
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue7 = WsaParser.extractNodeValue(item);
                if (localName.equals("Name")) {
                    this.m_strMethod = nodeValue7;
                }
                else if (localName.equals("HelpString")) {
                    this.m_strHelp = nodeValue7;
                }
                else if (localName.equals("ReturnValue")) {
                    (this.m_pRetVal = new PGParam()).readXML(item);
                }
                else if (localName.equals("Parameter")) {
                    final PGParam obj = new PGParam();
                    obj.readXML(item);
                    if (obj.getParamType() == 36) {
                        final PGDataSetParam obj2 = new PGDataSetParam(obj);
                        obj2.readDataSetXML(item);
                        vector.addElement(obj2);
                    }
                    else {
                        vector.addElement((PGDataSetParam)obj);
                    }
                }
            }
        }
        if (!vector.isEmpty()) {
            vector.copyInto(this.m_pParams = new PGParam[vector.size()]);
        }
    }
    
    static void copyScalarInfo(final PGMethodDetail pgMethodDetail, final PGMethodDetail pgMethodDetail2) {
        pgMethodDetail2.m_strMethod = pgMethodDetail.m_strMethod;
        pgMethodDetail2.m_strODLHelp = pgMethodDetail.m_strODLHelp;
        pgMethodDetail2.m_strHelp = pgMethodDetail.m_strHelp;
        pgMethodDetail2.m_bCustomized = pgMethodDetail.m_bCustomized;
        pgMethodDetail2.m_bUseUnkDefault = pgMethodDetail.m_bUseUnkDefault;
        pgMethodDetail2.m_bUseRetVal = pgMethodDetail.m_bUseRetVal;
        pgMethodDetail2.m_bIsTTResultSet = pgMethodDetail.m_bIsTTResultSet;
        pgMethodDetail2.m_bUseTTMappingDefault = pgMethodDetail.m_bUseTTMappingDefault;
        pgMethodDetail2.m_bUseBeforeImageDefault = pgMethodDetail.m_bUseBeforeImageDefault;
    }
    
    public boolean hasParamType(final int n, final boolean b) {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == n) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasArray(final boolean b) {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].isExtentField()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasDatasetHandleParam() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == 37 && !this.m_pParams[i].getWriteBeforeImage()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasDatasetHandleChangesParam() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == 37 && this.m_pParams[i].getWriteBeforeImage()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasDatasetAttributeFields() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == 36 && ((PGDataSetParam)this.m_pParams[i]).hasDatasetAttributeFields()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasDatasetTtabNamespaceConflict() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == 36 && ((PGDataSetParam)this.m_pParams[i]).hasDatasetTtabNamespaceConflict()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasInputParam() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            final int paramMode = this.m_pParams[i].getParamMode();
            if (paramMode == 1 || paramMode == 3) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasOutputParam() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            final int paramMode = this.m_pParams[i].getParamMode();
            if (paramMode == 2 || paramMode == 3) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasReferenceOnlyParameter() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getReferenceOnly()) {
                return true;
            }
        }
        return false;
    }
    
    public int getProcType() {
        return this.m_nProcType;
    }
    
    public void setProcType(final int nProcType) {
        this.m_nProcType = nProcType;
    }
    
    public void updateDataTypesFromXPXGFile() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            final PGParam pgParam = this.m_pParams[i];
            pgParam.setParamType(Parameter.XPXGTypeToProType(pgParam.getParamType()));
        }
        if (this.m_pRetVal != null) {
            this.m_pRetVal.setParamType(Parameter.XPXGTypeToProType(this.m_pRetVal.getParamType()));
        }
    }
}

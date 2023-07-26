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
import com.progress.open4gl.Parameter;
import com.progress.wsa.open4gl.XMLSerializable;
import java.io.Serializable;

public class PGProcDetail implements IPGDetail, Serializable, XMLSerializable
{
    private static final long serialVersionUID = -353150828421011358L;
    String m_strMethod;
    String m_strODLHelp;
    String m_strHelp;
    boolean m_bUseUnkDefault;
    boolean m_bCustomized;
    PGParam[] m_pParams;
    PGMethod[] m_pInternalProcs;
    PGParam m_pRetVal;
    boolean m_bUseRetVal;
    boolean m_bIsTTResultSet;
    boolean m_bUseTTMappingDefault;
    boolean m_bUseBeforeImageDefault;
    
    public PGProcDetail() {
        this.m_strMethod = null;
        this.m_strODLHelp = null;
        this.m_strHelp = null;
        this.m_bUseUnkDefault = true;
        this.m_bCustomized = false;
        this.m_pParams = new PGParam[0];
        this.m_pInternalProcs = new PGMethod[0];
        this.m_bUseRetVal = false;
        this.m_bIsTTResultSet = true;
        this.m_bUseTTMappingDefault = true;
        this.m_bUseBeforeImageDefault = true;
    }
    
    public PGProcDetail(final PGProcDetail pgProcDetail) {
        this.m_strMethod = pgProcDetail.m_strMethod;
        this.m_strODLHelp = pgProcDetail.m_strODLHelp;
        this.m_strHelp = pgProcDetail.m_strHelp;
        this.m_bCustomized = pgProcDetail.m_bCustomized;
        this.m_bUseUnkDefault = pgProcDetail.m_bUseUnkDefault;
        this.m_bUseRetVal = pgProcDetail.m_bUseRetVal;
        this.m_bUseTTMappingDefault = pgProcDetail.m_bUseTTMappingDefault;
        this.m_bUseBeforeImageDefault = pgProcDetail.m_bUseBeforeImageDefault;
        final int length = pgProcDetail.m_pParams.length;
        this.m_pParams = new PGParam[length];
        for (int i = 0; i < length; ++i) {
            this.m_pParams[i] = new PGParam(pgProcDetail.m_pParams[i]);
        }
        final int length2 = pgProcDetail.m_pInternalProcs.length;
        this.m_pInternalProcs = new PGMethod[length2];
        for (int j = 0; j < length2; ++j) {
            this.m_pInternalProcs[j] = new PGMethod(pgProcDetail.m_pInternalProcs[j]);
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
    
    public void setMethodName(final String strMethod) {
        this.m_strMethod = strMethod;
    }
    
    public boolean isTTResultSet() {
        return this.m_bIsTTResultSet;
    }
    
    public String valMethodName(final String s, final boolean b, final boolean b2, final boolean b3) {
        String s2 = null;
        if (s == null || s.length() == 0) {
            s2 = PGGenInfo.getMsg(8099442454849133699L, new Object[] { PGGenInfo.getResources().getTranString("PG_Name") });
        }
        else if (!PGAppObj.validateName(s, b, b2, b3)) {
            s2 = PGGenInfo.getMsg(8099442454849133701L, new Object[] { PGGenInfo.getResources().getTranString("PG_Name") });
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
    
    public boolean usesUnknownDefault() {
        return this.m_bUseUnkDefault;
    }
    
    public void setUnknownDefault(final boolean bUseUnkDefault) {
        this.m_bUseUnkDefault = bUseUnkDefault;
    }
    
    public boolean usesTTMappingDefault() {
        return this.m_bUseTTMappingDefault;
    }
    
    public void setTTMappingDefault(final boolean bUseTTMappingDefault) {
        this.m_bUseTTMappingDefault = bUseTTMappingDefault;
    }
    
    public boolean usesBeforeImageDefault() {
        return this.m_bUseBeforeImageDefault;
    }
    
    public void setBeforeImageDefault(final boolean bUseBeforeImageDefault) {
        this.m_bUseBeforeImageDefault = bUseBeforeImageDefault;
    }
    
    public boolean isCustomized() {
        return this.m_bCustomized;
    }
    
    public void setCustomized(final boolean bCustomized) {
        this.m_bCustomized = bCustomized;
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
    
    public PGMethod[] getInternalProcs() {
        return this.m_pInternalProcs;
    }
    
    public void setInternalProcs(final PGMethod[] pInternalProcs) {
        this.m_pInternalProcs = pInternalProcs;
    }
    
    public PGMethod getInternalProcs(final int n) {
        if (n >= this.m_pInternalProcs.length) {
            return null;
        }
        return this.m_pInternalProcs[n];
    }
    
    public void setInternalProcs(final int n, final PGMethod pgMethod) {
        if (n < this.m_pInternalProcs.length) {
            this.m_pInternalProcs[n] = pgMethod;
        }
    }
    
    public PGParam getReturnValue() {
        return this.m_pRetVal;
    }
    
    public void setReturnValue(final PGParam pRetVal) {
        this.m_pRetVal = pRetVal;
    }
    
    public boolean hasParamType(final int n, final boolean b) {
        if (!b) {
            for (int i = 0; i < this.m_pParams.length; ++i) {
                if (this.m_pParams[i].getParamType() == n) {
                    return true;
                }
            }
        }
        else {
            for (int length = this.m_pInternalProcs.length, j = 0; j < length; ++j) {
                final boolean hasParamType = this.m_pInternalProcs[j].getMethodDetail().hasParamType(n, b);
                if (hasParamType) {
                    return hasParamType;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetHandleParam(final boolean b) {
        if (!b) {
            for (int i = 0; i < this.m_pParams.length; ++i) {
                if (this.m_pParams[i].getParamType() == 37 && !this.m_pParams[i].getWriteBeforeImage()) {
                    return true;
                }
            }
        }
        else {
            for (int length = this.m_pInternalProcs.length, j = 0; j < length; ++j) {
                final boolean hasDatasetHandleParam = this.m_pInternalProcs[j].getMethodDetail().hasDatasetHandleParam();
                if (hasDatasetHandleParam) {
                    return hasDatasetHandleParam;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetHandleChangesParam(final boolean b) {
        if (!b) {
            for (int i = 0; i < this.m_pParams.length; ++i) {
                if (this.m_pParams[i].getParamType() == 37 && this.m_pParams[i].getWriteBeforeImage()) {
                    return true;
                }
            }
        }
        else {
            for (int length = this.m_pInternalProcs.length, j = 0; j < length; ++j) {
                final boolean hasDatasetHandleChangesParam = this.m_pInternalProcs[j].getMethodDetail().hasDatasetHandleChangesParam();
                if (hasDatasetHandleChangesParam) {
                    return hasDatasetHandleChangesParam;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetAttributeFields(final boolean b) {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == 36 && ((PGDataSetParam)this.m_pParams[i]).hasDatasetAttributeFields()) {
                return true;
            }
        }
        if (b) {
            for (int length = this.m_pInternalProcs.length, j = 0; j < length; ++j) {
                final boolean hasDatasetAttributeFields = this.m_pInternalProcs[j].getMethodDetail().hasDatasetAttributeFields();
                if (hasDatasetAttributeFields) {
                    return hasDatasetAttributeFields;
                }
            }
        }
        return false;
    }
    
    public boolean hasDatasetTtabNamespaceConflict(final boolean b) {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            if (this.m_pParams[i].getParamType() == 36 && ((PGDataSetParam)this.m_pParams[i]).hasDatasetTtabNamespaceConflict()) {
                return true;
            }
        }
        if (b) {
            for (int length = this.m_pInternalProcs.length, j = 0; j < length; ++j) {
                final boolean hasDatasetTtabNamespaceConflict = this.m_pInternalProcs[j].getMethodDetail().hasDatasetTtabNamespaceConflict();
                if (hasDatasetTtabNamespaceConflict) {
                    return hasDatasetTtabNamespaceConflict;
                }
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
    
    public boolean hasArray(final boolean b) {
        if (!b) {
            for (int i = 0; i < this.m_pParams.length; ++i) {
                if (this.m_pParams[i].isExtentField()) {
                    return true;
                }
            }
        }
        else {
            for (int length = this.m_pInternalProcs.length, j = 0; j < length; ++j) {
                final boolean hasArray = this.m_pInternalProcs[j].getMethodDetail().hasArray(b);
                if (hasArray) {
                    return hasArray;
                }
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
    
    public int getProcType() {
        return 1;
    }
    
    public void setProcType(final int n) {
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
    
    public void updateDataTypesFromXPXGFile() {
        for (int i = 0; i < this.m_pParams.length; ++i) {
            final PGParam pgParam = this.m_pParams[i];
            pgParam.setParamType(Parameter.XPXGTypeToProType(pgParam.getParamType()));
        }
        if (this.m_pRetVal != null) {
            this.m_pRetVal.setParamType(Parameter.XPXGTypeToProType(this.m_pRetVal.getParamType()));
        }
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            if (this.m_strMethod != null) {
                String string;
                if (b) {
                    string = str + ":Name";
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
                    string2 = str + ":HelpString";
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
                    string3 = str + ":ReturnValue";
                }
                else {
                    string3 = "ReturnValue";
                }
                xmlSerializer.startElement(s, "ReturnValue", string3, (Attributes)attributesImpl);
                this.m_pRetVal.writeXML(xmlSerializer, s, str);
                xmlSerializer.endElement(s, "ReturnValue", string3);
            }
            for (int i = 0; i < this.m_pParams.length; ++i) {
                final AttributesImpl attributesImpl2 = new AttributesImpl();
                attributesImpl2.addAttribute("", "allowUnknown", "allowUnknown", "CDATA", this.m_pParams[i].allowUnknown() ? "true" : "false");
                attributesImpl2.addAttribute("", "ordinal", "ordinal", "CDATA", Integer.toString(this.m_pParams[i].getParamOrdinal()));
                attributesImpl2.addAttribute("", "writeXmlBeforeImage", "writeXmlBeforeImage", "CDATA", this.m_pParams[i].getWriteBeforeImage() ? "true" : "false");
                String string4;
                if (b) {
                    string4 = str + ":Parameter";
                }
                else {
                    string4 = "Parameter";
                }
                xmlSerializer.startElement(s, "Parameter", string4, (Attributes)attributesImpl2);
                this.m_pParams[i].writeXML(xmlSerializer, s, str);
                xmlSerializer.endElement(s, "Parameter", string4);
            }
            for (int j = 0; j < this.m_pInternalProcs.length; ++j) {
                if (!this.m_pInternalProcs[j].isExcluded()) {
                    final AttributesImpl attributesImpl3 = new AttributesImpl();
                    attributesImpl3.addAttribute("", "isExcluded", "isExcluded", "CDATA", this.m_pInternalProcs[j].isExcluded() ? "true" : "false");
                    attributesImpl3.addAttribute("", "hasBadParams", "hasBadParams", "CDATA", this.m_pInternalProcs[j].hasBadParams() ? "true" : "false");
                    String string5;
                    if (b) {
                        string5 = str + ":InternalProc";
                    }
                    else {
                        string5 = "InternalProc";
                    }
                    xmlSerializer.startElement(s, "InternalProc", string5, (Attributes)attributesImpl3);
                    this.m_pInternalProcs[j].writeXML(xmlSerializer, s, str);
                    xmlSerializer.endElement(s, "InternalProc", string5);
                }
            }
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final Vector vector = new Vector<PGDataSetParam>();
        final Vector vector2 = new Vector<PGMethod>();
        final NamedNodeMap attributes = node.getAttributes();
        final String nodeValue = attributes.getNamedItem("usesUnknownDefault").getNodeValue();
        if (nodeValue.equals("true") || nodeValue.equals("1")) {
            this.m_bUseUnkDefault = true;
        }
        else {
            this.m_bUseUnkDefault = false;
        }
        final String nodeValue2 = attributes.getNamedItem("isCustomized").getNodeValue();
        if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
            this.m_bCustomized = true;
        }
        else {
            this.m_bCustomized = false;
        }
        final String nodeValue3 = attributes.getNamedItem("useRetVal").getNodeValue();
        if (nodeValue3.equals("true") || nodeValue3.equals("1")) {
            this.m_bUseRetVal = true;
        }
        else {
            this.m_bUseRetVal = false;
        }
        final Node namedItem = attributes.getNamedItem("IsTTResultSet");
        if (namedItem != null) {
            final String nodeValue4 = namedItem.getNodeValue();
            if (nodeValue4.equals("true") || nodeValue4.equals("1")) {
                this.m_bIsTTResultSet = true;
            }
            else {
                this.m_bIsTTResultSet = false;
            }
        }
        final Node namedItem2 = attributes.getNamedItem("usesTTMappingDefault");
        if (namedItem2 != null) {
            final String nodeValue5 = namedItem2.getNodeValue();
            if (nodeValue5.equals("true") || nodeValue5.equals("1")) {
                this.m_bUseTTMappingDefault = true;
            }
            else {
                this.m_bUseTTMappingDefault = false;
            }
        }
        final Node namedItem3 = attributes.getNamedItem("usesBeforeImageDefault");
        if (namedItem3 != null) {
            final String nodeValue6 = namedItem3.getNodeValue();
            if (nodeValue6.equals("true") || nodeValue6.equals("1")) {
                this.m_bUseBeforeImageDefault = true;
            }
            else {
                this.m_bUseBeforeImageDefault = false;
            }
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
                else if (localName.equals("InternalProc")) {
                    final PGMethod obj3 = new PGMethod();
                    obj3.readXML(item);
                    vector2.addElement(obj3);
                }
            }
        }
        if (!vector.isEmpty()) {
            vector.copyInto(this.m_pParams = new PGParam[vector.size()]);
        }
        if (!vector2.isEmpty()) {
            vector2.copyInto(this.m_pInternalProcs = new PGMethod[vector2.size()]);
        }
    }
}

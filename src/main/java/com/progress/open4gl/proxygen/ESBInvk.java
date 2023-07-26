// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import com.progress.open4gl.Parameter;
import org.xml.sax.SAXException;
import java.util.Iterator;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.progress.common.ehnlog.AppLogger;
import com.progress.wsa.admin.WsaParser;
import java.util.ArrayList;
import com.progress.wsa.open4gl.XMLSerializableRoot;

public class ESBInvk implements XMLSerializableRoot
{
    private static final String ROOT = "script";
    private static final String XML_TYPE = "scriptType";
    private static final String NAMESPACE = "urn:schemas-progress-com:esboe:0001";
    private static final String PREFIX = "oe";
    private static final int METHOD_TYPE = 0;
    private static final int FUNCTION_TYPE = 1;
    private static final int FILE_TYPE = 2;
    private static final int HANDLE_TYPE = 3;
    public static final int RELEASE_TYPE = 4;
    protected PGProc m_pgProc;
    protected PGMethod m_pgMethod;
    protected ArrayList m_vars;
    protected int m_ipIndex;
    
    public static boolean GenerateESBOEFile(String string, final PGProc pgProc) {
        boolean b = true;
        final ESBInvk esbInvk = new ESBInvk(pgProc);
        final WsaParser wsaParser = new WsaParser(null, null);
        try {
            wsaParser.serializeObject(esbInvk, string, true);
        }
        catch (Exception ex) {
            b = false;
            ex.printStackTrace();
        }
        if (pgProc.isPersistent()) {
            final ESBInvk esbInvk2 = new ESBInvk();
            string = string.substring(0, string.indexOf(".esboe")) + "_release.esboe";
            try {
                wsaParser.serializeObject(esbInvk2, string, true);
            }
            catch (Exception ex2) {
                b = false;
                ex2.printStackTrace();
            }
        }
        return b;
    }
    
    public static boolean GenerateESBOEFile(String string, final ZipOutputStream out, final PGProc pgProc) {
        boolean b = true;
        final ESBInvk esbInvk = new ESBInvk(pgProc);
        final WsaParser wsaParser = new WsaParser(null, null);
        try {
            out.putNextEntry(new ZipEntry(string));
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            wsaParser.serializeObject(esbInvk, outputStreamWriter);
            outputStreamWriter.flush();
            if (pgProc.isPersistent()) {
                final ESBInvk esbInvk2 = new ESBInvk();
                string = string.substring(0, string.indexOf(".esboe")) + "_release.esboe";
                out.putNextEntry(new ZipEntry(string));
                wsaParser.serializeObject(esbInvk2, outputStreamWriter);
                outputStreamWriter.flush();
            }
        }
        catch (Exception ex) {
            b = false;
            ex.printStackTrace();
        }
        return b;
    }
    
    public static boolean GenerateESBOEFile(final String s, final PGProc pgProc, final int n) {
        boolean b = true;
        final ESBInvk esbInvk = new ESBInvk(pgProc, n);
        final WsaParser wsaParser = new WsaParser(null, null);
        try {
            wsaParser.serializeObject(esbInvk, s, true);
        }
        catch (Exception ex) {
            b = false;
            ex.printStackTrace();
        }
        return b;
    }
    
    public static boolean GenerateESBOEFile(final String name, final ZipOutputStream out, final PGProc pgProc, final int n) {
        boolean b = true;
        final ESBInvk esbInvk = new ESBInvk(pgProc, n);
        final WsaParser wsaParser = new WsaParser(null, null);
        try {
            out.putNextEntry(new ZipEntry(name));
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            wsaParser.serializeObject(esbInvk, outputStreamWriter);
            outputStreamWriter.flush();
        }
        catch (Exception ex) {
            b = false;
            ex.printStackTrace();
        }
        return b;
    }
    
    public static String GenerateESBOEXML(final PGProc pgProc, final int n) {
        XMLSerializableRoot xmlSerializableRoot = null;
        switch (n) {
            case 0:
            case 1: {
                xmlSerializableRoot = new ESBInvk(pgProc, 0);
                break;
            }
            case 2:
            case 3: {
                xmlSerializableRoot = new ESBInvk(pgProc);
                break;
            }
            case 4: {
                xmlSerializableRoot = new ESBInvk();
                break;
            }
        }
        final WsaParser wsaParser = new WsaParser(null, null);
        String serializeObject;
        try {
            serializeObject = wsaParser.serializeObject(xmlSerializableRoot, null, true);
        }
        catch (Exception ex) {
            serializeObject = null;
            ex.printStackTrace();
        }
        return serializeObject;
    }
    
    public static PGProc ParseESBOEXML(final String s) {
        int int1 = -1;
        final PGProc pgProc = new PGProc();
        final WsaParser wsaParser = new WsaParser(null, null);
        Document str = null;
        try {
            str = wsaParser.parseStr(s, 0);
        }
        catch (Exception ex) {}
        final NodeList elementsByTagName = str.getDocumentElement().getElementsByTagName("Operation");
        Node item = null;
        if (elementsByTagName.getLength() > 0) {
            item = elementsByTagName.item(0);
        }
        if (null == item) {
            return null;
        }
        final Node namedItem = item.getAttributes().getNamedItem("specialOp");
        if (null != namedItem) {
            namedItem.getNodeValue();
            int1 = Integer.parseInt(namedItem.getNodeValue());
        }
        if (int1 == -1) {
            pgProc.readXML(item);
        }
        return pgProc;
    }
    
    static String ProTypeToSchema(final int n) {
        String s = null;
        switch (n) {
            case 1: {
                s = "xsd:string";
                break;
            }
            case 39: {
                s = "xsd:string";
                break;
            }
            case 2: {
                s = "xsd:date";
                break;
            }
            case 3: {
                s = "xsd:boolean";
                break;
            }
            case 4: {
                s = "xsd:int";
                break;
            }
            case 5: {
                s = "xsd:decimal";
                break;
            }
            case 7: {
                s = "xsd:long";
                break;
            }
            case 8:
            case 11:
            case 13: {
                s = "xsd:string";
                break;
            }
            case 14: {
                s = "xsd:long";
                break;
            }
            case 15:
            case 17:
            case 36:
            case 37: {
                s = "xsd:anyType";
                break;
            }
            case 34: {
                s = "xsd:dateTime";
                break;
            }
            case 40: {
                s = "xsd:dateTime";
                break;
            }
            case 41: {
                s = "xsd:long";
                break;
            }
            case 10: {
                s = "xsd:long";
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return s;
    }
    
    static String ProArrayTypeToSchema(final int n) {
        String s = null;
        switch (n) {
            case 1: {
                s = "S1:stringArrayType";
                break;
            }
            case 39: {
                s = "S1:stringArrayType";
                break;
            }
            case 2: {
                s = "S1:dateArrayType";
                break;
            }
            case 3: {
                s = "S1:booleanArrayType";
                break;
            }
            case 4: {
                s = "S1:intArrayType";
                break;
            }
            case 5: {
                s = "S1:decimalArrayType";
                break;
            }
            case 7: {
                s = "S1:longArrayType";
                break;
            }
            case 8: {
                s = "S1:base64BinaryArrayType";
                break;
            }
            case 11: {
                s = "S1:base64BinaryArrayType";
                break;
            }
            case 13: {
                s = "S1:base64BinaryArrayType";
                break;
            }
            case 14: {
                s = "S1:longArrayType";
                break;
            }
            case 34: {
                s = "S1:dateTimeArrayType";
                break;
            }
            case 40: {
                s = "S1:dateTimeArrayType";
                break;
            }
            case 41: {
                s = "S1:longArrayType";
                break;
            }
            case 10: {
                s = "S1:longArrayType";
                break;
            }
            default: {
                s = "";
                break;
            }
        }
        return s;
    }
    
    public ESBInvk() {
        this.m_pgProc = null;
        this.m_pgMethod = null;
        this.m_vars = null;
        this.m_ipIndex = -1;
        this.m_pgProc = null;
        this.m_pgMethod = null;
        this.m_vars = new ArrayList();
        this.initialize();
    }
    
    public ESBInvk(final PGProc pgProc) {
        this.m_pgProc = null;
        this.m_pgMethod = null;
        this.m_vars = null;
        this.m_ipIndex = -1;
        this.m_pgProc = pgProc;
        this.m_pgMethod = null;
        this.m_vars = new ArrayList();
        this.initialize();
    }
    
    public ESBInvk(final PGProc pgProc, final int ipIndex) {
        this.m_pgProc = null;
        this.m_pgMethod = null;
        this.m_vars = null;
        this.m_ipIndex = -1;
        this.m_pgProc = pgProc;
        this.m_pgMethod = pgProc.getProcDetail().getInternalProcs(ipIndex);
        this.m_ipIndex = ipIndex;
        this.m_vars = new ArrayList();
        this.initialize();
    }
    
    protected void initialize() {
        if (null != this.m_pgProc) {
            PGParam[] array;
            if (null == this.m_pgMethod) {
                array = this.m_pgProc.getProcDetail().getParameters();
            }
            else {
                array = this.m_pgMethod.getMethodDetail().getParameters();
            }
            for (int i = 0; i < array.length; ++i) {
                final int paramMode = array[i].getParamMode();
                if (paramMode == 3) {
                    this.m_vars.add(new ParamMap(array[i], 1));
                    this.m_vars.add(new ParamMap(array[i], 2));
                }
                else {
                    this.m_vars.add(new ParamMap(array[i], paramMode));
                }
            }
            if (null == this.m_pgMethod) {
                if (this.m_pgProc.getProcDetail().usesReturnValue()) {
                    this.m_vars.add(new ParamMap(this.m_pgProc.getProcDetail().getReturnValue(), 2));
                }
            }
            else if (this.m_pgMethod.getProcType() == 2 || this.m_pgMethod.getMethodDetail().usesReturnValue()) {
                this.m_vars.add(new ParamMap(this.m_pgMethod.getMethodDetail().getReturnValue(), 2));
            }
            if (this.m_pgProc.isPersistent()) {
                if (null == this.m_pgMethod) {
                    this.m_vars.add(new ParamMap(null, 2));
                }
                else {
                    this.m_vars.add(new ParamMap(null, 1));
                }
            }
        }
        else {
            this.m_vars.add(new ParamMap(null, 1));
        }
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String s2) throws SAXException {
        xmlSerializer.startElement("", "nameSpaces", "nameSpaces", (Attributes)null);
        final AttributesImpl attributesImpl = new AttributesImpl();
        attributesImpl.addAttribute("", "prefix", "prefix", "CDATA", "xsd");
        attributesImpl.addAttribute("", "uri", "uri", "CDATA", "http://www.w3.org/2001/XMLSchema");
        xmlSerializer.startElement("", "ns", "ns", (Attributes)attributesImpl);
        xmlSerializer.endElement("", "ns", "ns");
        final AttributesImpl attributesImpl2 = new AttributesImpl();
        attributesImpl2.addAttribute("", "prefix", "prefix", "CDATA", "S1");
        attributesImpl2.addAttribute("", "uri", "uri", "CDATA", "urn:schemas-progress-com:ESBArrayTypes:0001");
        xmlSerializer.startElement("", "ns", "ns", (Attributes)attributesImpl2);
        xmlSerializer.endElement("", "ns", "ns");
        xmlSerializer.endElement("", "nameSpaces", "nameSpaces");
        xmlSerializer.startElement("", "invocationAction", "invocationAction", (Attributes)null);
        xmlSerializer.startElement("", "variables", "variables", (Attributes)null);
        for (final ParamMap paramMap : this.m_vars) {
            final AttributesImpl attributesImpl3 = new AttributesImpl();
            attributesImpl3.addAttribute("", "name", "name", "CDATA", paramMap.getVariableName());
            attributesImpl3.addAttribute("", "type", "type", "CDATA", paramMap.getDispType());
            attributesImpl3.addAttribute("", "direction", "direction", "CDATA", paramMap.getDirection());
            attributesImpl3.addAttribute("", "required", "required", "CDATA", "false");
            xmlSerializer.startElement("", "value", "value", (Attributes)attributesImpl3);
            xmlSerializer.endElement("", "value", "value");
        }
        xmlSerializer.endElement("", "variables", "variables");
        xmlSerializer.startElement("", "parametermap", "parametermap", (Attributes)null);
        for (final ParamMap paramMap2 : this.m_vars) {
            final PGParam pgParam = paramMap2.getPGParam();
            if (null != pgParam) {
                final String variableName = paramMap2.getVariableName();
                final AttributesImpl attributesImpl4 = new AttributesImpl();
                attributesImpl4.addAttribute("", "parameterName", "parameterName", "CDATA", pgParam.getParamName());
                xmlSerializer.startElement("", "parameter", "parameter", (Attributes)attributesImpl4);
                xmlSerializer.startElement("", "variablename", "variablename", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(variableName.toCharArray(), 0, variableName.length());
                xmlSerializer.endElement("", "variablename", "variablename");
                xmlSerializer.endElement("", "parameter", "parameter");
            }
            else {
                final String variableName2 = paramMap2.getVariableName();
                final AttributesImpl attributesImpl5 = new AttributesImpl();
                attributesImpl5.addAttribute("", "parameterName", "parameterName", "CDATA", variableName2);
                xmlSerializer.startElement("", "parameter", "parameter", (Attributes)attributesImpl5);
                xmlSerializer.startElement("", "variablename", "variablename", (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(variableName2.toCharArray(), 0, variableName2.length());
                xmlSerializer.endElement("", "variablename", "variablename");
                xmlSerializer.endElement("", "parameter", "parameter");
            }
        }
        xmlSerializer.endElement("", "parametermap", "parametermap");
        xmlSerializer.endElement("", "invocationAction", "invocationAction");
        final String s3 = "wsad";
        final String s4 = "urn:schemas-progress-com:WSAD:0009";
        ((BaseMarkupSerializer)xmlSerializer).startPrefixMapping(s3, s4);
        final AttributesImpl attributesImpl6 = new AttributesImpl();
        attributesImpl6.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi:type", "CDATA", "oe:invocationType");
        xmlSerializer.startElement("", "scriptCommand", "scriptCommand", (Attributes)attributesImpl6);
        xmlSerializer.startElement("", "command", "command", (Attributes)null);
        if (null != this.m_pgProc) {
            final AttributesImpl attributesImpl7 = new AttributesImpl();
            attributesImpl7.addAttribute("", "isPersisten", "isPersistent", "CDATA", this.m_pgProc.isPersistent() ? "true" : "false");
            attributesImpl7.addAttribute("", "useFullName", "useFullName", "CDATA", "false");
            attributesImpl7.addAttribute("", "ipIndex", "ipIndex", "CDATA", Integer.toString(this.m_ipIndex));
            attributesImpl7.addAttribute("", "executionMode", "executionMode", "CDATA", Integer.toString(this.m_pgProc.getExecutionMode()));
            xmlSerializer.startElement("", "Operation", "Operation", (Attributes)attributesImpl7);
            this.m_pgProc.writeXML(xmlSerializer, s4, s3);
            xmlSerializer.endElement("", "Operation", "Operation");
        }
        else {
            final AttributesImpl attributesImpl8 = new AttributesImpl();
            attributesImpl8.addAttribute("", "isPersisten", "isPersistent", "CDATA", "true");
            attributesImpl8.addAttribute("", "useFullName", "useFullName", "CDATA", "false");
            attributesImpl8.addAttribute("", "specialOp", "specialOp", "CDATA", "2");
            xmlSerializer.startElement("", "Operation", "Operation", (Attributes)attributesImpl8);
            xmlSerializer.endElement("", "Operation", "Operation");
        }
        xmlSerializer.endElement("", "command", "command");
        xmlSerializer.startElement("", "parameterList", "parameterList", (Attributes)null);
        if (null != this.m_pgProc) {
            PGParam[] array;
            if (null == this.m_pgMethod) {
                array = this.m_pgProc.getProcDetail().getParameters();
            }
            else {
                array = this.m_pgMethod.getMethodDetail().getParameters();
            }
            for (int i = 0; i < array.length; ++i) {
                final int paramMode = array[i].getParamMode();
                String value;
                String value2;
                if (array[i].isExtentField()) {
                    value = "xsd:anyType";
                    value2 = ProArrayTypeToSchema(array[i].getParamType());
                }
                else {
                    value = (value2 = ProTypeToSchema(array[i].getParamType()));
                }
                final AttributesImpl attributesImpl9 = new AttributesImpl();
                switch (paramMode) {
                    case 1: {
                        attributesImpl9.addAttribute("", "direction", "direction", "CDATA", "IN");
                        break;
                    }
                    case 3: {
                        attributesImpl9.addAttribute("", "direction", "direction", "CDATA", "INOUT");
                        break;
                    }
                    case 2: {
                        attributesImpl9.addAttribute("", "direction", "direction", "CDATA", "OUT");
                        break;
                    }
                }
                attributesImpl9.addAttribute("", "name", "name", "CDATA", array[i].getParamName());
                attributesImpl9.addAttribute("", "displayType", "displayType", "CDATA", value2);
                attributesImpl9.addAttribute("", "baseType", "baseType", "CDATA", value);
                xmlSerializer.startElement("", "parameter", "parameter", (Attributes)attributesImpl9);
                xmlSerializer.endElement("", "parameter", "parameter");
            }
            if (null == this.m_pgMethod) {
                if (this.m_pgProc.getProcDetail().usesReturnValue()) {
                    final PGParam returnValue = this.m_pgProc.getProcDetail().getReturnValue();
                    final String proTypeToSchema = ProTypeToSchema(returnValue.getParamType());
                    final AttributesImpl attributesImpl10 = new AttributesImpl();
                    attributesImpl10.addAttribute("", "direction", "direction", "CDATA", "OUT");
                    attributesImpl10.addAttribute("", "name", "name", "CDATA", returnValue.getParamName());
                    attributesImpl10.addAttribute("", "displayType", "displayType", "CDATA", proTypeToSchema);
                    attributesImpl10.addAttribute("", "baseType", "baseType", "CDATA", proTypeToSchema);
                    xmlSerializer.startElement("", "parameter", "parameter", (Attributes)attributesImpl10);
                    xmlSerializer.endElement("", "parameter", "parameter");
                }
            }
            else if (this.m_pgMethod.getProcType() == 2 || this.m_pgMethod.getMethodDetail().usesReturnValue()) {
                final PGParam returnValue2 = this.m_pgMethod.getMethodDetail().getReturnValue();
                final String proTypeToSchema2 = ProTypeToSchema(returnValue2.getParamType());
                final AttributesImpl attributesImpl11 = new AttributesImpl();
                attributesImpl11.addAttribute("", "direction", "direction", "CDATA", "OUT");
                attributesImpl11.addAttribute("", "name", "name", "CDATA", returnValue2.getParamName());
                attributesImpl11.addAttribute("", "displayType", "displayType", "CDATA", proTypeToSchema2);
                attributesImpl11.addAttribute("", "baseType", "baseType", "CDATA", proTypeToSchema2);
                xmlSerializer.startElement("", "parameter", "parameter", (Attributes)attributesImpl11);
                xmlSerializer.endElement("", "parameter", "parameter");
            }
            if (this.m_pgProc.isPersistent()) {
                final String s5 = "xsd:string";
                final AttributesImpl attributesImpl12 = new AttributesImpl();
                if (null == this.m_pgMethod) {
                    attributesImpl12.addAttribute("", "direction", "direction", "CDATA", "OUT");
                }
                else {
                    attributesImpl12.addAttribute("", "direction", "direction", "CDATA", "IN");
                }
                attributesImpl12.addAttribute("", "name", "name", "CDATA", "_procid");
                attributesImpl12.addAttribute("", "displayType", "displayType", "CDATA", s5);
                attributesImpl12.addAttribute("", "baseType", "baseType", "CDATA", s5);
                xmlSerializer.startElement("", "parameter", "parameter", (Attributes)attributesImpl12);
                xmlSerializer.endElement("", "parameter", "parameter");
            }
        }
        else {
            final String s6 = "xsd:string";
            final AttributesImpl attributesImpl13 = new AttributesImpl();
            attributesImpl13.addAttribute("", "direction", "direction", "CDATA", "IN");
            attributesImpl13.addAttribute("", "name", "name", "CDATA", "_procid");
            attributesImpl13.addAttribute("", "displayType", "displayType", "CDATA", s6);
            attributesImpl13.addAttribute("", "baseType", "baseType", "CDATA", s6);
            xmlSerializer.startElement("", "parameter", "parameter", (Attributes)attributesImpl13);
            xmlSerializer.endElement("", "parameter", "parameter");
        }
        xmlSerializer.endElement("", "parameterList", "parameterList");
        xmlSerializer.endElement("", "scriptCommand", "scriptCommand");
    }
    
    public void readXML(final Node node) {
    }
    
    public String getPrefix() {
        return "oe";
    }
    
    public void setPrefix(final String s) {
    }
    
    public String getRoot() {
        return "script";
    }
    
    public void setRoot(final String s) {
    }
    
    public String getXMLType() {
        return "scriptType";
    }
    
    public void setXMLType(final String s) {
    }
    
    public void setSchemaLocation(final String s) {
    }
    
    public String getSchemaLocation() {
        return null;
    }
    
    public void setTargetNamespace(final String s) {
    }
    
    public String getTargetNamespace() {
        return "urn:schemas-progress-com:esboe:0001";
    }
    
    class ParamMap
    {
        protected PGParam m_pgParam;
        protected String m_varName;
        protected String m_baseType;
        protected String m_dispType;
        protected String m_dir;
        
        public ParamMap(final PGParam pgParam, final int n) {
            this.m_pgParam = null;
            this.m_varName = null;
            this.m_baseType = null;
            this.m_dispType = null;
            this.m_dir = null;
            this.m_pgParam = pgParam;
            if (null != this.m_pgParam) {
                this.initialize(n);
            }
            else {
                this.m_varName = "_procid";
                this.m_baseType = "xsd:string";
                this.m_dispType = this.m_baseType;
                if (n == 1) {
                    this.m_dir = "IN";
                }
                else {
                    this.m_dir = "OUT";
                }
            }
        }
        
        public PGParam getPGParam() {
            return this.m_pgParam;
        }
        
        public String getVariableName() {
            return this.m_varName;
        }
        
        public String getBaseType() {
            return this.m_baseType;
        }
        
        public String getDispType() {
            return this.m_dispType;
        }
        
        public String getDirection() {
            return this.m_dir;
        }
        
        protected void initialize(final int n) {
            this.m_baseType = ESBInvk.ProTypeToSchema(this.m_pgParam.getParamType());
            final StringBuffer sb = new StringBuffer();
            sb.append(this.m_pgParam.getParamName());
            sb.append('_');
            sb.append(Parameter.proToName(this.m_pgParam.getParamType()));
            sb.append('_');
            if (this.m_pgParam.isExtentField()) {
                sb.append("ARRAY_");
                this.m_dispType = ESBInvk.ProArrayTypeToSchema(this.m_pgParam.getParamType());
            }
            else {
                this.m_dispType = this.m_baseType;
            }
            if (n == 1) {
                this.m_dir = "IN";
            }
            else {
                this.m_dir = "OUT";
            }
            sb.append(this.m_dir);
            this.m_varName = sb.toString();
        }
    }
}

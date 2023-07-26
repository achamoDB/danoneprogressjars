// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.apache.xerces.parsers.AbstractDOMParser;
import org.xml.sax.SAXParseException;
import java.io.File;
import org.apache.xerces.impl.Version;
import org.xml.sax.ErrorHandler;
import java.io.Writer;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import org.apache.xml.serialize.XMLSerializer;
import java.io.StringWriter;
import org.apache.xml.serialize.OutputFormat;
import com.progress.wsa.open4gl.XMLSerializableRoot;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import org.w3c.dom.Document;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXNotRecognizedException;
import com.progress.wsa.WsaSOAPEngineContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.progress.common.ehnlog.AppLogger;
import org.apache.xerces.parsers.DOMParser;

public class WsaParser
{
    protected DOMParser m_parser;
    protected WSAErrorHandler m_errHandler;
    protected String m_installDir;
    protected String m_instancePath;
    protected AppLogger m_log;
    protected boolean m_useLog;
    protected boolean m_validation;
    protected boolean m_validationPossible;
    protected boolean m_validationBug;
    protected String m_schemaLocation;
    
    public static String encodeSchemaLocation(String s) {
        int beginIndex = 0;
        int i = s.indexOf(32);
        if (i > -1) {
            final StringBuffer sb = new StringBuffer("file:///");
            while (i > -1) {
                sb.append(s.substring(beginIndex, i));
                sb.append("%20");
                beginIndex = ++i;
                i = s.indexOf(32, beginIndex);
            }
            sb.append(s.substring(beginIndex));
            s = sb.toString();
        }
        int beginIndex2 = 0;
        int j = s.indexOf(92);
        if (j > -1) {
            final StringBuffer sb2 = new StringBuffer();
            while (j > -1) {
                sb2.append(s.substring(beginIndex2, j));
                sb2.append("/");
                beginIndex2 = ++j;
                j = s.indexOf(92, beginIndex2);
            }
            sb2.append(s.substring(beginIndex2));
            s = sb2.toString();
        }
        return s;
    }
    
    public static String encodeFilePath(String s) {
        int beginIndex = 0;
        int i = s.indexOf(32);
        if (i > -1) {
            final StringBuffer sb = new StringBuffer("file:///");
            while (i > -1) {
                sb.append(s.substring(beginIndex, i));
                sb.append("%20");
                beginIndex = ++i;
                i = s.indexOf(32, beginIndex);
            }
            sb.append(s.substring(beginIndex));
            s = sb.toString();
        }
        int beginIndex2 = 0;
        int j = s.indexOf(92);
        if (j > -1) {
            final StringBuffer sb2 = new StringBuffer();
            while (j > -1) {
                sb2.append(s.substring(beginIndex2, j));
                sb2.append("/");
                beginIndex2 = ++j;
                j = s.indexOf(92, beginIndex2);
            }
            sb2.append(s.substring(beginIndex2));
            s = sb2.toString();
        }
        return s;
    }
    
    public static String extractNodeValue(final Node node) {
        String nodeValue = "";
        if (node.getNodeType() == 1 && node.hasChildNodes()) {
            final Node firstChild = node.getFirstChild();
            if (firstChild.getNodeType() == 3) {
                nodeValue = firstChild.getNodeValue();
            }
        }
        else {
            final NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                final Node namedItemNS = attributes.getNamedItemNS("http://www.w3.org/2001/XMLSchema-instance", "nil");
                if (namedItemNS != null) {
                    final String nodeValue2 = namedItemNS.getNodeValue();
                    if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
                        nodeValue = null;
                    }
                }
            }
        }
        return nodeValue;
    }
    
    public WsaParser(final WsaSOAPEngineContext wsaSOAPEngineContext) {
        this.m_parser = null;
        this.m_errHandler = null;
        this.m_installDir = null;
        this.m_instancePath = null;
        this.m_log = null;
        this.m_useLog = false;
        this.m_validation = true;
        this.m_validationPossible = false;
        this.m_validationBug = false;
        this.m_schemaLocation = null;
        this.m_installDir = (String)wsaSOAPEngineContext.get("psc.wsa.install.dir");
        if (this.m_installDir != null) {
            int length = this.m_installDir.length();
            final char char1 = this.m_installDir.charAt(--length);
            if (char1 != '/' && char1 != '\\') {
                this.m_installDir += "/";
            }
        }
        if (!((String)wsaSOAPEngineContext.get("psc.wsa.engine.type")).equals("Container")) {
            this.m_instancePath = wsaSOAPEngineContext.getRealPath("/");
        }
        this.m_log = (AppLogger)wsaSOAPEngineContext.get("psc.wsa.log");
        this.m_useLog = true;
    }
    
    public WsaParser(final String installDir, final AppLogger log) {
        this.m_parser = null;
        this.m_errHandler = null;
        this.m_installDir = null;
        this.m_instancePath = null;
        this.m_log = null;
        this.m_useLog = false;
        this.m_validation = true;
        this.m_validationPossible = false;
        this.m_validationBug = false;
        this.m_schemaLocation = null;
        this.m_installDir = installDir;
        if (this.m_installDir != null) {
            int length = this.m_installDir.length();
            final char char1 = this.m_installDir.charAt(--length);
            if (char1 != '/' && char1 != '\\') {
                this.m_installDir += "/";
            }
        }
        if (log != null) {
            this.m_log = log;
            this.m_useLog = true;
        }
    }
    
    public boolean getValidation() {
        return this.m_validation;
    }
    
    public void setValidation(final boolean b) {
        if (b && this.m_validationPossible) {
            try {
                this.m_parser.setFeature("http://xml.org/sax/features/validation", true);
                this.m_parser.setFeature("http://apache.org/xml/features/validation/schema", true);
            }
            catch (SAXNotRecognizedException ex) {}
            catch (SAXNotSupportedException ex2) {}
            this.m_validation = true;
        }
        else {
            try {
                this.m_parser.setFeature("http://xml.org/sax/features/validation", false);
                this.m_parser.setFeature("http://apache.org/xml/features/validation/schema", false);
            }
            catch (SAXNotRecognizedException ex3) {}
            catch (SAXNotSupportedException ex4) {}
            this.m_validation = false;
        }
    }
    
    public synchronized Document parseStr(final String s, final int subSystem) throws IOException {
        if (this.m_parser == null) {
            this.initializeParser();
        }
        else if (this.m_validationBug) {
            try {
                this.m_parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", (Object)this.m_schemaLocation);
            }
            catch (Exception ex3) {}
        }
        this.m_errHandler.clearErrors();
        this.m_errHandler.setSubSystem(subSystem);
        final InputSource inputSource = new InputSource(new StringReader(s));
        try {
            this.m_parser.parse(inputSource);
        }
        catch (SAXException ex) {
            if (this.m_useLog) {
                this.m_log.logError(8607504787811871213L, new Object[] { ex.getMessage() });
            }
            return null;
        }
        catch (IOException ex2) {
            throw ex2;
        }
        if (this.m_errHandler.isFatal() || this.m_errHandler.getErrors() > 0) {
            return null;
        }
        return ((AbstractDOMParser)this.m_parser).getDocument();
    }
    
    public synchronized Document parseFile(final String s, final int subSystem) throws IOException {
        if (this.m_parser == null) {
            this.initializeParser();
        }
        else if (this.m_validationBug) {
            try {
                this.m_parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", (Object)this.m_schemaLocation);
            }
            catch (Exception ex3) {}
        }
        this.m_errHandler.clearErrors();
        this.m_errHandler.setSubSystem(subSystem);
        try {
            this.m_parser.parse(encodeFilePath(s));
        }
        catch (SAXException ex) {
            if (this.m_useLog) {
                this.m_log.logError(8607504787811871214L, new Object[] { s, ex.getMessage() });
            }
            return null;
        }
        catch (IOException ex2) {
            if (this.m_useLog) {
                this.m_log.logError(8607504787811871214L, new Object[] { s, ex2.getMessage() });
            }
            throw ex2;
        }
        if (this.m_errHandler.isFatal() || this.m_errHandler.getErrors() > 0) {
            if (this.m_useLog && this.m_log.ifLogVerbose(this.m_log.logGetEntrytypeBit(subSystem), subSystem)) {
                this.m_log.logVerbose(subSystem, "isFatal: %s - getErrors: %d", new Object[] { new Boolean(this.m_errHandler.isFatal()).toString(), new Integer(this.m_errHandler.getErrors()) });
            }
            return null;
        }
        return ((AbstractDOMParser)this.m_parser).getDocument();
    }
    
    public synchronized Document parseFileDigest(final String name, final int subSystem, final MessageDigest digest) throws IOException {
        if (this.m_parser == null) {
            this.initializeParser();
        }
        else if (this.m_validationBug) {
            try {
                this.m_parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", (Object)this.m_schemaLocation);
            }
            catch (Exception ex5) {}
        }
        this.m_errHandler.clearErrors();
        this.m_errHandler.setSubSystem(subSystem);
        DigestInputStream byteStream;
        try {
            byteStream = new DigestInputStream(new FileInputStream(name), digest);
        }
        catch (FileNotFoundException ex) {
            if (this.m_useLog) {
                this.m_log.logError(8607504787811871214L, new Object[] { name, ex.getMessage() });
            }
            return null;
        }
        catch (SecurityException ex2) {
            if (this.m_useLog) {
                this.m_log.logError(8607504787811871214L, new Object[] { name, ex2.getMessage() });
            }
            return null;
        }
        try {
            this.m_parser.parse(new InputSource(byteStream));
        }
        catch (SAXException ex3) {
            if (this.m_useLog) {
                this.m_log.logError(8607504787811871214L, new Object[] { name, ex3.getMessage() });
            }
            return null;
        }
        catch (IOException ex4) {
            throw ex4;
        }
        if (this.m_errHandler.isFatal() || this.m_errHandler.getErrors() > 0) {
            return null;
        }
        return ((AbstractDOMParser)this.m_parser).getDocument();
    }
    
    public String serializeObject(final XMLSerializableRoot xmlSerializableRoot, final String name) throws IOException {
        String string = null;
        boolean b = true;
        final OutputFormat outputFormat = new OutputFormat();
        Writer writer;
        XMLSerializer xmlSerializer;
        if (name == null || name.length() == 0) {
            writer = new StringWriter();
            b = false;
            outputFormat.setOmitXMLDeclaration(true);
            xmlSerializer = new XMLSerializer(writer, outputFormat);
        }
        else {
            try {
                writer = new OutputStreamWriter(new FileOutputStream(name), "UTF-8");
                outputFormat.setIndenting(true);
                outputFormat.setLineSeparator(System.getProperty("line.separator"));
                xmlSerializer = new XMLSerializer(writer, outputFormat);
            }
            catch (IOException ex) {
                throw ex;
            }
        }
        try {
            AttributesImpl attributesImpl = null;
            ((BaseMarkupSerializer)xmlSerializer).startDocument();
            ((BaseMarkupSerializer)xmlSerializer).startPrefixMapping("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            final String prefix = xmlSerializableRoot.getPrefix();
            final String targetNamespace = xmlSerializableRoot.getTargetNamespace();
            ((BaseMarkupSerializer)xmlSerializer).startPrefixMapping(prefix, xmlSerializableRoot.getTargetNamespace());
            final String schemaLocation = xmlSerializableRoot.getSchemaLocation();
            if (schemaLocation != null && schemaLocation.length() > 0) {
                attributesImpl = new AttributesImpl();
                attributesImpl.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "xsi:schemaLocation", "CDATA", targetNamespace + " " + schemaLocation);
            }
            final String root = xmlSerializableRoot.getRoot();
            String string2;
            if (prefix != null && prefix.length() > 0) {
                string2 = prefix + ":" + root;
            }
            else {
                string2 = root;
            }
            xmlSerializer.startElement(targetNamespace, root, string2, (Attributes)attributesImpl);
            xmlSerializableRoot.writeXML(xmlSerializer, targetNamespace, prefix);
            xmlSerializer.endElement(targetNamespace, root, string2);
            ((BaseMarkupSerializer)xmlSerializer).endDocument();
        }
        catch (SAXException ex2) {
            ex2.printStackTrace();
        }
        if (b) {
            writer.flush();
            writer.close();
        }
        else {
            writer.close();
            string = writer.toString();
        }
        return string;
    }
    
    public String serializeObject(final XMLSerializableRoot xmlSerializableRoot, final String name, final boolean b) throws IOException {
        String string = null;
        boolean b2 = true;
        final OutputFormat outputFormat = new OutputFormat();
        Writer writer;
        if (name == null || name.length() == 0) {
            writer = new StringWriter();
            b2 = false;
            outputFormat.setOmitXMLDeclaration(true);
        }
        else {
            writer = new OutputStreamWriter(new FileOutputStream(name), "UTF-8");
        }
        if (b) {
            outputFormat.setIndent(2);
            outputFormat.setLineWidth(0);
            outputFormat.setLineSeparator(System.getProperty("line.separator"));
        }
        XMLSerializer xmlSerializer;
        if (b2) {
            xmlSerializer = new XMLSerializer(writer, outputFormat);
        }
        else {
            xmlSerializer = new XMLSerializer((Writer)writer, outputFormat);
        }
        try {
            AttributesImpl attributesImpl = null;
            ((BaseMarkupSerializer)xmlSerializer).startDocument();
            ((BaseMarkupSerializer)xmlSerializer).startPrefixMapping("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            final String prefix = xmlSerializableRoot.getPrefix();
            final String targetNamespace = xmlSerializableRoot.getTargetNamespace();
            ((BaseMarkupSerializer)xmlSerializer).startPrefixMapping(prefix, xmlSerializableRoot.getTargetNamespace());
            final String schemaLocation = xmlSerializableRoot.getSchemaLocation();
            if (schemaLocation != null && schemaLocation.length() > 0) {
                attributesImpl = new AttributesImpl();
                attributesImpl.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", "xsi:schemaLocation", "CDATA", targetNamespace + " " + schemaLocation);
            }
            final String root = xmlSerializableRoot.getRoot();
            String string2;
            if (prefix != null && prefix.length() > 0) {
                string2 = prefix + ":" + root;
            }
            else {
                string2 = root;
            }
            xmlSerializer.startElement(targetNamespace, root, string2, (Attributes)attributesImpl);
            xmlSerializableRoot.writeXML(xmlSerializer, targetNamespace, prefix);
            xmlSerializer.endElement(targetNamespace, root, string2);
            ((BaseMarkupSerializer)xmlSerializer).endDocument();
        }
        catch (SAXException ex) {
            ex.printStackTrace();
        }
        if (b2) {
            writer.flush();
            writer.close();
        }
        else {
            writer.close();
            string = ((StringWriter)writer).toString();
        }
        return string;
    }
    
    public void serializeObject(final XMLSerializableRoot xmlSerializableRoot, final Writer writer) {
        final OutputFormat outputFormat = new OutputFormat();
        outputFormat.setOmitXMLDeclaration(true);
        final XMLSerializer xmlSerializer = new XMLSerializer(writer, outputFormat);
        final String prefix = xmlSerializableRoot.getPrefix();
        final String targetNamespace = xmlSerializableRoot.getTargetNamespace();
        try {
            ((BaseMarkupSerializer)xmlSerializer).startDocument();
            xmlSerializableRoot.writeXML(xmlSerializer, targetNamespace, prefix);
            ((BaseMarkupSerializer)xmlSerializer).endDocument();
        }
        catch (SAXException ex) {
            ex.printStackTrace();
        }
    }
    
    private void initializeParser() {
        this.m_parser = new DOMParser();
        this.m_errHandler = new WSAErrorHandler();
        this.m_parser.setErrorHandler((ErrorHandler)this.m_errHandler);
        try {
            if (Version.getVersion().indexOf("2.6.") > -1) {
                this.m_validationBug = true;
            }
        }
        catch (Throwable t) {
            this.m_validationBug = false;
        }
        try {
            this.m_parser.setFeature("http://xml.org/sax/features/namespaces", true);
            this.m_parser.setFeature("http://xml.org/sax/features/validation", true);
            this.m_parser.setFeature("http://apache.org/xml/features/validation/schema", true);
            StringBuffer sb = null;
            final String schema = this.findSchema("wsad0009.xsd");
            if (schema != null) {
                sb = new StringBuffer("urn:schemas-progress-com:WSAD:0009");
                sb.append(" ");
                sb.append(schema);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0009.xsd" });
            }
            final String schema2 = this.findSchema("wsad0008.xsd");
            if (schema2 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0008");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0008");
                }
                sb.append(" ");
                sb.append(schema2);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema2 });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0008.xsd" });
            }
            final String schema3 = this.findSchema("wsad0007.xsd");
            if (schema3 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0007");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0007");
                }
                sb.append(" ");
                sb.append(schema3);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema3 });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0007.xsd" });
            }
            final String schema4 = this.findSchema("wsad0006.xsd");
            if (schema4 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0006");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0006");
                }
                sb.append(" ");
                sb.append(schema4);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema4 });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0006.xsd" });
            }
            final String schema5 = this.findSchema("wsad0005.xsd");
            if (schema5 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0005");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0005");
                }
                sb.append(" ");
                sb.append(schema5);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema5 });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0005.xsd" });
            }
            final String schema6 = this.findSchema("wsad0004.xsd");
            if (schema6 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0004");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0004");
                }
                sb.append(" ");
                sb.append(schema6);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema6 });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0004.xsd" });
            }
            final String schema7 = this.findSchema("wsad0003.xsd");
            if (schema7 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0003");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0003");
                }
                sb.append(" ");
                sb.append(schema7);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema7 });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0009.xsd" });
            }
            final String schema8 = this.findSchema("wsad0002.xsd");
            if (schema8 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0002");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0002");
                }
                sb.append(" ");
                sb.append(schema8);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema8 });
                }
            }
            else if (this.m_useLog) {
                this.m_log.logError(8607504787811871217L, new Object[] { "wsad0009.xsd" });
            }
            final String schema9 = this.findSchema("wsad0001.xsd");
            if (schema9 != null) {
                if (sb == null) {
                    sb = new StringBuffer("urn:schemas-progress-com:WSAD:0001");
                }
                else {
                    sb.append(" ");
                    sb.append("urn:schemas-progress-com:WSAD:0001");
                }
                sb.append(" ");
                sb.append(schema9);
                if (this.m_useLog && this.m_log.ifLogVerbose(1L, 0)) {
                    this.m_log.logVerbose(0, 8607504787811871216L, new Object[] { schema9 });
                }
            }
            if (sb != null) {
                this.m_schemaLocation = sb.toString();
                this.m_parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", (Object)this.m_schemaLocation);
                this.m_validationPossible = true;
            }
            else {
                this.setValidation(false);
                this.m_validationPossible = false;
                if (this.m_useLog) {
                    this.m_log.logError(8607504787811871217L, new Object[] { "wsad0009.xsd" });
                }
            }
        }
        catch (SAXNotRecognizedException ex) {}
        catch (SAXNotSupportedException ex2) {}
    }
    
    private String findSchema(final String s) {
        final String s2 = null;
        if (this.m_installDir != null && this.m_installDir.length() > 0) {
            final String string = this.m_installDir + "properties/schemas/" + s;
            if (new File(string).exists()) {
                return encodeFilePath(string);
            }
        }
        if (this.m_instancePath != null && this.m_instancePath.length() > 0) {
            final String string2 = this.m_instancePath + s;
            if (new File(string2).exists()) {
                return encodeFilePath(string2);
            }
        }
        if (this.m_installDir != null && this.m_installDir.length() > 0) {
            final String string3 = this.m_installDir + "esbadapter/schema/" + s;
            if (new File(string3).exists()) {
                return encodeFilePath(string3);
            }
        }
        if (this.m_installDir != null && this.m_installDir.length() > 0) {
            final String string4 = this.m_installDir + "bin/" + s;
            if (new File(string4).exists()) {
                return encodeFilePath(string4);
            }
        }
        return s2;
    }
    
    private String encodeInstallDir() {
        String s = this.m_installDir;
        int beginIndex = 0;
        final StringBuffer sb = new StringBuffer();
        sb.append("file:///");
        int i = s.indexOf(32);
        if (i > -1) {
            while (i > -1) {
                sb.append(s.substring(beginIndex, i));
                sb.append("%20");
                beginIndex = ++i;
                i = s.indexOf(32, beginIndex);
            }
            sb.append(s.substring(beginIndex));
            s = sb.toString();
        }
        int beginIndex2 = 0;
        int j = s.indexOf(92);
        if (j > -1) {
            final StringBuffer sb2 = new StringBuffer();
            while (j > -1) {
                sb2.append(s.substring(beginIndex2, j));
                sb2.append("/");
                beginIndex2 = ++j;
                j = s.indexOf(92, beginIndex2);
            }
            sb2.append(s.substring(beginIndex2));
            s = sb2.toString();
        }
        return s;
    }
    
    private class WSAErrorHandler implements ErrorHandler
    {
        int m_errors;
        int m_warnings;
        int m_subSystem;
        boolean m_fatal;
        String systemId;
        String message;
        int lineNum;
        int colNum;
        
        public WSAErrorHandler() {
            this.m_errors = 0;
            this.m_warnings = 0;
            this.m_subSystem = 0;
            this.m_fatal = false;
        }
        
        public void clearErrors() {
            this.m_errors = 0;
            this.m_warnings = 0;
            this.m_fatal = false;
        }
        
        public void error(final SAXParseException ex) {
            if (0 == this.m_errors++ && WsaParser.this.m_useLog) {
                WsaParser.this.m_log.logError(8607504787811871208L, new Object[] { ex.getSystemId() });
            }
            if (WsaParser.this.m_useLog && WsaParser.this.m_log.ifLogVerbose(1L, 0)) {
                WsaParser.this.m_log.logVerbose(this.m_subSystem, 8607504787811871209L, new Object[] { ex.getMessage() });
                WsaParser.this.m_log.logVerbose(this.m_subSystem, 8607504787811871210L, new Object[] { new Integer(ex.getLineNumber()), new Integer(ex.getColumnNumber()) });
            }
        }
        
        public void fatalError(final SAXParseException ex) {
            this.m_fatal = true;
            if (WsaParser.this.m_useLog) {
                WsaParser.this.m_log.logError(8607504787811871211L, new Object[] { ex.getSystemId() });
                WsaParser.this.m_log.logError(8607504787811871209L, new Object[] { ex.getMessage() });
                WsaParser.this.m_log.logError(8607504787811871210L, new Object[] { new Integer(ex.getLineNumber()), new Integer(ex.getColumnNumber()) });
            }
        }
        
        public void warning(final SAXParseException ex) {
            ++this.m_warnings;
            if (WsaParser.this.m_useLog && WsaParser.this.m_log.ifLogVerbose(1L, 0)) {
                WsaParser.this.m_log.logVerbose(this.m_subSystem, 8607504787811871212L, new Object[] { ex.getSystemId() });
                WsaParser.this.m_log.logVerbose(this.m_subSystem, 8607504787811871209L, new Object[] { ex.getMessage() });
                WsaParser.this.m_log.logVerbose(this.m_subSystem, 8607504787811871210L, new Object[] { new Integer(ex.getLineNumber()), new Integer(ex.getColumnNumber()) });
            }
        }
        
        public int getErrors() {
            return this.m_errors;
        }
        
        public int getWarnings() {
            return this.m_warnings;
        }
        
        public boolean isFatal() {
            return this.m_fatal;
        }
        
        public void setSubSystem(final int subSystem) {
            this.m_subSystem = subSystem;
        }
    }
}

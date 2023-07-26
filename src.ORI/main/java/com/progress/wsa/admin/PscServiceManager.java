// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import org.apache.xml.serialize.BaseMarkupSerializer;
import org.apache.soap.util.ConfigManager;
import java.io.InputStream;
import java.io.File;
import java.util.Enumeration;
import org.w3c.dom.Text;
import java.io.Reader;
import org.xml.sax.InputSource;
import java.io.StringReader;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.apache.xml.serialize.XHTMLSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import javax.xml.parsers.DocumentBuilderFactory;
import com.progress.wsa.WsaProperties;
import com.progress.common.ehnlog.AppLogger;
import java.io.Writer;
import java.util.Hashtable;
import com.progress.wsa.tools.WsaStatusInfo;
import com.progress.wsa.tools.StatusInfo;
import com.progress.wsa.tools.ListInfo;
import com.progress.wsa.tools.QueryInfo;
import com.progress.wsa.WsaSOAPException;
import org.apache.soap.Constants;
import org.apache.soap.server.DeploymentDescriptor;
import org.apache.soap.util.xml.QName;
import org.apache.soap.server.TypeMapping;
import org.apache.soap.SOAPException;
import com.progress.wsa.WsaSOAPEngineException;
import javax.servlet.ServletContext;
import com.progress.wsa.WsaSOAPEngineContext;
import com.progress.wsa.WsaServiceManager;
import org.apache.soap.server.ServiceManager;

public class PscServiceManager extends ServiceManager implements WsaServiceManager
{
    private static final String XHTMLNS = "http://www.w3.org/1999/xhtml";
    private static final String XHTMLSTRICTPUBID = "-//W3C//DTD XHTML 1.0 Strict//EN";
    private static final String XHTMLTRANSPUBID = "-//W3C//DTD XHTML 1.0 Transitional//EN";
    private static final String XHTMLFRAMEPUBID = "-//W3C//DTD XHTML 1.0 Frameset//EN";
    private static final String LAT1ENTPUBID = "-//W3C//ENTITIES Latin 1 for XHTML//EN";
    private static final String SYMBOLENTPUBID = "-//W3C//ENTITIES Symbols for XHTML//EN";
    private static final String SPECENTPUBID = "-//W3C//ENTITIES Special for XHTML//EN";
    private static final String XHTMLDOC = "<?xml version='1.0' encoding='UTF-8'?>\r<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>\r<html xmlns='http://www.w3.org/1999/xhtml'><head id='head'/><body id='body'/></html>";
    protected PscDeploymentDescriptor m_wsadd;
    protected PscDeploymentDescriptor m_wsadd_91d;
    private boolean m_enableApacheAdmin;
    private WsaSOAPEngineContext m_context;
    
    public PscServiceManager(final WsaSOAPEngineContext context, final String configFilename) throws WsaSOAPEngineException {
        super((ServletContext)null, configFilename);
        this.m_wsadd = null;
        this.m_wsadd_91d = null;
        this.m_enableApacheAdmin = false;
        this.m_context = null;
        this.m_context = context;
        final String s = (String)this.m_context.get("psc.wsa.apache.admin");
        if (null != s && s.equals("1")) {
            this.m_enableApacheAdmin = true;
        }
        if (configFilename != null && !configFilename.equals("")) {
            super.configFilename = configFilename;
        }
        super.configMgr = null;
        super.configMgr = (ConfigManager)new PscConfigManager2();
        ((PscConfigManager2)super.configMgr).setContext(this.m_context);
        try {
            super.configMgr.init();
        }
        catch (SOAPException ex) {
            super.configMgr = null;
            throw new WsaSOAPEngineException("Unable to initialize Configuration Manager: " + ex.getMessage());
        }
        catch (Exception ex2) {
            throw new WsaSOAPEngineException("Unable to initialize Configuration Manager: " + ex2.getMessage());
        }
        super.smsdd = null;
        this.m_wsadd = null;
        this.m_wsadd_91d = null;
        (this.m_wsadd_91d = new PscDeploymentDescriptor()).setID("urn:services-progress-com:wsa-admin:0001");
        this.m_wsadd_91d.setMethods(new String[] { "pscdeploy", "pscundeploy", "psclist", "pscquery", "appstatus", "resetappstatus", "wsastatus", "resetwsastatus", "query", "getRuntimeProperties", "setRuntimeProperties", "importApp", "update", "enableApp", "disableApp", "resetRuntimeProperties" });
        this.m_wsadd_91d.setScope(2);
        this.m_wsadd_91d.setProviderType((byte)3);
        this.m_wsadd_91d.setProviderClass("com.progress.wsa.admin.PscAdminProvider");
        this.m_wsadd_91d.setServiceClass("com.progress.wsa.admin.PscAdminProvider");
        this.m_wsadd_91d.setIsStatic(false);
        this.m_wsadd_91d.setMappings(new TypeMapping[] { new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0001", "ListInfo"), "com.progress.wsa.tools.ListInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0001", "QueryInfo"), "com.progress.wsa.tools.QueryInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0001", "StatusInfo"), "com.progress.wsa.tools.StatusInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0001", "WsaStatusInfo"), "com.progress.wsa.tools.WsaStatusInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:WSAD:0002", "WSADType"), "com.progress.wsa.admin.WSAD", "com.progress.wsa.xmr.WSADSerializer", "com.progress.wsa.xmr.WSADSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:WSAD:0002", "AppContainerType"), "com.progress.wsa.admin.AppContainer", "com.progress.wsa.xmr.AppCntrSerializer", "com.progress.wsa.xmr.AppCntrSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://xml.apache.org/xml-soap", "TypeMapping"), "org.apache.soap.server.TypeMapping", "org.apache.soap.server.TypeMappingSerializer", "org.apache.soap.server.TypeMappingSerializer") });
        (this.m_wsadd = new PscDeploymentDescriptor()).setID("urn:services-progress-com:wsa-admin:0002");
        this.m_wsadd.setMethods(new String[] { "pscdeploy", "pscundeploy", "psclist", "pscquery", "appstatus", "resetappstatus", "wsastatus", "resetwsastatus", "query", "getRuntimeProperties", "setRuntimeProperties", "exportApp", "importApp", "update", "enableApp", "disableApp", "resetRuntimeProperties" });
        this.m_wsadd.setScope(2);
        this.m_wsadd.setProviderType((byte)3);
        this.m_wsadd.setProviderClass("com.progress.wsa.admin.PscAdminProvider");
        this.m_wsadd.setServiceClass("com.progress.wsa.admin.PscAdminProvider");
        this.m_wsadd.setIsStatic(false);
        this.m_wsadd.setMappings(new TypeMapping[] { new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "ListInfo"), "com.progress.wsa.tools.ListInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "QueryInfo"), "com.progress.wsa.tools.QueryInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "StatusInfo"), "com.progress.wsa.tools.StatusInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:wsa-admin:0002", "WsaStatusInfo"), "com.progress.wsa.tools.WsaStatusInfo", "org.apache.soap.encoding.soapenc.BeanSerializer", "org.apache.soap.encoding.soapenc.BeanSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:WSAD:0009", "WSADType"), "com.progress.wsa.admin.WSAD", "com.progress.wsa.xmr.WSADSerializer", "com.progress.wsa.xmr.WSADSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("urn:schemas-progress-com:WSAD:0009", "AppContainerType"), "com.progress.wsa.admin.AppContainer", "com.progress.wsa.xmr.AppCntrSerializer", "com.progress.wsa.xmr.AppCntrSerializer"), new TypeMapping("http://schemas.xmlsoap.org/soap/encoding/", new QName("http://xml.apache.org/xml-soap", "TypeMapping"), "org.apache.soap.server.TypeMapping", "org.apache.soap.server.TypeMappingSerializer", "org.apache.soap.server.TypeMappingSerializer") });
    }
    
    public PscConfigManager2 configManager() {
        return (PscConfigManager2)super.configMgr;
    }
    
    public DeploymentDescriptor query(final String str) throws SOAPException {
        if (str == null) {
            return null;
        }
        if (str.equals("urn:services-progress-com:wsa-admin:0001")) {
            return this.m_wsadd_91d;
        }
        if (str.equals("urn:services-progress-com:wsa-admin:0002")) {
            return this.m_wsadd;
        }
        final DeploymentDescriptor query = super.configMgr.query(str);
        if (query != null) {
            return query;
        }
        throw new WsaSOAPException(Constants.FAULT_CODE_SERVER, "service '" + str + "' unknown");
    }
    
    public QueryInfo pscquery(final String s) throws WsaSOAPException {
        if (s == null) {
            return null;
        }
        final QueryInfo pscquery = ((PscConfigManager2)super.configMgr).pscquery(s);
        if (pscquery != null) {
            return pscquery;
        }
        return null;
    }
    
    public ListInfo pscdeploy(final String s, final WSAD wsad) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).pscdeploy(s, wsad);
    }
    
    public AppContainer pscundeploy(final String s) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).pscundeploy(s);
    }
    
    public ListInfo[] psclist() throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).psclist();
    }
    
    public StatusInfo appstatus(final String s) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).appstatus(s);
    }
    
    public void resetappstatus(final String s) throws WsaSOAPException {
        ((PscConfigManager2)super.configMgr).resetappstatus(s);
    }
    
    public WsaStatusInfo wsastatus() throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).wsastatus();
    }
    
    public void resetwsastatus() throws WsaSOAPException {
        ((PscConfigManager2)super.configMgr).resetwsastatus();
    }
    
    public Hashtable getRuntimeProperties(final String s) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).getRuntimeProperties(s);
    }
    
    public void setRuntimeProperties(final String s, final Hashtable hashtable) throws WsaSOAPException {
        ((PscConfigManager2)super.configMgr).setRuntimeProperties(s, hashtable);
    }
    
    public AppContainer exportApp(final String s) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).exportApp(s);
    }
    
    public ListInfo importApp(final AppContainer appContainer) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).importApp(appContainer);
    }
    
    public ListInfo update(final String s, final WSAD wsad) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).update(s, wsad);
    }
    
    public boolean enableApp(final String s) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).enableApp(s);
    }
    
    public boolean disableApp(final String s) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).disableApp(s);
    }
    
    public void resetRuntimeProperties(final String s) throws WsaSOAPException {
        ((PscConfigManager2)super.configMgr).resetRuntimeProperties(s);
    }
    
    public String getWsdlDocPath(final String s) throws WsaSOAPException {
        return ((PscConfigManager2)super.configMgr).getWsdlDocPath(s);
    }
    
    public void generateWSDLList(final Writer writer, final String s) throws IOException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        final WsaProperties wsaProperties = (WsaProperties)this.m_context.get("psc.wsa.params");
        if (wsaProperties.wsdlListingPage != null && wsaProperties.wsdlListingPage.length() > 0) {
            if (!this.isValidPath(wsaProperties.wsdlListingPage)) {
                appLogger.logError(8607504787811871474L, new Object[] { "wsdlListingPage", wsaProperties.wsdlListingPage });
                this.generateDefaultWSDLList(writer, s);
                return;
            }
            final String realPath = this.m_context.getRealPath(wsaProperties.wsdlListingPage);
            try {
                final DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
                instance.setNamespaceAware(true);
                final DocumentBuilder documentBuilder = instance.newDocumentBuilder();
                documentBuilder.setEntityResolver(new XHTMLResolver());
                final Document parse = documentBuilder.parse(realPath);
                if (parse != null) {
                    final Element elementById = parse.getElementById("wsdltable");
                    if (elementById != null) {
                        elementById.appendChild(this.generateWSDLListDoc(parse, s));
                        ((BaseMarkupSerializer)new XHTMLSerializer(writer, new OutputFormat())).serialize(parse);
                    }
                    else {
                        appLogger.logError(8607504787811871427L, new Object[] { realPath });
                        this.generateDefaultWSDLList(writer, s);
                    }
                }
                else {
                    appLogger.logError(8607504787811871428L, new Object[] { realPath });
                    this.generateDefaultWSDLList(writer, s);
                }
            }
            catch (IOException ex) {
                appLogger.logError(8607504787811871429L, new Object[] { realPath });
                this.generateDefaultWSDLList(writer, s);
            }
            catch (ParserConfigurationException ex2) {
                appLogger.logError(8607504787811871429L, new Object[] { realPath });
                this.generateDefaultWSDLList(writer, s);
            }
            catch (SAXException ex3) {
                appLogger.logError(8607504787811871429L, new Object[] { realPath });
                this.generateDefaultWSDLList(writer, s);
            }
        }
        else {
            this.generateDefaultWSDLList(writer, s);
        }
    }
    
    private void generateDefaultWSDLList(final Writer writer, final String s) {
        try {
            final DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
            instance.setNamespaceAware(true);
            final DocumentBuilder documentBuilder = instance.newDocumentBuilder();
            documentBuilder.setEntityResolver(new XHTMLResolver());
            final Document parse = documentBuilder.parse(new InputSource(new StringReader("<?xml version='1.0' encoding='UTF-8'?>\r<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>\r<html xmlns='http://www.w3.org/1999/xhtml'><head id='head'/><body id='body'/></html>")));
            final Element documentElement = parse.getDocumentElement();
            final Element elementById = parse.getElementById("head");
            final Element elementNS = parse.createElementNS("http://www.w3.org/1999/xhtml", "title");
            elementNS.appendChild(parse.createTextNode("Progress WSA WSDL Listing Page"));
            elementById.appendChild(elementNS);
            documentElement.appendChild(elementById);
            final Element elementById2 = parse.getElementById("body");
            final Element elementNS2 = parse.createElementNS("http://www.w3.org/1999/xhtml", "h1");
            elementNS2.appendChild(parse.createTextNode("Progress Web Services Adapter"));
            elementById2.appendChild(elementNS2);
            final Element elementNS3 = parse.createElementNS("http://www.w3.org/1999/xhtml", "div");
            elementNS3.appendChild(this.generateWSDLListDoc(parse, s));
            elementById2.appendChild(elementNS3);
            ((BaseMarkupSerializer)new XHTMLSerializer(writer, new OutputFormat())).serialize(parse);
        }
        catch (IOException ex) {}
        catch (ParserConfigurationException ex2) {}
        catch (SAXException ex3) {}
    }
    
    public void generateNoWSDLList(final Writer writer) throws IOException {
        final String s = "WSDL Document retrieval is not available";
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        final WsaProperties wsaProperties = (WsaProperties)this.m_context.get("psc.wsa.params");
        if (wsaProperties.noWsdlPage != null && wsaProperties.noWsdlPage.length() > 0) {
            if (!this.isValidPath(wsaProperties.noWsdlPage)) {
                appLogger.logError(8607504787811871474L, new Object[] { "noWsdlPage", wsaProperties.noWsdlPage });
                this.generateHTTPError(writer, 503, s);
                return;
            }
            final String realPath = this.m_context.getRealPath(wsaProperties.noWsdlPage);
            try {
                final DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
                instance.setNamespaceAware(true);
                final DocumentBuilder documentBuilder = instance.newDocumentBuilder();
                documentBuilder.setEntityResolver(new XHTMLResolver());
                final Document parse = documentBuilder.parse(realPath);
                if (parse != null) {
                    ((BaseMarkupSerializer)new XHTMLSerializer(writer, new OutputFormat())).serialize(parse);
                }
                else {
                    appLogger.logError(8607504787811871428L, new Object[] { realPath });
                    this.generateHTTPError(writer, 503, s);
                }
            }
            catch (IOException ex) {
                appLogger.logError(8607504787811871429L, new Object[] { realPath });
                this.generateHTTPError(writer, 503, s);
            }
            catch (ParserConfigurationException ex2) {
                this.generateHTTPError(writer, 503, s);
            }
            catch (SAXException ex3) {
                this.generateHTTPError(writer, 503, s);
            }
        }
        else {
            this.generateHTTPError(writer, 503, s);
        }
    }
    
    public void generateHTTPError(final Writer writer, final int i, final String s) throws IOException {
        final AppLogger appLogger = (AppLogger)this.m_context.get("psc.wsa.log");
        final WsaProperties wsaProperties = (WsaProperties)this.m_context.get("psc.wsa.params");
        if (wsaProperties.httpErrorPage != null && wsaProperties.httpErrorPage.length() > 0) {
            if (!this.isValidPath(wsaProperties.httpErrorPage)) {
                appLogger.logError(8607504787811871474L, new Object[] { "httpErrorPage", wsaProperties.httpErrorPage });
                this.generateDefaultHTTPError(writer, i, s);
                return;
            }
            final String realPath = this.m_context.getRealPath(wsaProperties.httpErrorPage);
            try {
                final DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
                instance.setNamespaceAware(true);
                final DocumentBuilder documentBuilder = instance.newDocumentBuilder();
                documentBuilder.setEntityResolver(new XHTMLResolver());
                final Document parse = documentBuilder.parse(realPath);
                if (parse != null) {
                    final Element elementById = parse.getElementById("httpstatus");
                    final Element elementById2 = parse.getElementById("httpdescription");
                    if (elementById != null && elementById2 != null) {
                        final Element elementNS = parse.createElementNS("http://www.w3.org/1999/xhtml", "span");
                        elementNS.setAttribute("class", "status-code");
                        elementNS.appendChild(parse.createTextNode(Integer.toString(i)));
                        final Element elementNS2 = parse.createElementNS("http://www.w3.org/1999/xhtml", "p");
                        elementNS2.setAttribute("class", "status");
                        elementNS2.appendChild(parse.createTextNode("Status: "));
                        elementNS2.appendChild(elementNS);
                        elementById.appendChild(elementNS2);
                        final Element elementNS3 = parse.createElementNS("http://www.w3.org/1999/xhtml", "p");
                        elementNS3.setAttribute("class", "description");
                        elementNS3.appendChild(parse.createTextNode(s));
                        elementById2.appendChild(elementNS3);
                        ((BaseMarkupSerializer)new XHTMLSerializer(writer, new OutputFormat())).serialize(parse);
                    }
                    else {
                        appLogger.logError(8607504787811871456L, new Object[] { realPath });
                        this.generateDefaultHTTPError(writer, i, s);
                    }
                }
                else {
                    appLogger.logError(8607504787811871457L, new Object[] { realPath });
                    this.generateDefaultHTTPError(writer, i, s);
                }
            }
            catch (IOException ex) {
                appLogger.logError(8607504787811871455L, new Object[] { realPath });
                this.generateDefaultHTTPError(writer, i, s);
            }
            catch (ParserConfigurationException ex2) {
                this.generateDefaultHTTPError(writer, i, s);
            }
            catch (SAXException ex3) {
                this.generateDefaultHTTPError(writer, i, s);
            }
        }
        else {
            this.generateDefaultHTTPError(writer, i, s);
        }
    }
    
    private void generateDefaultHTTPError(final Writer writer, final int i, final String s) {
        try {
            final DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
            instance.setNamespaceAware(true);
            final DocumentBuilder documentBuilder = instance.newDocumentBuilder();
            documentBuilder.setEntityResolver(new XHTMLResolver());
            final Document parse = documentBuilder.parse(new InputSource(new StringReader("<?xml version='1.0' encoding='UTF-8'?>\r<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>\r<html xmlns='http://www.w3.org/1999/xhtml'><head id='head'/><body id='body'/></html>")));
            final Element documentElement = parse.getDocumentElement();
            final Element elementById = parse.getElementById("head");
            final Element elementNS = parse.createElementNS("http://www.w3.org/1999/xhtml", "title");
            elementNS.appendChild(parse.createTextNode("Progress WSA HTTP Error Page"));
            elementById.appendChild(elementNS);
            documentElement.appendChild(elementById);
            final Element elementById2 = parse.getElementById("body");
            final Element elementNS2 = parse.createElementNS("http://www.w3.org/1999/xhtml", "h1");
            elementNS2.appendChild(parse.createTextNode("Progress Web Services Adapter Error"));
            elementById2.appendChild(elementNS2);
            final Element elementNS3 = parse.createElementNS("http://www.w3.org/1999/xhtml", "p");
            elementNS3.appendChild(parse.createTextNode("Status: " + i));
            elementById2.appendChild(elementNS3);
            final Element elementNS4 = parse.createElementNS("http://www.w3.org/1999/xhtml", "p");
            Text text;
            if (null != s) {
                text = parse.createTextNode(s);
            }
            else {
                text = parse.createTextNode("The HTTP request is denied.");
            }
            elementNS4.appendChild(text);
            elementById2.appendChild(elementNS4);
            documentElement.appendChild(elementById2);
            ((BaseMarkupSerializer)new XHTMLSerializer(writer, new OutputFormat())).serialize(parse);
        }
        catch (IOException ex) {}
        catch (ParserConfigurationException ex2) {}
        catch (SAXException ex3) {}
    }
    
    private Element generateWSDLListDoc(final Document document, final String str) {
        Element element = null;
        int n = 0;
        final Enumeration applications = ((PscConfigManager2)super.configMgr).getApplications();
        if (applications == null) {
            element = document.createElementNS("http://www.w3.org/1999/xhtml", "p");
            element.appendChild(document.createTextNode("There are no deployed applications on this Web Services Adapter"));
        }
        else {
            while (applications.hasMoreElements()) {
                final AppContainer appContainer = applications.nextElement();
                final String id = appContainer.getId();
                if (!id.equals("urn:services-progress-com:wsa-default:0001")) {
                    if (n == 0) {
                        n = 1;
                        element = document.createElementNS("http://www.w3.org/1999/xhtml", "table");
                        element.setAttribute("class", "table");
                        final Element elementNS = document.createElementNS("http://www.w3.org/1999/xhtml", "caption");
                        elementNS.setAttribute("class", "table-caption");
                        elementNS.appendChild(document.createTextNode("Web Services Adapter Deployed Applications"));
                        element.appendChild(elementNS);
                        final Element elementNS2 = document.createElementNS("http://www.w3.org/1999/xhtml", "thead");
                        elementNS2.setAttribute("class", "table-header");
                        final Element elementNS3 = document.createElementNS("http://www.w3.org/1999/xhtml", "tr");
                        final Element elementNS4 = document.createElementNS("http://www.w3.org/1999/xhtml", "th");
                        elementNS4.setAttribute("class", "table-header-small");
                        elementNS4.appendChild(document.createTextNode("Name"));
                        elementNS3.appendChild(elementNS4);
                        final Element elementNS5 = document.createElementNS("http://www.w3.org/1999/xhtml", "th");
                        elementNS5.setAttribute("class", "table-header-small");
                        elementNS5.appendChild(document.createTextNode("WSDL File"));
                        elementNS3.appendChild(elementNS5);
                        final Element elementNS6 = document.createElementNS("http://www.w3.org/1999/xhtml", "th");
                        elementNS6.setAttribute("class", "table-header-large");
                        elementNS6.appendChild(document.createTextNode("Target URL"));
                        elementNS3.appendChild(elementNS6);
                        elementNS2.appendChild(elementNS3);
                        element.appendChild(elementNS2);
                    }
                    final Element elementNS7 = document.createElementNS("http://www.w3.org/1999/xhtml", "tr");
                    elementNS7.setAttribute("class", "table-row");
                    final Element elementNS8 = document.createElementNS("http://www.w3.org/1999/xhtml", "td");
                    elementNS8.appendChild(document.createTextNode(appContainer.getFriendlyName()));
                    elementNS7.appendChild(elementNS8);
                    final Element elementNS9 = document.createElementNS("http://www.w3.org/1999/xhtml", "td");
                    final Element elementNS10 = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
                    final StringBuffer sb = new StringBuffer(str);
                    sb.append("?targetURI=");
                    sb.append(id);
                    elementNS10.setAttribute("href", sb.toString());
                    elementNS10.appendChild(document.createTextNode(appContainer.getWSDLFileName()));
                    elementNS9.appendChild(elementNS10);
                    elementNS7.appendChild(elementNS9);
                    final Element elementNS11 = document.createElementNS("http://www.w3.org/1999/xhtml", "td");
                    elementNS11.appendChild(document.createTextNode(id));
                    elementNS7.appendChild(elementNS11);
                    element.appendChild(elementNS7);
                }
            }
            if (n == 0) {
                element = document.createElementNS("http://www.w3.org/1999/xhtml", "p");
                element.appendChild(document.createTextNode("There are no deployed applications on this Web Services Adapter"));
            }
        }
        return element;
    }
    
    private boolean isValidPath(final String s) {
        return !s.startsWith("/") && !s.startsWith(File.separator) && (s.length() < 3 || s.charAt(0) != '.' || s.charAt(1) != '.') && (s.length() < 3 || !Character.isLetter(s.charAt(0)) || s.charAt(1) != ':');
    }
    
    public class XHTMLResolver implements EntityResolver
    {
        public InputSource resolveEntity(final String s, final String systemId) {
            InputStream byteStream = null;
            if (s.equals("-//W3C//DTD XHTML 1.0 Strict//EN")) {
                byteStream = this.getClass().getResourceAsStream("/dtd/xhtml1-strict.dtd");
            }
            else if (s.equals("-//W3C//DTD XHTML 1.0 Transitional//EN")) {
                byteStream = this.getClass().getResourceAsStream("/dtd/xhtml1-transitional.dtd");
            }
            else if (s.equals("-//W3C//DTD XHTML 1.0 Frameset//EN")) {
                byteStream = this.getClass().getResourceAsStream("/dtd/xhtml1-frameset.dtd");
            }
            else if (s.equals("-//W3C//ENTITIES Latin 1 for XHTML//EN")) {
                byteStream = this.getClass().getResourceAsStream("/dtd/xhtml-lat1.ent");
            }
            else if (s.equals("-//W3C//ENTITIES Symbols for XHTML//EN")) {
                byteStream = this.getClass().getResourceAsStream("/dtd/xhtml-symbol.ent");
            }
            else if (s.equals("-//W3C//ENTITIES Special for XHTML//EN")) {
                byteStream = this.getClass().getResourceAsStream("/dtd/xhtml-special.ent");
            }
            InputSource inputSource;
            if (byteStream != null) {
                inputSource = new InputSource(byteStream);
            }
            else {
                inputSource = new InputSource(systemId);
            }
            return inputSource;
        }
    }
}

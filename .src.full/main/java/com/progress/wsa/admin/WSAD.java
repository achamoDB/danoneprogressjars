// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.io.ObjectInputStream;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import java.net.MalformedURLException;
import com.progress.common.util.PscURLParser;
import java.io.IOException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.progress.common.ehnlog.AppLogger;
import com.progress.open4gl.proxygen.PGAppObj;
import com.progress.wsa.open4gl.XMLSerializableRoot;
import java.io.Serializable;

public class WSAD implements Serializable, XMLSerializableRoot
{
    private static final String ROOT = "WSAD";
    private static final String XML_TYPE = "WSADType";
    protected transient String m_schemaLoc;
    protected transient String m_prefix;
    protected transient String m_namespace;
    protected transient WsaParser m_parser;
    protected transient PGAppObj m_appObj;
    
    public static WSAD loadWSMFile(final String s, final String s2) throws IOException {
        final WsaParser parser = new WsaParser(s, null);
        final AppContainer appContainer = new AppContainer(parser);
        final Document file = parser.parseFile(s2, 0);
        if (file == null) {
            return null;
        }
        final Element documentElement = file.getDocumentElement();
        final String namespaceURI = documentElement.getNamespaceURI();
        if (namespaceURI == null) {
            return null;
        }
        WSAD wsad;
        if (namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0009") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0008") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0007") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0006") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0005") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0004") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0003") == 0) {
            appContainer.readXML(documentElement);
            wsad = appContainer.getWSAD();
        }
        else {
            if (namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0002") != 0) {
                return null;
            }
            wsad = new WSAD();
            wsad.readXML(documentElement);
        }
        wsad.setParser(parser);
        return wsad;
    }
    
    public static WSAD loadXPXGFile(final String s, final String s2) throws IOException {
        final WsaParser parser = new WsaParser(s, null);
        final Document file = parser.parseFile(s2, 0);
        if (file == null) {
            return null;
        }
        final Element documentElement = file.getDocumentElement();
        final String namespaceURI = documentElement.getNamespaceURI();
        if (namespaceURI == null) {
            return null;
        }
        if (namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0009") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0008") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0007") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0006") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0005") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0004") == 0 || namespaceURI.compareTo("urn:schemas-progress-com:WSAD:0003") == 0) {
            final WSAD wsad = new WSAD();
            wsad.readXML(documentElement);
            wsad.setParser(parser);
            return wsad;
        }
        return null;
    }
    
    public WSAD() {
        this.m_schemaLoc = null;
        this.m_prefix = null;
        this.m_namespace = "urn:schemas-progress-com:WSAD:0009";
        this.m_parser = null;
    }
    
    public WSAD(final PGAppObj appObj) {
        this.m_schemaLoc = null;
        this.m_prefix = null;
        this.m_namespace = "urn:schemas-progress-com:WSAD:0009";
        this.m_parser = null;
        this.m_appObj = appObj;
    }
    
    public void setPGAppObj(final PGAppObj appObj) {
        this.m_appObj = appObj;
    }
    
    public PGAppObj getPGAppObj() {
        return this.m_appObj;
    }
    
    public void setParser(final WsaParser parser) {
        this.m_parser = parser;
    }
    
    public WsaParser getParser() {
        return this.m_parser;
    }
    
    public int getEncodingStyle() {
        int encoding = 0;
        if (this.m_appObj != null) {
            encoding = this.m_appObj.getWSInfo().getDWGenInfo().getEncoding();
        }
        return encoding;
    }
    
    public void setEncodingStyle(final int encoding) {
        if (this.m_appObj != null) {
            this.m_appObj.getWSInfo().getDWGenInfo().setEncoding(encoding);
        }
    }
    
    public String getWebServiceNamespace() {
        String webServiceNameSpace = null;
        if (this.m_appObj != null) {
            webServiceNameSpace = this.m_appObj.getWSInfo().getDWGenInfo().getWebServiceNameSpace();
        }
        return webServiceNameSpace;
    }
    
    public void setWebServiceNamespace(final String webServiceNameSpace) {
        if (this.m_appObj != null) {
            this.m_appObj.getWSInfo().getDWGenInfo().setWebServiceNameSpace(webServiceNameSpace);
        }
    }
    
    public void setSoapAction(final String soapAction) {
        if (this.m_appObj != null) {
            this.m_appObj.getWSInfo().getDWGenInfo().setSoapAction(soapAction);
        }
    }
    
    public void setAppendToSoapAction(final boolean appendToSoapAction) {
        if (this.m_appObj != null) {
            this.m_appObj.getWSInfo().getDWGenInfo().setAppendToSoapAction(appendToSoapAction);
        }
    }
    
    public void saveWSMFile(final String s) {
        boolean b = false;
        if (this.m_appObj == null) {
            return;
        }
        AppContainer appContainer;
        if (this.m_parser == null) {
            appContainer = new AppContainer();
            this.m_parser = new WsaParser(null, null);
            b = true;
        }
        else {
            appContainer = new AppContainer(this.m_parser);
        }
        appContainer.setWSAD(this);
        try {
            this.m_parser.serializeObject(appContainer, s, true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (b) {
            this.m_parser = null;
        }
    }
    
    public void saveWSMFile(final String s, final String s2) {
        boolean b = false;
        if (this.m_appObj == null) {
            return;
        }
        AppContainer appContainer;
        if (this.m_parser == null) {
            appContainer = new AppContainer();
            this.m_parser = new WsaParser(null, null);
            b = true;
        }
        else {
            appContainer = new AppContainer(this.m_parser);
        }
        appContainer.setWSAD(this);
        final AppRuntimeProps runtimeProperties = new AppRuntimeProps();
        if (null != s2) {
            try {
                final PscURLParser pscURLParser = new PscURLParser(s2);
                final String scheme = pscURLParser.getScheme();
                if (null != scheme) {
                    runtimeProperties.setStringProperty("PROGRESS.Session.appServiceProtocol", scheme);
                }
                final String host = pscURLParser.getHost();
                if (null != host) {
                    runtimeProperties.setStringProperty("PROGRESS.Session.appServiceHost", host);
                }
                runtimeProperties.setIntProperty("PROGRESS.Session.appServicePort", pscURLParser.getPort());
                final String service = pscURLParser.getService();
                if (null != service) {
                    runtimeProperties.setStringProperty("PROGRESS.Session.appServiceName", service);
                }
                appContainer.setRuntimeProperties(runtimeProperties);
            }
            catch (MalformedURLException ex2) {}
        }
        try {
            this.m_parser.serializeObject(appContainer, s, true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        if (b) {
            this.m_parser = null;
        }
    }
    
    public void saveXPXGFile(final String s) throws Exception {
        boolean b = false;
        if (this.m_appObj == null) {
            return;
        }
        if (this.m_parser == null) {
            this.m_parser = new WsaParser(null, null);
            b = true;
        }
        try {
            this.m_parser.serializeObject(this, s, false);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            if (b) {
                this.m_parser = null;
            }
        }
    }
    
    public void setSchemaLocation(final String s) {
    }
    
    public String getSchemaLocation() {
        return this.m_schemaLoc;
    }
    
    public void setTargetNamespace(final String namespace) {
        this.m_namespace = namespace;
    }
    
    public String getTargetNamespace() {
        return this.m_namespace;
    }
    
    public void setRoot(final String s) {
    }
    
    public String getRoot() {
        return "WSAD";
    }
    
    public void setPrefix(final String prefix) {
        this.m_prefix = prefix;
    }
    
    public String getPrefix() {
        return this.m_prefix;
    }
    
    public void setXMLType(final String s) {
    }
    
    public String getXMLType() {
        return "WSADType";
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        String string;
        if (str != null && str.length() > 0) {
            string = str + ":" + "AppObject";
        }
        else {
            string = "AppObject";
        }
        xmlSerializer.startElement(s, "AppObject", string, (Attributes)null);
        this.m_appObj.writeXML(xmlSerializer, s, str);
        xmlSerializer.endElement(s, "AppObject", string);
    }
    
    public void readXML(final Node node) {
        this.m_appObj = new PGAppObj();
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                WsaParser.extractNodeValue(item);
                if (localName.equals("AppObject")) {
                    this.m_appObj.readXML(item);
                }
            }
        }
    }
    
    public String toString() {
        String serializeObject = null;
        if (this.m_parser == null) {
            this.m_parser = new WsaParser(null, null);
        }
        try {
            serializeObject = this.m_parser.serializeObject(this, (String)null);
        }
        catch (IOException ex) {}
        return serializeObject;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
    }
}

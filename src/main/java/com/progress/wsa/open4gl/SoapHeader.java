// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl;

import java.util.StringTokenizer;
import org.apache.soap.util.xml.DOMUtils;
import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.apache.soap.Constants;
import org.w3c.dom.Element;
import org.apache.soap.SOAPException;
import com.progress.wsa.WsaConstants;
import com.progress.common.util.Base64;
import java.security.MessageDigest;
import com.progress.common.util.UUID;
import org.w3c.dom.DocumentType;
import org.apache.xerces.dom.DocumentImpl;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.wsa.admin.PscDeploymentDescriptor;
import org.w3c.dom.Document;
import java.util.Vector;
import org.apache.soap.Header;

public class SoapHeader
{
    public static final int SIMPLE_INT = 0;
    public static final int SIMPLE_STRING = 1;
    private Header m_header;
    private Vector m_headerEntries;
    private Document m_dom;
    private PscDeploymentDescriptor m_objDesc;
    private String m_wsaUUID;
    private String m_objectUUID;
    private String m_objectUUIDHeader;
    private IAppLogger m_log;
    
    public SoapHeader() {
        this.m_header = null;
        this.m_headerEntries = null;
        this.m_dom = null;
        this.m_objDesc = null;
        this.m_wsaUUID = null;
        this.m_objectUUID = null;
        this.m_objectUUIDHeader = null;
        this.m_log = null;
    }
    
    public SoapHeader(final PscDeploymentDescriptor objDesc) {
        this.m_header = null;
        this.m_headerEntries = null;
        this.m_dom = null;
        this.m_objDesc = null;
        this.m_wsaUUID = null;
        this.m_objectUUID = null;
        this.m_objectUUIDHeader = null;
        this.m_log = null;
        this.m_header = new Header();
        this.m_headerEntries = new Vector();
        this.m_dom = (Document)new DocumentImpl((DocumentType)null);
        this.m_objDesc = objDesc;
        this.m_log = this.m_objDesc.getLogSink();
    }
    
    public SoapHeader(final Header header, final PscDeploymentDescriptor objDesc) {
        this.m_header = null;
        this.m_headerEntries = null;
        this.m_dom = null;
        this.m_objDesc = null;
        this.m_wsaUUID = null;
        this.m_objectUUID = null;
        this.m_objectUUIDHeader = null;
        this.m_log = null;
        this.m_header = header;
        this.m_objDesc = objDesc;
        this.m_log = this.m_objDesc.getLogSink();
        if (null != this.m_header) {
            this.parseInputHeaders();
        }
    }
    
    public Header getSOAPHeader() {
        return this.m_header;
    }
    
    public String getObjectUUID() {
        return this.m_objectUUID;
    }
    
    public String getUUIDHeader() {
        return this.m_objectUUIDHeader;
    }
    
    public String getWSAUUID() {
        return this.m_wsaUUID;
    }
    
    public void buildNewSoapHeader(final String wsaUUID, final String s, final String s2, final String s3) throws SOAPException {
        final StringBuffer sb = new StringBuffer();
        final String s4 = (null != s2) ? s2 : this.m_objDesc.getProgressObjectName();
        final String s5 = (null != s3) ? s3 : this.m_objDesc.getID();
        if (null != this.m_header.getHeaderEntries()) {
            this.m_header = null;
            this.m_header = new Header();
        }
        this.m_wsaUUID = wsaUUID;
        this.m_objectUUID = ((null != s) ? s : new UUID().toString());
        if (null != this.m_wsaUUID) {
            sb.append(this.m_wsaUUID);
            sb.append(";");
        }
        sb.append(this.m_objectUUID);
        sb.append(";");
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(wsaUUID.getBytes());
            instance.update(s.getBytes());
            instance.update(wsaUUID.getBytes());
            sb.append(Base64.encode(instance.digest()));
        }
        catch (Exception ex) {
            this.m_log.logError("Error creating MessageDigest for ObjectID: %s", new Object[] { ex.toString() });
        }
        this.m_objectUUIDHeader = sb.toString();
        this.m_objDesc.defaultEncoding();
        String s6;
        if (this.m_objDesc.defaultEncoding().equals(WsaConstants.WSA_SERVICE_ENCODING[3])) {
            s6 = s4 + "ID";
        }
        else {
            s6 = "t:" + s4 + "ID";
        }
        this.addElement(true, this.m_objectUUIDHeader, s6, s5, 1);
    }
    
    public void addElement(final Element obj) throws SOAPException {
        if (obj == null) {
            throw new SOAPException(Constants.FAULT_CODE_SERVER, "addElement -- the element cannot be null");
        }
        this.m_headerEntries.addElement(obj);
        this.m_header.setHeaderEntries(this.m_headerEntries);
    }
    
    public void addElement(final boolean b, final Element element) throws SOAPException {
        if (element == null) {
            throw new SOAPException(Constants.FAULT_CODE_SERVER, "addElement -- the element cannot be null");
        }
        try {
            final Attr attributeNS = this.m_dom.createAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "SOAP-ENV:mustUnderstand");
            attributeNS.setValue(b ? "1" : "0");
            element.setAttributeNode(attributeNS);
            this.addElement(element);
        }
        catch (DOMException ex) {
            throw new SOAPException(Constants.FAULT_CODE_SERVER, "addElement -- DOM errors: " + ex.getMessage());
        }
    }
    
    public void addElement(final boolean b, final String s, final String s2, final String value, final int n) throws SOAPException {
        if (n != 0 && n != 1) {
            throw new SOAPException(Constants.FAULT_CODE_SERVER, "addElement -- invalid value for: type");
        }
        if (s == null || s2 == null || value == null) {
            throw new SOAPException(Constants.FAULT_CODE_SERVER, "addElement -- parameters cannot be null");
        }
        if (s == "" || s2 == "" || value == "") {
            throw new SOAPException(Constants.FAULT_CODE_SERVER, "addElement -- parameters cannot be empty");
        }
        try {
            final Element element = this.m_dom.createElement("UUID");
            final Element elementNS = this.m_dom.createElementNS(value, s2);
            element.appendChild(this.m_dom.createTextNode(s));
            elementNS.appendChild(element);
            if (this.m_objDesc.defaultEncoding().equals(WsaConstants.WSA_SERVICE_ENCODING[1])) {
                final Attr attribute = this.m_dom.createAttribute("SOAP-ENV:encodingStyle");
                attribute.setValue("http://schemas.xmlsoap.org/soap/encoding/");
                elementNS.setAttributeNode(attribute);
            }
            if (this.m_objDesc.defaultEncoding().equals(WsaConstants.WSA_SERVICE_ENCODING[3])) {
                final Attr attribute2 = this.m_dom.createAttribute("xmlns");
                attribute2.setValue(value);
                elementNS.setAttributeNode(attribute2);
            }
            if (this.m_objDesc.defaultEncoding().equals(WsaConstants.WSA_SERVICE_ENCODING[1])) {
                final Attr attribute3 = this.m_dom.createAttribute("xsi:type");
                switch (n) {
                    case 0: {
                        attribute3.setValue("xsd:int");
                        break;
                    }
                    default: {
                        attribute3.setValue("xsd:string");
                        break;
                    }
                }
                element.setAttributeNode(attribute3);
            }
            this.addElement(elementNS);
        }
        catch (DOMException ex) {
            throw new SOAPException(Constants.FAULT_CODE_SERVER, "addElement -- DOM errors: " + ex.getMessage());
        }
    }
    
    protected void parseInputHeaders() {
        this.m_headerEntries = this.m_header.getHeaderEntries();
        if (null != this.m_headerEntries) {
            final String string = this.m_objDesc.getProgressObjectName() + "ID";
            Element element = null;
            for (int i = 0; i < this.m_headerEntries.size(); ++i) {
                final Element element2 = this.m_headerEntries.elementAt(i);
                final String nodeName = element2.getNodeName();
                if (this.m_log.ifLogExtended(4L, 2)) {
                    this.m_log.logExtended(2, "Checking client request SOAP Header : %s", new Object[] { element2.toString() });
                }
                if (0 == string.compareTo(nodeName.substring(nodeName.lastIndexOf(":") + 1)) && null == element) {
                    element = element2;
                }
            }
            if (null != element) {
                final Element firstChildElement = DOMUtils.getFirstChildElement(element);
                if (null != firstChildElement) {
                    final String nodeName2 = firstChildElement.getNodeName();
                    if (firstChildElement.getLocalName().equals("UUID")) {
                        final String childCharacterData = DOMUtils.getChildCharacterData(firstChildElement);
                        if (null != childCharacterData) {
                            final StringTokenizer stringTokenizer = new StringTokenizer(childCharacterData.trim(), ";");
                            int n = 0;
                            final String[] array = new String[3];
                            final int countTokens = stringTokenizer.countTokens();
                            while (stringTokenizer.hasMoreTokens() && n < 3) {
                                array[n] = stringTokenizer.nextToken();
                                ++n;
                            }
                            if (countTokens == 3) {
                                String encode = new String();
                                try {
                                    final MessageDigest instance = MessageDigest.getInstance("MD5");
                                    instance.update(array[0].getBytes());
                                    instance.update(array[1].getBytes());
                                    instance.update(array[0].getBytes());
                                    encode = Base64.encode(instance.digest());
                                }
                                catch (Exception ex) {
                                    this.m_log.logError("Error creating MessageDigest for ObjectID: %s", new Object[] { ex.toString() });
                                }
                                if (encode.equals(array[2])) {
                                    this.m_wsaUUID = array[0];
                                    this.m_objectUUID = array[1];
                                    this.m_objectUUIDHeader = this.m_wsaUUID + ";" + this.m_objectUUID;
                                }
                                else if (this.m_log.ifLogExtended(4L, 2)) {
                                    this.m_log.logExtended(2, "Request Header %s failed validation.", new Object[] { element.getNodeName() });
                                }
                            }
                            else if (this.m_log.ifLogExtended(4L, 2)) {
                                this.m_log.logExtended(2, "Request Header %s failed validation.", new Object[] { element.getNodeName() });
                            }
                        }
                        else if (this.m_log.ifLogExtended(4L, 2)) {
                            this.m_log.logExtended(2, "Request Header %s was missing the %s object ID data", new Object[] { element.getNodeName(), "UUID" });
                        }
                    }
                    else if (this.m_log.ifLogExtended(4L, 2)) {
                        this.m_log.logExtended(2, "Request Header %s: expected node named %s found node named %s", new Object[] { element.getNodeName(), "UUID", nodeName2 });
                    }
                }
                else if (this.m_log.ifLogExtended(4L, 2)) {
                    this.m_log.logExtended(2, "Request Header %s was missing the %s object ID data", new Object[] { element.getNodeName(), "UUID" });
                }
            }
        }
    }
}

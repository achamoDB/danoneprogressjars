// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.wsdlgen;

import org.apache.xml.serialize.BaseMarkupSerializer;
import java.util.ResourceBundle;
import com.progress.common.util.crypto;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import com.progress.wsa.admin.WsaParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;
import org.apache.xml.serialize.XMLSerializer;
import com.progress.open4gl.Open4GLException;
import com.progress.international.resources.ProgressResources;
import com.progress.wsa.open4gl.XMLSerializable;
import com.progress.message.pgMsg;
import java.io.Serializable;

public class DWGenInfo implements Serializable, pgMsg, XMLSerializable
{
    private static final long serialVersionUID = 4899053515305950532L;
    private static ProgressResources m_resources;
    public static final int RPC_ENCODED = 1;
    public static final int RPC_LITERAL = 2;
    public static final int DOC_LITERAL = 3;
    private int m_curEncoding;
    private String m_strSoapEndPointURL;
    private String m_strWebServiceNameSpace;
    private boolean m_bAppendToSoapAction;
    private boolean m_bUseUserDefinedSoapAction;
    private String m_strSoapAction;
    private boolean m_bUserDefinedWSNamespace;
    private boolean m_bGenWSDL;
    private boolean m_bRPCEncoded;
    private boolean m_bRPCLiteral;
    private boolean m_bDocLiteral;
    private boolean m_bUseCustomPortBindSuffix;
    private boolean m_bUseCustomServiceSuffix;
    private String m_strPortBindSuffix;
    private String m_strServiceSuffix;
    private boolean m_bhasConnectReturnString;
    private String m_strServiceName;
    private int m_esbEncoding;
    private String m_strEntryEndpoint;
    private boolean m_bUseDefaultEntryEndpoint;
    private String m_strFaultEndpoint;
    private boolean m_bUseDefaultFaultEndpoint;
    private String m_strRejectedEndpoint;
    private boolean m_bUseDefaultRejectedEndpoint;
    private String m_strContainerName;
    private String m_strAppserverURL;
    private String m_strFilename;
    private String m_strResourceDir;
    private boolean m_bOverwrite;
    private String m_sonicURL;
    private String m_sonicDomain;
    private String m_sonicUser;
    private String m_sonicPassword;
    private boolean m_bUseFileSystem;
    private boolean m_bDeployToDS;
    private boolean m_bCreateXAR;
    private String m_strXARName;
    private String m_strSonicResourceDir;
    private boolean m_bSonicOverwriteFiles;
    
    public DWGenInfo() {
        this.m_bGenWSDL = true;
        this.m_bRPCEncoded = true;
        this.m_bRPCLiteral = false;
        this.m_bDocLiteral = false;
        this.m_curEncoding = 1;
        this.m_strSoapEndPointURL = "";
        this.m_strWebServiceNameSpace = "";
        this.m_bAppendToSoapAction = false;
        this.m_strSoapAction = "";
        this.m_bUserDefinedWSNamespace = false;
        this.m_bUseUserDefinedSoapAction = false;
        this.m_bUseCustomPortBindSuffix = false;
        this.m_bUseCustomServiceSuffix = false;
        this.m_bhasConnectReturnString = false;
        this.m_strPortBindSuffix = "";
        this.m_strServiceSuffix = "";
        this.m_strServiceName = "";
        this.m_esbEncoding = 2;
        this.m_strEntryEndpoint = "";
        this.m_bUseDefaultEntryEndpoint = true;
        this.m_strFaultEndpoint = "";
        this.m_bUseDefaultFaultEndpoint = true;
        this.m_strRejectedEndpoint = "";
        this.m_bUseDefaultRejectedEndpoint = true;
        this.m_strContainerName = "";
        this.m_strAppserverURL = "";
        this.m_strFilename = "";
        this.m_strResourceDir = "";
        this.m_bOverwrite = false;
    }
    
    public int getEncoding() {
        return this.m_curEncoding;
    }
    
    public void setEncoding(final int curEncoding) {
        this.m_curEncoding = curEncoding;
    }
    
    public int getESBEncoding() {
        return this.m_esbEncoding;
    }
    
    public void setESBEncoding(final int esbEncoding) {
        this.m_esbEncoding = esbEncoding;
    }
    
    public String getSoapEndPointURL() {
        return this.m_strSoapEndPointURL;
    }
    
    public void setSoapEndPointURL(final String strSoapEndPointURL) {
        this.m_strSoapEndPointURL = strSoapEndPointURL;
    }
    
    public String getWebServiceNameSpace() {
        return this.m_strWebServiceNameSpace;
    }
    
    public void setWebServiceNameSpace(final String strWebServiceNameSpace) {
        this.m_strWebServiceNameSpace = strWebServiceNameSpace;
    }
    
    public boolean appendToSoapAction() {
        return this.m_bAppendToSoapAction;
    }
    
    public void setAppendToSoapAction(final boolean bAppendToSoapAction) {
        this.m_bAppendToSoapAction = bAppendToSoapAction;
    }
    
    public boolean useUserDefinedSoapAction() {
        return this.m_bUseUserDefinedSoapAction;
    }
    
    public void setUseUserDefinedSoapAction(final boolean bUseUserDefinedSoapAction) {
        this.m_bUseUserDefinedSoapAction = bUseUserDefinedSoapAction;
    }
    
    public String getSoapAction() {
        return this.m_strSoapAction;
    }
    
    public void setSoapAction(final String strSoapAction) {
        this.m_strSoapAction = strSoapAction;
    }
    
    public boolean genWSDL() {
        return this.m_bGenWSDL;
    }
    
    public void setGenWSDL(final boolean bGenWSDL) {
        this.m_bGenWSDL = bGenWSDL;
    }
    
    public boolean genRPCEncoded() {
        return this.m_bRPCEncoded;
    }
    
    public void setRPCEncoded(final boolean brpcEncoded) {
        this.m_bRPCEncoded = brpcEncoded;
    }
    
    public boolean genRPCLiteral() {
        return this.m_bRPCLiteral;
    }
    
    public void setRPCLiteral(final boolean brpcLiteral) {
        this.m_bRPCLiteral = brpcLiteral;
    }
    
    public boolean genDocLiteral() {
        return this.m_bDocLiteral;
    }
    
    public void setDocLiteral(final boolean bDocLiteral) {
        this.m_bDocLiteral = bDocLiteral;
    }
    
    public boolean isUserDefinedWSNamespace() {
        return this.m_bUserDefinedWSNamespace;
    }
    
    public void setUserDefinedWSNamespace(final boolean bUserDefinedWSNamespace) {
        this.m_bUserDefinedWSNamespace = bUserDefinedWSNamespace;
    }
    
    public String getPortTypeBindingSuffix() {
        return this.m_strPortBindSuffix;
    }
    
    public void setPortTypeBindingSuffix(final String strPortBindSuffix) {
        this.m_strPortBindSuffix = strPortBindSuffix;
    }
    
    public String getServiceSuffix() {
        return this.m_strServiceSuffix;
    }
    
    public void setServiceSuffix(final String strServiceSuffix) {
        this.m_strServiceSuffix = strServiceSuffix;
    }
    
    public boolean usePortTypeBindingSuffix() {
        return this.m_bUseCustomPortBindSuffix;
    }
    
    public void setUsePortTypeBindingSuffix(final boolean bUseCustomPortBindSuffix) {
        this.m_bUseCustomPortBindSuffix = bUseCustomPortBindSuffix;
    }
    
    public boolean useServiceSuffix() {
        return this.m_bUseCustomServiceSuffix;
    }
    
    public void setUseServiceSuffix(final boolean bUseCustomServiceSuffix) {
        this.m_bUseCustomServiceSuffix = bUseCustomServiceSuffix;
    }
    
    public boolean hasConnectReturnString() {
        return this.m_bhasConnectReturnString;
    }
    
    public void setHasConnectReturnString(final boolean bhasConnectReturnString) {
        this.m_bhasConnectReturnString = bhasConnectReturnString;
    }
    
    public String getServiceName() {
        return this.m_strServiceName;
    }
    
    public void setServiceName(final String strServiceName) {
        this.m_strServiceName = strServiceName;
    }
    
    public String getEntryEndpoint() {
        return this.m_strEntryEndpoint;
    }
    
    public void setEntryEndpoint(final String strEntryEndpoint) {
        this.m_strEntryEndpoint = strEntryEndpoint;
    }
    
    public boolean useDefaultEntryEndpoint() {
        return this.m_bUseDefaultEntryEndpoint;
    }
    
    public String getFaultEndpoint() {
        return this.m_strFaultEndpoint;
    }
    
    public void setFaultEndpoint(final String strFaultEndpoint) {
        this.m_strFaultEndpoint = strFaultEndpoint;
    }
    
    public boolean useDefaultFaultEndpoint() {
        return this.m_bUseDefaultFaultEndpoint;
    }
    
    public String getRejectedEndpoint() {
        return this.m_strRejectedEndpoint;
    }
    
    public void setRejectedEndpoint(final String strRejectedEndpoint) {
        this.m_strRejectedEndpoint = strRejectedEndpoint;
    }
    
    public boolean useDefaultRejectedEndpoint() {
        return this.m_bUseDefaultRejectedEndpoint;
    }
    
    public String getContainer() {
        return this.m_strContainerName;
    }
    
    public void setContainer(final String strContainerName) {
        this.m_strContainerName = strContainerName;
    }
    
    public String getAppserverURL() {
        return this.m_strAppserverURL;
    }
    
    public void setAppserverURL(final String strAppserverURL) {
        this.m_strAppserverURL = strAppserverURL;
    }
    
    public String getFilename() {
        return this.m_strFilename;
    }
    
    public void setFilename(final String strFilename) {
        this.m_strFilename = strFilename;
    }
    
    public boolean overwriteFiles() {
        return this.m_bOverwrite;
    }
    
    public void setOverwriteFiles(final boolean bOverwrite) {
        this.m_bOverwrite = bOverwrite;
    }
    
    public String getResourceDir() {
        return this.m_strResourceDir;
    }
    
    public void setResourceDir(final String strResourceDir) {
        this.m_strResourceDir = strResourceDir;
    }
    
    public boolean getUseFileSystem() {
        return this.m_bUseFileSystem;
    }
    
    public void setUseFileSystem(final boolean bUseFileSystem) {
        this.m_bUseFileSystem = bUseFileSystem;
    }
    
    public boolean getDeployToDS() {
        return this.m_bDeployToDS;
    }
    
    public void setDeployToDS(final boolean bDeployToDS) {
        this.m_bDeployToDS = bDeployToDS;
    }
    
    public boolean getCreateXAR() {
        return this.m_bCreateXAR;
    }
    
    public void setCreateXAR(final boolean bCreateXAR) {
        this.m_bCreateXAR = bCreateXAR;
    }
    
    public String getSonicResourceDir() {
        return this.m_strSonicResourceDir;
    }
    
    public void setSonicResourceDir(final String strSonicResourceDir) {
        this.m_strSonicResourceDir = strSonicResourceDir;
    }
    
    public boolean getSonicOverwriteFiles() {
        return this.m_bSonicOverwriteFiles;
    }
    
    public void setSonicOverwriteFiles(final boolean bSonicOverwriteFiles) {
        this.m_bSonicOverwriteFiles = bSonicOverwriteFiles;
    }
    
    public String getSonicXARName() {
        return this.m_strXARName;
    }
    
    public void setSonicXARName(final String strXARName) {
        this.m_strXARName = strXARName;
    }
    
    public String getSonicURL() {
        return this.m_sonicURL;
    }
    
    public void setSonicURL(final String sonicURL) {
        this.m_sonicURL = sonicURL;
    }
    
    public String getSonicDomain() {
        return this.m_sonicDomain;
    }
    
    public void setSonicDomain(final String sonicDomain) {
        this.m_sonicDomain = sonicDomain;
    }
    
    public String getSonicUser() {
        return this.m_sonicUser;
    }
    
    public void setSonicUser(final String sonicUser) {
        this.m_sonicUser = sonicUser;
    }
    
    public String getSonicPassword() {
        return this.m_sonicPassword;
    }
    
    public void setSonicPassword(final String sonicPassword) {
        this.m_sonicPassword = sonicPassword;
    }
    
    public static String valWebServiceNameSpace(final String s) {
        String msg = null;
        if (s == null || s.length() == 0) {
            msg = getMsg(8099442454849133699L, new Object[] { getResources().getTranString("PG_Namespace") });
        }
        return msg;
    }
    
    public static String valSoapEndPointURL(final String s) {
        String msg = null;
        if (s == null || s.length() == 0) {
            msg = getMsg(8099442454849133699L, new Object[] { getResources().getTranString("PG_WSAURL") });
        }
        return msg;
    }
    
    static ProgressResources getResources() {
        return DWGenInfo.m_resources;
    }
    
    static String getMsg(final long n, final Object[] array) {
        return new Open4GLException(n, array).getMessage();
    }
    
    public void writeXML(final XMLSerializer xmlSerializer, final String s, final String str) throws SAXException {
        boolean b = false;
        if (str != null && str.length() > 0) {
            b = true;
        }
        try {
            String string;
            if (b) {
                string = str + ":CurrentEncoding";
            }
            else {
                string = "CurrentEncoding";
            }
            xmlSerializer.startElement(s, "CurrentEncoding", string, (Attributes)null);
            final String string2 = Integer.toString(this.m_curEncoding);
            ((BaseMarkupSerializer)xmlSerializer).characters(string2.toCharArray(), 0, string2.length());
            xmlSerializer.endElement(s, "CurrentEncoding", string);
            if (this.m_strSoapEndPointURL != null) {
                String string3;
                if (b) {
                    string3 = str + ":SoapEndpointURL";
                }
                else {
                    string3 = "SoapEndpointURL";
                }
                xmlSerializer.startElement(s, "SoapEndpointURL", string3, (Attributes)null);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strSoapEndPointURL.toCharArray(), 0, this.m_strSoapEndPointURL.length());
                xmlSerializer.endElement(s, "SoapEndpointURL", string3);
            }
            if (this.m_strWebServiceNameSpace != null) {
                final AttributesImpl attributesImpl = new AttributesImpl();
                attributesImpl.addAttribute("", "userDefined", "userDefined", "CDATA", this.m_bUserDefinedWSNamespace ? "true" : "false");
                String string4;
                if (b) {
                    string4 = str + ":WebServiceNamespace";
                }
                else {
                    string4 = "WebServiceNamespace";
                }
                xmlSerializer.startElement(s, "WebServiceNamespace", string4, (Attributes)attributesImpl);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strWebServiceNameSpace.toCharArray(), 0, this.m_strWebServiceNameSpace.length());
                xmlSerializer.endElement(s, "WebServiceNamespace", string4);
            }
            final AttributesImpl attributesImpl2 = new AttributesImpl();
            attributesImpl2.addAttribute("", "appendToSoapAction", "appendToSoapAction", "CDATA", this.m_bAppendToSoapAction ? "true" : "false");
            String string5;
            if (b) {
                string5 = str + ":SoapAction";
            }
            else {
                string5 = "SoapAction";
            }
            xmlSerializer.startElement(s, "SoapAction", string5, (Attributes)attributesImpl2);
            if (this.m_bUseUserDefinedSoapAction && this.m_strSoapAction != null) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strSoapAction.toCharArray(), 0, this.m_strSoapAction.length());
            }
            xmlSerializer.endElement(s, "SoapAction", string5);
            if (this.m_bUseCustomPortBindSuffix) {
                final AttributesImpl attributesImpl3 = new AttributesImpl();
                attributesImpl3.addAttribute("", "userDefined", "userDefined", "CDATA", this.m_bUseCustomPortBindSuffix ? "true" : "false");
                String string6;
                if (b) {
                    string6 = str + ":PortTypeBindingSuffix";
                }
                else {
                    string6 = "PortTypeBindingSuffix";
                }
                xmlSerializer.startElement(s, "PortTypeBindingSuffix", string6, (Attributes)attributesImpl3);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strPortBindSuffix.toCharArray(), 0, this.m_strPortBindSuffix.length());
                xmlSerializer.endElement(s, "PortTypeBindingSuffix", string6);
            }
            if (this.m_bUseCustomServiceSuffix) {
                final AttributesImpl attributesImpl4 = new AttributesImpl();
                attributesImpl4.addAttribute("", "userDefined", "userDefined", "CDATA", this.m_bUseCustomServiceSuffix ? "true" : "false");
                String string7;
                if (b) {
                    string7 = str + ":ServiceSuffix";
                }
                else {
                    string7 = "ServiceSuffix";
                }
                xmlSerializer.startElement(s, "ServiceSuffix", string7, (Attributes)attributesImpl4);
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strServiceSuffix.toCharArray(), 0, this.m_strServiceSuffix.length());
                xmlSerializer.endElement(s, "ServiceSuffix", string7);
            }
            final AttributesImpl attributesImpl5 = new AttributesImpl();
            attributesImpl5.addAttribute("", "userDefined", "userDefined", "CDATA", this.m_bhasConnectReturnString ? "true" : "false");
            String string8;
            if (b) {
                string8 = str + ":ConnectReturnString";
            }
            else {
                string8 = "ConnectReturnString";
            }
            xmlSerializer.startElement(s, "ConnectReturnString", string8, (Attributes)attributesImpl5);
            xmlSerializer.endElement(s, "ConnectReturnString", string8);
            final AttributesImpl attributesImpl6 = new AttributesImpl();
            attributesImpl6.addAttribute("", "RPCEncoded", "RPCEncoded", "CDATA", this.m_bRPCEncoded ? "true" : "false");
            attributesImpl6.addAttribute("", "RPCLiteral", "RPCLiteral", "CDATA", this.m_bRPCLiteral ? "true" : "false");
            attributesImpl6.addAttribute("", "DocLiteral", "DocLiteral", "CDATA", this.m_bDocLiteral ? "true" : "false");
            String string9;
            if (b) {
                string9 = str + ":TestWSDL";
            }
            else {
                string9 = "TestWSDL";
            }
            xmlSerializer.startElement(s, "TestWSDL", string9, (Attributes)attributesImpl6);
            xmlSerializer.endElement(s, "TestWSDL", string9);
            String string10;
            if (b) {
                string10 = str + ":ESBEncoding";
            }
            else {
                string10 = "ESBEncoding";
            }
            xmlSerializer.startElement(s, "ESBEncoding", string10, (Attributes)null);
            final String string11 = Integer.toString(this.m_esbEncoding);
            ((BaseMarkupSerializer)xmlSerializer).characters(string11.toCharArray(), 0, string11.length());
            xmlSerializer.endElement(s, "ESBEncoding", string10);
            final AttributesImpl attributesImpl7 = new AttributesImpl();
            attributesImpl7.addAttribute("", "useDefault", "useDefault", "CDATA", this.m_bUseDefaultEntryEndpoint ? "true" : "false");
            String string12;
            if (b) {
                string12 = str + ":EntryEndpoint";
            }
            else {
                string12 = "EntryEndpoint";
            }
            xmlSerializer.startElement(s, "EntryEndpoint", string12, (Attributes)attributesImpl7);
            if (!this.m_bUseDefaultEntryEndpoint) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strEntryEndpoint.toCharArray(), 0, this.m_strEntryEndpoint.length());
            }
            xmlSerializer.endElement(s, "EntryEndpoint", string12);
            final AttributesImpl attributesImpl8 = new AttributesImpl();
            attributesImpl8.addAttribute("", "useDefault", "useDefault", "CDATA", this.m_bUseDefaultFaultEndpoint ? "true" : "false");
            String string13;
            if (b) {
                string13 = str + ":FaultEndpoint";
            }
            else {
                string13 = "FaultEndpoint";
            }
            xmlSerializer.startElement(s, "FaultEndpoint", string13, (Attributes)attributesImpl8);
            if (!this.m_bUseDefaultFaultEndpoint) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strFaultEndpoint.toCharArray(), 0, this.m_strFaultEndpoint.length());
            }
            xmlSerializer.endElement(s, "FaultEndpoint", string13);
            final AttributesImpl attributesImpl9 = new AttributesImpl();
            attributesImpl9.addAttribute("", "useDefault", "useDefault", "CDATA", this.m_bUseDefaultRejectedEndpoint ? "true" : "false");
            String string14;
            if (b) {
                string14 = str + ":RejectedEndpoint";
            }
            else {
                string14 = "RejectedEndpoint";
            }
            xmlSerializer.startElement(s, "RejectedEndpoint", string14, (Attributes)attributesImpl9);
            if (!this.m_bUseDefaultRejectedEndpoint) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strRejectedEndpoint.toCharArray(), 0, this.m_strRejectedEndpoint.length());
            }
            xmlSerializer.endElement(s, "RejectedEndpoint", string14);
            String string15;
            if (b) {
                string15 = str + ":Container";
            }
            else {
                string15 = "Container";
            }
            xmlSerializer.startElement(s, "Container", string15, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strContainerName.toCharArray(), 0, this.m_strContainerName.length());
            xmlSerializer.endElement(s, "Container", string15);
            String string16;
            if (b) {
                string16 = str + ":AppserverURL";
            }
            else {
                string16 = "AppserverURL";
            }
            xmlSerializer.startElement(s, "AppserverURL", string16, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strAppserverURL.toCharArray(), 0, this.m_strAppserverURL.length());
            xmlSerializer.endElement(s, "AppserverURL", string16);
            final AttributesImpl attributesImpl10 = new AttributesImpl();
            attributesImpl10.addAttribute("", "overWrite", "overWrite", "CDATA", this.m_bOverwrite ? "true" : "false");
            String string17;
            if (b) {
                string17 = str + ":FileName";
            }
            else {
                string17 = "FileName";
            }
            xmlSerializer.startElement(s, "FileName", string17, (Attributes)attributesImpl10);
            ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strFilename.toCharArray(), 0, this.m_strFilename.length());
            xmlSerializer.endElement(s, "FileName", string17);
            String string18;
            if (b) {
                string18 = str + ":ResourceDirectory";
            }
            else {
                string18 = "ResourceDirectory";
            }
            xmlSerializer.startElement(s, "ResourceDirectory", string18, (Attributes)null);
            ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strResourceDir.toCharArray(), 0, this.m_strResourceDir.length());
            xmlSerializer.endElement(s, "ResourceDirectory", string18);
            String string19;
            if (b) {
                string19 = str + ":SonicAppService";
            }
            else {
                string19 = "SonicAppService";
            }
            final AttributesImpl attributesImpl11 = new AttributesImpl();
            attributesImpl11.addAttribute("", "useFileSystem", "useFileSystem", "CDATA", this.m_bUseFileSystem ? "true" : "false");
            attributesImpl11.addAttribute("", "deployToDS", "deployToDS", "CDATA", this.m_bDeployToDS ? "true" : "false");
            attributesImpl11.addAttribute("", "createXAR", "createXAR", "CDATA", this.m_bCreateXAR ? "true" : "false");
            xmlSerializer.startElement(s, "SonicAppService", string19, (Attributes)attributesImpl11);
            String string20;
            if (b) {
                string20 = str + ":ResourceDirectory";
            }
            else {
                string20 = "ResourceDirectory";
            }
            final AttributesImpl attributesImpl12 = new AttributesImpl();
            attributesImpl12.addAttribute("", "overWrite", "overWrite", "CDATA", this.m_bSonicOverwriteFiles ? "true" : "false");
            xmlSerializer.startElement(s, "ResourceDirectory", string20, (Attributes)attributesImpl12);
            if (null != this.m_strSonicResourceDir) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strSonicResourceDir.toCharArray(), 0, this.m_strSonicResourceDir.length());
            }
            xmlSerializer.endElement(s, "ResourceDirectory", string20);
            String string21;
            if (b) {
                string21 = str + ":XARName";
            }
            else {
                string21 = "XARName";
            }
            xmlSerializer.startElement(s, "XARName", string21, (Attributes)null);
            if (null != this.m_strXARName) {
                ((BaseMarkupSerializer)xmlSerializer).characters(this.m_strXARName.toCharArray(), 0, this.m_strXARName.length());
            }
            xmlSerializer.endElement(s, "XARName", string21);
            xmlSerializer.endElement(s, "SonicAppService", string21);
        }
        catch (SAXException ex) {
            throw ex;
        }
    }
    
    public void readXML(final Node node) {
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue = WsaParser.extractNodeValue(item);
                if (localName.equals("CurrentEncoding")) {
                    this.m_curEncoding = Integer.parseInt(nodeValue);
                }
                else if (localName.equals("SoapEndpointURL")) {
                    this.m_strSoapEndPointURL = nodeValue;
                }
                else if (localName.equals("WebServiceNamespace")) {
                    final String nodeValue2 = item.getAttributes().getNamedItem("userDefined").getNodeValue();
                    if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
                        this.m_bUserDefinedWSNamespace = true;
                    }
                    else {
                        this.m_bUserDefinedWSNamespace = false;
                    }
                    this.m_strWebServiceNameSpace = nodeValue;
                }
                else if (localName.equals("PortTypeBindingSuffix")) {
                    final String nodeValue3 = item.getAttributes().getNamedItem("userDefined").getNodeValue();
                    if (nodeValue3.equals("true") || nodeValue3.equals("1")) {
                        this.m_bUseCustomPortBindSuffix = true;
                    }
                    else {
                        this.m_bUseCustomPortBindSuffix = false;
                    }
                    this.m_strPortBindSuffix = nodeValue;
                }
                else if (localName.equals("ServiceSuffix")) {
                    final String nodeValue4 = item.getAttributes().getNamedItem("userDefined").getNodeValue();
                    if (nodeValue4.equals("true") || nodeValue4.equals("1")) {
                        this.m_bUseCustomServiceSuffix = true;
                    }
                    else {
                        this.m_bUseCustomServiceSuffix = false;
                    }
                    this.m_strServiceSuffix = nodeValue;
                }
                else if (localName.equals("SoapAction")) {
                    final String nodeValue5 = item.getAttributes().getNamedItem("appendToSoapAction").getNodeValue();
                    if (nodeValue5.equals("true") || nodeValue5.equals("1")) {
                        this.m_bAppendToSoapAction = true;
                    }
                    else {
                        this.m_bAppendToSoapAction = false;
                    }
                    this.m_strSoapAction = nodeValue;
                    if (this.m_strSoapAction.length() > 0) {
                        this.m_bUseUserDefinedSoapAction = true;
                    }
                    else {
                        this.m_strSoapAction = null;
                    }
                }
                else if (localName.equals("TestWSDL")) {
                    final NamedNodeMap attributes = item.getAttributes();
                    final String nodeValue6 = attributes.getNamedItem("RPCEncoded").getNodeValue();
                    if (nodeValue6.equals("true") || nodeValue6.equals("1")) {
                        this.m_bRPCEncoded = true;
                    }
                    else {
                        this.m_bRPCEncoded = false;
                    }
                    final String nodeValue7 = attributes.getNamedItem("RPCLiteral").getNodeValue();
                    if (nodeValue7.equals("true") || nodeValue7.equals("1")) {
                        this.m_bRPCLiteral = true;
                    }
                    else {
                        this.m_bRPCLiteral = false;
                    }
                    final String nodeValue8 = attributes.getNamedItem("DocLiteral").getNodeValue();
                    if (nodeValue8.equals("true") || nodeValue8.equals("1")) {
                        this.m_bDocLiteral = true;
                    }
                    else {
                        this.m_bDocLiteral = false;
                    }
                    final Node namedItem = attributes.getNamedItem("bGen");
                    if (namedItem != null) {
                        final String nodeValue9 = namedItem.getNodeValue();
                        if (nodeValue9.equals("true") || nodeValue9.equals("1")) {
                            this.setGenWSDL(true);
                        }
                        else {
                            this.setGenWSDL(false);
                        }
                    }
                }
                else if (localName.equals("ConnectReturnString")) {
                    final String nodeValue10 = item.getAttributes().getNamedItem("userDefined").getNodeValue();
                    if (nodeValue10.equals("true") || nodeValue10.equals("1")) {
                        this.m_bhasConnectReturnString = true;
                    }
                    else {
                        this.m_bhasConnectReturnString = false;
                    }
                }
                else if (localName.equals("ESBEncoding")) {
                    this.m_esbEncoding = Integer.parseInt(nodeValue);
                }
                else if (localName.equals("EntryEndpoint")) {
                    final String nodeValue11 = item.getAttributes().getNamedItem("useDefault").getNodeValue();
                    if (nodeValue11.equals("true") || nodeValue11.equals("1")) {
                        this.m_bUseDefaultEntryEndpoint = true;
                    }
                    else {
                        this.m_bUseDefaultEntryEndpoint = false;
                    }
                    if (!this.m_bUseDefaultEntryEndpoint) {
                        this.m_strEntryEndpoint = nodeValue;
                    }
                }
                else if (localName.equals("FaultEndpoint")) {
                    final String nodeValue12 = item.getAttributes().getNamedItem("useDefault").getNodeValue();
                    if (nodeValue12.equals("true") || nodeValue12.equals("1")) {
                        this.m_bUseDefaultFaultEndpoint = true;
                    }
                    else {
                        this.m_bUseDefaultFaultEndpoint = false;
                    }
                    if (!this.m_bUseDefaultFaultEndpoint) {
                        this.m_strFaultEndpoint = nodeValue;
                    }
                }
                else if (localName.equals("RejectedEndpoint")) {
                    final String nodeValue13 = item.getAttributes().getNamedItem("useDefault").getNodeValue();
                    if (nodeValue13.equals("true") || nodeValue13.equals("1")) {
                        this.m_bUseDefaultRejectedEndpoint = true;
                    }
                    else {
                        this.m_bUseDefaultRejectedEndpoint = false;
                    }
                    if (!this.m_bUseDefaultRejectedEndpoint) {
                        this.m_strRejectedEndpoint = nodeValue;
                    }
                }
                else if (localName.equals("Container")) {
                    this.m_strContainerName = nodeValue;
                }
                else if (localName.equals("AppserverURL")) {
                    this.m_strAppserverURL = nodeValue;
                }
                else if (localName.equals("FileName")) {
                    this.m_strFilename = nodeValue;
                    final String nodeValue14 = item.getAttributes().getNamedItem("overWrite").getNodeValue();
                    if (nodeValue14.equals("true") || nodeValue14.equals("1")) {
                        this.m_bOverwrite = true;
                    }
                    else {
                        this.m_bOverwrite = false;
                    }
                }
                else if (localName.equals("ResourceDirectory")) {
                    this.m_strResourceDir = nodeValue;
                }
                else if (localName.equals("SonicAppService")) {
                    this.parseSonicAppserviceInfo(item);
                }
                else if (localName.equals("SonicConnectionInfo")) {
                    this.parseSonicConnectionInfo(item);
                }
            }
        }
    }
    
    public void parseSonicConnectionInfo(final Node node) {
        final crypto crypto = new crypto();
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue = WsaParser.extractNodeValue(item);
                if (localName.equals("SonicURL")) {
                    this.m_sonicURL = nodeValue;
                }
                else if (localName.equals("SonicDomain")) {
                    this.m_sonicDomain = nodeValue;
                }
                else if (localName.equals("User")) {
                    this.m_sonicUser = nodeValue;
                }
                else if (localName.equals("Password")) {
                    this.m_sonicPassword = crypto.decrypt(nodeValue);
                }
            }
        }
    }
    
    public void parseSonicAppserviceInfo(final Node node) {
        final NamedNodeMap attributes = node.getAttributes();
        final String nodeValue = attributes.getNamedItem("useFileSystem").getNodeValue();
        if (nodeValue.equals("true") || nodeValue.equals("1")) {
            this.m_bUseFileSystem = true;
        }
        else {
            this.m_bUseFileSystem = false;
        }
        final String nodeValue2 = attributes.getNamedItem("deployToDS").getNodeValue();
        if (nodeValue2.equals("true") || nodeValue2.equals("1")) {
            this.m_bDeployToDS = true;
        }
        else {
            this.m_bDeployToDS = false;
        }
        final String nodeValue3 = attributes.getNamedItem("createXAR").getNodeValue();
        if (nodeValue3.equals("true") || nodeValue3.equals("1")) {
            this.m_bCreateXAR = true;
        }
        else {
            this.m_bCreateXAR = false;
        }
        final NodeList childNodes = node.getChildNodes();
        for (int length = childNodes.getLength(), i = 0; i < length; ++i) {
            final Node item = childNodes.item(i);
            if (item.getNodeType() == 1) {
                final String localName = item.getLocalName();
                final String nodeValue4 = WsaParser.extractNodeValue(item);
                if (localName.equals("ResourceDirectory")) {
                    this.m_strSonicResourceDir = nodeValue4;
                    final String nodeValue5 = item.getAttributes().getNamedItem("overWrite").getNodeValue();
                    if (nodeValue5.equals("true") || nodeValue5.equals("1")) {
                        this.m_bSonicOverwriteFiles = true;
                    }
                    else {
                        this.m_bSonicOverwriteFiles = false;
                    }
                }
                else if (localName.equals("XARName")) {
                    this.m_strXARName = nodeValue4;
                }
            }
        }
    }
    
    static {
        DWGenInfo.m_resources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.PGBundle");
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.esb.tools;

import java.util.Hashtable;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.sonicsw.xqimpl.util.DOMUtils;
import org.w3c.dom.Element;
import com.progress.wsa.admin.AppRuntimeProps;

public class EsbRuntimeProperties extends AppRuntimeProps
{
    protected String _ESB_LOGGING_ENTRY_TYPES;
    public static final String[] m_esbPropNames;
    
    public EsbRuntimeProperties() {
        this._ESB_LOGGING_ENTRY_TYPES = "ESBDefault";
        this.setIntProperty("PROGRESS.Session.sessionModel", 1);
        this.setStringProperty("PROGRESS.Session.serviceLoggingEntryTypes", this._ESB_LOGGING_ENTRY_TYPES);
    }
    
    public Element toDom() throws DOMException {
        final String targetNamespace = this.getTargetNamespace();
        final Document document = DOMUtils.createDocument();
        final Element elementNS = document.createElementNS(targetNamespace, this.getRoot());
        elementNS.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", targetNamespace);
        document.appendChild(elementNS);
        elementNS.appendChild(this.createPropNode(document, "runtimePropertyVersion", "runtimePropertyVersion", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.appServiceProtocol", "appServiceProtocol", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.appServiceHost", "appServiceHost", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.appServicePort", "appServicePort", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.appServiceName", "appServiceName", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.staleO4GLObjectTimeout", "staleO4GLObjectTimeout", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.minConnections", "minSessions", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.maxConnections", "maxSessions", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.initialConnections", "initialSessions", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.idleConnectionTimeout", "idleSessionTimeout", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.requestWaitTimeout", "requestWaitTimeout", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.nsClientMinPort", "nsClientMinPort", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.nsClientMaxPort", "nsClientMaxPort", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.nsClientPortRetry", "nsClientPortRetry", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.nsClientPortRetryInterval", "nsClientPortRetryInterval", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.nsClientPicklistSize", "nsClientPicklistSize", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.nsClientPicklistExpiration", "nsClientPicklistExpiration", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.serviceAvailable", "serviceAvailable", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.serviceLoggingLevel", "serviceLoggingLevel", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.serviceLoggingEntryTypes", "serviceLoggingEntryTypes", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.sessionModel", "appServiceConnectionMode", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.serviceFaultLevel", "serviceFaultLevel", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.waitIfBusy", "waitIfBusy", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.connectionLifetime", "connectionLifetime", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.minIdleConnections", "minIdleConnections", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.noHostVerify", "noHostVerify", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.noSslSessionReuse", "noSessionReuse", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.appServerKeepalive", "appServerKeepalive", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.clientASKActivityTimeout", "clientASKActivityTimeout", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.clientASKActivityTimeout", "clientASKResponseTimeout", targetNamespace));
        elementNS.appendChild(this.createPropNode(document, "PROGRESS.Session.actionalGroupName", "actionalGroupName", targetNamespace));
        return elementNS;
    }
    
    public Hashtable getESBProperties() throws Exception {
        return this.getAppProperties(EsbRuntimeProperties.m_esbPropNames);
    }
    
    private Element createPropNode(final Document document, final String s, final String s2, final String s3) {
        final String string = this.getProperty(s).toString();
        final Element elementNS = document.createElementNS(s3, s2);
        elementNS.appendChild(document.createTextNode(string));
        return elementNS;
    }
    
    static {
        m_esbPropNames = new String[] { "initialSessions", "minSessions", "maxSessions", "minIdleConnections", "idleSessionTimeout", "connectionLifetime", "staleO4GLObjectTimeout", "requestWaitTimeout", "waitIfBusy", "nsClientMinPort", "nsClientMaxPort", "nsClientPortRetry", "nsClientPortRetryInterval", "nsClientPicklistSize", "nsClientPicklistExpiration", "serviceLoggingLevel", "serviceLoggingEntryTypes", "noHostVerify", "noSessionReuse", "appServerKeepalive", "clientASKActivityTimeout", "clientASKResponseTimeout", "actionalGroupName" };
    }
}

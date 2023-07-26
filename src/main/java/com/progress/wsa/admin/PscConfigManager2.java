// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.admin;

import java.util.Enumeration;
import org.w3c.dom.Node;
import com.progress.wsa.tools.ListInfo;
import com.progress.wsa.WsaSOAPException;
import java.util.Hashtable;
import com.progress.common.ehnlog.AppLogger;

public class PscConfigManager2 extends PscConfigManager
{
    public void loadRegistry() throws WsaSOAPException {
        final String s = (String)super.m_context.get("psc.wsa.engine.type");
        if (s.equals("Adapter")) {
            super.loadRegistry();
            return;
        }
        if (s.equals("AppServer")) {
            super.loadRegistry();
            return;
        }
        if (s.equals("Container")) {
            final AppLogger appLogger = (AppLogger)super.m_context.get("psc.wsa.log");
            if (appLogger.ifLogVerbose(1L, 0)) {
                appLogger.logVerbose(0, "Initializing OpenEdge Sonic ESB Adapter");
            }
            super.m_parser = new WsaParser(super.m_context);
            super.dds = new Hashtable();
            final AppContainer default1 = AppContainer.createDefault(super.m_parser, null);
            default1.initRuntime(super.m_context, super.m_parser);
            default1.getRuntimeProperties().setIsDefaultProps(true);
            super.dds.put("urn:services-progress-com:wsa-default:0001", default1);
        }
    }
    
    public void saveRegistry() throws WsaSOAPException {
        final String s = (String)super.m_context.get("psc.wsa.engine.type");
        if (s.equals("Adapter")) {
            super.saveRegistry();
            return;
        }
        if (s.equals("AppServer")) {
            super.saveRegistry();
        }
    }
    
    public ListInfo installApp(final String friendlyName, String id, final String s, final Hashtable hashtable) throws WsaSOAPException {
        final AppContainer appContainer = new AppContainer(super.m_parser);
        try {
            appContainer.readXML(super.m_parser.parseStr(s, 3).getDocumentElement());
            appContainer.setContext(super.m_context);
            appContainer.setFriendlyName(friendlyName);
            if (id != null && id.length() > 0) {
                appContainer.setId(id);
            }
            else {
                id = appContainer.getId();
            }
            final AppRuntimeProps runtimeProperties = appContainer.getRuntimeProperties();
            final WSAD wsad = appContainer.getWSAD();
            if (wsad.getPGAppObj().getWSInfo().getPGGenInfo().getConnectionFree()) {
                runtimeProperties.setProperty("PROGRESS.Session.sessionModel", new Integer(1));
            }
            else {
                runtimeProperties.setProperty("PROGRESS.Session.sessionModel", new Integer(0));
            }
            appContainer.disableApp();
            final Enumeration keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                final String key = keys.nextElement();
                final String value = hashtable.get(key);
                if (key.equals("webServiceNamespace")) {
                    wsad.setWebServiceNamespace(value);
                }
                else if (key.equals("soapAction")) {
                    wsad.setSoapAction(value);
                }
                else if (key.equals("appendToSoapAction")) {
                    wsad.setAppendToSoapAction((boolean)value);
                }
                else {
                    runtimeProperties.setProperty(key, value);
                }
            }
            final AppLogger appLogger = (AppLogger)super.m_context.get("psc.wsa.log");
            if (appLogger.ifLogBasic(8L, 3)) {
                appLogger.logError(8607504787811871590L, new Object[] { friendlyName });
            }
            if (this.getApp(friendlyName) != null) {
                appLogger.logError(8607504787811871591L, new Object[] { friendlyName });
                throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871349L, new Object[] { friendlyName });
            }
            if (this.getApp(id) != null) {
                appLogger.logError(8607504787811871592L, new Object[] { id });
                throw new WsaSOAPException("SOAP-ENV:Server.Admin", 8607504787811871350L, new Object[] { id });
            }
            appContainer.enableApp();
            appContainer.setRuntimeStats(null);
            appContainer.initRuntime(super.m_context, super.m_parser);
            super.dds.put(appContainer.getId(), appContainer);
            super.m_friendlyApps.put(appContainer.getFriendlyName(), appContainer);
            super.m_serviceNamesCache = null;
        }
        catch (Throwable t) {
            throw new WsaSOAPException("SOAP-ENV:Server.Admin", "Unexpected Exception initializing adapter", t);
        }
        final ListInfo listInfo = new ListInfo();
        listInfo.setFriendlyName(appContainer.getFriendlyName());
        listInfo.setTargetURI(appContainer.getId());
        listInfo.setStatus(appContainer.getAppStatus());
        return listInfo;
    }
}

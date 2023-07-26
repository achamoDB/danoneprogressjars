// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import com.progress.common.log.ProLog;
import com.progress.wsa.admin.PingResponse;
import com.progress.wsa.tools.StatusInfo;
import com.progress.wsa.tools.WsaStatusInfo;
import com.progress.wsa.tools.PropElement;
import java.util.Hashtable;
import com.progress.wsa.admin.AppContainer;
import com.progress.ubroker.tools.IYodaSharedResources;
import com.progress.wsa.tools.QueryInfo;
import com.progress.wsa.tools.AdminStatus;
import org.w3c.dom.Document;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.chimera.common.IChimeraHierarchy;
import org.w3c.dom.Node;
import com.progress.common.ehnlog.AppLogger;
import com.progress.wsa.admin.WsaParser;
import com.progress.wsa.admin.WSAD;
import com.progress.wsa.tools.WsaLoginInfo;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import com.progress.wsa.tools.WsaPluginLog;
import com.progress.wsa.tools.ListInfo;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.chimera.common.NameContext;
import com.progress.wsa.admin.WebServerLoginContext;
import com.progress.wsa.admin.WsaAdminPlugin;
import java.util.Vector;
import com.progress.ubroker.tools.PropMgrPlugin;
import java.rmi.server.RemoteStub;
import com.progress.chimera.common.IChimeraRemoteCommand;
import com.progress.ubroker.tools.IYodaRMI;
import com.progress.ubroker.tools.IBTMsgCodes;
import com.progress.ubroker.tools.AbstractGuiToolRemObj;

public class WsaInstanceRemoteObject extends AbstractGuiToolRemObj implements IBTMsgCodes, IYodaRMI, IWsaCmdConst, IChimeraRemoteCommand
{
    RemoteStub m_stub;
    PropMgrPlugin pmp;
    String fullPropGroupPath;
    String urlVal;
    String wsAuthVal;
    String soapAction;
    WebServicesPersonality child;
    Vector webservices;
    WsaAdminPlugin soapClient;
    WebServerLoginContext wsCntxt;
    static final boolean DEBUG_TRACE = true;
    protected static NameContext nameTable;
    
    public WsaInstanceRemoteObject(final IYodaRMI yodaRMI, final String str, final String str2, final String s, final String s2, final String s3, final String s4, final String s5) throws RemoteException {
        super(yodaRMI, str, str2, s, s2, s3, s4, s5);
        this.m_stub = null;
        this.pmp = null;
        this.fullPropGroupPath = null;
        this.urlVal = "";
        this.wsAuthVal = null;
        this.soapAction = null;
        this.child = null;
        this.webservices = new Vector();
        this.soapClient = null;
        this.wsCntxt = null;
        this.m_stub = super.stub();
        this.pmp = ((WsaPluginRemoteObject)yodaRMI).m_pmpObject;
        this.fullPropGroupPath = str2 + "." + str;
        this.urlVal = this.pmp.getExpandedPropertyValue("wsaUrl", this.fullPropGroupPath);
        this.soapClient = new WsaAdminPlugin(this.urlVal);
        this.wsCntxt = new WebServerLoginContext();
        new WsaPropChangeListenerObject(this, this.m_stub);
        this.updateSecuritySettings();
    }
    
    protected NameContext getNameContext() {
        return WsaInstanceRemoteObject.nameTable;
    }
    
    public static WsaInstanceRemoteObject findWsaInstanceRemoteObject(final String s) {
        try {
            return WsaInstanceRemoteObject.nameTable.get(AbstractGuiToolRemObj.adjustName(s));
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public RemoteStub getRemStub() throws RemoteException {
        return this.m_stub;
    }
    
    public Enumeration getChildren() throws RemoteException {
        if (this.child == null) {
            this.child = new WebServicesPersonality(this);
        }
        final Vector<WebServicesPersonality> vector = new Vector<WebServicesPersonality>();
        vector.addElement(this.child);
        return new SerializableEnumeration(vector);
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.ubroker.wsa.WsaInstanceMMCClient";
    }
    
    public String getDisplayName() throws RemoteException {
        return super.m_svcName;
    }
    
    Vector getWebServices() {
        if (this.webservices.isEmpty()) {
            final Vector<ListInfo> vector = new Vector<ListInfo>();
            if (this.soapClient.list(vector).getStatus() != 0) {}
            for (int i = 0; i < vector.size(); ++i) {
                try {
                    this.webservices.addElement(new WebService(vector.get(i).getFriendlyName(), vector.get(i).getTargetURI(), this));
                }
                catch (RemoteException ex) {
                    WsaPluginLog.logError("Failed to create Web Service remote objects");
                }
            }
        }
        return this.webservices;
    }
    
    public ToolRemoteCmdStatus doRemoteToolCmd(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) throws RemoteException {
        final int command = toolRemoteCmdDescriptor.getCommand();
        final WSRemoteCmdStatus wsRemoteCmdStatus = (WSRemoteCmdStatus)((WsaPluginRemoteObject)super.m_yodaRMIGlue).checkServerState(command);
        if (wsRemoteCmdStatus != null) {
            return wsRemoteCmdStatus;
        }
        WSRemoteCmdStatus wsRemoteCmdStatus2 = null;
        try {
            if (command >= 1 && command < 50) {
                return super.m_yodaRMIGlue.doRemoteToolCmd(toolRemoteCmdDescriptor);
            }
            switch (command) {
                case 201: {
                    wsRemoteCmdStatus2 = this.deployWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 219: {
                    wsRemoteCmdStatus2 = this.undeployWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 202: {
                    wsRemoteCmdStatus2 = this.importWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 203: {
                    wsRemoteCmdStatus2 = this.exportWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 204: {
                    wsRemoteCmdStatus2 = this.enableWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 205: {
                    wsRemoteCmdStatus2 = this.disableWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 208: {
                    wsRemoteCmdStatus2 = this.updateWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 207: {
                    wsRemoteCmdStatus2 = this.testWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 211: {
                    wsRemoteCmdStatus2 = this.getRTDefaults((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 212: {
                    wsRemoteCmdStatus2 = this.udpateRTDefaults((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 213: {
                    wsRemoteCmdStatus2 = this.udpateOneRTDefault((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 214: {
                    wsRemoteCmdStatus2 = this.resetRTDefaults((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 215: {
                    wsRemoteCmdStatus2 = this.getRTProperties((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 216: {
                    wsRemoteCmdStatus2 = this.updateRTProperties((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 217: {
                    wsRemoteCmdStatus2 = this.udpateOneRTProp((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 218: {
                    wsRemoteCmdStatus2 = this.resetRTProperties((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 209: {
                    wsRemoteCmdStatus2 = this.getRTStats((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 210: {
                    wsRemoteCmdStatus2 = this.resetRTStats((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 200: {
                    wsRemoteCmdStatus2 = this.pingWsa((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 206: {
                    wsRemoteCmdStatus2 = this.queryWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                case 220: {
                    wsRemoteCmdStatus2 = this.listWS((WSRemoteCmdDescriptor)toolRemoteCmdDescriptor);
                    break;
                }
                default: {
                    wsRemoteCmdStatus2 = new WSRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
                    wsRemoteCmdStatus2.setErrorStatus(-2);
                    break;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            wsRemoteCmdStatus2 = new WSRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
            wsRemoteCmdStatus2.setDetailErrMsg("");
        }
        return wsRemoteCmdStatus2;
    }
    
    private WSRemoteCmdStatus deployWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final Enumeration argsList = wsRemoteCmdDescriptor.getArgsList();
        final WsaLoginInfo soapClientConnectContext = argsList.nextElement();
        String appObjectName = (String)argsList.nextElement();
        final StringBuffer sb = new StringBuffer();
        while (argsList.hasMoreElements()) {
            sb.append((String)argsList.nextElement());
        }
        wsRemoteCmdDescriptor.resetArgList();
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        if (sb.length() == 0 || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final WSAD wsad = new WSAD();
        try {
            final Document str = new WsaParser(System.getProperty("Install.Dir"), null).parseStr(sb.toString(), 0);
            if (str == null) {
                wsRemoteCmdStatus.setErrorStatus(-3);
                wsRemoteCmdStatus.setDetailStatusMsg("AdminServer unable to recognize WSM content");
                return wsRemoteCmdStatus;
            }
            wsad.readXML(str.getDocumentElement());
        }
        catch (Exception ex) {
            wsRemoteCmdStatus.setErrorStatus(-3);
            wsRemoteCmdStatus.setDetailStatusMsg("AdminServer unable to recognize WSM content");
            return wsRemoteCmdStatus;
        }
        if (appObjectName == null) {
            appObjectName = wsad.getPGAppObj().getAppObjectName();
        }
        final ListInfo listInfo = new ListInfo();
        if (!this.isAdminStatusSuccess(this.soapClient.deploy(wsad, listInfo, appObjectName), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        WsaPluginLog.logMsg("Deployed Web Service: " + appObjectName + "in WSA");
        final WebService ws = this.findWs(appObjectName);
        if (ws == null) {
            try {
                final WebService obj = new WebService(appObjectName, wsad.getTargetNamespace(), this);
                WsaPluginLog.logMsg("Adding child node " + obj.hashCode() + " " + appObjectName + " to parent " + this.hashCode() + " " + super.m_svcName);
                this.addChildNode(this.m_stub, this.child, obj);
                this.webservices.addElement(obj);
            }
            catch (Exception ex2) {
                final String msg = UBToolsMsg.getMsg(7094295313015381991L, new Object[] { "WebService", appObjectName, "WSA" });
                WsaPluginLog.logError(msg);
                wsRemoteCmdStatus.setDetailErrMsg(msg);
            }
        }
        else {
            ws.setName(appObjectName);
            ws.setNamespace(wsad.getTargetNamespace());
        }
        wsRemoteCmdStatus.setDeployWSStatus(listInfo.toString());
        return wsRemoteCmdStatus;
    }
    
    private boolean isAdminStatusSuccess(final AdminStatus adminStatus, final WSRemoteCmdStatus wsRemoteCmdStatus) {
        if (adminStatus.getStatus() != 0) {
            wsRemoteCmdStatus.setErrorStatus(adminStatus.getStatus(), "WSA request error: " + adminStatus.getFault());
            return false;
        }
        return true;
    }
    
    private WSRemoteCmdStatus undeployWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final Object fetchFirstArg = wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        if (fetchFirstArg == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        String displayName = "";
        IChimeraHierarchy ws = null;
        try {
            if (fetchFirstArg instanceof IChimeraHierarchy) {
                displayName = ((IChimeraHierarchy)fetchFirstArg).getDisplayName();
                ws = (IChimeraHierarchy)fetchFirstArg;
            }
            else if (fetchFirstArg instanceof String) {
                ws = this.findWs((String)fetchFirstArg);
                displayName = (String)fetchFirstArg;
            }
        }
        catch (Exception ex) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        if (displayName == "") {
            wsRemoteCmdStatus.setErrorStatus(-3);
            wsRemoteCmdStatus.setDetailStatusMsg("Cannot find Web Service -- it might be undeployed already.\nUndeploy cannot proceed.");
            return wsRemoteCmdStatus;
        }
        final QueryInfo queryInfo = new QueryInfo();
        if (!this.isAdminStatusSuccess(this.soapClient.query(displayName, queryInfo), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        if (queryInfo.getListInfo().getStatus() == 0) {
            wsRemoteCmdStatus.setErrorStatus(0);
            wsRemoteCmdStatus.setDetailErrMsg("Cannot undeploy an enabled Web Service.\nDisable first then undeploy.");
            return wsRemoteCmdStatus;
        }
        if (!this.isAdminStatusSuccess(this.soapClient.undeploy(displayName), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        WsaPluginLog.logMsg("Undeployed Web Service: " + displayName);
        if (ws == null) {
            wsRemoteCmdStatus.setSuccessStatus();
            return wsRemoteCmdStatus;
        }
        try {
            if (!((WsaPluginRemoteObject)this.getRemoteManageObject()).m_plugin.delChildNode(((IYodaSharedResources)ws).getRemStub(), ws, displayName, super.getEventStream(this.getPropGroupPath() + "." + displayName))) {
                wsRemoteCmdStatus.setDetailErrMsg("Cannot remove a tree node " + displayName + " from parent node " + super.m_svcName);
                return wsRemoteCmdStatus;
            }
        }
        catch (Exception ex2) {
            wsRemoteCmdStatus.setDetailErrMsg("Cannot remove a tree node " + displayName + " from parent node " + super.m_svcName);
            return wsRemoteCmdStatus;
        }
        for (int i = 0; i < this.webservices.size(); ++i) {
            if (displayName.equals(((WebService)this.webservices.elementAt(i)).getName())) {
                this.webservices.removeElementAt(i);
                wsRemoteCmdStatus.setSuccessStatus();
                return wsRemoteCmdStatus;
            }
        }
        wsRemoteCmdStatus.setDetailErrMsg("Error while updating AdminServer internals");
        return wsRemoteCmdStatus;
    }
    
    private WebService findWs(final String s) {
        final Enumeration<WebService> elements = this.webservices.elements();
        while (elements.hasMoreElements()) {
            final WebService webService = elements.nextElement();
            if (s.indexOf(":") != -1) {
                if (webService.getNamespace().equals(s)) {
                    return webService;
                }
                continue;
            }
            else {
                if (webService.getName().equals(s)) {
                    return webService;
                }
                continue;
            }
        }
        return null;
    }
    
    private WSRemoteCmdStatus importWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final Enumeration argsList = wsRemoteCmdDescriptor.getArgsList();
        final WsaLoginInfo soapClientConnectContext = argsList.nextElement();
        String friendlyName = (String)argsList.nextElement();
        final StringBuffer sb = new StringBuffer();
        while (argsList.hasMoreElements()) {
            sb.append((String)argsList.nextElement());
        }
        wsRemoteCmdDescriptor.resetArgList();
        if (sb.length() == 0 || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final AppContainer appContainer = new AppContainer();
        try {
            final Document str = new WsaParser(System.getProperty("Install.Dir"), null).parseStr(sb.toString(), 0);
            if (str == null) {
                wsRemoteCmdStatus.setErrorStatus(-3);
                wsRemoteCmdStatus.setDetailStatusMsg("AdminServer unable to recognize WSD content");
                return wsRemoteCmdStatus;
            }
            appContainer.readXML(str.getDocumentElement());
        }
        catch (Exception ex) {
            wsRemoteCmdStatus.setErrorStatus(-3);
            wsRemoteCmdStatus.setDetailStatusMsg("AdminServer unable to recognize WSD content");
            return wsRemoteCmdStatus;
        }
        if (friendlyName == null) {
            friendlyName = appContainer.getFriendlyName();
        }
        final ListInfo listInfo = new ListInfo();
        if (!this.isAdminStatusSuccess(this.soapClient.importApp(appContainer, listInfo), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        WsaPluginLog.logMsg("Imported Web Service: " + friendlyName);
        final WebService ws = this.findWs(friendlyName);
        Label_0478: {
            if (ws == null) {
                try {
                    final WebService obj = new WebService(friendlyName, appContainer.getTargetNamespace(), this);
                    WsaPluginLog.logMsg("Adding child node " + obj.hashCode() + " " + friendlyName + " to parent " + this.hashCode() + " " + super.m_svcName);
                    this.addChildNode(this.m_stub, this.child, obj);
                    this.webservices.addElement(obj);
                    wsRemoteCmdStatus.setImportWSStatus(listInfo.toString());
                    return wsRemoteCmdStatus;
                }
                catch (Exception ex2) {
                    WsaPluginLog.logError(UBToolsMsg.getMsg(7094295313015381991L, new Object[] { "WebService", friendlyName, "WSA" }));
                    break Label_0478;
                }
            }
            ws.setName(friendlyName);
            ws.setNamespace(appContainer.getTargetNamespace());
        }
        wsRemoteCmdStatus.setImportWSStatus(listInfo.toString());
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus exportWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = wsRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        if (fetchAddNewGuiToolArg == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final AppContainer appContainer = new AppContainer();
        if (!this.isAdminStatusSuccess(this.soapClient.exportApp(fetchAddNewGuiToolArg, appContainer), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        final String string = appContainer.toString();
        int n = string.length() / 65535;
        if (string.length() % 65535 > 0) {
            ++n;
        }
        final String[] exportWSStatus = new String[n];
        for (int i = 0; i < n; ++i) {
            if (i == n - 1) {
                exportWSStatus[i] = string.substring(i * 65535);
            }
            else {
                exportWSStatus[i] = string.substring(i * 65535, (i + 1) * 65535);
            }
        }
        wsRemoteCmdStatus.setExportWSStatus(exportWSStatus);
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus enableWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = wsRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        if (fetchAddNewGuiToolArg == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        if (!this.isAdminStatusSuccess(this.soapClient.enable(fetchAddNewGuiToolArg), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus disableWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        if (s == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        if (!this.isAdminStatusSuccess(this.soapClient.disable(s), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus queryWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        if (s == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final QueryInfo queryInfo = new QueryInfo();
        if (!this.isAdminStatusSuccess(this.soapClient.query(s, queryInfo), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setQueryWSStatus(queryInfo.toString());
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus updateWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final Enumeration argsList = wsRemoteCmdDescriptor.getArgsList();
        final WsaLoginInfo soapClientConnectContext = argsList.nextElement();
        final String s = (String)argsList.nextElement();
        final StringBuffer sb = new StringBuffer();
        while (argsList.hasMoreElements()) {
            sb.append((String)argsList.nextElement());
        }
        wsRemoteCmdDescriptor.resetArgList();
        if (sb.length() == 0 || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final WSAD wsad = new WSAD();
        try {
            final Document str = new WsaParser(System.getProperty("Install.Dir"), null).parseStr(sb.toString(), 0);
            if (str == null) {
                wsRemoteCmdStatus.setErrorStatus(-3);
                wsRemoteCmdStatus.setDetailStatusMsg("AdminServer unable to recognize WSM content");
                return wsRemoteCmdStatus;
            }
            wsad.readXML(str.getDocumentElement());
        }
        catch (Exception ex) {
            wsRemoteCmdStatus.setErrorStatus(-3);
            wsRemoteCmdStatus.setDetailStatusMsg("AdminServer unable to recognize WSM content");
            return wsRemoteCmdStatus;
        }
        final ListInfo listInfo = new ListInfo();
        if (!this.isAdminStatusSuccess(this.soapClient.update(wsad, s, listInfo), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setUpdateWSStatus(listInfo.toString());
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus testWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus resetRTProperties(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        if (s == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        if (!this.isAdminStatusSuccess(this.soapClient.resetRTProps(s), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus updateRTProperties(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final Vector wsaProperties = (Vector)wsRemoteCmdDescriptor.fetchSecondArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchThirdArg();
        if (wsaProperties == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final AdminStatus adminStatus = new AdminStatus(-1);
        AdminStatus adminStatus2;
        if (s == null) {
            adminStatus2 = this.soapClient.setWsaProperties(wsaProperties);
        }
        else {
            adminStatus2 = this.soapClient.setProperties(s, wsaProperties);
        }
        if (!this.isAdminStatusSuccess(adminStatus2, wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus udpateOneRTProp(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final String s2 = (String)wsRemoteCmdDescriptor.fetchSecondArg();
        final String s3 = (String)wsRemoteCmdDescriptor.fetchThirdArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchFourthArg();
        if (s2 == null || s3 == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final AdminStatus adminStatus = new AdminStatus(-1);
        AdminStatus adminStatus2;
        if (s == null) {
            adminStatus2 = this.soapClient.setWSAProperties(s2, s3);
        }
        else {
            adminStatus2 = this.soapClient.setProperties(s, s2, s3);
        }
        if (!this.isAdminStatusSuccess(adminStatus2, wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus resetRTDefaults(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchFirstArg();
        if (soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        if (!this.isAdminStatusSuccess(this.soapClient.resetDefaults(), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus udpateOneRTDefault(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final String s2 = (String)wsRemoteCmdDescriptor.fetchSecondArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchThirdArg();
        if (s == null || s2 == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        if (!this.isAdminStatusSuccess(this.soapClient.setdefaults(s, s2), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus udpateRTDefaults(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final Vector vector = (Vector)wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        if (vector == null || soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        if (!this.isAdminStatusSuccess(this.soapClient.setdefaults(vector), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus getRTDefaults(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchFirstArg();
        if (soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final Hashtable hashtable = new Hashtable();
        if (!this.isAdminStatusSuccess(this.soapClient.getDefaults(hashtable), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setGetRTDefaultsStatus(this.convertPropsH2V(hashtable));
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus getRTProperties(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        final Hashtable hashtable = new Hashtable();
        final AdminStatus adminStatus = new AdminStatus(-1);
        if (soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        AdminStatus adminStatus2;
        if (s == null) {
            adminStatus2 = this.soapClient.getWSAProperties(hashtable);
        }
        else {
            adminStatus2 = this.soapClient.getProperties(s, hashtable);
        }
        if (!this.isAdminStatusSuccess(adminStatus2, wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setGetRTPropertiesStatus(this.convertPropsH2V(hashtable));
        return wsRemoteCmdStatus;
    }
    
    private Vector convertPropsH2V(final Hashtable hashtable) {
        if (hashtable == null) {
            return null;
        }
        final Vector<PropElement> vector = new Vector<PropElement>(21);
        final Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            final String key = (String)keys.nextElement();
            final Integer value = hashtable.get(key);
            if (!key.equals("runtimePropertyVersion")) {
                if (key.equals("PROGRESS.Session.serviceAvailable")) {
                    continue;
                }
                String s;
                if (value instanceof String) {
                    s = (String)value;
                }
                else if (value instanceof Integer) {
                    s = value.toString();
                }
                else if (value instanceof Boolean) {
                    s = ((Boolean)(Object)value).toString();
                }
                else if (value instanceof Long) {
                    s = ((Long)value).toString();
                }
                else {
                    s = value.toString();
                }
                vector.addElement(new PropElement(key, s));
            }
        }
        return vector;
    }
    
    private WSRemoteCmdStatus getRTStats(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        final WsaStatusInfo wsaStatusInfo = new WsaStatusInfo();
        final StatusInfo statusInfo = new StatusInfo();
        final AdminStatus adminStatus = new AdminStatus(-1);
        if (soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        AdminStatus adminStatus2;
        if (s == null) {
            adminStatus2 = this.soapClient.wsaStats(wsaStatusInfo);
        }
        else {
            adminStatus2 = this.soapClient.getStats(s, statusInfo);
        }
        if (!this.isAdminStatusSuccess(adminStatus2, wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setGetRTStatsStatus((s == null) ? wsaStatusInfo.toString() : statusInfo.toString());
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus resetRTStats(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final String s = (String)wsRemoteCmdDescriptor.fetchFirstArg();
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchSecondArg();
        final WsaStatusInfo wsaStatusInfo = new WsaStatusInfo();
        final StatusInfo statusInfo = new StatusInfo();
        final AdminStatus adminStatus = new AdminStatus(-1);
        if (soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        AdminStatus adminStatus2;
        if (s == null) {
            adminStatus2 = this.soapClient.resetWsaStats();
        }
        else {
            adminStatus2 = this.soapClient.resetStats(s);
        }
        if (!this.isAdminStatusSuccess(adminStatus2, wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        wsRemoteCmdStatus.setSuccessStatus();
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus pingWsa(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchFirstArg();
        if (soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final PingResponse pingResponse = new PingResponse();
        final AdminStatus pingWSA = this.soapClient.pingWSA(pingResponse);
        if (pingWSA.getStatus() == 0) {
            wsRemoteCmdStatus.setDetailStatusMsg(pingResponse.toString());
            wsRemoteCmdStatus.setPingWsaStatus(pingResponse);
        }
        else if (pingWSA.getStatus() == -3) {
            wsRemoteCmdStatus.setDetailStatusMsg(pingResponse.toString());
            wsRemoteCmdStatus.setPingWsaStatus(pingResponse);
        }
        else {
            wsRemoteCmdStatus.setErrorStatus(pingWSA.getStatus(), "WSA request error: " + pingWSA.getFault());
        }
        return wsRemoteCmdStatus;
    }
    
    private WSRemoteCmdStatus listWS(final WSRemoteCmdDescriptor wsRemoteCmdDescriptor) {
        final WSRemoteCmdStatus wsRemoteCmdStatus = new WSRemoteCmdStatus(wsRemoteCmdDescriptor.getCommand());
        final WsaLoginInfo soapClientConnectContext = (WsaLoginInfo)wsRemoteCmdDescriptor.fetchFirstArg();
        if (soapClientConnectContext == null) {
            wsRemoteCmdStatus.setErrorStatus(-3, "MISSING_ARG");
            return wsRemoteCmdStatus;
        }
        this.setSoapClientConnectContext(soapClientConnectContext);
        final Vector<ListInfo> vector = new Vector<ListInfo>();
        if (!this.isAdminStatusSuccess(this.soapClient.list(vector), wsRemoteCmdStatus)) {
            return wsRemoteCmdStatus;
        }
        final Enumeration<WebService> elements = this.webservices.elements();
        while (elements.hasMoreElements()) {
            final WebService webService = elements.nextElement();
            try {
                final String displayName = webService.getDisplayName();
                if (!((WsaPluginRemoteObject)this.getRemoteManageObject()).m_plugin.delChildNode(webService.getRemStub(), webService, displayName, super.getEventStream(this.getPropGroupPath() + "." + displayName))) {
                    wsRemoteCmdStatus.setDetailErrMsg("Cannot remove " + displayName + " from the old list");
                    return wsRemoteCmdStatus;
                }
                continue;
            }
            catch (Exception ex) {
                wsRemoteCmdStatus.setErrorStatus(-1);
                wsRemoteCmdStatus.setDetailErrMsg("Exception caught while removing the old list.");
                return wsRemoteCmdStatus;
            }
        }
        this.webservices.removeAllElements();
        final Enumeration<ListInfo> elements2 = vector.elements();
        final StringBuffer sb = new StringBuffer();
        while (elements2.hasMoreElements()) {
            final ListInfo listInfo = elements2.nextElement();
            final String friendlyName = listInfo.getFriendlyName();
            try {
                final WebService obj = new WebService(friendlyName, listInfo.getTargetURI(), this);
                WsaPluginLog.logMsg("Adding child node " + obj.hashCode() + " " + friendlyName + " to parent " + this.hashCode() + " " + super.m_svcName);
                final WsaGuiPlugin plugin = ((WsaPluginRemoteObject)this.getRemoteManageObject()).m_plugin;
                if (!this.addChildNode(this.m_stub, this.child, obj)) {
                    wsRemoteCmdStatus.setDetailErrMsg("Cannot add tree node" + friendlyName + " to parent node " + super.m_svcName);
                    return wsRemoteCmdStatus;
                }
                this.webservices.addElement(obj);
                sb.append(listInfo.toString() + "\n");
            }
            catch (Exception ex2) {
                wsRemoteCmdStatus.setDetailErrMsg("Exception caught while adding the new list.");
                return wsRemoteCmdStatus;
            }
        }
        wsRemoteCmdStatus.setListWSStatus(sb.toString());
        return wsRemoteCmdStatus;
    }
    
    public void updateSecuritySettings() {
        this.urlVal = this.pmp.getExpandedPropertyValue("wsaUrl", this.fullPropGroupPath);
        this.wsAuthVal = this.pmp.getExpandedPropertyValue("webServerAuth", this.fullPropGroupPath);
        this.soapAction = this.pmp.getExpandedPropertyValue("adminSoapAction", this.fullPropGroupPath);
        final String expandedPropertyValue = this.pmp.getExpandedPropertyValue("proxyHost", this.fullPropGroupPath);
        final String expandedPropertyValue2 = this.pmp.getExpandedPropertyValue("proxyPort", this.fullPropGroupPath);
        final String expandedPropertyValue3 = this.pmp.getExpandedPropertyValue("proxyUsername", this.fullPropGroupPath);
        final String expandedPropertyValue4 = this.pmp.getExpandedPropertyValue("proxyPassword", this.fullPropGroupPath);
        final boolean b = expandedPropertyValue != null && !expandedPropertyValue.trim().equals("");
        if (this.wsCntxt == null) {
            this.wsCntxt = new WebServerLoginContext();
        }
        this.wsCntxt.setProxyInfo(expandedPropertyValue, expandedPropertyValue2, expandedPropertyValue3, expandedPropertyValue4, b);
    }
    
    private void setSoapClientConnectContext(final WsaLoginInfo wsaLoginInfo) {
        if (this.wsAuthVal == null) {
            return;
        }
        String s = null;
        String s2 = null;
        if (wsaLoginInfo.getBypassPropChecking()) {
            s = wsaLoginInfo.getWsaUsername();
            s2 = wsaLoginInfo.getWsaPwd();
        }
        else {
            final int int1 = Integer.parseInt(this.wsAuthVal);
            if (int1 == 1) {
                s = wsaLoginInfo.getAsUsername();
                s2 = wsaLoginInfo.getAsPwd();
            }
            else if (int1 == 2) {
                s = wsaLoginInfo.getWsUsername();
                s2 = wsaLoginInfo.getWsPwd();
            }
        }
        this.wsCntxt.setWsLoginInfo(s, s2);
        this.soapClient.setWebServerLogin(s, s2);
        this.soapClient.setWsaUrl(this.urlVal);
        this.soapClient.setProtocolOptions("psc.soapheader", this.soapAction);
        this.soapClient.setConnectContext(this.wsCntxt);
    }
    
    public void setSystemInput(final String s) throws RemoteException {
    }
    
    public int runIt(final String[] array) throws RemoteException {
        return 0;
    }
    
    public String getSystemOutput() throws RemoteException {
        return null;
    }
    
    public String getSystemError() throws RemoteException {
        return null;
    }
    
    public Hashtable getStructuredSystemOutput() throws RemoteException {
        return null;
    }
    
    public boolean addChildNode(final RemoteStub remoteStub, IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2) {
        boolean addChildNode = false;
        try {
            final Hashtable<String, String> hashtable = new Hashtable<String, String>();
            if (chimeraHierarchy == null) {
                chimeraHierarchy = new WebServicesPersonality(this);
            }
            final WebServicesPersonality webServicesPersonality = (WebServicesPersonality)chimeraHierarchy;
            final WsaInstanceRemoteObject wsaInstanceRemoteObject = (webServicesPersonality != null) ? webServicesPersonality.getParent() : null;
            final String value = (wsaInstanceRemoteObject != null) ? wsaInstanceRemoteObject.getDisplayName() : null;
            if (value != null) {
                hashtable.put("parentName", value);
            }
            addChildNode = ((WsaPluginRemoteObject)super.m_yodaRMIGlue).m_plugin.addChildNode(remoteStub, chimeraHierarchy, chimeraHierarchy2, hashtable, this.getEventStream((chimeraHierarchy2 != null) ? (this.getPropGroupPath() + "." + chimeraHierarchy2.getDisplayName()) : this.getPropGroupPath()));
        }
        catch (Exception ex) {
            ProLog.logd("WSA", 3, "Error posting add child event: " + ex.getMessage());
        }
        return addChildNode;
    }
    
    static {
        WsaInstanceRemoteObject.nameTable = new NameContext();
    }
}

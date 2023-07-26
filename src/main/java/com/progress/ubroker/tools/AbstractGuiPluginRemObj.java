// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.tools.events.EStopServiceEvent;
import com.progress.ubroker.tools.events.EStartServiceEvent;
import com.progress.ubroker.management.events.EAbnormalShutdownEvent;
import com.progress.common.networkevents.EventListener;
import java.io.File;
import com.progress.ubroker.util.CfgValidateErrs;
import com.progress.ubroker.util.PropertiesSaveDescriptor;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import java.rmi.server.RemoteStub;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventListener;
import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventInterestObject;
import java.util.Hashtable;
import java.util.Vector;
import com.progress.chimera.adminserver.ServerPolicyInfo;
import com.progress.ubroker.util.IToolCmdConst;
import com.progress.ubroker.util.IPropConst;
import java.io.FilenameFilter;
import com.progress.chimera.common.IChimeraHierarchy;

public abstract class AbstractGuiPluginRemObj implements IChimeraHierarchy, IPMUAccessCallback, IYodaRMI, IYodaSharedResources, FilenameFilter, IBTMsgCodes, IPropConst, IToolCmdConst
{
    AbstractGuiPlugin m_parentPlugin;
    public String m_personality;
    public String m_userName;
    public String m_password;
    public String m_groupPath;
    public ServerPolicyInfo m_policyInfo;
    public String m_rmiHost;
    public String m_rmiPort;
    public String m_osName;
    public String m_adminSrvrURL;
    public String[] m_newChildrenNames;
    public Vector m_childrenNames;
    public Vector m_childrenRemoteObjects;
    public Vector m_nsChildrenNames;
    public PropMgrPlugin m_pmpObject;
    public Hashtable m_controlCmdCache;
    public Hashtable m_eventStreamCache;
    public int m_numChildren;
    public boolean m_refreshPropertiesASAP;
    public boolean m_configChanged;
    public boolean m_serviceContainerClient;
    public IEventInterestObject m_startSvcEieio;
    public IEventInterestObject m_stopSvcEieio;
    public boolean m_eieioInited;
    
    public AbstractGuiPluginRemObj(final AbstractGuiPlugin parentPlugin, final String personality, final PropMgrPlugin pmpObject) throws RemoteException {
        this.m_parentPlugin = null;
        this.m_personality = null;
        this.m_userName = null;
        this.m_password = null;
        this.m_groupPath = null;
        this.m_policyInfo = null;
        this.m_rmiHost = null;
        this.m_rmiPort = null;
        this.m_osName = null;
        this.m_adminSrvrURL = null;
        this.m_newChildrenNames = null;
        this.m_childrenNames = null;
        this.m_childrenRemoteObjects = null;
        this.m_nsChildrenNames = null;
        this.m_pmpObject = null;
        this.m_controlCmdCache = new Hashtable();
        this.m_eventStreamCache = new Hashtable();
        this.m_numChildren = 0;
        this.m_refreshPropertiesASAP = false;
        this.m_configChanged = false;
        this.m_serviceContainerClient = true;
        this.m_startSvcEieio = null;
        this.m_stopSvcEieio = null;
        this.m_eieioInited = false;
        this.m_personality = personality;
        this.m_osName = this.getOsName();
        this.m_parentPlugin = parentPlugin;
        this.m_policyInfo = this.m_parentPlugin.m_serverPolicyInfo;
        this.m_rmiHost = this.m_parentPlugin.m_rmiHost;
        this.m_rmiPort = this.m_parentPlugin.m_rmiPort;
        this.m_childrenRemoteObjects = new Vector();
        this.m_childrenNames = new Vector();
        (this.m_pmpObject = pmpObject).registerUser(this);
        this.m_groupPath = this.m_pmpObject.getParentGroupValue(this.m_personality);
        if (!this.m_personality.equals("Messengers")) {
            this.expressInterestEvents();
        }
    }
    
    public Hashtable getData(final String str, final String[] array) {
        Hashtable data = null;
        try {
            data = ((SvcControlCmd)this.lookupControlCmd(str)).getData(array);
            return data;
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Broker not started: " + str + ". Cannot get stats data.");
            return data;
        }
    }
    
    public Enumeration getChildren() throws RemoteException {
        return null;
    }
    
    public String getDisplayName() throws RemoteException {
        return this.m_personality;
    }
    
    public String getHelpMapFile() throws RemoteException {
        return null;
    }
    
    public String getApplicationName() throws RemoteException {
        return null;
    }
    
    public synchronized void expressInterestEvents() {
        if (this.m_eieioInited) {
            return;
        }
        try {
            this.expressInterestStartSvcEvent();
            this.expressInterestStopSvcEvent();
            this.expressInterestCrashEvent();
            this.m_eieioInited = true;
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Exception in AbstractGuiPluginRemobj.ExpressInterestEvents()" + ex.toString());
        }
    }
    
    public void expressInterestCrashEvent() {
        try {
            this.getEventBroker().expressInterest(EAbnormalShutdownEvent.class, new UBrokerAbnormalShutdownEventListener(this), this.getEventStream(this.m_personality));
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Exception in AbstractGuiPluginRemobj.expressInterestCrashEvent()" + ex.toString());
        }
    }
    
    public void expressInterestStartSvcEvent() {
        try {
            this.m_startSvcEieio = this.getEventBroker().expressInterest(EStartServiceEvent.class, this.createStartSvcEvtListener(this), this.getEventStream(this.m_personality));
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Exception in AbstractGuiPluginRemobj.expressInterestStartSvcEvent()" + ex.toString());
        }
    }
    
    public void expressInterestStopSvcEvent() {
        try {
            this.m_stopSvcEieio = this.getEventBroker().expressInterest(EStopServiceEvent.class, this.createStopSvcEvtListener(this), this.getEventStream(this.m_personality));
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Exception in AbstractGuiPluginRemobj.expressInterestStopSvcEvent()" + ex.toString());
        }
    }
    
    public void revokeEventInterest(final int n) {
        try {
            if (n == 2) {
                if (this.m_stopSvcEieio == null) {
                    return;
                }
                this.getEventBroker().revokeInterest(this.m_stopSvcEieio);
                this.m_stopSvcEieio = null;
            }
            else if (n == 1) {
                if (this.m_startSvcEieio == null) {
                    return;
                }
                this.getEventBroker().revokeInterest(this.m_startSvcEieio);
                this.m_startSvcEieio = null;
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Exception in AbstractGuiPluginRemobj.revokeEventInterest()" + ex.toString());
        }
    }
    
    public void getSvcChildren() {
        try {
            if (this.refreshPMPObjectIfChanged() || this.m_childrenNames.size() == 0) {
                this.m_newChildrenNames = this.m_pmpObject.getSvcNameForParentGrp(this.m_groupPath);
                this.m_configChanged = true;
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381970L, new Object[] { ex.getMessage() }));
        }
    }
    
    public String getMMCClientClass() throws RemoteException {
        return null;
    }
    
    public void setPropGroupPath(final String groupPath) {
        this.m_groupPath = groupPath;
    }
    
    public void setRMIUsrInfo(final String userName, final String password, final String rmiHost, final String rmiPort, final String adminSrvrURL) {
        this.m_userName = userName;
        this.m_password = password;
        this.m_rmiHost = rmiHost;
        this.m_rmiPort = rmiPort;
        this.m_adminSrvrURL = adminSrvrURL;
    }
    
    public synchronized boolean refreshPMPObjectIfChanged() {
        return true;
    }
    
    public IEventStream getEventStream(final String s) throws RemoteException {
        IEventStream openEventStream = this.m_eventStreamCache.get(s);
        if (openEventStream == null) {
            try {
                openEventStream = this.getEventBroker().openEventStream("EventStream_For_" + s);
                this.m_eventStreamCache.put(s, openEventStream);
            }
            catch (Exception ex) {
                UBToolsMsg.logException("Failed to open event stream for " + s + ": " + ex.toString());
            }
        }
        return openEventStream;
    }
    
    public String getPropGroupPath() throws RemoteException {
        return this.m_groupPath;
    }
    
    public String getAdminSrvrURL() throws RemoteException {
        return this.m_adminSrvrURL;
    }
    
    public String getAdminSrvrOSName() throws RemoteException {
        return this.m_osName;
    }
    
    public String getAdminSrvrHost() throws RemoteException {
        return this.m_rmiHost;
    }
    
    public String getAdminSrvrPort() throws RemoteException {
        return this.m_rmiPort;
    }
    
    public IPropertyManagerRemote getRPM() throws RemoteException {
        return AbstractGuiPlugin.getRPM();
    }
    
    public String getPropertyFilename() throws RemoteException {
        return AbstractGuiPlugin.getPropertyFilename();
    }
    
    public String getSchemaFilename() throws RemoteException {
        return AbstractGuiPlugin.getSchemaFilename();
    }
    
    public String[] getSchemaPropFnList() throws RemoteException {
        return AbstractGuiPlugin.getSchemaPropFnList();
    }
    
    public String[] getSchemaPropFilename() throws RemoteException {
        return AbstractGuiPlugin.getSchemaPropFnList();
    }
    
    public IEventBroker getEventBroker() throws RemoteException {
        return AbstractGuiPlugin.getEventBroker();
    }
    
    public IYodaRMI getRemoteManageObject() throws RemoteException {
        return this;
    }
    
    public String getSvcName() throws RemoteException {
        return null;
    }
    
    public String getAdminServerIPAddr() throws RemoteException {
        return AbstractGuiPlugin.getAdminServerIPAddr();
    }
    
    public String[] getLoginUserInfo() throws RemoteException {
        return new String[] { this.m_userName, this.m_password };
    }
    
    public boolean getRscLookedUp(final String s) throws RemoteException {
        return AbstractGuiPlugin.getRscLookedUp(s);
    }
    
    public void regRscLookedUp(final String s) throws RemoteException {
        AbstractGuiPlugin.regRscLookedUp(s);
    }
    
    public RemoteStub getRemStub() throws RemoteException {
        return null;
    }
    
    public Hashtable getLogFiles(final String s) throws RemoteException {
        return this.m_pmpObject.getLogFNList(s);
    }
    
    public ToolRemoteCmdStatus doRemoteToolCmd(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) throws RemoteException {
        final int command = toolRemoteCmdDescriptor.getCommand();
        toolRemoteCmdDescriptor.getVersion();
        final ToolRemoteCmdStatus checkServerState = this.checkServerState(command);
        if (checkServerState != null) {
            return checkServerState;
        }
        ToolRemoteCmdStatus toolRemoteCmdStatus = null;
        try {
            switch (command) {
                case 1: {
                    toolRemoteCmdStatus = this.getSvcConfig(toolRemoteCmdDescriptor);
                    break;
                }
                case 2: {
                    toolRemoteCmdStatus = this.saveSvcConfig(toolRemoteCmdDescriptor);
                    break;
                }
                case 3: {
                    toolRemoteCmdStatus = this.replaceProperties(toolRemoteCmdDescriptor);
                    break;
                }
                case 4: {
                    toolRemoteCmdStatus = this.getPreferenceProperties(toolRemoteCmdDescriptor);
                    break;
                }
                case 5: {
                    toolRemoteCmdStatus = this.handlePreferencePropertiesChanged(toolRemoteCmdDescriptor);
                    break;
                }
                case 6: {
                    toolRemoteCmdStatus = this.getNSInstanceNames(toolRemoteCmdDescriptor);
                    break;
                }
                case 7: {
                    toolRemoteCmdStatus = this.getAdminRoleNames(toolRemoteCmdDescriptor);
                    break;
                }
                case 8: {
                    toolRemoteCmdStatus = this.getSSLAliasNames(toolRemoteCmdDescriptor);
                    break;
                }
                case 9: {
                    toolRemoteCmdStatus = this.validateProperties(toolRemoteCmdDescriptor);
                    break;
                }
                case 10: {
                    toolRemoteCmdStatus = this.loadGuiSchema(toolRemoteCmdDescriptor);
                    break;
                }
                case 11: {
                    toolRemoteCmdStatus = this.fetchPropertyManagerRemoteRef(toolRemoteCmdDescriptor);
                    break;
                }
                case 12: {
                    toolRemoteCmdStatus = this.getGUISchemaPropFilename(toolRemoteCmdDescriptor);
                    break;
                }
                case 14: {
                    toolRemoteCmdStatus = this.validateOneProperty(toolRemoteCmdDescriptor);
                    break;
                }
                case 15: {
                    toolRemoteCmdStatus = this.getNSInstReferences(toolRemoteCmdDescriptor);
                    break;
                }
                default: {
                    toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
                    toolRemoteCmdStatus.setErrorStatus(0);
                    break;
                }
            }
        }
        catch (Exception ex) {
            toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
            toolRemoteCmdStatus.setErrorStatus(-1);
        }
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus getSvcConfig(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        try {
            toolRemoteCmdStatus.setGetSvcCfgStatus(this.m_pmpObject.getPropertiesStream(propSpec));
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381971L, new Object[] { propSpec });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus saveSvcConfig(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        String s = toolRemoteCmdDescriptor.getPropSpec();
        try {
            final char[] fetchSaveSvcCfgArg = toolRemoteCmdDescriptor.fetchSaveSvcCfgArg();
            if (s == null) {
                s = this.m_groupPath;
            }
            toolRemoteCmdStatus.setSuccessOrFailureStatus(this.m_pmpObject.saveSvcConfig(fetchSaveSvcCfgArg, s));
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381972L, new Object[] { ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus validateProperties(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        try {
            UBToolsMsg.logMsg("AbstractGuiToolRemObj.validateProperties() is called");
            final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
            final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
            UBToolsMsg.logMsg("svcGrpPath = " + propSpec);
            try {
                final PropertiesSaveDescriptor fetchValidateSaveCfgArg = toolRemoteCmdDescriptor.fetchValidateSaveCfgArg();
                if (propSpec == null) {
                    final String groupPath = this.m_groupPath;
                }
                final CfgValidateErrs validateProperties = this.m_pmpObject.validateProperties(fetchValidateSaveCfgArg);
                if (validateProperties != null) {
                    toolRemoteCmdStatus.setValidateSaveCfgStatus(validateProperties);
                }
                else {
                    toolRemoteCmdStatus.setSuccessOrFailureStatus(false);
                }
                return toolRemoteCmdStatus;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                final String msg = UBToolsMsg.getMsg(7094295313015381972L, new Object[] { ex.getMessage() });
                UBToolsMsg.logException(msg);
                toolRemoteCmdStatus.setDetailErrMsg(msg);
                return toolRemoteCmdStatus;
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
            return null;
        }
    }
    
    public ToolRemoteCmdStatus getPreferenceProperties(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        try {
            toolRemoteCmdStatus.setGetPrefPropStatus(this.m_pmpObject.getPreferenceStream());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381971L, new Object[] { "Preference" });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus handlePreferencePropertiesChanged(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        try {
            this.m_pmpObject.refreshPreferences();
            SvcControlCmd.setPreferences(this.m_pmpObject.getPreferences());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            toolRemoteCmdStatus.setErrorStatus(-1);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getAdminRoleNames(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final Vector adminRoleNames = this.getAdminRoleNames();
        final String[] anArray = new String[adminRoleNames.size()];
        adminRoleNames.copyInto(anArray);
        toolRemoteCmdStatus.setSuccessStatus();
        if (adminRoleNames != null) {
            toolRemoteCmdStatus.addResultSet((Object)anArray);
        }
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus getSSLAliasNames(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String[] sslAliasNames = this.getSSLAliasNames();
        toolRemoteCmdStatus.setSuccessStatus();
        if (sslAliasNames != null) {
            toolRemoteCmdStatus.addResultSet((Object)sslAliasNames);
        }
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus getNSInstanceNames(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        this.m_nsChildrenNames = this.getNameServerNames();
        final String[] anArray = new String[this.m_nsChildrenNames.size()];
        this.m_nsChildrenNames.copyInto(anArray);
        toolRemoteCmdStatus.setSuccessStatus();
        if (this.m_nsChildrenNames != null) {
            toolRemoteCmdStatus.addResultSet((Object)anArray);
        }
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus loadGuiSchema(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        ToolRemoteCmdStatus toolRemoteCmdStatus = null;
        try {
            toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
            toolRemoteCmdStatus.setLoadGUISchemaStatus((Object)null);
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String s = "GUI Schema loading problem...";
            UBToolsMsg.logException(s + ex.toString());
            toolRemoteCmdStatus.setDetailErrMsg(s);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus fetchPropertyManagerRemoteRef(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        ToolRemoteCmdStatus toolRemoteCmdStatus = null;
        try {
            toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
            toolRemoteCmdStatus.setFetchRPMStatus(this.getRPM());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String s = "Failed to fetch Property Manager Remote reference";
            UBToolsMsg.logException(s + ex.toString());
            toolRemoteCmdStatus.setDetailErrMsg(s);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getGUISchemaPropFilename(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        ToolRemoteCmdStatus toolRemoteCmdStatus = null;
        try {
            toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
            toolRemoteCmdStatus.setGetGUISchemaPropFnStatus(this.m_pmpObject.getSchemaPropFilename());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String s = "Failed to get schema filename...";
            UBToolsMsg.logException(s + ex.toString());
            toolRemoteCmdStatus.setDetailErrMsg(s);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus replaceProperties(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        try {
            toolRemoteCmdStatus.setSuccessOrFailureStatus(this.m_pmpObject.updatePropertyCollection(propSpec, toolRemoteCmdDescriptor.fetchReplacePropertiesArg()));
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            toolRemoteCmdStatus.setErrorStatus(-1);
            return toolRemoteCmdStatus;
        }
    }
    
    public Vector getAdminRoleNames() {
        String[] adminRoles = null;
        final Vector<String> vector = new Vector<String>();
        try {
            adminRoles = this.m_pmpObject.getAdminRoles();
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381973L, new Object[] { ex.getMessage() }));
        }
        if (adminRoles != null) {
            for (int length = adminRoles.length, i = 0; i < length; ++i) {
                vector.addElement(adminRoles[i]);
            }
        }
        return vector;
    }
    
    public boolean accept(final File file, final String s) {
        return s.endsWith(".pem");
    }
    
    public String[] getSSLAliasNames() {
        String[] list = null;
        try {
            final File file = new File(this.m_pmpObject.getExpandedPropertyValue("keyStorePath", this.getPropGroupPath()));
            if (file.isDirectory()) {
                list = file.list(this);
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381973L, new Object[] { ex.getMessage() }));
        }
        return list;
    }
    
    public Vector getNameServerNames() {
        String[] nameServerInstances = null;
        final Vector<String> vector = new Vector<String>();
        try {
            nameServerInstances = this.m_pmpObject.getNameServerInstances();
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381973L, new Object[] { ex.getMessage() }));
        }
        if (nameServerInstances != null) {
            for (int length = nameServerInstances.length, i = 0; i < length; ++i) {
                vector.addElement(nameServerInstances[i]);
            }
        }
        return vector;
    }
    
    public ToolRemoteCmdStatus validateOneProperty(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        toolRemoteCmdDescriptor.getPropSpec();
        try {
            toolRemoteCmdStatus.setValidateValidOnePropStatus(this.m_pmpObject.validateOneProperty(toolRemoteCmdDescriptor.getPropSpec(), toolRemoteCmdDescriptor.fetchValOnePropNameArg(), toolRemoteCmdDescriptor.fetchValOnePropValArgStr()));
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            toolRemoteCmdStatus.setErrorStatus(-1);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getNSInstReferences(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        try {
            toolRemoteCmdDescriptor.getPropSpec();
            toolRemoteCmdStatus.setNSInstRefStatus(this.m_pmpObject.getNSInstRefCnt(toolRemoteCmdDescriptor.fetchNSInstRefArg()));
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            toolRemoteCmdStatus.setErrorStatus(-1);
            return toolRemoteCmdStatus;
        }
    }
    
    public void refreshProperties() {
        this.m_refreshPropertiesASAP = true;
    }
    
    public void handlePMUContextReset(final boolean serviceContainerClient) {
        this.m_serviceContainerClient = serviceContainerClient;
        if (this.m_serviceContainerClient) {
            try {
                this.handleRefreshProperties();
            }
            catch (Exception ex) {
                UBToolsMsg.logMsg("Exception in handlePMUContextReset(): cannot reset properties");
            }
        }
    }
    
    public synchronized void refreshPropertiesIfChanged() {
    }
    
    public void shutdown() {
        this.m_controlCmdCache.clear();
        this.m_childrenRemoteObjects = null;
        this.m_childrenNames = null;
        this.m_newChildrenNames = null;
    }
    
    public String getOsName() {
        return System.getProperty("os.name");
    }
    
    public int findSvcInChildrenNameVect(final String s) {
        final int size = this.m_childrenNames.size();
        int n = 1;
        int index = 0;
        while (n != 0 && index < size) {
            if (s.equals(this.m_childrenNames.elementAt(index))) {
                n = 0;
            }
            else {
                ++index;
            }
        }
        if (n != 0) {
            return -1;
        }
        return index;
    }
    
    public Object removeControlCmd(final String s) {
        return this.m_controlCmdCache.remove(this.m_pmpObject.getSvcName(s));
    }
    
    public ToolRemoteCmdStatus handleAddNew(final String s, final String s2, final int n) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(n);
        if (!this.m_pmpObject.uniqueSvcName(s2)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        if (!this.m_pmpObject.handleAddNew(s, s2)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        toolRemoteCmdStatus.setSuccessStatus();
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus handleWsaAddNew(final String s, final String s2, final String s3, final String s4, final int n) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(n);
        if (!this.m_pmpObject.uniqueSvcName(s2)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        if (!this.m_pmpObject.handleWsaAddNew(s, s2, s3, s4)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        toolRemoteCmdStatus.setSuccessStatus();
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus handleNSAddNew(final String s, final String s2, final String s3, final int n) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(n);
        if (!this.m_pmpObject.uniqueSvcName(s2)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        if (!this.m_pmpObject.handleNSAddNew(s, s2, s3)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        toolRemoteCmdStatus.setSuccessStatus();
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus handleAiaAddNew(final String s, final String s2, final int n) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(n);
        if (!this.m_pmpObject.uniqueSvcName(s2)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        if (!this.m_pmpObject.handleAiaAddNew(s, s2)) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        toolRemoteCmdStatus.setSuccessStatus();
        return toolRemoteCmdStatus;
    }
    
    public IChimeraHierarchy getToolInstRemObj(final String s) {
        final int svcInChildrenNameVect = this.findSvcInChildrenNameVect(s);
        if (svcInChildrenNameVect >= 0) {
            return (IChimeraHierarchy)this.m_childrenRemoteObjects.elementAt(svcInChildrenNameVect);
        }
        return null;
    }
    
    public void delToolInstRemObj(final String s) {
        final int svcInChildrenNameVect = this.findSvcInChildrenNameVect(s);
        if (svcInChildrenNameVect >= 0) {
            this.m_childrenNames.removeElementAt(svcInChildrenNameVect);
            this.m_childrenRemoteObjects.removeElementAt(svcInChildrenNameVect);
        }
    }
    
    public ToolRemoteCmdStatus checkServerState(final int n) {
        ToolRemoteCmdStatus toolRemoteCmdStatus = null;
        if (!this.m_serviceContainerClient) {
            toolRemoteCmdStatus = new ToolRemoteCmdStatus(n);
            toolRemoteCmdStatus.setErrorStatus(-1);
        }
        return toolRemoteCmdStatus;
    }
    
    public abstract ToolRemoteCmdStatus startSvcProcess(final ToolRemoteCmdDescriptor p0);
    
    public abstract ToolRemoteCmdStatus stopSvcProcess(final ToolRemoteCmdDescriptor p0);
    
    public abstract ToolRemoteCmdStatus pingService(final ToolRemoteCmdDescriptor p0);
    
    public abstract ToolRemoteCmdStatus addNewSvcGuiTool(final ToolRemoteCmdDescriptor p0);
    
    public abstract ToolRemoteCmdStatus delSvcGuiTool(final ToolRemoteCmdDescriptor p0);
    
    public abstract Object lookupControlCmd(final String p0);
    
    public abstract void handleRefreshProperties();
    
    public abstract void updateSvcControlObjState(final int p0, final String p1, final String p2);
    
    private EventListener createStartSvcEvtListener(final AbstractGuiPluginRemObj abstractGuiPluginRemObj) {
        return new StartSvcEvtListener(abstractGuiPluginRemObj);
    }
    
    private EventListener createStopSvcEvtListener(final AbstractGuiPluginRemObj abstractGuiPluginRemObj) {
        return new StopSvcEvtListener(abstractGuiPluginRemObj);
    }
}

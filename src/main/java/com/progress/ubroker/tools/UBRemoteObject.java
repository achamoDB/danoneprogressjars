// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.events.EAbnormalShutdownServerEvent;
import com.progress.ubroker.management.events.IOpenEdgeManagementEvent;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.EventListener;
import com.progress.ubroker.management.events.ETrimAgentsEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.management.events.EAddAgentsEvent;
import com.progress.ubroker.management.events.COpenEdgeManagementContent;
import java.util.Vector;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.common.util.acctAuthenticate;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import com.progress.common.networkevents.IEventListener;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import com.progress.chimera.adminserver.IAdministrationServer;
import java.rmi.server.RemoteStub;
import com.progress.ubroker.util.IToolCmdConst;

public class UBRemoteObject extends AbstractGuiPluginRemObj implements IYodaRMI, IYodaSharedResources, IBTMsgCodes, IToolCmdConst
{
    static final String UB_REMOTE_OBJ_NAME = "Unified Broker TreeNode Remote Object";
    UBGuiPlugin m_myParentPlugin;
    RemoteStub m_stubObject;
    boolean reqUserName;
    
    public UBRemoteObject(final UBGuiPlugin myParentPlugin, final String s, final PropMgrPlugin propMgrPlugin, final IAdministrationServer administrationServer) throws RemoteException {
        super(myParentPlugin, s, propMgrPlugin);
        this.m_myParentPlugin = null;
        this.m_stubObject = null;
        this.reqUserName = false;
        this.m_myParentPlugin = myParentPlugin;
        if (administrationServer != null) {
            this.reqUserName = administrationServer.isReqUserName();
        }
        try {
            this.m_stubObject = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381976L, new Object[] { "Unified Broker TreeNode Remote Object", ex.getMessage() }));
        }
        if (super.m_personality.equalsIgnoreCase(UBGuiPlugin.APPSVR_ID)) {
            this.getEventBroker().expressInterest(EAbnormalShutdownServerEvent.class, new UBManagementEventsListener(), this.getEventStream(s));
        }
    }
    
    public Enumeration getChildren() throws RemoteException {
        super.getSvcChildren();
        if (super.m_configChanged && super.m_newChildrenNames != null) {
            if (super.m_configChanged) {
                super.m_nsChildrenNames = this.getNameServerNames();
            }
            if (super.m_childrenNames.size() == 0) {
                super.m_numChildren = super.m_newChildrenNames.length;
                this.makeAllToolRemoteObjects();
            }
            else {
                this.handleConfigChanges();
            }
            super.m_configChanged = false;
        }
        if (super.m_childrenRemoteObjects.size() > 0) {
            return new SerializableEnumeration(super.m_childrenRemoteObjects);
        }
        return null;
    }
    
    public String getMMCClientClass() throws RemoteException {
        return "com.progress.vj.ubroker.UBMMCClient";
    }
    
    public String getApplicationName() throws RemoteException {
        return "Unified Broker TreeNode Remote Object";
    }
    
    public String getPropGroupPath() throws RemoteException {
        return super.getPropGroupPath();
    }
    
    public String getSvcName() throws RemoteException {
        return super.getSvcName();
    }
    
    public String getAdminSrvrURL() throws RemoteException {
        return super.getAdminSrvrURL();
    }
    
    public String getAdminSrvrOSName() throws RemoteException {
        return super.getAdminSrvrOSName();
    }
    
    public String getAdminSrvrHost() throws RemoteException {
        return super.getAdminSrvrHost();
    }
    
    public String getAdminSrvrPort() throws RemoteException {
        return super.getAdminSrvrPort();
    }
    
    public IPropertyManagerRemote getRPM() throws RemoteException {
        return super.getRPM();
    }
    
    public String getPropertyFilename() throws RemoteException {
        return super.getPropertyFilename();
    }
    
    public String getSchemaFilename() throws RemoteException {
        return super.getSchemaFilename();
    }
    
    public String[] getSchemaPropFnList() throws RemoteException {
        return super.getSchemaPropFnList();
    }
    
    public IEventBroker getEventBroker() throws RemoteException {
        return super.getEventBroker();
    }
    
    public IYodaRMI getRemoteManageObject() throws RemoteException {
        return super.getRemoteManageObject();
    }
    
    public RemoteStub getRemStub() throws RemoteException {
        return this.m_stubObject;
    }
    
    public Hashtable getLogFiles(final String s) throws RemoteException {
        return super.getLogFiles(s);
    }
    
    public ToolRemoteCmdStatus doRemoteToolCmd(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) throws RemoteException {
        final int command = toolRemoteCmdDescriptor.getCommand();
        final ToolRemoteCmdStatus checkServerState = this.checkServerState(command);
        if (checkServerState != null) {
            return checkServerState;
        }
        ToolRemoteCmdStatus toolRemoteCmdStatus2 = null;
        try {
            if (command >= 1 && command < 50) {
                return super.doRemoteToolCmd(toolRemoteCmdDescriptor);
            }
            if (this.reqUserName && (command == 100 || command == 101)) {
                final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
                final SvcStartArgsPkt svcStartArgsPkt = new SvcStartArgsPkt(this.m_myParentPlugin.getPolicy(super.m_pmpObject.getPersonStrFromPropPath(propSpec)), propSpec, super.m_pmpObject.getSvcName(propSpec), super.m_pmpObject);
                if (!new acctAuthenticate().verifyUserJNI(svcStartArgsPkt.m_username, svcStartArgsPkt.m_password)) {
                    UBToolsMsg.logException("Failed to verify user " + svcStartArgsPkt.m_username + "'s password.");
                    final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
                    toolRemoteCmdStatus.setErrorStatus(-1);
                    return toolRemoteCmdStatus;
                }
                UBToolsMsg.logException("Password of user " + svcStartArgsPkt.m_username + " was successfully verified.");
            }
            switch (command) {
                case 100: {
                    toolRemoteCmdStatus2 = this.startSvcProcess(toolRemoteCmdDescriptor);
                    break;
                }
                case 101: {
                    toolRemoteCmdStatus2 = this.stopSvcProcess(toolRemoteCmdDescriptor);
                    break;
                }
                case 102: {
                    toolRemoteCmdStatus2 = this.pingService(toolRemoteCmdDescriptor);
                    break;
                }
                case 103: {
                    toolRemoteCmdStatus2 = this.addNewSvcGuiTool(toolRemoteCmdDescriptor);
                    break;
                }
                case 104: {
                    toolRemoteCmdStatus2 = this.delSvcGuiTool(toolRemoteCmdDescriptor);
                    break;
                }
                case 105: {
                    toolRemoteCmdStatus2 = this.getSvcSummaryStatusRSLabels(toolRemoteCmdDescriptor);
                    break;
                }
                case 106: {
                    toolRemoteCmdStatus2 = this.getSvcSummaryStatusRS(toolRemoteCmdDescriptor);
                    break;
                }
                case 107: {
                    toolRemoteCmdStatus2 = this.getSvcDetailStatusRSLabels(toolRemoteCmdDescriptor);
                    break;
                }
                case 108: {
                    toolRemoteCmdStatus2 = this.getSvcDetailStatusRS(toolRemoteCmdDescriptor);
                    break;
                }
                case 109: {
                    toolRemoteCmdStatus2 = this.trimSrvrBy(toolRemoteCmdDescriptor);
                    break;
                }
                case 110: {
                    toolRemoteCmdStatus2 = this.addNewSrvrs(toolRemoteCmdDescriptor);
                    break;
                }
                default: {
                    toolRemoteCmdStatus2 = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
                    toolRemoteCmdStatus2.setErrorStatus(-2);
                    break;
                }
            }
        }
        catch (Exception ex) {
            toolRemoteCmdStatus2 = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
            toolRemoteCmdStatus2.setErrorStatus(-1);
        }
        return toolRemoteCmdStatus2;
    }
    
    public ToolRemoteCmdStatus startSvcProcess(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        try {
            toolRemoteCmdStatus.setSuccessOrFailureStatus(((UBControlCmd)this.lookupControlCmd(propSpec)).startService());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381986L, new Object[] { "UBRemoteObject", super.m_personality, propSpec, ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public Hashtable getData(final String str, final String[] array) {
        Hashtable data = null;
        try {
            data = ((UBControlCmd)this.lookupControlCmd(str)).getData(array);
            return data;
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Broker not started: " + str + ". Cannot get stats data.");
            return data;
        }
    }
    
    public int getBrokerCollectDataState(final String str) {
        int brokerCollectState = -1;
        try {
            brokerCollectState = ((UBControlCmd)this.lookupControlCmd(str)).getBrokerCollectState();
            return brokerCollectState;
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Broker not started: " + str + ". Cannot retrieve broker's state of collStatsData.");
            return brokerCollectState;
        }
    }
    
    public ToolRemoteCmdStatus stopSvcProcess(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmd(propSpec);
        try {
            if (ubControlCmd.stopService(propSpec)) {
                if (this.removeControlCmd(propSpec) == null) {
                    UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381987L, new Object[] { "UBRemoteObject", "UBControlCmd", propSpec }));
                }
                toolRemoteCmdStatus.setSuccessStatus();
            }
            else {
                toolRemoteCmdStatus.setErrorStatus(-1);
            }
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381988L, new Object[] { "UBRemoteObject", propSpec, ex.toString() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus pingService(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        final int fetchPingSvcArg = toolRemoteCmdDescriptor.fetchPingSvcArg();
        try {
            long pingSvcStatus;
            if (fetchPingSvcArg == 0) {
                pingSvcStatus = ubControlCmd.pingService();
            }
            else {
                pingSvcStatus = ubControlCmd.pingService(fetchPingSvcArg);
            }
            toolRemoteCmdStatus.setPingSvcStatus(pingSvcStatus);
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381989L, new Object[] { "UBRemoteObject", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus addNewSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        String msg = null;
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        if (fetchAddNewGuiToolArg == null) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        final ToolRemoteCmdStatus handleAddNew = super.handleAddNew(super.m_groupPath, fetchAddNewGuiToolArg, toolRemoteCmdDescriptor.getCommand());
        if (!handleAddNew.getSuccessOrFailure()) {
            return handleAddNew;
        }
        try {
            super.m_pmpObject.updateSvcNameCache(this.getPropGroupPath());
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381990L, new Object[] { "UBRemoteObject", fetchAddNewGuiToolArg, ex.getMessage() }));
        }
        if (this.findSvcInChildrenNameVect(fetchAddNewGuiToolArg) < 0) {
            try {
                final UBTRemoteObject obj = new UBTRemoteObject(this, fetchAddNewGuiToolArg, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
                this.addTreeNodeRemObjRef(obj, fetchAddNewGuiToolArg, this.getPropGroupPath());
                super.m_childrenRemoteObjects.addElement(obj);
                super.m_childrenNames.addElement(fetchAddNewGuiToolArg);
                super.m_numChildren = super.m_childrenNames.size();
                handleAddNew.setSuccessStatus();
                return handleAddNew;
            }
            catch (Exception ex2) {
                msg = UBToolsMsg.getMsg(7094295313015381991L, new Object[] { "UBRemoteObject", fetchAddNewGuiToolArg, super.m_personality });
                UBToolsMsg.logException(msg);
            }
        }
        handleAddNew.setDetailErrMsg(msg);
        return handleAddNew;
    }
    
    public synchronized ToolRemoteCmdStatus delSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        if (fetchAddNewGuiToolArg == null | fetchAddNewGuiToolArg.length() == 0) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        final IChimeraHierarchy toolInstRemObj = this.getToolInstRemObj(fetchAddNewGuiToolArg);
        if (toolInstRemObj != null && this.deleteTreeNodeRemObjRef(toolInstRemObj, fetchAddNewGuiToolArg, super.m_groupPath)) {
            this.delToolInstRemObj(fetchAddNewGuiToolArg);
            super.m_controlCmdCache.remove(fetchAddNewGuiToolArg);
            super.m_numChildren = super.m_childrenNames.size();
            if (super.m_pmpObject.delSvcConfig(fetchAddNewGuiToolArg, super.m_groupPath)) {
                toolRemoteCmdStatus.setSuccessStatus();
                return toolRemoteCmdStatus;
            }
        }
        toolRemoteCmdStatus.setErrorStatus(-1);
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus getSvcSummaryStatusRSLabels(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        try {
            toolRemoteCmdStatus.setGetSummaryStatuslblStatus(ubControlCmd.getSummaryStatusRSFieldLabels());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "UBRemoteObject", "getSvcSummaryStatusRSLabels", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getSvcSummaryStatusRS(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        try {
            toolRemoteCmdStatus.setGetSummaryStatusDataStatus(ubControlCmd.getSummaryStatusRS());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "UBRemoteObject", "getSvcSummaryStatusRS", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getSvcDetailStatusRSLabels(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        try {
            toolRemoteCmdStatus.setGetDetailStatusLblStatus(ubControlCmd.getDetailStatusRSColumnCaptions());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "UBRemoteObject", "getSvcDetailStatusRSLabels", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getSvcDetailStatusRS(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        try {
            toolRemoteCmdStatus.setGetDetailStatusDataStatus(ubControlCmd.getDetailStatusRS());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "UBRemoteObject", "getSvcDetailStatusRS", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus trimSrvrBy(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final int fetchAddNewSrvrsArg = toolRemoteCmdDescriptor.fetchAddNewSrvrsArg();
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmd(propSpec);
        try {
            final int trimSrvrByRetNum = ubControlCmd.trimSrvrByRetNum(fetchAddNewSrvrsArg);
            if (trimSrvrByRetNum > 0) {
                toolRemoteCmdStatus.setTrimSrvrByStatus(0);
                this.postTrimSrvrEvent(propSpec);
                this.postTrimSrvrMgmtEvent(propSpec, trimSrvrByRetNum);
            }
            else {
                toolRemoteCmdStatus.setTrimSrvrByStatus(trimSrvrByRetNum);
            }
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "UBRemoteObject", "trimSrvrBy", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public void trimSrvrByRetNum(final String s, final int n) {
        final int trimSrvrByRetNum = ((UBControlCmd)this.lookupControlCmd(s)).trimSrvrByRetNum(n);
        if (trimSrvrByRetNum > 0) {
            this.postTrimSrvrEvent(s);
            this.postTrimSrvrMgmtEvent(s, trimSrvrByRetNum);
        }
    }
    
    public ToolRemoteCmdStatus addNewSrvrs(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        try {
            final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
            final int addNewSrvrsRetNum = ((UBControlCmd)this.lookupControlCmd(propSpec)).addNewSrvrsRetNum(toolRemoteCmdDescriptor.fetchAddNewSrvrsArg());
            if (addNewSrvrsRetNum > 0) {
                toolRemoteCmdStatus.setAddNewSrvrsStatus(0);
                this.postAddSrvrEvent(propSpec);
                this.postAddSrvrMgmtEvent(propSpec, addNewSrvrsRetNum);
            }
            else {
                toolRemoteCmdStatus.setAddNewSrvrsStatus(addNewSrvrsRetNum);
            }
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "UBRemoteObject", "addNewSrvrs", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public void addNewSrvrsRetNum(final String s, final int n) {
        final int addNewSrvrsRetNum = ((UBControlCmd)this.lookupControlCmd(s)).addNewSrvrsRetNum(n);
        if (addNewSrvrsRetNum > 0) {
            this.postAddSrvrEvent(s);
            this.postAddSrvrMgmtEvent(s, addNewSrvrsRetNum);
        }
    }
    
    public void shutdown() {
        super.shutdown();
    }
    
    public Object lookupControlCmd(final String s) {
        return this.lookupControlCmdBySvcName(s, super.m_pmpObject.getSvcName(s));
    }
    
    private Object lookupControlCmdBySvcName(final String s, final String key) {
        if (!super.m_controlCmdCache.containsKey(key)) {
            final UBControlCmd value = new UBControlCmd(new SvcStartArgsPkt(this.m_myParentPlugin.getPolicy(super.m_pmpObject.getPersonStrFromPropPath(s)), s, key, super.m_pmpObject), key, super.m_rmiHost, super.m_rmiPort);
            super.m_controlCmdCache.put(key, value);
            return value;
        }
        return super.m_controlCmdCache.get(key);
    }
    
    public void updateSvcControlObjState(final int n, final String s, final String s2) {
        try {
            final UBControlCmd ubControlCmd = (UBControlCmd)this.lookupControlCmdBySvcName(s, s2);
            if (ubControlCmd != null) {
                if (n == 1) {
                    ubControlCmd.setStateStart();
                }
                else if (n == 2) {
                    ubControlCmd.setStateStop();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public Enumeration getAppsSvcNamesFromNS() throws RemoteException {
        return null;
    }
    
    public void updateControlCmd(final String s) {
        if (super.m_controlCmdCache.containsKey(s)) {
            super.m_controlCmdCache.get(s).updateConfig();
        }
    }
    
    public synchronized void handleRefreshProperties() {
        try {
            super.m_newChildrenNames = super.m_pmpObject.getSvcNameForParentGrp(this.getPropGroupPath());
            this.handleConfigChanges();
        }
        catch (Exception ex) {}
    }
    
    private synchronized void handleConfigChanges() {
        final Vector childrenRemoteObjects = new Vector<UBTRemoteObject>();
        final Vector childrenNames = new Vector<String>();
        try {
            if (super.m_newChildrenNames == null) {
                return;
            }
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                final int svcInChildrenNameVect = this.findSvcInChildrenNameVect(obj);
                if (svcInChildrenNameVect < 0) {
                    childrenRemoteObjects.addElement(new UBTRemoteObject(this, obj, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                }
                else {
                    childrenRemoteObjects.addElement((UBTRemoteObject)super.m_childrenRemoteObjects.elementAt(svcInChildrenNameVect));
                    super.m_childrenNames.removeElementAt(svcInChildrenNameVect);
                    super.m_childrenRemoteObjects.removeElementAt(svcInChildrenNameVect);
                    this.updateControlCmd(obj);
                }
                childrenNames.addElement(obj);
            }
            if (childrenRemoteObjects.size() > 0) {
                super.m_childrenRemoteObjects = childrenRemoteObjects;
            }
            if (childrenNames.size() > 0) {
                super.m_childrenNames = childrenNames;
                super.m_numChildren = super.m_childrenNames.size();
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "UBRemoteObject", "handleConfigChanges", ex.toString() }));
        }
    }
    
    private void makeAllToolRemoteObjects() {
        try {
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                super.m_childrenRemoteObjects.addElement(new UBTRemoteObject(this, obj, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                super.m_childrenNames.addElement(obj);
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "UBRemoteObject", "makeAllToolRemoteObjects", ex.toString() }));
        }
    }
    
    private boolean addTreeNodeRemObjRef(final UBTRemoteObject ubtRemoteObject, final String str, final String str2) {
        try {
            return this.m_myParentPlugin.addChildNode(this.m_stubObject, this, ubtRemoteObject, super.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private boolean deleteTreeNodeRemObjRef(final IChimeraHierarchy chimeraHierarchy, final String str, final String str2) {
        final UBTRemoteObject ubtRemoteObject = (UBTRemoteObject)chimeraHierarchy;
        try {
            return this.m_myParentPlugin.delChildNode(ubtRemoteObject.getRemStub(), chimeraHierarchy, str, super.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private void postAddSrvrEvent(final String s) {
        final String svcName = super.m_pmpObject.getSvcName(s);
        final IChimeraHierarchy toolInstRemObj = this.getToolInstRemObj(svcName);
        final UBTRemoteObject ubtRemoteObject = (UBTRemoteObject)toolInstRemObj;
        try {
            this.m_myParentPlugin.postAddSrvrEvent(ubtRemoteObject.getRemStub(), toolInstRemObj, s, svcName, super.getEventStream(s));
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException("problem posting add server event");
        }
    }
    
    private void postAddSrvrMgmtEvent(final String s, final int value) {
        final COpenEdgeManagementContent cOpenEdgeManagementContent = new COpenEdgeManagementContent(super.m_pmpObject.getSvcName(s), new Object[] { new Integer(value) });
        try {
            final EAddAgentsEvent eAddAgentsEvent = new EAddAgentsEvent(this, cOpenEdgeManagementContent);
            eAddAgentsEvent.setSource(super.m_pmpObject.getCanonicalName(super.m_pmpObject.getParentGroupValue(super.m_personality)));
            AbstractGuiPlugin.getEventBroker().postEvent(eAddAgentsEvent);
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException("problem posting add server management event");
        }
    }
    
    private void postTrimSrvrEvent(final String s) {
        final String svcName = super.m_pmpObject.getSvcName(s);
        final IChimeraHierarchy toolInstRemObj = this.getToolInstRemObj(svcName);
        final UBTRemoteObject ubtRemoteObject = (UBTRemoteObject)toolInstRemObj;
        try {
            this.m_myParentPlugin.postTrimSrvrEvent(ubtRemoteObject.getRemStub(), toolInstRemObj, s, svcName, super.getEventStream(s));
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException("problem posting trim server event");
        }
    }
    
    private void postTrimSrvrMgmtEvent(final String s, final int value) {
        final COpenEdgeManagementContent cOpenEdgeManagementContent = new COpenEdgeManagementContent(super.m_pmpObject.getSvcName(s), new Object[] { new Integer(value) });
        try {
            final ETrimAgentsEvent eTrimAgentsEvent = new ETrimAgentsEvent(this, cOpenEdgeManagementContent);
            eTrimAgentsEvent.setSource(super.m_pmpObject.getCanonicalName(super.m_pmpObject.getParentGroupValue(super.m_personality)));
            AbstractGuiPlugin.getEventBroker().postEvent(eTrimAgentsEvent);
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException("problem posting trim server management event");
        }
    }
    
    class UBManagementEventsListener extends EventListener
    {
        public synchronized void processEvent(final IEventObject eventObject) {
            try {
                if (!((INotificationEvent)eventObject).getNotificationName().equalsIgnoreCase("EAbnormalShutdownServerEvent")) {
                    return;
                }
                final COpenEdgeManagementContent content = ((IOpenEdgeManagementEvent)eventObject).getContent();
                System.out.println("$$$ Received " + eventObject.eventTypeString() + " for " + content.getIssuerName() + " : server " + ((Integer)content.getContent()[0]).toString() + " has been abnormally shutdown");
            }
            catch (Exception ex) {
                UBToolsMsg.logException("$$$$ Exception during processEvent(): " + ex.getMessage());
            }
        }
    }
}

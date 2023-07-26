// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.aia;

import java.util.Vector;
import com.progress.ubroker.tools.SvcStartArgsPkt;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.ubroker.tools.UBToolsMsg;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.ubroker.tools.PropMgrPlugin;
import java.rmi.server.RemoteStub;
import com.progress.ubroker.util.IToolCmdConst;
import com.progress.ubroker.tools.IBTMsgCodes;
import com.progress.ubroker.tools.IYodaSharedResources;
import com.progress.ubroker.tools.IYodaRMI;
import com.progress.ubroker.tools.AbstractGuiPluginRemObj;

public class AiaRemoteObject extends AbstractGuiPluginRemObj implements IYodaRMI, IYodaSharedResources, IBTMsgCodes, IToolCmdConst
{
    static final String AIA_REMOTE_OBJ_NAME = "AppServer Internet Adapter (AIA) remote object";
    AiaGuiPlugin m_myParentPlugin;
    RemoteStub m_stubObject;
    static boolean DEBUG_TRACE;
    
    public AiaRemoteObject(final AiaGuiPlugin myParentPlugin, final String str, final PropMgrPlugin propMgrPlugin) throws RemoteException {
        super(myParentPlugin, str, propMgrPlugin);
        this.m_myParentPlugin = null;
        this.m_stubObject = null;
        this.m_myParentPlugin = myParentPlugin;
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("in AiaRemoteObject constructor " + str);
        }
        try {
            this.m_stubObject = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381976L, new Object[] { "AiaRemoteObject", "AppServer Internet Adapter (AIA) remote object", ex.getMessage() }));
        }
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("about to exit AiaRemoteObject constructor");
        }
    }
    
    public Enumeration getChildren() throws RemoteException {
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("in AiaRemoteObj.getChildren()");
        }
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
        return "com.progress.vj.ubroker.aia.AiaMMCClient";
    }
    
    public String getApplicationName() throws RemoteException {
        return "AppServer Internet Adapter (AIA) remote object";
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
        ToolRemoteCmdStatus toolRemoteCmdStatus = null;
        try {
            if (command >= 1 && command < 50) {
                return super.doRemoteToolCmd(toolRemoteCmdDescriptor);
            }
            switch (command) {
                case 103: {
                    toolRemoteCmdStatus = this.addNewSvcGuiTool(toolRemoteCmdDescriptor);
                    break;
                }
                case 104: {
                    toolRemoteCmdStatus = this.delSvcGuiTool(toolRemoteCmdDescriptor);
                    break;
                }
                default: {
                    toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
                    toolRemoteCmdStatus.setErrorStatus(-2);
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
    
    public ToolRemoteCmdStatus startSvcProcess(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("AiaRemoteObject.startSvcProcess(" + propSpec + ") is called");
        }
        try {
            toolRemoteCmdStatus.setSuccessOrFailureStatus(((AiaControlCmd)this.lookupControlCmd(propSpec)).startService());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381986L, new Object[] { "AiaRemoteObject", super.m_personality, propSpec, ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus stopSvcProcess(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final AiaControlCmd aiaControlCmd = (AiaControlCmd)this.lookupControlCmd(propSpec);
        try {
            if (aiaControlCmd.stopService(propSpec)) {
                if (this.removeControlCmd(propSpec) == null) {
                    UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381987L, new Object[] { "AiaRemoteObject", "AiaControlCmd", propSpec }));
                }
                toolRemoteCmdStatus.setSuccessStatus();
            }
            else {
                toolRemoteCmdStatus.setErrorStatus(-1);
            }
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381988L, new Object[] { "AiaRemoteObject", propSpec, ex.toString() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus pingService(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("AiaRemoteObject.pingService(" + propSpec + ") is called");
        }
        final AiaControlCmd aiaControlCmd = (AiaControlCmd)this.lookupControlCmd(propSpec);
        final int fetchPingSvcArg = toolRemoteCmdDescriptor.fetchPingSvcArg();
        try {
            long n;
            if (fetchPingSvcArg == 0) {
                n = aiaControlCmd.pingService();
            }
            else {
                n = aiaControlCmd.pingService(fetchPingSvcArg);
            }
            if (AiaRemoteObject.DEBUG_TRACE) {
                System.out.println("  active for " + n + " seconds.");
            }
            toolRemoteCmdStatus.setPingSvcStatus(n);
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381989L, new Object[] { "AiaRemoteObject", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus addNewSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("AiaRemoteObject.addNewSvcGuiTool():this.hashCode() = " + this.hashCode());
        }
        String msg = null;
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        if (fetchAddNewGuiToolArg == null) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        final ToolRemoteCmdStatus handleAiaAddNew = super.handleAiaAddNew(super.m_groupPath, fetchAddNewGuiToolArg, toolRemoteCmdDescriptor.getCommand());
        if (!handleAiaAddNew.getSuccessOrFailure()) {
            return handleAiaAddNew;
        }
        try {
            super.m_pmpObject.updateSvcNameCache(this.getPropGroupPath());
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381990L, new Object[] { "AiaRemoteObject", fetchAddNewGuiToolArg, ex.getMessage() }));
        }
        if (this.findSvcInChildrenNameVect(fetchAddNewGuiToolArg) < 0) {
            try {
                final AiaTRemoteObject obj = new AiaTRemoteObject(this, fetchAddNewGuiToolArg, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
                if (AiaRemoteObject.DEBUG_TRACE) {
                    System.out.println("Adding child node " + obj.hashCode() + ": " + fetchAddNewGuiToolArg + " to parent " + this.getPropGroupPath() + " to parentObject: " + this.hashCode());
                }
                this.addTreeNodeRemObjRef(obj, fetchAddNewGuiToolArg, this.getPropGroupPath());
                super.m_childrenRemoteObjects.addElement(obj);
                super.m_childrenNames.addElement(fetchAddNewGuiToolArg);
                super.m_numChildren = super.m_childrenNames.size();
                handleAiaAddNew.setSuccessStatus();
                return handleAiaAddNew;
            }
            catch (Exception ex2) {
                msg = UBToolsMsg.getMsg(7094295313015381991L, new Object[] { "AiaRemoteObject", fetchAddNewGuiToolArg, super.m_personality });
                UBToolsMsg.logException(msg);
            }
        }
        handleAiaAddNew.setDetailErrMsg(msg);
        return handleAiaAddNew;
    }
    
    public synchronized ToolRemoteCmdStatus delSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        if (fetchAddNewGuiToolArg == null | fetchAddNewGuiToolArg.length() == 0) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("About to remove tool instance " + fetchAddNewGuiToolArg);
        }
        final IChimeraHierarchy toolInstRemObj = this.getToolInstRemObj(fetchAddNewGuiToolArg);
        if (toolInstRemObj != null && this.deleteTreeNodeRemObjRef(toolInstRemObj, fetchAddNewGuiToolArg, super.m_groupPath)) {
            if (AiaRemoteObject.DEBUG_TRACE) {
                System.out.println("Delete treenodeRemObjRef successful");
            }
            this.delToolInstRemObj(fetchAddNewGuiToolArg);
            super.m_controlCmdCache.remove(fetchAddNewGuiToolArg);
            super.m_numChildren = super.m_childrenNames.size();
            if (AiaRemoteObject.DEBUG_TRACE) {
                System.out.println("svcName = " + fetchAddNewGuiToolArg + " is removed, numChildren = " + super.m_numChildren);
                for (int i = 0; i < super.m_numChildren; ++i) {
                    System.out.println((String)super.m_childrenNames.elementAt(i));
                }
            }
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
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("AiaRemoteObject.getSvcSummaryStatusRSLabels(" + propSpec + ") is called");
        }
        final AiaControlCmd aiaControlCmd = (AiaControlCmd)this.lookupControlCmd(propSpec);
        try {
            toolRemoteCmdStatus.setGetSummaryStatuslblStatus(aiaControlCmd.getSummaryStatusRSFieldLabels());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "AiaRemoteObject", "getSvcSummaryStatusRSLabels", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getSvcSummaryStatusRS(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final AiaControlCmd aiaControlCmd = (AiaControlCmd)this.lookupControlCmd(propSpec);
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("AiaRemoteObject.getSvcSummaryStatusRS(" + propSpec + ") is called");
        }
        try {
            toolRemoteCmdStatus.setGetSummaryStatusDataStatus(aiaControlCmd.getSummaryStatusRS());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "AiaRemoteObject", "getSvcSummaryStatusRS", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getSvcDetailStatusRSLabels(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final AiaControlCmd aiaControlCmd = (AiaControlCmd)this.lookupControlCmd(propSpec);
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("doing AiaRemoteObject.getSvcDetailStatusRSLabels(" + propSpec + ") is called");
        }
        try {
            toolRemoteCmdStatus.setGetDetailStatusLblStatus(aiaControlCmd.getDetailStatusRSColumnCaptions());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "AiaRemoteObject", "getSvcDetailStatusRSLabels", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getSvcDetailStatusRS(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final AiaControlCmd aiaControlCmd = (AiaControlCmd)this.lookupControlCmd(propSpec);
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println("AiaRemoteObject.getSvcDetailStatusRS(" + propSpec + ") is called");
        }
        try {
            toolRemoteCmdStatus.setGetDetailStatusDataStatus(aiaControlCmd.getDetailStatusRS());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "AiaRemoteObject", "getSvcDetailStatusRS", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public Object lookupControlCmd(final String s) {
        return this.lookupControlCmdBySvcName(s, super.m_pmpObject.getSvcName(s));
    }
    
    public Object removeControlCmd(final String s) {
        return super.m_controlCmdCache.remove(super.m_pmpObject.getSvcName(s));
    }
    
    private Object lookupControlCmdBySvcName(final String s, final String key) {
        if (!super.m_controlCmdCache.containsKey(key)) {
            final AiaControlCmd value = new AiaControlCmd(new SvcStartArgsPkt(this.m_myParentPlugin.getPolicy(super.m_pmpObject.getPersonStrFromPropPath(s)), s, key, super.m_pmpObject), key, super.m_rmiHost, super.m_rmiPort);
            super.m_controlCmdCache.put(key, value);
            return value;
        }
        return super.m_controlCmdCache.get(key);
    }
    
    public void updateSvcControlObjState(final int n, final String s, final String s2) {
        try {
            final AiaControlCmd aiaControlCmd = (AiaControlCmd)this.lookupControlCmdBySvcName(s, s2);
            if (aiaControlCmd != null) {
                if (n == 1) {
                    aiaControlCmd.setStateStart();
                }
                else if (n == 2) {
                    aiaControlCmd.setStateStop();
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
        if (AiaRemoteObject.DEBUG_TRACE) {
            System.out.println(" calling AiaRemoteObject.refreshProperties()...");
        }
        try {
            super.m_newChildrenNames = super.m_pmpObject.getSvcNameForParentGrp(this.getPropGroupPath());
            this.handleConfigChanges();
        }
        catch (Exception ex) {}
    }
    
    private synchronized void handleConfigChanges() {
        final Vector childrenRemoteObjects = new Vector<AiaTRemoteObject>();
        final Vector childrenNames = new Vector<String>();
        try {
            if (super.m_newChildrenNames == null) {
                return;
            }
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                final int svcInChildrenNameVect = this.findSvcInChildrenNameVect(obj);
                if (svcInChildrenNameVect < 0) {
                    childrenRemoteObjects.addElement(new AiaTRemoteObject(this, obj, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                }
                else {
                    childrenRemoteObjects.addElement((AiaTRemoteObject)super.m_childrenRemoteObjects.elementAt(svcInChildrenNameVect));
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
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "AiaRemoteObject", "handleConfigChanges", ex.toString() }));
        }
    }
    
    private void makeAllToolRemoteObjects() {
        try {
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                super.m_childrenRemoteObjects.addElement(new AiaTRemoteObject(this, obj, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                super.m_childrenNames.addElement(obj);
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "AiaRemoteObject", "makeAllToolRemoteObjects", ex.toString() }));
        }
    }
    
    private boolean addTreeNodeRemObjRef(final AiaTRemoteObject aiaTRemoteObject, final String str, final String str2) {
        try {
            return this.m_myParentPlugin.addChildNode(this.m_stubObject, this, aiaTRemoteObject, super.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private boolean deleteTreeNodeRemObjRef(final IChimeraHierarchy chimeraHierarchy, final String str, final String str2) {
        final AiaTRemoteObject aiaTRemoteObject = (AiaTRemoteObject)chimeraHierarchy;
        try {
            return this.m_myParentPlugin.delChildNode(aiaTRemoteObject.getRemStub(), chimeraHierarchy, str, super.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private void postAddSrvrEvent(final String s) {
        final String svcName = super.m_pmpObject.getSvcName(s);
        final IChimeraHierarchy toolInstRemObj = this.getToolInstRemObj(svcName);
        final AiaTRemoteObject aiaTRemoteObject = (AiaTRemoteObject)toolInstRemObj;
        try {
            this.m_myParentPlugin.postAddSrvrEvent(aiaTRemoteObject.getRemStub(), toolInstRemObj, s, svcName, super.getEventStream(s));
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException("problem posting add server event");
        }
    }
    
    private void postTrimSrvrEvent(final String s) {
        final String svcName = super.m_pmpObject.getSvcName(s);
        final IChimeraHierarchy toolInstRemObj = this.getToolInstRemObj(svcName);
        final AiaTRemoteObject aiaTRemoteObject = (AiaTRemoteObject)toolInstRemObj;
        try {
            this.m_myParentPlugin.postTrimSrvrEvent(aiaTRemoteObject.getRemStub(), toolInstRemObj, s, svcName, super.getEventStream(s));
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException("problem posting trim server event");
        }
    }
    
    public void shutdown() {
        super.shutdown();
        super.m_controlCmdCache.clear();
        super.m_childrenRemoteObjects = null;
        super.m_childrenNames = null;
        super.m_newChildrenNames = null;
    }
    
    static {
        AiaRemoteObject.DEBUG_TRACE = false;
    }
}

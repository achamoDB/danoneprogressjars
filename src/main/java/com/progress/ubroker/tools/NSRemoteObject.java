// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Vector;
import java.util.Hashtable;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.property.IPropertyManagerRemote;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteStub;
import com.progress.ubroker.util.IPropConst;

public class NSRemoteObject extends AbstractGuiPluginRemObj implements IYodaRMI, IYodaSharedResources, IBTMsgCodes, IPropConst
{
    static final String NS_REMOTE_OBJ_NAME = "NameServer TreeNode Remote Object";
    NSGuiPlugin m_myParentPlugin;
    RemoteStub m_stubObject;
    
    public NSRemoteObject(final NSGuiPlugin myParentPlugin, final String s, final PropMgrPlugin propMgrPlugin) throws RemoteException {
        super(myParentPlugin, s, propMgrPlugin);
        this.m_myParentPlugin = null;
        this.m_stubObject = null;
        this.m_myParentPlugin = myParentPlugin;
        try {
            this.m_stubObject = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381976L, new Object[] { "NameServer TreeNode Remote Object", ex.getMessage() }));
        }
    }
    
    public Enumeration getChildren() throws RemoteException {
        super.getSvcChildren();
        if (super.m_configChanged && super.m_newChildrenNames != null) {
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
        return "com.progress.vj.ubroker.NSMMCClient";
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
    
    public String getAdminServerIPAddr() throws RemoteException {
        return super.getAdminServerIPAddr();
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
                case 100: {
                    toolRemoteCmdStatus = this.startSvcProcess(toolRemoteCmdDescriptor);
                    break;
                }
                case 101: {
                    toolRemoteCmdStatus = this.stopSvcProcess(toolRemoteCmdDescriptor);
                    break;
                }
                case 102: {
                    toolRemoteCmdStatus = this.pingService(toolRemoteCmdDescriptor);
                    break;
                }
                case 103: {
                    toolRemoteCmdStatus = this.addNewSvcGuiTool(toolRemoteCmdDescriptor);
                    break;
                }
                case 104: {
                    toolRemoteCmdStatus = this.delSvcGuiTool(toolRemoteCmdDescriptor);
                    break;
                }
                case 111: {
                    toolRemoteCmdStatus = this.getSummaryStatus(toolRemoteCmdDescriptor);
                    break;
                }
                case 112: {
                    toolRemoteCmdStatus = this.getDetailStatus(toolRemoteCmdDescriptor);
                    break;
                }
                case 113: {
                    toolRemoteCmdStatus = this.getNSLocationChoice(toolRemoteCmdDescriptor);
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
        try {
            toolRemoteCmdStatus.setSuccessOrFailureStatus(((NSControlCmd)this.lookupControlCmd(propSpec)).startService());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381986L, new Object[] { "NSRemoteObject", super.m_personality, propSpec, ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus stopSvcProcess(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final NSControlCmd nsControlCmd = (NSControlCmd)this.lookupControlCmd(propSpec);
        try {
            if (nsControlCmd.stopService(propSpec)) {
                if (this.removeControlCmd(propSpec) == null) {
                    UBToolsMsg.logMsg(UBToolsMsg.getMsg(7094295313015381987L, new Object[] { "NSRemoteObject", "NSControlCmd", propSpec }));
                }
                toolRemoteCmdStatus.setSuccessStatus();
            }
            else {
                toolRemoteCmdStatus.setErrorStatus(-1);
            }
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381988L, new Object[] { "NSRemoteObject", propSpec, ex.toString() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus pingService(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final NSControlCmd nsControlCmd = (NSControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        final int fetchPingSvcArg = toolRemoteCmdDescriptor.fetchPingSvcArg();
        try {
            long pingSvcStatus;
            if (fetchPingSvcArg == 0) {
                pingSvcStatus = nsControlCmd.pingService();
            }
            else {
                pingSvcStatus = nsControlCmd.pingService(fetchPingSvcArg);
            }
            toolRemoteCmdStatus.setPingSvcStatus(pingSvcStatus);
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381989L, new Object[] { "NSRemoteObject", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus addNewSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        String msg = null;
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        final String fetchAddNewGuiToolArg2 = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg2();
        if (fetchAddNewGuiToolArg == null) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        final ToolRemoteCmdStatus handleNSAddNew = super.handleNSAddNew(super.m_groupPath, fetchAddNewGuiToolArg, fetchAddNewGuiToolArg2, toolRemoteCmdDescriptor.getCommand());
        if (!handleNSAddNew.getSuccessOrFailure()) {
            return handleNSAddNew;
        }
        try {
            super.m_pmpObject.updateSvcNameCache(super.m_groupPath);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381990L, new Object[] { "NSRemoteObject", fetchAddNewGuiToolArg, ex.toString() }));
        }
        if (this.findSvcInChildrenNameVect(fetchAddNewGuiToolArg) < 0) {
            try {
                final NSTRemoteObject obj = new NSTRemoteObject(this, fetchAddNewGuiToolArg, super.m_groupPath, super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
                this.addTreeNodeRemObjRef(obj, fetchAddNewGuiToolArg, super.m_groupPath);
                super.m_childrenRemoteObjects.addElement(obj);
                super.m_childrenNames.addElement(fetchAddNewGuiToolArg);
                super.m_numChildren = super.m_childrenNames.size();
                handleNSAddNew.setSuccessStatus();
                return handleNSAddNew;
            }
            catch (Exception ex2) {
                msg = UBToolsMsg.getMsg(7094295313015381991L, new Object[] { "NSTRemoteObject", fetchAddNewGuiToolArg, super.m_personality });
                UBToolsMsg.logException(msg);
            }
        }
        handleNSAddNew.setDetailErrMsg(msg);
        return handleNSAddNew;
    }
    
    public synchronized ToolRemoteCmdStatus delSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchRemoveGuiToolArg = toolRemoteCmdDescriptor.fetchRemoveGuiToolArg();
        if (fetchRemoveGuiToolArg == null | fetchRemoveGuiToolArg.length() == 0) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        final int svcInChildrenNameVect = this.findSvcInChildrenNameVect(fetchRemoveGuiToolArg);
        if (svcInChildrenNameVect >= 0 && this.deleteTreeNodeRemObjRef((IChimeraHierarchy)super.m_childrenRemoteObjects.elementAt(svcInChildrenNameVect), fetchRemoveGuiToolArg, super.m_groupPath)) {
            super.m_childrenNames.removeElementAt(svcInChildrenNameVect);
            super.m_childrenRemoteObjects.removeElementAt(svcInChildrenNameVect);
            super.m_controlCmdCache.remove(fetchRemoveGuiToolArg);
            super.m_numChildren = super.m_childrenNames.size();
            if (super.m_pmpObject.delSvcConfig(fetchRemoveGuiToolArg, super.m_groupPath)) {
                toolRemoteCmdStatus.setSuccessStatus();
                return toolRemoteCmdStatus;
            }
        }
        toolRemoteCmdStatus.setErrorStatus(-1);
        return toolRemoteCmdStatus;
    }
    
    public ToolRemoteCmdStatus getSummaryStatus(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final NSControlCmd nsControlCmd = (NSControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        try {
            toolRemoteCmdStatus.setGetNSSummaryStatus(nsControlCmd.getSummaryStatus());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381981L, new Object[] { "NSRemoteObject", ex.toString() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getDetailStatus(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final NSControlCmd nsControlCmd = (NSControlCmd)this.lookupControlCmd(toolRemoteCmdDescriptor.getPropSpec());
        try {
            toolRemoteCmdStatus.setGetNSDetailStatus(nsControlCmd.getDetailStatus());
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381994L, new Object[] { "NSRemoteObject", ex.toString() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus getNSLocationChoice(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        final String fetchGetNSLocPropArg = toolRemoteCmdDescriptor.fetchGetNSLocPropArg();
        try {
            toolRemoteCmdStatus.setNSLocPropStatus(super.m_pmpObject.getNSLocation(propSpec + "." + fetchGetNSLocPropArg));
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            final String msg = UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "NSRemoteObject", "getNSLocationChoice()", ex.toString() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public Hashtable getData(final String str, final String[] array) {
        Hashtable data = null;
        try {
            data = ((NSControlCmd)this.lookupControlCmd(str)).getData(array);
            return data;
        }
        catch (Exception ex) {
            UBToolsMsg.logException("NameServer not started: " + str + ". Cannot get stats data.");
            return data;
        }
    }
    
    public String getApplicationName() throws RemoteException {
        return "NameServer TreeNode Remote Object";
    }
    
    public void shutdown() {
        super.shutdown();
    }
    
    public Object lookupControlCmd(final String s) {
        return this.lookupControlCmdBySvcName(s, super.m_pmpObject.getSvcName(s));
    }
    
    private Object lookupControlCmdBySvcName(final String s, final String key) {
        if (!super.m_controlCmdCache.containsKey(key)) {
            final NSControlCmd value = new NSControlCmd(new SvcStartArgsPkt(super.m_policyInfo, s, key, super.m_pmpObject), key, super.m_rmiHost, super.m_rmiPort);
            super.m_controlCmdCache.put(key, value);
            return value;
        }
        return super.m_controlCmdCache.get(key);
    }
    
    public void updateSvcControlObjState(final int n, final String s, final String s2) {
        try {
            final NSControlCmd nsControlCmd = (NSControlCmd)this.lookupControlCmdBySvcName(s, s2);
            if (nsControlCmd != null) {
                if (n == 1) {
                    nsControlCmd.setStateStart();
                }
                else if (n == 2) {
                    nsControlCmd.setStateStop();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void handleRefreshProperties() {
        super.m_newChildrenNames = super.m_pmpObject.getSvcNameForParentGrp(super.m_groupPath);
        this.handleConfigChanges();
    }
    
    private synchronized void handleConfigChanges() {
        final Vector childrenRemoteObjects = new Vector<NSTRemoteObject>();
        final Vector childrenNames = new Vector<String>();
        try {
            if (super.m_newChildrenNames == null) {
                return;
            }
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                final int svcInChildrenNameVect = this.findSvcInChildrenNameVect(obj);
                if (svcInChildrenNameVect < 0) {
                    childrenRemoteObjects.addElement(new NSTRemoteObject(this, obj, super.m_groupPath, super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                }
                else {
                    childrenRemoteObjects.addElement((NSTRemoteObject)super.m_childrenRemoteObjects.elementAt(svcInChildrenNameVect));
                    super.m_childrenNames.removeElementAt(svcInChildrenNameVect);
                    super.m_childrenRemoteObjects.removeElementAt(svcInChildrenNameVect);
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
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "NSRemoteObject", "handleConfigChanges", ex.toString() }));
        }
    }
    
    private void makeAllToolRemoteObjects() {
        try {
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                super.m_childrenRemoteObjects.addElement(new NSTRemoteObject(this, obj, super.m_groupPath, super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                super.m_childrenNames.addElement(obj);
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "NSRemoteObject", "makeAllToolRemoteObjects", ex.toString() }));
        }
    }
    
    private boolean addTreeNodeRemObjRef(final NSTRemoteObject nstRemoteObject, final String str, final String str2) {
        try {
            return this.m_myParentPlugin.addChildNode(this.m_stubObject, this, nstRemoteObject, super.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private boolean deleteTreeNodeRemObjRef(final IChimeraHierarchy chimeraHierarchy, final String str, final String str2) {
        final NSTRemoteObject nstRemoteObject = (NSTRemoteObject)chimeraHierarchy;
        try {
            return this.m_myParentPlugin.delChildNode(nstRemoteObject.getRemStub(), chimeraHierarchy, str, super.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}

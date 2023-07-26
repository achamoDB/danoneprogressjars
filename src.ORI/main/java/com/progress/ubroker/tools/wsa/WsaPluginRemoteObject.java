// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import java.util.Vector;
import com.progress.ubroker.tools.SvcStartArgsPkt;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.ubroker.tools.IYodaRMI;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.ubroker.tools.UBToolsMsg;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.ubroker.tools.PropMgrPlugin;
import java.rmi.server.RemoteStub;
import com.progress.ubroker.tools.AbstractGuiPluginRemObj;

public class WsaPluginRemoteObject extends AbstractGuiPluginRemObj
{
    WsaGuiPlugin m_plugin;
    RemoteStub m_stub;
    static boolean DEBUG_TRACE;
    
    public WsaPluginRemoteObject(final WsaGuiPlugin plugin, final String str, final PropMgrPlugin propMgrPlugin) throws RemoteException {
        super(plugin, str, propMgrPlugin);
        this.m_plugin = null;
        this.m_stub = null;
        this.m_plugin = plugin;
        if (WsaPluginRemoteObject.DEBUG_TRACE) {
            System.out.println("in WsaPluginRemoteObject constructor " + str);
        }
        try {
            this.m_stub = UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381976L, new Object[] { "WsaPluginRemoteObject", "WSA Plugin Remote Object", ex.getMessage() }));
        }
        if (WsaPluginRemoteObject.DEBUG_TRACE) {
            System.out.println("about to exit WsaPluginRemoteObject constructor");
        }
    }
    
    public Enumeration getChildren() throws RemoteException {
        if (WsaPluginRemoteObject.DEBUG_TRACE) {
            System.out.println("in WsaPluginRemoteObject.getChildren()");
        }
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
        return "com.progress.vj.ubroker.wsa.WsaPersonMMCClient";
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
        return null;
    }
    
    public ToolRemoteCmdStatus stopSvcProcess(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        return null;
    }
    
    public ToolRemoteCmdStatus pingService(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String propSpec = toolRemoteCmdDescriptor.getPropSpec();
        if (WsaPluginRemoteObject.DEBUG_TRACE) {
            System.out.println("WsaPluginRemoteObject.pingService(" + propSpec + ") is called");
        }
        final WsaControlCmd wsaControlCmd = (WsaControlCmd)this.lookupControlCmd(propSpec);
        final int fetchPingSvcArg = toolRemoteCmdDescriptor.fetchPingSvcArg();
        try {
            long n;
            if (fetchPingSvcArg == 0) {
                n = wsaControlCmd.pingService();
            }
            else {
                n = wsaControlCmd.pingService(fetchPingSvcArg);
            }
            if (WsaPluginRemoteObject.DEBUG_TRACE) {
                System.out.println("  active for " + n + " seconds.");
            }
            toolRemoteCmdStatus.setPingSvcStatus(n);
            return toolRemoteCmdStatus;
        }
        catch (Exception ex) {
            final String msg = UBToolsMsg.getMsg(7094295313015381989L, new Object[] { "WsaPluginRemoteObject", ex.getMessage() });
            UBToolsMsg.logException(msg);
            toolRemoteCmdStatus.setDetailErrMsg(msg);
            return toolRemoteCmdStatus;
        }
    }
    
    public ToolRemoteCmdStatus addNewSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        if (WsaPluginRemoteObject.DEBUG_TRACE) {
            System.out.println("WsaPluginRemoteObject.addNewSvcGuiTool():this.hashCode() = " + this.hashCode());
        }
        String msg = null;
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchAddNewGuiToolArg = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg();
        final String fetchAddNewGuiToolArg2 = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg2();
        final String fetchAddNewGuiToolArg3 = toolRemoteCmdDescriptor.fetchAddNewGuiToolArg3();
        if (fetchAddNewGuiToolArg == null || fetchAddNewGuiToolArg2 == null || fetchAddNewGuiToolArg3 == null) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        final ToolRemoteCmdStatus handleWsaAddNew = super.handleWsaAddNew(super.m_groupPath, fetchAddNewGuiToolArg, fetchAddNewGuiToolArg2, fetchAddNewGuiToolArg3, toolRemoteCmdDescriptor.getCommand());
        if (!handleWsaAddNew.getSuccessOrFailure()) {
            return handleWsaAddNew;
        }
        try {
            super.m_pmpObject.updateSvcNameCache(this.getPropGroupPath());
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381990L, new Object[] { "WsaPluginRemoteObject", fetchAddNewGuiToolArg, ex.getMessage() }));
        }
        if (this.findSvcInChildrenNameVect(fetchAddNewGuiToolArg) < 0) {
            try {
                final WsaInstanceRemoteObject obj = new WsaInstanceRemoteObject(this, fetchAddNewGuiToolArg, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
                if (WsaPluginRemoteObject.DEBUG_TRACE) {
                    System.out.println("Adding child node " + obj.hashCode() + ": " + fetchAddNewGuiToolArg + " to parent " + this.getPropGroupPath() + " to parentObject: " + this.hashCode());
                }
                this.addTreeNodeRemObjRef(obj, fetchAddNewGuiToolArg, this.getPropGroupPath());
                super.m_childrenRemoteObjects.addElement(obj);
                super.m_childrenNames.addElement(fetchAddNewGuiToolArg);
                super.m_numChildren = super.m_childrenNames.size();
                handleWsaAddNew.setSuccessStatus();
                return handleWsaAddNew;
            }
            catch (Exception ex2) {
                msg = UBToolsMsg.getMsg(7094295313015381991L, new Object[] { "WsaPluginRemoteObject", fetchAddNewGuiToolArg, super.m_personality });
                UBToolsMsg.logException(msg);
            }
        }
        handleWsaAddNew.setDetailErrMsg(msg);
        return handleWsaAddNew;
    }
    
    public synchronized ToolRemoteCmdStatus delSvcGuiTool(final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor) {
        final ToolRemoteCmdStatus toolRemoteCmdStatus = new ToolRemoteCmdStatus(toolRemoteCmdDescriptor.getCommand());
        final String fetchRemoveGuiToolArg = toolRemoteCmdDescriptor.fetchRemoveGuiToolArg();
        if (fetchRemoveGuiToolArg == null | fetchRemoveGuiToolArg.length() == 0) {
            toolRemoteCmdStatus.setErrorStatus(-3);
            return toolRemoteCmdStatus;
        }
        if (WsaPluginRemoteObject.DEBUG_TRACE) {
            System.out.println("About to remove tool instance " + fetchRemoveGuiToolArg);
        }
        final IChimeraHierarchy toolInstRemObj = this.getToolInstRemObj(fetchRemoveGuiToolArg);
        if (toolInstRemObj != null && this.deleteTreeNodeRemObjRef(toolInstRemObj, fetchRemoveGuiToolArg, super.m_groupPath)) {
            if (WsaPluginRemoteObject.DEBUG_TRACE) {
                System.out.println("Delete treenodeRemObjRef successful");
            }
            this.delToolInstRemObj(fetchRemoveGuiToolArg);
            super.m_controlCmdCache.remove(fetchRemoveGuiToolArg);
            super.m_numChildren = super.m_childrenNames.size();
            if (WsaPluginRemoteObject.DEBUG_TRACE) {
                System.out.println("svcName = " + fetchRemoveGuiToolArg + " is removed, numChildren = " + super.m_numChildren);
                for (int i = 0; i < super.m_numChildren; ++i) {
                    System.out.println((String)super.m_childrenNames.elementAt(i));
                }
            }
            if (super.m_pmpObject.delSvcConfig(fetchRemoveGuiToolArg, super.m_groupPath)) {
                toolRemoteCmdStatus.setSuccessStatus();
                return toolRemoteCmdStatus;
            }
        }
        toolRemoteCmdStatus.setErrorStatus(-1);
        return toolRemoteCmdStatus;
    }
    
    public Object lookupControlCmd(final String s) {
        return this.lookupControlCmdBySvcName(s, super.m_pmpObject.getSvcName(s));
    }
    
    private Object lookupControlCmdBySvcName(final String s, final String key) {
        if (!super.m_controlCmdCache.containsKey(key)) {
            final WsaControlCmd value = new WsaControlCmd(new SvcStartArgsPkt(this.m_plugin.getPolicy(super.m_pmpObject.getPersonStrFromPropPath(s)), s, key, super.m_pmpObject), key, super.m_rmiHost, super.m_rmiPort);
            super.m_controlCmdCache.put(key, value);
            return value;
        }
        return super.m_controlCmdCache.get(key);
    }
    
    public void updateSvcControlObjState(final int n, final String s, final String s2) {
        try {
            final WsaControlCmd wsaControlCmd = (WsaControlCmd)this.lookupControlCmdBySvcName(s, s2);
            if (wsaControlCmd != null) {
                if (n == 1) {
                    wsaControlCmd.setStateStart();
                    this.expressInterestStartSvcEvent();
                }
                else if (n == 2) {
                    wsaControlCmd.setStateStop();
                    this.expressInterestStopSvcEvent();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateControlCmd(final String s) {
        if (super.m_controlCmdCache.containsKey(s)) {
            super.m_controlCmdCache.get(s).updateConfig();
        }
    }
    
    public synchronized void handleRefreshProperties() {
        if (WsaPluginRemoteObject.DEBUG_TRACE) {
            System.out.println(" calling WsaPluginRemoteObject.refreshProperties().");
        }
        try {
            super.m_newChildrenNames = super.m_pmpObject.getSvcNameForParentGrp(this.getPropGroupPath());
            this.handleConfigChanges();
        }
        catch (Exception ex) {}
    }
    
    private synchronized void handleConfigChanges() {
        final Vector childrenRemoteObjects = new Vector<WsaInstanceRemoteObject>();
        final Vector childrenNames = new Vector<String>();
        try {
            if (super.m_newChildrenNames == null) {
                return;
            }
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                final int svcInChildrenNameVect = this.findSvcInChildrenNameVect(obj);
                if (svcInChildrenNameVect < 0) {
                    childrenRemoteObjects.addElement(new WsaInstanceRemoteObject(this, obj, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                }
                else {
                    childrenRemoteObjects.addElement((WsaInstanceRemoteObject)super.m_childrenRemoteObjects.elementAt(svcInChildrenNameVect));
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
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "WsaPluginRemoteObject", "handleConfigChanges", ex.toString() }));
        }
    }
    
    private void makeAllToolRemoteObjects() {
        try {
            for (int i = 0; i < super.m_newChildrenNames.length; ++i) {
                final String obj = super.m_newChildrenNames[i];
                super.m_childrenRemoteObjects.addElement(new WsaInstanceRemoteObject(this, obj, this.getPropGroupPath(), super.m_userName, super.m_password, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL));
                super.m_childrenNames.addElement(obj);
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381977L, new Object[] { "WsaPluginRemoteObject", "makeAllToolRemoteObjects", ex.toString() }));
        }
    }
    
    private boolean addTreeNodeRemObjRef(final WsaInstanceRemoteObject wsaInstanceRemoteObject, final String str, final String str2) {
        try {
            return this.m_plugin.addChildNode(this.m_stub, this, wsaInstanceRemoteObject, this.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private boolean deleteTreeNodeRemObjRef(final IChimeraHierarchy chimeraHierarchy, final String str, final String str2) {
        try {
            return this.m_plugin.delChildNode(((WsaInstanceRemoteObject)chimeraHierarchy).getRemStub(), chimeraHierarchy, str, this.getEventStream(str2 + "." + str));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    static {
        WsaPluginRemoteObject.DEBUG_TRACE = false;
    }
}

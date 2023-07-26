// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.NameServerPluginComponent;
import com.progress.common.networkevents.IEventStream;
import java.rmi.server.RemoteStub;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.Remote;
import com.progress.chimera.adminserver.IAdministrationServer;

public class NSGuiPlugin extends AbstractGuiPlugin implements IBTMsgCodes
{
    public static String NS_ID;
    public static String PLUGIN_ID;
    
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        return super.init(n, administrationServer, array);
    }
    
    public String getComponentClassName() {
        return NameServerPluginComponent.class.getName();
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        super.m_wkRemote = (IChimeraHierarchy)super.getRemoteObject(s, s2);
        try {
            ((NSRemoteObject)super.m_wkRemote).setRMIUsrInfo(s, s2, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381975L, new Object[] { "NSRemoteObject", ex.getMessage() }));
        }
        return super.m_wkRemote;
    }
    
    public boolean delChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final String s, final IEventStream eventStream) {
        try {
            super.delChildNode(remoteStub, chimeraHierarchy, s, eventStream);
            return true;
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381982L, new Object[] { "NSGuiPlugin", s, ex.toString() }));
            return false;
        }
    }
    
    public boolean addChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, final IEventStream eventStream) {
        try {
            super.addChildNode(remoteStub, chimeraHierarchy, chimeraHierarchy2, eventStream);
            return true;
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381983L, new Object[] { "NSGuiPlugin", chimeraHierarchy2.toString(), ex.toString() }));
            return false;
        }
    }
    
    public void refreshAddNewToolPanel(final IChimeraHierarchy chimeraHierarchy, final String s) {
    }
    
    public void shutdown() {
        this.setComponentState(3);
        ((NSRemoteObject)super.m_wkRemote).shutdown();
        if (super.m_pluginComponent != null) {
            super.m_pluginComponent.stop();
        }
    }
    
    static {
        NSGuiPlugin.NS_ID = "NameServer";
        NSGuiPlugin.PLUGIN_ID = "NameServer";
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.adapter;

import com.progress.ubroker.management.AdapterPluginComponent;
import com.progress.common.networkevents.IEventStream;
import java.rmi.server.RemoteStub;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.Remote;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.ubroker.tools.IBTMsgCodes;
import com.progress.ubroker.tools.AbstractAdapterGuiPlugin;

public class AdapterGuiPlugin extends AbstractAdapterGuiPlugin implements IBTMsgCodes
{
    protected static final boolean DEBUG_TRACE = false;
    public static String ADAPTER_ID;
    
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        return super.init(n, administrationServer, array);
    }
    
    public String getComponentClassName() {
        return AdapterPluginComponent.class.getName();
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        super.m_wkRemote = (IChimeraHierarchy)super.getRemoteObject(s, s2);
        try {
            ((AdapterRemoteObject)super.m_wkRemote).setRMIUsrInfo(s, s2, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381975L, new Object[] { "AdapterRemoteObject", ex.getMessage() }));
        }
        return super.m_wkRemote;
    }
    
    public boolean delChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final String s, final IEventStream eventStream) {
        try {
            return super.delChildNode(remoteStub, chimeraHierarchy, s, eventStream);
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381982L, new Object[] { "AdapterGuiPlugin", s, ex.toString() }));
            return false;
        }
    }
    
    public boolean addChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, final IEventStream eventStream) {
        try {
            super.addChildNode(remoteStub, chimeraHierarchy, chimeraHierarchy2, eventStream);
            return true;
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381983L, new Object[] { "AdapterGuiPlugin", chimeraHierarchy2.toString(), ex.toString() }));
            return false;
        }
    }
    
    public void refreshAddNewToolPanel(final IChimeraHierarchy chimeraHierarchy, final String s) {
    }
    
    public void shutdown() {
        ((AdapterRemoteObject)super.m_wkRemote).shutdown();
    }
    
    static {
        AdapterGuiPlugin.ADAPTER_ID = "SonicMQ Adapter";
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.aia;

import com.progress.ubroker.management.AiaPluginComponent;
import com.progress.common.networkevents.IEventStream;
import java.rmi.server.RemoteStub;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.Remote;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.ubroker.tools.IBTMsgCodes;
import com.progress.ubroker.tools.AbstractGuiPlugin;

public class AiaGuiPlugin extends AbstractGuiPlugin implements IBTMsgCodes
{
    protected static final boolean DEBUG_TRACE = false;
    public static String AIA_ID;
    private static AiaGuiPlugin m_self;
    
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        return super.init(n, administrationServer, array);
    }
    
    public String getComponentClassName() {
        return AiaPluginComponent.class.getName();
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        super.m_wkRemote = (IChimeraHierarchy)super.getRemoteObject(s, s2);
        try {
            ((AiaRemoteObject)super.m_wkRemote).setRMIUsrInfo(s, s2, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381975L, new Object[] { "AiaRemoteObject", ex.getMessage() }));
        }
        return super.m_wkRemote;
    }
    
    public boolean delChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final String s, final IEventStream eventStream) {
        try {
            return super.delChildNode(remoteStub, chimeraHierarchy, s, eventStream);
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381982L, new Object[] { "AiaGuiPlugin", s, ex.toString() }));
            return false;
        }
    }
    
    public boolean addChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, final IEventStream eventStream) {
        try {
            super.addChildNode(remoteStub, chimeraHierarchy, chimeraHierarchy2, eventStream);
            return true;
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381983L, new Object[] { "AiaGuiPlugin", chimeraHierarchy2.toString(), ex.toString() }));
            return false;
        }
    }
    
    public void refreshAddNewToolPanel(final IChimeraHierarchy chimeraHierarchy, final String s) {
    }
    
    public void shutdown() {
        ((AiaRemoteObject)super.m_wkRemote).shutdown();
    }
    
    static {
        AiaGuiPlugin.AIA_ID = "AppServer Internet Adapter";
        AiaGuiPlugin.m_self = null;
    }
}

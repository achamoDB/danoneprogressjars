// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import com.progress.ubroker.management.WsaPluginComponent;
import com.progress.ubroker.tools.UBToolsMsg;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.Remote;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.ubroker.tools.AbstractGuiPlugin;

public class WsaGuiPlugin extends AbstractGuiPlugin
{
    protected static final boolean DEBUG_TRACE = false;
    public static String WSA_ID;
    
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        return super.init(n, administrationServer, array);
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        super.m_wkRemote = (IChimeraHierarchy)super.getRemoteObject(s, s2);
        try {
            ((WsaPluginRemoteObject)super.m_wkRemote).setRMIUsrInfo(s, s2, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381975L, new Object[] { "WsaPluginRemoteObject", ex.getMessage() }));
        }
        return super.m_wkRemote;
    }
    
    public String getComponentClassName() {
        return WsaPluginComponent.class.getName();
    }
    
    public void shutdown() {
        ((WsaPluginRemoteObject)super.m_wkRemote).shutdown();
    }
    
    public boolean createInstance(final String[] array) {
        boolean fetchAddNewGuiToolStatus = false;
        final WsaPluginRemoteObject wsaPluginRemoteObject = (WsaPluginRemoteObject)super.m_wkRemote;
        try {
            if (array != null && array.length > 0) {
                final String s = array[0];
                final String s2 = (array.length > 1) ? array[1] : null;
                final String s3 = (array.length > 2) ? array[2] : null;
                final String instanceNameOnly = AbstractGuiPlugin.getInstanceNameOnly(s);
                if (instanceNameOnly != null && wsaPluginRemoteObject != null) {
                    if (s2 != null && s3 != null) {
                        super.m_cmdDescriptor.makeAddNewGuiToolPkt(instanceNameOnly, s2, s3);
                    }
                    else if (s2 != null && s3 == null) {
                        super.m_cmdDescriptor.makeAddNewGuiToolPkt(instanceNameOnly, s2);
                    }
                    else {
                        super.m_cmdDescriptor.makeAddNewGuiToolPkt(instanceNameOnly);
                    }
                    super.m_cmdStatus = wsaPluginRemoteObject.doRemoteToolCmd(super.m_cmdDescriptor);
                    fetchAddNewGuiToolStatus = super.m_cmdStatus.fetchAddNewGuiToolStatus();
                }
            }
        }
        catch (Exception ex) {
            fetchAddNewGuiToolStatus = false;
        }
        return fetchAddNewGuiToolStatus;
    }
    
    static {
        WsaGuiPlugin.WSA_ID = "Web Services Adapter";
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.MessengersPluginComponent;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.Remote;
import com.progress.chimera.adminserver.IAdministrationServer;

public class MSGuiPlugin extends AbstractGuiPlugin implements IBTMsgCodes
{
    public static String MESSENGER_ID;
    
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        return super.init(n, administrationServer, array);
    }
    
    public String getComponentClassName() {
        return MessengersPluginComponent.class.getName();
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        super.m_wkRemote = (IChimeraHierarchy)super.getRemoteObject(s, s2);
        try {
            ((MSRemoteObject)super.m_wkRemote).setRMIUsrInfo(s, s2, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
        }
        catch (Exception ex) {
            UBToolsMsg.logException(UBToolsMsg.getMsg(7094295313015381975L, new Object[] { "MSRemoteObject", ex.getMessage() }));
        }
        return super.m_wkRemote;
    }
    
    public void shutdown() {
        ((MSRemoteObject)super.m_wkRemote).shutdown();
    }
    
    static {
        MSGuiPlugin.MESSENGER_ID = "Messengers";
    }
}

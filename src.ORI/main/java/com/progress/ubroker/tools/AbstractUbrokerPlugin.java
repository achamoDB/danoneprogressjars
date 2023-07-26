// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.util.Vector;
import com.progress.chimera.adminserver.ServerPluginInfo;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventBroker;
import java.util.HashMap;
import com.progress.chimera.adminserver.ServerPolicyInfo;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.chimera.adminserver.AbstractServerPlugin;

public abstract class AbstractUbrokerPlugin extends AbstractServerPlugin
{
    protected static IAdministrationServer m_iAdministrationServer;
    protected static boolean m_warmStartInProgress;
    protected ServerPolicyInfo m_serverPolicyInfo;
    protected HashMap m_policyInfo;
    
    public AbstractUbrokerPlugin() {
        this.m_serverPolicyInfo = null;
        this.m_policyInfo = new HashMap();
    }
    
    public static IAdministrationServer getIAdministrationServer() {
        return AbstractUbrokerPlugin.m_iAdministrationServer;
    }
    
    public static IEventBroker getEventBroker() {
        try {
            return AbstractUbrokerPlugin.m_iAdministrationServer.getEventBroker();
        }
        catch (RemoteException ex) {
            return null;
        }
    }
    
    public boolean init(final int n, final IAdministrationServer iAdministrationServer, final String[] array) {
        try {
            final Vector serverPluginInfo = iAdministrationServer.getServerPluginInfo();
            for (int i = 0; i < serverPluginInfo.size(); ++i) {
                final ServerPluginInfo serverPluginInfo2 = serverPluginInfo.elementAt(i);
                if (serverPluginInfo2.getIndex() == n) {
                    this.m_serverPolicyInfo = serverPluginInfo2.getPolicy();
                }
                if (serverPluginInfo2.getPersonality() != null) {
                    this.m_policyInfo.put(serverPluginInfo2.getPersonality(), serverPluginInfo2.getPolicy());
                }
            }
        }
        catch (Exception ex) {
            this.m_serverPolicyInfo = null;
        }
        AbstractUbrokerPlugin.m_iAdministrationServer = iAdministrationServer;
        return super.init(n, AbstractUbrokerPlugin.m_iAdministrationServer, array);
    }
    
    public ServerPolicyInfo getPolicy(final String s) {
        if (this.m_policyInfo.containsKey(s)) {
            return this.m_policyInfo.get(s);
        }
        return this.m_serverPolicyInfo;
    }
    
    public static synchronized void setWarmStartInProgress(final boolean warmStartInProgress) {
        AbstractUbrokerPlugin.m_warmStartInProgress = warmStartInProgress;
    }
    
    public static boolean getWarmStartInProgress() {
        return AbstractUbrokerPlugin.m_warmStartInProgress;
    }
    
    static {
        AbstractUbrokerPlugin.m_iAdministrationServer = null;
        AbstractUbrokerPlugin.m_warmStartInProgress = false;
    }
}

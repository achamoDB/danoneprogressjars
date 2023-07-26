// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.RemoteException;
import com.progress.common.rmiregistry.IPingable;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.rmiregistry.RegistryManager;
import com.progress.chimera.log.RegistryManagerLog;
import java.rmi.RMISecurityManager;
import com.progress.common.util.NetInfo;
import com.progress.international.resources.ProgressResources;
import com.progress.message.asMsg;
import com.progress.common.util.IMessageCallback;

public class AdminServerQuery implements IAdminServerConst, IMessageCallback, asMsg
{
    static ProgressResources admBundle;
    static ProgressResources cmnBundle;
    private AdminServerGetopt m_args;
    
    public AdminServerQuery(final String[] array) {
        this.m_args = null;
        this.m_args = new AdminServerGetopt(array);
        String hostName = this.m_args.getHostName();
        if (hostName != null && !NetInfo.isLocalHost(hostName) && !this.m_args.isCluster()) {
            System.out.println("Ignoring host setting: " + hostName);
            hostName = null;
        }
        final String rmiRegistryPort = this.m_args.getRMIRegistryPort();
        int int1 = 0;
        try {
            int1 = Integer.parseInt(rmiRegistryPort);
        }
        catch (NumberFormatException ex) {
            System.err.println(usage());
            System.exit(1);
        }
        if (this.query(hostName, int1)) {
            System.exit(0);
        }
        else {
            System.exit(1);
        }
    }
    
    public static String usage() {
        return AdminServerQuery.cmnBundle.getTranString("Usage:") + " proadsv -query [options]" + AdminServerType.NEWLINE + AdminServerType.NEWLINE + "where \"options\" include:" + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServerQuery.admBundle.getTranString("AdminServer Usage help") + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServerQuery.admBundle.getTranString("AdminServer Usage port", new Object[] { IAdminServerConst.RMI_REGISTRY_PORT }) + AdminServerType.NEWLINE + AdminServerType.NEWLINE;
    }
    
    private boolean query(final String str, final int i) {
        System.setSecurityManager(new RMISecurityManager());
        RegistryManagerLog.get();
        final IAdminServerConnection adminServerConnection = (IAdminServerConnection)new RegistryManager(this, str, i).getPrimaryObject();
        if (this.m_args.isCluster()) {
            System.out.println("Checking AdminServer status on " + str + ":" + i + " ...");
        }
        if (adminServerConnection == null) {
            System.out.println(ExceptionMessageAdapter.getMessage(7021956244000743775L, null));
            return false;
        }
        try {
            ((IPingable)adminServerConnection).ping();
        }
        catch (RemoteException ex) {
            System.out.println(ExceptionMessageAdapter.getMessage(7021956244000743776L, null));
            return false;
        }
        System.out.println(ExceptionMessageAdapter.getMessage(7021956244000743777L, null));
        return true;
    }
    
    public void handleMessage(final String x) {
        System.out.println(x);
    }
    
    public void handleMessage(final int n, final String x) {
        if (n == 1) {
            System.out.println(x);
        }
    }
    
    public void handleException(final Throwable t, final String x) {
        System.err.println(x);
    }
    
    static {
        AdminServerQuery.admBundle = AdminServerType.admBundle;
        AdminServerQuery.cmnBundle = AdminServerType.cmnBundle;
    }
}

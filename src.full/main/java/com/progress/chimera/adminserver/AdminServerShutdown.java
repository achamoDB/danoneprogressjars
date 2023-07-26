// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.rmiregistry.IPingable;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.util.crypto;
import com.progress.common.util.acctAuthenticate;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.rmiregistry.RegistryManager;
import com.progress.common.util.NetInfo;
import java.rmi.RMISecurityManager;
import com.progress.chimera.log.RegistryManagerLog;
import com.progress.international.resources.ProgressResources;
import com.progress.chimera.log.AdminServerLog;
import com.progress.message.asMsg;
import com.progress.common.networkevents.IEventListener;
import com.progress.common.util.IMessageCallback;

public class AdminServerShutdown implements IAdminServerConst, IMessageCallback, IEventListener, asMsg
{
    static AdminServerLog m_adminServerSubsystem;
    static ProgressResources admBundle;
    static ProgressResources cmnBundle;
    private AdminServerGetopt m_args;
    
    public AdminServerShutdown(final String[] array) {
        this.m_args = null;
        RegistryManagerLog.get();
        this.m_args = new AdminServerGetopt(array);
        try {
            final String rmiRegistryPort = this.m_args.getRMIRegistryPort();
            Integer n = null;
            try {
                n = ((rmiRegistryPort == null) ? null : new Integer(Integer.parseInt(rmiRegistryPort)));
            }
            catch (NumberFormatException ex) {
                System.err.println(AdminServerShutdown.admBundle.getTranString("Provided port is not numeric - only numeric values allowed."));
                System.exit(1);
            }
            this.shutdown(this.m_args.getUserName(), this.m_args.getPassword(), n);
        }
        catch (Exception ex2) {
            System.err.println(AdminServerShutdown.admBundle.getTranString("Shutdown request failed."));
            System.exit(1);
        }
        System.exit(0);
    }
    
    public static String usage() {
        return AdminServerShutdown.cmnBundle.getTranString("Usage:") + " proadsv -stop [options]" + AdminServerType.NEWLINE + AdminServerType.NEWLINE + "where \"options\" include:" + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServerShutdown.admBundle.getTranString("AdminServer Usage help") + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServerShutdown.admBundle.getTranString("AdminServer Usage port", new Object[] { IAdminServerConst.RMI_REGISTRY_PORT }) + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServerShutdown.admBundle.getTranString("AdminServer Usage user") + AdminServerType.NEWLINE + AdminServerType.NEWLINE + AdminServerShutdown.admBundle.getTranString("AdminServer Usage password") + AdminServerType.NEWLINE + AdminServerType.NEWLINE;
    }
    
    private synchronized void shutdown(String s, String s2, final Integer obj) throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        String hostName = this.m_args.getHostName();
        if (hostName != null && !NetInfo.isLocalHost(hostName) && !this.m_args.isCluster()) {
            System.out.println("Ignoring host setting: " + hostName);
            hostName = null;
        }
        if (this.m_args.isCluster()) {
            System.out.println("Shutting down AdminServer on " + hostName + ":" + obj + " ...");
        }
        final IAdminServerConnection adminServerConnection = (IAdminServerConnection)new RegistryManager(this, hostName, obj).getPrimaryObject();
        if (adminServerConnection == null) {
            System.err.println(ExceptionMessageAdapter.getMessage(7021956244000743778L, null));
            if (hostName == null) {}
            return;
        }
        if (s == null) {
            try {
                new acctAuthenticate();
                s = acctAuthenticate.whoamiJNI();
            }
            catch (Exception ex) {}
            if (null == s) {
                s = System.getProperty("user.name");
            }
            s2 = new acctAuthenticate().generatePassword(s);
        }
        else if (s2 == null) {
            s2 = new acctAuthenticate().promptForPassword(s);
        }
        final crypto crypto = new crypto();
        s = crypto.encrypt(s);
        s2 = crypto.encrypt(s2);
        final IAdminServer connect = adminServerConnection.connect(s, s2);
        if (connect == null) {
            System.err.println(ExceptionMessageAdapter.getMessage(7021956244000743779L, null));
        }
        else {
            System.out.println(ExceptionMessageAdapter.getMessage(7021956244000742654L, null));
            connect.shutdown();
            final int n = 2;
            final int n2 = 60;
            try {
                new EventBroker(this).monitorObject((IPingable)connect, n * 1000, n2 * 1000, this);
                this.wait();
            }
            catch (Exception obj2) {
                System.err.println(ExceptionMessageAdapter.getMessage(7021956244000743771L, null));
                System.err.println("ERROR: " + obj2);
                throw obj2;
            }
            System.out.println(ExceptionMessageAdapter.getMessage(7021956244000742652L, null));
        }
    }
    
    public synchronized void processEvent(final IEventObject eventObject) throws RemoteException {
        this.notify();
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
        AdminServerShutdown.m_adminServerSubsystem = AdminServerLog.get();
        AdminServerShutdown.admBundle = AdminServerType.admBundle;
        AdminServerShutdown.cmnBundle = AdminServerType.cmnBundle;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.system;

import com.progress.mf.runtime.ManagementPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventBroker;
import java.rmi.Remote;
import com.progress.chimera.adminserver.ServerPluginInfo;
import com.progress.chimera.adminserver.AdminServer;
import java.rmi.RemoteException;
import com.progress.common.licensemgr.LicenseMgr;
import java.util.Vector;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.osmetrics.OSMetricsImpl;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.mf.IManagedPlugin;
import com.progress.chimera.adminserver.IAdminLicenseCheck;
import com.progress.chimera.adminserver.IServerPlugin;
import com.progress.chimera.common.ChimeraRemoteObject;

public class SystemPlugIn extends ChimeraRemoteObject implements ISystemPlugIn, IServerPlugin, IAdminLicenseCheck, IManagedPlugin
{
    public static final String OS_NAME;
    public static final String NEWLINE;
    public static final String FILE_SEPARATOR;
    protected static IAdministrationServer m_adminServer;
    private static SystemPlugIn m_self;
    private static OSMetricsImpl m_osMetrics;
    public static final String PLUGIN_ID = "osmetrics";
    public static ConnectionManagerLog m_theLog;
    private static SystemPluginComponent m_pluginComponent;
    
    public static SystemPlugIn get() {
        return SystemPlugIn.m_self;
    }
    
    public boolean adminLicenseCheck(final String s, final Vector vector) throws LicenseMgr.NotLicensed, LicenseMgr.CannotContactLicenseMgr, LicenseMgr.NoSuchProduct, LicenseMgr.ProductExpired, LicenseMgr.LicenseError {
        return true;
    }
    
    public SystemPlugIn() throws RemoteException {
        SystemPlugIn.m_self = this;
    }
    
    public void run() {
        this.start();
    }
    
    public boolean isAdminServerStartupComplete() {
        boolean adminServerStartupComplete;
        try {
            adminServerStartupComplete = ((AdminServer)SystemPlugIn.m_adminServer).isAdminServerStartupComplete();
        }
        catch (Exception ex) {
            adminServerStartupComplete = false;
        }
        return adminServerStartupComplete;
    }
    
    public boolean isPluginLicensed(final String anotherString) {
        if (anotherString == null) {
            return false;
        }
        boolean b = false;
        final Vector serverPluginInfo = ((AdminServer)SystemPlugIn.m_adminServer).getServerPluginInfo();
        for (int index = 0; index < serverPluginInfo.size() && !b; ++index) {
            final IServerPlugin pluginInstance = serverPluginInfo.elementAt(index).getPluginInstance();
            if (pluginInstance instanceof IManagedPlugin) {
                final String pluginName = ((IManagedPlugin)pluginInstance).getPluginName();
                if (pluginName != null && pluginName.equalsIgnoreCase(anotherString)) {
                    b = true;
                }
            }
        }
        return b;
    }
    
    public boolean init(final int n, final IAdministrationServer adminServer, final String[] array) {
        boolean b = false;
        SystemPlugIn.m_adminServer = adminServer;
        try {
            SystemPlugIn.m_osMetrics = OSMetricsImpl.getInstance();
            b = (SystemPlugIn.m_osMetrics != null && SystemPlugIn.m_osMetrics.initLibrary());
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            AdminServer.m_adminServerSubsystem.log("SystemPlugIn not supported.");
        }
        return b;
    }
    
    public void shutdown() {
        this.stop();
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        return SystemPlugIn.m_self;
    }
    
    public String getOS() throws RemoteException {
        return SystemPlugIn.m_adminServer.getOS();
    }
    
    public IAdministrationServer getAdminServer() throws RemoteException {
        return SystemPlugIn.m_adminServer;
    }
    
    public synchronized IEventBroker getEventBroker() {
        try {
            return SystemPlugIn.m_adminServer.getEventBroker();
        }
        catch (Throwable t) {
            Excp.print(t);
            return null;
        }
    }
    
    public synchronized IEventStream getEventStream() throws RemoteException {
        try {
            return SystemPlugIn.m_adminServer.getEventStream();
        }
        catch (Throwable t) {
            Excp.print(t);
            return null;
        }
    }
    
    public ISystemPlugIn getPlugIn() throws RemoteException {
        return SystemPlugIn.m_self;
    }
    
    public synchronized ILicenseMgr getLicenseManager() throws RemoteException {
        return ((AdminServer)SystemPlugIn.m_adminServer).getLicenseManager();
    }
    
    public void start() {
    }
    
    public void stop() {
        this.setComponentState(3);
        if (SystemPlugIn.m_osMetrics != null) {
            SystemPlugIn.m_osMetrics.closeLibrary();
            SystemPlugIn.m_osMetrics = null;
        }
        if (SystemPlugIn.m_pluginComponent != null) {
            SystemPlugIn.m_pluginComponent.stop();
        }
    }
    
    public synchronized OSMetricsImpl getOSMetrics() {
        return SystemPlugIn.m_osMetrics;
    }
    
    public void initManagedPlugin(final AbstractPluginComponent abstractPluginComponent) {
        SystemPlugIn.m_pluginComponent = (SystemPluginComponent)abstractPluginComponent;
        this.setComponentState(1);
        SystemPlugIn.m_pluginComponent.start();
        this.setComponentState(2);
    }
    
    public AbstractPluginComponent getPluginComponent() {
        return SystemPlugIn.m_pluginComponent;
    }
    
    public String getComponentClassName() {
        return SystemPluginComponent.class.getName();
    }
    
    public String getPluginName() {
        return "osmetrics";
    }
    
    public void setComponentState(final int n) {
        ManagementPlugin.setComponentAdapterState(n, SystemPlugIn.m_pluginComponent);
    }
    
    static {
        OS_NAME = System.getProperty("os.name");
        NEWLINE = System.getProperty("line.separator");
        FILE_SEPARATOR = System.getProperty("file.separator");
        SystemPlugIn.m_adminServer = null;
        SystemPlugIn.m_self = null;
        SystemPlugIn.m_osMetrics = null;
        SystemPlugIn.m_theLog = ConnectionManagerLog.get();
        SystemPlugIn.m_pluginComponent = null;
    }
}

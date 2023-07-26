// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.runtime;

import com.progress.common.networkevents.EventObject;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import com.progress.chimera.adminserver.ServerPluginInfo;
import com.progress.chimera.adminserver.AdminServer;
import java.util.Enumeration;
import com.progress.common.networkevents.IEventListener;
import com.progress.mf.AbstractPluginContext;
import com.progress.mf.AbstractPluginComponent;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.networkevents.EventBroker;
import java.rmi.Remote;
import com.progress.common.log.ProLog;
import com.progress.common.licensemgr.LicenseMgr;
import java.util.Vector;
import java.rmi.RemoteException;
import java.util.Hashtable;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.message.asMsg;
import com.progress.mf.IManagedPlugin;
import com.progress.chimera.adminserver.IServerPlugin;
import com.progress.chimera.adminserver.IAdminServerConst;
import com.progress.chimera.adminserver.IAdminLicenseCheck;
import com.progress.chimera.common.IChimeraRemoteObject;
import com.progress.chimera.common.ChimeraRemoteObject;

public class ManagementPlugin extends ChimeraRemoteObject implements IChimeraRemoteObject, IAdminLicenseCheck, Runnable, IAdminServerConst, IServerPlugin, IManagedPlugin, asMsg
{
    public static String PLUGIN_ID;
    private static ManagementPlugin self;
    private static boolean localOnlyMode;
    private boolean m_initialized;
    private boolean TRACE_NOTIFICATIONS;
    private int defaultLogLevel;
    protected static IAdministrationServer adminServer;
    protected static PropertyManager properties;
    public static final String MGT_PROP_FILE_TITLE = "Management Plugin Properties File";
    public static final String MGT_PROP_FILE_NAME = "management.properties";
    public static final String MGT_PROP_FILE_PROPERTY = "Management.ConfigFile";
    public static final String PROPERTIES_DIR_NAME = "properties";
    public static final String MGT_ENABLED_PROPERTY = "isMonitored";
    private static ManagementPluginComponent m_pluginComponent;
    private static CatchAllEventsListener caeListener;
    private static Hashtable m_pluginTable;
    
    public static ManagementPlugin get() {
        return ManagementPlugin.self;
    }
    
    public ManagementPlugin() throws RemoteException {
        this.m_initialized = false;
        this.TRACE_NOTIFICATIONS = Boolean.getBoolean("traceFathomNotifications");
        this.defaultLogLevel = (this.TRACE_NOTIFICATIONS ? 3 : 5);
        ManagementPlugin.self = this;
    }
    
    public boolean adminLicenseCheck(final String s, final Vector vector) throws LicenseMgr.NotLicensed, LicenseMgr.CannotContactLicenseMgr, LicenseMgr.NoSuchProduct, LicenseMgr.ProductExpired, LicenseMgr.LicenseError {
        return true;
    }
    
    public boolean init(final int n, final IAdministrationServer adminServer, final String[] array) {
        boolean b = false;
        String s = null;
        ManagementPlugin.adminServer = adminServer;
        try {
            s = adminServer.getMgmtPropFile();
        }
        catch (RemoteException ex2) {}
        if (s == null) {
            s = System.getProperty("Management.ConfigFile");
        }
        if (s == null) {
            s = IAdminServerConst.INSTALL_DIR + IAdminServerConst.FILE_SEPARATOR + array[0].replace('/', IAdminServerConst.FILE_SEPARATOR.charAt(0));
            ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7669630165411962937L, new Object[] { s });
        }
        else {
            ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7669630165411962938L, new Object[] { s });
        }
        try {
            this.load(s);
            b = true;
        }
        catch (Exception ex) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746690L, ex.getMessage());
        }
        return b;
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        return this;
    }
    
    public void shutdown() {
        if (ManagementPlugin.m_pluginComponent != null) {
            ManagementPlugin.m_pluginComponent.stop();
        }
    }
    
    public void run() {
        if (ManagementPlugin.m_pluginComponent != null) {
            ManagementPlugin.m_pluginComponent.start();
        }
    }
    
    private void load(final String s) throws Exception {
        try {
            ManagementPlugin.properties = new ManagementProperties(s, (EventBroker)this.getEventBroker());
        }
        catch (PropertyManager.GroupNameException ex) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746688L, s);
            throw ex;
        }
        catch (PropertyManager.PropertyNameException ex2) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746692L, s);
            throw ex2;
        }
        catch (PropertyManager.PropertyValueException ex3) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746696L, s);
            throw ex3;
        }
        catch (PropertyManager.PropertySyntaxException ex4) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746699L, s);
            throw ex4;
        }
        catch (PropertyManager.LoadIOException ex5) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746700L, s);
            throw ex5;
        }
        catch (PropertyManager.LoadFileNotFoundException ex6) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746702L);
            throw ex6;
        }
    }
    
    public synchronized IEventBroker getEventBroker() {
        try {
            return ManagementPlugin.adminServer.getEventBroker();
        }
        catch (Throwable t) {
            ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746704L, t.getMessage());
            return null;
        }
    }
    
    public void initManagedPlugin(final AbstractPluginComponent abstractPluginComponent) {
        if (!this.m_initialized && abstractPluginComponent != null) {
            ManagementPlugin.m_pluginComponent = (ManagementPluginComponent)abstractPluginComponent;
            if (ManagementPlugin.m_pluginComponent.getContext() instanceof AbstractPluginContext) {
                ManagementPlugin.localOnlyMode = true;
            }
            ManagementPlugin.caeListener = new CatchAllEventsListener();
            try {
                ManagementPlugin.adminServer.getEventBroker().expressInterest(EventObject.class, ManagementPlugin.caeListener, ManagementPlugin.adminServer.getEventStream());
                final ManagementProperties managementProperties = (ManagementProperties)ManagementPlugin.properties;
                final String s = managementProperties.getDirectoryService() ? "Fathom" : "Remote";
                final String s2 = managementProperties.getIsMonitored() ? "Enabled" : "Disabled";
                String domainName = managementProperties.getDomainName();
                String containerName = managementProperties.getContainerName();
                String hostName = managementProperties.getHostName();
                String value = String.valueOf(managementProperties.getPort());
                if (containerName == null) {
                    domainName = (containerName = (hostName = (value = "")));
                }
                ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7021956244000746431L);
                ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7021956244000746432L, s);
                ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7021956244000746433L, s2);
                ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7021956244000746439L, domainName);
                ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7021956244000746434L, containerName);
                ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7021956244000746435L, hostName);
                ProLog.log(ManagementPlugin.PLUGIN_ID, 2, 7021956244000746436L, value);
            }
            catch (RemoteException ex) {
                ProLog.logErr(ManagementPlugin.PLUGIN_ID, 1, 7021956244000746706L, ex.getMessage());
            }
        }
    }
    
    public AbstractPluginComponent getPluginComponent() {
        return ManagementPlugin.m_pluginComponent;
    }
    
    public String getComponentClassName() {
        return ManagementPluginComponent.class.getName();
    }
    
    public String getPluginName() {
        return ManagementPlugin.PLUGIN_ID;
    }
    
    public void setComponentState(final int n) {
        setComponentAdapterState(n, ManagementPlugin.m_pluginComponent);
    }
    
    public static void setComponentAdapterState(final int n, final AbstractPluginComponent abstractPluginComponent) {
        if (abstractPluginComponent != null) {
            switch (n) {
                case 1: {
                    abstractPluginComponent.setStarting();
                    break;
                }
                case 2: {
                    abstractPluginComponent.setOnline();
                    break;
                }
                case 3: {
                    abstractPluginComponent.setStopping();
                    break;
                }
                case 4: {
                    abstractPluginComponent.setOffline();
                    break;
                }
            }
        }
    }
    
    public String toString() {
        return this.getPluginName();
    }
    
    public Object findTargetPlugin(final String s) {
        Object o = null;
        if (s != null) {
            o = ManagementPlugin.m_pluginTable.get(s);
            if (o == null) {
                this.updatePluginTable();
                o = ManagementPlugin.m_pluginTable.get(s);
            }
        }
        return o;
    }
    
    public Object findOEComponent(final String s) {
        Object o = null;
        if (s != null) {
            final Enumeration<String> keys = ManagementPlugin.m_pluginTable.keys();
            while (keys.hasMoreElements()) {
                final String key = keys.nextElement();
                if (key.endsWith(s)) {
                    o = ManagementPlugin.m_pluginTable.get(key);
                    break;
                }
            }
            if (o == null) {
                this.updatePluginTable();
                final Enumeration<String> keys2 = ManagementPlugin.m_pluginTable.keys();
                while (keys2.hasMoreElements()) {
                    final String key2 = keys2.nextElement();
                    if (key2.endsWith(s)) {
                        o = ManagementPlugin.m_pluginTable.get(key2);
                        break;
                    }
                }
            }
        }
        return o;
    }
    
    public void updatePluginTable() {
        final Vector serverPluginInfo = AdminServer.getPluginMgr().getServerPluginInfo();
        if (serverPluginInfo != null) {
            final Enumeration<ServerPluginInfo> elements = serverPluginInfo.elements();
            while (elements.hasMoreElements()) {
                String canonicalName = null;
                AbstractPluginComponent pluginComponent = null;
                final IServerPlugin pluginInstance = elements.nextElement().getPluginInstance();
                if (pluginInstance != null && pluginInstance instanceof IManagedPlugin) {
                    try {
                        pluginComponent = ((IManagedPlugin)pluginInstance).getPluginComponent();
                        canonicalName = pluginComponent.getCanonicalName();
                    }
                    catch (Exception ex) {}
                    if (canonicalName == null || pluginComponent == null) {
                        continue;
                    }
                    ManagementPlugin.m_pluginTable.put(canonicalName, pluginComponent);
                }
            }
        }
    }
    
    static {
        ManagementPlugin.PLUGIN_ID = "ManagementPlugin";
        ManagementPlugin.self = null;
        ManagementPlugin.localOnlyMode = false;
        ManagementPlugin.adminServer = null;
        ManagementPlugin.properties = null;
        ManagementPlugin.m_pluginComponent = null;
        ManagementPlugin.caeListener = null;
        ManagementPlugin.m_pluginTable = new Hashtable();
    }
    
    private class CatchAllEventsListener extends EventListener
    {
        boolean stopped;
        
        private CatchAllEventsListener() {
            this.stopped = false;
        }
        
        public synchronized void processEvent(final IEventObject eventObject) {
            String source = null;
            String notificationType = null;
            try {
                if (eventObject instanceof INotificationEvent) {
                    if (ManagementPlugin.this.TRACE_NOTIFICATIONS) {
                        if (ManagementPlugin.localOnlyMode) {
                            ProLog.logd(ManagementPlugin.PLUGIN_ID, "Generating LOCAL_ONLY notification");
                        }
                        else {
                            ProLog.logd(ManagementPlugin.PLUGIN_ID, "Generating notification");
                        }
                    }
                    source = ((INotificationEvent)eventObject).getSource();
                    notificationType = ((INotificationEvent)eventObject).getNotificationType();
                    if (ManagementPlugin.this.TRACE_NOTIFICATIONS) {
                        ProLog.logd(ManagementPlugin.PLUGIN_ID, "Notification: " + source + " - " + notificationType);
                    }
                    final AbstractPluginComponent abstractPluginComponent = (AbstractPluginComponent)ManagementPlugin.this.findTargetPlugin(source);
                    if (abstractPluginComponent != null) {
                        abstractPluginComponent.sendNotification((INotificationEvent)eventObject);
                    }
                    else {
                        String substring = "NULL";
                        if (source != null) {
                            substring = source.substring(source.lastIndexOf("=") + 1);
                        }
                        ProLog.logErr(ManagementPlugin.PLUGIN_ID, ManagementPlugin.this.defaultLogLevel, 7021956244000746718L, substring, notificationType);
                    }
                }
            }
            catch (Exception ex) {
                ProLog.logErr(ManagementPlugin.PLUGIN_ID, 7021956244000746723L, source, notificationType, ex.getMessage());
            }
        }
    }
}

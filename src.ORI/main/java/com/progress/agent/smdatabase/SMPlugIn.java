// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import com.progress.mf.runtime.ManagementPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.progress.chimera.adminserver.AdminServer;
import com.progress.common.licensemgr.ILicenseMgr;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventBroker;
import java.rmi.Remote;
import com.progress.common.exception.ProException;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.EventBroker;
import com.progress.common.log.ProLog;
import com.progress.chimera.adminserver.IAdminServerConst;
import com.progress.agent.database.AgentPlugIn;
import java.rmi.RemoteException;
import com.progress.chimera.log.ConnectionManagerLog;
import java.util.Vector;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.adminserver.IAdministrationServer;
import com.progress.mf.IManagedPlugin;
import com.progress.chimera.adminserver.IServerPlugin;
import com.progress.common.property.IPropertyProvider;
import com.progress.chimera.common.IChimeraRemoteObject;
import com.progress.chimera.common.ChimeraRemoteObject;

public class SMPlugIn extends ChimeraRemoteObject implements IChimeraRemoteObject, IPropertyProvider, IServerPlugin, IManagedPlugin
{
    protected static IAdministrationServer adminServer;
    protected static PropertyManager properties;
    private static SMPlugIn self;
    private static SMDatabasePluginComponent m_pluginComponent;
    private static Vector m_smDatabases;
    private static boolean m_isInitialized;
    public static final String PLUGIN_ID = "SMDatabase";
    public static final String SMDATABASE_ID = "_SMDatabase_";
    public static final String OS_NAME;
    public static final String NEWLINE;
    public static final String FILE_SEPARATOR;
    public static ConnectionManagerLog theLog;
    static String propertyFileName;
    
    public static SMPlugIn get() {
        return SMPlugIn.self;
    }
    
    public static boolean instantiateNew(final String str, final String s, final String s2) {
        boolean b = true;
        try {
            SMPlugIn.m_smDatabases.addElement(new SMDatabase(str, s, s2));
            final SMProperties smProperties = (SMProperties)SMPlugIn.properties;
            smProperties.putProperty("smdatabase." + str + ".displayName", str);
            smProperties.putProperty("smdatabase." + str + ".databaseName", s2);
            smProperties.putProperty("smdatabase." + str + ".hostName", s);
            smProperties.save();
        }
        catch (Exception ex) {
            b = false;
        }
        return b;
    }
    
    public static String getCanonicalName() {
        return (SMPlugIn.m_pluginComponent != null) ? SMPlugIn.m_pluginComponent.getCanonicalName() : "";
    }
    
    public static String normalizeDatabaseName(String s) {
        s = s.replace('/', SMPlugIn.FILE_SEPARATOR.charAt(0));
        if (s.endsWith(".db")) {
            s = s.substring(0, s.length() - 3);
        }
        return s;
    }
    
    public SMPlugIn() throws RemoteException {
        SMPlugIn.self = this;
    }
    
    public PropertyManager properties() {
        return SMPlugIn.properties;
    }
    
    public Vector getSMDatabases() {
        return SMPlugIn.m_smDatabases;
    }
    
    public boolean isInitialized() {
        return SMPlugIn.m_isInitialized;
    }
    
    public boolean init(final int n, final IAdministrationServer adminServer, final String[] array) {
        try {
            SMPlugIn.adminServer = adminServer;
            if (AgentPlugIn.get() == null || AgentPlugIn.propertiesS() == null) {
                SMPlugIn.theLog.logErr(2, "Warning: DatabaseAgent plugin must be loaded in order to successfully run the SMDatabase plugin.");
            }
            try {
                SMPlugIn.propertyFileName = adminServer.getSmdbPropFile();
            }
            catch (RemoteException ex10) {}
            if (SMPlugIn.propertyFileName == null) {
                SMPlugIn.propertyFileName = System.getProperty("smdatabase.propertyfile");
            }
            if (SMPlugIn.propertyFileName == null) {
                SMPlugIn.propertyFileName = IAdminServerConst.INSTALL_DIR + IAdminServerConst.FILE_SEPARATOR + array[0].replace('/', IAdminServerConst.FILE_SEPARATOR.charAt(0));
                ProLog.log("SMDatabase", 2, 7669630165411962937L, new Object[] { SMPlugIn.propertyFileName });
            }
            else {
                ProLog.log("SMDatabase", 2, 7669630165411962938L, new Object[] { SMPlugIn.propertyFileName });
            }
            SMPlugIn.properties = new SMProperties(SMPlugIn.propertyFileName, (EventBroker)this.getEventBroker());
            String[] groups = SMPlugIn.properties.groups("SMDatabase", true, false);
            if (groups == null) {
                groups = new String[0];
            }
            for (int i = 0; i < groups.length; ++i) {
                final String trim = groups[i].trim();
                final String property = SMPlugIn.properties.getProperty("SMDatabase." + trim + ".displayname");
                final String property2 = SMPlugIn.properties.getProperty("SMDatabase." + trim + ".databasename");
                final String property3 = SMPlugIn.properties.getProperty("SMDatabase." + trim + ".host");
                if (property != null && property.trim().length() > 0) {
                    try {
                        SMPlugIn.m_smDatabases.addElement(new SMDatabase(property, property3, property2));
                    }
                    catch (RemoteException ex) {
                        SMPlugIn.theLog.logErr(3, "Can't instantiate script managed database object, " + property + ": " + ex.getMessage());
                    }
                }
            }
            SMPlugIn.m_isInitialized = true;
        }
        catch (PropertyManager.GroupNameException ex2) {
            Tools.px(ex2, "Property file contains invalid group names.");
        }
        catch (PropertyManager.PropertyNameException ex3) {
            Tools.px(ex3, "Property file contains invalid property names.");
        }
        catch (PropertyManager.PropertyValueException ex4) {
            Tools.px(ex4, "Property file contains invalid property values.");
        }
        catch (PropertyManager.PropertySyntaxException ex5) {
            Tools.px(ex5, "Property file is malformed.");
        }
        catch (PropertyManager.LoadIOException ex6) {
            Tools.px(ex6, "Property file failed to load.");
        }
        catch (PropertyManager.PropertyVersionException ex7) {
            Tools.px(ex7, "Incorrect property file version.");
        }
        catch (PropertyManager.LoadFileNotFoundException ex11) {
            try {
                SMPlugIn.properties = new SMProperties(null, (EventBroker)this.getEventBroker());
                ((SMProperties)SMPlugIn.properties).generatePropFile(SMPlugIn.propertyFileName);
                SMPlugIn.m_isInitialized = true;
            }
            catch (ProException ex8) {
                Tools.px(ex8, "Could not create file: smdatabase.properties.");
            }
        }
        catch (PropertyManager.PropertyException ex9) {
            Tools.px(ex9, ex9.getMessage());
        }
        return SMPlugIn.m_isInitialized;
    }
    
    public void shutdown() {
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        return this;
    }
    
    public IAdministrationServer getAdminServer() throws RemoteException {
        return SMPlugIn.adminServer;
    }
    
    public synchronized IEventBroker getEventBroker() {
        try {
            return SMPlugIn.adminServer.getEventBroker();
        }
        catch (Throwable t) {
            Excp.print(t);
            return null;
        }
    }
    
    public synchronized IEventStream getEventStream() throws RemoteException {
        try {
            return SMPlugIn.adminServer.getEventStream();
        }
        catch (Throwable t) {
            Excp.print(t);
            return null;
        }
    }
    
    public String getPropertyFileName() {
        return SMPlugIn.propertyFileName;
    }
    
    public synchronized ILicenseMgr getLicenseManager() throws RemoteException {
        return ((AdminServer)SMPlugIn.adminServer).getLicenseManager();
    }
    
    public PropertyManager getPlugInPropertyManager() {
        return this.properties();
    }
    
    public void initManagedPlugin(final AbstractPluginComponent abstractPluginComponent) {
        SMPlugIn.m_pluginComponent = (SMDatabasePluginComponent)abstractPluginComponent;
        this.setComponentState(1);
        SMPlugIn.m_pluginComponent.start();
        this.setComponentState(2);
    }
    
    public AbstractPluginComponent getPluginComponent() {
        return SMPlugIn.m_pluginComponent;
    }
    
    public String getComponentClassName() {
        return SMDatabasePluginComponent.class.getName();
    }
    
    public String getPluginName() {
        return "SMDatabase";
    }
    
    public void setComponentState(final int n) {
        ManagementPlugin.setComponentAdapterState(n, SMPlugIn.m_pluginComponent);
    }
    
    static {
        SMPlugIn.adminServer = null;
        SMPlugIn.properties = null;
        SMPlugIn.self = null;
        SMPlugIn.m_pluginComponent = null;
        SMPlugIn.m_smDatabases = new Vector();
        SMPlugIn.m_isInitialized = false;
        OS_NAME = System.getProperty("os.name");
        NEWLINE = System.getProperty("line.separator");
        FILE_SEPARATOR = System.getProperty("file.separator");
        SMPlugIn.theLog = ConnectionManagerLog.get();
        SMPlugIn.propertyFileName = null;
    }
}

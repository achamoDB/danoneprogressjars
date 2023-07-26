// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.lang.reflect.Method;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import com.progress.common.util.Port;
import java.util.Vector;
import com.progress.common.log.ProLog;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.progress.agent.database.AgentProperties;
import com.progress.chimera.adminserver.IServerPlugin;
import com.progress.common.property.PropertyManager;
import com.progress.agent.database.AgentPlugIn;
import java.rmi.RemoteException;
import com.sonicsw.mf.common.info.IManagementInfo;

public class JAPluginComponent extends ReplicationPluginComponent implements IJAComponentConstants
{
    protected static final IManagementInfo[] DB_MANAGEMENT_INFO;
    
    public IManagementInfo[] getManagementInfo() {
        return JAPluginComponent.DB_MANAGEMENT_INFO;
    }
    
    public String getAdminServerPort() {
        String port;
        try {
            port = JAPlugIn.get().getAdminServer().getPort();
        }
        catch (RemoteException ex) {
            port = "0";
        }
        return port;
    }
    
    public String getAgentPort() {
        String agentPort = "0";
        try {
            if (super.m_agentIsLicensed) {
                agentPort = AgentPlugIn.get().getAgentPort();
            }
        }
        catch (Exception ex) {
            agentPort = "Unknown";
        }
        return agentPort;
    }
    
    public void setDatabaseProperties(final PropertyManager databaseProperties) {
        super.m_databaseProperties = databaseProperties;
    }
    
    public PropertyManager getDatabaseProperties() {
        return super.m_databaseProperties;
    }
    
    public void setDatabasePlugIn(final IServerPlugin databasePlugIn) {
        super.m_databasePlugIn = databasePlugIn;
    }
    
    public IServerPlugin getDatabasePlugIn() {
        return super.m_databasePlugIn;
    }
    
    public AgentProperties setAgentProperties() {
        try {
            if (super.m_agentIsLicensed) {
                super.m_agentProperties = (AgentProperties)AgentPlugIn.propertiesS();
            }
        }
        catch (Exception ex) {
            super.m_agentProperties = null;
        }
        return super.m_agentProperties;
    }
    
    public String setAgentPropertyFileName() {
        try {
            if (super.m_agentIsLicensed) {
                final AgentProperties agentProperties = super.m_agentProperties;
                super.m_agentPropertyFileName = AgentProperties.getPropertyFileName();
            }
        }
        catch (Exception ex) {
            super.m_agentPropertyFileName = null;
        }
        return super.m_agentPropertyFileName;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        super.init(o, frameworkComponentContext);
    }
    
    public void postLoadInit() {
        super.m_agentIsLicensed = true;
        this.setDatabasePlugIn((IServerPlugin)super.m_pluginObject);
        JAPlugIn.get();
        this.setDatabaseProperties(JAPlugIn.propertiesS());
        super.m_agentProperties = this.setAgentProperties();
        super.m_agentPropertyFileName = this.setAgentPropertyFileName();
        super.m_replicationIsLicensed = JAPlugIn.get().isReplLicensed();
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "datatabase.";
        AbstractPluginComponent.m_metricSearchPrefix = "database.metric.vst.";
    }
    
    public void start(final String s) {
        this.start(s, null, null);
    }
    
    public void start(final String s, final String s2, final String s3) {
        try {
            final IJAExecutableObject executableObject = this.getExecutableObject(s, s2, s3);
            if (executableObject != null) {
                executableObject.start();
            }
            else {
                ProLog.logd(super.PLUGIN_ID, 4, "start failed: database name cannot be null.");
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "start failed: " + ex.getMessage());
        }
    }
    
    public void stop(final String s) {
        this.stop(s, null, null);
    }
    
    public void stop(final String s, final String s2) {
        this.stop(s, s2, null);
    }
    
    public void stop(final String s, final String s2, final String s3) {
        final StringBuffer sb = new StringBuffer();
        try {
            final IJAExecutableObject executableObject = this.getExecutableObject(s, s2, s3);
            if (executableObject != null) {
                executableObject.stop();
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "stop failed: " + ex.getMessage());
        }
    }
    
    public void destroy(final String s) {
        this.delete(s, null, null);
    }
    
    public void delete(final String s, final String s2, final String s3) {
        try {
            final IJAExecutableObject executableObject = this.getExecutableObject(s, s2, s3);
            if (executableObject != null) {
                executableObject.delete(null);
            }
            else {
                ProLog.logd(super.PLUGIN_ID, 4, "delete failed: database name cannot be null.");
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "delete failed: " + ex.getMessage());
        }
    }
    
    public Boolean nameUsed(final String s) {
        boolean nameUsed;
        try {
            nameUsed = JADatabase.nameUsed(s);
        }
        catch (Exception ex) {
            nameUsed = false;
        }
        return new Boolean(nameUsed);
    }
    
    public Boolean configurationCreateNew(final String s, final String s2) {
        boolean value;
        try {
            JAConfiguration.instantiateNew(JAPlugIn.get(), JADatabase.findDatabase(s), s2, null);
            value = true;
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public Boolean serverGroupCreateNew(final String s, final String s2) {
        boolean value;
        try {
            JAService.instantiateNew(JAPlugIn.get(), JAConfiguration.findConfiguration(s), s2, null);
            value = true;
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public Boolean configurationNameUsed(final String s, final String s2) {
        boolean nameUsed;
        try {
            nameUsed = JAConfiguration.nameUsed(JADatabase.findDatabase(s), s2);
        }
        catch (Exception ex) {
            nameUsed = false;
        }
        return new Boolean(nameUsed);
    }
    
    public Boolean serverGroupNameUsed(final String s, final String s2) {
        boolean nameUsed;
        try {
            nameUsed = JAService.nameUsed(JAConfiguration.findConfiguration(s), s2);
        }
        catch (Exception ex) {
            nameUsed = false;
        }
        return new Boolean(nameUsed);
    }
    
    public String configurationMakeNewName(final String s) {
        String newName;
        try {
            newName = JAConfiguration.makeNewName(JADatabase.findDatabase(s));
        }
        catch (Exception ex) {
            newName = "";
        }
        return newName;
    }
    
    public String serverGroupMakeNewName(final String s) {
        String newName;
        try {
            newName = JAService.makeNewName(JAConfiguration.findConfiguration(s));
        }
        catch (Exception ex) {
            newName = "";
        }
        return newName;
    }
    
    public void setUser(final String s, final String user) {
        try {
            final JADatabase database = JADatabase.findDatabase(s);
            if (database != null) {
                database.setUser(user);
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "setUser failed: " + ex.getMessage());
        }
    }
    
    public String getUser(final String s) {
        String user = null;
        try {
            final JADatabase database = JADatabase.findDatabase(s);
            if (database != null) {
                user = database.getUser();
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getUser failed: " + ex.getMessage());
        }
        return user;
    }
    
    public Character getInvalidChar(final String s) {
        Character invalidChar;
        try {
            invalidChar = JAPlugIn.getInvalidChar(s);
        }
        catch (Exception ex) {
            invalidChar = null;
        }
        return invalidChar;
    }
    
    public String makeNewChildName() {
        String newName;
        try {
            newName = JADatabase.makeNewName();
        }
        catch (Exception ex) {
            newName = null;
        }
        return newName;
    }
    
    public Boolean createDatabase(final String[] array) throws Exception {
        String s = null;
        String s2 = null;
        String s3 = null;
        boolean booleanValue = false;
        boolean value = false;
        if (array != null) {
            if (array.length >= 3) {
                s = array[0];
                s2 = array[1];
                new Boolean(array[2]);
            }
            if (array.length >= 4) {
                s3 = ((array[3].length() == 0) ? null : array[3]);
            }
            if (array.length >= 5) {
                final String s4 = (array[4].length() == 0) ? null : array[4];
            }
            if (array.length >= 6) {
                booleanValue = new Boolean(array[5]);
            }
        }
        final boolean b = !booleanValue;
        final JAPlugIn value2 = JAPlugIn.get();
        JADatabase jaDatabase = null;
        synchronized (value2) {
            try {
                jaDatabase = JADatabase.findDatabase(s);
                if (jaDatabase == null) {
                    jaDatabase = JADatabase.instantiateNew(JAPlugIn.get(), s, s2, false, null);
                }
                final JuniperProperties juniperProperties = (JuniperProperties)JAPlugIn.propertiesS();
                if (juniperProperties != null && s3 != null && s3.length() > 0) {
                    juniperProperties.processLegacyArguments(s3, s, true, b);
                    JATools.writeOutProperties(value2);
                }
                value = true;
            }
            catch (Exception ex) {
                if (booleanValue) {
                    throw ex;
                }
                if (jaDatabase != null) {
                    jaDatabase.delete(this);
                }
            }
        }
        return new Boolean(value);
    }
    
    public String getCurrentConfiguration(final String s) {
        String displayName = null;
        try {
            final IJAConfiguration currentConfiguration = JADatabase.findDatabase(s).getCurrentConfiguration();
            if (currentConfiguration != null) {
                displayName = currentConfiguration.getDisplayName();
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getCurrentConfiguration: failed: " + ex.getMessage());
        }
        return displayName;
    }
    
    private IJAExecutableObject getExecutableObject(final String s, final String s2, final String str) throws Exception {
        IJAExecutableObject ijaExecutableObject = null;
        final StringBuffer sb = new StringBuffer();
        if (s != null && s2 == null && str == null) {
            ijaExecutableObject = JADatabase.findDatabase(s);
        }
        else if (s != null && s2 != null && str == null) {
            sb.append(s);
            sb.append(".");
            sb.append(s2);
            ijaExecutableObject = JAConfiguration.findConfiguration(sb.toString());
        }
        else if (s != null && s2 != null && str != null) {
            sb.append(s);
            sb.append(".");
            sb.append(s2);
            sb.append(".");
            sb.append(str);
            ijaExecutableObject = JAService.findService(sb.toString());
        }
        return ijaExecutableObject;
    }
    
    private IJAExecutableObject getExecutableObject(final String s, final int n) throws NumberFormatException, RemoteException {
        IJAExecutableObject ijaExecutableObject = null;
        final JADatabase database = JADatabase.findDatabase(s);
        IJAConfiguration ijaConfiguration = database.getCurrentConfiguration();
        if (ijaConfiguration == null) {
            ijaConfiguration = database.getDefaultConfiguration();
        }
        switch (n) {
            case 1: {
                ijaExecutableObject = database;
                break;
            }
            case 3: {
                ijaExecutableObject = database.getAgent();
                break;
            }
            case 4: {
                ijaExecutableObject = ijaConfiguration.getAIWriter();
                break;
            }
            case 5: {
                ijaExecutableObject = ijaConfiguration.getBIWriter();
                break;
            }
            case 6: {
                ijaExecutableObject = ijaConfiguration.getAPWriter();
                break;
            }
            case 7: {
                ijaExecutableObject = ijaConfiguration.getWatchdog();
                break;
            }
        }
        return ijaExecutableObject;
    }
    
    public Boolean getState(final String s, final Integer n, final Integer n2) {
        boolean value = false;
        try {
            final int intValue = n;
            final int intValue2 = n2;
            final IJAExecutableObject executableObject = this.getExecutableObject(s, intValue);
            switch (intValue2) {
                case 1: {
                    value = executableObject.isIdle();
                    break;
                }
                case 2: {
                    value = executableObject.isStarting();
                    break;
                }
                case 3: {
                    value = executableObject.isInitializing();
                    break;
                }
                case 4: {
                    value = executableObject.isRunning();
                    break;
                }
                case 5: {
                    value = executableObject.isShuttingDown();
                    break;
                }
                case 6: {
                    value = executableObject.isStartable();
                    break;
                }
                case 7: {
                    value = executableObject.isStopable();
                    break;
                }
                case 8: {
                    value = executableObject.isDeleteable();
                    break;
                }
            }
        }
        catch (NumberFormatException ex2) {
            ProLog.logd(super.PLUGIN_ID, 4, "getState: unknown database type: " + n.toString());
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getState: failed: " + ex.getMessage());
        }
        return new Boolean(value);
    }
    
    public String getStateDescriptor(final String s, final Integer n) {
        String stateDescriptor = "Unknown";
        try {
            stateDescriptor = this.getExecutableObject(s, n).getStateDescriptor();
        }
        catch (NumberFormatException ex2) {
            ProLog.logd(super.PLUGIN_ID, 4, "getStateDescriptor: unknown database type: " + n.toString());
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getStateDescriptor: failed: " + ex.getMessage());
        }
        return stateDescriptor;
    }
    
    public String[] getInstanceNames() {
        if (!(this.getDatabasePlugIn() instanceof JAPlugIn)) {
            return new String[0];
        }
        final Vector<String> vector = new Vector<String>();
        try {
            final Vector databasesVector = JAPlugIn.get().getDatabasesVector();
            for (int i = 0; i < databasesVector.size(); ++i) {
                vector.addElement(databasesVector.elementAt(i).getDisplayName());
            }
        }
        catch (Throwable t) {
            ProLog.logd(super.PLUGIN_ID, 4, "getInstanceNames failed: " + t.getMessage());
        }
        return vector.toArray(new String[0]);
    }
    
    public Boolean databaseExists(final String s) {
        boolean value = false;
        try {
            if (JADatabase.findDatabase(s) != null) {
                value = true;
            }
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public Boolean agentExists(final String s) {
        boolean value = false;
        try {
            final JADatabase database = JADatabase.findDatabase(s);
            if (database != null && database.getAgent() != null) {
                value = true;
            }
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public Boolean configurationExists(final String s) {
        boolean value = false;
        try {
            if (JAConfiguration.findConfiguration(s) != null) {
                value = true;
            }
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public Boolean serverGroupExists(final String s) {
        boolean value = false;
        try {
            if (JAService.findService(s) != null) {
                value = true;
            }
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public String getAbsoluteDatabaseFileName(final String str) {
        String s = null;
        try {
            s = super.m_databaseProperties.getProperty("Database." + str + ".databaseName");
            if (s != null) {
                if (s.endsWith(".db")) {
                    s = s.substring(0, s.lastIndexOf(46));
                }
                if (s.indexOf(DatabasePluginComponent.FILE_SEPARATOR) < 0) {
                    final String property = System.getProperty("Work.Dir");
                    if (property == null) {
                        final String property2 = System.getProperty("user.dir");
                        if (property2 != null) {
                            s = property2 + DatabasePluginComponent.FILE_SEPARATOR + s;
                        }
                    }
                    else {
                        s = property + DatabasePluginComponent.FILE_SEPARATOR + s;
                    }
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getAbsoluteDatabaseFileName failed: " + ex.getMessage());
        }
        return s;
    }
    
    public Integer convertServiceNameToPortNumber(final String s) {
        int portInt = 12345;
        try {
            if (s != null) {
                portInt = new Port(s).getPortInt();
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "Problems converting database port: " + ex.getMessage());
        }
        return new Integer(portInt);
    }
    
    public Boolean isPortInUse(final String s) {
        boolean inUse = false;
        try {
            if (s != null) {
                inUse = new Port(s).isInUse();
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "Problems checking database port in use: " + ex.getMessage());
        }
        return new Boolean(inUse);
    }
    
    public Boolean writeProperties() {
        boolean value = false;
        try {
            if (super.m_databaseProperties instanceof JuniperProperties) {
                ((JuniperProperties)super.m_databaseProperties).save();
                value = true;
            }
        }
        catch (PropertyManager.SaveIOException ex) {
            value = false;
            ProLog.logd(super.PLUGIN_ID, 4, ex.getMessage());
        }
        return new Boolean(value);
    }
    
    public void startAgent(final String s) {
        try {
            if (s != null) {
                final IAgentDatabaseHandle agent = JADatabase.findDatabase(s).getAgent();
                if (agent != null) {
                    agent.start();
                }
                else {
                    ProLog.logd(super.PLUGIN_ID, 4, "start failed: database agent name cannot be null.");
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "start failed: " + ex.getMessage());
        }
    }
    
    public void stopAgent(final String s) {
        try {
            if (s != null) {
                final IAgentDatabaseHandle agent = JADatabase.findDatabase(s).getAgent();
                if (agent != null) {
                    agent.stop();
                }
                else {
                    ProLog.logd(super.PLUGIN_ID, 4, "stop failed: database agent name cannot be null.");
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "stop failed: " + ex.getMessage());
        }
    }
    
    public void deleteAgent(final String s) {
        try {
            if (s != null) {
                final IAgentDatabaseHandle agent = JADatabase.findDatabase(s).getAgent();
                if (agent != null) {
                    agent.delete(null);
                }
                else {
                    ProLog.logd(super.PLUGIN_ID, 4, "delete failed: database agent name cannot be null.");
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "delete failed: " + ex.getMessage());
        }
    }
    
    static {
        DB_MANAGEMENT_INFO = new IManagementInfo[ReplicationPluginComponent.REPL_MANAGEMENT_INFO.length + 54];
        int length = ReplicationPluginComponent.REPL_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(ReplicationPluginComponent.REPL_MANAGEMENT_INFO, 0, JAPluginComponent.DB_MANAGEMENT_INFO, 0, ReplicationPluginComponent.REPL_MANAGEMENT_INFO.length);
        try {
            method = JAPluginComponent.class.getMethod("destroy", String.class);
        }
        catch (Exception ex) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the specified database instance.", method);
        try {
            method = JAPluginComponent.class.getMethod("delete", String.class, String.class, String.class);
        }
        catch (Exception ex2) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the specified database instance.", method);
        try {
            method = JAPluginComponent.class.getMethod("start", String.class, String.class, String.class);
        }
        catch (Exception ex3) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Start the specified database instance.", method);
        try {
            method = JAPluginComponent.class.getMethod("stop", String.class, String.class, String.class);
        }
        catch (Exception ex4) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Stop the specified database instance.", method);
        try {
            method = JAPluginComponent.class.getMethod("writeProperties", (Class[])new Class[0]);
        }
        catch (Exception ex5) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Write the database properties.", method);
        try {
            method = JAPluginComponent.class.getMethod("createDatabase", String[].class);
        }
        catch (Exception ex6) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new instance of the local database.", method);
        try {
            method = JAPluginComponent.class.getMethod("setUser", String.class, String.class);
        }
        catch (Exception ex7) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set the name of the user who is operating on this database.", method);
        try {
            method = JAPluginComponent.class.getMethod("getUser", String.class);
        }
        catch (Exception ex8) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the name of the user who is operating on this database.", method);
        try {
            method = JAPluginComponent.class.getMethod("deleteAgent", String.class);
        }
        catch (Exception ex9) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the specified database agent instance.", method);
        try {
            method = JAPluginComponent.class.getMethod("startAgent", String.class);
        }
        catch (Exception ex10) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Start the specified database agent instance.", method);
        try {
            method = JAPluginComponent.class.getMethod("stopAgent", String.class);
        }
        catch (Exception ex11) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Stop the specified database agent instance.", method);
        try {
            method = JAPluginComponent.class.getMethod("getState", String.class, Integer.class, Integer.class);
        }
        catch (Exception ex12) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get state of the specified database instance type.", method);
        try {
            method = JAPluginComponent.class.getMethod("getStateDescriptor", String.class, Integer.class);
        }
        catch (Exception ex13) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the state description of the specified database instance type.", method);
        try {
            method = JAPluginComponent.class.getMethod("getCurrentConfiguration", String.class);
        }
        catch (Exception ex14) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the running or default configuration associated with the named database.", method);
        try {
            method = JAPluginComponent.class.getMethod("getAbsoluteDatabaseFileName", String.class);
        }
        catch (Exception ex15) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return absolute file path associated with the named database.", method);
        try {
            method = JAPluginComponent.class.getMethod("convertServiceNameToPortNumber", String.class);
        }
        catch (Exception ex16) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Converts a service name to its integer value.", method);
        try {
            method = JAPluginComponent.class.getMethod("isPortInUse", String.class);
        }
        catch (Exception ex17) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Checks whether or not the service name or port is in use.", method);
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("InstanceNames", String[].class.getName(), "Instances", true, false);
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("AdminServerPort", String.class.getName(), "Port that AdminServer is running on", true, false);
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("AgentPort", String.class.getName(), "Port that agent is listening on", true, false);
        try {
            method = JAPluginComponent.class.getMethod("databaseExists", String.class);
        }
        catch (Exception ex18) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database exist?", method);
        try {
            method = JAPluginComponent.class.getMethod("agentExists", String.class);
        }
        catch (Exception ex19) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database agent exist?", method);
        try {
            method = JAPluginComponent.class.getMethod("configurationExists", String.class);
        }
        catch (Exception ex20) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database configuration exist?", method);
        try {
            method = JAPluginComponent.class.getMethod("serverGroupExists", String.class);
        }
        catch (Exception ex21) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database server group exist?", method);
        try {
            method = JAPluginComponent.class.getMethod("nameUsed", String.class);
        }
        catch (Exception ex22) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the named database display name available?", method);
        try {
            method = JAPluginComponent.class.getMethod("configurationCreateNew", String.class, String.class);
        }
        catch (Exception ex23) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new configuration group with the given display name.", method);
        try {
            method = JAPluginComponent.class.getMethod("serverGroupCreateNew", String.class, String.class);
        }
        catch (Exception ex24) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new server group with the given display name.", method);
        try {
            method = JAPluginComponent.class.getMethod("configurationNameUsed", String.class, String.class);
        }
        catch (Exception ex25) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the named configuration display name available?", method);
        try {
            method = JAPluginComponent.class.getMethod("serverGroupNameUsed", String.class, String.class);
        }
        catch (Exception ex26) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the named server group display name available?", method);
        try {
            method = JAPluginComponent.class.getMethod("getInvalidChar", String.class);
        }
        catch (Exception ex27) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the first invalid character found in the named database display name.", method);
        try {
            method = JAPluginComponent.class.getMethod("makeNewChildName", (Class[])new Class[0]);
        }
        catch (Exception ex28) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the next default database display name available.", method);
        try {
            method = JAPluginComponent.class.getMethod("configurationMakeNewName", String.class);
        }
        catch (Exception ex29) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Propose a new configuration display name for the named database group.", method);
        try {
            method = JAPluginComponent.class.getMethod("serverGroupMakeNewName", String.class);
        }
        catch (Exception ex30) {}
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Propose a new server group display name for the named configuration group", method);
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAutostartCompleted", "All database autostarts are completed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EStartupProcessCompleted", "The database process startup is complete.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ContainerDeleteAddNodeEvent", "A new database has been added.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ContainerDeleteTreeNodeEvent", "A database has been deleted.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EDatabaseNameChanged", "The database name has been changed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EConfigurationStarted", "The database configuration has been started.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EConfigurationAdded", "The database configuration has been added.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EConfigurationDeleted", "The database configuration has been deleted.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EConfigurationRemoved", "The database configuration has been removed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EConfigurationStateChanged", "The database configuration state has changed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EConfigurationCrashEvent", "The database configuration has crashed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EServerGroupStarted", "The database server group has been started.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EServerGroupAdded", "The database server group has been added.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EServerGroupDeleted", "The database server group has been deleted.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EServerGroupRemoved", "The database server group has been removed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EServerGroupStateChanged", "The database server group state has changed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EServerGroupCrashEvent", "The database server group has crashed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAuxiliaryStartupEvent", "An auxiliary (ai, bi, apw, or watchdog) process been started.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAuxiliaryStartupFailed", "The auxiliary (ai, bi, apw, or watchdog) process startup has failed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAuxiliaryStateChanged", "The auxiliary (ai, bi, apw, or watchdog) process state has changed.");
        JAPluginComponent.DB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAuxiliaryCrashEvent", "The auxiliary (ai, bi, apw, or watchdog) process has crashed.");
    }
}

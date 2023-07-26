// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.smdatabase;

import java.lang.reflect.Method;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import java.io.File;
import java.util.Hashtable;
import java.util.Vector;
import com.progress.agent.database.AgentDatabase;
import com.progress.common.log.ProLog;
import java.rmi.RemoteException;
import com.progress.chimera.adminserver.IServerPlugin;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.progress.agent.database.AgentPlugIn;
import com.progress.agent.database.AgentProperties;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.juniper.admin.DatabasePluginComponent;

public class SMDatabasePluginComponent extends DatabasePluginComponent
{
    private static final IManagementInfo[] SM_MANAGEMENT_INFO;
    
    public AgentProperties setAgentProperties() {
        try {
            super.m_agentProperties = (AgentProperties)AgentPlugIn.propertiesS();
        }
        catch (Exception ex) {
            super.m_agentProperties = null;
        }
        return super.m_agentProperties;
    }
    
    public String setAgentPropertyFileName() {
        try {
            super.m_agentPropertyFileName = AgentProperties.getPropertyFileName();
        }
        catch (Exception ex) {
            super.m_agentPropertyFileName = null;
        }
        return super.m_agentPropertyFileName;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        super.init(o, frameworkComponentContext);
        super.setDatabasePlugIn((IServerPlugin)o);
        super.setDatabaseProperties(((SMPlugIn)o).properties());
        super.m_agentProperties = this.setAgentProperties();
        super.m_agentPropertyFileName = this.setAgentPropertyFileName();
        super.PLUGIN_ID = "SMDatabase";
    }
    
    public IManagementInfo[] getManagementInfo() {
        return SMDatabasePluginComponent.SM_MANAGEMENT_INFO;
    }
    
    public String getAgentPort() {
        String agentPort;
        try {
            agentPort = AgentPlugIn.get().getAgentPort();
        }
        catch (Exception ex) {
            agentPort = "Unknown";
        }
        return agentPort;
    }
    
    public String getAdminServerPort() {
        String port;
        try {
            port = AgentPlugIn.get().getAdminServer().getPort();
        }
        catch (RemoteException ex) {
            port = "0";
        }
        return port;
    }
    
    public Boolean disconnectUser(final String str, final Integer n) {
        Boolean disconnectUser = new Boolean(false);
        try {
            disconnectUser = super.disconnectUser("_SMDatabase_" + str, n);
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 3, "Uable to disconnect user: " + ex.getMessage());
        }
        return (disconnectUser == null) ? new Boolean(false) : disconnectUser;
    }
    
    public Boolean getState(final String s, final Integer n, final Integer n2) {
        boolean value = false;
        try {
            final int intValue = n;
            final int intValue2 = n2;
            final SMDatabase smDatabase = SMDatabase.findSMDatabase(s);
            final AgentDatabase databaseAgent = smDatabase.getDatabaseAgent();
            switch (intValue2) {
                case 1: {
                    if (intValue == 3) {
                        value = databaseAgent.isIdle();
                        break;
                    }
                    value = smDatabase.isIdle();
                    break;
                }
                case 2: {
                    value = databaseAgent.isStarting();
                    break;
                }
                case 3: {
                    value = databaseAgent.isInitializing();
                    break;
                }
                case 4: {
                    if (intValue == 3) {
                        value = databaseAgent.isRunning();
                        break;
                    }
                    value = smDatabase.isRunning();
                    break;
                }
                case 5: {
                    value = databaseAgent.isShuttingDown();
                    break;
                }
                case 6: {
                    value = databaseAgent.isStartable();
                    break;
                }
                case 7: {
                    value = databaseAgent.isStopable();
                    break;
                }
                case 8: {
                    value = databaseAgent.isDeleteable();
                    break;
                }
            }
        }
        catch (NumberFormatException ex2) {
            ProLog.logd(super.PLUGIN_ID, 4, "getState: unknown script managed database type: " + n.toString());
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getState: failed: " + ex.getMessage());
        }
        return new Boolean(value);
    }
    
    public String getStateDescriptor(final String s, final Integer n) {
        String s2 = "Unknown";
        try {
            final int intValue = n;
            final SMDatabase smDatabase = SMDatabase.findSMDatabase(s);
            final AgentDatabase databaseAgent = smDatabase.getDatabaseAgent();
            switch (intValue) {
                case 2: {
                    s2 = smDatabase.getLastKnownStatus();
                    break;
                }
                case 4: {
                    s2 = smDatabase.getAuxProcessStatus(1);
                    break;
                }
                case 6: {
                    s2 = smDatabase.getAuxProcessStatus(3);
                    break;
                }
                case 5: {
                    s2 = smDatabase.getAuxProcessStatus(2);
                    break;
                }
                case 7: {
                    s2 = smDatabase.getAuxProcessStatus(4);
                    break;
                }
                case 3: {
                    s2 = databaseAgent.getStateDescriptor();
                    break;
                }
            }
        }
        catch (NumberFormatException ex2) {
            ProLog.logd(super.PLUGIN_ID, 4, "getStateDescriptor: unknown script managed database type: " + n.toString());
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getStateDescriptor: failed: " + ex.getMessage());
        }
        return s2;
    }
    
    public String[] getInstanceNames() {
        if (!(this.getDatabasePlugIn() instanceof SMPlugIn)) {}
        final Vector<String> vector = new Vector<String>();
        try {
            final Vector smDatabases = SMPlugIn.get().getSMDatabases();
            for (int i = 0; i < smDatabases.size(); ++i) {
                vector.addElement(smDatabases.elementAt(i).getDisplayName());
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getInstanceNames failed: " + ex.getMessage());
        }
        return vector.toArray(new String[0]);
    }
    
    public Boolean databaseExists(final String s) {
        boolean value = false;
        try {
            if (SMDatabase.findSMDatabase(s) != null) {
                value = true;
            }
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public Boolean agentExists(final String str) {
        boolean value = false;
        try {
            if (AgentDatabase.findAgentDatabase("_SMDatabase_" + str) != null) {
                value = true;
            }
        }
        catch (Exception ex) {
            ProLog.logd("SMDatabase", 4, "agentExists failed: " + ex.getMessage());
        }
        return new Boolean(value);
    }
    
    public void start(final String s) {
    }
    
    public void stop(final String s) {
    }
    
    public void destroy(final String s) {
        this.delete(s, null, null);
    }
    
    public void delete(final String s, final String s2, final String s3) {
        try {
            SMDatabase.findSMDatabase(s).delete(true, null);
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getBrokerPort failed: " + ex.getMessage());
        }
    }
    
    public void startAgent(final String s) {
    }
    
    public void stopAgent(final String str) {
        try {
            if (str != null) {
                AgentDatabase.findAgentDatabase("_SMDatabase_" + str).stop();
            }
            else {
                ProLog.logd(super.PLUGIN_ID, 4, "stop failed: database agent name cannot be null.");
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "stop failed: " + ex.getMessage());
        }
    }
    
    public void deleteAgent(final String str) {
        try {
            if (str != null) {
                AgentDatabase.findAgentDatabase("_SMDatabase_" + str).delete(null);
            }
            else {
                ProLog.logd(super.PLUGIN_ID, 4, "delete failed: database agent name cannot be null.");
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "delete failed: " + ex.getMessage());
        }
    }
    
    public Boolean createDatabase(final String[] array) {
        boolean instantiateNew = false;
        try {
            if (array != null && array.length == 3) {
                instantiateNew = SMPlugIn.instantiateNew(array[0], array[1], array[2]);
            }
            else {
                ProLog.logd(super.PLUGIN_ID, 4, "create failed: incorrect number of database arguments.");
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "create failed: " + ex.getMessage());
        }
        return new Boolean(instantiateNew);
    }
    
    public String getBrokerPort(final String s) {
        String brokerPort = "0";
        try {
            brokerPort = SMDatabase.findSMDatabase(s).getBrokerPort();
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getBrokerPort failed: " + ex.getMessage());
        }
        return brokerPort;
    }
    
    public Boolean getFileSysCS(final String s) {
        boolean fileSysCS = false;
        try {
            fileSysCS = SMDatabase.findSMDatabase(s).getFileSysCS();
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "getFileSysCS failed: " + ex.getMessage());
        }
        return new Boolean(fileSysCS);
    }
    
    public Boolean setAuxProcessStatus(final String s, final Integer n, final String s2) {
        boolean setAuxProcessStatus = false;
        try {
            setAuxProcessStatus = SMDatabase.findSMDatabase(s).setAuxProcessStatus(n, s2);
        }
        catch (Exception ex) {
            ProLog.logd(super.PLUGIN_ID, 4, "setAuxProcessStatus failed: " + ex.getMessage());
        }
        return new Boolean(setAuxProcessStatus);
    }
    
    public Boolean setDatabasePath(final String s, final String databasePath) {
        boolean value;
        try {
            SMDatabase.findSMDatabase(s).setDatabasePath(databasePath);
            value = true;
        }
        catch (Exception ex) {
            value = false;
            ProLog.logd(super.PLUGIN_ID, 4, "setDatabasePath failed: " + ex.getMessage());
        }
        return new Boolean(value);
    }
    
    public Boolean setDatabaseHost(final String s, final String databaseHost) {
        boolean value;
        try {
            SMDatabase.findSMDatabase(s).setDatabaseHost(databaseHost);
            value = true;
        }
        catch (Exception ex) {
            value = false;
            ProLog.logd(super.PLUGIN_ID, 4, "setDatabaseHost failed: " + ex.getMessage());
        }
        return new Boolean(value);
    }
    
    public Boolean rename(final String s, final String s2, final String s3) {
        boolean value;
        try {
            SMDatabase.findSMDatabase(s2).rename(s2, s3);
            value = true;
        }
        catch (Exception ex) {
            value = false;
            ProLog.logd(super.PLUGIN_ID, 4, "rename failed: " + ex.getMessage());
        }
        return new Boolean(value);
    }
    
    public Hashtable getStatistics(final String str, final String[] array) {
        return super.getStatistics("_SMDatabase_" + str, array);
    }
    
    public String getAbsoluteDatabaseFileName(final String s) {
        String absolutePath;
        try {
            absolutePath = new File(SMDatabase.findSMDatabase(s).physicalName()).getAbsolutePath();
        }
        catch (Exception ex) {
            absolutePath = "";
        }
        return absolutePath;
    }
    
    public Boolean writeProperties() {
        boolean value;
        try {
            ((SMProperties)SMPlugIn.get().getPlugInPropertyManager()).save();
            value = true;
        }
        catch (Exception ex) {
            value = false;
            ProLog.logd(super.PLUGIN_ID, 4, "Property save failed: " + ex.getMessage());
        }
        return new Boolean(value);
    }
    
    static {
        SM_MANAGEMENT_INFO = new IManagementInfo[DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO.length + 28];
        int length = DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO, 0, SMDatabasePluginComponent.SM_MANAGEMENT_INFO, 0, DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO.length);
        try {
            method = SMDatabasePluginComponent.class.getMethod("destroy", String.class);
        }
        catch (Exception ex) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the specified database instance.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("delete", String.class, String.class, String.class);
        }
        catch (Exception ex2) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the specified database instance.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("start", String.class, String.class, String.class);
        }
        catch (Exception ex3) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Start the specified database instance.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("stop", String.class, String.class, String.class);
        }
        catch (Exception ex4) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Stop the specified database instance.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("writeProperties", (Class[])new Class[0]);
        }
        catch (Exception ex5) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Write the database properties.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("createDatabase", String[].class);
        }
        catch (Exception ex6) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Create a new instance of the script managed database.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("deleteAgent", String.class);
        }
        catch (Exception ex7) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the specified database agent instance.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("startAgent", String.class);
        }
        catch (Exception ex8) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Start the specified database agent instance.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("stopAgent", String.class);
        }
        catch (Exception ex9) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Stop the specified database agent instance.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("disconnectUser", String.class, Integer.class);
        }
        catch (Exception ex10) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Disconnect the named user id from the database.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("getState", String.class, Integer.class, Integer.class);
        }
        catch (Exception ex11) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get state of the specified database instance type.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("getStateDescriptor", String.class, Integer.class);
        }
        catch (Exception ex12) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the state description of the specified database instance type.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("getPortInfo", String.class);
        }
        catch (Exception ex13) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return information about named port or service name.", method);
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("InstanceNames", String[].class.getName(), "Instances", true, false);
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("AdminServerPort", String.class.getName(), "Port that AdminServer is running on", true, false);
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("AgentPort", String.class.getName(), "Port that agent is listening on", true, false);
        try {
            method = SMDatabasePluginComponent.class.getMethod("getFileSysCS", String.class);
        }
        catch (Exception ex14) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Is the file system case sensitive?", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("setAuxProcessStatus", String.class, Integer.class, String.class);
        }
        catch (Exception ex15) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set the statuses of the auxiliary processes.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("getBrokerPort", String.class);
        }
        catch (Exception ex16) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the broker port.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("setDatabasePath", String.class, String.class);
        }
        catch (Exception ex17) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update the database path in the plugin.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("setDatabaseHost", String.class, String.class);
        }
        catch (Exception ex18) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update the database host in the plugin.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("rename", String.class, String.class, String.class);
        }
        catch (Exception ex19) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Rename the script managed database object.", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("databaseExists", String.class);
        }
        catch (Exception ex20) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database exist?", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("agentExists", String.class);
        }
        catch (Exception ex21) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database agent exist?", method);
        try {
            method = SMDatabasePluginComponent.class.getMethod("getAbsoluteDatabaseFileName", String.class);
        }
        catch (Exception ex22) {}
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return absolute file path associated with the named database.", method);
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ESMDStartupCompleted", "A script manage database agent process been started.");
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ESMDShutdownEventCompleted", "The script manage database agent process state has changed.");
        SMDatabasePluginComponent.SM_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ESMDCrash", "The script manage database agent process has crashed.");
    }
}

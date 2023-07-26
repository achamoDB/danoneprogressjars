// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import java.util.Date;
import java.text.DateFormat;
import com.progress.agent.database.AgentDatabase;
import com.progress.common.property.PropertyList;
import com.progress.agent.database.AgentPlugIn;
import java.util.Vector;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.property.EPropertiesChanged;
import java.util.StringTokenizer;
import java.util.Enumeration;
import com.progress.common.log.ProLog;
import com.progress.common.util.Port;
import java.util.Hashtable;
import java.io.File;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import java.lang.reflect.Method;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.agent.database.AgentProperties;
import com.progress.common.property.PropertyManager;
import com.progress.chimera.adminserver.IServerPlugin;
import com.progress.mf.AbstractPluginComponent;

public abstract class DatabasePluginComponent extends AbstractPluginComponent implements IJAComponentConstants
{
    public String PLUGIN_ID;
    public static String FILE_SEPARATOR;
    public IServerPlugin m_databasePlugIn;
    public PropertyManager m_databaseProperties;
    public AgentProperties m_agentProperties;
    public boolean m_agentIsLicensed;
    public String m_agentPropertyFileName;
    public static final String DATABASE_PROP_PREFIX = "datatabase.";
    public static final String DATABASE_METRIC_PREFIX = "database.metric.vst.";
    public static final IManagementInfo[] DB_BASE_MANAGEMENT_INFO;
    public static Method processMetricsMethod;
    
    public DatabasePluginComponent() {
        this.PLUGIN_ID = JAPlugIn.PLUGIN_ID;
        this.m_databasePlugIn = null;
        this.m_databaseProperties = null;
        this.m_agentProperties = null;
        this.m_agentIsLicensed = true;
        this.m_agentPropertyFileName = null;
    }
    
    public abstract IManagementInfo[] getManagementInfo();
    
    public void setDatabaseProperties(final PropertyManager databaseProperties) {
        this.m_databaseProperties = databaseProperties;
    }
    
    public PropertyManager getDatabaseProperties() {
        return this.m_databaseProperties;
    }
    
    public void setDatabasePlugIn(final IServerPlugin databasePlugIn) {
        this.m_databasePlugIn = databasePlugIn;
    }
    
    public IServerPlugin getDatabasePlugIn() {
        return this.m_databasePlugIn;
    }
    
    public abstract AgentProperties setAgentProperties();
    
    public abstract String setAgentPropertyFileName();
    
    public abstract Boolean databaseExists(final String p0);
    
    public abstract Boolean agentExists(final String p0);
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        super.init(o, frameworkComponentContext);
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "datatabase.";
        AbstractPluginComponent.m_metricSearchPrefix = "database.metric.vst.";
    }
    
    public Boolean fileExists(final String pathname) {
        boolean exists = false;
        try {
            if (pathname != null) {
                exists = new File(pathname).exists();
            }
        }
        catch (Exception ex) {}
        return new Boolean(exists);
    }
    
    public Hashtable getPortInfo(final String s) {
        final Hashtable<String, Boolean> hashtable = new Hashtable<String, Boolean>();
        try {
            final Port port = new Port(s);
            hashtable.put("PortInUse", new Boolean(port.isInUse()));
            hashtable.put("PortIntValue", (Boolean)(Object)new Integer(port.getPortInt()));
            hashtable.put("PortName", (Boolean)port.getName());
            hashtable.put("PlatformName", (Boolean)System.getProperty("os.name"));
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getPortInfo: failed: " + ex.getMessage());
        }
        return hashtable;
    }
    
    public String[] getArrayProperty(final String s) {
        String[] arrayProperty = null;
        try {
            arrayProperty = this.m_databaseProperties.getArrayProperty(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getArrayProperty failed: " + ex.getMessage());
        }
        return arrayProperty;
    }
    
    public Boolean getBooleanProperty(final String s) {
        Boolean b = null;
        try {
            b = new Boolean(this.m_databaseProperties.getBooleanProperty(s));
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getBooleanProperty failed: " + ex.getMessage());
        }
        return b;
    }
    
    public String getProperty(final String s) {
        String property = null;
        try {
            property = this.m_databaseProperties.getProperty(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getProperty failed: " + ex.getMessage());
        }
        return property;
    }
    
    public Boolean putArrayProperty(final String s, final String[] array) {
        boolean putArrayProperty = false;
        try {
            putArrayProperty = this.m_databaseProperties.putArrayProperty(s, array);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "putArrayProperty failed: " + ex.getMessage());
        }
        return new Boolean(putArrayProperty);
    }
    
    public Boolean removeConfiguration(final String s) {
        boolean value;
        try {
            this.m_databaseProperties.removeGroup(s);
            value = true;
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, ex.getMessage());
            value = false;
        }
        return new Boolean(value);
    }
    
    public Hashtable getConfiguration(final String s) {
        return this.makePropertyValueHashtable(s);
    }
    
    public Hashtable getConfigurationSchema(final String[] array) {
        final Hashtable<String, Hashtable> hashtable = new Hashtable<String, Hashtable>();
        for (int i = 0; i < array.length; ++i) {
            hashtable.put(array[i], this.getPropertySchemaHashtable(array[i]));
        }
        return hashtable;
    }
    
    public Hashtable getConfigurationSchema(final String s) {
        return this.getPropertySchemaHashtable(s);
    }
    
    public void setConfiguration(final String s, final Hashtable hashtable) throws Exception {
        final Hashtable groupSchemaHashtable = this.m_databaseProperties.getGroupSchemaHashtable();
        if (groupSchemaHashtable != null) {
            final Enumeration<String> keys = groupSchemaHashtable.keys();
            while (keys.hasMoreElements()) {
                final String prefix = keys.nextElement();
                if (s.startsWith(prefix)) {
                    this.setConfiguration(prefix, s, hashtable);
                    break;
                }
            }
        }
    }
    
    private IJARemoteObject getRemoteObjectStub(final String str) {
        IJARemoteObject ijaRemoteObject = null;
        String nextToken = null;
        String nextToken2 = null;
        String nextToken3 = null;
        String nextToken4 = null;
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ".", false);
        stringTokenizer.countTokens();
        if (stringTokenizer.hasMoreTokens()) {
            nextToken = stringTokenizer.nextToken();
        }
        if (stringTokenizer.hasMoreTokens()) {
            nextToken2 = stringTokenizer.nextToken();
        }
        if (stringTokenizer.hasMoreTokens()) {
            nextToken3 = stringTokenizer.nextToken();
        }
        if (stringTokenizer.hasMoreTokens()) {
            nextToken4 = stringTokenizer.nextToken();
        }
        if (nextToken.startsWith("database")) {
            ijaRemoteObject = JADatabase.findDatabase(nextToken2);
        }
        else if (nextToken.startsWith("configuration")) {
            ijaRemoteObject = JAConfiguration.findConfiguration(nextToken2 + "." + nextToken3);
        }
        else if (nextToken.startsWith("servergroup")) {
            ijaRemoteObject = JAService.findService(nextToken2 + "." + nextToken3 + "." + nextToken4);
        }
        return ijaRemoteObject;
    }
    
    public void setConfiguration(final String s, final String s2, final Hashtable hashtable) throws Exception {
        try {
            this.m_databaseProperties.setConfiguration(s, s2, hashtable);
            final IJARemoteObject remoteObjectStub = this.getRemoteObjectStub(s2);
            if (remoteObjectStub != null) {
                JAPlugIn.get().getEventBroker().postEvent(new EPropertiesChanged(remoteObjectStub.remoteStub(), null));
            }
        }
        catch (PropertyManager.NoSuchGroupException ex) {
            ProLog.logd(this.PLUGIN_ID, 4, ex.getMessage());
            throw ex;
        }
        catch (PropertyManager.PropertyValuesException ex2) {
            ProLog.logd(this.PLUGIN_ID, 4, ex2.getMessage());
            throw ex2;
        }
        catch (Throwable t) {
            ProLog.logd(this.PLUGIN_ID, 4, "setConfiguration failed: " + t.getMessage());
        }
    }
    
    public Vector validateProperties(final String[] array, final String s, final Hashtable hashtable, final Hashtable hashtable2) throws Exception {
        final Vector vector = new Vector();
        Vector validateProperties;
        try {
            validateProperties = this.m_databaseProperties.validateProperties(array, s, hashtable, hashtable2);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, ex.getMessage());
            throw ex;
        }
        return validateProperties;
    }
    
    public Hashtable getThresholds(final String s) {
        return null;
    }
    
    public Hashtable getThresholdSchema(final String[] array) {
        return null;
    }
    
    public void setThresholds(final String s, final Hashtable hashtable) throws Exception {
        throw new Exception("Method not implemented");
    }
    
    public Hashtable getGroupSchemaHashtable() {
        Hashtable groupSchemaHashtable = null;
        try {
            groupSchemaHashtable = this.m_databaseProperties.getGroupSchemaHashtable();
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupSchemaHashtable failed: " + ex.getMessage());
        }
        return groupSchemaHashtable;
    }
    
    public Hashtable getGroupAttributeHashtable(final String s) {
        Hashtable groupAttributeHashtable = null;
        try {
            groupAttributeHashtable = this.m_databaseProperties.getGroupAttributeHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupAttributeHashtable failed: " + ex.getMessage());
        }
        return groupAttributeHashtable;
    }
    
    public Hashtable getPropertySchemaHashtable(final String s) {
        Hashtable propertySchemaHashtable = null;
        try {
            propertySchemaHashtable = this.m_databaseProperties.getPropertySchemaHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getPropertySchemaHashtable failed: " + ex.getMessage());
        }
        return propertySchemaHashtable;
    }
    
    public Hashtable getCategorySchemaHashtable(final String s) {
        Hashtable categorySchemaHashtable = null;
        try {
            categorySchemaHashtable = this.m_databaseProperties.getCategorySchemaHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getCategorySchemaHashtable(final String s, final String s2) {
        Hashtable categorySchemaHashtable = null;
        try {
            categorySchemaHashtable = this.m_databaseProperties.getCategorySchemaHashtable(s, s2);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getCategorySchemaHashtable(final String s, final String[] array) {
        Hashtable categorySchemaHashtable = null;
        try {
            categorySchemaHashtable = this.m_databaseProperties.getCategorySchemaHashtable(s, array);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getCategoryAttributeHashtable(final String s) {
        Hashtable categoryAttributeHashtable = null;
        try {
            categoryAttributeHashtable = this.m_databaseProperties.getCategoryAttributeHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategoryAttributeHashtable failed: " + ex.getMessage());
        }
        return categoryAttributeHashtable;
    }
    
    public Hashtable makePropertyValueHashtable(final String s) {
        Hashtable propertyValueHash = null;
        try {
            propertyValueHash = this.m_databaseProperties.makePropertyValueHash(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "makePropertyValueHashtable failed: " + ex.getMessage());
        }
        return propertyValueHash;
    }
    
    public abstract void startAgent(final String p0);
    
    public abstract void stopAgent(final String p0);
    
    public abstract void deleteAgent(final String p0);
    
    public String[] getAgentArrayProperty(final String s) {
        String[] arrayProperty = null;
        try {
            if (this.m_agentIsLicensed) {
                arrayProperty = this.m_agentProperties.getArrayProperty(s);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getArrayProperty failed: " + ex.getMessage());
        }
        return arrayProperty;
    }
    
    public Boolean getAgentBooleanProperty(final String s) {
        Boolean b = null;
        try {
            if (this.m_agentIsLicensed) {
                b = new Boolean(this.m_agentProperties.getBooleanProperty(s));
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getBooleanProperty failed: " + ex.getMessage());
        }
        return b;
    }
    
    public String getAgentProperty(final String s) {
        String property = null;
        try {
            if (this.m_agentIsLicensed) {
                property = this.m_agentProperties.getProperty(s);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getBooleanProperty failed: " + ex.getMessage());
        }
        return property;
    }
    
    public Hashtable getStatistics(final String str, final String[] array) {
        boolean b = false;
        final Hashtable<String, Object[]> hashtable = new Hashtable<String, Object[]>();
        try {
            AgentPlugIn.get();
            final AgentProperties agentProperties = (AgentProperties)AgentPlugIn.propertiesS();
            for (int i = 0; i < array.length; ++i) {
                if (array[i].equals("QueryAuxiliaries")) {
                    b = true;
                }
                else {
                    final PropertyList list = new PropertyList();
                    final StringBuffer sb = new StringBuffer();
                    sb.append("metric.vst.");
                    sb.append(str);
                    sb.append(".");
                    sb.append(array[i]);
                    agentProperties.processMetric2(list, this.PLUGIN_ID, sb.toString());
                    final Vector propertyElements = list.getPropertyElements();
                    final int j = (propertyElements != null) ? propertyElements.size() : 0;
                    final Object[] value = new Object[j];
                    ProLog.logd(this.PLUGIN_ID, 5, sb.toString() + " got " + j + " elements");
                    for (int k = 0; k < j; ++k) {
                        value[k] = propertyElements.elementAt(k).getPropertyValues().toArray(new String[0]);
                    }
                    hashtable.put(array[i], value);
                }
            }
            hashtable.put("Status", (Object[])(Object)new Boolean(true));
        }
        catch (Exception ex) {
            final String s = (ex.getMessage() == null) ? ex.toString() : ex.getMessage();
            ProLog.logd(this.PLUGIN_ID, 3, "getStatistics failed: " + s);
            ex.printStackTrace();
            hashtable.put("Status", (Object[])(Object)new Boolean(false));
            hashtable.put("Error", (Object[])(Object)s);
            if (ex instanceof SchemaLockedException) {
                hashtable.put("Error", (Object[])(Object)"SchemaLocked");
            }
            else if (ex instanceof SchemaParsingException) {
                hashtable.put("Error", (Object[])(Object)"SchemaParsingError");
            }
        }
        if (b) {
            final AgentDatabase agentDatabase = AgentDatabase.findAgentDatabase(str);
            if (agentDatabase != null && agentDatabase.isRunning() && agentDatabase.isRemoteDb()) {
                agentDatabase.sendPacket("S_APW_REQ");
                agentDatabase.sendPacket("S_AIW_REQ");
                agentDatabase.sendPacket("S_BIW_REQ");
                agentDatabase.sendPacket("S_WDOG_REQ");
            }
            else {
                final JADatabase database = JADatabase.findDatabase(str);
                final JAConfiguration jaConfiguration = (database != null) ? ((JAConfiguration)database.getRunningConfiguration()) : null;
                if (jaConfiguration != null) {
                    ((MProservJuniper)jaConfiguration.rjo).sendPacket("AWU?");
                    ((MProservJuniper)jaConfiguration.rjo).sendPacket("AIU?");
                    ((MProservJuniper)jaConfiguration.rjo).sendPacket("BIU?");
                    ((MProservJuniper)jaConfiguration.rjo).sendPacket("WDU?");
                }
            }
        }
        return hashtable;
    }
    
    public Hashtable getStatisticSchema(final String[] array) {
        Hashtable partialTableSchema = new Hashtable();
        try {
            if (this.m_agentIsLicensed) {
                final AgentProperties agentProperties = this.m_agentProperties;
                partialTableSchema = AgentProperties.getPartialTableSchema(array);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getStatisticsSchema failed: " + ex.getMessage());
        }
        return partialTableSchema;
    }
    
    public Object[] getTableSchema(final String s, final String s2) {
        Object[] fullTableSchema = new Object[0];
        try {
            if (this.m_agentIsLicensed) {
                String schemaVersion = "9";
                if (s != null) {
                    final AgentDatabase agentDatabase = AgentDatabase.findAgentDatabase(s);
                    if (agentDatabase != null && agentDatabase.isRunning()) {
                        schemaVersion = agentDatabase.getSchemaVersion();
                    }
                }
                final AgentProperties agentProperties = this.m_agentProperties;
                fullTableSchema = AgentProperties.getFullTableSchema(s2, schemaVersion);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getStatisticsSchema failed: " + ex.getMessage());
        }
        return fullTableSchema;
    }
    
    public Boolean is64BitSchema(final String s) {
        boolean is64BitSchema = false;
        try {
            if (this.m_agentIsLicensed && s != null) {
                final AgentDatabase agentDatabase = AgentDatabase.findAgentDatabase(s);
                if (agentDatabase != null && agentDatabase.isRunning()) {
                    is64BitSchema = agentDatabase.is64BitSchema();
                }
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "is64BitSchema() failed: " + ex.getMessage());
        }
        return new Boolean(is64BitSchema);
    }
    
    public void writeAgentProperties() {
        try {
            if (this.m_agentIsLicensed) {
                this.m_agentProperties.save(this.m_agentPropertyFileName, "Update: " + DateFormat.getDateInstance().format(new Date()));
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "writeAgentProperties failed: " + ex.getMessage());
        }
    }
    
    public Boolean disconnectUser(final String s, final Integer n) {
        Boolean disconnectUser = new Boolean(false);
        try {
            if (this.m_agentIsLicensed) {
                disconnectUser = AgentDatabase.disconnectUser(s, n);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 3, "Uable to disconnect user: " + ex.getMessage());
        }
        return disconnectUser;
    }
    
    public Hashtable getAgentConfiguration(final String s) {
        return this.makeAgentPropertyValueHashtable(s);
    }
    
    public Hashtable getAgentConfigurationSchema(final String[] array) {
        final Hashtable<String, Hashtable> hashtable = new Hashtable<String, Hashtable>();
        for (int i = 0; i < array.length; ++i) {
            hashtable.put(array[i], this.getPropertySchemaHashtable(array[i]));
        }
        return hashtable;
    }
    
    public Hashtable getAgentConfigurationSchema(final String s) {
        return this.getPropertySchemaHashtable(s);
    }
    
    public void setAgentConfiguration(final String s, final Hashtable hashtable) throws Exception {
        if (this.m_agentIsLicensed) {
            final Hashtable groupSchemaHashtable = this.m_agentProperties.getGroupSchemaHashtable();
            if (groupSchemaHashtable != null) {
                final Enumeration<String> keys = groupSchemaHashtable.keys();
                while (keys.hasMoreElements()) {
                    final String prefix = keys.nextElement();
                    if (s.startsWith(prefix)) {
                        this.setConfiguration(prefix, s, hashtable);
                        break;
                    }
                }
            }
        }
    }
    
    public void setAgentConfiguration(final String s, final String s2, final Hashtable hashtable) throws Exception {
        try {
            if (this.m_agentIsLicensed) {
                this.m_agentProperties.setConfiguration(s, s2, hashtable);
            }
        }
        catch (PropertyManager.NoSuchGroupException ex) {
            ProLog.logd(this.PLUGIN_ID, 4, ex.getMessage());
            throw ex;
        }
        catch (PropertyManager.PropertyValuesException ex2) {
            ProLog.logd(this.PLUGIN_ID, 4, ex2.getMessage());
            throw ex2;
        }
        catch (Exception ex3) {
            ProLog.logd(this.PLUGIN_ID, 4, "setConfiguration failed: " + ex3.getMessage());
        }
    }
    
    public Hashtable getAgentGroupSchemaHashtable() {
        Hashtable groupSchemaHashtable = null;
        try {
            if (this.m_agentIsLicensed) {
                groupSchemaHashtable = this.m_agentProperties.getGroupSchemaHashtable();
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupSchemaHashtable failed: " + ex.getMessage());
        }
        return groupSchemaHashtable;
    }
    
    public Hashtable getAgentGroupAttributeHashtable(final String s) {
        Hashtable groupAttributeHashtable = null;
        try {
            if (this.m_agentIsLicensed) {
                groupAttributeHashtable = this.m_agentProperties.getGroupAttributeHashtable(s);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupAttributeHashtable failed: " + ex.getMessage());
        }
        return groupAttributeHashtable;
    }
    
    public Hashtable getAgentPropertySchemaHashtable(final String s) {
        Hashtable propertySchemaHashtable = null;
        try {
            if (this.m_agentIsLicensed) {
                propertySchemaHashtable = this.m_agentProperties.getPropertySchemaHashtable(s);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getPropertySchemaHashtable failed: " + ex.getMessage());
        }
        return propertySchemaHashtable;
    }
    
    public Hashtable getAgentCategorySchemaHashtable(final String s) {
        Hashtable categorySchemaHashtable = null;
        try {
            if (this.m_agentIsLicensed) {
                categorySchemaHashtable = this.m_agentProperties.getCategorySchemaHashtable(s);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getAgentCategorySchemaHashtable(final String s, final String s2) {
        Hashtable categorySchemaHashtable = null;
        try {
            if (this.m_agentIsLicensed) {
                categorySchemaHashtable = this.m_agentProperties.getCategorySchemaHashtable(s, s2);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getAgentCategorySchemaHashtable(final String s, final String[] array) {
        Hashtable categorySchemaHashtable = null;
        try {
            if (this.m_agentIsLicensed) {
                categorySchemaHashtable = this.m_agentProperties.getCategorySchemaHashtable(s, array);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getAgentCategoryAttributeHashtable(final String s) {
        Hashtable categoryAttributeHashtable = null;
        try {
            if (this.m_agentIsLicensed) {
                categoryAttributeHashtable = this.m_agentProperties.getCategoryAttributeHashtable(s);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategoryAttributeHashtable failed: " + ex.getMessage());
        }
        return categoryAttributeHashtable;
    }
    
    public Hashtable makeAgentPropertyValueHashtable(final String s) {
        Hashtable propertyValueHash = null;
        try {
            if (this.m_agentIsLicensed) {
                propertyValueHash = this.m_agentProperties.makePropertyValueHash(s);
            }
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupAttributeHashtable failed: " + ex.getMessage());
        }
        return propertyValueHash;
    }
    
    static {
        DatabasePluginComponent.FILE_SEPARATOR = System.getProperty("file.separator");
        DB_BASE_MANAGEMENT_INFO = new IManagementInfo[AbstractPluginComponent.BASE_MANAGEMENT_INFO.length + 46];
        DatabasePluginComponent.processMetricsMethod = null;
        int length = AbstractPluginComponent.BASE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(AbstractPluginComponent.BASE_MANAGEMENT_INFO, 0, DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO, 0, AbstractPluginComponent.BASE_MANAGEMENT_INFO.length);
        try {
            method = DatabasePluginComponent.class.getMethod("writeAgentProperties", (Class[])new Class[0]);
        }
        catch (Exception ex) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Write the database agent properties.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("disconnectUser", String.class, Integer.class);
        }
        catch (Exception ex2) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Disconnect the named user id from the database.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("removeConfiguration", String.class);
        }
        catch (Exception ex3) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Remove the configuration associated with the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getPortInfo", String.class);
        }
        catch (Exception ex4) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return information about named port or service name.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("fileExists", String.class);
        }
        catch (Exception ex5) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named file exist?", method);
        try {
            method = DatabasePluginComponent.class.getMethod("databaseExists", String.class);
        }
        catch (Exception ex6) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database exist?", method);
        try {
            method = DatabasePluginComponent.class.getMethod("agentExists", String.class);
        }
        catch (Exception ex7) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named database agent exist?", method);
        try {
            method = DatabasePluginComponent.class.getMethod("putArrayProperty", String.class, String[].class);
        }
        catch (Exception ex8) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set the array of property values for the named property.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getArrayProperty", String.class);
        }
        catch (Exception ex9) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the list of values associated with the named property.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getBooleanProperty", String.class);
        }
        catch (Exception ex10) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the Boolean value associated with the named property.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getProperty", String.class);
        }
        catch (Exception ex11) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the String value associated with the named property.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentArrayProperty", String.class);
        }
        catch (Exception ex12) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the list of values associated with the named property.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentBooleanProperty", String.class);
        }
        catch (Exception ex13) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the Boolean value associated with the named property.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentProperty", String.class);
        }
        catch (Exception ex14) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the String value associated with the named property.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("setConfiguration", String.class, Hashtable.class);
        }
        catch (Exception ex15) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set properties for the specified configuration (property) name.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getConfiguration", String.class);
        }
        catch (Exception ex16) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a set of properties for the specified configuration (property) name.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getConfigurationSchema", String.class);
        }
        catch (Exception ex17) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified configuration element name.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getConfigurationSchema", String[].class);
        }
        catch (Exception ex18) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of configuration element names.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getGroupSchemaHashtable", String.class);
        }
        catch (Exception ex19) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for all groups.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getGroupAttributeHashtable", String.class);
        }
        catch (Exception ex20) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getPropertySchemaHashtable", String.class);
        }
        catch (Exception ex21) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property schema definition for the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getCategorySchemaHashtable", String.class);
        }
        catch (Exception ex22) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getCategorySchemaHashtable", String.class);
        }
        catch (Exception ex23) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getCategorySchemaHashtable", String.class);
        }
        catch (Exception ex24) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getCategoryAttributeHashtable", String.class);
        }
        catch (Exception ex25) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("makePropertyValueHashtable", String.class);
        }
        catch (Exception ex26) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property values defined for the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("validateProperties", String[].class, String.class, Hashtable.class, Hashtable.class);
        }
        catch (Exception ex27) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Validate the provided values for the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("setAgentConfiguration", String.class, Hashtable.class);
        }
        catch (Exception ex28) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set properties for the specified configuration (property) name.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentConfiguration", String.class);
        }
        catch (Exception ex29) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a set of properties for the specified configuration (property) name.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentConfigurationSchema", String.class);
        }
        catch (Exception ex30) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified configuration element name.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentConfigurationSchema", String[].class);
        }
        catch (Exception ex31) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of configuration element names.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentGroupSchemaHashtable", String.class);
        }
        catch (Exception ex32) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for all groups.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentGroupAttributeHashtable", String.class);
        }
        catch (Exception ex33) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentPropertySchemaHashtable", String.class);
        }
        catch (Exception ex34) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property schema definition for the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentCategorySchemaHashtable", String.class);
        }
        catch (Exception ex35) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentCategorySchemaHashtable", String.class);
        }
        catch (Exception ex36) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentCategorySchemaHashtable", String.class);
        }
        catch (Exception ex37) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getAgentCategoryAttributeHashtable", String.class);
        }
        catch (Exception ex38) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified category.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("makeAgentPropertyValueHashtable", String.class);
        }
        catch (Exception ex39) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property values defined for the specified group.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getStatistics", String.class, String[].class);
        }
        catch (Exception ex40) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return from the specified instance, the set of statistics for the listed statistic names.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getStatisticSchema", String[].class);
        }
        catch (Exception ex41) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of statistic names.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("getTableSchema", String.class, String.class);
        }
        catch (Exception ex42) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified vst or system table.", method);
        try {
            method = DatabasePluginComponent.class.getMethod("is64BitSchema", String.class);
        }
        catch (Exception ex43) {}
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return true if the named agent supports 64-bit vst and system tables.", method);
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EDBAStartupCompletedEvent", "An database agent process been started.");
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EDBAStateChanged", "The database agent process state has changed.");
        DatabasePluginComponent.DB_BASE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EDBACrashEvent", "The database agent process has crashed.");
    }
    
    public static class SchemaException extends PropertyManager.PropertyException
    {
        public SchemaException(final String s) {
            super(s, (Object[])null);
        }
        
        public String getPropertyName() {
            return (String)this.getArgument(0);
        }
    }
    
    public static class SchemaLockedException extends SchemaException
    {
        public SchemaLockedException(final String str) {
            super("Schema locked by another user: " + str);
        }
    }
    
    public static class SchemaDeniedException extends SchemaException
    {
        public SchemaDeniedException(final String str, final String str2) {
            super("Schema denied for database " + str + " / table " + str2);
        }
    }
    
    public static class SchemaParsingException extends SchemaException
    {
        public SchemaParsingException(final String str, final String str2) {
            super("Error processing schema for database " + str + " / table " + str2);
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import java.lang.reflect.Method;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import com.progress.ubroker.tools.NSGuiPlugin;
import com.progress.ubroker.util.DynamicPropertyValues;
import java.util.Vector;
import java.rmi.RemoteException;
import com.progress.common.property.PropertyManager;
import java.util.Hashtable;
import com.progress.ubroker.tools.SvcControlCmd;
import com.progress.common.log.ProLog;
import com.progress.ubroker.tools.PropMgrPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.ubroker.util.ToolRemoteCmdStatus;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import com.progress.ubroker.util.PropMgrUtils;
import com.progress.ubroker.tools.AbstractGuiPluginRemObj;
import com.progress.mf.AbstractPluginComponent;

public abstract class OpenEdgePluginComponent extends AbstractPluginComponent
{
    private static int DEFAULT_UB_PING_RETRY_COUNT;
    protected int serverType;
    protected String PLUGIN_ID;
    protected AbstractGuiPluginRemObj m_plugin;
    protected PropMgrUtils.UBProperties m_ubProperties;
    protected PropMgrUtils m_propMgrUtils;
    private ToolRemoteCmdDescriptor m_cmdDescriptor;
    private ToolRemoteCmdStatus m_cmdStatus;
    protected static final IManagementInfo[] OE_MANAGEMENT_INFO;
    
    public OpenEdgePluginComponent() {
        this.PLUGIN_ID = "UBroker";
        this.m_plugin = null;
        this.m_ubProperties = null;
        this.m_propMgrUtils = null;
        this.m_cmdDescriptor = new ToolRemoteCmdDescriptor();
        this.m_cmdStatus = new ToolRemoteCmdStatus();
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        super.init(o, frameworkComponentContext);
        if (super.m_pluginObject != null) {
            this.m_plugin = (AbstractGuiPluginRemObj)((AbstractGuiPlugin)o).m_wkRemote;
            final PropMgrPlugin pmpObject = this.m_plugin.m_pmpObject;
            this.m_ubProperties = pmpObject.getUBProperties();
            this.m_propMgrUtils = pmpObject.getPropMgrUtils();
        }
        this.PLUGIN_ID = this.m_plugin.m_personality;
    }
    
    public void start(final String str) {
        final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor = new ToolRemoteCmdDescriptor();
        toolRemoteCmdDescriptor.makeStartSvcPkt(this.getPropertyGroup() + "." + str);
        new Thread(new StartRunnableObject(toolRemoteCmdDescriptor)).start();
    }
    
    public void stop(final String str) {
        final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor = new ToolRemoteCmdDescriptor();
        toolRemoteCmdDescriptor.makeStopSvcPkt(this.getPropertyGroup() + "." + str);
        new Thread(new StopRunnableObject(toolRemoteCmdDescriptor)).start();
    }
    
    public void destroy(final String s) {
        final ToolRemoteCmdDescriptor toolRemoteCmdDescriptor = new ToolRemoteCmdDescriptor();
        toolRemoteCmdDescriptor.makeRemoveGuiToolPkt(s, this.getPropertyGroup());
        this.m_plugin.delSvcGuiTool(toolRemoteCmdDescriptor);
    }
    
    public String[] getInstanceNames() {
        String[] array = null;
        try {
            this.m_plugin.getChildren();
            array = this.m_plugin.m_childrenNames.toArray(new String[0]);
        }
        catch (Exception ex) {
            ProLog.logd(this.m_plugin.m_personality, 3, "Unable to retrieve instance names.");
        }
        return array;
    }
    
    public Boolean getIsRunning(final String str) {
        boolean value = false;
        final SvcControlCmd svcControlCmd = (SvcControlCmd)this.m_plugin.lookupControlCmd(this.getPropertyGroup() + "." + str);
        if (svcControlCmd != null) {
            final long svcCntlState = svcControlCmd.getSvcCntlState();
            if (svcCntlState == 2L || svcCntlState == 1L) {
                value = true;
            }
            if (svcControlCmd.silentPingOnce() == 1L) {
                value = true;
            }
        }
        return new Boolean(value);
    }
    
    public Long silentPingOnce(final String str) {
        return new Long(((SvcControlCmd)this.m_plugin.lookupControlCmd(this.getPropertyGroup() + "." + str)).silentPingOnce());
    }
    
    public Long silentPingRetry(final String str, final Integer n) {
        return new Long(((SvcControlCmd)this.m_plugin.lookupControlCmd(this.getPropertyGroup() + "." + str)).silentPingRetry(n));
    }
    
    public String getExpandedValueForProperty(final String str, final String s) {
        String expandedPropertyValue = this.m_propMgrUtils.getExpandedPropertyValue(s, this.getPropertyGroup() + "." + str);
        if (expandedPropertyValue == null) {
            expandedPropertyValue = "";
        }
        return expandedPropertyValue;
    }
    
    public Hashtable getEnvPropertyValues(String string) {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        try {
            if (!string.startsWith(this.getPropertyGroup())) {
                string = this.getPropertyGroup() + "." + string;
            }
            final String[][] customizedEnvVars = this.m_propMgrUtils.getCustomizedEnvVars(this.m_ubProperties.properties(string, true, true));
            if (customizedEnvVars != null) {
                for (int i = 0; i < customizedEnvVars.length; ++i) {
                    hashtable.put(customizedEnvVars[i][0], customizedEnvVars[i][1]);
                }
            }
        }
        catch (PropertyManager.NoSuchGroupException ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getEnvPropertyValues failed: " + ex.getMessage());
        }
        catch (Exception ex2) {
            ProLog.logd(this.PLUGIN_ID, 4, "getEnvPropertyValues failed: " + ex2.getMessage());
        }
        return hashtable;
    }
    
    public Hashtable getCategories() {
        return this.m_ubProperties.getCategories(this.getPropertyGroup());
    }
    
    public Hashtable getConfiguration(String string) {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        try {
            final String string2 = this.getPropertyGroup().toLowerCase() + ".";
            final String lowerCase = string.toLowerCase();
            if (!lowerCase.startsWith(string2) && !lowerCase.startsWith("environment") && !lowerCase.startsWith("adminrole")) {
                string = string2 + string;
            }
            final PropertyManager.PropertyCollection properties = this.m_ubProperties.properties(string, true, true);
            while (properties != null && properties.hasMoreElements()) {
                final PropertyManager.Property property = (PropertyManager.Property)properties.nextElement();
                final String name = property.getName();
                final String valueOrDefault = property.getValueOrDefault();
                if (valueOrDefault == null) {
                    hashtable.put(name, "");
                }
                else {
                    hashtable.put(name, valueOrDefault);
                }
            }
        }
        catch (PropertyManager.NoSuchGroupException ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getConfiguration failed: " + ex.getMessage());
        }
        catch (Exception ex2) {
            ProLog.logd(this.PLUGIN_ID, 4, "getConfiguration failed: " + ex2.getMessage());
        }
        return hashtable;
    }
    
    public String getProperty(final String s) {
        String property = null;
        if (this.m_ubProperties != null) {
            property = this.m_ubProperties.getProperty(s);
        }
        return property;
    }
    
    public Boolean getBooleanProperty(final String s) {
        Boolean b = null;
        try {
            b = new Boolean(this.m_ubProperties.getBooleanProperty(s));
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getBooleanProperty failed: " + ex.getMessage());
        }
        return b;
    }
    
    public String[] getArrayProperty(final String s) {
        String[] arrayProperty = null;
        try {
            arrayProperty = this.m_ubProperties.getArrayProperty(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getArrayProperty failed: " + ex.getMessage());
        }
        return arrayProperty;
    }
    
    public Boolean putArrayProperty(final String s, final String[] array) {
        boolean putArrayProperty = false;
        try {
            putArrayProperty = this.m_ubProperties.putArrayProperty(s, array);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "putArrayProperty failed: " + ex.getMessage());
        }
        return new Boolean(putArrayProperty);
    }
    
    public Hashtable getConfigurationSchema(final String[] array) {
        return this.m_ubProperties.getCategorySchemaHashtable(this.getPropertyGroup(), array);
    }
    
    public void setConfiguration(String string, final Hashtable hashtable) throws Exception {
        boolean b;
        try {
            final String lowerCase = string.toLowerCase();
            if (!string.startsWith(this.getPropertyGroup()) && !lowerCase.startsWith("environment") && !lowerCase.startsWith("adminrole")) {
                string = this.getPropertyGroup() + "." + string;
            }
            this.m_propMgrUtils.saveProperties(string, hashtable);
            b = true;
        }
        catch (Exception ex) {
            b = false;
        }
        if (!b) {
            throw new Exception("Unable to set configuration properties for: " + string);
        }
    }
    
    public Boolean removeConfiguration(String string) throws Exception {
        final String lowerCase = string.toLowerCase();
        if (!string.startsWith(this.getPropertyGroup()) && !lowerCase.startsWith("environment") && !lowerCase.startsWith("adminrole")) {
            string = this.getPropertyGroup() + "." + string;
        }
        boolean value;
        try {
            this.m_ubProperties.removeGroup(string);
            value = true;
        }
        catch (Throwable t) {
            value = false;
        }
        return new Boolean(value);
    }
    
    public Boolean writeProperties() {
        boolean value = false;
        try {
            if (this.m_ubProperties != null) {
                this.m_ubProperties.save(this.m_ubProperties.getPath(), "Update all properties");
                value = true;
            }
        }
        catch (Exception ex) {
            value = false;
        }
        return new Boolean(value);
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
    
    protected String getPropertyGroup() {
        String propGroupPath = "";
        try {
            propGroupPath = this.m_plugin.getPropGroupPath();
        }
        catch (RemoteException ex) {}
        return propGroupPath;
    }
    
    protected void initSearchPrefix() {
    }
    
    public Hashtable getStatisticSchema(final String[] array) {
        final Hashtable<String, String[]> hashtable = new Hashtable<String, String[]>();
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equals("detailStatus")) {
                hashtable.put(array[i], UBrokerVST.getUBDetailStatusRawStringSchema(this.serverType));
            }
            else if (array[i].equals("summaryStatus")) {
                hashtable.put(array[i], UBrokerVST.getUBSummaryStatusRawStringSchema(this.serverType));
            }
            else {
                hashtable.put(array[i], (String[])UBrokerVST.getSchema(new String[] { array[i] }).get(array[i]));
            }
        }
        return hashtable;
    }
    
    public Hashtable getStatistics(final String str, final String[] array) {
        final String string = this.getPropertyGroup() + "." + str;
        final Hashtable hashtable = new Hashtable();
        final Hashtable data = this.m_plugin.getData(string, array);
        return (data == null) ? new Hashtable() : data;
    }
    
    public Hashtable getGroupSchemaHashtable() {
        Hashtable groupSchemaHashtable = null;
        try {
            groupSchemaHashtable = this.m_ubProperties.getGroupSchemaHashtable();
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupSchemaHashtable failed: " + ex.getMessage());
        }
        return groupSchemaHashtable;
    }
    
    public Hashtable getGroupAttributeHashtable(final String s) {
        Hashtable groupAttributeHashtable = null;
        try {
            groupAttributeHashtable = this.m_ubProperties.getGroupAttributeHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupAttributeHashtable failed: " + ex.getMessage());
        }
        return groupAttributeHashtable;
    }
    
    public Hashtable getPropertySchemaHashtable(final String s) {
        Hashtable propertySchemaHashtable = null;
        try {
            propertySchemaHashtable = this.m_ubProperties.getPropertySchemaHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getPropertySchemaHashtable failed: " + ex.getMessage());
        }
        return propertySchemaHashtable;
    }
    
    public Hashtable getCategorySchemaHashtable(final String s) {
        Hashtable categorySchemaHashtable = null;
        try {
            categorySchemaHashtable = this.m_ubProperties.getCategorySchemaHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getCategorySchemaHashtable(final String s, final String s2) {
        Hashtable categorySchemaHashtable = null;
        try {
            categorySchemaHashtable = this.m_ubProperties.getCategorySchemaHashtable(s, s2);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getCategorySchemaHashtable(final String s, final String[] array) {
        Hashtable categorySchemaHashtable = null;
        try {
            categorySchemaHashtable = this.m_ubProperties.getCategorySchemaHashtable(s, array);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategorySchemaHashtable failed: " + ex.getMessage());
        }
        return categorySchemaHashtable;
    }
    
    public Hashtable getCategoryAttributeHashtable(final String s) {
        Hashtable categoryAttributeHashtable = null;
        try {
            categoryAttributeHashtable = this.m_ubProperties.getCategoryAttributeHashtable(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getCategoryAttributeHashtable failed: " + ex.getMessage());
        }
        return categoryAttributeHashtable;
    }
    
    public Hashtable makePropertyValueHashtable(final String s) {
        Hashtable propertyValueHash = null;
        try {
            propertyValueHash = this.m_ubProperties.makePropertyValueHash(s);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, "getGroupAttributeHashtable failed: " + ex.getMessage());
        }
        return propertyValueHash;
    }
    
    public Vector validateProperties(final String[] array, final String s, final Hashtable hashtable, final Hashtable hashtable2) throws Exception {
        final Vector vector = new Vector();
        Vector validateProperties;
        try {
            validateProperties = this.m_ubProperties.validateProperties(array, s, hashtable, hashtable2);
        }
        catch (Exception ex) {
            ProLog.logd(this.PLUGIN_ID, 4, ex.getMessage());
            throw ex;
        }
        return validateProperties;
    }
    
    public String[] getNameServerInstances() {
        String[] nameServerInstances;
        try {
            nameServerInstances = PropMgrPlugin.getInstance().getNameServerInstances();
        }
        catch (Exception ex) {
            nameServerInstances = null;
        }
        return nameServerInstances;
    }
    
    public String[] getSSLAliasNames() {
        String[] array;
        try {
            final String[] sslAliasNames;
            array = (sslAliasNames = this.m_plugin.getSSLAliasNames());
            if (sslAliasNames != null) {
                for (int i = 0; i < sslAliasNames.length; ++i) {
                    final int index = sslAliasNames[i].indexOf(".pem");
                    if (index > 0) {
                        array[i] = sslAliasNames[i].substring(0, index);
                    }
                }
            }
        }
        catch (Exception ex) {
            array = null;
        }
        return array;
    }
    
    public Boolean nameUsed(final String s) {
        boolean groupExists;
        try {
            groupExists = this.m_ubProperties.groupExists(s);
        }
        catch (Exception ex) {
            groupExists = false;
        }
        return new Boolean(groupExists);
    }
    
    public String[] getAdminRoleNames() {
        String[] adminRoles;
        try {
            adminRoles = PropMgrPlugin.getInstance().getAdminRoles();
        }
        catch (Exception ex) {
            adminRoles = null;
        }
        return adminRoles;
    }
    
    public String[] getAdminRoleInstances() {
        String[] groups;
        try {
            groups = this.m_ubProperties.groups("AdminRole", true, false, false);
        }
        catch (Exception ex) {
            groups = null;
        }
        return groups;
    }
    
    public char[] getPreferenceProperties() {
        char[] preferenceStream;
        try {
            preferenceStream = PropMgrPlugin.getInstance().getPreferenceStream();
        }
        catch (Exception ex) {
            preferenceStream = null;
        }
        return preferenceStream;
    }
    
    public Boolean updatePropertyCollection(final String s, final char[] array) {
        boolean updatePropertyCollection;
        try {
            updatePropertyCollection = PropMgrPlugin.getInstance().updatePropertyCollection(s, array);
        }
        catch (Exception ex) {
            updatePropertyCollection = false;
        }
        return new Boolean(updatePropertyCollection);
    }
    
    public String[] validateOneProperty(final String s, final String s2, final String s3) {
        String[] errMsgList = null;
        try {
            errMsgList = this.m_propMgrUtils.validateOneProperty(s, s2, s3).getErrMsgList();
        }
        catch (Exception ex) {}
        return errMsgList;
    }
    
    public abstract Boolean createInstance(final String[] p0);
    
    public abstract Boolean deleteInstance(final String p0);
    
    public String getDataServerSrvrMinPort() {
        return ((Integer)DynamicPropertyValues.getDataServerSrvrMinPort("UBroker.OR.srvrMinPort")).toString();
    }
    
    public String getDataServerSrvrMaxPort() {
        return ((Integer)DynamicPropertyValues.getDataServerSrvrMaxPort("UBroker.OR.srvrMaxPort")).toString();
    }
    
    public Boolean createNameServerInstance(final String[] array) {
        boolean instance = false;
        final NSGuiPlugin nameServerPlugin = AbstractGuiPlugin.getNameServerPlugin();
        if (nameServerPlugin != null) {
            instance = nameServerPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteNameServerInstance(final String s) {
        boolean deleteInstance = false;
        final NSGuiPlugin nameServerPlugin = AbstractGuiPlugin.getNameServerPlugin();
        if (nameServerPlugin != null) {
            deleteInstance = nameServerPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        OpenEdgePluginComponent.DEFAULT_UB_PING_RETRY_COUNT = 1;
        OE_MANAGEMENT_INFO = new IManagementInfo[AbstractPluginComponent.BASE_MANAGEMENT_INFO.length + 52];
        int length = AbstractPluginComponent.BASE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(AbstractPluginComponent.BASE_MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, AbstractPluginComponent.BASE_MANAGEMENT_INFO.length);
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("InstanceNames", String[].class.getName(), "Instances", true, false);
        try {
            method = OpenEdgePluginComponent.class.getMethod("destroy", String.class);
        }
        catch (Exception ex) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Destroy (delete) the specified Open Edge instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("start", String.class);
        }
        catch (Exception ex2) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Start the specified Open Edge instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("stop", String.class);
        }
        catch (Exception ex3) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Stop the specified Open Edge instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getIsRunning", String.class);
        }
        catch (Exception ex4) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Returns the running state (boolean) of an instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("silentPingOnce", String.class);
        }
        catch (Exception ex5) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Returns the state (Long) after ping broker once.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("silentPingRetry", String.class, Integer.class);
        }
        catch (Exception ex6) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Returns the state (Long) after ping broker n times.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("nameUsed", String.class);
        }
        catch (Exception ex7) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Does the named instance already exist?", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("createInstance", String[].class);
        }
        catch (Exception ex8) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Add the named instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("deleteInstance", String.class);
        }
        catch (Exception ex9) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Delete the named instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("createNameServerInstance", String[].class);
        }
        catch (Exception ex10) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Add the named instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("deleteNameServerInstance", String.class);
        }
        catch (Exception ex11) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Delete the named instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getStatistics", String.class, String[].class);
        }
        catch (Exception ex12) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return from the specified instance, the set of statistics for the listed statistic names.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getStatisticSchema", String[].class);
        }
        catch (Exception ex13) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of statistic names.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("removeConfiguration", String.class);
        }
        catch (Exception ex14) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Remove the specified group and its associated properties.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("setConfiguration", String.class, Hashtable.class);
        }
        catch (Exception ex15) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set properties for the specified configuration (property) name.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getConfiguration", String.class);
        }
        catch (Exception ex16) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return a set of properties for the specified configuration (property) name.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getConfigurationSchema", String[].class);
        }
        catch (Exception ex17) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of configuration element names.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("putArrayProperty", String.class, String[].class);
        }
        catch (Exception ex18) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set the array of property values for the named property.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getArrayProperty", String.class);
        }
        catch (Exception ex19) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the list of values associated with the named property.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getBooleanProperty", String.class);
        }
        catch (Exception ex20) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the Boolean value associated with the named property.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getProperty", String.class);
        }
        catch (Exception ex21) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the String value associated with the named property.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getExpandedValueForProperty", String.class, String.class);
        }
        catch (Exception ex22) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the specified property value in its expanded form.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getEnvPropertyValues", String.class);
        }
        catch (Exception ex23) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the environment values for the specified instance.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("writeProperties", (Class[])new Class[0]);
        }
        catch (Exception ex24) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Commit all properties in memory to disk.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getGroupSchemaHashtable", String.class);
        }
        catch (Exception ex25) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for all groups.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getGroupAttributeHashtable", String.class);
        }
        catch (Exception ex26) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified group.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getPropertySchemaHashtable", String.class);
        }
        catch (Exception ex27) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property schema definition for the specified group.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getCategorySchemaHashtable", String.class);
        }
        catch (Exception ex28) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getCategorySchemaHashtable", String.class);
        }
        catch (Exception ex29) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getCategorySchemaHashtable", String.class);
        }
        catch (Exception ex30) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified category.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getCategoryAttributeHashtable", String.class);
        }
        catch (Exception ex31) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the attributes defined for the specified category.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("makePropertyValueHashtable", String.class);
        }
        catch (Exception ex32) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the property values defined for the specified group.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("nameExists", String.class);
        }
        catch (Exception ex33) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Returns true if the specified property group already exists.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("validateProperties", String[].class, String.class, Hashtable.class, Hashtable.class);
        }
        catch (Exception ex34) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Validate the provided values for the specified group?", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("validateOneProperty", String.class, String.class, String.class);
        }
        catch (Exception ex35) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Validate the provided property value.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getSSLAliasNames", (Class[])new Class[0]);
        }
        catch (Exception ex36) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get all known SSL alias names.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getNameServerInstances", (Class[])new Class[0]);
        }
        catch (Exception ex37) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get all NameSever instances defined.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getAdminRoleNames", (Class[])new Class[0]);
        }
        catch (Exception ex38) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get all admin role full group names.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getAdminRoleInstances", (Class[])new Class[0]);
        }
        catch (Exception ex39) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the admin role instances.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getPreferenceProperties", (Class[])new Class[0]);
        }
        catch (Exception ex40) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the perference properties.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getDataServerSrvrMinPort", (Class[])new Class[0]);
        }
        catch (Exception ex41) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the default srvrMinPort value for this platform.", method);
        try {
            method = OpenEdgePluginComponent.class.getMethod("getDataServerSrvrMaxPort", (Class[])new Class[0]);
        }
        catch (Exception ex42) {}
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the default srvrMaxPort value for this platform.", method);
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EStartServiceEvent", "Ubroker instance startup completed.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EStopServiceEvent", "Ubroker instance stop completed.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EUbrokerProcessActiveEvent", "Ubroker process is in active state.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAbnormalShutdownEvent", "Ubroker process was shutdown abnormally.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ContainerAddTreeNodeEvent", "Added a new Ubroker instance.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ContainerDeleteTreeNodeEvent", "Deleted an existing Ubroker instance.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EPMPWarmStartBeganEvent", "PropMgrPlugin started the warmstart process.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EPMPWarmStartFinishedEvent", "PropMgrPlugin finished the warmstart process.");
        OpenEdgePluginComponent.OE_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ELogFileNameChanged", "The named process' log file name has changed.");
    }
    
    private class StartRunnableObject implements Runnable
    {
        ToolRemoteCmdDescriptor m_cmd;
        
        public StartRunnableObject(final ToolRemoteCmdDescriptor cmd) {
            this.m_cmd = null;
            this.m_cmd = cmd;
        }
        
        public void run() {
            OpenEdgePluginComponent.this.m_plugin.startSvcProcess(this.m_cmd);
        }
    }
    
    private class StopRunnableObject implements Runnable
    {
        ToolRemoteCmdDescriptor m_cmd;
        
        public StopRunnableObject(final ToolRemoteCmdDescriptor cmd) {
            this.m_cmd = null;
            this.m_cmd = cmd;
        }
        
        public void run() {
            OpenEdgePluginComponent.this.m_plugin.stopSvcProcess(this.m_cmd);
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import java.lang.reflect.Method;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import com.progress.ubroker.tools.UBGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.progress.ubroker.util.getServerCnt;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.property.EPropertiesChanged;
import com.progress.ubroker.tools.UBTRemoteObject;
import java.util.Hashtable;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.ubroker.tools.UBRemoteObject;

public class UbrokerPluginComponent extends OpenEdgePluginComponent
{
    protected UBRemoteObject m_ubPlugin;
    protected static final IManagementInfo[] UB_MANAGEMENT_INFO;
    public static final String UBROKER_PROP_PREFIX = "TBD";
    public static final String UBROKER_METRIC_PREFIX = "ubroker.metric.vst.";
    
    public UbrokerPluginComponent() {
        this.m_ubPlugin = null;
    }
    
    public IManagementInfo[] getManagementInfo() {
        return UbrokerPluginComponent.UB_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        super.init(o, frameworkComponentContext);
        this.m_ubPlugin = (UBRemoteObject)super.m_plugin;
        super.PLUGIN_ID = super.m_plugin.m_personality;
        if (super.m_plugin.m_personality.equals("WebSpeed")) {
            super.serverType = 1;
        }
        else if (super.m_plugin.m_personality.equals("AppServer")) {
            super.serverType = 0;
        }
    }
    
    public void setConfiguration(final String s, final Hashtable hashtable) throws Exception {
        super.setConfiguration(s, hashtable);
        try {
            final UBTRemoteObject ubtRemoteObject = UBTRemoteObject.findUBTRemoteObject(s);
            final IEventBroker eventBroker = this.m_ubPlugin.getEventBroker();
            if (ubtRemoteObject != null && eventBroker != null) {
                eventBroker.postEvent(new EPropertiesChanged(ubtRemoteObject.stub(), null));
            }
        }
        catch (Throwable t) {}
    }
    
    public Integer getLicensedServerCount() {
        int maxServers = 0;
        try {
            maxServers = new getServerCnt().getMaxServers(0, super.serverType);
        }
        catch (Exception ex) {}
        return new Integer(maxServers);
    }
    
    public Integer getBrokerSettingCollectStatsData(final String str) {
        return new Integer(this.m_ubPlugin.getBrokerCollectDataState(this.getPropertyGroup() + "." + str));
    }
    
    public void addServers(final String str, final Integer n) {
        new Thread(new AddServerRunnableObject(this.getPropertyGroup() + "." + str, n)).start();
    }
    
    public void trimServers(final String str, final Integer n) {
        new Thread(new TrimServerRunnableObject(this.getPropertyGroup() + "." + str, n)).start();
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "ubroker.metric.vst.";
    }
    
    public Boolean createAppServerInstance(final String[] array) {
        boolean instance = false;
        final UBGuiPlugin appServerPlugin = AbstractGuiPlugin.getAppServerPlugin();
        if (appServerPlugin != null) {
            instance = appServerPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean createWebSpeedInstance(final String[] array) {
        boolean instance = false;
        final UBGuiPlugin webSpeedPlugin = AbstractGuiPlugin.getWebSpeedPlugin();
        if (webSpeedPlugin != null) {
            instance = webSpeedPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteAppServerInstance(final String s) {
        boolean deleteInstance = false;
        final UBGuiPlugin appServerPlugin = AbstractGuiPlugin.getAppServerPlugin();
        if (appServerPlugin != null) {
            deleteInstance = appServerPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    public Boolean deleteWebSpeedInstance(final String s) {
        boolean deleteInstance = false;
        final UBGuiPlugin webSpeedPlugin = AbstractGuiPlugin.getWebSpeedPlugin();
        if (webSpeedPlugin != null) {
            deleteInstance = webSpeedPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final UBGuiPlugin ubGuiPlugin = (super.serverType == 1) ? AbstractGuiPlugin.getWebSpeedPlugin() : AbstractGuiPlugin.getAppServerPlugin();
        if (ubGuiPlugin != null) {
            instance = ubGuiPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final UBGuiPlugin ubGuiPlugin = (super.serverType == 1) ? AbstractGuiPlugin.getWebSpeedPlugin() : AbstractGuiPlugin.getAppServerPlugin();
        if (ubGuiPlugin != null) {
            deleteInstance = ubGuiPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        UB_MANAGEMENT_INFO = new IManagementInfo[OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length + 13];
        int length = OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, UbrokerPluginComponent.UB_MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length);
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createAttributeInfo("LicensedServerCount", Integer.class.getName(), "Available licensed server count", true, false);
        try {
            method = UbrokerPluginComponent.class.getMethod("addServers", String.class, Integer.class);
        }
        catch (Exception ex) {}
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Add a number of servers for the specific ubroker instance", method);
        try {
            method = UbrokerPluginComponent.class.getMethod("trimServers", String.class, Integer.class);
        }
        catch (Exception ex2) {}
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Trim a number of servers for the specific ubroker instance", method);
        try {
            method = UbrokerPluginComponent.class.getMethod("getBrokerSettingCollectStatsData", String.class);
        }
        catch (Exception ex3) {}
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the broker state of collectStatsData", method);
        try {
            method = UbrokerPluginComponent.class.getMethod("createWebSpeedInstance", String[].class);
        }
        catch (Exception ex4) {}
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Add the named instance.", method);
        try {
            method = UbrokerPluginComponent.class.getMethod("deleteWebSpeedInstance", String.class);
        }
        catch (Exception ex5) {}
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Delete the named instance.", method);
        try {
            method = UbrokerPluginComponent.class.getMethod("createAppServerInstance", String[].class);
        }
        catch (Exception ex6) {}
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Add the named instance.", method);
        try {
            method = UbrokerPluginComponent.class.getMethod("deleteAppServerInstance", String.class);
        }
        catch (Exception ex7) {}
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Delete the named instance.", method);
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAddAgentsEvent", "Add server with return number completed.");
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ETrimAgentsEvent", "Trim server with return number completed.");
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EAbnormalShutdownServerEvent", "Ubroker server/agent process was shutdown abnormally.");
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ENameServerUnavailableEvent", "Ubroker failed to reach the NameServer.");
        UbrokerPluginComponent.UB_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "EUbrokerClientAbnormalDisconnectEvent", "Ubroker detects client disconnected abnormally.");
    }
    
    private class AddServerRunnableObject implements Runnable
    {
        String m_path;
        int m_num;
        
        public AddServerRunnableObject(final String path, final Integer n) {
            this.m_path = null;
            this.m_num = -1;
            this.m_path = path;
            this.m_num = n;
        }
        
        public void run() {
            UbrokerPluginComponent.this.m_ubPlugin.addNewSrvrsRetNum(this.m_path, this.m_num);
        }
    }
    
    private class TrimServerRunnableObject implements Runnable
    {
        String m_path;
        int m_num;
        
        public TrimServerRunnableObject(final String path, final Integer n) {
            this.m_path = null;
            this.m_num = -1;
            this.m_path = path;
            this.m_num = n;
        }
        
        public void run() {
            UbrokerPluginComponent.this.m_ubPlugin.trimSrvrByRetNum(this.m_path, this.m_num);
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import java.lang.reflect.Method;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.info.InfoFactory;
import com.progress.mf.AbstractPluginComponent;
import com.progress.ubroker.tools.NSGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.nameserver.NSStatisticSchema;
import com.progress.ubroker.tools.UBToolsMsg;
import java.util.Map;
import com.progress.common.networkevents.IEventBroker;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.property.EPropertiesChanged;
import com.progress.ubroker.tools.NSTRemoteObject;
import java.util.Hashtable;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.ubroker.tools.NSRemoteObject;

public class NameServerPluginComponent extends OpenEdgePluginComponent
{
    protected NSRemoteObject m_nsPlugin;
    private static final IManagementInfo[] MANAGEMENT_INFO;
    public static final String NAMESERVER_PROP_PREFIX = "TBD";
    public static final String NAMESERVER_METRIC_PREFIX = "nameserver.metric.vst.";
    
    public NameServerPluginComponent() {
        this.m_nsPlugin = null;
    }
    
    public IManagementInfo[] getManagementInfo() {
        return NameServerPluginComponent.MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
            this.m_nsPlugin = (NSRemoteObject)super.m_plugin;
        }
    }
    
    public void setConfiguration(final String s, final Hashtable hashtable) throws Exception {
        super.setConfiguration(s, hashtable);
        try {
            final NSTRemoteObject nstRemoteObject = NSTRemoteObject.findNSTRemoteObject(s);
            final IEventBroker eventBroker = this.m_nsPlugin.getEventBroker();
            if (nstRemoteObject != null && eventBroker != null) {
                eventBroker.postEvent(new EPropertiesChanged(nstRemoteObject.stub(), null));
            }
        }
        catch (Throwable t) {}
    }
    
    public Hashtable getStatistics(final String s, final String[] array) {
        final Hashtable hashtable = new Hashtable();
        try {
            if (s == null || s.length() <= 0 || array == null || array[0] == null) {
                return hashtable;
            }
            final String string = this.getPropertyGroup() + "." + s;
            for (int i = 0; i < array.length; ++i) {
                final Hashtable data = this.m_nsPlugin.getData(string, new String[] { array[i] });
                if (data != null) {
                    hashtable.putAll(data);
                }
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Unable to contact the NameServer to obtain statistics. NameServer: " + s);
        }
        return hashtable;
    }
    
    public Hashtable getStatisticSchema(final String[] array) {
        final Hashtable hashtable = new Hashtable();
        if (array != null && array[0] != null) {
            for (int i = 0; i < array.length; ++i) {
                final Hashtable schema = NSStatisticSchema.getSchema(array[i]);
                if (schema != null) {
                    hashtable.putAll(schema);
                }
            }
        }
        return hashtable;
    }
    
    public Boolean nsIsRunning(final String s) {
        Boolean b = new Boolean(false);
        if (this.silentPingOnce(s) == 1L) {
            b = new Boolean(true);
        }
        return b;
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final NSGuiPlugin nameServerPlugin = AbstractGuiPlugin.getNameServerPlugin();
        if (nameServerPlugin != null) {
            instance = nameServerPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final NSGuiPlugin nameServerPlugin = AbstractGuiPlugin.getNameServerPlugin();
        if (nameServerPlugin != null) {
            deleteInstance = nameServerPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "nameserver.metric.vst.";
    }
    
    static {
        MANAGEMENT_INFO = new IManagementInfo[OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length + 9];
        int length = OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, NameServerPluginComponent.MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length);
        try {
            method = NameServerPluginComponent.class.getMethod("nsIsRunning", String.class);
        }
        catch (Exception ex) {}
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Returns true if specified name server instance is running.", method);
        try {
            method = NameServerPluginComponent.class.getMethod("getStatistics", String.class, String[].class);
        }
        catch (Exception ex2) {}
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return from the specified instance, the set of statistics for the listed statistic names.", method);
        try {
            method = NameServerPluginComponent.class.getMethod("getStatisticSchema", String[].class);
        }
        catch (Exception ex3) {}
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Return the schema definition for the specified list of statistic names.", method);
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ENSBrokerTimeoutEvent", "The registered Broker is not responding.");
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ENSDuplicateBrokerUUIDEvent", "Erroneous UUID received from a second Broker.");
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ENSAppServiceNotFoundEvent", "Application Service requested by client not found.");
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ENSClientRequestRejectedEvent", "The client request was rejected due to an incorrect message header.");
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ENSBrokerRegistrationFailureEvent", "The Broker registration failed.");
        NameServerPluginComponent.MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "ENSReregisteredBrokerEvent", "The Broker has been reregistered by the NameServer for consistency.");
    }
}

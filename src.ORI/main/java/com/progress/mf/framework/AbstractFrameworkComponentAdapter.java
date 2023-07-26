// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.framework;

import com.sonicsw.mf.common.runtime.IIdentity;
import com.sonicsw.mf.common.IComponentContext;
import com.sonicsw.mf.common.AbstractComponent;
import java.util.Hashtable;
import com.sonicsw.mf.framework.INotificationHandler;
import com.progress.common.networkevents.IEventObject;
import com.progress.mf.AbstractPluginNotification;
import com.sonicsw.mf.common.runtime.INotification;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.mf.AbstractPluginContext;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import java.util.HashMap;
import com.progress.common.property.PropertyManager;
import com.progress.mf.common.IDataProvider;
import com.sonicsw.mf.framework.AbstractFrameworkComponent;

public abstract class AbstractFrameworkComponentAdapter extends AbstractFrameworkComponent implements IFrameworkComponentAdapter, IDataProvider
{
    protected Object m_pluginObject;
    protected PropertyManager m_propertyManager;
    protected boolean m_initialized;
    private boolean m_localOnly;
    private HashMap m_oemNotificaionSources;
    private boolean TRACE_NOTIFICATIONS;
    public static final String EVENT_DELIMITER = ";";
    
    public AbstractFrameworkComponentAdapter() {
        this.m_pluginObject = null;
        this.m_propertyManager = null;
        this.m_initialized = false;
        this.m_localOnly = false;
        this.TRACE_NOTIFICATIONS = Boolean.getBoolean("traceFathomNotifications");
    }
    
    public void init(final Object o) {
        this.init(o, super.m_frameworkContext);
    }
    
    public void init(final Object pluginObject, final IFrameworkComponentContext frameworkComponentContext) {
        if (!this.m_initialized && pluginObject != null) {
            this.m_pluginObject = pluginObject;
            if (frameworkComponentContext != null) {
                super.m_frameworkContext = frameworkComponentContext;
                ((AbstractComponent)this).m_context = (IComponentContext)frameworkComponentContext;
                this.m_initialized = true;
                if (((AbstractComponent)this).m_context instanceof AbstractPluginContext) {
                    this.m_localOnly = true;
                }
            }
        }
    }
    
    public INotification createNotification(final INotificationEvent eventObject) {
        Object notification = null;
        try {
            if (eventObject != null) {
                notification = this.createNotification(eventObject.getCategory(), eventObject.getSubCategory(), eventObject.getNotificationName(), eventObject.getSeverityLevel());
                if (notification != null) {
                    final Object eventData = eventObject.getEventData();
                    eventObject.getErrorString();
                    ((INotification)notification).setAttribute("eventData", eventData);
                    ((INotification)notification).setAttribute("errorString", eventData);
                    ((INotification)notification).setAttribute("description", (Object)eventObject.description());
                    ((INotification)notification).setAttribute("eventType", (Object)eventObject.eventTypeString());
                    ((INotification)notification).setAttribute("isExpedite", (Object)new Boolean(eventObject.expedite()));
                    ((INotification)notification).setAttribute("issuerName", (Object)eventObject.issuerName());
                    ((INotification)notification).setAttribute("timeIssued", (Object)eventObject.timeIssued());
                    ((INotification)notification).setAttribute("nameString", (Object)eventObject.toString());
                    if (notification instanceof AbstractPluginNotification) {
                        ((AbstractPluginNotification)notification).setEventObject(eventObject);
                    }
                }
            }
        }
        catch (Exception ex) {}
        return (INotification)notification;
    }
    
    public void sendNotification(final INotificationEvent notificationEvent) {
        ((AbstractComponent)this).m_context.sendNotification(this.createNotification(notificationEvent));
    }
    
    public String getVersion() {
        String s = null;
        if (this.m_pluginObject != null) {
            s = new String("9.9xyz");
        }
        return s;
    }
    
    public INotification createNotification(final short n, final String s, final String s2, final int n2) {
        return ((AbstractComponent)this).m_context.createNotification(n, s, s2, n2);
    }
    
    public void sendNotification(final INotification notification) {
        ((AbstractComponent)this).m_context.sendNotification(notification);
    }
    
    public void logMessage(final String s, final int n) {
        ((AbstractComponent)this).m_context.logMessage(s, n);
    }
    
    public void logMessage(final String s, final Throwable t, final int n) {
        ((AbstractComponent)this).m_context.logMessage(s, t, n);
    }
    
    public void logMessage(final Throwable t, final int n) {
        ((AbstractComponent)this).m_context.logMessage(t, n);
    }
    
    public boolean registerErrorCondition(final String s, final int n) {
        return ((AbstractComponent)this).m_context.registerErrorCondition(s, n);
    }
    
    public void clearErrorCondition() {
        ((AbstractComponent)this).m_context.clearErrorCondition();
    }
    
    public synchronized void start() {
        super.start();
    }
    
    public synchronized void stop() {
        super.stop();
    }
    
    public void destroy() {
        super.destroy();
    }
    
    public void addNotificationHandler(final String s, final INotificationHandler key, final String[] value) {
        if (this.m_oemNotificaionSources == null) {
            this.m_oemNotificaionSources = new HashMap();
        }
        synchronized (this.m_oemNotificaionSources) {
            HashMap<INotificationHandler, String[]> value2 = this.m_oemNotificaionSources.get(s);
            if (value2 == null) {
                value2 = new HashMap<INotificationHandler, String[]>();
                this.m_oemNotificaionSources.put(s, value2);
            }
            value2.put(key, value);
        }
        super.m_frameworkContext.addNotificationHandler(s, key, value);
    }
    
    public void removeNotificationHandler(final INotificationHandler notificationHandler) {
        this.removeNotificationHandler(((IComponentContext)super.m_frameworkContext).getComponentName().getCanonicalName(), notificationHandler);
    }
    
    public void removeNotificationHandler(final String key, final INotificationHandler key2) {
        if (this.m_oemNotificaionSources != null) {
            synchronized (this.m_oemNotificaionSources) {
                final HashMap hashMap = this.m_oemNotificaionSources.get(key);
                if (hashMap != null) {
                    hashMap.remove(key2);
                }
            }
        }
        super.m_frameworkContext.removeNotificationHandler(key, key2);
    }
    
    public void removeAllNotificationHandlers() {
        if (this.m_oemNotificaionSources != null) {
            synchronized (this.m_oemNotificaionSources) {
                final Object[] array = this.m_oemNotificaionSources.keySet().toArray();
                for (int i = 0; i < array.length; ++i) {
                    final HashMap<Object, String[]> hashMap = this.m_oemNotificaionSources.get(array[i]);
                    if (hashMap != null) {
                        final Object[] array2 = hashMap.keySet().toArray();
                        for (int j = 0; j < array2.length; ++j) {
                            if (hashMap.get(array2[j]) != null) {
                                hashMap.remove(array2[j]);
                                super.m_frameworkContext.removeNotificationHandler((String)array[i], (INotificationHandler)array2[j]);
                            }
                        }
                    }
                    this.m_oemNotificaionSources.remove(array[i]);
                }
            }
        }
    }
    
    public void removeContainerNotificationHandlers(final String str) {
        if (this.m_oemNotificaionSources != null) {
            synchronized (this.m_oemNotificaionSources) {
                final Object[] array = this.m_oemNotificaionSources.keySet().toArray();
                for (int i = 0; i < array.length; ++i) {
                    if (((String)array[i]).startsWith("Fathom1." + str + ":")) {
                        final HashMap<Object, String[]> hashMap = this.m_oemNotificaionSources.get(array[i]);
                        if (hashMap != null) {
                            final Object[] array2 = hashMap.keySet().toArray();
                            for (int j = 0; j < array2.length; ++j) {
                                if (hashMap.get(array2[j]) != null) {
                                    hashMap.remove(array2[j]);
                                    super.m_frameworkContext.removeNotificationHandler((String)array[i], (INotificationHandler)array2[j]);
                                }
                            }
                        }
                        this.m_oemNotificaionSources.remove(array[i]);
                    }
                }
            }
        }
    }
    
    public void handleNotification(final INotification notification) {
        if (this.m_oemNotificaionSources != null) {
            if (this.TRACE_NOTIFICATIONS) {
                String str;
                if (notification instanceof AbstractPluginNotification) {
                    str = ((AbstractPluginNotification)notification).getCanonicalName();
                }
                else {
                    str = ((IIdentity)notification.getSourceIdentity()).getCanonicalName();
                }
                System.out.println("[" + this.getCanonicalName() + "] Event: " + str + " " + notification.getEventName());
            }
            synchronized (this.m_oemNotificaionSources) {
                String key;
                if (notification instanceof AbstractPluginNotification) {
                    key = ((AbstractPluginNotification)notification).getCanonicalName();
                }
                else {
                    key = ((IIdentity)notification.getSourceIdentity()).getCanonicalName();
                }
                final HashMap<Object, String[]> hashMap = this.m_oemNotificaionSources.get(key);
                if (hashMap != null) {
                    final Object[] array = hashMap.keySet().toArray();
                    for (int i = 0; i < array.length; ++i) {
                        final String[] array2 = hashMap.get(array[i]);
                        if (array2 != null) {
                            for (int j = 0; j < array2.length; ++j) {
                                if (array2[j] != null && array2[j].equals(notification.getType())) {
                                    ((INotificationHandler)array[i]).handleNotification(notification);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

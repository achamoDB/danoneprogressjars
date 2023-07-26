// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf;

import com.sonicsw.mf.common.config.IAttributeSet;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import com.sonicsw.mf.common.metrics.manager.IMetricsRegistrar;
import com.sonicsw.mf.common.metrics.IMetricInfo;
import java.util.Date;
import com.sonicsw.mf.common.config.IElement;
import com.sonicsw.mf.common.runtime.INotification;
import com.sonicsw.mf.common.runtime.ICollectiveOpStatus;
import java.lang.reflect.Method;
import com.progress.mf.runtime.ManagementPlugin;
import com.sonicsw.mf.framework.IContainer;
import com.sonicsw.mf.common.runtime.impl.CanonicalName;
import java.util.HashMap;
import com.sonicsw.mf.common.runtime.ICanonicalName;
import com.sonicsw.mf.framework.INotificationHandler;
import java.util.Hashtable;
import com.sonicsw.mf.framework.IFrameworkComponentContext;

public abstract class AbstractPluginContext implements IFrameworkComponentContext
{
    protected boolean m_initialized;
    protected AbstractPluginComponent m_pluginComponent;
    protected Hashtable m_pluginComponents;
    protected INotificationHandler m_handler;
    protected String[] m_oemNotificaionTypes;
    protected ICanonicalName m_componentName;
    protected HashMap m_oemNotificaionSubscriptions;
    protected Long m_oemNotificaionSubscriptionTimeout;
    
    public AbstractPluginContext() {
        this.m_initialized = false;
        this.m_pluginComponent = null;
        this.m_pluginComponents = new Hashtable();
        this.m_handler = null;
        this.m_oemNotificaionTypes = null;
        this.m_componentName = null;
        this.m_oemNotificaionSubscriptionTimeout = null;
    }
    
    public void init(final AbstractPluginComponent pluginComponent) {
        if (!this.m_initialized && pluginComponent != null) {
            this.m_pluginComponent = pluginComponent;
            this.m_componentName = (ICanonicalName)new CanonicalName("local", "localhost", ((IManagedPlugin)this.m_pluginComponent.getPluginObject()).getPluginName());
            this.m_initialized = true;
        }
    }
    
    public IContainer getContainer() {
        return null;
    }
    
    public Object invoke(final String s, final String s2, final Object[] args, final String[] array, final boolean b, final long n) throws Exception {
        Object invoke = null;
        final Object targetPlugin = ManagementPlugin.get().findTargetPlugin(s);
        final Method method = this.findMethod(targetPlugin, s2, args, array);
        if (targetPlugin != null && method != null) {
            invoke = method.invoke(targetPlugin, args);
        }
        return invoke;
    }
    
    public ICollectiveOpStatus invoke(final String[] array, final String s, final Object[] array2, final String[] array3, final boolean b, final long n) throws Exception {
        return null;
    }
    
    protected Method findMethod(final Object o, final String name, final Object[] array, final String[] array2) throws Exception {
        final Class[] parameterTypes = new Class[array2.length];
        for (int i = 0; i < array2.length; ++i) {
            try {
                parameterTypes[i] = Class.forName(array2[i]);
            }
            catch (ClassNotFoundException ex) {
                parameterTypes[i] = array[i].getClass();
            }
        }
        return o.getClass().getMethod(name, (Class<?>[])parameterTypes);
    }
    
    public String[] setAttributes(final String s, final String[] array, final Object[] array2, final boolean b) throws Exception {
        return new String[0];
    }
    
    public Object[] getAttributeValues(final String s, final String[] array) throws Exception {
        Object[] array2 = null;
        final Object[] array3 = new Object[0];
        final String[] array4 = new String[0];
        if (array != null && array.length > 0) {
            array2 = new Object[array.length];
            for (int i = 0; i < array.length; ++i) {
                array2[i] = this.invoke(s, "get" + array[i], array3, array4, true, 0L);
            }
        }
        return array2;
    }
    
    public void forwardNotification(final INotification notification) {
    }
    
    public void addNotificationHandler(final String s, final INotificationHandler notificationHandler, final String[] array) {
        AbstractPluginComponent abstractPluginComponent = null;
        try {
            abstractPluginComponent = (AbstractPluginComponent)ManagementPlugin.get().findTargetPlugin(s);
        }
        catch (Exception ex) {}
        if (abstractPluginComponent != null) {
            ((AbstractPluginContext)abstractPluginComponent.getContext()).addNotificationSubscription(this.m_pluginComponent, notificationHandler, array);
        }
    }
    
    public void removeNotificationHandler(final String s, final INotificationHandler notificationHandler) {
        AbstractPluginComponent abstractPluginComponent = null;
        try {
            abstractPluginComponent = (AbstractPluginComponent)ManagementPlugin.get().findTargetPlugin(s);
        }
        catch (Exception ex) {}
        if (abstractPluginComponent != null) {
            ((AbstractPluginContext)abstractPluginComponent.getContext()).removeNotificationSubscription(this.m_pluginComponent, notificationHandler);
        }
    }
    
    public ICanonicalName getComponentName() {
        return this.m_componentName;
    }
    
    public IElement getConfiguration(final boolean b) {
        return null;
    }
    
    public IElement getConfiguration(final String s, final boolean b) {
        return null;
    }
    
    public IElement[] getConfigurations(final String[] array, final boolean[] array2) {
        return null;
    }
    
    public IElement[] getConfigurations(final String s, final boolean b) {
        return null;
    }
    
    public void sendNotification(final INotification notification) {
        if (this.m_oemNotificaionSubscriptions != null) {
            final AbstractPluginComponent[] array = (AbstractPluginComponent[])this.m_oemNotificaionSubscriptions.keySet().toArray(new AbstractPluginComponent[0]);
            for (int i = 0; i < array.length; ++i) {
                array[i].handleNotification(notification);
            }
        }
    }
    
    public void addNotificationSubscription(final AbstractPluginComponent abstractPluginComponent, final INotificationHandler key, final String[] value) {
        if (this.m_oemNotificaionSubscriptions == null) {
            this.m_oemNotificaionSubscriptions = new HashMap();
        }
        synchronized (this.m_oemNotificaionSubscriptions) {
            HashMap<INotificationHandler, String[]> value2 = this.m_oemNotificaionSubscriptions.get(abstractPluginComponent);
            if (value2 == null) {
                value2 = new HashMap<INotificationHandler, String[]>();
                this.m_oemNotificaionSubscriptions.put(abstractPluginComponent, value2);
            }
            value2.put(key, value);
        }
    }
    
    public void removeNotificationSubscription(final AbstractPluginComponent key, final INotificationHandler key2) {
        if (this.m_oemNotificaionSubscriptions == null) {
            this.m_oemNotificaionSubscriptions = new HashMap();
        }
        if (this.m_oemNotificaionSubscriptions != null) {
            synchronized (this.m_oemNotificaionSubscriptions) {
                final HashMap hashMap = this.m_oemNotificaionSubscriptions.get(key);
                if (hashMap != null) {
                    hashMap.remove(key2);
                }
            }
        }
    }
    
    public void logMessage(final String s, final int n) {
    }
    
    public void logMessage(final String s, final Throwable t, final int n) {
    }
    
    public void logMessage(final Throwable t, final int n) {
    }
    
    public boolean registerErrorCondition(final String s, final int n) {
        return false;
    }
    
    public void clearErrorCondition() {
    }
    
    public void scheduleTask(final Runnable runnable, final Date date) {
    }
    
    public void cancelTask(final Runnable runnable) {
    }
    
    public IMetricsRegistrar initMetricsManagement(final IMetricInfo[] array) {
        return null;
    }
    
    public void fireAttributeChangeHandlers() {
    }
    
    public void loadComponent(final String s, final String s2, final String s3, final boolean b, final int n) throws Exception {
    }
    
    public void unloadComponent(final String s) throws Exception {
    }
    
    public void setNotificationSubscriptionTimeout(final Long oemNotificaionSubscriptionTimeout) {
        this.m_oemNotificaionSubscriptionTimeout = oemNotificaionSubscriptionTimeout;
    }
    
    public Long getNotificationSubscriptionTimeout() {
        return this.m_oemNotificaionSubscriptionTimeout;
    }
    
    public void makeGlobalComponentUniquenessCall(final String s, final String s2) throws Exception {
    }
    
    public void makeGlobalComponentUniquenessCall(final String s, final String s2, final String s3) throws Exception {
    }
    
    public void writeLibrary(final String s, final InputStream inputStream) throws IOException {
    }
    
    public IElement getFSConfiguration(final String s, final boolean b) {
        return null;
    }
    
    public IElement registerFileChangeInterest(final String s) {
        return null;
    }
    
    public IElement[] getFSConfigurations(final String[] array, final boolean b) {
        return null;
    }
    
    public void addSharedPath(final URL url) throws UnsupportedOperationException {
    }
    
    public void addSharedClassname(final String s) throws UnsupportedOperationException {
    }
    
    public IAttributeSet getDeploymentParameters() {
        return null;
    }
}

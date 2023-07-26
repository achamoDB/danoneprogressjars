// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf;

import com.sonicsw.mf.common.AbstractComponent;
import com.sonicsw.mf.common.info.InfoFactory;
import com.sonicsw.mf.common.runtime.IComponentState;
import com.sonicsw.mf.common.runtime.INotification;
import java.util.Hashtable;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.mf.framework.AbstractFrameworkComponentAdapter;

public abstract class AbstractPluginComponent extends AbstractFrameworkComponentAdapter
{
    public static final String PLUGIN_ONLINE_TEXT = "Plugin_Online";
    protected static String m_propertySearchPrefix;
    protected static String m_metricSearchPrefix;
    protected static final IManagementInfo[] BASE_MANAGEMENT_INFO;
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
            this.initSearchPrefix();
        }
    }
    
    public Hashtable getStatistics(final String[] array) {
        return null;
    }
    
    public Hashtable getConfiguration(final String s, final String s2) {
        return null;
    }
    
    public void setConfiguration(final String s, final String s2, final Hashtable hashtable) throws Exception {
        throw new Exception("Method not implemented");
    }
    
    public Hashtable getThresholds() {
        return null;
    }
    
    public void setThresholds(final Hashtable hashtable) throws Exception {
        throw new Exception("Method not implemented");
    }
    
    protected String getpropertySearchPrefix() {
        return AbstractPluginComponent.m_propertySearchPrefix;
    }
    
    protected String getMetricSearchPrefix() {
        return AbstractPluginComponent.m_metricSearchPrefix;
    }
    
    protected abstract void initSearchPrefix();
    
    public IFrameworkComponentContext getContext() {
        IFrameworkComponentContext frameworkContext = null;
        if (super.m_initialized) {
            frameworkContext = super.m_frameworkContext;
        }
        return frameworkContext;
    }
    
    public Object getPluginObject() {
        return super.m_pluginObject;
    }
    
    public String getCanonicalName() {
        String canonicalName = null;
        if (super.m_initialized) {
            canonicalName = ((AbstractComponent)this).m_context.getComponentName().getCanonicalName();
        }
        return canonicalName;
    }
    
    public void setStarting() {
        ((AbstractComponent)this).setState((short)2);
        this.sendNotification(this.createNotification((short)0, INotification.SUBCATEGORY_TEXT[0], IComponentState.STATE_TEXT[2], 3));
    }
    
    public void setOnline() {
        ((AbstractComponent)this).setState((short)3);
        this.sendNotification(this.createNotification((short)2, INotification.SUBCATEGORY_TEXT[0], "Plugin_Online", 3));
    }
    
    public void setStopping() {
        ((AbstractComponent)this).setState((short)4);
        this.sendNotification(this.createNotification((short)0, INotification.SUBCATEGORY_TEXT[0], IComponentState.STATE_TEXT[4], 3));
    }
    
    public void setOffline() {
        ((AbstractComponent)this).setState((short)1);
    }
    
    static {
        AbstractPluginComponent.m_propertySearchPrefix = null;
        AbstractPluginComponent.m_metricSearchPrefix = null;
        BASE_MANAGEMENT_INFO = new IManagementInfo[5];
        int n = 0;
        AbstractPluginComponent.BASE_MANAGEMENT_INFO[n++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)0, INotification.SUBCATEGORY_TEXT[0], IComponentState.STATE_TEXT[2], "Adminserver plugin starting.");
        AbstractPluginComponent.BASE_MANAGEMENT_INFO[n++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)0, INotification.SUBCATEGORY_TEXT[0], IComponentState.STATE_TEXT[3], "Adminserver plugin online.");
        AbstractPluginComponent.BASE_MANAGEMENT_INFO[n++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)2, INotification.SUBCATEGORY_TEXT[0], "Plugin_Online", "Adminserver plugin online (application).");
        AbstractPluginComponent.BASE_MANAGEMENT_INFO[n++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)0, INotification.SUBCATEGORY_TEXT[0], IComponentState.STATE_TEXT[4], "Adminserver plugin stopping.");
        AbstractPluginComponent.BASE_MANAGEMENT_INFO[n++] = (IManagementInfo)InfoFactory.createNotificationInfo((short)0, INotification.SUBCATEGORY_TEXT[0], IComponentState.STATE_TEXT[1], "Adminserver plugin offline.");
    }
}

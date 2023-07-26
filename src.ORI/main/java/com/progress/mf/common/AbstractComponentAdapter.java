// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.common;

import com.sonicsw.mf.common.IComponentContext;
import java.util.Hashtable;
import com.sonicsw.mf.common.runtime.INotification;
import com.progress.common.networkevents.INotificationEvent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.progress.common.property.PropertyManager;
import com.sonicsw.mf.common.AbstractComponent;

public abstract class AbstractComponentAdapter extends AbstractComponent implements IComponentAdapter, IDataProvider
{
    protected Object m_pluginObject;
    protected PropertyManager m_propertyManager;
    protected IFrameworkComponentContext m_frameworkContext;
    protected boolean m_initialized;
    
    public AbstractComponentAdapter() {
        this.m_pluginObject = null;
        this.m_propertyManager = null;
        this.m_frameworkContext = null;
        this.m_initialized = false;
    }
    
    public void init(final Object pluginObject, final IFrameworkComponentContext frameworkContext) {
        if (!this.m_initialized) {
            if (pluginObject != null) {
                this.m_pluginObject = pluginObject;
            }
            if (frameworkContext != null) {
                this.m_frameworkContext = frameworkContext;
            }
            if (pluginObject != null && frameworkContext != null) {
                this.m_initialized = true;
            }
        }
    }
    
    public void sendNotification(final INotificationEvent notificationEvent) {
        this.sendNotification(this.createNotification(notificationEvent));
    }
    
    public String getVersion() {
        String s = null;
        if (this.m_pluginObject != null) {
            s = new String("9.9xyz");
        }
        return s;
    }
    
    public INotification createNotification(final short n, final String s, final String s2, final int n2) {
        return ((IComponentContext)this.m_frameworkContext).createNotification(n, s, s2, n2);
    }
    
    public void sendNotification(final INotification notification) {
        super.m_context.sendNotification(notification);
    }
    
    public void logMessage(final String s, final int n) {
        super.m_context.logMessage(s, n);
    }
    
    public void logMessage(final String s, final Throwable t, final int n) {
        super.m_context.logMessage(s, t, n);
    }
    
    public void logMessage(final Throwable t, final int n) {
        super.m_context.logMessage(t, n);
    }
    
    public boolean registerErrorCondition(final String s, final int n) {
        return super.m_context.registerErrorCondition(s, n);
    }
    
    public void clearErrorCondition() {
        super.m_context.clearErrorCondition();
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
}

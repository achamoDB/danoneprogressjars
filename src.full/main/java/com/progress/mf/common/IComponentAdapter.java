// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.common;

import java.util.Hashtable;
import com.sonicsw.mf.common.runtime.INotification;
import com.progress.common.networkevents.INotificationEvent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.IComponent;

public interface IComponentAdapter extends IComponent
{
    void init(final Object p0, final IFrameworkComponentContext p1);
    
    INotification createNotification(final INotificationEvent p0);
    
    void sendNotification(final INotificationEvent p0);
    
    String getVersion();
    
    Hashtable getStatistics(final String[] p0);
    
    Hashtable getStatisticSchema(final String[] p0);
    
    Hashtable getConfiguration(final String p0);
    
    Hashtable getConfigurationSchema(final String[] p0);
    
    void setConfiguration(final String p0, final Hashtable p1) throws Exception;
    
    Hashtable getThresholds();
    
    Hashtable getThresholdSchema(final String[] p0);
    
    void setThresholds(final Hashtable p0) throws Exception;
    
    INotification createNotification(final short p0, final String p1, final String p2, final int p3);
    
    void sendNotification(final INotification p0);
    
    void logMessage(final String p0, final int p1);
    
    void logMessage(final String p0, final Throwable p1, final int p2);
    
    void logMessage(final Throwable p0, final int p1);
    
    boolean registerErrorCondition(final String p0, final int p1);
    
    void clearErrorCondition();
}

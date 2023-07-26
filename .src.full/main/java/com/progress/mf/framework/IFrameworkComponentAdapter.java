// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.framework;

import com.sonicsw.mf.framework.INotificationHandler;
import java.util.Hashtable;
import com.progress.mf.common.IComponentAdapter;

public interface IFrameworkComponentAdapter extends IComponentAdapter
{
    void init(final Object p0);
    
    void start(final String p0);
    
    void stop(final String p0);
    
    void destroy(final String p0);
    
    String[] getInstanceNames();
    
    String getCanonicalName();
    
    Hashtable getStatistics(final String p0, final String[] p1);
    
    Hashtable getConfiguration(final String p0, final String p1);
    
    void setConfiguration(final String p0, final String p1, final Hashtable p2) throws Exception;
    
    Hashtable getThresholds(final String p0);
    
    void setThresholds(final String p0, final Hashtable p1) throws Exception;
    
    void addNotificationHandler(final String p0, final INotificationHandler p1, final String[] p2);
    
    void removeNotificationHandler(final INotificationHandler p0);
    
    void removeNotificationHandler(final String p0, final INotificationHandler p1);
}

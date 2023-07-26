// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.mf.runtime;

import java.util.Hashtable;
import com.progress.mf.AbstractPluginComponent;

public class ManagementPluginComponent extends AbstractPluginComponent
{
    public static final String FRAMEWORK_PROP_PREFIX = "TBD";
    public static final String FRAMEWORK_METRIC_PREFIX = "framework.metric.vst.";
    
    public void start(final String s) {
    }
    
    public void stop(final String s) {
    }
    
    public void destroy(final String s) {
    }
    
    public String[] getInstanceNames() {
        return null;
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "framework.metric.vst.";
    }
    
    public Hashtable getStatistics(final String s, final String[] array) {
        return null;
    }
    
    public Hashtable getStatisticSchema(final String[] array) {
        return null;
    }
    
    public Hashtable getConfiguration(final String s) {
        return null;
    }
    
    public Hashtable getConfigurationSchema(final String[] array) {
        return null;
    }
    
    public void setConfiguration(final String s, final Hashtable hashtable) throws Exception {
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
}

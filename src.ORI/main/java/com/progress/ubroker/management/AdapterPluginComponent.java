// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import com.progress.ubroker.tools.adapter.AdapterGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;

public class AdapterPluginComponent extends OpenEdgePluginComponent
{
    private static final IManagementInfo[] ADAPTER_MANAGEMENT_INFO;
    public static final String ADAPTER_PROP_PREFIX = "TBD";
    public static final String ADAPTER_METRIC_PREFIX = "adapter.metric.vst.";
    
    public IManagementInfo[] getManagementInfo() {
        return AdapterPluginComponent.ADAPTER_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
            super.serverType = 4;
        }
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "adapter.metric.vst.";
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final AdapterGuiPlugin adapterPlugin = AbstractGuiPlugin.getAdapterPlugin();
        if (adapterPlugin != null) {
            instance = adapterPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final AdapterGuiPlugin adapterPlugin = AbstractGuiPlugin.getAdapterPlugin();
        if (adapterPlugin != null) {
            deleteInstance = adapterPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        ADAPTER_MANAGEMENT_INFO = new IManagementInfo[OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length + 0];
        final int length = OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length;
        System.arraycopy(OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, AdapterPluginComponent.ADAPTER_MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length);
    }
}

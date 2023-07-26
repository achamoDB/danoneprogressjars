// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import com.progress.ubroker.tools.aia.AiaGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;

public class AiaPluginComponent extends OpenEdgePluginComponent
{
    private static final IManagementInfo[] AIA_MANAGEMENT_INFO;
    public static final String AIA_PROP_PREFIX = "TBD";
    public static final String AIA_METRIC_PREFIX = "aia.metric.vst.";
    
    public IManagementInfo[] getManagementInfo() {
        return AiaPluginComponent.AIA_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
        }
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "aia.metric.vst.";
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final AiaGuiPlugin aiaPlugin = AbstractGuiPlugin.getAiaPlugin();
        if (aiaPlugin != null) {
            instance = aiaPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final AiaGuiPlugin aiaPlugin = AbstractGuiPlugin.getAiaPlugin();
        if (aiaPlugin != null) {
            deleteInstance = aiaPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        AIA_MANAGEMENT_INFO = new IManagementInfo[OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length];
        final int length = OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length;
        System.arraycopy(OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, AiaPluginComponent.AIA_MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length);
    }
}

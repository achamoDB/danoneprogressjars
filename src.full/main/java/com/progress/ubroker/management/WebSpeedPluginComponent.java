// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import com.progress.ubroker.tools.UBGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;

public class WebSpeedPluginComponent extends UbrokerPluginComponent
{
    private static final IManagementInfo[] WS_MANAGEMENT_INFO;
    public static final String WS_PROP_PREFIX = "TBD";
    public static final String WS_METRIC_PREFIX = "webspeed.metric.vst.";
    
    public IManagementInfo[] getManagementInfo() {
        return WebSpeedPluginComponent.WS_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
            super.serverType = 1;
        }
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "webspeed.metric.vst.";
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final UBGuiPlugin webSpeedPlugin = AbstractGuiPlugin.getWebSpeedPlugin();
        if (webSpeedPlugin != null) {
            instance = webSpeedPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final UBGuiPlugin webSpeedPlugin = AbstractGuiPlugin.getWebSpeedPlugin();
        if (webSpeedPlugin != null) {
            deleteInstance = webSpeedPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        WS_MANAGEMENT_INFO = new IManagementInfo[UbrokerPluginComponent.UB_MANAGEMENT_INFO.length + 0];
        final int length = UbrokerPluginComponent.UB_MANAGEMENT_INFO.length;
        System.arraycopy(UbrokerPluginComponent.UB_MANAGEMENT_INFO, 0, WebSpeedPluginComponent.WS_MANAGEMENT_INFO, 0, UbrokerPluginComponent.UB_MANAGEMENT_INFO.length);
    }
}

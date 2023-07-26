// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import com.progress.ubroker.tools.UBGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;

public class AppServerPluginComponent extends UbrokerPluginComponent
{
    private static final IManagementInfo[] AS_MANAGEMENT_INFO;
    public static final String AS_PROP_PREFIX = "TBD";
    public static final String AS_METRIC_PREFIX = "appserver.metric.vst.";
    
    public IManagementInfo[] getManagementInfo() {
        return AppServerPluginComponent.AS_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
            super.serverType = 0;
        }
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "appserver.metric.vst.";
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final UBGuiPlugin appServerPlugin = AbstractGuiPlugin.getAppServerPlugin();
        if (appServerPlugin != null) {
            instance = appServerPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final UBGuiPlugin appServerPlugin = AbstractGuiPlugin.getAppServerPlugin();
        if (appServerPlugin != null) {
            deleteInstance = appServerPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        AS_MANAGEMENT_INFO = new IManagementInfo[UbrokerPluginComponent.UB_MANAGEMENT_INFO.length + 0];
        final int length = UbrokerPluginComponent.UB_MANAGEMENT_INFO.length;
        System.arraycopy(UbrokerPluginComponent.UB_MANAGEMENT_INFO, 0, AppServerPluginComponent.AS_MANAGEMENT_INFO, 0, UbrokerPluginComponent.UB_MANAGEMENT_INFO.length);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import com.progress.ubroker.tools.MSGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;

public class MessengersPluginComponent extends OpenEdgePluginComponent
{
    private static final IManagementInfo[] MSNGER_MANAGEMENT_INFO;
    public static final String MESSENGERS_PROP_PREFIX = "TBD";
    public static final String MESSENGERS_METRIC_PREFIX = "messengers.metric.vst.";
    
    public IManagementInfo[] getManagementInfo() {
        return MessengersPluginComponent.MSNGER_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
        }
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "messengers.metric.vst.";
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final MSGuiPlugin msngrPlugin = AbstractGuiPlugin.getMsngrPlugin();
        if (msngrPlugin != null) {
            instance = msngrPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final MSGuiPlugin msngrPlugin = AbstractGuiPlugin.getMsngrPlugin();
        if (msngrPlugin != null) {
            deleteInstance = msngrPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        MSNGER_MANAGEMENT_INFO = new IManagementInfo[OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length + 0];
        final int length = OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length;
        System.arraycopy(OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, MessengersPluginComponent.MSNGER_MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length);
    }
}

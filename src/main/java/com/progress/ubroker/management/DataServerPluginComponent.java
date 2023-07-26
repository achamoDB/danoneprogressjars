// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import com.progress.ubroker.tools.UBGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.sonicsw.mf.common.info.IManagementInfo;

public class DataServerPluginComponent extends OpenEdgePluginComponent
{
    private static final IManagementInfo[] DS_MANAGEMENT_INFO;
    public static final String DS_PROP_PREFIX = "TBD";
    public static final String DS_METRIC_PREFIX = "dataserver.metric.vst.";
    
    public IManagementInfo[] getManagementInfo() {
        return DataServerPluginComponent.DS_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
            if (super.m_plugin.m_personality.equals("Oracle DataServer")) {
                super.serverType = 3;
            }
            else if (super.m_plugin.m_personality.equals("ODBC DataServer")) {
                super.serverType = 2;
            }
            else if (super.m_plugin.m_personality.equals("MSS DataServer")) {
                super.serverType = 5;
            }
        }
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "dataserver.metric.vst.";
    }
    
    public Boolean createInstance(final String[] array) {
        if (super.serverType == 5) {
            return this.createInstance(AbstractGuiPlugin.getMssDataServerPlugin(), array);
        }
        if (super.serverType == 2) {
            return this.createInstance(AbstractGuiPlugin.getOdbcDataServerPlugin(), array);
        }
        if (super.serverType == 3) {
            return this.createInstance(AbstractGuiPlugin.getOracleDataServerPlugin(), array);
        }
        return new Boolean(false);
    }
    
    public Boolean createInstance(final UBGuiPlugin ubGuiPlugin, final String[] array) {
        boolean instance = false;
        if (ubGuiPlugin != null) {
            instance = ubGuiPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        if (super.serverType == 5) {
            return this.deleteInstance(AbstractGuiPlugin.getMssDataServerPlugin(), s);
        }
        if (super.serverType == 2) {
            return this.deleteInstance(AbstractGuiPlugin.getOdbcDataServerPlugin(), s);
        }
        if (super.serverType == 3) {
            return this.deleteInstance(AbstractGuiPlugin.getOracleDataServerPlugin(), s);
        }
        return new Boolean(false);
    }
    
    public Boolean deleteInstance(final UBGuiPlugin ubGuiPlugin, final String s) {
        boolean deleteInstance = false;
        if (ubGuiPlugin != null) {
            deleteInstance = ubGuiPlugin.deleteInstance(s);
        }
        return new Boolean(deleteInstance);
    }
    
    static {
        DS_MANAGEMENT_INFO = new IManagementInfo[OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length + 0];
        final int length = OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length;
        System.arraycopy(OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, DataServerPluginComponent.DS_MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length);
    }
}

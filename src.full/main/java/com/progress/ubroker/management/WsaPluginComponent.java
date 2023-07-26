// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import java.lang.reflect.Method;
import com.sonicsw.mf.common.info.InfoFactory;
import com.progress.wsa.admin.PingResponse;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;
import com.progress.ubroker.tools.wsa.WsaInstanceRemoteObject;
import com.progress.ubroker.tools.wsa.WSRemoteCmdStatus;
import com.progress.common.log.ProLog;
import com.progress.common.util.crypto;
import com.progress.ubroker.tools.wsa.WsaGuiPlugin;
import com.progress.ubroker.tools.AbstractGuiPlugin;
import com.progress.mf.AbstractPluginComponent;
import com.sonicsw.mf.framework.IFrameworkComponentContext;
import com.progress.ubroker.tools.wsa.WSRemoteCmdDescriptor;
import com.progress.wsa.tools.WsaLoginInfo;
import com.sonicsw.mf.common.info.IManagementInfo;
import com.progress.ubroker.tools.wsa.IWsaCmdConst;

public class WsaPluginComponent extends OpenEdgePluginComponent implements IWsaCmdConst
{
    private static String NEWLINE;
    private static final IManagementInfo[] WSA_MANAGEMENT_INFO;
    WsaLoginInfo m_wsaLogin;
    public static final String WSA_PROP_PREFIX = "TBD";
    public static final String WSA_METRIC_PREFIX = "wsa.metric.vst.";
    WSRemoteCmdDescriptor m_cmd;
    
    public WsaPluginComponent() {
        this.m_wsaLogin = new WsaLoginInfo();
        this.m_cmd = new WSRemoteCmdDescriptor();
    }
    
    public IManagementInfo[] getManagementInfo() {
        return WsaPluginComponent.WSA_MANAGEMENT_INFO;
    }
    
    public void init(final Object o, final IFrameworkComponentContext frameworkComponentContext) {
        if (!super.m_initialized) {
            super.init(o, frameworkComponentContext);
        }
    }
    
    protected void initSearchPrefix() {
        AbstractPluginComponent.m_propertySearchPrefix = "TBD";
        AbstractPluginComponent.m_metricSearchPrefix = "wsa.metric.vst.";
    }
    
    public Boolean createInstance(final String[] array) {
        boolean instance = false;
        final WsaGuiPlugin wsaPlugin = AbstractGuiPlugin.getWsaPlugin();
        if (wsaPlugin != null) {
            instance = wsaPlugin.createInstance(array);
        }
        return new Boolean(instance);
    }
    
    public Boolean deleteInstance(final String s) {
        boolean deleteInstance = false;
        final WsaGuiPlugin wsaPlugin = AbstractGuiPlugin.getWsaPlugin();
        if (wsaPlugin != null) {
            deleteInstance = wsaPlugin.deleteInstance(s);
            if (deleteInstance) {
                this.setWsaLogout();
            }
        }
        return new Boolean(deleteInstance);
    }
    
    public Boolean setWsaLogin(final String s, final String s2, final String s3) {
        boolean successOrFailure;
        try {
            this.m_wsaLogin.setWsaLoginInfo(new crypto().encrypt(s2), new crypto().encrypt(s3));
            successOrFailure = this.getRuntimeDefaults(s).getSuccessOrFailure();
            if (!successOrFailure) {
                this.m_wsaLogin.setWsaLoginInfo(null, null);
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "setWsaLogin: " + ex.toString());
            successOrFailure = false;
        }
        return new Boolean(successOrFailure);
    }
    
    public Boolean setWsaLogout() {
        boolean value;
        try {
            if (this.m_wsaLogin != null) {
                this.m_wsaLogin.setWsaLoginInfo(null, null);
            }
            value = true;
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "setWsaLogout: " + ex.toString());
            value = false;
        }
        return new Boolean(value);
    }
    
    private WSRemoteCmdStatus executeWsaCmd(final String str) {
        WSRemoteCmdStatus wsRemoteCmdStatus;
        try {
            wsRemoteCmdStatus = (WSRemoteCmdStatus)WsaInstanceRemoteObject.findWsaInstanceRemoteObject(this.getPropertyGroup() + "." + str).doRemoteToolCmd(this.m_cmd);
        }
        catch (Exception ex) {
            wsRemoteCmdStatus = null;
        }
        return wsRemoteCmdStatus;
    }
    
    public Boolean getIsRunning(final String s) {
        boolean running = false;
        try {
            this.m_cmd.makePingWsaCmd(this.m_wsaLogin);
            final PingResponse pingResponse = (PingResponse)this.executeWsaCmd(s).fetchPingWsaStatus();
            if (pingResponse != null && pingResponse.isRunning()) {
                running = pingResponse.isRunning();
            }
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "getIsRunning: " + ex.toString());
            running = false;
        }
        return new Boolean(running);
    }
    
    public WSRemoteCmdStatus deploy(final String s, final String s2, final String[] array) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeDeployWSCmd(this.m_wsaLogin, s2, array);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "deploy: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus undeploy(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeUndeployWSCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "undeploy: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus importWsd(final String s, final String s2, final String[] array) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeImportWSCmd(this.m_wsaLogin, s2, array);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "importWsd: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus export(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeExportWSCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "export: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus enable(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeEnableWSCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "enable: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus disable(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeDisableWSCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "disable: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus update(final String s, final String s2, final String[] array) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeUpdateWSCmd(this.m_wsaLogin, s2, array);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "update: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus query(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeQueryWSCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "query: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus list(final String s) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeListWSCmd(this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "list: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus getWsaDetailStatus(final String s) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makePingWsaCmd(this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "getWsaDetailStatus: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus getRuntimeDefaults(final String s) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeGetRTDefaultsCmd(this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "getRuntimeDefaults: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus getRuntimeProperties(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeGetRTPropertiesCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "getRuntimeProperties: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus getRuntimeStatistics(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeGetRTStatsCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "getRuntimeStatistics: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus resetRuntimeDefaults(final String s) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeResetRTDefaultsCmd(this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "resetRuntimeDefaults: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus resetRuntimeProperties(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeResetRTPropertiesCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "resetRuntimeProperties: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus resetRuntimeStatistics(final String s, final String s2) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeResetRTStatsCmd(s2, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "resetRuntimeStatistics: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus updateRuntimeDefaults(final String s, final Object o) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeUpdateRTDefaultsCmd(o, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "updateRuntimeDefaults: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus updateRuntimeProperties(final String s, final String s2, final Object o) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeUpdateRTPropertiesCmd(s2, o, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "updateRuntimeProperties: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus updateRuntimeDefault(final String s, final String s2, final String s3) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeUpdateOneRTDefaultCmd(s2, s3, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "updateRuntimeDefault: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    public WSRemoteCmdStatus updateRuntimeProperty(final String s, final String s2, final String s3, final String s4) {
        WSRemoteCmdStatus executeWsaCmd;
        try {
            this.m_cmd.makeUpdateOneRTPropCmd(s2, s3, s4, this.m_wsaLogin);
            executeWsaCmd = this.executeWsaCmd(s);
        }
        catch (Exception ex) {
            ProLog.logd(super.m_plugin.m_personality, 3, "updateRuntimeProperty: " + ex.toString());
            executeWsaCmd = null;
        }
        return executeWsaCmd;
    }
    
    static {
        WsaPluginComponent.NEWLINE = System.getProperty("line.separator");
        WSA_MANAGEMENT_INFO = new IManagementInfo[OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length + 22];
        int length = OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length;
        Method method = null;
        System.arraycopy(OpenEdgePluginComponent.OE_MANAGEMENT_INFO, 0, WsaPluginComponent.WSA_MANAGEMENT_INFO, 0, OpenEdgePluginComponent.OE_MANAGEMENT_INFO.length);
        try {
            method = WsaPluginComponent.class.getMethod("setWsaLogin", String.class, String.class, String.class);
        }
        catch (Exception ex) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Set the WSA login to the specified username/password.", method);
        try {
            method = WsaPluginComponent.class.getMethod("setWsaLogout", (Class[])new Class[0]);
        }
        catch (Exception ex2) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Log out of the WSA web server.", method);
        try {
            method = WsaPluginComponent.class.getMethod("deploy", String.class, String.class, String[].class);
        }
        catch (Exception ex3) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Deploy an array of WSAD objects.", method);
        try {
            method = WsaPluginComponent.class.getMethod("undeploy", String.class, String.class);
        }
        catch (Exception ex4) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Undeploy the named WSDL object.", method);
        try {
            method = WsaPluginComponent.class.getMethod("importWsd", String.class, String.class, String[].class);
        }
        catch (Exception ex5) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Import an array of WSDL objects.", method);
        try {
            method = WsaPluginComponent.class.getMethod("export", String.class, String.class);
        }
        catch (Exception ex6) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Import the named WSDL object.", method);
        try {
            method = WsaPluginComponent.class.getMethod("enable", String.class, String.class);
        }
        catch (Exception ex7) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Enable the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("disable", String.class, String.class);
        }
        catch (Exception ex8) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Disable the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("update", String.class, String.class, String[].class);
        }
        catch (Exception ex9) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update the array of WSDL objects.", method);
        try {
            method = WsaPluginComponent.class.getMethod("query", String.class, String.class);
        }
        catch (Exception ex10) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Query the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("list", String.class);
        }
        catch (Exception ex11) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("List the WSDL objects associated with the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("getWsaDetailStatus", String.class);
        }
        catch (Exception ex12) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the detailed status for the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("getRuntimeDefaults", String.class);
        }
        catch (Exception ex13) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the run time defaults associated with the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("getRuntimeProperties", String.class, String.class);
        }
        catch (Exception ex14) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the run time properties of either the WSA insance (deployed WS is null) or the named deployed WS.", method);
        try {
            method = WsaPluginComponent.class.getMethod("getRuntimeStatistics", String.class, String.class);
        }
        catch (Exception ex15) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Get the run time statistics of either the WSA insance (deployed WS is null) or the named deployed WS.", method);
        try {
            method = WsaPluginComponent.class.getMethod("resetRuntimeDefaults", String.class);
        }
        catch (Exception ex16) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Reset the run time defaults associated with the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("resetRuntimeProperties", String.class, String.class);
        }
        catch (Exception ex17) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Reset the run time properties of either the WSA insance (deployed WS is null) or the named deployed WS.", method);
        try {
            method = WsaPluginComponent.class.getMethod("resetRuntimeStatistics", String.class, String.class);
        }
        catch (Exception ex18) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Reset the run time statistics of either the WSA insance (deployed WS is null) or the named deployed WS.", method);
        try {
            method = WsaPluginComponent.class.getMethod("updateRuntimeDefaults", String.class, Object.class);
        }
        catch (Exception ex19) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update the list of run time defaults associated with the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("updateRuntimeProperties", String.class, String.class, Object.class);
        }
        catch (Exception ex20) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update the list of run time properties of either the WSA insance (deployed WS is null) or the named deployed WS.", method);
        try {
            method = WsaPluginComponent.class.getMethod("updateRuntimeDefault", String.class, String.class, String.class);
        }
        catch (Exception ex21) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update the named run time default with the named value associated with the named WSA service.", method);
        try {
            method = WsaPluginComponent.class.getMethod("updateRuntimeProperty", String.class, String.class, String.class, String.class);
        }
        catch (Exception ex22) {}
        WsaPluginComponent.WSA_MANAGEMENT_INFO[length++] = (IManagementInfo)InfoFactory.createOperationInfo("Update the run time property of either the WSA insance (deployed WS is null) or the named deployed WS.", method);
    }
}

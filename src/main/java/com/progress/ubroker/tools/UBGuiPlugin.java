// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.DataServerPluginComponent;
import com.progress.ubroker.management.UbrokerPluginComponent;
import java.util.Vector;
import java.util.Enumeration;
import com.progress.common.networkevents.IEventStream;
import java.rmi.server.RemoteStub;
import com.progress.chimera.common.IChimeraHierarchy;
import java.rmi.Remote;
import com.progress.chimera.adminserver.IAdministrationServer;

public class UBGuiPlugin extends AbstractGuiPlugin implements IBTMsgCodes
{
    public static String APPSVR_ID;
    public static String WEBSPD_ID;
    public static String OR_ID;
    public static String OD_ID;
    public static String MSS_ID;
    
    public boolean init(final int n, final IAdministrationServer administrationServer, final String[] array) {
        return super.init(n, administrationServer, array);
    }
    
    public String formatName(final String s) {
        return this.formatName(this.getGroupPrefix(), s);
    }
    
    public String formatName(final String str, String string) {
        if (string != null && !string.toLowerCase().startsWith(str.toLowerCase())) {
            string = str + "." + string;
        }
        return string;
    }
    
    public static String getInstanceNameOnly(String substring) {
        final int lastIndex = substring.lastIndexOf(".");
        if (lastIndex >= 0) {
            substring = substring.substring(lastIndex + 1);
        }
        return substring;
    }
    
    public static UBGuiPlugin getPlugin(String lowerCase) {
        UBGuiPlugin ubGuiPlugin = null;
        if (lowerCase != null) {
            lowerCase = lowerCase.toLowerCase();
        }
        if (lowerCase == null) {
            ubGuiPlugin = null;
        }
        else if (lowerCase.startsWith("UBroker.AS".toLowerCase())) {
            ubGuiPlugin = AbstractGuiPlugin.getAppServerPlugin();
        }
        else if (lowerCase.startsWith("UBroker.WS".toLowerCase())) {
            ubGuiPlugin = AbstractGuiPlugin.getWebSpeedPlugin();
        }
        else if (lowerCase.startsWith("UBroker.OR".toLowerCase())) {
            ubGuiPlugin = AbstractGuiPlugin.getOracleDataServerPlugin();
        }
        else if (lowerCase.startsWith("UBroker.OD".toLowerCase())) {
            ubGuiPlugin = AbstractGuiPlugin.getOdbcDataServerPlugin();
        }
        else if (lowerCase.startsWith("UBroker.MS".toLowerCase())) {
            ubGuiPlugin = AbstractGuiPlugin.getMssDataServerPlugin();
        }
        return ubGuiPlugin;
    }
    
    public String getComponentClassName() {
        String s = UbrokerPluginComponent.class.getName();
        if (super.m_groupPrefix.equals("UBroker.AS")) {
            s = UbrokerPluginComponent.class.getName();
        }
        else if (super.m_groupPrefix.equals("UBroker.WS")) {
            s = UbrokerPluginComponent.class.getName();
        }
        else if (super.m_groupPrefix.equals("UBroker.OR")) {
            s = DataServerPluginComponent.class.getName();
        }
        else if (super.m_groupPrefix.equals("UBroker.OD")) {
            s = DataServerPluginComponent.class.getName();
        }
        else if (super.m_groupPrefix.equals("UBroker.MS")) {
            s = DataServerPluginComponent.class.getName();
        }
        return s;
    }
    
    public Remote getRemoteObject(final String s, final String s2) {
        super.m_wkRemote = (IChimeraHierarchy)super.getRemoteObject(s, s2);
        try {
            ((UBRemoteObject)super.m_wkRemote).setRMIUsrInfo(s, s2, super.m_rmiHost, super.m_rmiPort, super.m_adminSrvrURL);
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381975L, new Object[] { "UBRemoteObject", ex.getMessage() }));
        }
        return super.m_wkRemote;
    }
    
    public boolean delChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final String s, final IEventStream eventStream) {
        try {
            return super.delChildNode(remoteStub, chimeraHierarchy, s, eventStream);
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381982L, new Object[] { "UBGuiPlugin", s, ex.toString() }));
            return false;
        }
    }
    
    public boolean addChildNode(final RemoteStub remoteStub, final IChimeraHierarchy chimeraHierarchy, final IChimeraHierarchy chimeraHierarchy2, final IEventStream eventStream) {
        try {
            super.addChildNode(remoteStub, chimeraHierarchy, chimeraHierarchy2, eventStream);
            return true;
        }
        catch (Exception ex) {
            System.err.println(UBToolsMsg.getMsg(7094295313015381983L, new Object[] { "UBGuiPlugin", chimeraHierarchy2.toString(), ex.toString() }));
            return false;
        }
    }
    
    public void refreshAddNewToolPanel(final IChimeraHierarchy chimeraHierarchy, final String s) {
    }
    
    public void shutdown() {
        this.setComponentState(3);
        ((UBRemoteObject)super.m_wkRemote).shutdown();
        if (super.m_pluginComponent != null) {
            super.m_pluginComponent.stop();
        }
    }
    
    public static String[] convertEnumToStringArray(final Enumeration enumeration) {
        final Vector<String> vector = new Vector<String>();
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
        return vector.toArray(new String[0]);
    }
    
    static {
        UBGuiPlugin.APPSVR_ID = "AppServer";
        UBGuiPlugin.WEBSPD_ID = "WebSpeed";
        UBGuiPlugin.OR_ID = "Oracle DataServer";
        UBGuiPlugin.OD_ID = "ODBC DataServer";
        UBGuiPlugin.MSS_ID = "MSS DataServer";
    }
}

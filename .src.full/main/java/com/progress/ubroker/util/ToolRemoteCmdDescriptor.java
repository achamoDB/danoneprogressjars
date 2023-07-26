// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;

public class ToolRemoteCmdDescriptor implements Serializable, IToolCmdConst
{
    public static int m_version;
    public int m_command;
    public String m_toolInstancePropSpec;
    public Vector m_cmdArgsList;
    
    public ToolRemoteCmdDescriptor() {
        this.m_command = 0;
        this.m_toolInstancePropSpec = null;
        this.m_cmdArgsList = null;
        this.resetAllVars();
    }
    
    public int getCommand() {
        return this.m_command;
    }
    
    public String getPropSpec() {
        return this.m_toolInstancePropSpec;
    }
    
    public Enumeration getArgsList() {
        return this.m_cmdArgsList.elements();
    }
    
    public int getVersion() {
        return ToolRemoteCmdDescriptor.m_version;
    }
    
    public void setCommand(final int command) {
        this.m_command = command;
    }
    
    public void setPropSpec(final String toolInstancePropSpec) {
        this.m_toolInstancePropSpec = toolInstancePropSpec;
    }
    
    public void addArgsList(final Object obj) {
        this.m_cmdArgsList.addElement(obj);
    }
    
    public void addArgsList(final Object[] array) {
        for (int i = 0; i < array.length; ++i) {
            this.addArgsList(array[i]);
        }
    }
    
    public void resetArgList() {
        if (this.m_cmdArgsList == null || this.m_cmdArgsList.size() > 0) {
            this.m_cmdArgsList = new Vector();
        }
    }
    
    public void makeGetSvcCfgPkt(final String s) {
        this.fillDescriptorContent(1, s);
    }
    
    public void makeSaveSvcCfgPkt(final char[] array) {
        this.fillDescriptorContent(2, null, array);
    }
    
    public void makeValidateSaveCfgPkt(final PropertiesSaveDescriptor propertiesSaveDescriptor) {
        this.fillDescriptorContent(9, null, propertiesSaveDescriptor);
    }
    
    public void makeStartSvcPkt(final String s) {
        this.fillDescriptorContent(100, s);
    }
    
    public void makeStopSvcPkt(final String s) {
        this.fillDescriptorContent(101, s);
    }
    
    public void makePingSvcPkt(final String s) {
        this.fillDescriptorContent(102, s);
    }
    
    public void makeGetNSNamesPkt() {
        this.fillDescriptorContent(6, null);
    }
    
    public void makeGetAdminRoleNames() {
        this.fillDescriptorContent(7, null);
    }
    
    public void makeGetSSLAliasNames() {
        this.fillDescriptorContent(8, null);
    }
    
    public void makeReplacePropertiesPkt(final String s, final char[] array) {
        this.fillDescriptorContent(3, s, array);
    }
    
    public void makeGetPrefPropPkt() {
        this.fillDescriptorContent(4, null);
    }
    
    public void makePrefPropChangedPkt() {
        this.fillDescriptorContent(5, null);
    }
    
    public void makeLoadGUISchemaPkt() {
        this.fillDescriptorContent(10, null);
    }
    
    public void makeGetGUISchemaPropFnPkt() {
        this.fillDescriptorContent(12, null);
    }
    
    public void makeFetchRPMPkt() {
        this.fillDescriptorContent(11, null);
    }
    
    public void makeAddNewGuiToolPkt(final String s) {
        this.fillDescriptorContent(103, "", s);
    }
    
    public void makeAddNewGuiToolPkt(final String s, final String s2) {
        this.fillDescriptorContent(103, "", new Object[] { s, s2 });
    }
    
    public void makeAddNewGuiToolPkt(final String s, final String s2, final String s3) {
        this.fillDescriptorContent(103, "", new Object[] { s, s2, s3 });
    }
    
    public void makeRemoveGuiToolPkt(final String s, final String s2) {
        this.fillDescriptorContent(104, s2, s);
    }
    
    public void makeGetSummaryStatusLblPkt(final String s) {
        this.fillDescriptorContent(105, s);
    }
    
    public void makeGetSummaryStatusDataPkt(final String s) {
        this.fillDescriptorContent(106, s);
    }
    
    public void makeGetDetailStatusLblPkt(final String s) {
        this.fillDescriptorContent(107, s);
    }
    
    public void makeGetDetailStatusDataPkt(final String s) {
        this.fillDescriptorContent(108, s);
    }
    
    public void makeTrimSrvrByPkt(final int value, final String s) {
        this.fillDescriptorContent(109, s, new Integer(value));
    }
    
    public void makeAddNewSrvrsPkt(final int value, final String s) {
        this.fillDescriptorContent(110, s, new Integer(value));
    }
    
    public void makeValidOnePropPkt(final String s, final String s2, final String s3) {
        this.fillDescriptorContent(14, s, new Object[] { s2, s3 });
    }
    
    public void makeNSInstRefPkt(final String s, final String s2) {
        this.fillDescriptorContent(15, s, s2);
    }
    
    public void makeGetNSDetailStatPkt(final String s) {
        this.fillDescriptorContent(112, s);
    }
    
    public void makeGetNSSummaryStatusPkt(final String s) {
        this.fillDescriptorContent(111, s);
    }
    
    public void makeGetNSLocPropPkt(final String s, final String s2) {
        this.fillDescriptorContent(113, s2, s);
    }
    
    public char[] fetchSaveSvcCfgArg() {
        return (char[])this.get1stCmdArg();
    }
    
    public PropertiesSaveDescriptor fetchValidateSaveCfgArg() {
        return (PropertiesSaveDescriptor)this.get1stCmdArg();
    }
    
    public char[] fetchReplacePropertiesArg() {
        return (char[])this.get1stCmdArg();
    }
    
    public int fetchPingSvcArg() {
        if (this.m_cmdArgsList.size() == 0) {
            return 0;
        }
        return (int)this.get1stCmdArg();
    }
    
    public String fetchAddNewGuiToolArg() {
        return (String)this.get1stCmdArg();
    }
    
    public String fetchAddNewGuiToolArg2() {
        final Enumeration argsList = this.getArgsList();
        final String s = argsList.nextElement();
        return argsList.nextElement();
    }
    
    public String fetchAddNewGuiToolArg3() {
        final Enumeration argsList = this.getArgsList();
        final String s = argsList.nextElement();
        final String s2 = argsList.nextElement();
        return argsList.nextElement();
    }
    
    public String fetchRemoveGuiToolArg() {
        return (String)this.get1stCmdArg();
    }
    
    public String fetchSchemaGroupArg() {
        return (String)this.get1stCmdArg();
    }
    
    public int fetchTrimSrvrByArg() {
        return (int)this.get1stCmdArg();
    }
    
    public int fetchAddNewSrvrsArg() {
        return (int)this.get1stCmdArg();
    }
    
    public String fetchValOnePropNameArg() {
        return (String)this.get1stCmdArg();
    }
    
    public String fetchValOnePropValArgStr() {
        final Enumeration argsList = this.getArgsList();
        final String s = argsList.nextElement();
        return argsList.nextElement();
    }
    
    public int fetchValidOnePropValArgInt() {
        return (int)this.get1stCmdArg();
    }
    
    public String fetchNSInstRefArg() {
        return (String)this.get1stCmdArg();
    }
    
    public String fetchGetNSLocPropArg() {
        return (String)this.get1stCmdArg();
    }
    
    public void fillDescriptorContent(final int command, final String propSpec) {
        this.resetAllVars();
        this.setCommand(command);
        this.setPropSpec(propSpec);
    }
    
    public void fillDescriptorContent(final int command, final String propSpec, final Object o) {
        this.resetAllVars();
        this.setCommand(command);
        this.setPropSpec(propSpec);
        this.addArgsList(o);
    }
    
    public void fillDescriptorContent(final int command, final String propSpec, final Object[] array) {
        this.resetAllVars();
        this.setCommand(command);
        this.setPropSpec(propSpec);
        this.addArgsList(array);
    }
    
    private void resetAllVars() {
        this.m_command = 0;
        this.m_toolInstancePropSpec = null;
        this.resetArgList();
    }
    
    private Object get1stCmdArg() {
        return this.getArgsList().nextElement();
    }
    
    static {
        ToolRemoteCmdDescriptor.m_version = 3;
    }
}

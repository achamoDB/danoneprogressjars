// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools.wsa;

import java.util.Enumeration;
import com.progress.chimera.common.IChimeraHierarchy;
import com.progress.ubroker.util.ToolRemoteCmdDescriptor;

public class WSRemoteCmdDescriptor extends ToolRemoteCmdDescriptor implements IWsaCmdConst
{
    private void cleanup() {
        super.m_command = 0;
        super.m_toolInstancePropSpec = null;
        this.resetArgList();
    }
    
    public void makeDeployWSCmd(final Object obj, final String obj2, final String obj3) {
        this.cleanup();
        this.setCommand(201);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        super.m_cmdArgsList.addElement(obj3);
    }
    
    public void makeDeployWSCmd(final Object obj, final String obj2, final String[] array) {
        this.cleanup();
        this.setCommand(201);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        for (int i = 0; i < array.length; ++i) {
            super.m_cmdArgsList.addElement(array[i]);
        }
    }
    
    public void makeUndeployWSCmd(final IChimeraHierarchy obj, final Object obj2) {
        this.cleanup();
        this.setCommand(219);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeUndeployWSCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(219);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeImportWSCmd(final Object obj, final String obj2, final String obj3) {
        this.cleanup();
        this.setCommand(202);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        super.m_cmdArgsList.addElement(obj3);
    }
    
    public void makeImportWSCmd(final Object obj, final String obj2, final String[] array) {
        this.cleanup();
        this.setCommand(202);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        for (int i = 0; i < array.length; ++i) {
            super.m_cmdArgsList.addElement(array[i]);
        }
    }
    
    public void makeExportWSCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(203);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeUpdateWSCmd(final Object obj, final String obj2, final String obj3) {
        this.cleanup();
        this.setCommand(208);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        super.m_cmdArgsList.addElement(obj3);
    }
    
    public void makeUpdateWSCmd(final Object obj, final String obj2, final String[] array) {
        this.cleanup();
        this.setCommand(208);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        for (int i = 0; i < array.length; ++i) {
            super.m_cmdArgsList.addElement(array[i]);
        }
    }
    
    public void makeQueryWSCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(206);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeListWSCmd(final Object obj) {
        this.cleanup();
        this.setCommand(220);
        super.m_cmdArgsList.addElement(obj);
    }
    
    public void makeTestWSCmd(final Object obj) {
        this.cleanup();
        this.setCommand(207);
        super.m_cmdArgsList.addElement(obj);
    }
    
    public void makePingWsaCmd(final Object obj) {
        this.cleanup();
        this.setCommand(200);
        super.m_cmdArgsList.addElement(obj);
    }
    
    public void makeGetRTStatsCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(209);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeResetRTStatsCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(210);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeGetRTDefaultsCmd(final Object obj) {
        this.cleanup();
        this.setCommand(211);
        super.m_cmdArgsList.addElement(obj);
    }
    
    public void makeUpdateRTDefaultsCmd(final Object obj, final Object obj2) {
        this.cleanup();
        this.setCommand(212);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeUpdateOneRTDefaultCmd(final String obj, final String obj2, final Object obj3) {
        this.cleanup();
        this.setCommand(213);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        super.m_cmdArgsList.addElement(obj3);
    }
    
    public void makeResetRTDefaultsCmd(final Object obj) {
        this.cleanup();
        this.setCommand(214);
        super.m_cmdArgsList.addElement(obj);
    }
    
    public void makeGetRTPropertiesCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(215);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeUpdateRTPropertiesCmd(final String obj, final Object obj2, final Object obj3) {
        this.cleanup();
        this.setCommand(216);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        super.m_cmdArgsList.addElement(obj3);
    }
    
    public void makeUpdateOneRTPropCmd(final String obj, final String obj2, final String obj3, final Object obj4) {
        this.cleanup();
        this.setCommand(217);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
        super.m_cmdArgsList.addElement(obj3);
        super.m_cmdArgsList.addElement(obj4);
    }
    
    public void makeResetRTPropertiesCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(218);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeDisableWSCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(205);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public void makeEnableWSCmd(final String obj, final Object obj2) {
        this.cleanup();
        this.setCommand(204);
        super.m_cmdArgsList.addElement(obj);
        super.m_cmdArgsList.addElement(obj2);
    }
    
    public Object fetchFirstArg() {
        return this.getArgsList().nextElement();
    }
    
    public Object fetchSecondArg() {
        final Enumeration argsList = this.getArgsList();
        argsList.nextElement();
        return argsList.nextElement();
    }
    
    public Object fetchThirdArg() {
        final Enumeration argsList = this.getArgsList();
        argsList.nextElement();
        argsList.nextElement();
        return argsList.nextElement();
    }
    
    public Object fetchFourthArg() {
        final Enumeration argsList = this.getArgsList();
        argsList.nextElement();
        argsList.nextElement();
        argsList.nextElement();
        return argsList.nextElement();
    }
}

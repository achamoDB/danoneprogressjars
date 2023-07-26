// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Enumeration;
import com.progress.chimera.common.Tools;

class CStateIdle extends CState
{
    static CStateIdle singleInstance;
    
    CStateIdle() {
        super(CStateStarting.class);
    }
    
    static CStateIdle get() {
        return CStateIdle.singleInstance;
    }
    
    public String displayName() {
        return JAConfiguration.resources.getTranString("Not Running");
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        final JAConfiguration jaConfiguration = (JAConfiguration)o;
        if (jaConfiguration.database.getCurrentConfiguration() == jaConfiguration) {
            jaConfiguration.database.setCurrentConfiguration(null);
        }
        jaConfiguration.startedByMe = false;
        jaConfiguration.rjo = null;
        jaConfiguration.isStopableNow = false;
        jaConfiguration.database.blockStarts(false);
        try {
            if (jaConfiguration.crashEieio != null) {
                jaConfiguration.plugIn.getEventBroker().revokeInterest(jaConfiguration.crashEieio);
            }
        }
        catch (Throwable t) {
            Tools.px(t);
        }
        jaConfiguration.crashEieio = null;
        final Enumeration elements = jaConfiguration.services.elements();
        while (elements.hasMoreElements()) {
            elements.nextElement().setIdle();
        }
        jaConfiguration.biWriter.setIdle();
        jaConfiguration.aiWriter.setIdle();
        jaConfiguration.watchdog.setIdle();
        jaConfiguration.apWriter.setIdle();
    }
    
    public void during(final Object o) {
        super.during(o);
    }
    
    static {
        CStateIdle.singleInstance = new CStateIdle();
    }
}

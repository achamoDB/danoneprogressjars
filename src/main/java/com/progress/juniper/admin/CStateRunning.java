// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.common.Tools;

class CStateRunning extends CState
{
    static CStateRunning singleInstance;
    
    CStateRunning() {
        super(CStateShuttingDown.class);
    }
    
    public String displayName() {
        return JAConfiguration.resources.getTranString("Running");
    }
    
    static CStateRunning get() {
        return CStateRunning.singleInstance;
    }
    
    public boolean transitionOK(final CStateInitializing cStateInitializing) {
        return true;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        final JAConfiguration currentConfiguration = (JAConfiguration)o;
        currentConfiguration.database.setCurrentConfiguration(currentConfiguration);
        currentConfiguration.isStopableNow = true;
        ((JAConfiguration)o).database.blockStarts(false);
    }
    
    public void exit(final Object o) throws StateException {
        super.exit(o);
        final JAConfiguration jaConfiguration = (JAConfiguration)o;
        JAConfiguration.getLog().log(1, 7669630165411962082L, new Object[] { jaConfiguration.name() });
        try {
            if (jaConfiguration.crashEieio != null) {
                jaConfiguration.plugIn.getEventBroker().revokeInterest(jaConfiguration.crashEieio);
            }
        }
        catch (Throwable t) {
            Tools.px(t);
        }
        jaConfiguration.crashEieio = null;
        jaConfiguration.isStopableNow = false;
    }
    
    static {
        CStateRunning.singleInstance = new CStateRunning();
    }
}

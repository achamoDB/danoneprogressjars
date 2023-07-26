// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.common.Tools;
import com.progress.message.jpMsg;

class CStateStarting extends CState implements jpMsg
{
    static CStateStarting singleInstance;
    
    CStateStarting() {
        super(CStateInitializing.class);
    }
    
    static CStateStarting get() {
        return CStateStarting.singleInstance;
    }
    
    public String displayName() {
        return JAConfiguration.resources.getTranString("Starting");
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        final JAConfiguration currentConfiguration = (JAConfiguration)o;
        currentConfiguration.database.setCurrentConfiguration(currentConfiguration);
        currentConfiguration.database.blockStarts(true);
        final StringBuffer sb = new StringBuffer(currentConfiguration.name());
        if (currentConfiguration.database.getUserS() != null) {
            sb.append(".  User: ");
            sb.append(currentConfiguration.database.getUserS());
        }
        JAConfiguration.getLog().log(1, 7669630165411962085L, new Object[] { sb.toString() });
        try {
            currentConfiguration.startUp();
        }
        catch (JAConfiguration.ConfigurationException ex) {
            Tools.px(ex, "Can't start configuration: " + currentConfiguration.name());
            throw new StateException();
        }
    }
    
    public void during(final Object o) {
        final JAConfiguration jaConfiguration = (JAConfiguration)o;
        if (!Boolean.getBoolean("DontStartMonitors")) {
            jaConfiguration.startPrimaryMonitors(this, CState.getProratedTiming("RTStarting"), CState.getProratedTiming("WTCStarting", "WTCStartingMin"), CState.getTiming("ContStarting"));
        }
    }
    
    public void exit(final Object o) throws StateException {
        ((JAConfiguration)o).stopPrimaryMonitors();
    }
    
    static {
        CStateStarting.singleInstance = new CStateStarting();
    }
}

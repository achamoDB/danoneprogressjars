// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class CStateInitializing extends CState
{
    static CStateInitializing singleInstance;
    
    CStateInitializing() {
        super(CStateRunning.class);
    }
    
    static CStateInitializing get() {
        return CStateInitializing.singleInstance;
    }
    
    public String displayName() {
        return JAConfiguration.resources.getTranString("Initializing");
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        final JAConfiguration currentConfiguration = (JAConfiguration)o;
        currentConfiguration.database.setCurrentConfiguration(currentConfiguration);
        currentConfiguration.isStopableNow = false;
        if (currentConfiguration.mainServerGroup != null) {
            currentConfiguration.mainServerGroup.setInitializing();
        }
    }
    
    public void during(final Object o) {
        final JAConfiguration jaConfiguration = (JAConfiguration)o;
        if (!Boolean.getBoolean("DontStartMonitors")) {
            jaConfiguration.startPrimaryMonitors(this, CState.getProratedTiming("RTInitializing"), CState.getProratedTiming("WTCInitializing", "WTCInitializingMin"), CState.getTiming("ContInitializing"));
        }
    }
    
    public void exit(final Object o) throws StateException {
        super.exit(o);
        ((JAConfiguration)o).database.blockStarts(false);
        ((JAConfiguration)o).stopPrimaryMonitors();
    }
    
    static {
        CStateInitializing.singleInstance = new CStateInitializing();
    }
}

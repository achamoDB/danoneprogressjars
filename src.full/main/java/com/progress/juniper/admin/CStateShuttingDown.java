// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

class CStateShuttingDown extends CState
{
    boolean asyncShutdown;
    static CStateShuttingDown singleInstance;
    
    CStateShuttingDown() {
        super(CStateIdle.class);
        this.asyncShutdown = false;
    }
    
    public String displayName() {
        return JAConfiguration.resources.getTranString("Shutting Down");
    }
    
    static CStateShuttingDown get() {
        return get(false);
    }
    
    static CStateShuttingDown get(final boolean asyncShutdown) {
        CStateShuttingDown.singleInstance.asyncShutdown = asyncShutdown;
        return CStateShuttingDown.singleInstance;
    }
    
    public boolean transitionOK(final CStateRunning cStateRunning) {
        return true;
    }
    
    public void enter(final Object o) throws StateException {
        super.enter(o);
        final JAConfiguration jaConfiguration = (JAConfiguration)o;
        jaConfiguration.database.blockStarts(true);
        jaConfiguration.isStopableNow = false;
        if (!this.asyncShutdown) {
            JAConfiguration.getLog().log(4, 7669630165411963154L);
            if (!((MProservJuniper)jaConfiguration.rjo).sendPacket("SHDN")) {
                throw new StateExceptionEntering(this, "Can't send packet");
            }
        }
    }
    
    public void during(final Object o) {
        final JAConfiguration jaConfiguration = (JAConfiguration)o;
        if (!Boolean.getBoolean("DontStartMonitors")) {
            jaConfiguration.startPrimaryMonitors(this, CState.getProratedTiming("RTShuttingDown"), CState.getProratedTiming("WTCShuttingDown", "WTCShuttingDownMin"), CState.getTiming("ContShuttingDown"));
        }
    }
    
    public void cleanup(final Object o, final State state) {
        ((JAConfiguration)o).stopPrimaryMonitors();
    }
    
    public void exit(final Object o) throws StateException {
        ((JAConfiguration)o).stopPrimaryMonitors();
    }
    
    static {
        CStateShuttingDown.singleInstance = new CStateShuttingDown();
    }
}

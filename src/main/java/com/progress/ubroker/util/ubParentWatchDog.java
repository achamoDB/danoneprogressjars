// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.ubroker.broker.ubListenerThread;
import com.progress.common.ehnlog.IAppLogger;

public class ubParentWatchDog implements ubConstants, IWatchable
{
    private IAppLogger m_log;
    private ubProperties m_properties;
    private ubListenerThread m_listener;
    private int m_parentPID;
    
    public ubParentWatchDog(final ubListenerThread listener, final ubProperties properties, final IAppLogger log) {
        this.m_listener = listener;
        this.m_properties = properties;
        this.m_log = log;
        this.m_parentPID = this.m_properties.parentPID;
    }
    
    public void watchEvent() {
        if (!this.parentIsRunning()) {
            this.m_log.logError(7665689515738018160L, new Object[] { new Integer(this.m_properties.parentPID) });
            this.m_listener.requestShutdown();
            this.m_parentPID = 0;
        }
    }
    
    private boolean parentIsRunning() {
        return this.m_parentPID <= 0 || this.m_properties.env.query_PID_JNI(this.m_parentPID, true) > 0;
    }
}

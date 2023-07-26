// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

public class PropMgrPluginFinder
{
    static final int FETCH_PMP_RETRIES = 3;
    static PropMgrPlugin m_pmpInstanceHandle;
    
    public static PropMgrPlugin get() {
        int n = 0;
        while (PropMgrPluginFinder.m_pmpInstanceHandle == null && ++n < 3) {
            try {
                Thread.sleep(300L);
            }
            catch (InterruptedException ex) {}
            PropMgrPluginFinder.m_pmpInstanceHandle = PropMgrPlugin.getInstance();
        }
        return PropMgrPluginFinder.m_pmpInstanceHandle;
    }
    
    static {
        PropMgrPluginFinder.m_pmpInstanceHandle = null;
    }
}

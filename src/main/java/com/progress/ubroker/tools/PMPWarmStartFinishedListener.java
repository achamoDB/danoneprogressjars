// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;

public class PMPWarmStartFinishedListener extends EventListener
{
    AbstractGuiPlugin plugin;
    
    public PMPWarmStartFinishedListener(final AbstractGuiPlugin plugin) {
        this.plugin = null;
        this.plugin = plugin;
    }
    
    public synchronized void processEvent(final IEventObject eventObject) {
        try {
            if (!((INotificationEvent)eventObject).getNotificationName().equalsIgnoreCase("EPMPWarmStartFinishedEvent")) {
                return;
            }
            this.plugin.setManagementPluginOnline();
        }
        catch (Exception ex) {
            UBToolsMsg.logError(5, "Exception processing PMPWarmStartFinishedListener: " + ex.getMessage());
        }
    }
}

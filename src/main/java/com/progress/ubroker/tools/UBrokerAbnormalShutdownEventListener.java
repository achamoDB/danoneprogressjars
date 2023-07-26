// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import com.progress.ubroker.management.events.COpenEdgeManagementContent;
import com.progress.ubroker.management.events.IOpenEdgeManagementEvent;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;

public class UBrokerAbnormalShutdownEventListener extends EventListener
{
    AbstractGuiPluginRemObj plugin;
    
    public UBrokerAbnormalShutdownEventListener(final AbstractGuiPluginRemObj plugin) {
        this.plugin = null;
        this.plugin = plugin;
    }
    
    public synchronized void processEvent(final IEventObject eventObject) {
        try {
            if (!((INotificationEvent)eventObject).getNotificationName().equalsIgnoreCase("EAbnormalShutdownEvent")) {
                return;
            }
            final COpenEdgeManagementContent content = ((IOpenEdgeManagementEvent)eventObject).getContent();
            content.getIssuerName();
            final String s = (String)content.getContent()[0];
            if (s.startsWith(this.plugin.m_groupPath)) {
                final SvcControlCmd svcControlCmd = (SvcControlCmd)this.plugin.lookupControlCmd(s);
                if (svcControlCmd != null) {
                    svcControlCmd.resetState();
                }
            }
        }
        catch (Exception ex) {
            UBToolsMsg.logError(5, "Exception processing EAbnormalShutdownEvent: " + ex.getMessage());
        }
    }
}

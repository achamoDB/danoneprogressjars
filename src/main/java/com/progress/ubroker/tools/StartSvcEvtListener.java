// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.rmi.RemoteException;
import com.progress.ubroker.tools.events.IYodaPluginEventSuper;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;

class StartSvcEvtListener extends EventListener
{
    AbstractGuiPluginRemObj m_guiPluginObj;
    
    public StartSvcEvtListener(final AbstractGuiPluginRemObj guiPluginObj) {
        this.m_guiPluginObj = null;
        this.m_guiPluginObj = guiPluginObj;
    }
    
    public void processEvent(final IEventObject eventObject) throws RemoteException {
        try {
            final IYodaPluginEventSuper yodaPluginEventSuper = (IYodaPluginEventSuper)eventObject;
            final String instanceName = yodaPluginEventSuper.getInstanceName();
            final String propGrpFullSpec = yodaPluginEventSuper.getPropGrpFullSpec();
            if (!propGrpFullSpec.equals(this.m_guiPluginObj.m_groupPath + "." + instanceName)) {
                return;
            }
            this.m_guiPluginObj.updateSvcControlObjState(1, propGrpFullSpec, instanceName);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            UBToolsMsg.logException("Exception processing StartSvcEvent: " + ex.toString());
        }
    }
}

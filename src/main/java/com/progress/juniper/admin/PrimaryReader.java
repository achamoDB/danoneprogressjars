// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.log.Excp;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventObject;
import java.io.BufferedReader;

class PrimaryReader extends ServerGroupReader
{
    public PrimaryReader(final JAConfiguration jaConfiguration, final JAService jaService, final BufferedReader bufferedReader, final Process process) {
        super(jaConfiguration, jaService, bufferedReader, process);
    }
    
    String descr() {
        return super.descr() + " (primary)";
    }
    
    EventObject[] getEvents(final String s) throws RemoteException {
        final EServerGroupStartupFailed eServerGroupStartupFailed = new EServerGroupStartupFailed(super.serverGroup, s);
        return new EventObject[] { eServerGroupStartupFailed, new EPrimaryStartupFailed(super.config.database, eServerGroupStartupFailed) };
    }
    
    void handleError(final String s) {
        super.config.stopI(false);
        try {
            super.config.getPlugIn().getEventBroker().postEvent(new EStartupProcessCompleted(super.config.database));
        }
        catch (RemoteException ex) {
            Excp.print("Can't post event marking end of startup process,");
        }
    }
}

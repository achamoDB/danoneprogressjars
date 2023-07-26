// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.log.Excp;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventObject;
import java.io.BufferedReader;

class SecondaryReader extends ServerGroupReader
{
    public SecondaryReader(final JAConfiguration jaConfiguration, final JAService jaService, final BufferedReader bufferedReader, final Process process) {
        super(jaConfiguration, jaService, bufferedReader, process);
    }
    
    String descr() {
        return super.descr() + " (secondary)";
    }
    
    EventObject[] getEvents(final String s) throws RemoteException {
        final EServerGroupStartupFailed eServerGroupStartupFailed = new EServerGroupStartupFailed(super.serverGroup, s);
        return new EventObject[] { eServerGroupStartupFailed, new ESecondaryStartupFailed(super.config.database, eServerGroupStartupFailed) };
    }
    
    void handleError(final String s) {
        super.config.removeServerGroupFromMonitor(super.serverGroup);
        try {
            super.serverGroup.setIdle();
        }
        catch (StateException ex) {
            Excp.print(ex);
        }
    }
}

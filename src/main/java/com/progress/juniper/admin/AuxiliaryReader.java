// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.log.Excp;
import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventObject;
import java.io.BufferedReader;

class AuxiliaryReader extends ProcessReader
{
    String description;
    
    public AuxiliaryReader(final JAConfiguration jaConfiguration, final String description, final BufferedReader bufferedReader, final Process process) {
        super(jaConfiguration, bufferedReader, process);
        this.description = null;
        this.description = description;
    }
    
    String descr() {
        return this.description;
    }
    
    EventObject[] getEvents(final String s) throws RemoteException {
        EAuxiliaryStartupFailed eAuxiliaryStartupFailed;
        if (this.description.equalsIgnoreCase("AI")) {
            eAuxiliaryStartupFailed = new EAIWriterStartupFailed(super.config.aiWriter, this.description, s);
        }
        else if (this.description.equalsIgnoreCase("BI")) {
            eAuxiliaryStartupFailed = new EBIWriterStartupFailed(super.config.biWriter, this.description, s);
        }
        else if (this.description.equalsIgnoreCase("watchdog")) {
            eAuxiliaryStartupFailed = new EWatchdogStartupFailed(super.config.watchdog, this.description, s);
        }
        else {
            if (!this.description.equalsIgnoreCase("APW")) {
                return null;
            }
            eAuxiliaryStartupFailed = new EAPWStartupFailed(super.config.apWriter, this.description, s);
        }
        return new EventObject[] { eAuxiliaryStartupFailed, new EAuxiliaryProcessStartupFailed(super.config.database, eAuxiliaryStartupFailed) };
    }
    
    void handleError(final String s) {
        try {
            if (this.description.equalsIgnoreCase("AI")) {
                super.config.auxiliaries.removeAI();
                super.config.aiWriter.setIdle();
            }
            else if (this.description.equalsIgnoreCase("BI")) {
                super.config.auxiliaries.removeBI();
                super.config.biWriter.setIdle();
            }
            else if (this.description.equalsIgnoreCase("watchdog")) {
                super.config.auxiliaries.removeWatchdog();
                super.config.watchdog.setIdle();
            }
            else if (this.description.equalsIgnoreCase("APW")) {
                super.config.auxiliaries.removeAPW();
                super.config.apWriter.setIdle();
            }
        }
        catch (StateException ex) {
            Excp.print(ex);
        }
    }
}

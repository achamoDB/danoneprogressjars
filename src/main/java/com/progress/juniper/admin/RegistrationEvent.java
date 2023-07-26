// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.EventObject;

public class RegistrationEvent extends EventObject implements IRegistrationEvent
{
    String configName;
    
    public String configurationName() {
        return this.configName;
    }
    
    public RegistrationEvent(final Object o, final String configName) throws RemoteException {
        super(o);
        this.configName = null;
        this.configName = configName;
    }
}

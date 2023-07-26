// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EConfigurationStarted extends EJAEvent implements IRegistrationEvent
{
    public static String notificationName;
    public static String notificationType;
    String configName;
    
    public String configurationName() {
        return this.configName;
    }
    
    public EConfigurationStarted(final Object o, final String configName) throws RemoteException {
        super(o, o);
        this.configName = null;
        this.configName = configName;
    }
    
    static {
        EConfigurationStarted.notificationName = "EConfigurationStarted";
        EConfigurationStarted.notificationType = "application.state." + EConfigurationStarted.notificationName;
    }
}

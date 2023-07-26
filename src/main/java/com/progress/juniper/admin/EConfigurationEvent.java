// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EConfigurationEvent extends EJAEvent implements IEConfigurationEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EConfigurationEvent(final Object o, final IJAConfiguration ijaConfiguration) throws RemoteException {
        super(o, ijaConfiguration);
    }
    
    public IJAConfiguration configuration() {
        return (IJAConfiguration)this.getObject();
    }
    
    static {
        EConfigurationEvent.notificationName = "EConfigurationEvent";
        EConfigurationEvent.notificationType = "application.state." + EConfigurationEvent.notificationName;
    }
}

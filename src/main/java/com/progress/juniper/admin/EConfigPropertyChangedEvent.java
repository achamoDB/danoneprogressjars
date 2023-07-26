// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EConfigPropertyChangedEvent extends EPropertyChangedEvent implements IEConfigurationEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EConfigPropertyChangedEvent(final IJAConfiguration ijaConfiguration, final Object o) throws RemoteException {
        super(ijaConfiguration, o);
    }
    
    public IJAConfiguration configuration() throws RemoteException {
        return (IJAConfiguration)this.issuer();
    }
    
    static {
        EConfigPropertyChangedEvent.notificationName = "EConfigPropertyChangedEvent";
        EConfigPropertyChangedEvent.notificationType = "application.state." + EConfigPropertyChangedEvent.notificationName;
    }
}

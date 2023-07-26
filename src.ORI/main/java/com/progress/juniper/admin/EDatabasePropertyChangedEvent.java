// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EDatabasePropertyChangedEvent extends EPropertyChangedEvent implements IEDatabaseEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EDatabasePropertyChangedEvent(final IJADatabase ijaDatabase, final Object o) throws RemoteException {
        super(ijaDatabase, o);
    }
    
    public IJADatabase database() throws RemoteException {
        return (IJADatabase)this.issuer();
    }
    
    static {
        EDatabasePropertyChangedEvent.notificationName = "EDatabasePropertyChangedEvent";
        EDatabasePropertyChangedEvent.notificationType = "application.state." + EDatabasePropertyChangedEvent.notificationName;
    }
}

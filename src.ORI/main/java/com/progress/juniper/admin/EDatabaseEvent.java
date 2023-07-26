// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EDatabaseEvent extends EJAEvent implements IEDatabaseEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EDatabaseEvent(final Object o, final IJADatabase ijaDatabase) throws RemoteException {
        super(o, ijaDatabase);
    }
    
    public IJADatabase database() {
        return (IJADatabase)this.getObject();
    }
    
    static {
        EDatabaseEvent.notificationName = "EDatabaseEvent";
        EDatabaseEvent.notificationType = "application.state." + EDatabaseEvent.notificationName;
    }
}

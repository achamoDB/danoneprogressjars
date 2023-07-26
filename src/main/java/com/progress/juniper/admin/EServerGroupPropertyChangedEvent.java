// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EServerGroupPropertyChangedEvent extends EPropertyChangedEvent implements IEServerGroupEvent
{
    public static String notificationName;
    
    public EServerGroupPropertyChangedEvent(final IJAService ijaService, final Object o) throws RemoteException {
        super(ijaService, o);
    }
    
    public IJAService serverGroup() throws RemoteException {
        return (IJAService)this.issuer();
    }
    
    static {
        EServerGroupPropertyChangedEvent.notificationName = "EServerGroupPropertyChangedEvent";
    }
}

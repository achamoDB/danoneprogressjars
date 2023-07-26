// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EAREvent extends EJAEvent implements IEAREvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EAREvent(final Object o, final IJAAgent ijaAgent) throws RemoteException {
        super(o, ijaAgent);
    }
    
    public IJAAgent agent() {
        return (IJAAgent)this.getObject();
    }
    
    static {
        EAREvent.notificationName = "EAREvent";
        EAREvent.notificationType = "application.state." + EAREvent.notificationName;
    }
}

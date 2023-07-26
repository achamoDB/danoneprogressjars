// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.util.IEClientSpecificEvent;

public class EPropertyChangedEvent extends EJAEvent implements IEClientSpecificEvent
{
    public static String notificationName;
    public static String notificationType;
    
    public EPropertyChangedEvent(final Object o, final Object o2) throws RemoteException {
        super(o, o2);
    }
    
    public Object guiID() throws RemoteException {
        return this.getObject();
    }
    
    static {
        EPropertyChangedEvent.notificationName = "EPropertyChangedEvent";
        EPropertyChangedEvent.notificationType = "application.state." + EPropertyChangedEvent.notificationName;
    }
}

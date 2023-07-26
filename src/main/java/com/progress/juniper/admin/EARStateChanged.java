// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EARStateChanged extends EAREvent implements IEARStateChanged
{
    public static String notificationName;
    public static String notificationType;
    JAStatusObject statusObject;
    
    EARStateChanged(final IJAAgent ijaAgent, final JAStatusObject statusObject) throws RemoteException {
        super(ijaAgent, ijaAgent);
        this.statusObject = null;
        super.addEventData(statusObject);
        this.statusObject = statusObject;
    }
    
    public JAStatusObject getStatus() {
        return this.statusObject;
    }
    
    static {
        EARStateChanged.notificationName = "EARStateChanged";
        EARStateChanged.notificationType = "application.state." + EARStateChanged.notificationName;
    }
}

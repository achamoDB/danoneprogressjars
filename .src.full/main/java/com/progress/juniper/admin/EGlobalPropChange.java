// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EGlobalPropChange extends EJAEvent implements IStateProvider
{
    public static String notificationName;
    public static String notificationType;
    Object propChangedObj;
    JAStatusObject statusObject;
    
    EGlobalPropChange(final IJADatabase ijaDatabase, final Object propChangedObj, final JAStatusObject statusObject) throws RemoteException {
        super(((JADatabase)ijaDatabase).remoteStub(), propChangedObj);
        this.propChangedObj = null;
        this.statusObject = null;
        super.addEventData(statusObject);
        this.propChangedObj = propChangedObj;
        this.statusObject = statusObject;
    }
    
    public Object propChangedObj() throws RemoteException {
        return this.propChangedObj;
    }
    
    public JAStatusObject getStatus() {
        return this.statusObject;
    }
    
    static {
        EGlobalPropChange.notificationName = "EGlobalPropChange";
        EGlobalPropChange.notificationType = "application.state." + EGlobalPropChange.notificationName;
    }
}

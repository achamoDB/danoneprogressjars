// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.system;

import java.rmi.RemoteException;
import java.util.Hashtable;
import com.progress.common.networkevents.INotificationEvent;
import com.progress.common.networkevents.IEventObject;

public class EReloadLogFileViewer extends ESystemObject implements IEventObject, INotificationEvent
{
    public static String notificationType;
    
    public EReloadLogFileViewer(final Object o, final Hashtable hashtable) throws RemoteException {
        super(o, hashtable);
    }
    
    public String getNotificationName() throws RemoteException {
        return "EReloadLogFileViewer";
    }
    
    static {
        EReloadLogFileViewer.notificationType = "application.state.EReloadLogFileViewer";
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.international.resources.ProgressResources;
import com.progress.common.networkevents.IEventObject;

public class EStartupProcessFailure extends EDatabaseStartupEvent implements IEFailure, IENestedEvent
{
    public static String notificationName;
    public static String notificationType;
    IEventObject nestedEvent;
    
    public String reasonForFailure() throws RemoteException {
        final IEventObject nestedEvent = this.getNestedEvent();
        if (nestedEvent instanceof IEFailure) {
            return ((IEFailure)nestedEvent).reasonForFailure();
        }
        return ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "Reason for failure unknown");
    }
    
    public IEventObject getNestedEvent() {
        return this.nestedEvent;
    }
    
    public EStartupProcessFailure(final JADatabase jaDatabase, final IEventObject nestedEvent) throws RemoteException {
        super(jaDatabase);
        this.nestedEvent = null;
        this.nestedEvent = nestedEvent;
        super.setErrorString(this.reasonForFailure());
    }
    
    static {
        EStartupProcessFailure.notificationName = "EStartupProcessFailure";
        EStartupProcessFailure.notificationType = "application.state." + EStartupProcessFailure.notificationName;
    }
}

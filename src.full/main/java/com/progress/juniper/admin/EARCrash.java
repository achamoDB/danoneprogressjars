// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventObject;

public class EARCrash extends EAREvent implements IEventObject, IEFailure
{
    public static String notificationName;
    public static String notificationType;
    String reasonForFailure;
    
    public EARCrash(final IJAAgent ijaAgent) throws RemoteException {
        this(ijaAgent, "");
    }
    
    public EARCrash(final IJAAgent ijaAgent, final String s) throws RemoteException {
        super(ijaAgent, ijaAgent);
        this.reasonForFailure = null;
        super.setErrorString(s);
        this.reasonForFailure = s;
    }
    
    public String reasonForFailure() {
        return this.reasonForFailure;
    }
    
    static {
        EARCrash.notificationName = "EARCrash";
        EARCrash.notificationType = "application.state." + EARCrash.notificationName;
    }
}

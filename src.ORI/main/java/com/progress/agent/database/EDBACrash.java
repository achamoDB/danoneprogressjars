// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import java.rmi.RemoteException;
import com.progress.juniper.admin.IEFailure;
import com.progress.common.networkevents.IEventObject;

public class EDBACrash extends EDBAEvent implements IEventObject, IEFailure
{
    public static String notificationName;
    public static String notificationType;
    String reasonForFailure;
    
    public EDBACrash(final IAgentDatabase agentDatabase) throws RemoteException {
        this(agentDatabase, "");
    }
    
    public EDBACrash(final IAgentDatabase agentDatabase, final String s) throws RemoteException {
        super(agentDatabase, agentDatabase);
        this.reasonForFailure = null;
        super.setErrorString(s);
        this.reasonForFailure = s;
    }
    
    public String reasonForFailure() {
        return this.reasonForFailure;
    }
    
    static {
        EDBACrash.notificationName = "EDBACrash";
        EDBACrash.notificationType = "application.state." + EDBACrash.notificationName;
    }
}

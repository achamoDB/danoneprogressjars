// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import com.progress.international.resources.ProgressResources;

public class EMissingDatabaseFile extends EDatabaseStartupEvent implements IEStartupComplete, IEFailure
{
    public static String notificationName;
    public static String notificationType;
    
    public String reasonForFailure() {
        return ProgressResources.retrieveTranString("com.progress.international.messages.CMNMsgBundle", "File not found");
    }
    
    public EMissingDatabaseFile(final JADatabase jaDatabase) throws RemoteException {
        super(jaDatabase);
        super.setErrorString(this.reasonForFailure());
    }
    
    static {
        EMissingDatabaseFile.notificationName = "EMissingDatabaseFile";
        EMissingDatabaseFile.notificationType = "application.state." + EMissingDatabaseFile.notificationName;
    }
}

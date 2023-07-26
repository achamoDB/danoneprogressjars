// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;

public class EStartupProcessException extends EDatabaseStartupEvent implements IEStartupComplete, IEFailure
{
    public static String notificationName;
    public static String notificationType;
    Exception exception;
    
    public String reasonForFailure() {
        return "An unexpected exception occured: " + this.exception;
    }
    
    public EStartupProcessException(final JADatabase jaDatabase, final Exception exception) throws RemoteException {
        super(jaDatabase);
        this.exception = null;
        this.exception = exception;
        super.setErrorString(this.reasonForFailure());
    }
    
    static {
        EStartupProcessException.notificationName = "EStartupProcessException";
        EStartupProcessException.notificationType = "application.state." + EStartupProcessException.notificationName;
    }
}

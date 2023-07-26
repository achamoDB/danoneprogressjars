// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EUbrokerLogFileNameChanged extends EOpenEdgeManagementEvent
{
    public static String notificationType;
    public static String MAIN_LOG_FILE;
    public static String SERVER_LOG_FILE;
    
    public EUbrokerLogFileNameChanged(final Object o, final String s, final String s2, final String source) throws RemoteException {
        super(o);
        super.m_content = new COpenEdgeManagementContent(o.toString(), new Object[] { s2, s });
        super.m_source = source;
    }
    
    public String getNotificationName() throws RemoteException {
        return "EUbrokerLogFileNameChanged";
    }
    
    static {
        EUbrokerLogFileNameChanged.notificationType = "application.state.EUbrokerLogFileNameChanged";
        EUbrokerLogFileNameChanged.MAIN_LOG_FILE = "main";
        EUbrokerLogFileNameChanged.SERVER_LOG_FILE = "server";
    }
}

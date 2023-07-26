// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver;

import java.rmi.RemoteException;
import com.progress.ubroker.tools.IAdminRemote;

public interface INSRemote extends IAdminRemote
{
    Object[][] getSummaryStatus() throws RemoteException;
    
    NameServer.AppService[] getDetailStatus() throws RemoteException;
}

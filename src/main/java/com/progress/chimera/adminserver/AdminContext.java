// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.util.Vector;
import java.rmi.RemoteException;
import com.progress.common.rmiregistry.IPingable;
import java.rmi.server.UnicastRemoteObject;

public class AdminContext extends UnicastRemoteObject implements IAdminServer, IPingable
{
    private static AdminServer as;
    private String uname;
    private String pswd;
    
    public AdminContext(final AdminServer as, final String uname, final String pswd) throws RemoteException {
        this.uname = "";
        this.pswd = "";
        AdminContext.as = as;
        this.uname = uname;
        this.pswd = pswd;
    }
    
    public Vector getPlugins() throws RemoteException {
        return AdminContext.as.getPlugins(this.uname, this.pswd);
    }
    
    public Vector getPlugins(final String s) throws RemoteException {
        return AdminContext.as.getPlugins(this.uname, this.pswd, s);
    }
    
    public void shutdown() throws RemoteException {
        AdminContext.as.shutdown(this.uname);
    }
    
    public void ping() throws RemoteException {
    }
}

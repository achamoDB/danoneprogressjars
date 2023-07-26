// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.RemoteException;
import java.util.HashSet;

public class NonNTAdminServer extends AdminServer
{
    public NonNTAdminServer(final String[] array) throws RemoteException {
        this(array, null);
    }
    
    public NonNTAdminServer(final String[] array, final HashSet set) throws RemoteException {
        super(array, set);
    }
    
    public void openServiceEventObject() throws Exception {
    }
    
    public void signalServiceEventObject() throws Exception {
    }
    
    public void createDBEventObject(final String s) throws Exception {
    }
    
    public void closeDBEventObject(final String s) throws Exception {
    }
    
    public static void main(final String[] array) {
        try {
            new NonNTAdminServer(array);
        }
        catch (RemoteException ex) {
            System.err.println(AdminServerType.admBundle.getTranString("AdminServer failed to start:", new Object[] { ex.getMessage() }));
            System.exit(1);
        }
    }
}

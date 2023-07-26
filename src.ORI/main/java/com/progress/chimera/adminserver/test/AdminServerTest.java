// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver.test;

import java.rmi.RemoteException;
import com.progress.chimera.adminserver.AdminServer;
import com.progress.chimera.adminserver.NonNTAdminServer;

public class AdminServerTest extends NonNTAdminServer
{
    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;
    
    public AdminServerTest(final String[] array) throws RemoteException {
        super(array);
        final TestPlugInMgmtAPI testPlugInMgmtAPI = new TestPlugInMgmtAPI(this);
        testPlugInMgmtAPI.start();
        try {
            testPlugInMgmtAPI.join();
        }
        catch (InterruptedException ex) {}
        System.exit(0);
    }
}

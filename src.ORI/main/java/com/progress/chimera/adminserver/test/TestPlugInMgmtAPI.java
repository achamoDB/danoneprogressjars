// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver.test;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.progress.chimera.adminserver.AdminServer;

public class TestPlugInMgmtAPI extends Thread
{
    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;
    private static final String INVALID_INPUT = "Invalid input.  Try again.";
    AdminServer m_adminServer;
    
    void doTest(final AdminServer adminServer, final int n) {
        adminServer.testOutStream.println("");
        if (n == 1) {
            new ConvertAddress(adminServer);
        }
        else if (n == 2) {
            adminServer.testOutStream.println("getPluginInstances() test not supported.");
        }
        else if (n == 3) {
            new GetPluginInfo(adminServer);
        }
        else if (n == 4) {
            new GetPropertyValues(adminServer);
        }
    }
    
    public AdminServer getAdminServer() {
        return this.m_adminServer;
    }
    
    public void setAdminServer(final AdminServer adminServer) {
        this.m_adminServer = adminServer;
    }
    
    public TestPlugInMgmtAPI(final AdminServer adminServer) {
        this.m_adminServer = null;
        this.setAdminServer(adminServer);
    }
    
    public void run() {
        final AdminServer adminServer = this.getAdminServer();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(adminServer.testInStream));
            final boolean b = true;
            while (b) {
                adminServer.testOutStream.println("");
                adminServer.testOutStream.println("\t1. convertAddress()");
                adminServer.testOutStream.println("\t2. getPluginInstances()");
                adminServer.testOutStream.println("\t3. getPluginInfo()");
                adminServer.testOutStream.println("\t4. getPropertyValues()");
                adminServer.testOutStream.println("\t9. exit");
                adminServer.testOutStream.println("");
                adminServer.testOutStream.print("\tEnter (1-4, or 9 to exit): ");
                final String line = bufferedReader.readLine();
                try {
                    final int intValue = new Integer(Integer.parseInt(line));
                    if (intValue == 9) {
                        adminServer.shutdown("TestPlugInMgmtAPI");
                        System.exit(0);
                    }
                    if (intValue >= 1 && intValue <= 4) {
                        this.doTest(adminServer, intValue);
                    }
                    else {
                        adminServer.testOutStream.println("Invalid input.  Try again.");
                    }
                }
                catch (NumberFormatException ex) {
                    adminServer.testOutStream.println("Invalid input.  Try again.");
                }
                adminServer.testOutStream.println();
            }
        }
        catch (IOException obj) {
            adminServer.testErrStream.println("Problem reading input: " + obj);
        }
    }
}

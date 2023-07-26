// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver.test;

import java.io.IOException;
import com.progress.chimera.adminserver.PlugInMgmtAPI;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.progress.chimera.adminserver.AdminServer;

public class ConvertAddress
{
    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;
    
    public ConvertAddress(final AdminServer adminServer) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(adminServer.testInStream));
            final boolean b = true;
            while (b) {
                adminServer.testOutStream.println("convertAddress( String address )");
                adminServer.testOutStream.print("Enter an address (nothing to return to main): ");
                final String line = bufferedReader.readLine();
                if (line == null || line.length() == 0) {
                    adminServer.testOutStream.println("Returning to main menu...");
                    return;
                }
                try {
                    final String[] convertAddress = adminServer.convertAddress(line);
                    if (convertAddress != null) {
                        adminServer.testOutStream.println("Plugin name:   " + convertAddress[0]);
                        adminServer.testOutStream.println("Property name: " + convertAddress[1]);
                    }
                }
                catch (PlugInMgmtAPI.UnknownAddressFormatException x) {
                    adminServer.testErrStream.println(x);
                }
                adminServer.testOutStream.println();
            }
        }
        catch (IOException obj) {
            adminServer.testErrStream.println("Problem reading input: " + obj);
        }
    }
}

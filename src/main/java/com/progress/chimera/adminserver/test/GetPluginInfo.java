// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver.test;

import java.util.Vector;
import java.io.IOException;
import com.progress.chimera.adminserver.PlugInMgmtAPI;
import com.progress.chimera.adminserver.ServerPluginInfo;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.progress.chimera.adminserver.AdminServer;

public class GetPluginInfo
{
    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;
    
    public GetPluginInfo(final AdminServer adminServer) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(adminServer.testInStream));
            final boolean b = true;
            while (b) {
                adminServer.testOutStream.println("getPropertyInfo( String address )");
                adminServer.testOutStream.print("Enter an address (nothing to return to main): ");
                final String line = bufferedReader.readLine();
                if (line == null || line.length() == 0) {
                    adminServer.testOutStream.println("Returning to main menu...");
                    return;
                }
                try {
                    final Vector pluginInfo = adminServer.getPluginInfo(line);
                    for (int index = 0; pluginInfo != null && index < pluginInfo.size(); ++index) {
                        final ServerPluginInfo serverPluginInfo = pluginInfo.elementAt(index);
                        adminServer.testOutStream.println("");
                        adminServer.testOutStream.println("         Plugin name: " + serverPluginInfo.getId());
                        adminServer.testOutStream.println("               class: " + serverPluginInfo.getClassName());
                        adminServer.testOutStream.print("       license codes: ");
                        final Vector productNumberList = serverPluginInfo.getProductNumberList();
                        for (int index2 = 0; productNumberList != null && index2 < productNumberList.size(); ++index2) {
                            adminServer.testOutStream.println("                      " + productNumberList.elementAt(index2).trim());
                        }
                        adminServer.testOutStream.print("           arguments: ");
                        final String[] args = serverPluginInfo.getArgs();
                        for (int n = 0; args != null && n < args.length; ++n) {
                            adminServer.testOutStream.println("                      " + args[n].trim());
                        }
                        adminServer.testOutStream.println();
                        adminServer.testOutStream.println();
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

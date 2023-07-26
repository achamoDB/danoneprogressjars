// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver.test;

import java.util.Vector;
import java.io.IOException;
import com.progress.chimera.adminserver.PlugInMgmtAPI;
import com.progress.common.property.PropertyList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.progress.chimera.adminserver.AdminServer;

public class GetPropertyValues
{
    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;
    
    public GetPropertyValues(final AdminServer adminServer) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(adminServer.testInStream));
            final boolean b = true;
            while (b) {
                adminServer.testOutStream.println("getPropertyValues( String address )");
                adminServer.testOutStream.print("Enter a name (nothing to return to main): ");
                final String line = bufferedReader.readLine();
                if (line == null || line.length() == 0) {
                    adminServer.testOutStream.println("Returning to main menu...");
                    return;
                }
                try {
                    final PropertyList propertyValues = adminServer.getPropertyValues(line);
                    if (propertyValues != null) {
                        final Vector propertyElements = propertyValues.getPropertyElements();
                        for (int i = 0; i < propertyElements.size(); ++i) {
                            final PropertyList.PropertyElement propertyElement = propertyElements.elementAt(i);
                            final String plugInName = propertyElement.getPlugInName();
                            final String propertyGroup = propertyElement.getPropertyGroup();
                            final String propertyName = propertyElement.getPropertyName();
                            final Vector propertyValues2 = propertyElement.getPropertyValues();
                            if (propertyValues2 != null) {
                                for (int j = 0; j < propertyValues2.size(); ++j) {
                                    adminServer.testOutStream.println(plugInName + "." + propertyGroup + "." + propertyName + "=" + propertyValues2.elementAt(j));
                                }
                            }
                        }
                    }
                    else {
                        adminServer.testOutStream.println("Unknown pluginName.propertyName: " + line);
                    }
                }
                catch (PlugInMgmtAPI.UnknownAddressFormatException x) {
                    adminServer.testErrStream.println(x);
                }
                catch (PlugInMgmtAPI.UnknownPluginException x2) {
                    adminServer.testErrStream.println(x2);
                }
                adminServer.testOutStream.println();
            }
        }
        catch (IOException obj) {
            adminServer.testErrStream.println("Problem reading input: " + obj);
        }
    }
}

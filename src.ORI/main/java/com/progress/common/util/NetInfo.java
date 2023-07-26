// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.util;

import java.net.UnknownHostException;
import com.progress.chimera.common.Tools;
import java.net.InetAddress;

public class NetInfo
{
    public static String getIPAddress(final String str) {
        String hostAddress = null;
        InetAddress byName = null;
        try {
            byName = InetAddress.getByName(str);
        }
        catch (Throwable t) {
            Tools.px(t, "Error instantiating InetAddress object for: " + str);
        }
        if (byName != null) {
            try {
                hostAddress = byName.getHostAddress();
            }
            catch (Throwable t2) {
                Tools.px(t2, "Error obtaining IP Address for: " + str);
            }
        }
        return hostAddress;
    }
    
    public static String getLocalMachineIPAddress() {
        String hostAddress = null;
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        }
        catch (Throwable t) {
            Tools.px(t, "Error instantiating InetAddress object for local machine");
        }
        if (localHost != null) {
            try {
                hostAddress = localHost.getHostAddress();
            }
            catch (Throwable t2) {
                Tools.px(t2, "Error obtaining IP Address for local machine");
            }
        }
        return hostAddress;
    }
    
    public static String getLocalMachineHostName() {
        String hostName = null;
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        }
        catch (Throwable t) {
            Tools.px(t, "Error instantiating InetAddress object for local machine");
        }
        if (localHost != null) {
            try {
                hostName = localHost.getHostName();
            }
            catch (Throwable t2) {
                Tools.px(t2, "Error obtaining host name for local machine");
            }
        }
        return hostName;
    }
    
    public static boolean isLocalHost(final String host) {
        boolean b = false;
        InetAddress[] allByName = null;
        InetAddress[] allByName2 = null;
        if (host != null && host.equalsIgnoreCase("localhost")) {
            b = true;
        }
        else if (host != null) {
            try {
                allByName = InetAddress.getAllByName(host);
                allByName2 = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
            }
            catch (UnknownHostException ex) {
                b = false;
            }
        }
        if (allByName != null && allByName2 != null && allByName.length > 0 && allByName2.length > 0) {
            for (int i = 0; i < allByName.length; ++i) {
                for (int j = 0; j < allByName2.length; ++j) {
                    if (allByName[i].getHostAddress().equals(allByName2[j].getHostAddress())) {
                        b = true;
                        break;
                    }
                }
            }
        }
        return b;
    }
}

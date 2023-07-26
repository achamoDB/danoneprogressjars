// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class SimpleAuthPrompt implements AuthorizationPrompter
{
    public NVPair getUsernamePassword(final AuthorizationInfo authorizationInfo, final boolean b) {
        if (authorizationInfo.getScheme().equalsIgnoreCase("SOCKS5")) {
            System.out.println("Enter username and password for SOCKS server on host " + authorizationInfo.getHost());
            System.out.println("Authentication Method: username/password");
        }
        else {
            System.out.println("Enter username and password for realm `" + authorizationInfo.getRealm() + "' on host " + authorizationInfo.getHost() + ":" + authorizationInfo.getPort());
            System.out.println("Authentication Scheme: " + authorizationInfo.getScheme());
        }
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Username: ");
        System.out.flush();
        String line;
        try {
            line = bufferedReader.readLine();
        }
        catch (IOException ex) {
            return null;
        }
        if (line == null || line.length() == 0) {
            return null;
        }
        echo(false);
        System.out.print("Password: ");
        System.out.flush();
        String line2;
        try {
            line2 = bufferedReader.readLine();
        }
        catch (IOException ex2) {
            return null;
        }
        System.out.println();
        echo(true);
        if (line2 == null) {
            return null;
        }
        return new NVPair(line, line2);
    }
    
    private static void echo(final boolean b) {
        final String property = System.getProperty("os.name");
        String[] cmdarray = null;
        if (property.equalsIgnoreCase("Windows 95") || property.equalsIgnoreCase("Windows NT")) {
            cmdarray = new String[] { "echo", b ? "on" : "off" };
        }
        else if (!property.equalsIgnoreCase("Windows")) {
            if (!property.equalsIgnoreCase("16-bit Windows")) {
                if (!property.equalsIgnoreCase("OS/2")) {
                    if (!property.equalsIgnoreCase("Mac OS")) {
                        if (!property.equalsIgnoreCase("MacOS")) {
                            if (property.equalsIgnoreCase("OpenVMS") || property.equalsIgnoreCase("VMS")) {
                                cmdarray = new String[] { "SET TERMINAL " + (b ? "/ECHO" : "/NOECHO") };
                            }
                            else {
                                cmdarray = new String[] { "/bin/sh", "-c", "stty " + (b ? "echo" : "-echo") + " < /dev/tty" };
                            }
                        }
                    }
                }
            }
        }
        if (cmdarray != null) {
            try {
                Runtime.getRuntime().exec(cmdarray).waitFor();
            }
            catch (Exception ex) {}
        }
    }
    
    static boolean canUseCLPrompt() {
        final String property = System.getProperty("os.name");
        return property.indexOf("Linux") >= 0 || property.indexOf("SunOS") >= 0 || property.indexOf("Solaris") >= 0 || property.indexOf("BSD") >= 0 || property.indexOf("AIX") >= 0 || property.indexOf("HP-UX") >= 0 || property.indexOf("IRIX") >= 0 || property.indexOf("OSF") >= 0 || property.indexOf("A/UX") >= 0 || property.indexOf("VMS") >= 0;
    }
}

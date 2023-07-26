// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import com.progress.common.util.Environment;
import java.util.Vector;

public class DynamicPropertyValues implements IPropConst
{
    public static Object getSrvrAppMode(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("Development");
        vector.addElement("Production");
        return vector;
    }
    
    public static Object getWSSrvrDbgOptions(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("Enabled");
        vector.addElement("Disabled");
        vector.addElement("Default");
        return vector;
    }
    
    public static Object getWSOperatingMode(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("Stateless");
        return vector;
    }
    
    public static Object getASOperatingMode(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("Stateless");
        vector.addElement("State-aware");
        vector.addElement("State-reset");
        vector.addElement("State-free");
        return vector;
    }
    
    public static Object getASKeepaliveMode(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("denyClientASK,denyServerASK");
        vector.addElement("denyClientASK,allowServerASK");
        vector.addElement("allowClientASK,denyServerASK");
        vector.addElement("allowClientASK,allowServerASK");
        return vector;
    }
    
    public static Object getOROperatingMode(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("State-aware");
        return vector;
    }
    
    public static Object getODOperatingMode(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("State-aware");
        return vector;
    }
    
    public static Object getMSSOperatingMode(final String s) {
        return getODOperatingMode(s);
    }
    
    public static Object getDataServerSrvrMinPort(final String s) {
        int value = 3000;
        if (isUnix()) {
            value = 1025;
        }
        return new Integer(value);
    }
    
    public static Object getDataServerSrvrMaxPort(final String s) {
        int value = 5000;
        if (isUnix()) {
            value = 2000;
        }
        return new Integer(value);
    }
    
    public static Object getNSLocationChoices(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("local");
        vector.addElement("remote");
        return vector;
    }
    
    public static Object getWsaLocationChoices(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("local");
        vector.addElement("remote");
        return vector;
    }
    
    public static Object getRegistrationMode(final String s) {
        final Vector<String> vector = new Vector<String>();
        vector.addElement("Register-IP");
        vector.addElement("Register-LocalHost");
        vector.addElement("Register-HostName");
        return vector;
    }
    
    public static Object getPropathDefault(final String s) {
        return (System.getProperty("os.name").indexOf("Windows") < 0) ? "${PROPATH}:${WRKDIR}" : "@{WinChar Startup\\PROPATH};@{WorkPath}";
    }
    
    public static Object getWorkDirDefault(final String s) {
        return (System.getProperty("os.name").indexOf("Windows") < 0) ? "$WRKDIR" : "@{WorkPath}";
    }
    
    public static Object getUbBrokerLogFileDefault(final String s) {
        return getWorkDirDefault(s) + System.getProperty("file.separator") + "broker.log";
    }
    
    public static Object getUbSrvrLogFileDefault(final String s) {
        return getWorkDirDefault(s) + System.getProperty("file.separator") + "server.log";
    }
    
    public static Object getAdapterSrvrLogFileDefault(final String s) {
        return getUbSrvrLogFileDefault(s);
    }
    
    public static Object getAdapterBrkrLogFileDefault(final String s) {
        return getUbBrokerLogFileDefault(s);
    }
    
    public static Object getAiaLogFileDefault(final String s) {
        return getWorkDirDefault(s) + System.getProperty("file.separator") + "aia.log";
    }
    
    public static Object getMessengersLogFileDefault(final String s) {
        return getWorkDirDefault(s) + System.getProperty("file.separator") + "msgr.log";
    }
    
    public static Object getNsSrvrLogFileDefault(final String s) {
        return getWorkDirDefault(s) + System.getProperty("file.separator") + "ns.log";
    }
    
    public static Object getWsaLogFileDefault(final String s) {
        return new Environment().expandPropertyValue((String)getWorkDirDefault(s)) + System.getProperty("file.separator") + "wsa.log";
    }
    
    public static Object getWsSrvrStartupParamDefault(final String s) {
        if (isUnix()) {
            return "-p web/objects/web-disp.p -weblogerror";
        }
        return "-p web\\objects\\web-disp.p -weblogerror";
    }
    
    public static Object getWsRootDefault(final String s) {
        return "";
    }
    
    public static Object getWsSrvrExecFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/_progres";
        }
        return "\"@{Startup\\DLC}\\bin\\_progres.exe\"";
    }
    
    public static Object getAsSrvrExecFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/_proapsv";
        }
        return "\"@{Startup\\DLC}\\bin\\_proapsv.exe\"";
    }
    
    public static Object getOrSrvrExecFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/_orasrv";
        }
        return "\"@{Startup\\DLC}\\bin\\_orasrv.exe\"";
    }
    
    public static Object getOdSrvrExecFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/_odbsrv";
        }
        return "\"@{Startup\\DLC}\\bin\\_odbsrv.exe\"";
    }
    
    public static Object getMsSrvrExecFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/_msssrv";
        }
        return "\"@{Startup\\DLC}\\bin\\_msssrv.exe\"";
    }
    
    public static Object getCGIIPMsngrExecFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/cgiip";
        }
        return "@{Startup\\DLC}\\bin\\cgiip.exe";
    }
    
    public static Object getWSISAMsngrExecFileDefault(final String s) {
        if (isUnix()) {
            return "";
        }
        return "@{Startup\\DLC}\\bin\\wsisa.dll";
    }
    
    public static Object getWSNSAMsngrExecFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/wsnsa.dll";
        }
        return "@{Startup\\DLC}\\bin\\wsnsa.dll";
    }
    
    public static Object getWSASPMsngrExecFileDefault(final String s) {
        if (isUnix()) {
            return "";
        }
        return "@{Startup\\DLC}\\bin\\wsasp.dll";
    }
    
    public static Object getCGIIPMsngrScriptFileDefault(final String s) {
        if (isUnix()) {
            return "$DLC/bin/wspd_cgi.sh";
        }
        return "";
    }
    
    public static Object getKeyStorePathDefault(final String s) {
        if (isUnix()) {
            return "$DLC/keys/";
        }
        return "@{Startup\\DLC}\\keys\\";
    }
    
    public static Object getCertStorePathDefault(final String s) {
        if (isUnix()) {
            return "$DLC/certs/";
        }
        return "@{Startup\\DLC}\\certs\\";
    }
    
    public static Object getCertStorePscDefault(final String s) {
        if (isUnix()) {
            return "$DLC/certs/psccerts.jar";
        }
        return "@{Startup\\DLC}\\certs\\psccerts.jar";
    }
    
    public static boolean isUnix() {
        return System.getProperty("os.name").indexOf("Windows") < 0;
    }
    
    public static Object getUbMqBrokerLogFileDefault(final String s) {
        return getWorkDirDefault(s) + System.getProperty("file.separator") + "mqbroker.log";
    }
    
    public static Object getAsMqBrokerLogFileDefault(final String s) {
        return getUbMqBrokerLogFileDefault(s);
    }
    
    public static Object getWsMqBrokerLogFileDefault(final String s) {
        return getUbMqBrokerLogFileDefault(s);
    }
    
    public static Object getUbMqServerLogFileDefault(final String s) {
        return getWorkDirDefault(s) + System.getProperty("file.separator") + "mqserver.log";
    }
    
    public static Object getAsMqServerLogFileDefault(final String s) {
        return getUbMqServerLogFileDefault(s);
    }
    
    public static Object getWsMqServerLogFileDefault(final String s) {
        return getUbMqServerLogFileDefault(s);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.chimera.adminserver.AdminServer;
import java.io.File;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.text.UnquotedString;
import com.progress.common.comsock.ComMsg;
import com.progress.chimera.common.Tools;
import com.progress.chimera.log.ConnectionManagerLog;
import java.util.Hashtable;
import com.progress.message.jpMsg;
import com.progress.common.comsock.ServerComSockListener;

class ConfigListener extends Thread implements MProservJuniperAPI, ServerComSockListener, jpMsg
{
    static Hashtable idLookup;
    static boolean idLookupDisabled;
    static ConnectionManagerLog theLog;
    int messageCount;
    int connectionCount;
    int runningCount;
    
    ConfigListener() {
        this.messageCount = 0;
        this.connectionCount = 0;
        this.runningCount = 0;
        this.setName("Juniper Plug-in Comsock Listener");
    }
    
    public synchronized void clientConnect(final int value) {
        ConfigListener.theLog.log(4, 7669630165411963175L, new Object[] { new Integer(value) });
    }
    
    public void ignoreInput() {
        ConfigListener.idLookupDisabled = true;
    }
    
    public synchronized void clientDisconnect(final int n) {
        ConfigListener.theLog.log(4, 7669630165411963176L, new Object[] { new Integer(n) });
        if (ConfigListener.idLookup == null || ConfigListener.idLookupDisabled) {
            return;
        }
        final JAConfiguration jaConfiguration = ConfigListener.idLookup.get(new Integer(n));
        if (jaConfiguration == null) {
            return;
        }
        if (jaConfiguration.isShuttingDown()) {
            ConfigListener.theLog.log(3, 7669630165411963177L, new Object[] { jaConfiguration.name() });
            jaConfiguration.exiting();
        }
        else {
            ConfigListener.theLog.log(3, 7669630165411963178L, new Object[] { jaConfiguration.name() });
            try {
                jaConfiguration.handleCrash();
                this.cleanup(jaConfiguration, n);
            }
            catch (Throwable t) {
                Tools.px(t);
            }
        }
    }
    
    synchronized void cleanup(final JAConfiguration jaConfiguration, final int value) {
        jaConfiguration.id = null;
        if (ConfigListener.idLookup == null || ConfigListener.idLookupDisabled) {
            return;
        }
        ConfigListener.idLookup.remove(new Integer(value));
    }
    
    public int getMessageCount() {
        return this.messageCount;
    }
    
    public int getConnectionCount() {
        return this.connectionCount;
    }
    
    public int getRunningCount() {
        return this.runningCount;
    }
    
    public void resetConnectionCount() {
        this.connectionCount = 0;
    }
    
    public synchronized void messageReceived(final int value, final ComMsg comMsg) {
        ConfigListener.theLog.log(4, 7669630165411963180L, new Object[] { comMsg, new Integer(value) });
        if (ConfigListener.idLookup == null || ConfigListener.idLookupDisabled) {
            return;
        }
        try {
            final String msgName = comMsg.getMsgName();
            ++this.messageCount;
            if (msgName.equals("STRD")) {
                ConfigListener.theLog.log(4, 7669630165411963181L);
                ConfigListener.theLog.log(4, 7669630165411963182L, new Object[] { new String(comMsg.getData()) });
                final String string = new UnquotedString(new String(comMsg.getData())).toString();
                final String configFile = JAPlugIn.getConfigFile();
                final String substring = string.substring(0, string.indexOf(";"));
                final String substring2 = string.substring(string.indexOf(";") + 1);
                final JAConfiguration configuration = JAConfiguration.findConfiguration(substring2);
                final boolean b = configuration != null && configuration.isClustered();
                final boolean startsWith = System.getProperty("os.name").startsWith("Windows");
                ConfigListener.theLog.log(4, 7669630165411963184L, new Object[] { substring });
                ConfigListener.theLog.log(4, 7669630165411963185L, new Object[] { substring2 });
                if (!substring.equals(configFile) && (!b || !startsWith)) {
                    ConfigListener.theLog.logErr(7669630165411963188L, new Object[] { JAPlugIn.getConfigFile() });
                    JAPlugIn.getSocketIntf().send(value, new ComMsg("REGD"));
                    return;
                }
                if (configuration != null) {
                    ++this.connectionCount;
                    ConfigListener.theLog.log(4, 7669630165411963186L, new Object[] { configuration, new Integer(value) });
                    configuration.id = new Integer(value);
                    ConfigListener.idLookup.put(new Integer(value), configuration);
                    configuration.plugIn.getEventBroker().postEvent(new EConfigurationStarted(configuration.fauxJuniper, substring2));
                    return;
                }
                ConfigListener.theLog.logErr(7669630165411963187L, new Object[] { substring2 });
                JAPlugIn.getSocketIntf().send(value, new ComMsg("REGD"));
            }
            else {
                final JAConfiguration jaConfiguration = ConfigListener.idLookup.get(new Integer(value));
                if (jaConfiguration == null) {
                    ConfigListener.theLog.logErr(3, "Invalid connection message received: " + msgName);
                    JAPlugIn.getSocketIntf().disconnect(value);
                    return;
                }
                if (msgName.equals("RUNG")) {
                    ConfigListener.theLog.log(4, 7669630165411963189L);
                    String s = jaConfiguration.database.physicalName().trim();
                    if (!s.toLowerCase().endsWith(".db")) {
                        s += ".db";
                    }
                    final File file = new File(s);
                    String canonicalPath = s;
                    if (file != null) {
                        canonicalPath = file.getCanonicalPath();
                    }
                    try {
                        final JAPlugIn plugIn = jaConfiguration.plugIn;
                        ((AdminServer)JAPlugIn.adminServer()).createDBEventObject(canonicalPath);
                    }
                    catch (Exception ex) {
                        Tools.px(ex, "Failed to create DB EventObject.");
                    }
                    try {
                        final String s2 = new String(comMsg.getData());
                        if (s2.substring(0, 1).equals("1")) {
                            jaConfiguration.startedByMe = true;
                        }
                        else {
                            jaConfiguration.startedByMe = false;
                        }
                        if (s2.substring(1, 2).equals("1")) {
                            jaConfiguration.aiEnabled = true;
                        }
                        else {
                            jaConfiguration.aiEnabled = false;
                        }
                        if (s2.substring(2, 3).equals("1")) {
                            jaConfiguration.replEnabled = true;
                        }
                        else {
                            jaConfiguration.replEnabled = false;
                        }
                        if (s2.substring(3, 4).equals("1")) {
                            jaConfiguration.clustEnabled = true;
                        }
                        else {
                            jaConfiguration.clustEnabled = false;
                        }
                    }
                    catch (Exception ex2) {}
                    ++this.runningCount;
                    jaConfiguration.handleRunning();
                    jaConfiguration.handleAgent();
                    return;
                }
                if (!msgName.equals("BSHU")) {
                    if (msgName.equals("SGRN")) {
                        final String string2 = new UnquotedString(new String(comMsg.getData())).toString();
                        final JAService servicebyPort = jaConfiguration.findServicebyPort(string2);
                        ConfigListener.theLog.log(4, 7669630165411963190L, new Object[] { servicebyPort, string2 });
                        if (servicebyPort == null) {
                            Tools.px(new Exception("Looking up server group by port: " + string2 + " - not found"));
                            return;
                        }
                        jaConfiguration.secondaryUp(servicebyPort);
                    }
                    else if (msgName.equals("SGCR")) {
                        final String string3 = new UnquotedString(new String(comMsg.getData())).toString();
                        final JAService servicebyPort2 = jaConfiguration.findServicebyPort(string3);
                        ConfigListener.theLog.log(4, 7669630165411963191L, new Object[] { servicebyPort2, string3 });
                        if (servicebyPort2 == null) {
                            Tools.px(new Exception("Looking up server group by port: " + string3 + " - not found"));
                            return;
                        }
                        jaConfiguration.secondaryCrash(servicebyPort2);
                    }
                    else if (msgName.equals("BIRN")) {
                        ConfigListener.theLog.log(4, "BI up message received");
                        jaConfiguration.biUp();
                    }
                    else if (msgName.equals("BICR")) {
                        ConfigListener.theLog.log(3, "BI crash message received");
                        jaConfiguration.biCrash();
                    }
                    else if (msgName.equals("AIRN")) {
                        ConfigListener.theLog.log(4, "AI up message received");
                        jaConfiguration.aiUp();
                    }
                    else if (msgName.equals("AICR")) {
                        ConfigListener.theLog.log(3, "AI crash message received");
                        jaConfiguration.aiCrash();
                    }
                    else if (msgName.equals("WDRN")) {
                        ConfigListener.theLog.log(4, "Watchdog up message received");
                        jaConfiguration.watchdogUp();
                    }
                    else if (msgName.equals("WDCR")) {
                        ConfigListener.theLog.log(3, "Watchdog crash message received");
                        jaConfiguration.watchdogCrash();
                    }
                    else if (msgName.equals("AWRN")) {
                        int int1;
                        try {
                            comMsg.getData();
                            int1 = Integer.parseInt(new String(comMsg.getData()));
                        }
                        catch (Throwable t) {
                            Tools.px(t);
                            return;
                        }
                        ConfigListener.theLog.log(4, "APW up message received, count=: " + int1);
                        jaConfiguration.apwsUp(int1);
                    }
                    else if (msgName.equals("AWCR")) {
                        ConfigListener.theLog.log(3, "APW crash message received");
                        jaConfiguration.apwCrash();
                    }
                    else if (msgName.equals("CONT")) {
                        int int2 = 5;
                        try {
                            if (comMsg.getData() != null) {
                                int2 = Integer.parseInt(new String(comMsg.getData()));
                            }
                        }
                        catch (Throwable t3) {}
                        jaConfiguration.continuation(int2);
                    }
                    else if (msgName.equals("URSD")) {
                        if (!jaConfiguration.isShuttingDown()) {
                            JAPlugIn.getSocketIntf().disconnect(value);
                            jaConfiguration.setState(CStateShuttingDown.get(true));
                            jaConfiguration.configRequestedShutDown();
                        }
                    }
                    else if (msgName.equals("AGSD")) {
                        jaConfiguration.configRequestedAbnormalGracefulShutDown();
                    }
                    else if (msgName.equals("AFSD")) {
                        jaConfiguration.configRequestedAbnormalForcedShutDown();
                    }
                    else if (msgName.equals("RSRN")) {
                        ConfigListener.theLog.log(4, "RSRN");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RSCR")) {
                        ConfigListener.theLog.log(4, "RSCR");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RARN")) {
                        ConfigListener.theLog.log(4, "RARN");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RACR")) {
                        ConfigListener.theLog.log(4, "RACR");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RCRN")) {
                        ConfigListener.theLog.log(4, "RCRN");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RCCR")) {
                        ConfigListener.theLog.log(4, "RCCR");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RASD")) {
                        ConfigListener.theLog.log(4, "RASD");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("ARAD")) {
                        ConfigListener.theLog.log(4, "ARAD");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("URAD")) {
                        ConfigListener.theLog.log(4, "URAD");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("ARID")) {
                        ConfigListener.theLog.log(4, "ARID");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RSTA")) {
                        ConfigListener.theLog.log(4, "RSTA");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RSTM")) {
                        ConfigListener.theLog.log(4, "RSTM");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("RSIR")) {
                        ConfigListener.theLog.log(4, "RSIR");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + new String(comMsg.getData()));
                    }
                    else if (msgName.equals("ARCL")) {
                        ConfigListener.theLog.log(4, "ARCL");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: " + comMsg.toString());
                    }
                    else if (msgName.equals("TSTA")) {
                        ConfigListener.theLog.log(4, "TEST");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: no data");
                    }
                    else if (msgName.equals("TSTD")) {
                        ConfigListener.theLog.log(4, "TSTD");
                        ConfigListener.theLog.log(4, "Query: " + msgName + " Data: no data");
                    }
                    else if (msgName.equals("DARN")) {
                        ConfigListener.theLog.log(4, "DB Agent up message received");
                        jaConfiguration.agentRemoteUp();
                    }
                    else if (msgName.equals("DASD")) {
                        ConfigListener.theLog.log(4, "DB Agent shut down message received");
                        jaConfiguration.agentRemoteShutdown();
                    }
                    else if (msgName.equals("DACR")) {
                        ConfigListener.theLog.log(4, "DB Agent crashed message received");
                        jaConfiguration.agentRemoteCrash();
                    }
                    else {
                        ConfigListener.theLog.log(3, "+++++++++++ Unhandled message: " + msgName);
                    }
                }
            }
        }
        catch (Throwable t2) {
            Tools.px(t2, "Error handling message from mprosrv");
        }
    }
    
    static {
        ConfigListener.idLookup = new Hashtable();
        ConfigListener.idLookupDisabled = false;
        ConfigListener.theLog = JAPlugIn.theLog;
    }
}

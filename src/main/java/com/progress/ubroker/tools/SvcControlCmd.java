// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.tools;

import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import com.progress.common.util.PscURLParser;
import java.rmi.Remote;
import com.progress.ubroker.management.events.EAbnormalShutdownEvent;
import com.progress.ubroker.management.events.EUbrokerProcessActiveEvent;
import com.progress.ubroker.management.events.COpenEdgeManagementContent;
import com.progress.ubroker.tools.events.EStopServiceEvent;
import com.progress.common.networkevents.IEventObject;
import com.progress.ubroker.tools.events.EStartServiceEvent;
import java.rmi.RemoteException;
import java.util.StringTokenizer;
import com.progress.common.util.Environment;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.progress.common.log.ProLog;
import com.progress.common.text.UnquotedString;
import com.progress.ubroker.management.IRemoteManager;
import java.util.Hashtable;
import com.progress.ubroker.util.UBPreferenceProperties;
import com.progress.ubroker.util.IPropConst;

public class SvcControlCmd implements IPropConst, IBTMsgCodes
{
    public static String NEWLINE;
    public static int m_connect_retries;
    public static int m_getStatus_retries;
    public static long m_ping_retries;
    public static int m_shutdown_retries;
    public static int m_toolConnectSvcRetryInterval;
    public static int m_toolShutdownSvcConfirmRetryInterval;
    public static String PROCESS_START_ERROR;
    public static boolean m_prefSet;
    public String m_svcName;
    public String m_rmiURL;
    public long m_state;
    public IAdminRemote m_svcAdminRemote;
    public SvcStartArgsPkt m_argsPkt;
    boolean DEBUG_TRACE;
    BrokerRunningStateWatcher stateWatcher;
    
    public static synchronized void setPreferences(final UBPreferenceProperties ubPreferenceProperties) {
        SvcControlCmd.m_connect_retries = ubPreferenceProperties.m_toolConnectSvcRetry;
        SvcControlCmd.m_getStatus_retries = ubPreferenceProperties.m_toolGetSvcStatusRetry;
        SvcControlCmd.m_ping_retries = ubPreferenceProperties.m_toolPingSvcRetry;
        SvcControlCmd.m_shutdown_retries = ubPreferenceProperties.m_toolShutdownSvcConfirmRetry;
        SvcControlCmd.m_toolConnectSvcRetryInterval = ubPreferenceProperties.m_toolConnectSvcRetryInterval;
        SvcControlCmd.m_toolShutdownSvcConfirmRetryInterval = ubPreferenceProperties.m_toolShutdownSvcConfirmRetryInterval;
        SvcControlCmd.m_prefSet = true;
    }
    
    public SvcControlCmd(final SvcStartArgsPkt argsPkt, final String svcName, final String s, final String s2) {
        this.m_state = 0L;
        this.m_svcAdminRemote = null;
        this.m_argsPkt = null;
        this.DEBUG_TRACE = false;
        this.m_argsPkt = argsPkt;
        if (!SvcControlCmd.m_prefSet) {
            this.getPreferences();
        }
        this.m_svcName = svcName;
        this.m_rmiURL = this.getRmiURL(s, s2, this.m_svcName);
    }
    
    public Hashtable getData(final String[] array) {
        Hashtable hashtable = null;
        try {
            this.silentPingRetry(SvcControlCmd.m_getStatus_retries);
            if (this.m_state == 1L) {
                hashtable = (Hashtable)((IRemoteManager)this.m_svcAdminRemote).getData(array);
            }
        }
        catch (Exception ex) {
            this.logException(7094295313015381995L, "getData", ex);
        }
        return hashtable;
    }
    
    public String getRmiURL(final String str, final String str2, final String str3) {
        return "rmi://" + str + ":" + str2 + "/" + str3;
    }
    
    public void setRmiURL(final String rmiURL) {
        this.m_rmiURL = rmiURL;
    }
    
    public void setRetryCounts(final int connect_retries, final int getStatus_retries, final long ping_retries, final int shutdown_retries) {
        SvcControlCmd.m_connect_retries = connect_retries;
        SvcControlCmd.m_getStatus_retries = getStatus_retries;
        SvcControlCmd.m_ping_retries = ping_retries;
        SvcControlCmd.m_shutdown_retries = shutdown_retries;
    }
    
    public void setStateStart() {
        if (this.m_state != 2L) {
            this.m_state = 2L;
        }
    }
    
    public void setStateStop() {
        if (this.m_state != 4L) {
            this.m_state = 4L;
        }
    }
    
    public void resetState() {
        this.m_state = -1L;
    }
    
    public synchronized boolean startService() {
        if (this.m_state == 2L || this.m_state == 1L) {
            UBToolsMsg.logMsg(UBToolsMsg.getMsg(7094295313015381998L));
            return true;
        }
        if (this.DEBUG_TRACE) {
            this.logMsg("SvcControlCmd: Beginning of startService");
        }
        this.m_argsPkt.updateConfig();
        final SvcStartArgs svcStartArgs = new SvcStartArgs(this.m_argsPkt, this.m_rmiURL);
        final String[] args = svcStartArgs.getArgs();
        final String[][] envVars = svcStartArgs.getEnvVars();
        Process exec = null;
        final StringBuffer sb = new StringBuffer(512);
        String filterJVMStartStatus = null;
        String pid = null;
        boolean b = false;
        try {
            final StringBuffer sb2 = new StringBuffer();
            for (int i = 0; i < args.length; ++i) {
                final String string = new UnquotedString(args[i]).toString();
                if ("-u".equals(string) && i + 1 < args.length) {
                    String str = new UnquotedString(args[i + 1]).toString();
                    final int index = str.indexOf(58);
                    if (index != -1) {
                        str = str.substring(0, index) + ":***";
                    }
                    sb2.append("-u " + str + " ");
                    ++i;
                }
                else {
                    sb2.append(string + " ");
                }
            }
            ProLog.logd("OpenEdge", 3, sb2.toString());
            exec = Runtime.getRuntime().exec(args);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(exec.getOutputStream()));
            if (envVars != null) {
                for (int j = 0; j < envVars.length; ++j) {
                    printWriter.println(envVars[j][0] + "=" + envVars[j][1]);
                }
            }
            final String proPath = svcStartArgs.getProPath();
            if (proPath != null) {
                printWriter.println("PROPATH=" + proPath);
            }
            printWriter.flush();
            String line;
            do {
                line = bufferedReader.readLine();
                sb.append(line + SvcControlCmd.NEWLINE);
            } while (!line.endsWith("%completed"));
            filterJVMStartStatus = this.filterJVMStartStatus(sb.toString());
            pid = this.getPid(filterJVMStartStatus);
            b = false;
            final Environment environment = new Environment();
            int n = 0;
            do {
                try {
                    ++n;
                    Thread.sleep(1000L);
                    b = (environment.query_PID(new Integer(pid), false) > 0);
                }
                catch (Exception ex2) {
                    b = false;
                }
            } while (b && n <= 10);
        }
        catch (Exception ex) {
            ProLog.logdErr("OpenEdge", 3, "Exception executing jvmStart: \n" + ex.toString());
        }
        ProLog.logd("OpenEdge", 5, "jvmStart execution status: " + exec.exitValue());
        ProLog.logd("OpenEdge", 5, filterJVMStartStatus.trim());
        if (b) {
            ProLog.logd("OpenEdge", 4, "Started broker. Broker: " + this.m_argsPkt.m_svcName + ", PID: " + pid);
            this.m_state = 2L;
            this.postStartServiceEvent(this.m_argsPkt.m_svcName, this.m_argsPkt.m_svcfullGroupPath);
            this.startBrokerAbnormalShutdownWatcher(pid);
            return true;
        }
        this.postBrokerAbnormalShutdownEvent();
        return false;
    }
    
    private String getPid(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str);
        if (stringTokenizer.nextToken().equals("!+0099processid")) {
            return stringTokenizer.nextToken();
        }
        return "";
    }
    
    public boolean stopService(final String s) {
        return this.stopServiceDoRetries(s, SvcControlCmd.m_shutdown_retries);
    }
    
    public boolean stopService(final String s, final int n) {
        return this.stopServiceDoRetries(s, n);
    }
    
    private boolean stopServiceDoRetries(final String s, final int n) {
        Label_0296: {
            if (this.pingService() >= 0L) {
                int n2 = 0;
                try {
                    int n3 = 0;
                    this.stopBrokerAbnormalShutdownWatcher();
                    int i = 1;
                    while (i != 0) {
                        try {
                            if (this.m_svcAdminRemote instanceof IRemoteManager) {
                                i = ((IRemoteManager)this.m_svcAdminRemote).invokeCommand(2, null);
                            }
                            else {
                                i = this.m_svcAdminRemote.shutDown();
                            }
                            if (i == 1) {
                                ++n3;
                            }
                            if (i != 0) {
                                Thread.sleep(SvcControlCmd.m_toolShutdownSvcConfirmRetryInterval);
                            }
                        }
                        catch (InterruptedException ex3) {
                            if (n3 > 0) {
                                i = 0;
                            }
                            break;
                        }
                        catch (RemoteException ex4) {
                            if (n3 > 0) {
                                i = 0;
                            }
                            break;
                        }
                        catch (Exception ex) {
                            this.logException(UBToolsMsg.getMsg(7094295313015382007L, new Object[] { s, new Integer(n2) }), ex);
                            return false;
                        }
                        ++n2;
                    }
                    if (i != 0) {
                        this.m_state = 5L;
                        this.logMsg(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "SvcControlCmd", "stopService", s, " failed to stop service after " + n2 + " retries." }));
                        return false;
                    }
                    this.m_state = 4L;
                    this.postStopServiceEvent(this.m_argsPkt.m_svcName, this.m_argsPkt.m_svcfullGroupPath);
                    return true;
                }
                catch (Exception ex2) {
                    this.logException(UBToolsMsg.getMsg(7094295313015382006L, new Object[] { s }), ex2);
                    break Label_0296;
                }
            }
            this.logMsg(UBToolsMsg.getMsg(7094295313015381999L, new Object[] { s }));
        }
        this.resetState();
        return false;
    }
    
    private void startBrokerAbnormalShutdownWatcher(final String s) {
        final BrokerAbnormalShutdownWatcher watcher = this.m_argsPkt.getPmpObject().getWatcher(this.m_argsPkt.m_svcfullGroupPath, this.m_svcName, s, this.m_argsPkt.m_canonicalName);
        if (watcher == null) {
            return;
        }
        watcher.setKeepWatchingFlag(true);
        if (!watcher.isWatching()) {
            watcher.start();
        }
    }
    
    private void stopBrokerAbnormalShutdownWatcher() {
        final BrokerAbnormalShutdownWatcher watcher = this.m_argsPkt.getPmpObject().getWatcher(this.m_argsPkt.m_svcfullGroupPath, this.m_svcName, null, this.m_argsPkt.m_canonicalName);
        if (watcher != null) {
            watcher.setKeepWatchingFlag(false);
        }
    }
    
    private void startBrokerRunningStateWatcher() {
        (this.stateWatcher = new BrokerRunningStateWatcher()).start();
    }
    
    public long pingService() {
        return this.pingService(SvcControlCmd.m_ping_retries);
    }
    
    public long pingService(final long n) {
        try {
            if (this.m_state != 1L && this.m_state != 5L) {
                this.connectToService((int)n);
            }
            if (this.m_svcAdminRemote != null) {
                return this.m_svcAdminRemote.ping();
            }
            return -1L;
        }
        catch (Exception ex) {
            this.logException(UBToolsMsg.getMsg(7094295313015381996L, new Object[] { "SvcControlCmd", "pingService", this.m_svcName, ex.toString() }), ex);
            this.resetState();
            return -1L;
        }
    }
    
    public long getSvcCntlState() {
        return this.m_state;
    }
    
    public void connectToService(final int i) {
        int n = 0;
        if (this.m_rmiURL == null) {
            this.logMsg(UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "SvcControlCmd", "connectToService", "Cannot connect to null URL" }));
            this.m_state = 6L;
            return;
        }
        while (n++ < i) {
            try {
                this.m_svcAdminRemote = (IAdminRemote)lookupService(this.m_rmiURL);
                this.m_state = 1L;
            }
            catch (Exception ex) {
                this.m_state = 7L;
                if (n >= i) {
                    continue;
                }
                try {
                    Thread.sleep(SvcControlCmd.m_toolConnectSvcRetryInterval);
                }
                catch (Exception ex2) {}
                continue;
            }
            break;
        }
        if (this.m_state != 1L) {
            this.m_state = 8L;
            if (i > 1) {
                this.logMsg(5, UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "SvcControlCmd", "connectToService", "Unable to connect to the service process at " + this.m_rmiURL + "(after " + i + " retries)" }));
            }
        }
    }
    
    public long silentPingOnce() {
        if (this.m_rmiURL == null) {
            return this.m_state = 6L;
        }
        try {
            (this.m_svcAdminRemote = (IAdminRemote)lookupService(this.m_rmiURL)).getStatusFormatted(3);
            this.m_state = 1L;
        }
        catch (Exception ex) {
            this.m_state = 8L;
        }
        return this.m_state;
    }
    
    public long silentPingRetry(final int n) {
        if (this.m_rmiURL == null) {
            return this.m_state = 6L;
        }
        int i = 0;
        while (i < n) {
            try {
                (this.m_svcAdminRemote = (IAdminRemote)lookupService(this.m_rmiURL)).getStatusFormatted(3);
                return this.m_state = 1L;
            }
            catch (Exception ex) {
                try {
                    Thread.sleep(SvcControlCmd.m_toolConnectSvcRetryInterval);
                }
                catch (Exception ex2) {}
                ++i;
                continue;
            }
            break;
        }
        return this.m_state = 8L;
    }
    
    public void fetchSvcRMIConnection() {
        this.fetchSvcRMIConnection(SvcControlCmd.m_connect_retries);
    }
    
    public void fetchSvcRMIConnection(final int n) {
        boolean b = false;
        try {
            if (this.pingService(1L) == -1L) {
                b = true;
            }
            if (b || (this.m_state != 1L && this.m_state != 5L)) {
                this.connectToService(n);
            }
        }
        catch (Exception ex) {
            this.logException(UBToolsMsg.getMsg(7094295313015381995L, new Object[] { "SvcControlCmd", "fetchSvcRMIConnection", " Failed to connect to the service process(" + this.m_svcName + ") rmi layer: \n" + ex.toString() }), ex);
        }
    }
    
    public int StopServiceImmediately(final String s) {
        try {
            if (this.pingService(1L) > 0L && this.m_state != 1L) {
                this.connectToService(1);
            }
        }
        catch (Exception ex2) {}
        int n;
        if (this.m_state == 1L) {
            try {
                this.stopBrokerAbnormalShutdownWatcher();
                this.m_svcAdminRemote.emergencyShutdown();
            }
            catch (Exception ex) {
                this.logException("emergencyShutdown Failure in SvcControlCmd.StopServiceImmediately(): ", ex);
            }
            this.postStopServiceEvent(this.m_argsPkt.m_svcName, this.m_argsPkt.m_svcfullGroupPath);
            n = 0;
        }
        else {
            n = 1;
        }
        return n;
    }
    
    public void postStartServiceEvent(final String s, final String s2) {
        try {
            final EStartServiceEvent eStartServiceEvent = new EStartServiceEvent(AbstractUbrokerPlugin.m_iAdministrationServer, s, s2);
            eStartServiceEvent.setSource(this.m_argsPkt.m_canonicalName);
            AbstractGuiPlugin.getEventBroker().postEvent(eStartServiceEvent);
            UBToolsMsg.logMsg(5, this.toString() + " m_state = " + this.m_state);
            UBToolsMsg.logMsg(5, "StartServiceEvent for " + s + " is posted...");
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post startService event for " + s + "(" + ex.toString() + ")");
        }
    }
    
    public void postStopServiceEvent(final String s, final String s2) {
        try {
            final EStopServiceEvent eStopServiceEvent = new EStopServiceEvent(AbstractUbrokerPlugin.m_iAdministrationServer, s, s2);
            eStopServiceEvent.setSource(this.m_argsPkt.m_canonicalName);
            AbstractGuiPlugin.getEventBroker().postEvent(eStopServiceEvent);
            UBToolsMsg.logMsg(5, this.toString() + " m_state = " + this.m_state);
            UBToolsMsg.logMsg(5, "StopServiceEvent for " + s + " is posted...");
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post stopService event for " + s + "(" + ex.toString() + ")");
        }
    }
    
    private void postUbrokerProcessActiveEvent() {
        try {
            final EUbrokerProcessActiveEvent eUbrokerProcessActiveEvent = new EUbrokerProcessActiveEvent(AbstractUbrokerPlugin.m_iAdministrationServer, new COpenEdgeManagementContent(this.m_argsPkt.m_svcName, null));
            eUbrokerProcessActiveEvent.setSource(this.m_argsPkt.m_canonicalName);
            AbstractGuiPlugin.getEventBroker().postEvent(eUbrokerProcessActiveEvent);
            UBToolsMsg.logMsg(5, this.toString() + " m_state = " + this.m_state);
            UBToolsMsg.logMsg(5, "UbrokerProcessActiveEvent for " + this.m_argsPkt.m_svcName + " is posted...");
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post UbrokerProcessActiveEvent event for " + this.m_argsPkt.m_svcName + "(" + ex.toString() + ")");
        }
    }
    
    private void postBrokerAbnormalShutdownEvent() {
        try {
            final EAbnormalShutdownEvent eAbnormalShutdownEvent = new EAbnormalShutdownEvent(AbstractUbrokerPlugin.m_iAdministrationServer, new COpenEdgeManagementContent(this.m_argsPkt.m_svcName, new Object[] { this.m_argsPkt.m_svcfullGroupPath }));
            eAbnormalShutdownEvent.setSource(this.m_argsPkt.m_canonicalName);
            AbstractGuiPlugin.getEventBroker().postEvent(eAbnormalShutdownEvent);
            UBToolsMsg.logMsg(5, this.toString() + " m_state = " + this.m_state);
            UBToolsMsg.logMsg(5, "EAbnormalShutdownEvent for " + this.m_argsPkt.m_svcName + " is posted...");
        }
        catch (Exception ex) {
            UBToolsMsg.logException("Failed to post EAbnormalShutdownEvent event for " + this.m_argsPkt.m_svcName + "(" + ex.toString() + ")");
        }
    }
    
    public void updateConfig() {
        this.m_argsPkt.updateConfig();
        this.getPreferences();
    }
    
    private void getPreferences() {
        SvcControlCmd.m_connect_retries = this.m_argsPkt.m_preferences.m_toolConnectSvcRetry;
        SvcControlCmd.m_getStatus_retries = this.m_argsPkt.m_preferences.m_toolGetSvcStatusRetry;
        SvcControlCmd.m_ping_retries = this.m_argsPkt.m_preferences.m_toolPingSvcRetry;
        SvcControlCmd.m_shutdown_retries = this.m_argsPkt.m_preferences.m_toolShutdownSvcConfirmRetry;
        SvcControlCmd.m_toolConnectSvcRetryInterval = this.m_argsPkt.m_preferences.m_toolConnectSvcRetryInterval;
        SvcControlCmd.m_toolShutdownSvcConfirmRetryInterval = this.m_argsPkt.m_preferences.m_toolShutdownSvcConfirmRetryInterval;
        SvcControlCmd.m_prefSet = true;
    }
    
    private void logMsg(final String s) {
        UBToolsMsg.logMsg(s);
    }
    
    private void logMsg(final int n, final String s) {
        UBToolsMsg.logMsg(n, s);
    }
    
    private void logException(final String str, final Throwable t) {
        UBToolsMsg.logException(str + "(" + t.toString() + ")");
    }
    
    private String filterJVMStartStatus(String string) {
        int i = string.indexOf("%");
        if (i > 0) {
            while (i > 0) {
                final int index = string.indexOf("%");
                if (index > 0) {
                    string = string.substring(0, index) + string.substring(index + 1);
                }
                i = string.indexOf("%");
            }
        }
        return string;
    }
    
    private boolean ServiceStartOK(final String s) {
        return s.indexOf(SvcControlCmd.PROCESS_START_ERROR) <= 0;
    }
    
    private void logException(final long n, final String s, final Throwable t) {
        UBToolsMsg.logException(UBToolsMsg.getMsg(n, new Object[] { "SvcControlCmd", s, t.toString() }));
    }
    
    private static Remote lookupService(final String s) throws MalformedURLException, RemoteException, NotBoundException {
        final PscURLParser pscURLParser = new PscURLParser(s);
        pscURLParser.setScheme(null);
        final String host = pscURLParser.getHost();
        final int port = pscURLParser.getPort();
        final String service = pscURLParser.getService();
        final Registry registry = LocateRegistry.getRegistry(host, port);
        return (service == null) ? registry : registry.lookup(service);
    }
    
    static {
        SvcControlCmd.NEWLINE = System.getProperty("line.separator");
        SvcControlCmd.m_connect_retries = 20;
        SvcControlCmd.m_getStatus_retries = 12;
        SvcControlCmd.m_ping_retries = 4L;
        SvcControlCmd.m_shutdown_retries = 10;
        SvcControlCmd.m_toolConnectSvcRetryInterval = 3000;
        SvcControlCmd.m_toolShutdownSvcConfirmRetryInterval = 3000;
        SvcControlCmd.PROCESS_START_ERROR = "Error starting JVM";
        SvcControlCmd.m_prefSet = false;
    }
    
    class BrokerRunningStateWatcher extends Thread
    {
        private IAdminRemote broker;
        boolean running;
        
        public BrokerRunningStateWatcher() {
            this.running = false;
            this.setName("BrokerRunningStateWatcher");
        }
        
        public void run() {
            while (!this.running) {
                try {
                    if (SvcControlCmd.this.m_state == 1L) {
                        this.running = true;
                        break;
                    }
                    if (SvcControlCmd.this.m_state != 2L) {
                        break;
                    }
                    this.broker = (IAdminRemote)lookupService(SvcControlCmd.this.m_rmiURL);
                    if (this.broker.getStatusFormatted(3).trim().equals("ACTIVE")) {
                        SvcControlCmd.this.postUbrokerProcessActiveEvent();
                        SvcControlCmd.this.m_state = 1L;
                        this.running = true;
                        break;
                    }
                    Thread.sleep(2000L);
                }
                catch (RemoteException ex) {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (Exception ex2) {}
                }
                catch (Exception ex3) {}
            }
        }
    }
}

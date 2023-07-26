// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management;

import com.progress.ubroker.util.ubThread;
import com.progress.ubroker.broker.ubClientThread;
import com.progress.ubroker.broker.ubServerThread;
import java.util.Collection;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import com.progress.ubroker.util.ubProperties;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.broker.ubClientThreadPool;
import com.progress.ubroker.broker.ubServerThreadPool;
import java.util.Hashtable;

public class UBrokerVST implements IRemoteManagerConst
{
    private static final String[] ACT_PROC_AS_SCHEMA;
    private static final String[] ACT_PROC_WS_SCHEMA;
    private static final String[] ACT_REQ_SCHEMA;
    private static final String[] ACT_CLIENT_SCHEMA;
    private static final String[] ACT_AGENT_SCHEMA;
    private Hashtable VSTs;
    private static Hashtable m_asProcHash;
    private static Hashtable m_wsProcHash;
    private Integer[] actReq;
    private Integer[] actClient;
    private Hashtable actAgentTable;
    private Integer[] actAgent;
    private Hashtable actProcTable;
    private ubServerThreadPool m_serverThreadPool;
    private ubClientThreadPool m_clientThreadPool;
    private IAppLogger m_logger;
    private ubProperties m_properties;
    private static final int REQ_COMPLETED = 0;
    private static final int REQ_QUEUED = 1;
    private static final int REQ_REJECTED = 2;
    private static final int AGENT_PID = 0;
    private static final int AGENT_STATUS = 1;
    private static final int AGENT_STATUS_TIME = 2;
    private static final int AGENT_IDLE = 3;
    private static final int AGENT_IDLE_TIME = 4;
    private static final int AGENT_BUSY = 5;
    private static final int AGENT_BUSY_TIME = 6;
    private static final int AGENT_LOCKED = 7;
    private static final int AGENT_LOCKED_TIME = 8;
    private static final int AGENT_CURR_PROC = 9;
    private static final int CLIENT_CURRENT = 0;
    private static final int CLIENT_TOTAL = 1;
    private static final int AS_PROC_NAME = 0;
    private static final int AS_PROC_PARENT = 1;
    private static final int AS_PROC_TYPE = 2;
    private static final int AS_PROC_SUCCESS = 3;
    private static final int AS_PROC_ERROR = 4;
    private static final int AS_PROC_QUIT = 5;
    private static final int AS_PROC_STOP = 6;
    private static final int AS_PROC_DEBUG = 7;
    private static final int AS_PROC_TIME = 8;
    private static final int AS_PROC_CALLS = 9;
    private static final int WS_PROC_NAME = 0;
    private static final int WS_PROC_TIME = 1;
    private static final int WS_PROC_CALLS = 2;
    
    private UBrokerVST() {
        this.VSTs = new Hashtable();
        this.actReq = new Integer[3];
        this.actClient = new Integer[3];
        this.actAgentTable = new Hashtable();
        this.actAgent = new Integer[3];
        this.actProcTable = new Hashtable();
        this.m_serverThreadPool = null;
        this.m_clientThreadPool = null;
        this.m_logger = null;
        this.m_properties = null;
    }
    
    public UBrokerVST(final ubServerThreadPool serverThreadPool, final ubClientThreadPool clientThreadPool, final IAppLogger logger, final ubProperties properties) {
        this.VSTs = new Hashtable();
        this.actReq = new Integer[3];
        this.actClient = new Integer[3];
        this.actAgentTable = new Hashtable();
        this.actAgent = new Integer[3];
        this.actProcTable = new Hashtable();
        this.m_serverThreadPool = null;
        this.m_clientThreadPool = null;
        this.m_logger = null;
        this.m_properties = null;
        this.m_serverThreadPool = serverThreadPool;
        this.m_clientThreadPool = clientThreadPool;
        this.m_logger = logger;
        this.m_properties = properties;
    }
    
    public static Hashtable getSchema(final String[] array) {
        final Hashtable<String, String[]> hashtable = new Hashtable<String, String[]>();
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equalsIgnoreCase("actProcAS")) {
                hashtable.put("actProcAS", UBrokerVST.ACT_PROC_AS_SCHEMA);
            }
            if (array[i].equalsIgnoreCase("actProcWS")) {
                hashtable.put("actProcWS", UBrokerVST.ACT_PROC_WS_SCHEMA);
            }
            if (array[i].equalsIgnoreCase("actClient")) {
                hashtable.put("actClient", UBrokerVST.ACT_CLIENT_SCHEMA);
            }
            if (array[i].equalsIgnoreCase("actReq")) {
                hashtable.put("actReq", UBrokerVST.ACT_REQ_SCHEMA);
            }
            if (array[i].equalsIgnoreCase("actAgent")) {
                hashtable.put("actAgent", UBrokerVST.ACT_AGENT_SCHEMA);
            }
        }
        return hashtable;
    }
    
    public static void main(final String[] array) {
        final UBrokerVST uBrokerVST = new UBrokerVST();
        final String[] array2 = { "actProc" };
        getSchema(array2);
        final Object[][] array3 = uBrokerVST.getData(array2).get("actProc");
        for (int i = 0; i < array3[0].length; ++i) {
            System.out.println("PROC_NAME: " + array3[0][i]);
            System.out.println("PROC_PARENT: " + array3[1][i]);
            System.out.println("PROC_TYPE: " + array3[2][i]);
            System.out.println("PROC_SUCCESS: " + array3[3][i]);
            System.out.println("PROC_ERROR: " + array3[4][i]);
            System.out.println("PROC_QUIT: " + array3[5][i]);
            System.out.println("PROC_STOP: " + array3[6][i]);
            System.out.println("PROC_DEBUG: " + array3[7][i]);
            System.out.println("PROC_TIME: " + array3[8][i]);
            System.out.println("PROC_CALLS: " + array3[9][i]);
            System.out.println();
        }
    }
    
    public static String[] getUBSummaryStatusRawStringSchema(final int n) {
        final String[] array = new String[14];
        array[0] = "Broker Name";
        array[1] = "Operating Mode";
        array[2] = "Broker Status";
        array[3] = "Broker Port";
        array[4] = "Broker PID";
        if (n == 1) {
            array[5] = "Active Agents";
            array[6] = "Busy Agents";
            array[7] = "Locked Agents";
            array[8] = "Available Agents";
        }
        else {
            array[5] = "Active Servers";
            array[6] = "Busy Servers";
            array[7] = "Locked Servers";
            array[8] = "Available Servers";
        }
        array[9] = "Active Clients (now, peak)";
        array[10] = "Client Queue Depth (cur, max)";
        array[11] = "Total Requests";
        array[12] = "Rq Wait (max, avg)";
        array[13] = "Rq Duration (max, avg)";
        return array;
    }
    
    public static String[] getUBSummaryStatusSchema(final int n) {
        final String[] array = new String[14];
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.YodaToolBundle");
        array[0] = progressResources.getTranString("SummaryStatus: BrokerName");
        array[1] = progressResources.getTranString("SummaryStatus: OperatingMode");
        array[2] = progressResources.getTranString("SummaryStatus: BrokerStatus");
        array[3] = progressResources.getTranString("SummaryStatus: BrokerPort");
        array[4] = progressResources.getTranString("SummaryStatus: BrokerPID");
        if (n == 1) {
            array[5] = progressResources.getTranString("SummaryStatus: ActiveAgents");
            array[6] = progressResources.getTranString("SummaryStatus: BusyAgents");
            array[7] = progressResources.getTranString("SummaryStatus: LockedAgents");
            array[8] = progressResources.getTranString("SummaryStatus: AvailableAgents");
        }
        else {
            array[5] = progressResources.getTranString("SummaryStatus: ActiveServers");
            array[6] = progressResources.getTranString("SummaryStatus: BusyServers");
            array[7] = progressResources.getTranString("SummaryStatus: LockedServers");
            array[8] = progressResources.getTranString("SummaryStatus: AvailableServers");
        }
        array[9] = progressResources.getTranString("SummaryStatus: ActiveClients");
        array[10] = progressResources.getTranString("SummaryStatus: ClientQueueDepth");
        array[11] = progressResources.getTranString("SummaryStatus: TotalRequests");
        array[12] = progressResources.getTranString("SummaryStatus: RequestWait");
        array[13] = progressResources.getTranString("SummaryStatus: RequestDuration");
        return array;
    }
    
    public static String[] getUBDetailStatusRawStringSchema(final int n) {
        return new String[] { (n == 4) ? "Srvr#" : "PID  ", "State", "Port", "nRq", "nRcvd", "nSent", "Started", "Last Change" };
    }
    
    public static String[] getUBDetailStatusSchema(final int n) {
        final String[] array = new String[8];
        final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.YodaToolBundle");
        array[0] = ((n == 4) ? "Srvr#" : "PID  ");
        array[1] = progressResources.getTranString("DetailsStatus: State");
        array[2] = progressResources.getTranString("DetailsStatus: Port");
        array[3] = progressResources.getTranString("DetailsStatus: NumberRequests");
        array[4] = progressResources.getTranString("DetailsStatus: NumberRequestsReceived");
        array[5] = progressResources.getTranString("DetailsStatus: NumberRequestsSent");
        array[6] = progressResources.getTranString("DetailsStatus: Started");
        array[7] = progressResources.getTranString("DetailsStatus: Last Change");
        return array;
    }
    
    public Hashtable getData(final String[] array) {
        final Hashtable<String, Object[][]> hashtable = new Hashtable<String, Object[][]>();
        for (int i = 0; i < array.length; ++i) {
            if (array[i].equalsIgnoreCase("actProcAS")) {
                hashtable.put("actProcAS", this.createActProcASTable());
            }
            if (array[i].equalsIgnoreCase("actProcWS")) {
                hashtable.put("actProcWS", this.createActProcWSTable());
            }
            if (array[i].equalsIgnoreCase("actClient")) {
                hashtable.put("actClient", (Object[][])(Object)this.createActClientTable());
            }
            if (array[i].equalsIgnoreCase("actReq")) {
                hashtable.put("actReq", (Object[][])(Object)this.createActReqTable());
            }
            if (array[i].equalsIgnoreCase("actAgent")) {
                hashtable.put("actAgent", this.createActAgentTable());
            }
        }
        return hashtable;
    }
    
    private Object[][] createActProcASTable() {
        final Collection values = UBrokerVST.m_asProcHash.values();
        values.iterator();
        final Object[][] array = new Object[10][UBrokerVST.m_asProcHash.size()];
        final Object[] array2 = values.toArray();
        for (int i = 0; i < UBrokerVST.m_asProcHash.size(); ++i) {
            array[0][i] = ((ubASProcStatsObj)array2[i]).getProcName();
            array[1][i] = ((ubASProcStatsObj)array2[i]).getProcParent();
            array[2][i] = ((ubASProcStatsObj)array2[i]).getProcType();
            array[3][i] = new Integer(((ubASProcStatsObj)array2[i]).getNumSuccess());
            array[4][i] = new Integer(((ubASProcStatsObj)array2[i]).getNumError());
            array[5][i] = new Integer(((ubASProcStatsObj)array2[i]).getNumQuit());
            array[6][i] = new Integer(((ubASProcStatsObj)array2[i]).getNumStop());
            array[7][i] = new Integer(((ubASProcStatsObj)array2[i]).getNumDebug());
            array[8][i] = new Long(((ubASProcStatsObj)array2[i]).getTime());
            array[9][i] = new Integer(((ubASProcStatsObj)array2[i]).getNumCalls());
        }
        return array;
    }
    
    private Object[][] createActProcWSTable() {
        final Collection values = UBrokerVST.m_wsProcHash.values();
        values.iterator();
        final Object[][] array = new Object[3][UBrokerVST.m_wsProcHash.size()];
        final Object[] array2 = values.toArray();
        for (int i = 0; i < UBrokerVST.m_wsProcHash.size(); ++i) {
            array[0][i] = ((ubWSProcStatsObj)array2[i]).getProcName();
            array[1][i] = new Long(((ubWSProcStatsObj)array2[i]).getTime());
            array[2][i] = new Integer(((ubWSProcStatsObj)array2[i]).getNumCalls());
        }
        return array;
    }
    
    private Integer[] createActClientTable() {
        final Integer[] array = new Integer[2];
        final ubThread[] enumerateThreads = this.m_clientThreadPool.enumerateThreads();
        final ubThread[] enumerateThreads2 = this.m_serverThreadPool.enumerateThreads();
        int value = 0;
        int getnumRqs = 0;
        if (this.m_properties.serverMode == 1 || this.m_properties.serverMode == 2) {
            for (int i = 0; i < enumerateThreads2.length; ++i) {
                if (((ubServerThread)enumerateThreads2[i]).isConnected()) {
                    ++value;
                }
            }
            getnumRqs = this.m_clientThreadPool.getSummaryStats().getnumRqs();
        }
        else {
            for (int j = 0; j < enumerateThreads.length; ++j) {
                if (((ubClientThread)enumerateThreads[j]).isConnected()) {
                    ++value;
                }
                getnumRqs += ((ubClientThread)enumerateThreads[j]).getTotalClients();
            }
        }
        array[0] = new Integer(value);
        array[1] = new Integer(getnumRqs);
        return array;
    }
    
    private Integer[] createActReqTable() {
        return new Integer[] { new Integer(this.m_serverThreadPool.getRequestsCompleted()), new Integer(this.m_clientThreadPool.getRequestsQueued()), new Integer(this.m_clientThreadPool.getRequestsRejected()) };
    }
    
    private Object[][] createActAgentTable() {
        final ubThread[] array = this.m_serverThreadPool.enumerateThreads();
        final Object[][] array2 = new Object[9][array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[0][i] = new Long(((ubServerThread)array[i]).getServerPid());
            array2[1][i] = new Integer(((ubServerThread)array[i]).getServerState(0));
            array2[2][i] = new Long(((ubServerThread)array[i]).getTimeEnteredCurrStatus());
            array2[3][i] = new Long(((ubServerThread)array[i]).getIdleCount());
            array2[4][i] = new Long(((ubServerThread)array[i]).getIdleTime());
            array2[5][i] = new Long(((ubServerThread)array[i]).getBusyCount());
            array2[6][i] = new Long(((ubServerThread)array[i]).getBusyTime());
            array2[7][i] = new Long(((ubServerThread)array[i]).getLockedCount());
            array2[8][i] = new Long(((ubServerThread)array[i]).getLockedTime());
        }
        return array2;
    }
    
    public static synchronized Hashtable getASProcHash() {
        return UBrokerVST.m_asProcHash;
    }
    
    public static synchronized Hashtable getWSProcHash() {
        return UBrokerVST.m_wsProcHash;
    }
    
    static {
        ACT_PROC_AS_SCHEMA = new String[] { "ActProcAS-Name", "ActProcAS-Parent", "ActProcAS-Type", "ActProcAS-Success", "ActProcAS-Error", "ActProcAS-Stop", "ActProcAS-Quit", "ActProcAS-Debug", "ActProcAS-Time", "ActProcAS-Calls" };
        ACT_PROC_WS_SCHEMA = new String[] { "ActProcWS-Name", "ActProcWS-Time", "ActProcWS-Calls" };
        ACT_REQ_SCHEMA = new String[] { "ActReq-Completed", "ActReq-Queued", "ActReq-Rejected" };
        ACT_CLIENT_SCHEMA = new String[] { "ActClient-Current", "ActClient-Total" };
        ACT_AGENT_SCHEMA = new String[] { "ActAgent-PID", "ActAgent-Status", "ActAgent-StatusTime", "ActAgent-Idle", "ActAgent-IdleTime", "ActAgent-Busy", "ActAgent-BusyTime", "ActAgent-Locked", "ActAgent-LockedTime" };
        UBrokerVST.m_asProcHash = new Hashtable();
        UBrokerVST.m_wsProcHash = new Hashtable();
    }
}

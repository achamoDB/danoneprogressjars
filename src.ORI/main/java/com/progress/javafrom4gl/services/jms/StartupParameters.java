// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.services.jms;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import com.progress.open4gl.dynamicapi.Util;
import java.util.StringTokenizer;
import com.progress.javafrom4gl.ServiceRuntime;
import progress.message.jclient.ConnectionFactory;
import com.progress.javafrom4gl.Log;
import java.util.Properties;

class StartupParameters
{
    static final String NEW_LINE;
    String jmsServerName;
    boolean pubSub;
    String brokerURL;
    String user;
    String password;
    String clientID;
    String connectID;
    String pingInterval;
    boolean transactedPublish;
    boolean transactedReceive;
    boolean singleMessageAck;
    boolean symbiontAdapt;
    boolean jmsDomain;
    String connectionURLs;
    boolean foSequential;
    boolean loadBalancing;
    int prefetchCount;
    int prefetchThreshold;
    boolean durableMessageOrder;
    int flowToDisk;
    boolean serverMessageSelector;
    String scConnectionFile;
    boolean faultTolerant;
    int ftReconnTimeout;
    int ftInitialTimeout;
    int ftBufferSize;
    boolean clientPersistence;
    String lsDirectory;
    long lsStoreSize;
    int lsReconnTimeout;
    int lsReconnInterval;
    int lsWaitTime;
    Integer priority;
    Long timeToLive;
    boolean persistency;
    boolean syncPublish;
    private String jmsServerNameDflt;
    private String brokerURLDflt;
    private String userDflt;
    private String passwordDflt;
    private String clientIDDflt;
    private String pingIntervalDflt;
    boolean clientFactory;
    boolean clientSpecifiedJms;
    String clientHostName;
    
    StartupParameters(final Properties properties) {
        this.jmsServerName = null;
        this.pubSub = true;
        this.brokerURL = null;
        this.user = null;
        this.password = null;
        this.clientID = null;
        this.connectID = null;
        this.pingInterval = null;
        this.transactedPublish = false;
        this.transactedReceive = false;
        this.singleMessageAck = false;
        this.symbiontAdapt = false;
        this.jmsDomain = false;
        this.connectionURLs = null;
        this.foSequential = true;
        this.loadBalancing = false;
        this.prefetchCount = -1;
        this.prefetchThreshold = -1;
        this.durableMessageOrder = false;
        this.flowToDisk = 0;
        this.serverMessageSelector = false;
        this.scConnectionFile = null;
        this.faultTolerant = false;
        this.ftReconnTimeout = 60;
        this.ftInitialTimeout = 20;
        this.ftBufferSize = 0;
        this.clientPersistence = false;
        this.lsDirectory = null;
        this.lsStoreSize = 10000L;
        this.lsReconnTimeout = 0;
        this.lsReconnInterval = 30;
        this.lsWaitTime = 0;
        this.priority = null;
        this.timeToLive = null;
        this.persistency = true;
        this.syncPublish = true;
        this.clientFactory = false;
        this.clientSpecifiedJms = false;
        this.clientHostName = null;
        this.jmsServerNameDflt = null;
        this.pubSub = true;
        this.brokerURLDflt = null;
        this.userDflt = null;
        this.passwordDflt = null;
        this.clientIDDflt = null;
        this.pingIntervalDflt = null;
        if (properties != null) {
            final String property = properties.getProperty("jmsServerName");
            this.jmsServerNameDflt = property;
            this.jmsServerName = property;
            final String property2 = properties.getProperty("brokerURL");
            this.brokerURLDflt = property2;
            this.brokerURL = property2;
            final String property3 = properties.getProperty("user");
            this.userDflt = property3;
            this.user = property3;
            final String property4 = properties.getProperty("password");
            this.passwordDflt = property4;
            this.password = property4;
            final String property5 = properties.getProperty("clientID");
            this.clientIDDflt = property5;
            this.clientID = property5;
            final String property6 = properties.getProperty("pingInterval");
            this.pingIntervalDflt = property6;
            this.pingInterval = property6;
        }
    }
    
    public String toString() {
        return "    Startup Parameters:" + StartupParameters.NEW_LINE + "    jmsServerName: " + this.jmsServerName + StartupParameters.NEW_LINE + (this.pubSub ? "    Publish/Subscribe  " : "    Point-To-Point     ") + StartupParameters.NEW_LINE + "    brokerURL: " + this.brokerURL + StartupParameters.NEW_LINE + "    user: " + this.user + StartupParameters.NEW_LINE + "    password: " + this.password + StartupParameters.NEW_LINE + "    clientID: " + this.clientID + StartupParameters.NEW_LINE + "    pingInterval: " + this.pingInterval + StartupParameters.NEW_LINE + "    transactedPublish: " + this.transactedPublish + StartupParameters.NEW_LINE + "    transactedReceive: " + this.transactedReceive + StartupParameters.NEW_LINE + "    singleMessageAck: " + this.singleMessageAck + StartupParameters.NEW_LINE + "    symbiontAdapter: " + this.symbiontAdapt + StartupParameters.NEW_LINE + "    jmsDomain: " + this.jmsDomain + StartupParameters.NEW_LINE;
    }
    
    void setConnectionParameters(final String user, final String password, final String s, final String clientHostName) throws Exception {
        final GetClntStartupInfo getClntStartupInfo = new GetClntStartupInfo(s);
        if (user != null) {
            this.user = user;
        }
        else {
            this.user = this.userDflt;
        }
        if (password != null) {
            this.password = password;
        }
        else {
            this.password = this.passwordDflt;
        }
        if (getClntStartupInfo.jmsServerName != null) {
            this.clientSpecifiedJms = true;
            this.jmsServerName = getClntStartupInfo.jmsServerName;
        }
        else {
            this.jmsServerName = this.jmsServerNameDflt;
        }
        this.pubSub = getClntStartupInfo.pubSub;
        if (getClntStartupInfo.brokerURL != null) {
            this.clientFactory = true;
            this.brokerURL = getClntStartupInfo.brokerURL;
        }
        else {
            this.brokerURL = this.brokerURLDflt;
        }
        if (getClntStartupInfo.clientID != null) {
            this.clientID = getClntStartupInfo.clientID;
        }
        else {
            this.clientID = this.clientIDDflt;
        }
        if (getClntStartupInfo.pingInterval != null) {
            this.pingInterval = getClntStartupInfo.pingInterval;
        }
        else {
            this.pingInterval = this.pingIntervalDflt;
        }
        if (getClntStartupInfo.transactedPublish) {
            this.transactedPublish = true;
        }
        if (getClntStartupInfo.transactedReceive) {
            this.transactedReceive = true;
        }
        this.priority = getClntStartupInfo.priority;
        this.timeToLive = getClntStartupInfo.timeToLive;
        this.persistency = getClntStartupInfo.persistency;
        this.syncPublish = getClntStartupInfo.syncPublish;
        if (getClntStartupInfo.connectionURLs != null) {
            this.clientFactory = true;
            this.connectionURLs = getClntStartupInfo.connectionURLs;
        }
        if (!getClntStartupInfo.foSequential) {
            this.foSequential = false;
        }
        if (getClntStartupInfo.loadBalancing) {
            this.loadBalancing = true;
        }
        if (getClntStartupInfo.singleMessageAck) {
            this.singleMessageAck = true;
        }
        if (getClntStartupInfo.prefetchCount >= 0) {
            this.prefetchCount = getClntStartupInfo.prefetchCount;
        }
        if (getClntStartupInfo.prefetchThreshold >= 0) {
            this.prefetchThreshold = getClntStartupInfo.prefetchThreshold;
        }
        if (this.user == null) {
            this.user = "";
        }
        if (this.password == null) {
            this.password = "";
        }
        this.durableMessageOrder = getClntStartupInfo.durableMessageOrder;
        this.flowToDisk = getClntStartupInfo.flowToDisk;
        this.serverMessageSelector = getClntStartupInfo.serverMessageSelector;
        this.scConnectionFile = getClntStartupInfo.scConnectionFile;
        this.faultTolerant = getClntStartupInfo.faultTolerant;
        this.ftReconnTimeout = getClntStartupInfo.ftReconnTimeout;
        this.ftInitialTimeout = getClntStartupInfo.ftInitialTimeout;
        this.ftBufferSize = getClntStartupInfo.ftBufferSize;
        this.clientPersistence = getClntStartupInfo.clientPersistence;
        this.lsDirectory = getClntStartupInfo.lsDirectory;
        this.lsStoreSize = getClntStartupInfo.lsStoreSize;
        this.lsReconnTimeout = getClntStartupInfo.lsReconnTimeout;
        this.lsReconnInterval = getClntStartupInfo.lsReconnInterval;
        this.lsWaitTime = getClntStartupInfo.lsWaitTime;
        this.symbiontAdapt = getClntStartupInfo.symbiontAdapt;
        this.jmsDomain = getClntStartupInfo.jmsDomain;
        this.connectID = getClntStartupInfo.connectID;
        this.clientHostName = clientHostName;
    }
    
    static {
        NEW_LINE = new String(System.getProperty("line.separator"));
    }
    
    static class GetClntStartupInfo
    {
        private static final int SERVER_PROTOCOL_VERSION = 118;
        private static final String FIRST_CONN = "__firstconn:";
        private static final char NULL_MARKER = '\u0001';
        private static final char PARAM_DELIMITER = '\u0002';
        private static final char[] PARAM_DELIMITER_ARRAY;
        private static final String PARAM_DELIMITER_STR;
        private Log log;
        private ConnectionFactory factory;
        String jmsServerName;
        boolean pubSub;
        String brokerURL;
        String clientID;
        String pingInterval;
        boolean transactedPublish;
        boolean transactedReceive;
        boolean singleMessageAck;
        Integer priority;
        Long timeToLive;
        boolean persistency;
        boolean syncPublish;
        String connectionURLs;
        boolean foSequential;
        boolean loadBalancing;
        int prefetchCount;
        int prefetchThreshold;
        boolean durableMessageOrder;
        int flowToDisk;
        boolean serverMessageSelector;
        String scConnectionFile;
        boolean faultTolerant;
        int ftReconnTimeout;
        int ftInitialTimeout;
        int ftBufferSize;
        boolean clientPersistence;
        String lsDirectory;
        long lsStoreSize;
        int lsReconnTimeout;
        int lsReconnInterval;
        int lsWaitTime;
        boolean symbiontAdapt;
        boolean jmsDomain;
        String connectID;
        
        GetClntStartupInfo(final String s) throws Exception {
            this.log = ServiceRuntime.getLog();
            this.factory = null;
            this.jmsServerName = null;
            this.pubSub = true;
            this.brokerURL = null;
            this.clientID = null;
            this.pingInterval = null;
            this.transactedPublish = false;
            this.transactedReceive = false;
            this.singleMessageAck = false;
            this.priority = null;
            this.timeToLive = null;
            this.persistency = true;
            this.syncPublish = true;
            this.connectionURLs = null;
            this.foSequential = true;
            this.loadBalancing = false;
            this.prefetchCount = -1;
            this.prefetchThreshold = -1;
            this.durableMessageOrder = false;
            this.flowToDisk = 0;
            this.serverMessageSelector = false;
            this.scConnectionFile = null;
            this.faultTolerant = false;
            this.ftReconnTimeout = 60;
            this.ftInitialTimeout = 20;
            this.ftBufferSize = 0;
            this.clientPersistence = false;
            this.lsDirectory = null;
            this.lsStoreSize = 10000L;
            this.lsReconnTimeout = 0;
            this.lsReconnInterval = 30;
            this.lsWaitTime = 0;
            this.symbiontAdapt = false;
            this.jmsDomain = false;
            this.connectID = null;
            final StringTokenizer stringTokenizer = new StringTokenizer(s.substring("__firstconn:".length()), GetClntStartupInfo.PARAM_DELIMITER_STR, false);
            final int intValue = new Integer(stringTokenizer.nextToken());
            if (118 != intValue) {
                throw new Exception(Util.getMessageText(7017734119350084688L, String.valueOf(intValue), String.valueOf(118)));
            }
            final String nextToken = stringTokenizer.nextToken();
            if (!nextToken.equals("ps")) {
                this.pubSub = false;
                if (nextToken.equals("jms")) {
                    this.jmsDomain = true;
                }
            }
            this.brokerURL = stringTokenizer.nextToken();
            if (this.brokerURL.charAt(0) == '\u0001') {
                this.brokerURL = null;
            }
            this.clientID = stringTokenizer.nextToken();
            if (this.clientID.charAt(0) == '\u0001') {
                this.clientID = null;
            }
            final String nextToken2 = stringTokenizer.nextToken();
            if (nextToken2.charAt(0) == '\u0001') {
                this.transactedPublish = false;
            }
            else if (nextToken2.toUpperCase().equals("YES")) {
                this.transactedPublish = true;
            }
            final String nextToken3 = stringTokenizer.nextToken();
            if (nextToken3.charAt(0) == '\u0001') {
                this.transactedReceive = false;
            }
            else if (nextToken3.toUpperCase().equals("YES")) {
                this.transactedReceive = true;
            }
            final String nextToken4 = stringTokenizer.nextToken();
            if (nextToken4.charAt(0) == '\u0001') {
                this.priority = null;
            }
            else {
                this.priority = new Integer(nextToken4);
            }
            final String nextToken5 = stringTokenizer.nextToken();
            if (nextToken5.charAt(0) == '\u0001') {
                this.timeToLive = null;
            }
            else {
                this.timeToLive = new Long(new BigDecimal(nextToken5).longValue());
            }
            final String nextToken6 = stringTokenizer.nextToken();
            if (nextToken6.charAt(0) == '\u0001') {
                this.persistency = true;
            }
            else if (nextToken6.toUpperCase().equals("NO")) {
                this.persistency = false;
            }
            final String nextToken7 = stringTokenizer.nextToken();
            if (nextToken7.charAt(0) == '\u0001') {
                this.syncPublish = true;
            }
            else if (nextToken7.toUpperCase().equals("NO")) {
                this.syncPublish = false;
            }
            this.jmsServerName = stringTokenizer.nextToken();
            if (this.jmsServerName.charAt(0) == '\u0001') {
                this.jmsServerName = null;
            }
            if (!stringTokenizer.hasMoreTokens()) {
                return;
            }
            this.pingInterval = stringTokenizer.nextToken();
            if (this.pingInterval.charAt(0) == '\u0001') {
                this.pingInterval = null;
            }
            if (!stringTokenizer.hasMoreTokens()) {
                return;
            }
            this.connectionURLs = stringTokenizer.nextToken();
            if (this.connectionURLs.charAt(0) == '\u0001') {
                this.connectionURLs = null;
            }
            final String nextToken8 = stringTokenizer.nextToken();
            if (nextToken8.charAt(0) == '\u0001') {
                this.foSequential = true;
            }
            else if (nextToken8.toUpperCase().equals("NO")) {
                this.foSequential = false;
            }
            final String nextToken9 = stringTokenizer.nextToken();
            if (nextToken9.charAt(0) == '\u0001') {
                this.loadBalancing = false;
            }
            else if (nextToken9.toUpperCase().equals("YES")) {
                this.loadBalancing = true;
            }
            final String nextToken10 = stringTokenizer.nextToken();
            if (nextToken10.charAt(0) == '\u0001') {
                this.singleMessageAck = false;
            }
            else if (nextToken10.toUpperCase().equals("YES")) {
                this.singleMessageAck = true;
            }
            final String nextToken11 = stringTokenizer.nextToken();
            if (nextToken11.charAt(0) != '\u0001') {
                this.prefetchCount = Integer.parseInt(nextToken11);
            }
            final String nextToken12 = stringTokenizer.nextToken();
            if (nextToken12.charAt(0) != '\u0001') {
                this.prefetchThreshold = Integer.parseInt(nextToken12);
            }
            if (!stringTokenizer.hasMoreTokens()) {
                return;
            }
            final String nextToken13 = stringTokenizer.nextToken();
            if (nextToken13.toUpperCase().equals("YES")) {
                this.durableMessageOrder = true;
            }
            final String nextToken14 = stringTokenizer.nextToken();
            if (nextToken14.charAt(0) != '\u0001') {
                this.flowToDisk = Integer.parseInt(nextToken14);
            }
            final String nextToken15 = stringTokenizer.nextToken();
            if (nextToken15.toUpperCase().equals("YES")) {
                this.serverMessageSelector = true;
            }
            this.scConnectionFile = stringTokenizer.nextToken();
            if (this.scConnectionFile.charAt(0) == '\u0001') {
                this.scConnectionFile = null;
            }
            else {
                this.factory = (ConnectionFactory)this.readFile(this.scConnectionFile);
            }
            final String nextToken16 = stringTokenizer.nextToken();
            if (nextToken16.toUpperCase().equals("YES")) {
                this.clientPersistence = true;
            }
            this.lsDirectory = stringTokenizer.nextToken();
            if (this.lsDirectory.charAt(0) == '\u0001') {
                this.lsDirectory = null;
            }
            final String nextToken17 = stringTokenizer.nextToken();
            if (nextToken17.charAt(0) != '\u0001') {
                this.lsStoreSize = Long.parseLong(nextToken17);
            }
            final String nextToken18 = stringTokenizer.nextToken();
            if (nextToken18.charAt(0) != '\u0001') {
                this.lsReconnTimeout = Integer.parseInt(nextToken18);
            }
            final String nextToken19 = stringTokenizer.nextToken();
            if (nextToken19.charAt(0) != '\u0001') {
                this.lsReconnInterval = Integer.parseInt(nextToken19);
            }
            final String nextToken20 = stringTokenizer.nextToken();
            if (nextToken20.toUpperCase().equals("YES")) {
                this.faultTolerant = true;
            }
            final String nextToken21 = stringTokenizer.nextToken();
            if (nextToken21.charAt(0) != '\u0001') {
                this.ftReconnTimeout = Integer.parseInt(nextToken21);
            }
            final String nextToken22 = stringTokenizer.nextToken();
            if (nextToken22.charAt(0) != '\u0001') {
                this.ftInitialTimeout = Integer.parseInt(nextToken22);
            }
            final String nextToken23 = stringTokenizer.nextToken();
            if (nextToken23.charAt(0) != '\u0001') {
                this.ftBufferSize = Integer.parseInt(nextToken23);
            }
            if (stringTokenizer.nextToken().toUpperCase().equals("YES")) {
                this.symbiontAdapt = true;
            }
            final String nextToken24 = stringTokenizer.nextToken();
            if (nextToken24.charAt(0) != '\u0001') {
                this.lsWaitTime = Integer.parseInt(nextToken24);
            }
            if (this.factory != null) {
                try {
                    if (this.clientID == null) {
                        this.clientID = this.factory.getClientID();
                    }
                    if (nextToken6.charAt(0) == '\u0001') {
                        this.persistency = this.factory.getPersistentDelivery();
                    }
                    if (this.pingInterval == null) {
                        final long pingInterval = this.factory.getPingInterval();
                        if (pingInterval > 0L) {
                            this.pingInterval = Long.toString(pingInterval);
                        }
                    }
                    if (nextToken8.charAt(0) == '\u0001') {
                        this.foSequential = this.factory.getSequential();
                    }
                    if (nextToken9.charAt(0) == '\u0001') {
                        this.loadBalancing = this.factory.getLoadBalancing();
                    }
                    if (nextToken11.charAt(0) == '\u0001') {
                        this.prefetchCount = this.factory.getPrefetchCount();
                    }
                    if (nextToken12.charAt(0) == '\u0001') {
                        this.prefetchThreshold = this.factory.getPrefetchThreshold();
                    }
                    if (nextToken13.charAt(0) == '\u0001') {
                        this.durableMessageOrder = this.factory.getDurableMessageOrder();
                    }
                    if (nextToken14.charAt(0) == '\u0001') {
                        this.flowToDisk = this.factory.getFlowToDisk();
                    }
                    if (nextToken15.charAt(0) == '\u0001') {
                        this.serverMessageSelector = this.factory.getSelectorAtBroker();
                    }
                    if (nextToken16.charAt(0) == '\u0001') {
                        this.clientPersistence = this.factory.isEnableLocalStore();
                    }
                    if (this.lsDirectory == null) {
                        this.lsDirectory = this.factory.getLocalStoreDirectory();
                    }
                    if (nextToken17.charAt(0) == '\u0001') {
                        this.lsStoreSize = this.factory.getLocalStoreSize();
                    }
                    if (nextToken18.charAt(0) == '\u0001') {
                        this.lsReconnTimeout = this.factory.getReconnectTimeout();
                    }
                    if (nextToken19.charAt(0) == '\u0001') {
                        this.lsReconnInterval = this.factory.getReconnectInterval();
                    }
                    if (nextToken20.charAt(0) == '\u0001') {
                        this.faultTolerant = this.factory.getFaultTolerant();
                    }
                    if (nextToken21.charAt(0) == '\u0001') {
                        this.ftReconnTimeout = this.factory.getFaultTolerantReconnectTimeout();
                    }
                    if (nextToken22.charAt(0) == '\u0001') {
                        this.ftInitialTimeout = this.factory.getInitialConnectTimeout();
                    }
                    if (nextToken23.charAt(0) == '\u0001') {
                        this.ftBufferSize = this.factory.getClientTransactionBufferSize().intValue();
                    }
                    if (nextToken24.charAt(0) == '\u0001') {
                        this.lsWaitTime = this.factory.getLocalStoreWaitTime();
                    }
                }
                catch (Exception ex) {
                    this.log.LogMsgln(2, true, null, "Cannot read value from Sonic connection file " + this.scConnectionFile);
                    this.log.LogStackTrace(3, true, null, ex);
                }
            }
            if (!stringTokenizer.hasMoreTokens()) {
                return;
            }
            this.connectID = stringTokenizer.nextToken();
            if (this.connectID.charAt(0) == '\u0001') {
                this.connectID = null;
            }
            if (this.factory != null && this.connectID == null) {
                this.connectID = this.factory.getConnectID();
            }
            if (!stringTokenizer.hasMoreTokens()) {
                return;
            }
        }
        
        private Object readFile(final String s) {
            Object object;
            try {
                final FileInputStream in = new FileInputStream(s);
                object = new ObjectInputStream(in).readObject();
                in.close();
            }
            catch (Exception ex) {
                object = null;
                this.log.LogMsgln(2, true, null, "Cannot read Sonic connection file " + s);
                this.log.LogStackTrace(3, true, null, ex);
            }
            return object;
        }
        
        static {
            PARAM_DELIMITER_ARRAY = new char[] { '\u0002' };
            PARAM_DELIMITER_STR = new String(GetClntStartupInfo.PARAM_DELIMITER_ARRAY);
        }
    }
}

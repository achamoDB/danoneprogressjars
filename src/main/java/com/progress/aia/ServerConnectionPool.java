// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;
import com.progress.common.util.UUID;
import java.util.Enumeration;
import java.net.MalformedURLException;
import com.progress.ubroker.util.ubWatchDog;
import java.util.Hashtable;
import com.progress.common.ehnlog.IAppLogger;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import com.progress.ubroker.util.IWatchable;
import com.progress.ubroker.util.ubConstants;

public class ServerConnectionPool implements ubConstants, IAiaDisplayInfo, IAiaDispInfoConst, IWatchable
{
    private static final int DEF_TIMEOUT = 3000;
    static DecimalFormat fmt2;
    static DecimalFormat fmt6;
    static SimpleDateFormat tf;
    static DateFormat df;
    static NumberFormat nf;
    private IAppLogger log;
    private Hashtable pool;
    private int connectionCount;
    private ubWatchDog ASKWatchdog;
    
    public ServerConnectionPool(final IAppLogger log) {
        this.log = log;
        this.pool = new Hashtable();
        this.connectionCount = 0;
    }
    
    public ServerConnection newServerConnection(final String s, final String s2, final String s3, final int n, final IAppLogger appLogger) throws MalformedURLException {
        return new ServerConnection(s, s2, s3, appLogger, ++this.connectionCount, n);
    }
    
    public ServerConnection addConnection(final ServerConnection value) {
        final String connHdl = this.newConnHdl();
        final ServerConnection serverConnection = this.pool.put(connHdl, value);
        value.setConnHdl(connHdl);
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "addConnection():  conn=" + value.toString() + " connhdl=" + connHdl + " oldConn=" + ((serverConnection == null) ? "null" : serverConnection.toString()));
        }
        return serverConnection;
    }
    
    public ServerConnection getConnection(final String s) {
        final ServerConnection obj = (s == null) ? null : this.pool.get(s);
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "getConnection(): connHdl=" + s + " ret=" + obj);
        }
        return obj;
    }
    
    public ServerConnection remConnection(final String s) {
        final ServerConnection obj = this.pool.remove(s);
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "remConnection(): connHdl=" + s + " ret=" + obj);
        }
        return obj;
    }
    
    public int size() {
        return this.pool.size();
    }
    
    public int count() {
        return this.connectionCount;
    }
    
    public boolean containsConn(final String key) {
        return this.pool.containsKey(key);
    }
    
    public synchronized Enumeration enumerate() {
        final Enumeration<Object> elements = this.pool.elements();
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "enumerate(): size=" + this.pool.size());
            int i = 0;
            final Enumeration<Object> enumeration = elements;
            while (enumeration.hasMoreElements()) {
                this.log.logVerbose(0, "elem[" + i + "]=" + enumeration.nextElement());
                ++i;
            }
        }
        return elements;
    }
    
    public synchronized void logPool(final String str) {
        if (!this.log.ifLogVerbose(1L, 0)) {
            return;
        }
        final Enumeration<Object> elements = this.pool.elements();
        this.log.logVerbose(0, str + " (pool size=" + this.pool.size() + ")");
        int i = 0;
        while (elements.hasMoreElements()) {
            this.log.logVerbose(0, "elem[" + i + "]=" + elements.nextElement());
            ++i;
        }
    }
    
    public synchronized String displayPoolInfo(final String str) {
        final Enumeration<ServerConnection> elements = this.pool.elements();
        final StringBuffer sb = new StringBuffer();
        sb.append(str + " (numCurrentConnections=" + this.pool.size() + ", numTotalConnections=" + this.connectionCount + ")\n");
        int n = 0;
        while (elements.hasMoreElements()) {
            final ServerConnection serverConnection = elements.nextElement();
            sb.append("[" + ServerConnectionPool.fmt6.format(serverConnection.getConnNum()) + "] " + serverConnection.getUserid() + " <---> " + serverConnection.getAppService() + " at " + serverConnection.getHost() + ":" + serverConnection.getPort() + "  " + ServerConnectionPool.fmt6.format(serverConnection.getNumPktsSent()) + "  " + ServerConnectionPool.fmt6.format(serverConnection.getNumPktsRcvd()) + "  " + this.fmttimestamp(serverConnection.getTsConnected()) + "  " + this.fmttimestamp(serverConnection.getTsLastAccess()) + "\n");
            ++n;
        }
        return sb.toString();
    }
    
    public synchronized void remAllConnections() {
        final Enumeration<ServerConnection> elements = this.pool.elements();
        if (this.log.ifLogVerbose(1L, 0)) {
            this.log.logVerbose(0, "remAllConnections(): size=" + this.pool.size());
        }
        while (elements.hasMoreElements()) {
            final ServerConnection obj = elements.nextElement();
            obj.disconnect();
            if (this.log.ifLogVerbose(1L, 0)) {
                this.log.logVerbose(0, obj + " disconnected.");
            }
        }
        this.pool.clear();
    }
    
    public synchronized int remMoldyConnections(final long n) {
        final Enumeration<ServerConnection> elements = this.pool.elements();
        final long currentTimeMillis = System.currentTimeMillis();
        this.logPool("Before mold removal");
        int n2 = 0;
        while (elements.hasMoreElements()) {
            final ServerConnection obj = elements.nextElement();
            final long n3 = currentTimeMillis - obj.getTsLastAccess();
            if (n3 > n) {
                obj.disconnect();
                this.remConnection(obj.getConnHdl());
                if (this.log.ifLogBasic(1L, 0)) {
                    this.log.logBasic(0, obj + " expired (inactive more than " + n3 / 1000L + "seconds). Connection closed.");
                }
                ++n2;
            }
        }
        if (n2 > 0) {
            this.logPool("After mold removal");
        }
        else if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, "No expired connections found.");
        }
        return n2;
    }
    
    public void startASKWatchdog(final AiaProperties aiaProperties) {
        if ((aiaProperties.clntAskCaps & 0x1) != 0x0) {
            (this.ASKWatchdog = new ubWatchDog("ASKWatchdog", this, 3000L, 6, this.log)).start();
        }
    }
    
    public void watchEvent() {
        final Enumeration<ServerConnection> elements = this.pool.elements();
        while (elements.hasMoreElements()) {
            this.checkSession(elements.nextElement());
        }
    }
    
    private void checkSession(final ServerConnection serverConnection) {
        serverConnection.manageASKPingRequest();
    }
    
    public String getDisplayInfoTitle() {
        return "Summary of Connection Status";
    }
    
    public String[] getDisplayInfoLabels() {
        if (this.pool.size() == 0) {
            return null;
        }
        return this.pool.elements().nextElement().getDisplayInfoLabels();
    }
    
    public AiaDisplayInfoDesc[][] getDisplayInfoTable() {
        if (this.pool.size() == 0) {
            return null;
        }
        final Enumeration<Object> keys = (Enumeration<Object>)this.pool.keys();
        final AiaDisplayInfoDesc[][] array = new AiaDisplayInfoDesc[this.pool.size()][8];
        int n = 0;
        while (keys.hasMoreElements()) {
            final Object nextElement = keys.nextElement();
            if (nextElement != null) {
                final IAiaDisplayInfo aiaDisplayInfo = this.pool.get(nextElement);
                if (aiaDisplayInfo == null) {
                    continue;
                }
                array[n] = aiaDisplayInfo.getDisplayInfoRow();
                ++n;
            }
        }
        return array;
    }
    
    public AiaDisplayInfoDesc[] getDisplayInfoRow() {
        return null;
    }
    
    private String newConnHdl() {
        return new UUID().toString().replace(':', 'o').replace('.', 'O');
    }
    
    private String fmttimestamp(final long date) {
        final Date date2 = new Date(date);
        return (date2 == null) ? "               " : (ServerConnectionPool.df.format(date2) + " " + ServerConnectionPool.tf.format(date2));
    }
    
    static {
        ServerConnectionPool.fmt2 = new DecimalFormat("00");
        ServerConnectionPool.fmt6 = new DecimalFormat("000000");
        (ServerConnectionPool.tf = new SimpleDateFormat("HH:mm")).setTimeZone(TimeZone.getDefault());
        ServerConnectionPool.df = DateFormat.getDateInstance(2, Locale.getDefault());
        (ServerConnectionPool.nf = ServerConnectionPool.df.getNumberFormat()).setMinimumIntegerDigits(2);
        ServerConnectionPool.nf.setMaximumIntegerDigits(2);
        ServerConnectionPool.df.setNumberFormat(ServerConnectionPool.nf);
    }
}

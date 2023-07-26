// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.util.Locale;
import java.util.TimeZone;
import java.net.InetAddress;
import java.util.Date;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class ubThreadStats
{
    public static final byte CONNSTATE_INIT = 0;
    public static final byte CONNSTATE_CONNECTING = 1;
    public static final byte CONNSTATE_CONNECTED = 2;
    public static final byte CONNSTATE_SENDING = 3;
    public static final byte CONNSTATE_RECEIVING = 4;
    public static final byte CONNSTATE_DISCONNECTING = 5;
    public static final byte CONNSTATE_DISCONNECTED = 6;
    public static final String[] DESC_CONNSTATE;
    static DecimalFormat fmt2;
    static DecimalFormat fmt3;
    static DecimalFormat fmt4;
    static DecimalFormat fmt5;
    static DecimalFormat fmt6;
    static SimpleDateFormat tf;
    static DateFormat df;
    static NumberFormat nf;
    private static Object connHdlCntrLock;
    private static int connHdlCntr;
    Date tsStatsReset;
    String threadname;
    int nRqs;
    Date tsLastStateChg;
    long tsLastSocketActivity;
    int nRqMsgs;
    int nRsps;
    int nRspMsgs;
    int nErrors;
    Date tsLastError;
    Date tsStartTime;
    int enqueueWaits;
    int maxQueueDepth;
    int maxRqDuration;
    long totRqDuration;
    int maxRqWait;
    long totRqWait;
    int connHdl;
    String connUserName;
    InetAddress connRmtHost;
    int connRmtPort;
    String connID;
    int connRqs;
    int connServerPID;
    int connServerPort;
    
    public static String getConnStateDesc(final int n) {
        return ubThreadStats.DESC_CONNSTATE[n];
    }
    
    private static int newConnHdl() {
        final int n;
        synchronized (ubThreadStats.connHdlCntrLock) {
            n = ++ubThreadStats.connHdlCntr;
        }
        return n;
    }
    
    public ubThreadStats() {
        this.threadname = new String("");
        this.resetStats();
    }
    
    public ubThreadStats(final String original) {
        this.threadname = new String(original);
        this.resetStats();
    }
    
    public ubThreadStats(final ubThreadStats ubThreadStats) {
        this.tsStatsReset = ubThreadStats.tsStatsReset;
        this.threadname = new String(ubThreadStats.getThreadname());
        this.nRqs = ubThreadStats.getnRqs();
        this.tsLastStateChg = ubThreadStats.tsLastStateChg;
        this.tsLastSocketActivity = ubThreadStats.gettsLastSocketActivity();
        this.nRqMsgs = ubThreadStats.getnRqMsgs();
        this.nRsps = ubThreadStats.getnRsps();
        this.nRspMsgs = ubThreadStats.getnRspMsgs();
        this.nErrors = ubThreadStats.getnErrors();
        this.tsLastError = ubThreadStats.tsLastError;
        this.tsStartTime = ubThreadStats.tsStartTime;
        this.maxQueueDepth = ubThreadStats.getMaxQueueDepth();
        this.enqueueWaits = ubThreadStats.getEnqueueWaits();
        this.maxRqDuration = ubThreadStats.maxRqDuration;
        this.totRqDuration = ubThreadStats.totRqDuration;
        this.maxRqWait = ubThreadStats.maxRqWait;
        this.totRqWait = ubThreadStats.totRqWait;
        this.connHdl = ubThreadStats.connHdl;
        this.connRmtHost = ubThreadStats.connRmtHost;
        this.connRmtPort = ubThreadStats.connRmtPort;
        this.connUserName = ubThreadStats.connUserName;
        this.connID = ubThreadStats.connID;
        this.connRqs = ubThreadStats.getConnRqs();
        this.connServerPID = ubThreadStats.connServerPID;
        this.connServerPort = ubThreadStats.connServerPort;
    }
    
    public String getThreadname() {
        return new String(this.threadname);
    }
    
    public synchronized Date gettsStatsReset() {
        return this.tsStatsReset;
    }
    
    public synchronized void settsStatsReset(final Date tsStatsReset) {
        this.tsStatsReset = tsStatsReset;
    }
    
    public synchronized void settsStatsReset() {
        this.tsStatsReset = new Date();
    }
    
    public synchronized int getnRqs() {
        return this.nRqs;
    }
    
    public synchronized String getFmtnRqs() {
        return ubThreadStats.fmt6.format(this.nRqs);
    }
    
    public synchronized int incrnRqs() {
        return ++this.nRqs;
    }
    
    public synchronized Date gettsLastStateChg() {
        return this.tsLastStateChg;
    }
    
    public synchronized String getFmtLastStateChg() {
        return this.fmttimestamp(this.tsLastStateChg);
    }
    
    public synchronized void settsLastStateChg(final Date tsLastStateChg) {
        this.tsLastStateChg = tsLastStateChg;
    }
    
    public synchronized void settsLastStateChg() {
        this.tsLastStateChg = new Date();
    }
    
    public synchronized long gettsLastSocketActivity() {
        return this.tsLastSocketActivity;
    }
    
    public synchronized void settsLastSocketActivity(final long tsLastSocketActivity) {
        this.tsLastSocketActivity = tsLastSocketActivity;
    }
    
    public synchronized void settsLastSocketActivity() {
        this.tsLastSocketActivity = System.currentTimeMillis();
    }
    
    public synchronized int getnRqMsgs() {
        return this.nRqMsgs;
    }
    
    public synchronized String getFmtnRqMsgs() {
        return ubThreadStats.fmt6.format(this.nRqMsgs);
    }
    
    public synchronized int incrnRqMsgs() {
        return ++this.nRqMsgs;
    }
    
    public synchronized int getnRsps() {
        return this.nRsps;
    }
    
    public synchronized String getFmtnRsps() {
        return ubThreadStats.fmt6.format(this.nRsps);
    }
    
    public synchronized int incrnRsps() {
        return ++this.nRsps;
    }
    
    public synchronized int getnRspMsgs() {
        return this.nRspMsgs;
    }
    
    public synchronized String getFmtnRspMsgs() {
        return ubThreadStats.fmt6.format(this.nRspMsgs);
    }
    
    public synchronized int incrnRspMsgs() {
        return ++this.nRspMsgs;
    }
    
    public synchronized int getnErrors() {
        return this.nErrors;
    }
    
    public synchronized String getFmtnErrors() {
        return ubThreadStats.fmt6.format(this.nErrors);
    }
    
    public synchronized int incrnErrors() {
        return ++this.nErrors;
    }
    
    public synchronized Date gettsLastError() {
        return this.tsLastError;
    }
    
    public synchronized String getFmtLastError() {
        return this.fmttimestamp(this.tsLastError);
    }
    
    public synchronized void settsLastError(final Date tsLastError) {
        this.tsLastError = tsLastError;
    }
    
    public synchronized void settsLastError() {
        this.tsLastError = new Date();
    }
    
    public synchronized Date gettsStartTime() {
        return this.tsStartTime;
    }
    
    public synchronized String getFmtStartTime() {
        return this.fmttimestamp(this.tsStartTime);
    }
    
    public synchronized void settsStartTime(final Date tsStartTime) {
        this.tsStartTime = tsStartTime;
    }
    
    public synchronized void settsStartTime() {
        this.tsStartTime = new Date();
    }
    
    public synchronized int getEnqueueWaits() {
        return this.enqueueWaits;
    }
    
    public synchronized String getFmtEnqueueWaits() {
        return ubThreadStats.fmt6.format(this.enqueueWaits);
    }
    
    public synchronized int setEnqueueWaits(final int enqueueWaits) {
        final int enqueueWaits2 = this.enqueueWaits;
        this.enqueueWaits = enqueueWaits;
        return enqueueWaits2;
    }
    
    public synchronized int getMaxQueueDepth() {
        return this.maxQueueDepth;
    }
    
    public synchronized String getFmtMaxQueueDepth() {
        return ubThreadStats.fmt6.format(this.maxQueueDepth);
    }
    
    public synchronized int setMaxQueueDepth(final int maxQueueDepth) {
        final int maxQueueDepth2 = this.maxQueueDepth;
        this.maxQueueDepth = maxQueueDepth;
        return maxQueueDepth2;
    }
    
    public synchronized int getMaxRqDuration() {
        return this.maxRqDuration;
    }
    
    public synchronized String getFmtMaxRqDuration() {
        return ubThreadStats.fmt6.format(this.maxRqDuration);
    }
    
    public synchronized long getTotRqDuration() {
        return this.totRqDuration;
    }
    
    public synchronized String getFmtTotRqDuration() {
        return ubThreadStats.fmt6.format(this.totRqDuration);
    }
    
    public synchronized void incTotRqDuration(final int maxRqDuration) {
        if (this.maxRqDuration < maxRqDuration) {
            this.maxRqDuration = maxRqDuration;
        }
        this.totRqDuration += maxRqDuration;
    }
    
    public synchronized int getMaxRqWait() {
        return this.maxRqWait;
    }
    
    public synchronized String getFmtMaxRqWait() {
        return ubThreadStats.fmt6.format(this.maxRqWait);
    }
    
    public synchronized long getTotRqWait() {
        return this.totRqWait;
    }
    
    public synchronized String getFmtTotRqWait() {
        return ubThreadStats.fmt6.format(this.totRqWait);
    }
    
    public synchronized void incTotRqWait(final int maxRqWait) {
        if (this.maxRqWait < maxRqWait) {
            this.maxRqWait = maxRqWait;
        }
        this.totRqWait += maxRqWait;
    }
    
    public synchronized int getConnHdl() {
        return this.connHdl;
    }
    
    public synchronized String getFmtConnHdl() {
        return ubThreadStats.fmt6.format(this.connHdl);
    }
    
    public synchronized int setConnHdl() {
        final int connHdl = this.connHdl;
        this.connHdl = newConnHdl();
        return connHdl;
    }
    
    public synchronized String getConnUserName() {
        return this.connUserName;
    }
    
    public synchronized String getFmtConnUserName() {
        return this.connUserName;
    }
    
    public synchronized String setConnUserName(final String connUserName) {
        final String connUserName2 = this.connUserName;
        this.connUserName = connUserName;
        return connUserName2;
    }
    
    public synchronized String getConnRmtHost() {
        return (this.connRmtHost == null) ? null : this.connRmtHost.getHostAddress();
    }
    
    public synchronized String getFmtConnRmtHost() {
        return (this.connRmtHost == null) ? null : this.connRmtHost.getHostAddress();
    }
    
    public synchronized String setConnRmtHost(final InetAddress connRmtHost) {
        final String s = (this.connRmtHost == null) ? null : this.connRmtHost.getHostAddress();
        this.connRmtHost = connRmtHost;
        return s;
    }
    
    public synchronized int getConnRmtPort() {
        return this.connRmtPort;
    }
    
    public synchronized String getFmtConnRmtPort() {
        return ubThreadStats.fmt6.format(this.connRmtPort);
    }
    
    public synchronized int setConnRmtPort(final int connRmtPort) {
        final int connRmtPort2 = this.connRmtPort;
        this.connRmtPort = connRmtPort;
        return connRmtPort2;
    }
    
    public synchronized String getConnID() {
        return this.connID;
    }
    
    public synchronized String getFmtConnID() {
        return this.connID;
    }
    
    public synchronized String setConnID(final String connID) {
        final String connID2 = this.connID;
        this.connID = connID;
        return connID2;
    }
    
    public synchronized int getConnRqs() {
        return this.connRqs;
    }
    
    public synchronized String getFmtConnRqs() {
        return ubThreadStats.fmt6.format(this.connRqs);
    }
    
    public synchronized int setConnRqs(final int connRqs) {
        final int connRqs2 = this.connRqs;
        this.connRqs = connRqs;
        return connRqs2;
    }
    
    public synchronized int incrConnRqs(final int n) {
        final int connRqs = this.connRqs;
        this.connRqs += n;
        return connRqs;
    }
    
    public synchronized int getConnServerPID() {
        return this.connServerPID;
    }
    
    public synchronized String getFmtConnServerPID() {
        return ubThreadStats.fmt6.format(this.connServerPID);
    }
    
    public synchronized int setConnServerPID(final int connServerPID) {
        final int connServerPID2 = this.connServerPID;
        this.connServerPID = connServerPID;
        return connServerPID2;
    }
    
    public synchronized int getConnServerPort() {
        return this.connServerPort;
    }
    
    public synchronized String getFmtConnServerPort() {
        return ubThreadStats.fmt6.format(this.connServerPort);
    }
    
    public synchronized int setConnServerPort(final int connServerPort) {
        final int connServerPort2 = this.connServerPort;
        this.connServerPort = connServerPort;
        return connServerPort2;
    }
    
    private String fmttimestamp(final Date date) {
        return (date == null) ? "               " : (ubThreadStats.df.format(date) + " " + ubThreadStats.tf.format(date));
    }
    
    private void resetStats() {
        this.tsStatsReset = new Date();
        this.nRqs = 0;
        this.tsLastStateChg = null;
        this.tsLastSocketActivity = System.currentTimeMillis();
        this.nRqMsgs = 0;
        this.nRsps = 0;
        this.nRspMsgs = 0;
        this.nErrors = 0;
        this.tsLastError = null;
        this.tsStartTime = null;
        this.enqueueWaits = 0;
        this.maxQueueDepth = 0;
        this.maxRqDuration = 0;
        this.totRqDuration = 0L;
        this.maxRqWait = 0;
        this.totRqWait = 0L;
        this.connHdl = 0;
        this.connUserName = null;
        this.connRmtHost = null;
        this.connRmtPort = 0;
        this.connID = null;
        this.connRqs = 0;
        this.connServerPID = 0;
        this.connServerPort = 0;
    }
    
    static {
        DESC_CONNSTATE = new String[] { "INITIALIZING", "CONNECTING", "CONNECTED", "SENDING", "RECEIVING", "DISCONNECTING", "DISCONNECTED" };
        ubThreadStats.fmt2 = new DecimalFormat("00");
        ubThreadStats.fmt3 = new DecimalFormat("000");
        ubThreadStats.fmt4 = new DecimalFormat("0000");
        ubThreadStats.fmt5 = new DecimalFormat("00000");
        ubThreadStats.fmt6 = new DecimalFormat("000000");
        (ubThreadStats.tf = new SimpleDateFormat("HH:mm")).setTimeZone(TimeZone.getDefault());
        ubThreadStats.df = DateFormat.getDateInstance(2, Locale.getDefault());
        (ubThreadStats.nf = ubThreadStats.df.getNumberFormat()).setMinimumIntegerDigits(2);
        ubThreadStats.nf.setMaximumIntegerDigits(2);
        ubThreadStats.df.setNumberFormat(ubThreadStats.nf);
        ubThreadStats.connHdlCntrLock = new Object();
        ubThreadStats.connHdlCntr = 0;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.aia;

import java.util.Locale;
import java.util.TimeZone;
import java.io.PrintStream;
import java.io.OutputStream;
import java.util.Properties;
import com.progress.common.ehnlog.ExtendedLogStream;
import java.util.Date;
import java.net.Inet6Address;
import com.progress.ubroker.util.ISSLSocketUtils;
import com.progress.ubroker.util.ISSLParams;
import com.progress.ubroker.ssl.SessionCache;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.io.IOException;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.ubMsg;
import java.net.MalformedURLException;
import com.progress.ubroker.ssl.SSLParamsFull;
import com.progress.ubroker.ssl.SSLSocketUtilsFull;
import com.progress.ubroker.util.MsgOutputStream;
import com.progress.ubroker.util.MsgInputStream;
import java.net.Socket;
import com.progress.ubroker.util.SocketConnectionInfoEx;
import com.progress.common.ehnlog.IAppLogger;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import com.progress.ubroker.util.ubConstants;

public class ServerConnection implements ubConstants, IAiaDispInfoConst, IAiaDisplayInfo
{
    public static final int ST_NOTCONNECTED = 0;
    public static final int ST_CONNECTED = 1;
    static final String[] DESC_STATE;
    static DecimalFormat fmt2;
    static DecimalFormat fmt3;
    static DecimalFormat fmt4;
    static DecimalFormat fmt5;
    static DecimalFormat fmt6;
    static SimpleDateFormat tf;
    static DateFormat df;
    static NumberFormat nf;
    private IAppLogger log;
    private String connHdl;
    private String connID;
    private int connNum;
    private SocketConnectionInfoEx sockInfo;
    private int serverPort;
    private Socket destSocket;
    private MsgInputStream is;
    private MsgOutputStream os;
    private int state;
    private long tsConnected;
    private long tsLastAccess;
    private long tsStatsReset;
    private long nPktsSent;
    private long nPktsRcvd;
    private String appService;
    private String appUserid;
    private int readTimeout;
    private SSLSocketUtilsFull m_socketUtils;
    private SSLSocketUtilsFull.SSLInfo m_sslInfo;
    private SSLParamsFull m_sslParams;
    private AiaProperties m_props;
    private String m_host;
    private int m_port;
    private int m_negotiatedAskVersion;
    private int m_negotiatedAskCaps;
    private boolean m_locked;
    private boolean m_xid;
    private String m_actUrl;
    
    public ServerConnection(final String s, final String appService, final String appUserid, final IAppLogger log, final int connNum, final int readTimeout) throws MalformedURLException {
        this.m_socketUtils = null;
        this.m_sslInfo = null;
        this.m_sslParams = null;
        this.m_props = null;
        this.m_host = null;
        this.m_port = 0;
        this.m_xid = false;
        this.m_actUrl = null;
        this.log = log;
        this.sockInfo = new SocketConnectionInfoEx(s);
        this.appService = appService;
        this.appUserid = appUserid;
        this.connNum = connNum;
        this.readTimeout = readTimeout;
        this.destSocket = null;
        this.is = null;
        this.os = null;
        this.connID = null;
        this.connHdl = null;
        this.state = 0;
        this.tsConnected = 0L;
        this.tsLastAccess = 0L;
        this.tsStatsReset = 0L;
        this.nPktsSent = 0L;
        this.nPktsRcvd = 0L;
        this.m_socketUtils = new SSLSocketUtilsFull();
        this.m_locked = false;
        this.m_negotiatedAskVersion = 0;
        this.m_negotiatedAskCaps = 0;
    }
    
    public void setActionalUrl(final String s) {
    }
    
    public String getActionalUrl() {
        if (null == this.m_actUrl) {
            final StringBuffer sb = new StringBuffer();
            sb.append("AppServer://");
            sb.append(this.getHost());
            sb.append('/');
            sb.append(this.getAppService());
            this.m_actUrl = sb.toString();
        }
        return this.m_actUrl;
    }
    
    public void setHost(final String s) {
        if (this.sockInfo != null) {
            this.sockInfo.setHost(s);
        }
        else if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "setHost(" + s + ") error : sockInfo == null");
        }
    }
    
    public String getHost() {
        return (this.sockInfo == null) ? "null" : this.sockInfo.getHost();
    }
    
    public void setService(final String s) {
        if (this.sockInfo != null) {
            this.sockInfo.setService(s);
        }
        else if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "setService(" + s + ") error : sockInfo == null");
        }
    }
    
    public String getService() {
        return (this.sockInfo == null) ? "null" : this.sockInfo.getService();
    }
    
    public void setStateFree(final boolean xid) {
        this.m_xid = xid;
    }
    
    public boolean isStateFree() {
        return this.m_xid;
    }
    
    public void setProtocol(final String s) {
        if (this.sockInfo != null) {
            this.sockInfo.setProtocol(s);
        }
        else if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "setProtocol(" + s + ") error : sockInfo == null");
        }
    }
    
    public String getProtocol() {
        return (this.sockInfo == null) ? "null" : this.sockInfo.getProtocol();
    }
    
    public void setPort(final int n) {
        if (this.sockInfo != null) {
            this.sockInfo.setPort(n);
        }
        else if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "setPort(" + n + ") error : sockInfo == null");
        }
    }
    
    public int getPort() {
        return (this.sockInfo == null) ? -1 : this.sockInfo.getPort();
    }
    
    public void setSocket(final Socket destSocket) {
        this.destSocket = destSocket;
    }
    
    public Socket getSocket() {
        return this.destSocket;
    }
    
    public void setConnID(final String connID) {
        this.connID = connID;
    }
    
    public String getConnID() {
        return this.connID;
    }
    
    public void setConnHdl(final String connHdl) {
        this.connHdl = connHdl;
    }
    
    public String getConnHdl() {
        return this.connHdl;
    }
    
    public void setConnNum(final int connNum) {
        this.connNum = connNum;
    }
    
    public int getConnNum() {
        return this.connNum;
    }
    
    public void setState(final int state) {
        this.state = state;
    }
    
    public int getState() {
        return this.state;
    }
    
    public void setServerPort(final int serverPort) {
        this.serverPort = serverPort;
    }
    
    public int getServerPort() {
        return this.serverPort;
    }
    
    public MsgInputStream getInputStream() {
        return this.is;
    }
    
    public MsgOutputStream getOutputStream() {
        return this.os;
    }
    
    public long getTsConnected() {
        return this.tsConnected;
    }
    
    public long getTsLastAccess() {
        return this.tsLastAccess;
    }
    
    public long getTsStatsReset() {
        return this.tsStatsReset;
    }
    
    public void resetStats() {
        final long n = 0L;
        this.nPktsRcvd = n;
        this.nPktsSent = n;
        this.tsStatsReset = System.currentTimeMillis();
    }
    
    public long getNumPktsSent() {
        return this.nPktsSent;
    }
    
    public long getNumPktsRcvd() {
        return this.nPktsRcvd;
    }
    
    public void setAppService(final String appService) {
        this.appService = appService;
    }
    
    public String getAppService() {
        return this.appService;
    }
    
    public void setUserid(final String appUserid) {
        this.appUserid = appUserid;
    }
    
    public String getUserid() {
        return this.appUserid;
    }
    
    public synchronized boolean lock(final boolean b) {
        final boolean locked = this.m_locked;
        if (b) {
            while (this.m_locked) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        this.m_locked = true;
        return locked;
    }
    
    public synchronized void unlock() {
        this.m_locked = false;
        this.notifyAll();
    }
    
    public boolean isLocked() {
        return this.m_locked;
    }
    
    public int getASKVersion() {
        return this.m_negotiatedAskVersion;
    }
    
    public int getASKCaps() {
        return this.m_negotiatedAskCaps;
    }
    
    public boolean getServerASKEnabled() {
        return (this.m_negotiatedAskCaps & 0x1) != 0x0;
    }
    
    public boolean getClientASKEnabled() {
        return (this.m_negotiatedAskCaps & 0x2) != 0x0;
    }
    
    public void negotiateAskCapabilities(final ubMsg ubMsg) {
        int negotiatedAskVersion;
        try {
            final String tlvField_NoThrow = ubMsg.getTlvField_NoThrow((short)10);
            negotiatedAskVersion = ((tlvField_NoThrow == null) ? 0 : Integer.parseInt(tlvField_NoThrow));
        }
        catch (Exception ex) {
            negotiatedAskVersion = 0;
        }
        if (negotiatedAskVersion == 0) {
            this.m_negotiatedAskVersion = 0;
            this.m_negotiatedAskCaps = 0;
            return;
        }
        this.m_negotiatedAskVersion = negotiatedAskVersion;
        int negotiatedAskCaps;
        try {
            final String tlvField_NoThrow2 = ubMsg.getTlvField_NoThrow((short)11);
            negotiatedAskCaps = ((tlvField_NoThrow2 == null) ? 0 : AiaProperties.parseAskCapabilities(tlvField_NoThrow2));
        }
        catch (Exception ex2) {
            negotiatedAskCaps = 0;
        }
        this.m_negotiatedAskCaps = negotiatedAskCaps;
    }
    
    private void askPingPacket(final int i) {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg((short)108, 70, 0, 0, 0);
        final String str = (i == 22) ? " sending ASKPing request" : ((i == 23) ? " sending ASKPing response" : (" (rqCode=" + i + ")"));
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(i);
        try {
            if (this.log.ifLogVerbose(16L, 4)) {
                this.log.logVerbose(4, this.toString() + str);
            }
            this.write(ubAppServerMsg, true);
        }
        catch (IOException ex) {
            this.log.logError(ex.getMessage());
        }
    }
    
    public void manageASKPingRequest() {
        if (this.log.ifLogVerbose(16L, 4)) {
            this.log.logVerbose(4, "manageASKPingRequest() - " + this.toString());
        }
        if (!this.getServerASKEnabled()) {
            if (this.log.ifLogVerbose(16L, 4)) {
                this.log.logVerbose(4, this.toString() + " ++++ serverASK not enabled for this session");
            }
            return;
        }
        if (this.lock(false)) {
            if (this.log.ifLogVerbose(16L, 4)) {
                this.log.logVerbose(4, this.toString() + " ++++ session already locked : manageASKPingRequest");
            }
            return;
        }
        if (this.log.ifLogVerbose(16L, 4)) {
            this.log.logVerbose(4, this.toString() + " ++++ session not locked : manageASKPingRequest");
        }
        if (this.state != 1) {
            this.log.logError("manageASKPingRequest() - " + this.toString() + " - Invalid state - " + ServerConnection.DESC_STATE[this.state]);
            this.unlock();
            return;
        }
        try {
            synchronized (this) {
                if (this.available() > 0) {
                    final ubAppServerMsg ubAppServerMsg = (ubAppServerMsg)this.read();
                    if (ubAppServerMsg.getubRq() == 22) {
                        if (this.log.ifLogVerbose(16L, 4)) {
                            this.log.logVerbose(4, this.toString() + " detected ASKPing request");
                        }
                        this.askPingPacket(23);
                    }
                    else {
                        this.log.logError("unexpected request received by ASKWatchDog : ubRq = " + ubAppServerMsg.getubRq());
                    }
                }
            }
        }
        catch (ubMsg.MsgFormatException ex) {
            this.log.logError(ex.getMessage() + ":" + ex.getDetail());
        }
        catch (IOException ex2) {
            this.log.logError(ex2.getMessage());
        }
        finally {
            if (this.log.ifLogVerbose(16L, 4)) {
                this.log.logVerbose(4, this.toString() + " ++++ unlocking session : manageASKPingRequest");
            }
            this.unlock();
            if (this.log.ifLogVerbose(16L, 4)) {
                this.log.logVerbose(4, this.toString() + " ++++ session unlocked : manageASKPingRequest");
            }
        }
    }
    
    public Socket newDestSocket(final String str, final int n) throws UnknownHostException, IOException {
        Object obj = null;
        Socket socket = null;
        if (this.log.ifLogBasic(32L, 5)) {
            this.log.logBasic(5, this.toString() + " : Connecting to " + str + " port " + n);
        }
        final InetAddress[] allByName = InetAddress.getAllByName(str);
        if (this.log.ifLogExtended(32L, 5)) {
            this.log.logExtended(5, this.toString() + " : Resolved " + str + " to : ");
            for (int i = 0; i < allByName.length; ++i) {
                this.log.logExtended(5, this.toString() + " :   [" + i + "] " + allByName[i].getHostAddress());
            }
        }
        int j = 0;
        while (j < allByName.length) {
            try {
                if (this.log.ifLogVerbose(32L, 5)) {
                    this.log.logVerbose(5, this.toString() + " : Connecting to " + allByName[j].getHostAddress() + " ... ");
                }
                socket = new Socket(allByName[j], n);
            }
            catch (IOException obj2) {
                if (this.log.ifLogVerbose(32L, 5)) {
                    this.log.logVerbose(5, this.toString() + " : Error connecting to " + allByName[j].getHostAddress() + " : " + obj2);
                }
                obj = obj2;
                ++j;
                continue;
            }
            break;
        }
        System.out.println("\n");
        if (j >= allByName.length) {
            if (this.log.ifLogBasic(32L, 5)) {
                this.log.logBasic(5, this.toString() + " : Unable to connect to host " + str + " port " + n + " : " + obj);
            }
            throw obj;
        }
        if (this.log.ifLogBasic(32L, 5)) {
            this.log.logBasic(5, this.toString() + " : Connected to " + str + " (" + allByName[j].getHostAddress() + ") port " + n);
        }
        return socket;
    }
    
    public void connect(final AiaProperties props) throws UnknownHostException, IOException {
        this.destSocket = this.newDestSocket(this.sockInfo.getHost(), this.sockInfo.getPort());
        if (this.destSocket == null) {
            this.log.logError("Error - ServerConnection.connect() failed to create a socket.");
            return;
        }
        this.m_host = this.destSocket.getInetAddress().getHostName();
        this.m_port = this.destSocket.getPort();
        this.m_props = props;
        if (this.m_props == null) {
            this.log.logError("Error - ServerConnection.connect() received a null AiaProperties");
        }
        if (this.isSSLEnabled()) {
            if (this.m_sslParams == null) {
                this.initSSLParams();
            }
            if (this.m_socketUtils == null) {
                this.m_socketUtils = new SSLSocketUtilsFull();
            }
            if (this.isSessionReuseEnabled()) {
                final SSLSocketUtilsFull.SSLInfo value = SessionCache.getInstance().get(this.m_host, this.m_port);
                this.m_sslParams.removeSession(this.m_host);
                if (value != null) {
                    this.m_sslParams.cacheSession(value.getVendorSession());
                }
            }
            if (this.m_socketUtils == null) {
                this.log.logError("Error converting socket to SSL socket in ServerConnection.connect()");
                throw new IOException("Error converting socket to SSL socket");
            }
            this.setSocket(this.m_socketUtils.createSSLSocket(this.destSocket, this.m_sslParams));
        }
        if (this.readTimeout > 0) {
            this.destSocket.setSoTimeout(this.readTimeout);
        }
        this.os = new MsgOutputStream(this.destSocket.getOutputStream(), 1024, this.log, 3, 8L, 3);
        this.is = new MsgInputStream(this.destSocket.getInputStream(), 10240, 0, this.log, 3, 8L, 3);
        if (this.isSSLEnabled()) {
            this.m_sslInfo = (SSLSocketUtilsFull.SSLInfo)this.m_socketUtils.getSocketSSLInfo(this.destSocket);
            if (this.m_sslInfo == null) {
                this.log.logError("Error - failed to get SSLInfo");
                throw new IOException("Error - failed to get SSLInfo");
            }
            if (this.isSessionReuseEnabled()) {
                SessionCache.getInstance().put(this.m_host, this.m_port, this.m_sslInfo);
            }
            if (this.isHostVerificationEnabled() && !this.m_socketUtils.isDefaultCertificate(this.m_sslInfo) && !this.verifyHost()) {
                this.log.logError("Error - server host name does not match");
                throw new IOException("Error - server host name does not match");
            }
        }
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "connected to (" + this.m_host + ") addressFamily= " + ((this.destSocket.getInetAddress() instanceof Inet6Address) ? "IPv6" : "IPv4"));
        }
        this.state = 1;
        this.tsConnected = System.currentTimeMillis();
        this.tsLastAccess = this.tsConnected;
        this.tsStatsReset = this.tsConnected;
        this.nPktsSent = 0L;
        this.nPktsRcvd = 0L;
    }
    
    public void disconnect() {
        try {
            if (this.os != null) {
                this.os.close();
            }
            if (this.is != null) {
                this.is.close();
            }
            if (this.destSocket != null) {
                this.destSocket.close();
                this.destSocket = null;
                if (this.isSSLEnabled() && this.isSessionReuseEnabled()) {
                    SessionCache.getInstance().release(this.m_host, this.m_port);
                }
            }
        }
        catch (IOException ex) {
            this.log.logError("Error closing socket :" + ex.toString() + " : " + ex.getMessage());
        }
        finally {
            if (this.destSocket != null) {
                try {
                    this.destSocket.close();
                    if (this.isSSLEnabled() && this.isSessionReuseEnabled()) {
                        SessionCache.getInstance().release(this.m_host, this.m_port);
                    }
                }
                catch (Exception ex2) {
                    this.log.logError("Error closing socket : " + ex2.toString() + " : " + ex2.getMessage());
                }
            }
            this.destSocket = null;
            this.os = null;
            this.is = null;
            this.state = 0;
            this.tsConnected = 0L;
            this.tsLastAccess = 0L;
            this.tsStatsReset = 0L;
            this.nPktsSent = 0L;
            this.nPktsRcvd = 0L;
            this.m_locked = false;
        }
    }
    
    public void write(final ubMsg ubMsg, final boolean b) throws IOException {
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "[" + this.toString() + "] " + "wrote " + ubMsg.getubRqDesc() + " to (" + this.getHost() + ":" + this.getPort() + ")");
        }
        this.os.writeMsg(ubMsg);
        if (b) {
            this.os.flush();
        }
        this.tsLastAccess = System.currentTimeMillis();
        ++this.nPktsSent;
    }
    
    public ubMsg read() throws IOException, ubMsg.MsgFormatException {
        final ubMsg msg = this.is.readMsg();
        if (this.log.ifLogVerbose(8L, 3)) {
            this.log.logVerbose(3, "[" + this.toString() + "] " + "read " + msg.getubRqDesc() + " from (" + this.getHost() + ":" + this.getPort() + ")");
        }
        this.tsLastAccess = System.currentTimeMillis();
        ++this.nPktsRcvd;
        return msg;
    }
    
    public int available() throws IOException {
        return this.is.available();
    }
    
    public String toString() {
        return "SC-" + ServerConnection.fmt6.format(this.connNum);
    }
    
    public String getDisplayInfoTitle() {
        return null;
    }
    
    public String[] getDisplayInfoLabels() {
        return new String[] { "ID", "User Info", "AppSvc", "Host:Port", "#Pkt Sent", "#Pkt Rcvd", "Connected", "Last Accessed" };
    }
    
    public AiaDisplayInfoDesc[] getDisplayInfoRow() {
        AiaDisplayInfoDesc[] array = null;
        try {
            array = new AiaDisplayInfoDesc[8];
            for (int i = 0; i < 8; ++i) {
                array[i] = new AiaDisplayInfoDesc();
            }
            array[0].setContent(new Integer(this.getConnNum()).toString(), 2);
            array[1].setContent(this.getUserid(), 1);
            array[2].setContent(this.getAppService(), 1);
            array[3].setContent(this.getHost() + ":" + this.getPort(), 1);
            array[4].setContent(ServerConnection.fmt6.format(this.getNumPktsSent()), 1);
            array[5].setContent(ServerConnection.fmt6.format(this.getNumPktsRcvd()), 1);
            array[6].setContent(this.fmttimestamp(this.getTsConnected()), 1);
            array[7].setContent(this.fmttimestamp(this.getTsLastAccess()), 1);
        }
        catch (Exception ex) {
            this.log.logError("Error filling status table row :" + ex.toString() + " : " + ex.getMessage());
        }
        return array;
    }
    
    public AiaDisplayInfoDesc[][] getDisplayInfoTable() {
        return null;
    }
    
    private String fmttimestamp(final long date) {
        final Date date2 = new Date(date);
        return (date2 == null) ? "               " : (ServerConnection.df.format(date2) + " " + ServerConnection.tf.format(date2));
    }
    
    public boolean isSSLEnabled() {
        return this.m_props != null && this.m_props.sslEnable;
    }
    
    private boolean isHostVerificationEnabled() {
        return this.m_props != null && !this.m_props.noHostVerify;
    }
    
    private boolean isSessionReuseEnabled() {
        return this.m_props != null && !this.m_props.noSessionReuse;
    }
    
    private boolean verifyHost() {
        try {
            final String commonNameField = this.m_socketUtils.getCommonNameField(this.m_socketUtils.getSubjectName(this.m_sslInfo));
            final String host = this.getHost();
            if (host == null) {
                return false;
            }
            final String canonicalHostName = InetAddress.getByName(host).getCanonicalHostName();
            return canonicalHostName != null && commonNameField != null && canonicalHostName.equalsIgnoreCase(commonNameField);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    private void initSSLParams() {
        final PrintStream printStream = new ExtendedLogStream(this.log, 8L, 3).getPrintStream();
        this.m_sslParams = new SSLParamsFull();
        try {
            this.m_sslParams.init(null, printStream, 6);
            this.m_sslParams.setReusingSessions(this.isSessionReuseEnabled());
            this.m_sslParams.setSSLVersions("sslv3,tlsv1");
            SessionCache.getInstance().setDebugStream(printStream);
        }
        catch (IOException ex) {
            this.log.logError("Error setting SSL parameters from ServerConnection");
            this.log.logError(ex.toString());
        }
        try {
            final StringBuffer sb = new StringBuffer();
            final String certStorePath = this.m_props.certStorePath;
            sb.append("-i ");
            sb.append(certStorePath);
            this.m_sslParams.loadAuthenticationCertificates(sb.toString());
        }
        catch (Exception ex2) {
            this.log.logError("Error loading Root CA certificate, loadpath=" + this.m_props.certStorePath);
            this.log.logError(ex2.toString());
        }
    }
    
    static {
        DESC_STATE = new String[] { "ST_NOTCONNECTED", "ST_CONNECTED" };
        ServerConnection.fmt2 = new DecimalFormat("00");
        ServerConnection.fmt3 = new DecimalFormat("000");
        ServerConnection.fmt4 = new DecimalFormat("0000");
        ServerConnection.fmt5 = new DecimalFormat("00000");
        ServerConnection.fmt6 = new DecimalFormat("000000");
        (ServerConnection.tf = new SimpleDateFormat("HH:mm")).setTimeZone(TimeZone.getDefault());
        ServerConnection.df = DateFormat.getDateInstance(2, Locale.getDefault());
        (ServerConnection.nf = ServerConnection.df.getNumberFormat()).setMinimumIntegerDigits(2);
        ServerConnection.nf.setMaximumIntegerDigits(2);
        ServerConnection.df.setNumberFormat(ServerConnection.nf);
    }
}

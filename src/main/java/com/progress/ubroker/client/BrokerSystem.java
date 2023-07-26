// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.client;

import java.io.InterruptedIOException;
import com.progress.ubroker.util.TlvItem;
import com.progress.open4gl.ConnectProtocolException;
import com.progress.open4gl.ConnectHttpsAuthException;
import com.progress.open4gl.ConnectHttpAuthException;
import com.progress.open4gl.ConnectAIAException;
import com.progress.open4gl.ConnectProxyAuthException;
import com.progress.ubroker.util.ubMsg;
import com.progress.open4gl.ConnectException;
import java.util.Properties;
import com.progress.open4gl.ConnectFailedException;
import com.progress.open4gl.AppServerIOException;
import java.io.IOException;
import com.progress.open4gl.BrokerIOException;
import java.net.UnknownHostException;
import com.progress.open4gl.UnknownHostnameException;
import com.progress.ubroker.util.NetworkProtocolException;
import com.progress.open4gl.broker.BrokerException;
import com.progress.ubroker.util.NetworkProtocolFactory;
import java.net.MalformedURLException;
import com.progress.open4gl.BadURLException;
import com.progress.open4gl.RunTimeProperties;
import com.progress.open4gl.javaproxy.IActionalInterceptor;
import java.util.Hashtable;
import com.progress.open4gl.dynamicapi.IPoolProps;
import com.progress.open4gl.dynamicapi.Tracer;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.ubAppServerMsg;
import com.progress.ubroker.util.IubMsgInputStream;
import com.progress.ubroker.util.IubMsgOutputStream;
import com.progress.ubroker.util.INetworkProtocol;
import com.progress.ubroker.util.SocketConnectionInfoEx;
import com.progress.message.jcMsg;
import com.progress.open4gl.broker.Broker;
import com.progress.ubroker.util.ubConstants;

public class BrokerSystem implements ubConstants, Broker, jcMsg
{
    public static final byte GOOD_CLOSE = 1;
    public static final byte BAD_CLOSE = 2;
    public static final byte DATA = 3;
    public static final int BUFSIZE = 8192;
    static final byte STATE_IDLE = 0;
    static final byte STATE_CONNECTED = 1;
    static final byte STATE_ALLOCATED = 2;
    static final byte STATE_SENDING = 3;
    static final byte STATE_RECEIVING = 4;
    static final byte STATE_EOF = 5;
    static final byte STATE_STOPPING = 6;
    static final byte STATE_ERROR = 7;
    static final String[] DESC_STATE;
    static final byte ASKSTATE_INIT = 0;
    static final byte ASKSTATE_ACTIVITY_TIMEOUT = 1;
    static final byte ASKSTATE_RESPONSE_TIMEOUT = 2;
    static final String[] DESC_ASKSTATE;
    static final String DEF_LOGFILE_BASE = "ubClient.";
    static final String DEF_LOGFILE = "BrokerSystem.log";
    static final boolean CONNECT_TO_BROKER = true;
    static final boolean CONNECT_TO_SERVER = false;
    static final int SOTIMEOUT = 2000;
    static final int HTTPTIMEOUT = 0;
    static final boolean TESTFLAG = false;
    int current_state;
    private SocketConnectionInfoEx sockInfo;
    private INetworkProtocol netProtocolHandler;
    int serverPort;
    String connID;
    private IubMsgOutputStream os;
    int os_pos;
    byte[] os_buf;
    private IubMsgInputStream is;
    ubAppServerMsg imsg;
    int is_pos;
    boolean eof;
    boolean stop_sent;
    int seqnum;
    IAppLogger log;
    int log_dest;
    boolean log_dump_full;
    int log_dump_max;
    int conn_info_maxlength;
    Tracer trace;
    int serverMode;
    short ubProtocolVersion;
    String m_requestID;
    IPoolProps m_properties;
    String m_sslSubjectName;
    private long m_basicLogEntries;
    private int m_basicLogIndex;
    private long m_debugLogEntries;
    private int m_debugLogIndex;
    private Hashtable m_capabilities;
    private String m_connectionReturnValue;
    private int m_negotiatedAskVersion;
    private int m_negotiatedAskCaps;
    private int m_clientASKActivityTimeout;
    private int m_clientASKResponseTimeout;
    private int m_clientASKActivityTimeoutMs;
    private int m_clientASKResponseTimeoutMs;
    private boolean m_ASKrqstACKsent;
    private byte m_ASKstate;
    private String m_sessionID;
    private long m_tsLastAccess;
    private IActionalInterceptor m_actional;
    
    public BrokerSystem(final IPoolProps poolProps, final IAppLogger appLogger) {
        this.sockInfo = null;
        this.netProtocolHandler = null;
        this.os = null;
        this.is = null;
        this.log_dump_full = false;
        this.log_dump_max = 200;
        this.conn_info_maxlength = 30000;
        this.trace = RunTimeProperties.tracer;
        this.init(poolProps, appLogger, 2);
    }
    
    protected void finalize() throws Throwable {
        if (null != this.netProtocolHandler) {
            if (null != this.os) {
                try {
                    this.os.close();
                }
                catch (Exception ex) {}
                this.os = null;
            }
            if (null != this.is) {
                try {
                    this.is.close();
                }
                catch (Exception ex2) {}
                this.is = null;
            }
            try {
                this.netProtocolHandler.closeConnection(true);
                this.netProtocolHandler.release();
            }
            catch (Exception ex3) {}
            this.netProtocolHandler = null;
        }
    }
    
    public void connect(final String s, final String s2, final String s3, final String s4, final String s5) throws BrokerException, ConnectException {
        int n = 0;
        final Properties asProperties = this.m_properties.getAsProperties();
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "connect() url= (" + s2 + ") " + "username= (" + s3 + ") " + "clientInfo= (" + s5 + ")");
        }
        this.trace.print("BrokerSystem CONNECT: url= (" + s2 + ") " + "username= (" + s3 + ") " + "clientInfo= (" + s5 + ")", 4);
        SocketConnectionInfoEx socketConnectionInfoEx;
        boolean directConnect;
        try {
            socketConnectionInfoEx = new SocketConnectionInfoEx(s2);
            final int protocolType = socketConnectionInfoEx.getProtocolType();
            directConnect = socketConnectionInfoEx.isDirectConnect();
            if ((protocolType || 5 == protocolType) && directConnect && 5162 == socketConnectionInfoEx.getPort()) {
                socketConnectionInfoEx.setPort(3090);
            }
        }
        catch (MalformedURLException ex2) {
            final BadURLException ex = new BadURLException(ex2.getMessage() + ": " + s2);
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
            throw ex;
        }
        if (this.current_state != 0) {
            final InvalidStateException ex3 = new InvalidStateException("connect", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex3);
            this.trace.print(ex3, 1);
            throw ex3;
        }
        try {
            (this.netProtocolHandler = NetworkProtocolFactory.create(0, socketConnectionInfoEx, this.log, this.log_dest)).init(asProperties, this.log, this.log_dest);
            if (!directConnect) {
                this.netProtocolHandler.resolveConnectionInfo(socketConnectionInfoEx);
            }
        }
        catch (MalformedURLException ex5) {
            final BadURLException ex4 = new BadURLException(ex5.getMessage() + ": " + s2);
            this.log.logStackTrace("", ex4);
            this.trace.print(ex4, 1);
            throw ex4;
        }
        catch (NetworkProtocolException ex7) {
            final BrokerException ex6 = new BrokerException(7, ex7.getMessage());
            this.log.logStackTrace("", ex6);
            this.trace.print(ex6, 1);
            throw ex6;
        }
        catch (Exception ex9) {
            final BrokerException ex8 = new BrokerException(ex9.toString());
            this.log.logStackTrace("", ex8);
            this.trace.print(ex8, 1);
            throw ex8;
        }
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "connect() host= (" + socketConnectionInfoEx.getHost() + ") " + "port= (" + socketConnectionInfoEx.getPort() + ") " + "service= (" + socketConnectionInfoEx.getService() + ") ");
        }
        this.trace.print("Connecting to Broker: host= (" + socketConnectionInfoEx.getHost() + ") " + "port= (" + socketConnectionInfoEx.getPort() + ") " + "service= (" + socketConnectionInfoEx.getService() + ") ", 4);
        final String property = asProperties.getProperty("PROGRESS.Session.appServerKeepalive");
        int askCapabilities;
        if (this.askValidateProtocolType(socketConnectionInfoEx)) {
            askCapabilities = this.parseAskCapabilities(property);
        }
        else {
            askCapabilities = 0;
        }
        if (askCapabilities == 0) {
            this.trace.print(this.m_sessionID + "      ASK Disabled.", 2);
        }
        else {
            this.trace.print(this.m_sessionID + "      Requesting ASK Capabilities= " + property, 2);
        }
        this.serverPort = 0;
        this.ubProtocolVersion = 109;
        while (this.ubProtocolVersion >= 108) {
            try {
                n = this.processConnect(s, socketConnectionInfoEx, s3, s4, s5, true, askCapabilities);
                if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.log.logBasic(this.m_debugLogIndex, "BrokerSystem processConnect(): got ubver= " + this.ubProtocolVersion);
                }
            }
            catch (UnknownHostException ex18) {
                final UnknownHostnameException ex10 = new UnknownHostnameException(socketConnectionInfoEx);
                this.log.logStackTrace("", ex10);
                throw ex10;
            }
            catch (IOException ex12) {
                final BrokerIOException ex11 = new BrokerIOException(socketConnectionInfoEx, ex12.toString());
                this.log.logStackTrace("", ex11);
                throw ex11;
            }
            catch (BrokerException ex13) {
                if (this.ubProtocolVersion > 108) {
                    this.disconnectSocket();
                    if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                        this.log.logBasic(this.m_debugLogIndex, "BrokerSystem : error on ver= " + this.ubProtocolVersion);
                    }
                    --this.ubProtocolVersion;
                    continue;
                }
                throw ex13;
            }
            break;
        }
        Label_1197: {
            if (n == 0 && this.serverPort != 0) {
                this.disconnectSocket();
                socketConnectionInfoEx.setPort(this.serverPort);
                this.serverMode = 1;
                try {
                    n = this.processConnect(s, socketConnectionInfoEx, s3, s4, s5, false, this.m_negotiatedAskCaps);
                    break Label_1197;
                }
                catch (UnknownHostException ex19) {
                    final UnknownHostnameException ex14 = new UnknownHostnameException(socketConnectionInfoEx);
                    this.log.logStackTrace("", ex14);
                    throw ex14;
                }
                catch (IOException ex16) {
                    final AppServerIOException ex15 = new AppServerIOException(socketConnectionInfoEx, ex16.toString());
                    this.log.logStackTrace("", ex15);
                    throw ex15;
                }
            }
            this.serverMode = 0;
        }
        if (n != 0) {
            final ConnectFailedException ex17 = new ConnectFailedException(n, this.m_connectionReturnValue);
            this.log.logStackTrace("", ex17);
            throw ex17;
        }
        if (askCapabilities != 0) {
            this.trace.print(this.m_sessionID + "      Negotiated ASK Version= " + this.formatAskVersion(this.m_negotiatedAskVersion) + "  Capabilities= " + this.formatAskCapabilities(this.m_negotiatedAskCaps), 1);
            if ((this.m_negotiatedAskCaps & 0x2) != 0x0) {
                this.trace.print(this.m_sessionID + "      ClientASKActivityTimeout= " + this.m_clientASKActivityTimeout + "  ClientASKResponseTimeout= " + this.m_clientASKResponseTimeout, 1);
            }
        }
    }
    
    public void xid(final String s, final String s2, final String s3, final String s4, final String s5) throws BrokerException, ConnectException {
        int processXID = 0;
        final Properties asProperties = this.m_properties.getAsProperties();
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "xid() url= (" + s2 + ") " + "username= (" + s3 + ") " + "connectionStr= (" + s5 + ")");
        }
        this.trace.print("BrokerSystem XID: url= (" + s2 + ") " + "username= (" + s3 + ") " + "connectionStr= (" + s5 + ")", 4);
        SocketConnectionInfoEx socketConnectionInfoEx;
        boolean directConnect;
        try {
            socketConnectionInfoEx = new SocketConnectionInfoEx(s2);
            final int protocolType = socketConnectionInfoEx.getProtocolType();
            directConnect = socketConnectionInfoEx.isDirectConnect();
            if ((protocolType || 5 == protocolType) && directConnect && 5162 == socketConnectionInfoEx.getPort()) {
                socketConnectionInfoEx.setPort(3090);
            }
        }
        catch (MalformedURLException ex2) {
            final BadURLException ex = new BadURLException(ex2.getMessage() + ": " + s2);
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
            throw ex;
        }
        if (this.current_state != 0) {
            final InvalidStateException ex3 = new InvalidStateException("xid", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex3);
            this.trace.print(ex3, 1);
            throw ex3;
        }
        try {
            (this.netProtocolHandler = NetworkProtocolFactory.create(0, socketConnectionInfoEx, this.log, this.log_dest)).init(asProperties, this.log, this.log_dest);
            if (!directConnect) {
                this.netProtocolHandler.resolveConnectionInfo(socketConnectionInfoEx);
            }
        }
        catch (MalformedURLException ex5) {
            final BadURLException ex4 = new BadURLException(ex5.getMessage() + ": " + s2);
            this.log.logStackTrace("", ex4);
            this.trace.print(ex4, 1);
            throw ex4;
        }
        catch (NetworkProtocolException ex7) {
            final BrokerException ex6 = new BrokerException(7, ex7.getMessage());
            this.log.logStackTrace("", ex6);
            this.trace.print(ex6, 1);
            throw ex6;
        }
        catch (Exception ex9) {
            final BrokerException ex8 = new BrokerException(ex9.toString());
            this.log.logStackTrace("", ex8);
            this.trace.print(ex8, 1);
            throw ex8;
        }
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "xid() host= (" + socketConnectionInfoEx.getHost() + ") " + "port= (" + socketConnectionInfoEx.getPort() + ") " + "service= (" + socketConnectionInfoEx.getService() + ") ");
        }
        this.trace.print("XID to Broker: host= (" + socketConnectionInfoEx.getHost() + ") " + "port= (" + socketConnectionInfoEx.getPort() + ") " + "service= (" + socketConnectionInfoEx.getService() + ") ", 4);
        final String property = asProperties.getProperty("PROGRESS.Session.appServerKeepalive");
        int askCapabilities;
        if (this.askValidateProtocolType(socketConnectionInfoEx)) {
            askCapabilities = this.parseAskCapabilities(property);
        }
        else {
            askCapabilities = 0;
        }
        if (askCapabilities == 0) {
            this.trace.print(this.m_sessionID + "      ASK Disabled.", 2);
        }
        else {
            this.trace.print(this.m_sessionID + "      Requesting ASK Capabilities= " + property, 2);
        }
        this.serverPort = 0;
        this.ubProtocolVersion = 109;
        while (this.ubProtocolVersion >= 109) {
            try {
                processXID = this.processXID(s, socketConnectionInfoEx, s3, s4, s5, askCapabilities);
                if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.log.logBasic(this.m_debugLogIndex, "BrokerSystem xid(): got ubver= " + this.ubProtocolVersion);
                }
            }
            catch (UnknownHostException ex15) {
                final UnknownHostnameException ex10 = new UnknownHostnameException(socketConnectionInfoEx);
                this.log.logStackTrace("", ex10);
                throw ex10;
            }
            catch (IOException ex12) {
                final BrokerIOException ex11 = new BrokerIOException(socketConnectionInfoEx, ex12.toString());
                this.log.logStackTrace("", ex11);
                throw ex11;
            }
            catch (BrokerException ex13) {
                if (this.ubProtocolVersion > 109) {
                    this.disconnectSocket();
                    if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                        this.log.logBasic(this.m_debugLogIndex, "BrokerSystem : error on ver= " + this.ubProtocolVersion);
                    }
                    --this.ubProtocolVersion;
                    continue;
                }
                throw ex13;
            }
            break;
        }
        this.serverMode = 3;
        if (processXID != 0) {
            final ConnectFailedException ex14 = new ConnectFailedException(processXID);
            this.log.logStackTrace("", ex14);
            throw ex14;
        }
        if (askCapabilities != 0) {
            this.trace.print(this.m_sessionID + "      Negotiated ASK Version= " + this.formatAskVersion(this.m_negotiatedAskVersion) + "  Capabilities= " + this.formatAskCapabilities(this.m_negotiatedAskCaps), 1);
            if ((this.m_negotiatedAskCaps & 0x2) != 0x0) {
                this.trace.print(this.m_sessionID + "      ClientASKActivityTimeout= " + this.m_clientASKActivityTimeout + "  ClientASKResponseTimeout= " + this.m_clientASKResponseTimeout, 1);
            }
        }
    }
    
    public void allocate(final String requestID) throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "allocate()");
        }
        if (this.current_state != 1) {
            final InvalidStateException ex = new InvalidStateException("allocate", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
            throw ex;
        }
        this.os_pos = 0;
        this.is_pos = 0;
        this.m_requestID = requestID;
        this.current_state = 2;
    }
    
    public void disconnect() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "disconnect()");
        }
        if (this.current_state != 0 && this.current_state != 7) {
            this.disconnectPacket("disconnect(" + this.connID + ")");
        }
        this.disconnectSocket();
    }
    
    public void unconditionalDisconnect() {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "unconditionalDisconnect()");
        }
        try {
            this.disconnectSocket();
        }
        catch (BrokerException ex) {
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
        }
    }
    
    public boolean isConnected() {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "isConnected() : current_state= " + BrokerSystem.DESC_STATE[this.current_state]);
        }
        return this.current_state != 0 && this.current_state != 7;
    }
    
    public void write(final int i) throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "write(" + i + ")");
        }
        final byte[] array = { (byte)i };
        this.write(array, 0, array.length);
    }
    
    public void write(final byte[] array) throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "write(msgbuf[])");
        }
        this.write(array, 0, array.length);
    }
    
    public void write(final byte[] array, final int i, final int j) throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "write(msgbuf[" + i + ":" + j + "]) " + "buflen= " + array.length);
        }
        if (this.current_state == 6) {
            final ClientStopException ex = new ClientStopException();
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
            throw ex;
        }
        if (this.current_state != 2 && this.current_state != 3) {
            final InvalidStateException ex2 = new InvalidStateException("write", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex2);
            this.trace.print(ex2, 1);
            throw ex2;
        }
        this.current_state = 3;
        int n2;
        for (int k = 0; k < j; k += n2) {
            final int n = this.os_buf.length - this.os_pos;
            n2 = ((n > j - k) ? (j - k) : n);
            System.arraycopy(array, i + k, this.os_buf, this.os_pos, n2);
            this.os_pos += n2;
            if (this.os_pos == this.os_buf.length) {
                this.writePacket(this.os_buf, this.os_pos);
                this.os_pos = 0;
            }
        }
        if (this.checkStop()) {
            final ServerStopException ex3 = new ServerStopException();
            this.log.logStackTrace("", ex3);
            this.trace.print(ex3.toString(), 1);
            throw ex3;
        }
    }
    
    public void prepareToReceive(final int i) throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "prepareToReceive(reason= " + i + ")");
        }
        if (this.current_state != 2 && this.current_state != 3) {
            final InvalidStateException ex = new InvalidStateException("prepareToReceive", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex);
            this.trace.print(ex.toString(), 1);
            throw ex;
        }
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "Setting current state to RECEIVING");
        }
        this.current_state = 4;
        this.writeLastPacket(i, this.os_buf, this.os_pos);
        this.os_pos = 0;
    }
    
    public void deallocate() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "deallocate()");
        }
        if (this.current_state != 5 && this.current_state != 2) {
            final InvalidStateException ex = new InvalidStateException("deallocate", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex);
            this.trace.print(ex.toString(), 1);
            throw ex;
        }
        this.os_pos = 0;
        this.is_pos = 0;
        this.eof = false;
        this.m_requestID = null;
        this.current_state = 1;
    }
    
    public int read() throws BrokerException {
        final byte[] array = { 0 };
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "read()");
        }
        return this.read(array, 0, array.length);
    }
    
    public int read(final byte[] array) throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "read(msgbuf[], len= " + array.length + ")");
        }
        return this.read(array, 0, array.length);
    }
    
    public int read(final byte[] array, final int n, final int i) throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "read(msgbuf[], offset= " + n + ", len= " + i + ") msgbuf.length= " + array.length);
        }
        if (this.current_state == 5) {
            this.stop_sent = false;
            return -1;
        }
        if (this.current_state != 4 && this.current_state != 6) {
            final InvalidStateException ex = new InvalidStateException("read", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
            throw ex;
        }
        int j = 0;
        while (j < i) {
            final int n2 = this.imsg.getBuflen() - this.is_pos;
            final int n3 = (n2 > i - j) ? (i - j) : n2;
            System.arraycopy(this.imsg.getMsgbuf(), this.is_pos, array, n + j, n3);
            this.is_pos += n3;
            j += n3;
            if (this.is_pos == this.imsg.getBuflen() && this.readPacket() == -1) {
                this.current_state = 5;
                this.stop_sent = false;
                break;
            }
        }
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "read(msgbuf[" + n + ":" + j + "]) ");
        }
        return j;
    }
    
    public void setStop() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "setStop() from state " + BrokerSystem.DESC_STATE[this.current_state]);
        }
        switch (this.current_state) {
            case 4: {
                this.current_state = 6;
                if (2 == this.netProtocolHandler.protocolType() || 3 == this.netProtocolHandler.protocolType()) {
                    this.setStopPacket();
                    break;
                }
                break;
            }
            case 3:
            case 6: {
                this.current_state = 6;
                break;
            }
            default: {
                this.log.logError("setStop:Invalid state: " + BrokerSystem.DESC_STATE[this.current_state]);
                this.trace.print("Non-fatal error - BrokerSystem.setStop:Invalid state: " + BrokerSystem.DESC_STATE[this.current_state], 1);
                break;
            }
        }
    }
    
    public boolean isReceiving() {
        return this.current_state == 4;
    }
    
    public boolean isStopping() {
        return this.current_state == 6;
    }
    
    public String getConnectionID() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "getConnectionID()");
        }
        return this.connID;
    }
    
    public String getRequestID() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "getRequestID()");
        }
        return this.m_requestID;
    }
    
    public String getSSLSubjectName() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "getSSLSubjectName()");
        }
        return this.m_sslSubjectName;
    }
    
    public String getConnectionReturnValue() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "getConnectionReturnValue()");
        }
        return this.m_connectionReturnValue;
    }
    
    public int getASKVersion() {
        return this.m_negotiatedAskVersion;
    }
    
    public boolean getServerASKEnabled() {
        return (this.m_negotiatedAskCaps & 0x1) != 0x0;
    }
    
    public boolean getClientASKEnabled() {
        return (this.m_negotiatedAskCaps & 0x2) != 0x0;
    }
    
    public int getClientASKActivityTimeout() {
        return this.m_clientASKActivityTimeout;
    }
    
    public int getClientASKResponseTimeout() {
        return this.m_clientASKResponseTimeout;
    }
    
    public void manageASKPingRequest() throws BrokerException {
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, this.m_sessionID + " manageASKPingRequest()");
        }
        if (this.current_state != 1) {
            final InvalidStateException ex = new InvalidStateException("manageASKPingRequest", BrokerSystem.DESC_STATE[this.current_state]);
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
            throw ex;
        }
        try {
            if (this.is.available() > 0) {
                final ubAppServerMsg ubAppServerMsg = (ubAppServerMsg)this.is.readMsg();
                this.m_tsLastAccess = System.currentTimeMillis();
                if (ubAppServerMsg.getubRq() != 22) {
                    final BrokerException ex2 = new BrokerException(7, "unexpected request received by ASKWatchDog : ubRq = " + ubAppServerMsg.getubRq());
                    this.log.logStackTrace("", ex2);
                    this.trace.print(ex2, 1);
                    throw ex2;
                }
                this.trace.print(this.m_sessionID + " detected ASKPing request", 4);
                this.askPingPacket(23);
            }
        }
        catch (ubMsg.MsgFormatException ex4) {
            final MessageFormatException ex3 = new MessageFormatException();
            this.log.logStackTrace("", ex3);
            this.log.logError(ex4.getMessage() + ":" + ex4.getDetail());
            this.trace.print(ex3.toString() + ex4.getMessage() + ":" + ex4.getDetail(), 1);
            throw ex3;
        }
        catch (NetworkProtocolException ex6) {
            final BrokerException ex5 = new BrokerException(7, ex6.getMessage());
            this.log.logStackTrace("", ex5);
            this.trace.print(ex5, 1);
            throw ex5;
        }
        catch (IOException obj) {
            final BrokerException ex7 = new BrokerException(0, obj + " [" + obj.getMessage() + "]");
            this.log.logStackTrace("", ex7);
            this.trace.print(ex7, 1);
            throw ex7;
        }
    }
    
    private void init(final IPoolProps properties, final IAppLogger log, final int log_dest) {
        this.current_state = 0;
        this.sockInfo = null;
        this.serverPort = 0;
        this.connID = null;
        this.os = null;
        this.os_pos = 0;
        this.os_buf = new byte[8192];
        this.imsg = new ubAppServerMsg((short)109, 0);
        this.is = null;
        this.is_pos = 0;
        this.seqnum = 0;
        this.eof = false;
        this.stop_sent = false;
        this.serverMode = 0;
        this.log = log;
        this.log_dest = log_dest;
        this.ubProtocolVersion = 109;
        this.m_requestID = null;
        this.m_properties = properties;
        this.m_sslSubjectName = null;
        this.m_connectionReturnValue = null;
        this.m_capabilities = new Hashtable();
        this.m_negotiatedAskVersion = 0;
        this.m_negotiatedAskCaps = 0;
        this.m_clientASKActivityTimeout = this.m_properties.getIntProperty("PROGRESS.Session.clientASKActivityTimeout");
        this.m_clientASKResponseTimeout = this.m_properties.getIntProperty("PROGRESS.Session.clientASKResponseTimeout");
        this.m_clientASKActivityTimeoutMs = this.m_clientASKActivityTimeout * 1000;
        this.m_clientASKResponseTimeoutMs = this.m_clientASKResponseTimeout * 1000;
        this.m_ASKstate = 0;
        this.m_ASKrqstACKsent = false;
        this.m_tsLastAccess = 0L;
        this.m_sessionID = null;
        this.initializeLogging(log);
        this.m_actional = (IActionalInterceptor)this.m_properties.getProperty("PROGRESS.Session.manifest");
        if (null != this.m_actional) {
            if (log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                log.logBasic(this.m_debugLogIndex, "BrokerSystem has ActionalInterceptor");
            }
        }
        else if (log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            log.logBasic(this.m_debugLogIndex, "BrokerSystem does not have ActionalInterceptor");
        }
    }
    
    private int processConnect(final String s, final Object o, final String s2, final String s3, final String s4, final boolean b, final int n) throws BrokerException, UnknownHostException, IOException, ConnectException {
        if (null == this.sockInfo) {
            this.sockInfo = (SocketConnectionInfoEx)o;
        }
        int connectPacket;
        try {
            if (null == this.netProtocolHandler) {
                throw new NullPointerException("Null protocol handler");
            }
            this.validateConnectionInfo(s2, s3, s4);
            this.netProtocolHandler.openConnection(this.sockInfo, 0, null, null, null);
            this.os = this.netProtocolHandler.getMsgOutputStream(0);
            this.is = this.netProtocolHandler.getMsgInputStream(0);
            this.m_sslSubjectName = this.netProtocolHandler.getSSLSubjectName();
            this.seqnum = 0;
            connectPacket = this.connectPacket(s, s2, s3, s4, b, n);
            if (connectPacket != 0) {
                this.disconnectSocket();
            }
            this.eof = false;
            this.current_state = ((connectPacket == 0) ? 1 : 0);
        }
        catch (NetworkProtocolException ex) {
            ConnectException ex2 = null;
            switch (ex.exceptionId()) {
                case 13: {
                    ex2 = new ConnectProxyAuthException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                case 12: {
                    ex2 = new ConnectAIAException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                case 9: {
                    ex2 = new ConnectHttpAuthException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                case 5: {
                    ex2 = new ConnectHttpsAuthException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                default: {
                    ex2 = new ConnectProtocolException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
            }
            this.log.logStackTrace("NetworkProtocolException translation: ", ex2);
            this.trace.print(ex2, 1);
            throw ex2;
        }
        catch (Exception ex4) {
            final BrokerException ex3 = new BrokerException(7, ex4.getMessage());
            this.log.logStackTrace("", ex3);
            this.trace.print(ex3, 1);
            throw ex3;
        }
        return connectPacket;
    }
    
    private int processXID(final String s, final Object o, final String s2, final String s3, final String s4, final int n) throws BrokerException, UnknownHostException, IOException, ConnectException {
        if (null == this.sockInfo) {
            this.sockInfo = (SocketConnectionInfoEx)o;
        }
        int xidPacket;
        try {
            if (null == this.netProtocolHandler) {
                throw new NullPointerException("Null protocol handler");
            }
            this.validateConnectionInfo(s2, s3, s4);
            this.netProtocolHandler.openConnection(this.sockInfo, 0, null, null, null);
            this.os = this.netProtocolHandler.getMsgOutputStream(0);
            this.is = this.netProtocolHandler.getMsgInputStream(0);
            this.seqnum = 0;
            xidPacket = this.xidPacket(s, s2, s3, s4, n);
            if (xidPacket != 0) {
                this.disconnectSocket();
            }
            this.eof = false;
            this.current_state = ((xidPacket == 0) ? 1 : 0);
        }
        catch (NetworkProtocolException ex) {
            ConnectException ex2 = null;
            switch (ex.exceptionId()) {
                case 13: {
                    ex2 = new ConnectProxyAuthException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                case 12: {
                    ex2 = new ConnectAIAException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                case 9: {
                    ex2 = new ConnectHttpAuthException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                case 5: {
                    ex2 = new ConnectHttpsAuthException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
                default: {
                    ex2 = new ConnectProtocolException(ex.jcMsgId(), ex.messageTokens());
                    break;
                }
            }
            this.log.logStackTrace("NetworkProtocolException translation: ", ex2);
            this.trace.print(ex2, 1);
            throw ex2;
        }
        catch (Exception ex4) {
            final BrokerException ex3 = new BrokerException(7, ex4.getMessage());
            this.log.logStackTrace("", ex3);
            this.trace.print(ex3, 1);
            throw ex3;
        }
        return xidPacket;
    }
    
    private int connectPacket(final String s, final String s2, final String s3, final String s4, final boolean b, final int n) throws IOException, BrokerException, NetworkProtocolException {
        final int n2 = 0;
        byte[] netByteArray = null;
        byte[] netByteArray2 = null;
        byte[] netByteArray3 = null;
        byte[] netByteArray4 = null;
        byte[] netByteArray5 = null;
        TlvItem tlvItem = null;
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "connectPacket() fToBroker= " + b);
        }
        if (null != this.m_actional) {
            final String header = this.m_actional.getHeader();
            if (null != header) {
                tlvItem = new TlvItem((short)13, header, false);
            }
        }
        int n3 = n2 + (("utf-8" == null) ? 2 : ("utf-8".length() + 3));
        if (s2 == null) {
            n3 += 2;
        }
        else {
            netByteArray = ubMsg.newNetByteArray(s2);
            n3 += netByteArray.length + 3;
        }
        if (s3 == null) {
            n3 += 2;
        }
        else {
            netByteArray2 = ubMsg.newNetByteArray(s3);
            n3 += netByteArray2.length + 3;
        }
        if (s4 == null) {
            n3 += 2;
        }
        else {
            netByteArray3 = ubMsg.newNetByteArray(s4);
            n3 += netByteArray3.length + 3;
        }
        if (this.connID == null) {
            n3 += 2;
        }
        else {
            netByteArray4 = ubMsg.newNetByteArray(this.connID);
            n3 += netByteArray4.length + 3;
        }
        if (2 == this.sockInfo.getProtocolType() || 3 == this.sockInfo.getProtocolType()) {
            if (this.sockInfo.getService() == null) {
                n3 += 2;
            }
            else {
                netByteArray5 = ubMsg.newNetByteArray(this.sockInfo.getService());
                n3 += netByteArray5.length + 3;
            }
        }
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(this.ubProtocolVersion, 10, ++this.seqnum, n3, n3);
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(3);
        switch (this.ubProtocolVersion) {
            case 108: {
                break;
            }
            default: {
                try {
                    ubAppServerMsg.appendTlvField((short)1, s);
                    if (null != tlvItem) {
                        ubAppServerMsg.appendTlvField(tlvItem.getTlvType(), tlvItem.getTlvData());
                    }
                    for (int i = 0; i < ubConstants.APPSRVCAPINFO_TYPE.length; ++i) {
                        ubAppServerMsg.appendTlvField(ubConstants.APPSRVCAPINFO_TYPE[i], ubConstants.APPSRVCAPINFO_VALUE[i]);
                    }
                    if (n != 0) {
                        ubAppServerMsg.appendTlvField((short)10, String.valueOf(65536));
                        ubAppServerMsg.appendTlvField((short)11, this.formatAskCapabilities(n));
                    }
                }
                catch (Exception ex) {
                    this.log.logError("appendTlvField Exception: " + ex.getMessage());
                    final BrokerException ex2 = new BrokerException(7, ex.getMessage());
                    this.trace.print(ex2, 1);
                    throw ex2;
                }
                break;
            }
        }
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        int buflen = com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, 0, "utf-8"), netByteArray), netByteArray2), netByteArray3), netByteArray4);
        if (2 == this.sockInfo.getProtocolType() || 3 == this.sockInfo.getProtocolType()) {
            buflen = com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, buflen, netByteArray5);
        }
        ubAppServerMsg.setBuflen(buflen);
        ubAppServerMsg msg;
        try {
            this.os.writeMsg(ubAppServerMsg);
            this.os.flushMsg();
            this.m_tsLastAccess = System.currentTimeMillis();
            msg = null;
            while (msg == null) {
                try {
                    msg = this.readMsg();
                }
                catch (InterruptedIOException ex3) {
                    if (!this.log.ifLogVerbose(this.m_basicLogEntries, this.m_basicLogIndex)) {
                        continue;
                    }
                    this.log.logVerbose(this.m_basicLogIndex, "Interrupted CONNECT readMsg() IO Exception: " + ex3.getMessage());
                }
            }
        }
        catch (ubMsg.MsgFormatException ex4) {
            this.log.logError("CONNECT MsgFormatException:  " + ex4.getMessage() + ":" + ex4.getDetail());
            this.trace.print("CONNECT MsgFormatException:  " + ex4.getMessage() + ":" + ex4.getDetail(), 1);
            return 8;
        }
        catch (IOException obj) {
            this.log.logError("CONNECT write IOException:  " + obj);
            throw obj;
        }
        final int getubRsp = msg.getubRsp();
        this.m_connectionReturnValue = msg.get4GLErrMsg();
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex) && this.m_connectionReturnValue != null) {
            this.log.logBasic(this.m_debugLogIndex, "connect procedure return value = " + this.m_connectionReturnValue);
        }
        if (getubRsp == 0 && msg.getMsgcode() == 11) {
            if (msg.get4GLCondCode() != 0) {
                this.log.logError("CONNECT failure(Open4GLCondCode)= " + msg.get4GLCondCode());
                this.trace.print("CONNECT failure(Open4GLCondCode)= " + msg.get4GLCondCode(), 1);
                return 11;
            }
            if (b) {
                final int buflen2 = msg.getBuflen();
                final int n4 = ((buflen2 < 5) ? 0 : ubMsg.getNetShort(msg.getMsgbuf(), 3)) + 14;
                this.connID = ((buflen2 > n4) ? com.progress.ubroker.util.ubAppServerMsg.getNetString(msg.getMsgbuf(), n4) : null);
                this.trace.print("Connection ID= " + this.connID, 4);
                this.serverPort = (msg.getubRspExt() >> 16 & 0xFFFF);
                if (this.log.ifLogVerbose(this.m_basicLogEntries, this.m_basicLogIndex)) {
                    this.log.logVerbose(this.m_basicLogIndex, "serverPort= " + this.serverPort);
                }
                this.trace.print("CONNECT: Reconnecting to AppServer at port " + this.serverPort);
                this.negotiateAskCapabilities(msg);
            }
            try {
                final String tlvField_NoThrow = msg.getTlvField_NoThrow((short)3001);
                if (tlvField_NoThrow != null) {
                    this.m_capabilities.put(new Short((short)3001), tlvField_NoThrow);
                }
            }
            catch (Exception ex5) {}
        }
        else {
            String netString = null;
            if (0 < ((msg.getBuflen() < 5) ? 0 : ubMsg.getNetShort(msg.getMsgbuf(), 3))) {
                netString = com.progress.ubroker.util.ubAppServerMsg.getNetString(msg.getMsgbuf(), 3);
            }
            this.log.logError(new String("CONNECT failure= " + getubRsp + " (" + netString + ")"));
            this.trace.print("CONNECT: Unable to connect - reason= " + getubRsp, 1);
        }
        return getubRsp;
    }
    
    private int xidPacket(final String s, final String s2, final String s3, final String s4, final int n) throws IOException, BrokerException, NetworkProtocolException {
        final int n2 = 0;
        byte[] netByteArray = null;
        byte[] netByteArray2 = null;
        byte[] netByteArray3 = null;
        byte[] netByteArray4 = null;
        byte[] netByteArray5 = null;
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "xidPacket()");
        }
        int n3 = n2 + (("utf-8" == null) ? 2 : ("utf-8".length() + 3));
        if (s2 == null) {
            n3 += 2;
        }
        else {
            netByteArray = ubMsg.newNetByteArray(s2);
            n3 += netByteArray.length + 3;
        }
        if (s3 == null) {
            n3 += 2;
        }
        else {
            netByteArray2 = ubMsg.newNetByteArray(s3);
            n3 += netByteArray2.length + 3;
        }
        if (s4 == null) {
            n3 += 2;
        }
        else {
            netByteArray3 = ubMsg.newNetByteArray(s4);
            n3 += netByteArray3.length + 3;
        }
        if (this.connID == null) {
            n3 += 2;
        }
        else {
            netByteArray4 = ubMsg.newNetByteArray(this.connID);
            n3 += netByteArray4.length + 3;
        }
        if (2 == this.sockInfo.getProtocolType() || 3 == this.sockInfo.getProtocolType()) {
            if (this.sockInfo.getService() == null) {
                n3 += 2;
            }
            else {
                netByteArray5 = ubMsg.newNetByteArray(this.sockInfo.getService());
                n3 += netByteArray5.length + 3;
            }
        }
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(this.ubProtocolVersion, 70, ++this.seqnum, n3, n3);
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(2);
        switch (this.ubProtocolVersion) {
            case 108: {
                break;
            }
            default: {
                try {
                    ubAppServerMsg.appendTlvField((short)1, s);
                    for (int i = 0; i < ubConstants.APPSRVCAPINFO_TYPE.length; ++i) {
                        ubAppServerMsg.appendTlvField(ubConstants.APPSRVCAPINFO_TYPE[i], ubConstants.APPSRVCAPINFO_VALUE[i]);
                    }
                    if (n != 0) {
                        ubAppServerMsg.appendTlvField((short)10, String.valueOf(65536));
                        ubAppServerMsg.appendTlvField((short)11, this.formatAskCapabilities(n));
                    }
                }
                catch (Exception ex) {
                    this.log.logError("appendTlvField Exception: " + ex.getMessage());
                    final BrokerException ex2 = new BrokerException(7, ex.getMessage());
                    this.trace.print(ex2, 1);
                    throw ex2;
                }
                break;
            }
        }
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        int buflen = com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, 0, "utf-8"), netByteArray), netByteArray2), netByteArray3), netByteArray4);
        if (2 == this.sockInfo.getProtocolType() || 3 == this.sockInfo.getProtocolType()) {
            buflen = com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, buflen, netByteArray5);
        }
        ubAppServerMsg.setBuflen(buflen);
        ubMsg msg;
        try {
            this.os.writeMsg(ubAppServerMsg);
            this.os.flushMsg();
            this.m_tsLastAccess = System.currentTimeMillis();
            msg = null;
            while (msg == null) {
                try {
                    msg = this.readMsg();
                }
                catch (InterruptedIOException ex3) {
                    if (!this.log.ifLogVerbose(this.m_basicLogEntries, this.m_basicLogIndex)) {
                        continue;
                    }
                    this.log.logVerbose(this.m_basicLogIndex, "Interrupted XID readMsg() IO Exception: " + ex3.getMessage());
                }
            }
        }
        catch (ubMsg.MsgFormatException ex4) {
            this.log.logError("XID MsgFormatException:  " + ex4.getMessage() + ":" + ex4.getDetail());
            this.trace.print("XID MsgFormatException:  " + ex4.getMessage() + ":" + ex4.getDetail(), 1);
            return 8;
        }
        catch (IOException obj) {
            this.log.logError("XID write IOException:  " + obj);
            throw obj;
        }
        final int getubRsp = msg.getubRsp();
        if (getubRsp == 0) {
            final int buflen2 = msg.getBuflen();
            final int n4 = ((buflen2 < 5) ? 0 : ubMsg.getNetShort(msg.getMsgbuf(), 3)) + 14;
            this.connID = ((buflen2 > n4) ? com.progress.ubroker.util.ubAppServerMsg.getNetString(msg.getMsgbuf(), n4) : null);
            if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.log.logBasic(this.m_debugLogIndex, "connID= " + this.connID);
            }
            this.trace.print("Connection ID= " + this.connID, 4);
            this.serverPort = 0;
            this.negotiateAskCapabilities(msg);
            try {
                final String tlvField_NoThrow = msg.getTlvField_NoThrow((short)3001);
                if (tlvField_NoThrow != null) {
                    this.m_capabilities.put(new Short((short)3001), tlvField_NoThrow);
                }
            }
            catch (Exception ex5) {}
        }
        else {
            String netString = null;
            if (0 < ((msg.getBuflen() < 5) ? 0 : ubMsg.getNetShort(msg.getMsgbuf(), 3))) {
                netString = com.progress.ubroker.util.ubAppServerMsg.getNetString(msg.getMsgbuf(), 3);
            }
            this.log.logError(new String("XID failure= " + getubRsp + " (" + netString + ")"));
            this.trace.print("XID: Unable to connect - reason= " + getubRsp, 1);
        }
        return getubRsp;
    }
    
    private boolean writePacket(final byte[] array, final int n) throws BrokerException {
        boolean b = true;
        TlvItem tlvItem = null;
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(this.ubProtocolVersion, 70, ++this.seqnum, n, 0);
        if (null != this.m_actional) {
            final String header = this.m_actional.getHeader();
            if (null != header) {
                tlvItem = new TlvItem((short)13, header, false);
            }
        }
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(4);
        switch (this.ubProtocolVersion) {
            case 108: {
                break;
            }
            default: {
                try {
                    ubAppServerMsg.appendTlvField((short)1, this.m_requestID);
                    this.addASKRequest(ubAppServerMsg, true);
                    if (null != tlvItem) {
                        ubAppServerMsg.appendTlvField(tlvItem.getTlvType(), tlvItem.getTlvData());
                    }
                }
                catch (Exception ex) {
                    this.log.logError("appendTlvField Exception: " + ex.getMessage());
                    final BrokerException ex2 = new BrokerException(7, ex.getMessage());
                    this.trace.print(ex2, 1);
                    this.current_state = 7;
                    throw ex2;
                }
                break;
            }
        }
        ubAppServerMsg.setMsgbuf(array, n);
        try {
            this.os.writeMsg(ubAppServerMsg);
            b = true;
            this.m_tsLastAccess = System.currentTimeMillis();
        }
        catch (IOException ex3) {
            this.eof = false;
            this.os_pos = 0;
            this.current_state = 7;
            this.throwCommunicationsException(8, "WRITEDATA IOException", ex3);
        }
        catch (NetworkProtocolException ex4) {
            this.eof = false;
            this.os_pos = 0;
            this.throwCommunicationsException(this.current_state = 7, "WRITEDATA NetworkProtocolException", ex4);
        }
        this.eof = false;
        return b;
    }
    
    private boolean writeLastPacket(final int n, final byte[] array, final int n2) throws BrokerException {
        boolean b = true;
        TlvItem tlvItem = null;
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(this.ubProtocolVersion, 70, ++this.seqnum, n2, 0);
        if (null != this.m_actional) {
            final String header = this.m_actional.getHeader();
            if (null != header) {
                tlvItem = new TlvItem((short)13, header, false);
            }
        }
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(5);
        switch (this.ubProtocolVersion) {
            case 108: {
                ubAppServerMsg.setubRqExt(0x20000000 | (n & 0xFF) << 8);
                break;
            }
            default: {
                try {
                    ubAppServerMsg.appendTlvField((short)1, this.m_requestID);
                    this.addASKRequest(ubAppServerMsg, false);
                    if (null != tlvItem) {
                        ubAppServerMsg.appendTlvField(tlvItem.getTlvType(), tlvItem.getTlvData());
                    }
                }
                catch (Exception ex) {
                    this.eof = false;
                    this.os_pos = 0;
                    this.current_state = 7;
                    this.log.logError("appendTlvField Exception: " + ex.getMessage());
                    final BrokerException ex2 = new BrokerException(7, ex.getMessage());
                    this.trace.print(ex2, 1);
                    throw ex2;
                }
                ubAppServerMsg.setubRqExt((n & 0xFF) << 8);
                break;
            }
        }
        ubAppServerMsg.setMsgbuf(array, n2);
        try {
            this.os.writeMsg(ubAppServerMsg);
            this.os.flushMsg();
            b = true;
            this.m_tsLastAccess = System.currentTimeMillis();
        }
        catch (IOException ex3) {
            this.eof = false;
            this.os_pos = 0;
            this.current_state = 7;
            this.throwCommunicationsException(8, "WRITEDATALAST IOException", ex3);
        }
        catch (NetworkProtocolException ex4) {
            this.eof = false;
            this.os_pos = 0;
            this.throwCommunicationsException(this.current_state = 7, "WRITEDATA NetworkProtocolException", ex4);
        }
        this.eof = false;
        return b;
    }
    
    private int readPacket() throws BrokerException {
        int buflen = 0;
        if (this.eof) {
            this.is_pos = 0;
            this.imsg.setBuflen(0);
            return -1;
        }
        this.imsg = null;
        while (this.imsg == null) {
            try {
                this.imsg = this.readMsg();
                if (this.imsg == null) {
                    continue;
                }
                buflen = this.imsg.getBuflen();
                continue;
            }
            catch (ubMsg.MsgFormatException ex2) {
                final MessageFormatException ex = new MessageFormatException();
                this.log.logStackTrace("", ex);
                this.log.logError(ex2.getMessage() + ":" + ex2.getDetail());
                this.trace.print(ex.toString() + ex2.getMessage() + ":" + ex2.getDetail(), 1);
                throw ex;
            }
            catch (InterruptedIOException ex8) {
                this.newAskEvent();
                if (this.current_state != 6) {
                    continue;
                }
                if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.log.logBasic(this.m_debugLogIndex, "readPacket() calling setStopPacket()");
                }
                this.setStopPacket();
                continue;
            }
            catch (IOException ex3) {
                this.throwCommunicationsException(9, "READPACKET IOException", ex3);
                continue;
            }
            catch (NetworkProtocolException ex5) {
                final BrokerException ex4 = new BrokerException(7, ex5.getMessage());
                this.log.logStackTrace("", ex4);
                this.trace.print(ex4, 1);
                throw ex4;
            }
            break;
        }
        final int getubRq = this.imsg.getubRq();
        switch (this.imsg.getubRsp()) {
            case 0: {
                break;
            }
            case 9: {
                this.stop_sent = false;
                this.current_state = 5;
                this.imsg.print("Server returned Abnormal EOF", 1, 0, this.log);
                final AbnormalEOFException ex6 = new AbnormalEOFException();
                this.log.logStackTrace("", ex6);
                this.trace.print(ex6, 1);
                throw ex6;
            }
            case 7: {
                this.stop_sent = false;
                this.current_state = 5;
                this.imsg.print("No available servers", 1, 0, this.log);
                final NoAvailableServersException ex7 = new NoAvailableServersException();
                this.log.logStackTrace("", ex7);
                this.trace.print(ex7, 1);
                throw ex7;
            }
            default: {
                this.imsg.print("READ error:  " + this.imsg.getubRsp(), 1, 0, this.log);
                this.throwBrokerException("read() error= " + this.imsg.getubRsp());
                break;
            }
        }
        this.eof = (getubRq == 13);
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "readPacket End: eof = " + this.eof + " : length = " + buflen);
        }
        this.is_pos = 0;
        if (buflen == 0 && !this.eof) {
            this.imsg.print("retlen==0 & !eof", 2, this.m_debugLogIndex, this.log);
        }
        if (this.current_state == 6) {
            if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.log.logBasic(this.m_debugLogIndex, "readPacket() calling setStopPacket() after read");
            }
            this.setStopPacket();
        }
        return (buflen == 0 && this.eof) ? -1 : buflen;
    }
    
    private void setStopPacket() throws BrokerException {
        if (this.stop_sent) {
            return;
        }
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(this.ubProtocolVersion, 40, ++this.seqnum, 0, 0);
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(7);
        try {
            if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.log.logBasic(this.m_debugLogIndex, "SETSTOP writeMsg()");
            }
            this.os.writeMsg(ubAppServerMsg);
            if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.log.logBasic(this.m_debugLogIndex, "SETSTOP flush()");
            }
            this.os.flushMsg();
            this.stop_sent = true;
            this.m_tsLastAccess = System.currentTimeMillis();
        }
        catch (IOException ex) {
            this.log.logError("SETSTOP IOException:  " + ex);
            this.trace.print("SETSTOP: IOException:  " + ex, 1);
        }
        catch (NetworkProtocolException ex3) {
            final BrokerException ex2 = new BrokerException(7, ex3.getMessage());
            this.log.logStackTrace("", ex2);
            this.trace.print(ex2, 1);
            throw ex2;
        }
    }
    
    private ubAppServerMsg readMsg() throws BrokerException, ubMsg.MsgFormatException, InterruptedIOException, IOException, NetworkProtocolException {
        ubMsg ubMsg;
        for (ubMsg = null; ubMsg == null; ubMsg = null) {
            ubMsg = this.is.readMsg();
            this.m_tsLastAccess = System.currentTimeMillis();
            this.m_ASKstate = 1;
            if (ubMsg != null && ubMsg.getubRq() == 22) {
                this.trace.print(this.m_sessionID + " detected ASKPing request", 4);
                this.askPingPacket(23);
                ubMsg = null;
            }
            if (ubMsg != null && ubMsg.getubRq() == 23) {
                this.trace.print(this.m_sessionID + " detected ASKPing reply", 4);
            }
        }
        return (ubAppServerMsg)ubMsg;
    }
    
    private void askPingPacket(final int i) throws BrokerException {
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(this.ubProtocolVersion, 70, ++this.seqnum, 0, 0);
        final String s = (i == 22) ? "UBRQ_ASKPING_RQ" : ((i == 23) ? "UBRQ_ASKPING_RSP" : ("(rqCode=" + i + ")"));
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(i);
        try {
            if (i == 23) {
                this.trace.print(this.m_sessionID + " sending ASKPing response", 4);
            }
            else {
                this.trace.print(this.m_sessionID + " sending ASKPing request", 4);
            }
            if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.log.logBasic(this.m_debugLogIndex, s + " writeMsg()");
            }
            this.os.writeMsg(ubAppServerMsg);
            if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.log.logBasic(this.m_debugLogIndex, s + " flush()");
            }
            this.os.flushMsg();
            this.m_tsLastAccess = System.currentTimeMillis();
        }
        catch (IOException obj) {
            final BrokerException ex = new BrokerException(0, obj + " [" + obj.getMessage() + "]");
            this.log.logStackTrace("", ex);
            this.trace.print(ex, 1);
            throw ex;
        }
        catch (NetworkProtocolException obj2) {
            final BrokerException ex2 = new BrokerException(7, obj2 + " [" + obj2.getMessage() + "]");
            this.log.logStackTrace("", ex2);
            this.trace.print(ex2, 1);
            throw ex2;
        }
    }
    
    private boolean checkStop() throws BrokerException {
        try {
            if (this.is.available() <= 0) {
                return false;
            }
            final ubAppServerMsg ubAppServerMsg = (ubAppServerMsg)this.is.readMsg();
            this.m_tsLastAccess = System.currentTimeMillis();
            this.m_ASKstate = 1;
            if (ubAppServerMsg == null) {
                return false;
            }
            if (ubAppServerMsg.getubRq() == 22) {
                if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.log.logBasic(this.m_debugLogIndex, "Server sent an ASKPING request");
                }
                this.trace.print(this.m_sessionID + " Server sent an ASKPING request", 1);
                this.askPingPacket(23);
                return false;
            }
            if (ubAppServerMsg.getubRq() == 23) {
                if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.log.logBasic(this.m_debugLogIndex, "Server sent an ASKPING reply");
                }
                this.trace.print(this.m_sessionID + " Server sent an ASKPing reply", 1);
                return false;
            }
            if (ubAppServerMsg.getMsgcode() == 40) {
                if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                    this.log.logBasic(this.m_debugLogIndex, "Server sent a STOP");
                }
                this.trace.print("Server sent a STOP", 1);
                return true;
            }
        }
        catch (ubMsg.MsgFormatException ex2) {
            final MessageFormatException ex = new MessageFormatException();
            this.log.logStackTrace("", ex);
            this.log.logError(ex2.getMessage() + ":" + ex2.getDetail());
            this.trace.print(ex, 1);
            throw ex;
        }
        catch (IOException ex4) {
            final BrokerSystemCommunicationsException ex3 = new BrokerSystemCommunicationsException(ex4);
            this.log.logStackTrace("", ex3);
            this.trace.print(ex3, 1);
            throw ex3;
        }
        catch (NetworkProtocolException ex6) {
            final BrokerException ex5 = new BrokerException(7, ex6.getMessage());
            this.log.logStackTrace("", ex5);
            this.trace.print(ex5, 1);
            throw ex5;
        }
        return false;
    }
    
    private boolean disconnectPacket(final String s) {
        final int n = 0;
        TlvItem tlvItem = null;
        int n2 = n + (("utf-8" == null) ? 2 : ("utf-8".length() + 3));
        n2 += 4;
        final ubAppServerMsg ubAppServerMsg = new ubAppServerMsg(this.ubProtocolVersion, 20, ++this.seqnum, n2, n2);
        if (null != this.m_actional) {
            final String header = this.m_actional.getHeader();
            if (null != header) {
                tlvItem = new TlvItem((short)13, header, false);
            }
        }
        ubAppServerMsg.setubSrc(3);
        ubAppServerMsg.setubRq(6);
        switch (this.ubProtocolVersion) {
            case 108: {
                break;
            }
            default: {
                try {
                    ubAppServerMsg.appendTlvField((short)1, s);
                    if (null != tlvItem) {
                        ubAppServerMsg.appendTlvField(tlvItem.getTlvType(), tlvItem.getTlvData());
                    }
                }
                catch (Exception ex) {
                    this.log.logError("appendTlvField Exception: " + ex.getMessage());
                    this.trace.print(new BrokerException(7, ex.getMessage()), 1);
                }
                break;
            }
        }
        final byte[] msgbuf = ubAppServerMsg.getMsgbuf();
        ubAppServerMsg.setBuflen(com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, com.progress.ubroker.util.ubAppServerMsg.setNetString(msgbuf, 0, "utf-8"), (String)null), (String)null));
        try {
            this.os.writeMsg(ubAppServerMsg);
            this.os.flushMsg();
            this.m_tsLastAccess = System.currentTimeMillis();
        }
        catch (IOException ex2) {
            this.log.logError("DISCONNECT IOException:  " + ex2);
            this.trace.print("DISCONNECT: IOException:  " + ex2, 1);
            return false;
        }
        catch (NetworkProtocolException ex3) {
            this.log.logError("DISCONNECT NetworkProtocolException:  " + ex3);
            this.trace.print("DISCONNECT: NetworkProtocolException:  " + ex3, 1);
            return false;
        }
        ubAppServerMsg msg = null;
        while (msg == null) {
            try {
                msg = this.readMsg();
                continue;
            }
            catch (ubMsg.MsgFormatException ex4) {
                this.log.logError("DISCONNECT MsgFormatException:  " + ex4.getMessage() + ":" + ex4.getDetail());
                this.trace.print("DISCONNECT: MsgFormatException:  " + ex4.getMessage() + ":" + ex4.getDetail(), 1);
                return false;
            }
            catch (InterruptedIOException obj) {
                this.log.logError("DISCONNECT InterruptedIOException:  " + obj);
                continue;
            }
            catch (IOException ex5) {
                this.log.logError("DISCONNECT IOException:  " + ex5);
                this.trace.print("DISCONNECT: IOException:  " + ex5, 1);
                return false;
            }
            catch (NetworkProtocolException ex6) {
                this.log.logError("DISCONNECT NetworkProtocolException:  " + ex6);
                this.trace.print("DISCONNECT: NetworkProtocolException:  " + ex6, 1);
                return false;
            }
            catch (BrokerException ex7) {
                continue;
            }
            break;
        }
        final boolean b = msg.getMsgcode() == 21;
        this.connID = null;
        return b;
    }
    
    private void disconnectSocket() throws BrokerException {
        try {
            if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
                this.log.logBasic(this.m_debugLogIndex, "Disconnecting from the AppServer...");
            }
            if (null != this.netProtocolHandler) {
                try {
                    if (this.os != null) {
                        this.os.close();
                        this.os = null;
                    }
                }
                catch (Exception ex5) {}
                try {
                    if (this.is != null) {
                        this.is.close();
                        this.os = null;
                    }
                }
                catch (Exception ex6) {}
                try {
                    this.netProtocolHandler.closeConnection(false);
                }
                catch (NetworkProtocolException ex2) {
                    final BrokerException ex = new BrokerException(7, ex2.getMessage());
                    this.log.logStackTrace("", ex);
                    this.trace.print(ex, 1);
                    throw ex;
                }
                catch (Exception ex4) {
                    final BrokerException ex3 = new BrokerException(7, ex4.getMessage());
                    this.log.logStackTrace("", ex3);
                    this.trace.print(ex3, 1);
                    throw ex3;
                }
            }
            this.os = null;
            this.is = null;
            this.eof = false;
            this.current_state = 0;
        }
        finally {
            this.os = null;
            this.is = null;
            this.eof = false;
            this.current_state = 0;
            this.m_requestID = null;
        }
    }
    
    private void throwBrokerException(final String s) throws BrokerException {
        final BrokerException ex = new BrokerException(s);
        this.log.logStackTrace("", ex);
        throw ex;
    }
    
    private void throwBrokerException(final int n, final String str) throws BrokerException {
        final BrokerException ex = new BrokerException(n, str + " (" + ubMsg.getubRspDesc(n) + ")");
        this.log.logStackTrace("", ex);
        throw ex;
    }
    
    private void throwCommunicationsException(final int n, final String str, final Exception obj) throws BrokerException {
        final String string = str + " : " + obj + " (" + obj.getMessage() + ")";
        final BrokerException ex = new BrokerException(n, string);
        this.log.logStackTrace(string, obj);
        this.trace.print(string, 1);
        this.trace.print(obj, 1);
        throw ex;
    }
    
    private void initializeLogging(final IAppLogger appLogger) {
        final String logContextName = appLogger.getLogContext().getLogContextName();
        if (logContextName.equals("Wsa")) {
            this.m_basicLogEntries = 8589934592L;
            this.m_basicLogIndex = 33;
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 33;
        }
        else if (logContextName.equals("O4gl")) {
            this.m_basicLogEntries = 8589934592L;
            this.m_basicLogIndex = 10;
            this.m_debugLogEntries = 8589934592L;
            this.m_debugLogIndex = 10;
        }
        else if (logContextName.equals("UBroker")) {
            this.m_basicLogEntries = 1L;
            this.m_basicLogIndex = 0;
            this.m_debugLogEntries = 2L;
            this.m_debugLogIndex = 1;
        }
        else if (logContextName.equals("Esb")) {
            this.m_basicLogEntries = 1L;
            this.m_basicLogIndex = 0;
            this.m_debugLogEntries = 4L;
            this.m_debugLogIndex = 2;
        }
        else {
            this.m_basicLogEntries = 0L;
            this.m_basicLogIndex = 0;
            this.m_debugLogEntries = 0L;
            this.m_debugLogIndex = 0;
        }
    }
    
    public long getTsLastAccess() {
        return this.m_tsLastAccess;
    }
    
    private void negotiateAskCapabilities(final ubMsg ubMsg) {
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
            negotiatedAskCaps = ((tlvField_NoThrow2 == null) ? 0 : this.parseAskCapabilities(tlvField_NoThrow2));
        }
        catch (Exception ex2) {
            negotiatedAskCaps = 0;
        }
        this.m_negotiatedAskCaps = negotiatedAskCaps;
        if (this.getClientASKEnabled()) {
            this.m_negotiatedAskCaps &= 0xFFFFFFFD;
            this.trace.print(this.m_sessionID + " ClientASK Protocol is not supported - ClientASK disabled.", 2);
        }
    }
    
    private int parseAskCapabilities(final String s) {
        int n = 1;
        if (s == null) {
            return n;
        }
        final String upperCase = s.toUpperCase();
        if (upperCase.indexOf("ALLOWSERVERASK") >= 0) {
            n |= 0x1;
        }
        if (upperCase.indexOf("ALLOWCLIENTASK") >= 0) {
            n |= 0x2;
        }
        if (upperCase.indexOf("DENYSERVERASK") >= 0) {
            n &= 0xFFFFFFFE;
        }
        if (upperCase.indexOf("DENYCLIENTASK") >= 0) {
            n &= 0xFFFFFFFD;
        }
        return n;
    }
    
    private String formatAskCapabilities(final int n) {
        return (((n & 0x1) > 0) ? "allowServerASK" : "denyServerASK") + "," + (((n & 0x2) > 0) ? "allowClientASK" : "denyClientASK");
    }
    
    private String formatAskVersion(final int n) {
        return (n >> 16 & 0xFFFF) + "." + (n & 0xFFFF);
    }
    
    private boolean askValidateProtocolType(final SocketConnectionInfoEx socketConnectionInfoEx) {
        final int protocolType = socketConnectionInfoEx.getProtocolType();
        final String protocol = socketConnectionInfoEx.getProtocol();
        boolean b = true;
        switch (protocolType) {
            case 1:
            case 4: {
                break;
            }
            default: {
                this.trace.print(this.m_sessionID + " " + new BrokerException(7, 7665970990714729922L, new Object[] { protocol }).getLocalizedMessage(), 2);
                b = false;
                break;
            }
        }
        return b;
    }
    
    private void addASKRequest(final ubAppServerMsg ubAppServerMsg, final boolean asKrqstACKsent) throws ubMsg.TlvFieldAlreadyExistsException, ubMsg.InvalidMsgVersionException, ubMsg.InvalidTlvBufferException {
        final long n = System.currentTimeMillis() - this.getTsLastAccess();
        if (this.getClientASKEnabled() && !this.m_ASKrqstACKsent && n > this.m_clientASKActivityTimeoutMs) {
            this.trace.print(this.m_sessionID + " ASKPING request added to message", 4);
            ubAppServerMsg.appendTlvField((short)12, Integer.toString(this.m_clientASKResponseTimeout));
            this.m_ASKstate = 2;
        }
        this.m_ASKrqstACKsent = asKrqstACKsent;
    }
    
    private void newAskEvent() throws BrokerException {
        if (this.getClientASKEnabled()) {
            final long n = System.currentTimeMillis() - this.getTsLastAccess();
            switch (this.m_ASKstate) {
                case 1: {
                    if (n > this.m_clientASKActivityTimeoutMs) {
                        this.processASKActivityTimeout();
                        break;
                    }
                    break;
                }
                case 2: {
                    if (n > this.m_clientASKResponseTimeoutMs) {
                        this.processASKResponseTimeout();
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private void processASKActivityTimeout() throws BrokerException {
        this.trace.print(this.m_sessionID + " ClientASK Activity Timeout has occurred - Issuing ASKPING request to the server", 4);
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "No messages have been received from the server within the clientASK Activity Timeout period ... issuing an ASKPing request to the server.");
        }
        this.m_ASKstate = 2;
        this.askPingPacket(22);
    }
    
    private void processASKResponseTimeout() throws BrokerException {
        this.unconditionalDisconnect();
        this.m_ASKstate = 1;
        this.trace.print(this.m_sessionID + " clientASK Response Timeout has occurred - disconnecting from server", 4);
        if (this.log.ifLogBasic(this.m_debugLogEntries, this.m_debugLogIndex)) {
            this.log.logBasic(this.m_debugLogIndex, "No messages were received from the server within the clientASK Response Timeout interval.  The connection  to the server has been terminated.");
        }
        this.throwBrokerException(0, "clientASK ResponseTimeout has occurred, disconnecting from server");
    }
    
    public void setSessionID(final String sessionID) {
        this.m_sessionID = sessionID;
    }
    
    public String getCapability(final short value) {
        String s = null;
        try {
            s = this.m_capabilities.get(new Short(value));
        }
        catch (Exception ex) {
            this.log.logError("BrokerSystem.getCapability() lookup failed: " + ex.getMessage());
        }
        return s;
    }
    
    private void validateConnectionInfo(final String s, final String s2, final String s3) throws NetworkProtocolException {
        int length = 0;
        if (s != null) {
            length = s.length();
        }
        if (s2 != null) {
            length += s2.length();
        }
        if (s3 != null) {
            length += s3.length();
        }
        if (length > this.conn_info_maxlength) {
            this.log.logError("NetworkProtocolException: Connect message size too long: exceeds " + this.conn_info_maxlength + " bytes.");
            throw new NetworkProtocolException("AppServer", "Connection string length " + length + " exceeds " + this.conn_info_maxlength + " bytes.");
        }
    }
    
    static {
        DESC_STATE = new String[] { " STATE_IDLE ", " STATE_CONNECTED ", " STATE_ALLOCATED ", " STATE_SENDING ", " STATE_RECEIVING ", " STATE_EOF ", " STATE_STOPPING ", " STATE_ERROR " };
        DESC_ASKSTATE = new String[] { "ASKSTATE_INIT", "ASKSTATE_ACTIVITY_TIMEOUT", "ASKSTATE_RESPONSE_TIMEOUT" };
    }
    
    public static class InvalidStateException extends BrokerException
    {
        public InvalidStateException(final String s, final String s2) {
            super(-4, 7665970990714723409L, new Object[] { s, s2 });
        }
    }
    
    public static class ClientStopException extends BrokerException
    {
        public ClientStopException() {
            super(-5, 7665970990714723412L, null);
        }
    }
    
    public static class ServerStopException extends BrokerException
    {
        public ServerStopException() {
            super(-5, 7665970990714723413L, null);
        }
    }
    
    public static class MessageFormatException extends BrokerException
    {
        public MessageFormatException() {
            super(-3, 7665970990714724568L, null);
        }
    }
    
    public static class BrokerSystemCommunicationsException extends BrokerException
    {
        public BrokerSystemCommunicationsException(final Throwable t) {
            super(-3, 7665970990714724569L, new Object[] { t.toString() });
        }
    }
    
    public static class AbnormalEOFException extends BrokerException
    {
        public AbnormalEOFException() {
            super(-2, "AppServer returned AbnormalEOF.");
        }
    }
    
    public static class NoAvailableServersException extends BrokerException
    {
        public NoAvailableServersException() {
            super(-7, "No servers available to process request");
        }
    }
}

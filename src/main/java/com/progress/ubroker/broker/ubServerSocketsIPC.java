// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import java.net.InetAddress;
import java.net.Inet6Address;
import com.progress.ubroker.ssl.ClientParams;
import java.net.UnknownHostException;
import java.net.SocketException;
import com.progress.ubroker.util.ISSLParams;
import com.progress.ubroker.ssl.SSLSocketUtilsFull;
import java.io.InterruptedIOException;
import com.progress.ubroker.util.ubMsg;
import java.io.IOException;
import com.progress.ubroker.util.ubProperties;
import com.progress.common.ehnlog.IAppLogger;
import com.progress.ubroker.util.MsgInputStream;
import com.progress.ubroker.util.MsgOutputStream;
import java.net.Socket;
import com.progress.ubroker.util.ubConstants;

class ubServerSocketsIPC implements ubConstants, IServerIPC
{
    public static final int DEF_RCVTIMEOUT = 0;
    public static final String LOCALHOST_IPV6 = "::1";
    public static final String LOCALHOST_IPV4 = "127.0.0.1";
    Socket serverSocket;
    int rcvtimeout;
    MsgOutputStream os;
    MsgInputStream is;
    IAppLogger log;
    int log_dest;
    int serverType;
    String socketDesc;
    private ubProperties properties;
    
    public ubServerSocketsIPC(final ubProperties properties, final IAppLogger log, final int log_dest) {
        this.properties = properties;
        this.serverType = properties.serverType;
        this.log = log;
        this.log_dest = log_dest;
        this.serverSocket = null;
        this.socketDesc = null;
        this.rcvtimeout = 0;
        this.os = null;
        this.is = null;
    }
    
    public ubServerSocketsIPC(final Socket serverSocket, final ubProperties ubProperties, final IAppLogger appLogger, final int n) {
        this(ubProperties, appLogger, n);
        this.serverSocket = serverSocket;
    }
    
    public void create(final int i) throws ServerIPCException {
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "ServerSocketIPC create: portnum= " + i);
        }
        this.connectServerSocket(i, this.rcvtimeout = 0);
    }
    
    public void create(final int i, final int n) throws ServerIPCException {
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "ServerSocketIPC create: portnum= " + i + " rcvtimeout= " + n);
        }
        this.connectServerSocket(i, this.rcvtimeout = n);
    }
    
    public void delete() throws ServerIPCException {
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "ServerSocketIPC delete");
        }
        try {
            this.is.close();
            this.os.close();
            this.serverSocket = null;
        }
        catch (IOException ex) {
            this.log.logError(7665689515738013631L, new Object[] { "delete()", ex.toString(), ex.getMessage() });
            throw new ServerIPCException("ubServerSocketsIPC.delete() IOException: " + ex.getMessage());
        }
        this.socketDesc = null;
    }
    
    public void write(final ubMsg ubMsg, final boolean b) throws ServerIPCException {
        try {
            this.os.writeMsg(ubMsg);
            if (b) {
                this.os.flush();
            }
        }
        catch (IOException ex) {
            this.log.logError(7665689515738013631L, new Object[] { "write()", ex.toString(), ex.getMessage() });
            throw new ServerIPCException("ubServerSocketsIPC.write() IOException: " + ex.getMessage());
        }
    }
    
    public void writeLast(final ubMsg ubMsg, final boolean b) throws ServerIPCException {
        this.write(ubMsg, b);
    }
    
    public ubMsg read() throws ServerIPCException {
        ubMsg msg;
        try {
            msg = this.is.readMsg();
        }
        catch (InterruptedIOException ex3) {
            return null;
        }
        catch (ubMsg.MsgFormatException ex) {
            this.log.logError(7665689515738013632L, new Object[] { ex.toString(), ex.getMessage() + " : " + ex.getDetail() });
            throw new ServerIPCException("ubServerSocketsIPC.read() MsgFormatException: " + ex.getMessage() + " : " + ex.getDetail());
        }
        catch (IOException ex2) {
            this.log.logError(7665689515738013631L, new Object[] { "read()", ex2.toString(), ex2.getMessage() });
            throw new ServerIPCException("ubServerSocketsIPC.read() IOException: " + ex2.getMessage());
        }
        return msg;
    }
    
    public int available() throws ServerIPCException {
        int available;
        try {
            available = this.is.available();
        }
        catch (IOException ex) {
            this.log.logError(7665689515738013631L, new Object[] { "available()", ex.toString(), ex.getMessage() });
            throw new ServerIPCException("ubServerSocketsIPC.available() IOException: " + ex.getMessage());
        }
        return available;
    }
    
    public String toString() {
        return this.socketDesc;
    }
    
    private void connectServerSocket(final int n, final int n2) throws ServerIPCException {
        String[] array;
        if (this.properties.ipver == 0) {
            array = new String[] { "localhost" };
        }
        else {
            array = new String[] { this.properties.localHost, "::1", "127.0.0.1" };
        }
        int i = 0;
        while (i < array.length) {
            try {
                this.connectSocket(array[i], n, n2);
            }
            catch (ServerIPCException ex) {
                if (++i == array.length) {
                    throw ex;
                }
                continue;
            }
            break;
        }
    }
    
    private void connectSocket(final String s, final int n, final int soTimeout) throws ServerIPCException {
        String str = "";
        try {
            if (this.serverSocket == null) {
                if (this.log.ifLogVerbose(65536L, 16)) {
                    this.log.logExtended(16, "connectSocket(" + s + ", " + n + ")");
                }
                this.serverSocket = new Socket(s, n);
            }
            this.socketDesc = this.formatSocketDesc(this.serverSocket);
            if (this.log.ifLogBasic(65536L, 16)) {
                this.log.logBasic(16, "connected to agent at " + this.socketDesc);
            }
            final ClientParams sslClientParams = this.properties.getSSLClientParams();
            if (sslClientParams != null) {
                this.serverSocket = new SSLSocketUtilsFull().createSSLSocket(this.serverSocket, sslClientParams);
            }
            if (soTimeout > 0) {
                str = "setSoTimeout()";
                this.serverSocket.setSoTimeout(soTimeout);
            }
            str = "setKeepAlive()";
            this.serverSocket.setKeepAlive(true);
            str = "";
            this.os = new MsgOutputStream(this.serverSocket.getOutputStream(), 1024, this.log, 2, 32L, 5);
            this.is = new MsgInputStream(this.serverSocket.getInputStream(), 10240, this.serverType, this.log, 2, 32L, 5);
        }
        catch (SocketException ex) {
            this.socketDesc = null;
            this.log.logError(7665689515738013631L, new Object[] { str, ex.toString(), ex.getMessage() });
            throw new ServerIPCException("ubServerSocketsIPC.create() " + str + " error: " + s + ex.getMessage());
        }
        catch (UnknownHostException ex2) {
            this.log.logError(7665689515738013631L, new Object[] { s });
            throw new ServerIPCException("ubServerSocketsIPC.create() unknown host: " + s + " " + ex2.getMessage());
        }
        catch (IOException ex3) {
            this.log.logError(7665689515738013631L, new Object[] { s, ex3.toString(), ex3.getMessage() });
            throw new ServerIPCException("ubServerSocketsIPC.create() IOException: " + ex3.getMessage());
        }
    }
    
    private String formatSocketDesc(final Socket socket) {
        final InetAddress inetAddress = socket.getInetAddress();
        final String hostAddress = inetAddress.getHostAddress();
        String s;
        if (inetAddress instanceof Inet6Address) {
            s = "[" + hostAddress + "]" + ":" + socket.getPort();
        }
        else {
            s = hostAddress + ":" + socket.getPort();
        }
        return s;
    }
}

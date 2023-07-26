// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.broker;

import com.progress.ubroker.ssl.ClientParams;
import com.progress.ubroker.ssl.InvalidCertificateException;
import com.progress.ubroker.ssl.InvalidKeyException;
import com.progress.ubroker.ssl.KeyException;
import java.util.Properties;
import com.progress.ubroker.ssl.ServerParams;
import com.progress.ubroker.util.ISSLParams;
import com.progress.ubroker.ssl.ServerProperties;
import com.progress.ubroker.ssl.SSLSocketUtilsFull;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import com.progress.common.ehnlog.ExtendedLogStream;
import java.io.OutputStream;
import com.progress.ubroker.util.ubProperties;
import com.progress.common.ehnlog.IAppLogger;
import java.net.ServerSocket;
import com.progress.ubroker.util.ubConstants;

class ubListenerPort implements ubConstants
{
    static final byte STATE_READY = 0;
    static final byte STATE_LISTENING = 1;
    static final byte STATE_CLOSING = 2;
    static final byte STATE_CLOSED = 3;
    static final String[] DESC_STATE;
    static final String[] DESC_STATE_EXT;
    static final int LISTEN_PORT_QUEUE_SIZE = 1024;
    static final int LISTEN_RETRY_COUNT = 3;
    int current_state;
    boolean shutdownRequested;
    int port;
    int msTimeout;
    ServerSocket listenerSocket;
    IAppLogger log;
    ubProperties properties;
    private OutputStream debugStream;
    
    ubListenerPort(final ubProperties properties, final IAppLogger log) throws IOException, SocketException {
        this.properties = properties;
        this.port = properties.portNum;
        this.msTimeout = properties.getValueAsInt("soTimeout");
        this.log = log;
        this.debugStream = new ExtendedLogStream(log, 8192L, 13).getPrintStream();
        this.listenerSocket = this.getListenerSocket();
        this.initSSLClientParams(properties);
        this.current_state = 0;
        this.shutdownRequested = false;
    }
    
    public synchronized Socket listen() {
        Socket accept = null;
        int n = 3;
        while (this.current_state == 1) {
            try {
                this.wait();
            }
            catch (InterruptedException ex4) {
                if (!this.log.ifLogBasic(2L, 1)) {
                    continue;
                }
                this.log.logBasic(1, "listen() wait interrupted");
            }
        }
        if (this.current_state == 0) {
            if (this.log.ifLogBasic(2L, 1)) {
                this.log.logBasic(1, "Listening::");
            }
            this.current_state = 1;
            while (this.current_state == 1) {
                try {
                    accept = this.listenerSocket.accept();
                    if (this.log.ifLogVerbose(1L, 0)) {
                        this.log.logVerbose(0, 7665689515738013629L, new Object[0]);
                    }
                    this.current_state = 0;
                }
                catch (InterruptedIOException ex) {
                    this.log.logError(7665689515738013550L, new Object[] { new Integer(this.port), ex.toString() + " : " + ex.getMessage() });
                    if (!this.shutdownRequested) {
                        continue;
                    }
                    this.current_state = 2;
                    this.close();
                }
                catch (SocketException ex2) {
                    if (n > 0) {
                        --n;
                        this.log.logError(7665689515738013550L, new Object[] { new Integer(this.port), ex2.toString() + " : " + ex2.getMessage() });
                        if (!this.shutdownRequested) {
                            continue;
                        }
                        this.current_state = 2;
                        this.close();
                    }
                    else {
                        this.log.logError(7665689515738013551L, new Object[] { new Integer(this.port), ex2.toString() });
                        this.current_state = 2;
                        this.close();
                        accept = null;
                    }
                }
                catch (IOException ex3) {
                    this.log.logError(7665689515738013551L, new Object[] { new Integer(this.port), ex3.toString() });
                    this.current_state = 2;
                    this.close();
                    accept = null;
                }
            }
        }
        else {
            accept = null;
        }
        this.notifyAll();
        return accept;
    }
    
    public void close() {
        this.shutdownRequested = true;
        synchronized (this) {
            while (this.current_state == 1) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex2) {}
            }
            this.current_state = 2;
            try {
                this.listenerSocket.close();
            }
            catch (IOException ex) {
                this.log.logError(7665689515738013630L, new Object[] { ex.toString(), ex.getMessage() });
            }
            if (this.log.ifLogBasic(2L, 1)) {
                this.log.logBasic(1, Integer.toString(this.port) + " socket closed");
            }
            this.current_state = 3;
            this.notifyAll();
        }
    }
    
    public int getLocalPort() throws IOException {
        return this.listenerSocket.getLocalPort();
    }
    
    private ServerSocket getListenerSocket() throws IOException {
        final ServerSocket serverSocket = this.properties.getValueAsBoolean("sslEnable") ? this.getSSLServerSocket() : this.getServerSocket();
        if (this.msTimeout > 0) {
            serverSocket.setSoTimeout(this.msTimeout);
        }
        return serverSocket;
    }
    
    private ServerSocket getServerSocket() throws IOException {
        return new ServerSocket(this.port, 1024);
    }
    
    private ServerSocket getSSLServerSocket() throws IOException {
        final SSLSocketUtilsFull sslSocketUtilsFull = new SSLSocketUtilsFull();
        final ServerProperties serverProperties = new ServerProperties(this.properties);
        if (this.log.ifLogBasic(1L, 0)) {
            this.log.logBasic(0, 8318992936683450196L, null);
        }
        final ServerParams sslServerParams = this.getSSLServerParams(serverProperties);
        if (this.log.ifLogBasic(2L, 1)) {
            this.log.logBasic(1, "Creating SSL socket::");
        }
        return sslSocketUtilsFull.createSSLServerSocket(this.port, 1024, sslServerParams);
    }
    
    private ServerParams getSSLServerParams(final ServerProperties serverProperties) throws IOException {
        final ServerParams serverParams = new ServerParams();
        try {
            serverParams.init(serverProperties, this.debugStream, 6);
        }
        catch (KeyException ex) {
            this.log.logError(8318992936683450145L, new Object[] { new Integer(0), ex.getMessage() });
            throw ex;
        }
        catch (InvalidKeyException ex2) {
            this.log.logError(8318992936683450146L, new Object[] { new Integer(0), "(initialization)" });
            throw ex2;
        }
        catch (InvalidCertificateException ex3) {
            this.log.logError(8318992936683450273L, new Object[] { "(" + ex3.getMessage() + ")" });
            throw ex3;
        }
        catch (IOException ex4) {
            this.log.logError(8318992936683450143L, new Object[] { ex4.toString() });
            throw ex4;
        }
        return serverParams;
    }
    
    private void initSSLClientParams(final ubProperties ubProperties) throws IOException {
        if (!ubProperties.getValueAsBoolean("sslEnable")) {
            return;
        }
        if (ubProperties.serverMode == 3) {
            return;
        }
        if (ubProperties.serverMode == 0) {
            return;
        }
        if (ubProperties.serverType == 1) {
            return;
        }
        final ClientParams sslClientParams = this.getSSLClientParams(ubProperties);
        sslClientParams.setReusingSessions(false);
        ubProperties.setSSLClientParams(sslClientParams);
    }
    
    private ClientParams getSSLClientParams(final ubProperties ubProperties) throws IOException {
        final ClientParams clientParams = new ClientParams();
        try {
            clientParams.init(ubProperties, this.debugStream, 6);
        }
        catch (InvalidCertificateException ex) {
            this.log.logError(8318992936683450152L, new Object[] { "(" + ex.getMessage() + ")" });
            throw ex;
        }
        catch (IOException ex2) {
            this.log.logError(8318992936683450151L, new Object[] { ex2.toString() });
            throw ex2;
        }
        return clientParams;
    }
    
    static {
        DESC_STATE = new String[] { " STATE_READY ", " STATE_LISTENING ", " STATE_CLOSING ", " STATE_CLOSED " };
        DESC_STATE_EXT = new String[] { " READY ", " LISTENING ", " CLOSING ", " CLOSED " };
    }
}

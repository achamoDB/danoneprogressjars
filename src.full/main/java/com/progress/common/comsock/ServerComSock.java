// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsock;

import java.net.Socket;
import java.io.InterruptedIOException;
import com.progress.common.log.Excp;
import java.io.IOException;
import java.util.Vector;
import java.net.ServerSocket;
import java.net.InetAddress;

public class ServerComSock extends Thread
{
    boolean timeToGo;
    private int port;
    private InetAddress inetAddr;
    private ServerSocket serverSocket;
    protected Vector clients;
    protected Vector listeners;
    
    void stopThread() {
        this.timeToGo = true;
    }
    
    public ServerComSock(final int n) {
        this(n, null);
    }
    
    public ServerComSock(final int n, final InetAddress inetAddr) {
        this.timeToGo = false;
        this.clients = new Vector();
        this.listeners = new Vector();
        this.port = n;
        this.inetAddr = inetAddr;
        this.setName("Server Socket Utility" + n);
    }
    
    public void connect() throws IOException {
        if (this.inetAddr != null) {
            this.serverSocket = new ServerSocket(this.port, 0, this.inetAddr);
        }
        else {
            this.serverSocket = new ServerSocket(this.port);
        }
        this.serverSocket.setSoTimeout(2000);
        this.start();
    }
    
    protected void clientShutdown(final Integer n) {
        final boolean b = n == null;
        final int n2 = b ? 0 : n;
        for (int size = this.clients.size(), i = 0; i < size; ++i) {
            final Client client = this.clients.elementAt(i);
            if (b || client.id == n2) {
                client.stopThread();
                try {
                    client.close();
                }
                catch (IOException ex) {
                    Excp.print(ex);
                }
            }
        }
    }
    
    protected void shutDownAClient(final int value) {
        this.clientShutdown(new Integer(value));
    }
    
    protected void shutDownAllClients() {
        this.clientShutdown(null);
    }
    
    protected void shutdownServer() {
        this.stopThread();
    }
    
    public void disconnect() {
        synchronized (this.clients) {
            this.shutDownAllClients();
            this.shutdownServer();
            this.clients.removeAllElements();
        }
    }
    
    public void disconnect(final int n) {
        this.shutDownAClient(n);
    }
    
    public void send(final int i, final ComMsg comMsg) throws IOException {
        synchronized (this.clients) {
            for (int size = this.clients.size(), j = 0; j < size; ++j) {
                final Client client = this.clients.elementAt(j);
                if (client.id == i) {
                    comMsg.write(client.outStream);
                    return;
                }
            }
        }
        throw new IOException("Client ID: " + i + " not found");
    }
    
    public void addEventListener(final ServerComSockListener serverComSockListener) {
        if (!this.listeners.contains(serverComSockListener)) {
            this.listeners.addElement(serverComSockListener);
        }
    }
    
    public void run() {
        while (true) {
            try {
                do {
                    final Socket accept = this.serverSocket.accept();
                    if (this.timeToGo) {
                        break;
                    }
                    final Client obj = new Client(this, accept);
                    synchronized (this.clients) {
                        this.clients.addElement(obj);
                    }
                    for (int size = this.listeners.size(), i = 0; i < size; ++i) {
                        ((ServerComSockListener)this.listeners.elementAt(i)).clientConnect(obj.id);
                    }
                    obj.start();
                } while (!this.timeToGo);
            }
            catch (InterruptedIOException ex) {
                if (!this.timeToGo) {
                    continue;
                }
            }
            catch (IOException ex2) {
                continue;
            }
            break;
        }
        try {
            this.serverSocket.close();
        }
        catch (IOException ex3) {}
    }
}

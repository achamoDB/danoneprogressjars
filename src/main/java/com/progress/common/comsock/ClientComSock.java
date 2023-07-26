// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsock;

import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.util.Vector;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;

public class ClientComSock extends Thread
{
    private String address;
    private int port;
    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private boolean timeToGo;
    protected Vector listeners;
    
    public ClientComSock(final String address, final int port) {
        this.timeToGo = false;
        this.listeners = null;
        this.address = address;
        this.port = port;
        this.setName("Client Socket Utility");
    }
    
    public void connect() throws IOException {
        (this.socket = new Socket(this.address, this.port)).setSoTimeout(2000);
        this.inStream = new DataInputStream(this.socket.getInputStream());
        this.outStream = new DataOutputStream(this.socket.getOutputStream());
        this.start();
    }
    
    public void disconnect() {
        this.stopThread();
    }
    
    public void send(final ComMsg comMsg) throws IOException {
        comMsg.write(this.outStream);
    }
    
    public void addEventListener(final ClientComSockListener clientComSockListener) {
        if (this.listeners == null) {
            this.listeners = new Vector();
        }
        if (!this.listeners.contains(clientComSockListener)) {
            this.listeners.addElement(clientComSockListener);
        }
    }
    
    void stopThread() {
        this.timeToGo = true;
    }
    
    public void run() {
        while (true) {
            try {
                final ComMsg comMsg = new ComMsg(this.inStream);
                if (this.timeToGo) {
                    return;
                }
                if (this.listeners == null) {
                    continue;
                }
                for (int size = this.listeners.size(), i = 0; i < size; ++i) {
                    ((ClientComSockListener)this.listeners.elementAt(i)).messageReceived(comMsg);
                }
            }
            catch (InterruptedIOException ex) {
                if (this.timeToGo) {
                    return;
                }
                continue;
            }
            catch (IOException ex2) {
                System.out.println(((ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.CMNMsgBundle")).getTranString("Error reading message from server."));
            }
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsock;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.io.IOException;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;

class Client extends Thread
{
    private static int idCounter;
    protected int id;
    protected DataInputStream inStream;
    protected DataOutputStream outStream;
    private Socket socket;
    private ServerComSock scs;
    private boolean stopRequested;
    
    protected Client(final ServerComSock scs, final Socket socket) throws IOException {
        this.stopRequested = false;
        this.scs = scs;
        this.id = Client.idCounter++;
        this.socket = socket;
        this.inStream = new DataInputStream(socket.getInputStream());
        this.outStream = new DataOutputStream(socket.getOutputStream());
        this.setName("Socket client");
    }
    
    protected void close() throws IOException {
        this.inStream.close();
        this.outStream.flush();
        this.outStream.close();
        this.socket.close();
    }
    
    void stopThread() {
        this.stopRequested = true;
    }
    
    public void run() {
        while (true) {
            try {
                this.socket.setSoTimeout(2000);
                break Label_0014;
            }
            catch (SocketException ex) {}
            try {
                while (true) {
                    final ComMsg comMsg = new ComMsg(this.inStream);
                    for (int size = this.scs.listeners.size(), i = 0; i < size; ++i) {
                        ((ServerComSockListener)this.scs.listeners.elementAt(i)).messageReceived(this.id, comMsg);
                    }
                }
            }
            catch (InterruptedIOException ex2) {
                if (this.stopRequested) {
                    return;
                }
                continue;
            }
            catch (IOException ex3) {
                if (this.stopRequested) {
                    return;
                }
                for (int size2 = this.scs.listeners.size(), j = 0; j < size2; ++j) {
                    ((ServerComSockListener)this.scs.listeners.elementAt(j)).clientDisconnect(this.id);
                }
                synchronized (this.scs.clients) {
                    this.scs.clients.removeElement(this);
                }
                try {
                    this.close();
                }
                catch (IOException ex4) {}
            }
            break;
        }
    }
    
    static {
        Client.idCounter = 0;
    }
}

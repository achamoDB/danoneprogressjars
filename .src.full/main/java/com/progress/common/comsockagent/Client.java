// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.comsockagent;

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
    private ServerComSockAgent scs;
    private boolean stopRequested;
    private boolean initialConnect;
    
    protected Client(final ServerComSockAgent scs, final Socket socket) throws IOException {
        this.stopRequested = false;
        this.initialConnect = true;
        this.scs = scs;
        this.id = Client.idCounter++;
        this.socket = socket;
        this.inStream = new DataInputStream(socket.getInputStream());
        this.outStream = new DataOutputStream(socket.getOutputStream());
        this.setName("SocketAgent client" + this.id);
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
                ComMsgAgent comMsgAgent;
                while (true) {
                    comMsgAgent = new ComMsgAgent(this.inStream);
                    if (comMsgAgent.getMsgType() == ComMsgAgent.AMPROTO) {
                        if (!comMsgAgent.processAMMesg(this.inStream, this.outStream)) {
                            break;
                        }
                        continue;
                    }
                    else {
                        for (int size = this.scs.listeners.size(), i = 0; i < size; ++i) {
                            ((ServerComSockAgentListener)this.scs.listeners.elementAt(i)).messageReceived(this.id, comMsgAgent);
                        }
                    }
                }
                if (comMsgAgent.getMsgCode() == ComMsgAgent.CONN_REQ) {
                    try {
                        this.close();
                    }
                    catch (IOException ex2) {}
                    return;
                }
                throw new IOException();
            }
            catch (InterruptedIOException ex3) {
                if (this.stopRequested) {
                    return;
                }
                continue;
            }
            catch (IOException ex4) {
                for (int size2 = this.scs.listeners.size(), j = 0; j < size2; ++j) {
                    ((ServerComSockAgentListener)this.scs.listeners.elementAt(j)).clientDisconnect(this.id);
                }
                synchronized (this.scs.clients) {
                    this.scs.clients.removeElement(this);
                }
                try {
                    this.close();
                }
                catch (IOException ex5) {}
            }
            break;
        }
    }
    
    static {
        Client.idCounter = 0;
    }
}

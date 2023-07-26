// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

public class GetInfoThread extends Thread
{
    Sockets recvSocket;
    
    public GetInfoThread(final Sockets recvSocket) {
        this.recvSocket = recvSocket;
    }
    
    public void run() {
        try {
            this.recvSocket.getMessages();
        }
        catch (Exception ex) {}
    }
}

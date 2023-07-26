// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

import java.rmi.RemoteException;

public class PingIt extends Thread
{
    IPingable pingable;
    LocalBoolean sync;
    
    public static boolean isActive(final IPingable pingable) {
        return isActive(pingable, 5000);
    }
    
    public static boolean isActive(final IPingable pingable, final int n) {
        final LocalBoolean localBoolean = new LocalBoolean(false);
        try {
            synchronized (localBoolean) {
                new PingIt(pingable, localBoolean).start();
                localBoolean.wait(n);
                if (localBoolean.killed) {
                    throw new ThreadDeath();
                }
            }
        }
        catch (InterruptedException ex) {}
        return localBoolean.get();
    }
    
    void destroyThread() {
        synchronized (this.sync) {
            this.sync.killed = true;
            this.sync.notifyAll();
        }
    }
    
    private PingIt(final IPingable pingable, final LocalBoolean sync) {
        this.pingable = null;
        this.sync = null;
        this.pingable = pingable;
        this.sync = sync;
        this.setName("Pinger (PingIt)");
    }
    
    public void run() {
        try {
            this.pingable.ping();
            synchronized (this.sync) {
                this.sync.set(true);
                this.sync.notifyAll();
            }
        }
        catch (RemoteException ex) {
            synchronized (this.sync) {
                this.sync.notifyAll();
            }
        }
    }
}

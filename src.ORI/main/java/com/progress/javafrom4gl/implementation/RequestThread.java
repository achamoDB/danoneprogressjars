// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

class RequestThread extends Thread
{
    RequestExecuter executer;
    boolean ready;
    boolean stopped;
    
    RequestThread() {
        this.ready = false;
        this.stopped = false;
        this.start();
    }
    
    synchronized void doStop() throws Exception {
        this.stopped = true;
        this.notifyAll();
    }
    
    synchronized void init(final RequestExecuter executer) throws Exception {
        while (!this.ready) {
            this.wait();
        }
        this.executer = executer;
        this.notify();
    }
    
    public void run() {
        try {
            this.go();
        }
        catch (Exception ex) {
            throw new Error(ex.toString());
        }
    }
    
    synchronized void go() throws Exception {
        while (!this.stopped) {
            this.ready = true;
            this.notify();
            this.wait();
            if (this.stopped) {
                this.executer = null;
                return;
            }
            this.executer.executeRequest();
            this.executer = null;
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.RemoteException;
import java.io.PrintStream;
import com.progress.common.rmiregistry.ICallable;

class PrimaryStartupMonitorCallback implements ICallable
{
    JAConfiguration config;
    static int extent;
    
    PrimaryStartupMonitorCallback(final JAConfiguration config) {
        this.config = null;
        this.config = config;
        ++PrimaryStartupMonitorCallback.extent;
    }
    
    protected void finalize() {
        --PrimaryStartupMonitorCallback.extent;
    }
    
    public static void printInstanceCount(final PrintStream printStream) {
        printStream.println("There are " + PrimaryStartupMonitorCallback.extent + " instances of PrimaryStartupMonitorCallback");
    }
    
    public Object call(final Object o) throws RemoteException {
        while (this.config.state.get() == o) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException ex) {}
        }
        return this.config.state;
    }
    
    static {
        PrimaryStartupMonitorCallback.extent = 0;
    }
}

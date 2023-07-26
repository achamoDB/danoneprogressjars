// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.log.Excp;
import com.progress.common.networkevents.IEventObject;
import com.progress.international.resources.ProgressResources;
import java.rmi.RemoteException;
import com.progress.common.networkevents.EventObject;
import java.io.PrintStream;
import java.io.BufferedReader;

abstract class ProcessReader extends Thread
{
    BufferedReader fromJuniper;
    JAConfiguration config;
    static int extent;
    Process process;
    
    public ProcessReader(final JAConfiguration config, final BufferedReader fromJuniper, final Process process) {
        this.config = null;
        this.fromJuniper = fromJuniper;
        this.process = process;
        this.config = config;
        ++ProcessReader.extent;
    }
    
    protected void finalize() {
        --ProcessReader.extent;
    }
    
    public static void printInstanceCount(final PrintStream printStream) {
        printStream.println("There are " + ProcessReader.extent + " instances of ProcessReader");
    }
    
    abstract String descr();
    
    abstract EventObject[] getEvents(final String p0) throws RemoteException;
    
    void handleMessage(final String s) {
    }
    
    void handleError(final String s) {
    }
    
    public void run() {
        this.setName("Process Reader/" + this.descr());
        this.config.iT(this.getName());
        String string = null;
        try {
            String line;
            while ((line = this.fromJuniper.readLine()) != null) {
                if (line.indexOf("**") > 0 || line.indexOf("1043") > 0 || line.indexOf("1038") > 0 || line.indexOf("912") > 0 || line.toLowerCase().indexOf("error") > 0) {
                    final JAConfiguration config = this.config;
                    JAConfiguration.getLog().logErr(ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "ConfigErrorFrom", new Object[] { this.config.name(), this.descr(), line }));
                }
                else {
                    final JAConfiguration config2 = this.config;
                    JAConfiguration.getLog().log(2, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "ConfigMessageFrom", new Object[] { this.config.name(), this.descr(), line }));
                    this.handleMessage(line);
                }
                if (string == null) {
                    string = line;
                }
                else {
                    string = line + "\n" + string;
                }
            }
            int exitValue = 0;
            if (this.process != null) {
                try {
                    this.process.waitFor();
                    exitValue = this.process.exitValue();
                }
                catch (InterruptedException ex2) {}
                catch (IllegalThreadStateException ex3) {}
            }
            if (exitValue != 0) {
                if (string == null) {
                    string = new String(" ");
                }
                final EventObject[] events = this.getEvents(string);
                if (events != null) {
                    for (int i = 0; i < events.length; ++i) {
                        this.config.plugIn.getEventBroker().postEvent(events[i]);
                    }
                }
                this.handleError(string);
            }
            final JAConfiguration config3 = this.config;
            JAConfiguration.getLog().log(4, ProgressResources.retrieveTranString("com.progress.international.messages.ADMMsgBundle", "Exiting process reader"));
            this.fromJuniper.close();
        }
        catch (Exception ex) {
            Excp.print(ex, "ProcessReader Exception" + ex.getMessage());
        }
        this.config.dT(this.getName());
    }
    
    static {
        ProcessReader.extent = 0;
    }
}

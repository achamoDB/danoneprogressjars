// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import java.io.InputStream;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import java.io.IOException;
import java.rmi.RemoteException;
import com.progress.common.log.ProLog;
import com.progress.common.networkevents.IEventListener;
import java.rmi.server.RemoteStub;

public class JAAgentConnect implements Runnable
{
    JAPlugIn m_jaPlugIn;
    JAAgent m_jaAgent;
    String m_agentDisplayName;
    String[] m_command;
    boolean m_isInitializing;
    
    public JAAgentConnect(final JAAgent jaAgent, final String[] command) {
        this.m_jaPlugIn = JAPlugIn.get();
        this.m_jaAgent = null;
        this.m_agentDisplayName = "";
        this.m_command = null;
        this.m_isInitializing = false;
        this.m_command = command;
        this.m_jaAgent = jaAgent;
        this.m_agentDisplayName = this.m_jaAgent.getDisplayName(false);
    }
    
    public void run() {
        try {
            final IEventBroker eventBroker = this.m_jaPlugIn.getEventBroker();
            final IEventStream eventStream = this.m_jaPlugIn.getEventStream();
            final RemoteAgentStateChangedListener remoteAgentStateChangedListener = new RemoteAgentStateChangedListener(this.m_jaAgent);
            synchronized (remoteAgentStateChangedListener) {
                final IEventInterestObject expressInterest = eventBroker.expressInterest(EARStateChanged.class, remoteAgentStateChangedListener, (RemoteStub)this.m_jaAgent.evThis(), eventStream);
                final Process exec = Runtime.getRuntime().exec(this.m_command);
                final int n = (exec == null) ? -1 : exec.waitFor();
                String string = "";
                String string2 = "";
                if (n != 0) {
                    final InputStream inputStream = exec.getInputStream();
                    final InputStream errorStream = exec.getErrorStream();
                    int read;
                    while ((read = inputStream.read()) != -1) {
                        string += (char)read;
                    }
                    int read2;
                    while ((read2 = errorStream.read()) != -1) {
                        string2 += (char)read2;
                    }
                    final String string3 = string + string2;
                    ProLog.logdErr("DatabaseAgent", "Database agent " + this.m_agentDisplayName + " not started.\n" + string3);
                    expressInterest.revokeInterest();
                    this.m_jaAgent.handleCrash(string3);
                }
                else {
                    for (int n2 = 40; !this.m_jaAgent.isRunning() && !this.m_jaAgent.isIdle() && n2 > 0; --n2) {
                        this.m_jaAgent.sendRunningQuery();
                        try {
                            remoteAgentStateChangedListener.wait(3000L);
                        }
                        catch (InterruptedException ex2) {
                            ProLog.logd("Fathom", "Interruption received while waiting for database agent to start: " + this.m_agentDisplayName);
                        }
                    }
                    expressInterest.revokeInterest();
                    if (!remoteAgentStateChangedListener.isInitializing()) {
                        final String string4 = "Database agent start up timed out: " + this.m_agentDisplayName;
                        ProLog.logdErr("DatabaseAgent", string4);
                        this.m_jaAgent.handleCrash(string4);
                    }
                }
            }
        }
        catch (InterruptedException ex3) {
            ProLog.logd("DatabaseAgent", "Database agent interrupted: " + this.m_agentDisplayName);
        }
        catch (RemoteException ex4) {
            ProLog.logd("DatabaseAgent", "Unable to obtain event broker for database agent: " + this.m_agentDisplayName);
        }
        catch (IOException ex) {
            ProLog.logX(ex, "Unable to start database agent process: " + this.m_agentDisplayName);
        }
    }
    
    class RemoteAgentStateChangedListener extends EventListener
    {
        boolean m_isInitializing;
        
        public RemoteAgentStateChangedListener(final JAAgent jaAgent) {
            this.m_isInitializing = false;
            JAAgentConnect.this.m_jaAgent = jaAgent;
        }
        
        public synchronized void processEvent(final IEventObject eventObject) {
            try {
                final EARStateChanged earStateChanged = (EARStateChanged)eventObject;
                earStateChanged.agent().getDisplayName();
                final JAStatusObject status = earStateChanged.getStatus();
                if (status.isInitializing() || status.isRunning()) {
                    this.m_isInitializing = true;
                    this.notify();
                }
                else if (status.isIdle()) {
                    this.notify();
                }
            }
            catch (RemoteException ex) {
                ProLog.logX(ex);
            }
        }
        
        public boolean isInitializing() {
            return this.m_isInitializing;
        }
    }
}

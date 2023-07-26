// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.agent.database;

import com.progress.juniper.admin.JAStatusObject;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import java.io.InputStream;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.IEventBroker;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.StringTokenizer;
import com.progress.juniper.admin.JAPlugIn;
import com.progress.juniper.admin.JuniperProperties;
import com.progress.common.log.ProLog;
import com.progress.common.networkevents.IEventListener;
import java.rmi.server.RemoteStub;

public class AgentConnect implements Runnable
{
    AgentPlugIn m_agentPlugIn;
    AgentDatabase m_agentDatabase;
    String m_agentDisplayName;
    String[] m_command;
    boolean m_isInitializing;
    
    public AgentConnect(final AgentDatabase agentDatabase, final String[] command) {
        this.m_agentPlugIn = AgentPlugIn.get();
        this.m_agentDatabase = null;
        this.m_agentDisplayName = "";
        this.m_command = null;
        this.m_isInitializing = false;
        this.m_command = command;
        this.m_agentDatabase = agentDatabase;
        this.m_agentDisplayName = this.m_agentDatabase.getDisplayName(false);
    }
    
    public void run() {
        try {
            final IEventBroker eventBroker = this.m_agentPlugIn.getEventBroker();
            final IEventStream eventStream = this.m_agentPlugIn.getEventStream();
            final AgentStateChangedListener agentStateChangedListener = new AgentStateChangedListener(this.m_agentDatabase);
            synchronized (agentStateChangedListener) {
                final IEventInterestObject expressInterest = eventBroker.expressInterest(EDBAStateChanged.class, agentStateChangedListener, (RemoteStub)this.m_agentDatabase.evThis(), eventStream);
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
                    this.m_agentDatabase.handleCrash(string3);
                }
                else {
                    final String property = ((JuniperProperties)JAPlugIn.get().properties()).getProperty("configuration." + this.m_agentDatabase + ".defaultConfiguration" + ".otherargs");
                    String string4 = null;
                    if (property.contains("-AdminMsgTimeout")) {
                        final StringTokenizer stringTokenizer = new StringTokenizer(property);
                        while (stringTokenizer.hasMoreTokens()) {
                            if (stringTokenizer.nextToken().toString().contains("-AdminMsgTimeout")) {
                                string4 = stringTokenizer.nextToken().toString();
                            }
                        }
                    }
                    try {
                        if (string4 != null && Integer.parseInt(string4) > 120) {
                            agentStateChangedListener.wait(Integer.parseInt(string4) * 1000);
                        }
                        else {
                            agentStateChangedListener.wait(120000L);
                        }
                    }
                    catch (InterruptedException ex2) {
                        ProLog.logd("Fathom", "Interruption received while waiting for database agent to start: " + this.m_agentDisplayName);
                    }
                    expressInterest.revokeInterest();
                    if (!agentStateChangedListener.isInitializing()) {
                        final String string5 = "Database agent start up timed out: " + this.m_agentDisplayName + " -AdminMsgTimeout set to: " + Integer.parseInt(string4);
                        ProLog.logdErr("DatabaseAgent", string5);
                        this.m_agentDatabase.handleCrash(string5);
                    }
                }
            }
        }
        catch (Exception ex) {
            String s;
            if (ex instanceof InterruptedException) {
                s = ex.getMessage();
            }
            else if (ex instanceof RemoteException) {
                s = ex.getMessage();
            }
            else if (ex instanceof IOException) {
                s = ex.getMessage();
            }
            else {
                s = ex.getMessage();
            }
            ProLog.logdErr("DatabaseAgent", s);
            if (!this.m_agentDatabase.isIdle()) {
                this.m_agentDatabase.handleCrash(s);
            }
        }
    }
    
    class AgentStateChangedListener extends EventListener
    {
        boolean m_isInitializing;
        
        public AgentStateChangedListener(final AgentDatabase agentDatabase) {
            this.m_isInitializing = false;
            AgentConnect.this.m_agentDatabase = agentDatabase;
        }
        
        public synchronized void processEvent(final IEventObject eventObject) {
            try {
                final EDBAStateChanged edbaStateChanged = (EDBAStateChanged)eventObject;
                edbaStateChanged.agent().getDisplayName();
                final JAStatusObject status = edbaStateChanged.getStatus();
                if (status.isInitializing() || status.isRunning()) {
                    this.m_isInitializing = true;
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

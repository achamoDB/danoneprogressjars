// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.io.IOException;
import com.progress.chimera.log.ConnectionManagerLog;
import com.progress.common.comsock.ComMsg;
import java.util.Enumeration;
import com.progress.common.log.Excp;
import java.rmi.RemoteException;
import com.progress.message.jpMsg;

class MProservJuniper implements IJuniper, MProservJuniperAPI, jpMsg
{
    JAConfiguration config;
    
    MProservJuniper(final JAConfiguration config) {
        this.config = config;
    }
    
    protected void finalize() {
    }
    
    public void ping() throws RemoteException {
    }
    
    public void setAdminServer(final IAdminJuniper adminJuniper) throws RemoteException {
        this.sendPacket("REGA");
    }
    
    public void disconnectAdminServer() throws RemoteException {
    }
    
    public void shutDown() throws RemoteException {
        try {
            this.config.setState(CStateShuttingDown.get());
            this.shutDownAuxiliaries();
            this.shutDownServices();
            final JAAgent jaAgent = (JAAgent)this.config.database.getAgentRemote();
            if (jaAgent != null) {
                jaAgent.handleShutdown();
            }
        }
        catch (StateException ex) {
            final JAConfiguration config = this.config;
            JAConfiguration.getLog().logErr(7669630165411963161L);
            Excp.print(ex, "Can't set state to shutdown, reverting to state idle");
            try {
                this.config.setState(CStateIdle.get());
            }
            catch (StateException ex2) {
                Excp.print(ex2);
            }
        }
    }
    
    void shutDownServices() {
        final Enumeration<JAService> elements = this.config.services.elements();
        while (elements.hasMoreElements()) {
            final JAService jaService = elements.nextElement();
            if (!jaService.isStarting() && !jaService.isInitializing()) {
                if (!jaService.isRunning()) {
                    continue;
                }
            }
            try {
                jaService.setShuttingDown();
            }
            catch (Exception ex) {
                Excp.print(ex);
            }
        }
    }
    
    void shutDownAuxiliaries() {
        try {
            if (this.config.biWriter.isStarting() || this.config.biWriter.isInitializing() || this.config.biWriter.isRunning()) {
                this.config.biWriter.setShuttingDown();
            }
            if (this.config.aiWriter.isStarting() || this.config.aiWriter.isInitializing() || this.config.aiWriter.isRunning()) {
                this.config.aiWriter.setShuttingDown();
            }
            if (this.config.watchdog.isStarting() || this.config.watchdog.isInitializing() || this.config.watchdog.isRunning()) {
                this.config.watchdog.setShuttingDown();
            }
            if (this.config.apWriter.isStarting() || this.config.apWriter.isInitializing() || this.config.apWriter.isRunning()) {
                this.config.apWriter.setShuttingDown();
            }
        }
        catch (StateException ex) {
            Excp.print(ex);
        }
    }
    
    public boolean sendPacket(final String s) {
        return this.sendPacket(new ComMsg(s));
    }
    
    public boolean sendPacket(final String s, final String s2) {
        return this.sendPacket(new ComMsg(s, s2.getBytes()));
    }
    
    public boolean sendPacket(final ComMsg comMsg) {
        return sendPacket(comMsg, this.config.idValue());
    }
    
    public static boolean sendPacket(final ComMsg comMsg, final int value) {
        ConnectionManagerLog.get().log(4, 7669630165411963162L, new Object[] { new Integer(value), comMsg });
        return sendPacketX(comMsg, value);
    }
    
    public static boolean sendPacketX(final ComMsg comMsg, final int n) {
        try {
            JAPlugIn.getSocketIntf().send(n, comMsg);
        }
        catch (RemoteException ex) {
            ConnectionManagerLog.get().log(3, 7669630165411963163L);
            return false;
        }
        catch (IOException ex2) {
            ConnectionManagerLog.get().log(3, 7669630165411963163L);
            return false;
        }
        return true;
    }
}

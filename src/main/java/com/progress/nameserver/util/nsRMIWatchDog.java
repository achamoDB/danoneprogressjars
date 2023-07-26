// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.nameserver.util;

import com.progress.nameserver.NSLogEvent;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.net.MalformedURLException;
import com.progress.common.event.ExceptionEvent;
import java.rmi.RemoteException;
import com.progress.common.event.ProEvent;
import com.progress.common.event.EventManager;
import com.progress.nameserver.NameServer;
import com.progress.ubroker.util.IWatchable;

public class nsRMIWatchDog implements IWatchable
{
    private NameServer m_nameserver;
    private EventManager m_eventManager;
    private String m_rmiUrl;
    private String m_name;
    private boolean m_rmiAvailable;
    
    public nsRMIWatchDog(final NameServer nameserver, final String rmiUrl, final String name, final EventManager eventManager) {
        this.m_nameserver = nameserver;
        this.m_eventManager = eventManager;
        this.m_rmiUrl = rmiUrl;
        this.m_name = name;
        this.m_rmiAvailable = true;
    }
    
    public void watchEvent() {
        try {
            this.m_nameserver.lookupService(this.m_rmiUrl);
        }
        catch (RemoteException ex) {
            if (this.m_rmiAvailable) {
                this.m_rmiAvailable = false;
                this.m_eventManager.post(new LostAdminServerEvent(this));
            }
        }
        catch (MalformedURLException ex2) {
            this.m_eventManager.post(new ExceptionEvent(this, new NameServer.InvalidURLException(this.m_name, this.m_rmiUrl)));
        }
        catch (NotBoundException ex3) {
            try {
                NameServer.rebindService(this.m_rmiUrl, this.m_nameserver);
                this.m_nameserver.setFathomEventStream();
                this.m_rmiAvailable = true;
                this.m_eventManager.post(new FoundAdminServerEvent(this));
            }
            catch (RemoteException ex4) {
                if (!this.m_rmiAvailable) {
                    this.m_eventManager.post(new ExceptionEvent(this, new NameServer.UnableToRegisterException(this.m_name, this.m_rmiUrl)));
                    this.m_rmiAvailable = true;
                }
            }
            catch (MalformedURLException ex5) {
                if (this.m_rmiAvailable) {
                    return;
                }
                this.m_eventManager.post(new ExceptionEvent(this, new NameServer.InvalidURLException(this.m_name, this.m_rmiUrl)));
                this.m_rmiAvailable = true;
            }
        }
    }
    
    static class LostAdminServerEvent extends NSLogEvent
    {
        public LostAdminServerEvent(final Object o) {
            super(o, 2, "RMI unavailable - lost contact with AdminServer. ", new Object[0]);
        }
    }
    
    static class FoundAdminServerEvent extends NSLogEvent
    {
        public FoundAdminServerEvent(final Object o) {
            super(o, 2, "RMI available - re-registered with AdminServer. ", new Object[0]);
        }
    }
}

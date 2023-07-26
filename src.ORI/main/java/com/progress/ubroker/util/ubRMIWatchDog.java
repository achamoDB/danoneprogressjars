// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import com.progress.ubroker.broker.ubListenerThread;
import com.progress.common.ehnlog.IAppLogger;

public class ubRMIWatchDog implements ubConstants, IWatchable
{
    private IAppLogger m_log;
    private ubListenerThread m_listener;
    private String m_rmiUrl;
    private boolean m_rmiAvailable;
    
    public ubRMIWatchDog(final ubListenerThread listener, final String rmiUrl, final IAppLogger log) {
        this.m_listener = listener;
        this.m_log = log;
        this.m_rmiUrl = rmiUrl;
        this.m_rmiAvailable = true;
    }
    
    public void watchEvent() {
        try {
            ubThread.lookupService(this.m_rmiUrl);
        }
        catch (RemoteException ex4) {
            if (this.m_rmiAvailable) {
                this.m_rmiAvailable = false;
                if (this.m_log.ifLogBasic(1L, 0)) {
                    this.m_log.logBasic(0, "RMI unavailable - lost contact with AdminServer. ", new Object[0]);
                }
            }
        }
        catch (MalformedURLException ex) {
            this.m_log.logError(7665689515738014026L, new Object[] { ex.getMessage() });
        }
        catch (NotBoundException ex5) {
            try {
                ubThread.rebindService(this.m_rmiUrl, this.m_listener);
                this.m_rmiAvailable = true;
                if (this.m_log.ifLogBasic(1L, 0)) {
                    this.m_log.logBasic(0, "RMI available - re-registered with AdminServer. ", new Object[0]);
                }
            }
            catch (RemoteException ex2) {
                if (!this.m_rmiAvailable) {
                    this.m_log.logError(7665689515738014025L, new Object[] { ex2.getMessage() });
                    this.m_rmiAvailable = true;
                }
            }
            catch (MalformedURLException ex3) {
                if (this.m_rmiAvailable) {
                    return;
                }
                this.m_log.logError(7665689515738014026L, new Object[] { ex3.getMessage() });
                this.m_rmiAvailable = true;
            }
        }
    }
}

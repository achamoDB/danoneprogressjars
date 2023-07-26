// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

import java.rmi.ConnectIOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.AlreadyBoundException;
import com.progress.common.temporalcoordination.Timings;
import java.net.Socket;
import com.progress.common.log.ProLog;
import java.net.InetAddress;
import java.rmi.Remote;
import com.progress.common.util.IMessageCallback;
import java.rmi.registry.Registry;

public class PrimaryRegistryManager extends RegistryManager
{
    static Registry theRegistry;
    
    public PrimaryRegistryManager() {
    }
    
    public PrimaryRegistryManager(final int n) {
        super(n);
    }
    
    public PrimaryRegistryManager(final IMessageCallback messageCallback) {
        super(messageCallback);
    }
    
    public PrimaryRegistryManager(final IMessageCallback messageCallback, final String s, final int n) {
        super(messageCallback, s, n);
    }
    
    public PrimaryRegistryManager(final IMessageCallback messageCallback, final String s) {
        super(messageCallback, s);
    }
    
    public PrimaryRegistryManager(final IMessageCallback messageCallback, final int n) {
        super(messageCallback, n);
    }
    
    public PrimaryRegistryManager(final IMessageCallback messageCallback, final String s, final Integer n) {
        super(messageCallback, s, n);
    }
    
    public boolean isPrimary() {
        return true;
    }
    
    public Registry getRegistry() {
        return PrimaryRegistryManager.theRegistry;
    }
    
    public void register(final String selfKey, final Remote self) throws RemoteException, AccessException, Throwable {
        final String hostAddress = InetAddress.getByName(super.host).getHostAddress();
        this.printMess(1, 8242994692971568823L);
        this.printMess(1, 8242994692971568825L, new Object[] { selfKey });
        this.printMess(1, 8242994692971568828L, new Object[] { super.host });
        ProLog.logd("RegistryManager", 1, "     Address: " + hostAddress);
        this.printMess(1, 8242994692971568831L, new Object[] { new Integer(super.port) });
        super.self = self;
        super.selfKey = selfKey;
        Label_0270: {
            if (!this.registryIsRunning()) {
                try {
                    ProLog.logd("RegistryManager", "Opening RMI socket on " + hostAddress + ": " + super.port);
                    if (new Socket(hostAddress, super.port) != null) {
                        this.printMess(4, "Port " + super.port + " already in use");
                        throw new Exception("Could not start RMI Registry");
                    }
                }
                catch (Throwable t3) {
                    this.printMess(4, "RMI registry already running.");
                }
                try {
                    this.startRMIRegistry();
                    break Label_0270;
                }
                catch (Throwable t) {
                    this.printExcp(t, "Could not start RMI Registry");
                    throw new Exception("Could not start RMI Registry");
                }
            }
            this.printMess(2, 8242994692971568834L);
            this.listEntries();
        }
        boolean b = true;
        try {
            int i;
            for (i = 0; i < 5; ++i) {
                try {
                    TryIt.call(new PrimaryBinder(this), null, Timings.getRegistryTimeout());
                    break;
                }
                catch (TimedOut timedOut) {
                    this.printMess(2, 8242994692971568836L);
                }
                catch (CallFailed callFailed) {
                    this.printMess(2, 8242994692971568838L);
                }
                if (i < 4) {
                    Thread.sleep(Timings.getRetryWait());
                }
            }
            if (i == 5) {
                this.printErrMess(8242994692971568840L, new Object[] { new Integer(5) });
                b = false;
            }
        }
        catch (AlreadyBoundException ex) {
            this.printExcp(ex, "PrimaryRegistryManager - Could not bind  - this should never happen");
            b = false;
        }
        catch (Throwable t2) {
            this.printExcp(t2, "PrimaryRegistryManager - Could not bind name " + super.selfKey + " to object " + super.self);
            b = false;
        }
        if (b) {
            this.printMess(2, 8242994692971568841L);
        }
        else {
            this.printErrMess(8242994692971568842L);
        }
        if (!b) {
            throw new RemoteException("Bind of \"" + super.selfKey + "\" failed");
        }
    }
    
    public void unregister() {
        this.printMess(1, 8242994692971568844L, new Object[] { super.selfKey });
        if (!this.registryIsRunning()) {
            this.printMess(3, 8242994692971568845L);
            return;
        }
        try {
            LocateRegistry.getRegistry(super.host, super.port).unbind(super.selfKey);
        }
        catch (NotBoundException ex) {
            this.printErrMess(8242994692971568875L, new Object[] { super.selfKey });
        }
        catch (Throwable t) {
            this.printExcp(t, "Unable to remove old entry from registry.");
            this.printErrMess(8242994692971568876L, new Object[] { super.selfKey, t });
        }
        if (PrimaryRegistryManager.theRegistry != null) {
            this.printMess(1, 8242994692971568850L);
        }
        else {
            this.printMess(3, 8242994692971568845L);
        }
    }
    
    protected boolean registryIsRunning() {
        return PrimaryRegistryManager.theRegistry != null;
    }
    
    protected void startRMIRegistry() throws Exception {
        try {
            this.printMess(1, 8242994692971568852L, new Object[] { new Integer(this.port()) });
            PrimaryRegistryManager.theRegistry = LocateRegistry.createRegistry(this.port());
            if (PrimaryRegistryManager.theRegistry != null) {
                this.printMess(2, 8242994692971568853L, new Object[] { PrimaryRegistryManager.theRegistry });
                return;
            }
            this.printErrMess(8242994692971568854L);
            throw new Exception("Could not start RMI Registry");
        }
        catch (Exception ex) {
            this.printExcp(ex, "Failed to start RMI registry thread");
            this.printErrMess(8242994692971568856L);
            throw ex;
        }
    }
    
    static {
        PrimaryRegistryManager.theRegistry = null;
    }
    
    class PrimaryBinder implements ICallable
    {
        RegistryManager regMan;
        Integer dummy;
        
        PrimaryBinder(final RegistryManager regMan) {
            this.regMan = null;
            this.dummy = new Integer(0);
            this.regMan = regMan;
        }
        
        public Object call(final Object o) throws RemoteException, Exception {
            PrimaryRegistryManager.this.printMess(1, 8242994692971568857L, new Object[] { this.regMan.self, this.regMan.selfKey });
            try {
                LocateRegistry.getRegistry(PrimaryRegistryManager.this.host, PrimaryRegistryManager.this.port).rebind(this.regMan.selfKey, this.regMan.self);
            }
            catch (ConnectIOException ex) {
                throw ex;
            }
            catch (Exception ex2) {
                PrimaryRegistryManager.this.printExcp(ex2, "Bind failed");
                PrimaryRegistryManager.this.printErrMess(8242994692971568860L, new Object[] { ex2 });
                throw ex2;
            }
            PrimaryRegistryManager.this.printMess(1, 8242994692971568861L, new Object[] { this.regMan.selfKey });
            return this.dummy;
        }
    }
}

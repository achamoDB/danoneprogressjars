// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.rmiregistry;

import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.EventListener;
import com.progress.common.networkevents.IEventListener;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.net.InetAddress;
import com.progress.common.exception.ExceptionMessageAdapter;
import com.progress.common.temporalcoordination.Timings;
import java.rmi.Remote;
import com.progress.common.util.IMessageCallback;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.networkevents.EventBroker;
import com.progress.message.reMsg;

public class RegistryManager implements reMsg
{
    static EventBroker localEventBroker;
    Binder theBinder;
    IEventInterestObject crashEIO;
    int userRegistryTimeout;
    int userPingTimeout;
    int userRetryWait;
    public static final int DEFAULT_RMI_REGISTRY_PORT = 20931;
    public static final String DEFAULT_PRIMARY_BINDNAME = "Chimera";
    public static final String RMI_REGISTRY_PORT_PROPERTY = "RMIRegistryPort";
    protected String selfKey;
    protected String primaryBindName;
    protected String host;
    protected int port;
    protected IMessageCallback messCB;
    protected Remote self;
    
    public void setRegistryTimeout(final int userRegistryTimeout) {
        this.userRegistryTimeout = userRegistryTimeout;
    }
    
    public int getRegistryTimeout() {
        if (this.userRegistryTimeout > 0) {
            return this.userRegistryTimeout;
        }
        return Timings.getRegistryTimeout();
    }
    
    public void setPingTimeout(final int userPingTimeout) {
        this.userPingTimeout = userPingTimeout;
    }
    
    public int getPingTimeout() {
        if (this.userPingTimeout > 0) {
            return this.userPingTimeout;
        }
        return Timings.getPingTimeout();
    }
    
    public void setRetryWait(final int userRetryWait) {
        this.userRetryWait = userRetryWait;
    }
    
    public int getRetryWait() {
        if (this.userRetryWait > 0) {
            return this.userRetryWait;
        }
        return Timings.getRetryWait();
    }
    
    EventBroker getLocalEventBroker() {
        if (RegistryManager.localEventBroker == null) {
            try {
                RegistryManager.localEventBroker = new EventBroker(this.messCB);
            }
            catch (Throwable t) {
                this.printExcp(t, "Error making local event broker");
            }
        }
        return RegistryManager.localEventBroker;
    }
    
    protected void printErrMess(final String s) {
        this.printMess(0, s);
    }
    
    protected void printErrMess(final String s, final Object[] array) {
        this.printMess(0, s, array);
    }
    
    protected void printErrMess(final long n) {
        this.printMess(0, n);
    }
    
    protected void printErrMess(final long n, final Object[] array) {
        this.printMess(0, n, array);
    }
    
    protected void printMess(final int n, final String s) {
        final String message = ExceptionMessageAdapter.getMessage(s, null);
        if (this.messCB != null) {
            this.messCB.handleMessage(message);
        }
    }
    
    protected void printMess(final int n, final String s, final Object[] array) {
        final String message = ExceptionMessageAdapter.getMessage(s, array);
        if (this.messCB != null) {
            this.messCB.handleMessage(message);
        }
    }
    
    protected void printMess(final int n, final long n2) {
        final String message = ExceptionMessageAdapter.getMessage(n2, null);
        if (this.messCB != null) {
            this.messCB.handleMessage(message);
        }
    }
    
    protected void printMess(final int n, final long n2, final Object[] array) {
        final String message = ExceptionMessageAdapter.getMessage(n2, array);
        if (this.messCB != null) {
            this.messCB.handleMessage(n, message);
        }
    }
    
    protected void printExcp(final Throwable t, final String s) {
        if (this.messCB != null) {
            this.messCB.handleException(t, s);
        }
    }
    
    public RegistryManager() {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(null, null, null);
    }
    
    public RegistryManager(final int value) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(null, null, new Integer(value));
    }
    
    public RegistryManager(final String s) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(null, s, null);
    }
    
    public RegistryManager(final IMessageCallback messageCallback) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(messageCallback, null, null);
    }
    
    public RegistryManager(final IMessageCallback messageCallback, final String s, final int value) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(messageCallback, s, new Integer(value));
    }
    
    public RegistryManager(final IMessageCallback messageCallback, final String s, final int value, final int n) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(messageCallback, s, new Integer(value));
    }
    
    public RegistryManager(final IMessageCallback messageCallback, final String s, final Integer n) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(messageCallback, s, n);
    }
    
    public RegistryManager(final IMessageCallback messageCallback, final String s) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(messageCallback, s, null);
    }
    
    public RegistryManager(final IMessageCallback messageCallback, final int value) {
        this.theBinder = null;
        this.crashEIO = null;
        this.userRegistryTimeout = -1;
        this.userPingTimeout = -1;
        this.userRetryWait = -1;
        this.selfKey = null;
        this.primaryBindName = null;
        this.host = null;
        this.messCB = null;
        this.self = null;
        this.RegistryManagerX(messageCallback, null, new Integer(value));
    }
    
    public void RegistryManagerX(final IMessageCallback messCB, final String host, final Integer n) {
        this.messCB = messCB;
        this.host = host;
        if (this.host == null) {
            try {
                this.host = InetAddress.getLocalHost().getHostName();
            }
            catch (Throwable t) {
                this.host = "localhost";
                this.printExcp(t, "RegistryManager - could not get hostname, using \"" + this.host + "\"");
            }
        }
        if (n == null) {
            this.port = this.getPortProperty();
        }
        else {
            this.port = n;
        }
    }
    
    public String getPrimaryBindName() {
        return "Chimera";
    }
    
    public String host() {
        return this.host;
    }
    
    public int port() {
        return this.port;
    }
    
    protected String makeURL(final String s) {
        return this.makeURL(s, this.host, this.port);
    }
    
    private int getPortProperty() {
        final String property = System.getProperty("RMIRegistryPort");
        if (property != null) {
            return new Integer(property);
        }
        return 20931;
    }
    
    private String makeURL(final String str, final String str2, final int i) {
        return "rmi://" + str2 + ":" + i + "/" + str;
    }
    
    public void listEntries() {
        this.listEntries(1);
    }
    
    public void listEntries(final int n) {
        this.printMess(n, 8242994692971568863L);
        String[] list;
        try {
            list = LocateRegistry.getRegistry(this.host, this.port).list();
        }
        catch (Throwable t) {
            this.printErrMess(8242994692971568864L);
            return;
        }
        for (int i = 0; i < list.length; ++i) {
            boolean b = true;
            try {
                Remote remote;
                try {
                    remote = (Remote)TryIt.call(new LookerUpper(list[i], this.host, this.port), null, this.getRegistryTimeout());
                }
                catch (TimedOut timedOut) {
                    this.printMess(n, 8242994692971568865L, new Object[] { list[i] });
                    continue;
                }
                if (remote == null) {
                    this.printErrMess(8242994692971568866L, new Object[] { list[i] });
                }
                else {
                    if (!PingIt.isActive((IPingable)remote, this.getPingTimeout())) {
                        b = false;
                    }
                    if (b) {
                        this.printMess(n, 8242994692971568910L, new Object[] { list[i], " (active)" });
                    }
                    else {
                        this.printMess(n, 8242994692971568910L, new Object[] { list[i], " (stale)" });
                    }
                }
            }
            catch (Throwable t2) {
                this.printMess(n, 8242994692971568911L, new Object[] { list[i] });
            }
        }
    }
    
    public Remote lookup(final String s) throws RemoteException, NotBoundException, AccessException {
        return this.lookup(s, this.host, this.port);
    }
    
    public Remote lookup(final String s, final String s2, final int n) throws RemoteException, NotBoundException, AccessException {
        return this.lookupX(s, s2, n);
    }
    
    public Remote lookupX(final String str, final String s, final int value) throws RemoteException, NotBoundException, AccessException {
        this.printMess(2, 8242994692971568867L, new Object[] { str, s, new Integer(value) });
        Remote remote;
        try {
            try {
                remote = (Remote)TryIt.call(new LookerUpper(str, s, value), null, this.getRegistryTimeout());
            }
            catch (TimedOut timedOut) {
                this.printMess(2, 8242994692971568868L, new Object[] { str });
                return null;
            }
            if (remote == null) {
                this.printMess(2, 8242994692971568869L, new Object[] { str });
                throw new NotBoundException();
            }
            this.printMess(2, 8242994692971568870L, new Object[] { str });
            try {
                if (!PingIt.isActive((IPingable)remote, this.getPingTimeout())) {
                    this.printMess(2, 8242994692971568872L, new Object[] { str });
                    throw new NotBoundException();
                }
                this.printMess(3, 8242994692971568871L, new Object[] { str });
            }
            catch (NotBoundException ex) {
                throw ex;
            }
            catch (Throwable t) {
                this.printExcp(t, "  Trouble accessing object with name " + str + ". Probably not subtype of IPingable. Ignoring it...");
                this.printErrMess(8242994692971568873L, new Object[] { str });
                remote = null;
            }
        }
        catch (NotBoundException ex2) {
            throw ex2;
        }
        catch (Throwable t2) {
            this.printMess(2, 8242994692971568869L, new Object[] { str });
            throw new NotBoundException();
        }
        return remote;
    }
    
    public void register(final Remote remote) throws RemoteException, AccessException, Throwable {
        this.register(null, remote, null);
    }
    
    public void register(final String s, final Remote remote) throws RemoteException, AccessException, Throwable {
        this.register(s, remote, null);
    }
    
    public void register(final Remote remote, final IRegistration registration) throws RemoteException, AccessException, Throwable {
        this.register(null, remote, registration);
    }
    
    public void register(final String selfKey, final Remote self, final IRegistration registration) throws RemoteException, AccessException, Throwable {
        if (selfKey != null) {
            this.printMess(1, 8242994692971569062L, new Object[] { self, selfKey });
        }
        else {
            this.printMess(1, 8242994692971569063L, new Object[] { self });
        }
        this.self = self;
        if (selfKey != null) {
            this.selfKey = selfKey;
        }
        (this.theBinder = new Binder(this, registration)).start();
    }
    
    public void disconnect() {
        if (this.theBinder != null) {
            this.theBinder.stop();
        }
        try {
            if (this.crashEIO != null) {
                RegistryManager.localEventBroker.revokeInterest(this.crashEIO);
            }
        }
        catch (RemoteException ex) {}
    }
    
    public void unregister() {
        this.disconnect();
        if (this.selfKey == null) {
            this.printMess(1, 8242994692971569067L);
            return;
        }
        this.printMess(1, 8242994692971568874L, new Object[] { this.selfKey });
        try {
            LocateRegistry.getRegistry(this.host, this.port).unbind(this.selfKey);
        }
        catch (NotBoundException ex) {
            this.printErrMess(8242994692971568875L, new Object[] { this.selfKey });
        }
        catch (Throwable t) {
            this.printExcp(t, "Unable to remove old entry from registry.");
            this.printErrMess(8242994692971568876L, new Object[] { this.selfKey });
        }
    }
    
    public Object getPrimaryObject() {
        try {
            return this.lookup(this.getPrimaryBindName());
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    static {
        RegistryManager.localEventBroker = null;
    }
    
    class Binder extends Thread
    {
        IRegistration registrationCallback;
        RegistryManager regMan;
        EventBroker localEventBroker;
        
        Binder(final RegistryManager regMan, final IRegistration registrationCallback) {
            this.registrationCallback = null;
            this.regMan = null;
            this.localEventBroker = null;
            this.regMan = regMan;
            this.registrationCallback = registrationCallback;
            this.localEventBroker = this.regMan.getLocalEventBroker();
        }
        
        public void run() {
            RegistryManager.this.printMess(4, 8242994692971568877L, new Object[] { this.regMan.self });
            if (this.regMan.selfKey != null) {
                RegistryManager.this.printMess(4, 8242994692971568878L, new Object[] { this.regMan.selfKey });
            }
            else {
                RegistryManager.this.printMess(4, 8242994692971568879L);
            }
            while (true) {
                try {
                    final IPingable pingable = (IPingable)RegistryManager.this.getPrimaryObject();
                    if (pingable != null) {
                        if (this.regMan.selfKey != null) {
                            LocateRegistry.getRegistry(RegistryManager.this.host, RegistryManager.this.port).rebind(this.regMan.selfKey, this.regMan.self);
                        }
                        RegistryManager.this.printMess(4, 8242994692971568880L);
                        if (this.registrationCallback != null) {
                            this.registrationCallback.registered(this.regMan.self, pingable);
                        }
                        final CrashEventHandler crashEventHandler = new CrashEventHandler(this);
                        try {
                            RegistryManager.this.crashEIO = this.localEventBroker.monitorObject(pingable, RegistryManager.this.getRetryWait(), RegistryManager.this.getPingTimeout(), crashEventHandler);
                            RegistryManager.this.printMess(4, 8242994692971568881L);
                        }
                        catch (Throwable t) {
                            RegistryManager.this.printExcp(t, "Error expressing interest in crash event: " + t.getMessage());
                        }
                        RegistryManager.this.theBinder = null;
                        return;
                    }
                }
                catch (Throwable t2) {}
                RegistryManager.this.printMess(4, 8242994692971568883L, new Object[] { new Integer(RegistryManager.this.getRetryWait() / 1000) });
                try {
                    Thread.sleep(RegistryManager.this.getRetryWait());
                }
                catch (Throwable t3) {}
            }
        }
        
        class CrashEventHandler extends EventListener
        {
            Binder binder;
            
            CrashEventHandler(final Binder binder) {
                this.binder = null;
                this.binder = binder;
            }
            
            public void processEvent(final IEventObject eventObject) throws RemoteException {
                RegistryManager.this.printErrMess(8242994692971568884L);
                this.binder.localEventBroker.revokeInterest(this.binder.regMan.crashEIO);
                this.binder.regMan.crashEIO = null;
                (RegistryManager.this.theBinder = new Binder(this.binder.regMan, this.binder.registrationCallback)).start();
            }
        }
    }
    
    class LookerUpper implements ICallable
    {
        String key;
        String host;
        int port;
        
        LookerUpper(final String key, final String host, final int port) {
            this.key = null;
            this.key = key;
            this.host = host;
            this.port = port;
        }
        
        public Object call(final Object o) throws RemoteException {
            Object lookup = null;
            RegistryManager.this.printMess(4, 8242994692971568820L, new Object[] { this.key });
            try {
                lookup = LocateRegistry.getRegistry(this.host, this.port).lookup(this.key);
            }
            catch (Throwable t) {}
            return lookup;
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.Remote;
import java.rmi.NotBoundException;
import com.progress.common.rmiregistry.PingIt;
import java.util.Enumeration;
import java.rmi.UnmarshalException;
import java.rmi.MarshalException;
import com.progress.chimera.common.Tools;
import java.rmi.server.RemoteStub;
import com.progress.common.temporalcoordination.Timings;
import com.progress.common.exception.ExceptionMessageAdapter;
import java.io.PrintStream;
import java.rmi.RemoteException;
import com.progress.common.rmiregistry.RegistryManager;
import java.util.Vector;
import com.progress.common.collections.linked.QueueSynchronized;
import com.progress.common.util.IMessageCallback;
import com.progress.message.evMsg;
import com.progress.common.rmiregistry.IPingable;
import java.rmi.server.UnicastRemoteObject;

public class EventBroker extends UnicastRemoteObject implements IEventBroker, IEventBrokerDebug, IPingable, evMsg
{
    static int counterG;
    public int id;
    protected IMessageCallback messCB;
    public Router router;
    QueueSynchronized inputEventQueue;
    Vector dispatchTable;
    EventScreen eventScreen;
    ThreadTrackerVector monitors;
    ThreadTrackerVector locators;
    static int total;
    static Class eventBaseClass;
    static RegistryManager regMan;
    
    public EventBroker() throws RemoteException {
        this(null);
    }
    
    public EventBroker(final IMessageCallback messageCallback) throws RemoteException {
        this.id = 0;
        this.messCB = null;
        this.router = null;
        this.inputEventQueue = new QueueSynchronized();
        this.dispatchTable = new Vector();
        this.eventScreen = new EventScreen();
        this.monitors = new ThreadTrackerVector();
        this.locators = new ThreadTrackerVector();
        ++EventBroker.counterG;
        this.id = EventBroker.counterG;
        this.messCB = this.messCB;
        (this.router = new Router(this, this.inputEventQueue, this.dispatchTable)).start();
    }
    
    public void printDispatchTableRemote() {
        this.printDispatchTable(System.out);
    }
    
    public void printDispatchTable(final PrintStream printStream) {
        printStream.println("");
        printStream.println("Dump of all dispatch entries  for event broker: " + this);
        if (this.dispatchTable == null) {
            printStream.println("");
            return;
        }
        int n = 0;
        try {
            synchronized (this.dispatchTable) {
                printStream.println("");
                n = 0;
                while (true) {
                    final DispatchEntry dispatchEntry = this.dispatchTable.elementAt(n);
                    printStream.println("   Event type: " + dispatchEntry.eventType);
                    if (dispatchEntry.issuer != null) {
                        printStream.println("   For issuer:   " + dispatchEntry.issuer);
                    }
                    else if (dispatchEntry.cIssuers != null) {
                        printStream.println("   For Issuers of type: " + dispatchEntry.cIssuers);
                    }
                    else if (dispatchEntry.eIssuers != null) {
                        printStream.println("   For Issuers in enumeration: " + dispatchEntry.eIssuers);
                    }
                    printStream.println("");
                    ++n;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            printStream.println("Total dispatch entries: " + n);
            printStream.println("");
        }
    }
    
    public void shutdown() {
        this.router.shutdown();
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
    
    protected void printMess(final int n, final long n2) {
        final String message = ExceptionMessageAdapter.getMessage(n2, null);
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
    
    protected void printMess(final int n, final long n2, final Object[] array) {
        final String message = ExceptionMessageAdapter.getMessage(n2, array);
        if (this.messCB != null) {
            this.messCB.handleMessage(message);
        }
    }
    
    protected void printExcp(final Throwable t, final String s) {
        if (this.messCB != null) {
            this.messCB.handleException(t, s);
        }
    }
    
    public void ping() {
    }
    
    void bounceClient(final Client obj) {
        try {
            synchronized (this.dispatchTable) {
                int n = 0;
                while (true) {
                    if (((DispatchEntry)this.dispatchTable.elementAt(n)).client.equals(obj)) {
                        this.dispatchTable.removeElementAt(n);
                        --n;
                    }
                    ++n;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
    }
    
    public IEventStream openEventStream(final String s) throws RemoteException {
        return new Client(s, this);
    }
    
    public IEventStream openEventStream(final String s, final int n) throws RemoteException {
        return new Client(s, this, n);
    }
    
    public void closeEventStream(final IEventStream eventStream) throws RemoteException {
        ((IClient)eventStream).terminate();
    }
    
    public IEventInterestObject locateObject(final String s, final RegistryManager registryManager, final IEventListener eventListener) throws RemoteException {
        return this.locateObject(s, registryManager, Timings.getRetryWait(), eventListener);
    }
    
    public IEventInterestObject locateObject(final String str, final RegistryManager registryManager, final int n, final IEventListener eventListener) throws RemoteException {
        final IEventInterestObject expressInterest = this.expressInterest(LocateEvent.class, eventListener, new Client("Locator:" + str, this));
        final Locator locator = new Locator(str, registryManager, expressInterest, this, n);
        locator.setDaemon(true);
        locator.start();
        this.locators.addElement(new ThreadTracker(locator, expressInterest));
        this.printMess(1, 7311031045082586870L, new Object[] { str });
        this.printMess(1, 7311031045082586871L, new Object[] { locator });
        this.printMess(1, 7311031045082586872L, new Object[] { registryManager.host(), new Integer(registryManager.port()) });
        this.printMess(1, 7311031045082586873L, new Object[] { new Integer(n / 1000) });
        return expressInterest;
    }
    
    public IEventInterestObject monitorObject(final IPingable pingable, final IEventListener eventListener) throws RemoteException {
        return this.monitorObject(pingable, Timings.getRetryWait(), Timings.getPingTimeout(), eventListener);
    }
    
    public IEventInterestObject monitorObject(final IPingable pingable, final int n, final IEventListener eventListener) throws RemoteException {
        return this.monitorObject(pingable, n, Timings.getPingTimeout(), eventListener);
    }
    
    public IEventInterestObject monitorObject(final IPingable obj, final int n, final int n2, final IEventListener eventListener) throws RemoteException {
        final IEventInterestObject expressInterest = this.expressInterest(DisconnectEvent.class, eventListener, (RemoteStub)obj, new Client("Monitor:" + obj, this));
        final Monitor monitor = new Monitor(obj, expressInterest, this, n, n2);
        monitor.setDaemon(true);
        monitor.start();
        this.monitors.addElement(new ThreadTracker(monitor, expressInterest));
        this.printMess(1, 7311031045082586874L, new Object[] { obj });
        this.printMess(1, 7311031045082586875L, new Object[] { monitor });
        this.printMess(1, 7311031045082586876L, new Object[] { new Integer(n / 1000) });
        this.printMess(1, 7311031045082586877L, new Object[] { new Integer(n2 / 1000) });
        return expressInterest;
    }
    
    public IEventInterestObject expressInterest(final Class clazz, final IEventListener eventListener, final IEventStream eventStream) throws RemoteException {
        return this.expressInterest(clazz, eventListener, (RemoteStub)null, eventStream);
    }
    
    public IEventInterestObject expressInterest(final Class clazz, final IEventListener eventListener, final RemoteStub remoteStub, final IEventStream eventStream) throws RemoteException {
        return this.expressInterestLocal(clazz, eventListener, remoteStub, eventStream);
    }
    
    public IEventInterestObject expressInterestLocal(final Class clazz, final IEventListener eventListener, final Object obj, final IEventStream eventStream) throws RemoteException {
        if (obj != null) {
            this.printMess(4, "Interest expressed in: " + clazz.getName() + " for object " + obj);
        }
        else {
            this.printMess(4, "Interest expressed in: " + clazz.getName());
        }
        final DispatchEntry obj2 = new DispatchEntry(this, clazz, eventListener, obj, (IClient)eventStream);
        synchronized (this.dispatchTable) {
            this.dispatchTable.addElement(obj2);
        }
        this.eventScreen.add(clazz);
        return obj2;
    }
    
    public IEventInterestObject expressInterest(final Class clazz, final IEventListener eventListener, final Class obj, final IEventStream eventStream) throws RemoteException {
        this.printMess(4, "Interest expressed in: " + clazz.getName() + " for objects of type: " + obj);
        final DispatchEntry obj2 = new DispatchEntry(this, clazz, eventListener, obj, (IClient)eventStream);
        synchronized (this.dispatchTable) {
            this.dispatchTable.addElement(obj2);
        }
        this.eventScreen.add(clazz);
        return obj2;
    }
    
    public void revokeInterest(final IEventInterestObject eventInterestObject) throws RemoteException {
        this.printMess(4, "Interest revoked for: " + eventInterestObject.eventType());
        if (Tools.isaSubclass(eventInterestObject.eventType(), DisconnectEvent.class)) {
            this.monitors.eliminate(eventInterestObject);
        }
        else if (Tools.isaSubclass(eventInterestObject.eventType(), LocateEvent.class)) {
            this.locators.eliminate(eventInterestObject);
        }
        synchronized (this.dispatchTable) {
            for (int size = this.dispatchTable.size(), i = 0; i < size; ++i) {
                if (eventInterestObject.equals(this.dispatchTable.elementAt(i))) {
                    this.dispatchTable.removeElementAt(i);
                    break;
                }
            }
        }
    }
    
    public void postEvent(final IEventObject eventObject) throws RemoteException {
        try {
            this.printMess(1, 7311031045082586878L, new Object[] { eventObject.getClass() });
            Class classDef = null;
            synchronized (this) {
                classDef = eventObject.classDef();
            }
            if (!this.eventScreen.ok(classDef)) {
                this.printMess(1, "Throwing away event of type " + classDef.getName());
                return;
            }
            final boolean expedite = eventObject.expedite();
            final LocalEventObject localEventObject = new LocalEventObject(eventObject, classDef, eventObject.issuer(), expedite);
            if (expedite) {
                this.inputEventQueue.insertAtHead(localEventObject);
            }
            else {
                this.inputEventQueue.insert(localEventObject);
            }
            synchronized (this.router) {
                this.router.notify();
            }
        }
        catch (MarshalException ex) {
            this.printErrMess(7311031045082586881L);
        }
        catch (UnmarshalException ex2) {
            this.printErrMess(7311031045082586881L);
        }
        catch (Exception obj) {
            this.printErrMess(7311031045082586885L, new Object[] { obj.getClass().getName(), obj.getMessage() });
            this.printExcp(obj, "EventManager can't enqueue event of type " + eventObject.getClass() + " because of unexpected exception: " + obj);
        }
    }
    
    public static boolean isaSubclass(final Class clazz, final Class obj) {
        if (clazz.equals(obj)) {
            return true;
        }
        if (clazz == obj) {
            return true;
        }
        if (clazz == EventBroker.eventBaseClass) {
            return false;
        }
        final Class superclass = clazz.getSuperclass();
        return superclass != null && isaSubclass(superclass, obj);
    }
    
    static {
        EventBroker.counterG = 0;
        EventBroker.total = 0;
        EventBroker.eventBaseClass = EventObject.eventBaseClass();
        EventBroker.regMan = null;
    }
    
    class Router extends Thread
    {
        QueueSynchronized inputEventQueue;
        Vector dispatchTable;
        EventBroker eb;
        boolean allDone;
        
        Router(final EventBroker eb, final QueueSynchronized inputEventQueue, final Vector dispatchTable) {
            this.inputEventQueue = null;
            this.dispatchTable = null;
            this.eb = null;
            this.allDone = false;
            this.inputEventQueue = inputEventQueue;
            this.dispatchTable = dispatchTable;
            this.eb = eb;
            this.setName("Event input handler");
        }
        
        public void shutdown() {
            this.allDone = true;
        }
        
        public void run() {
            while (!this.allDone) {
                Label_0010: {
                    break Label_0010;
                    try {
                        while (true) {
                            final LocalEventObject localEventObject = (LocalEventObject)this.inputEventQueue.extract();
                            if (localEventObject == null) {
                                break;
                            }
                            int n = 0;
                            try {
                                int index = 0;
                                while (true) {
                                    DispatchEntry dispatchEntry = null;
                                    synchronized (this.dispatchTable) {
                                        dispatchEntry = this.dispatchTable.elementAt(index);
                                    }
                                    if (this.handle(dispatchEntry, localEventObject)) {
                                        ++n;
                                    }
                                    ++index;
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException ex) {}
                            catch (Throwable t) {
                                this.eb.printExcp(t, "Cant handle events 1: " + t.getClass().getName() + " " + t.getMessage());
                            }
                            this.printRecipientCount(localEventObject.event, n);
                        }
                    }
                    catch (Throwable t2) {
                        this.eb.printExcp(t2, "Cant handle events 2: " + t2.getMessage());
                    }
                }
                synchronized (this) {
                    try {
                        this.wait();
                    }
                    catch (Throwable t3) {}
                }
            }
        }
        
        boolean handle(final DispatchEntry dispatchEntry, final LocalEventObject obj) {
            final IEventObject event = obj.event;
            final Class type = obj.type;
            try {
                if (!EventBroker.isaSubclass(type, dispatchEntry.eventType)) {
                    return false;
                }
            }
            catch (Throwable t) {
                this.eb.printExcp(t, "Cant get subclass for " + obj + ": " + t.getMessage());
                return false;
            }
            Object issuer = null;
            try {
                issuer = event.issuer();
            }
            catch (Throwable t2) {}
            if (dispatchEntry.issuer != null) {
                if (dispatchEntry.issuer.equals(issuer)) {
                    this.passToClient(obj, dispatchEntry);
                    return true;
                }
                return false;
            }
            else {
                if (dispatchEntry.cIssuers == null) {
                    if (dispatchEntry.eIssuers != null) {
                        final Object obj2 = null;
                        if (dispatchEntry.eIssuers.hasMoreElements()) {
                            if (issuer.equals(obj2)) {
                                this.passToClient(obj, dispatchEntry);
                                return true;
                            }
                            return false;
                        }
                    }
                    this.passToClient(obj, dispatchEntry);
                    return true;
                }
                if (EventBroker.isaSubclass(issuer.getClass(), dispatchEntry.cIssuers)) {
                    this.passToClient(obj, dispatchEntry);
                    return true;
                }
                return false;
            }
        }
        
        private void passToClient(final LocalEventObject localEventObject, final DispatchEntry dispatchEntry) {
            final IEventObject event = localEventObject.event;
            dispatchEntry.eventType.getName();
            try {
                dispatchEntry.client.processEvent(event, dispatchEntry.callback, localEventObject.issuer, dispatchEntry.client, localEventObject.expedite);
            }
            catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        
        void printRecipientCount(final IEventObject eventObject, final int i) {
            try {
                this.eb.printMess(5, "There are " + i + " recipients for event " + eventObject.classDef());
            }
            catch (Throwable t) {
                Tools.px(t);
            }
        }
    }
    
    class EventScreen
    {
        Vector eventScreen;
        String dummy;
        
        EventScreen() {
            this.eventScreen = new Vector();
            this.dummy = "";
        }
        
        void add(final Class clazz) {
            if (this.eventScreen.contains(clazz)) {
                return;
            }
            this.eventScreen.addElement(clazz);
        }
        
        boolean ok(final Class clazz) {
            final Enumeration<Class> elements = this.eventScreen.elements();
            while (elements.hasMoreElements()) {
                if (Tools.isaSubclass(clazz, elements.nextElement())) {
                    return true;
                }
            }
            return false;
        }
    }
    
    class LocalEventObject
    {
        public IEventObject event;
        public Class type;
        public Object issuer;
        public boolean expedite;
        
        LocalEventObject(final IEventObject event, final Class type, final Object issuer, final boolean expedite) {
            this.event = event;
            this.type = type;
            this.issuer = issuer;
            this.expedite = expedite;
        }
    }
    
    class TrackerThread extends Thread
    {
        boolean stop;
        
        TrackerThread() {
            this.stop = false;
        }
    }
    
    class Monitor extends TrackerThread
    {
        IPingable target;
        EventBroker eb;
        int waitInterval;
        int timeoutInterval;
        IEventInterestObject eieio;
        
        Monitor(final IPingable target, final IEventInterestObject eieio, final EventBroker eb, final int waitInterval, final int timeoutInterval) {
            this.target = null;
            this.eb = null;
            this.eb = eb;
            this.eieio = eieio;
            this.target = target;
            this.waitInterval = waitInterval;
            this.timeoutInterval = timeoutInterval;
            this.setName("Process tracker");
        }
        
        public void run() {
        Label_0142:
            while (true) {
                try {
                    while (!super.stop) {
                        if (!PingIt.isActive(this.target, this.timeoutInterval)) {
                            break Label_0142;
                        }
                        try {
                            Thread.sleep(this.waitInterval);
                        }
                        catch (InterruptedException ex) {}
                    }
                    final EventBroker eb = this.eb;
                    final int n = 4;
                    final EventBroker eb2 = this.eb;
                    eb.printMess(n, 7311031045082586887L, new Object[] { this });
                    return;
                }
                catch (ThreadDeath threadDeath) {
                    final EventBroker eb3 = this.eb;
                    final int n2 = 4;
                    final EventBroker eb4 = this.eb;
                    eb3.printMess(n2, 7311031045082586888L, new Object[] { this });
                    this.eb.monitors.remove(this);
                    return;
                }
                catch (Throwable t) {
                    final EventBroker eb5 = this.eb;
                    final EventBroker eb6 = this.eb;
                    eb5.printErrMess(7311031045082586889L, new Object[] { this.target });
                    this.eb.printExcp(t, "Error whilst running Monitor thread.");
                    continue;
                }
                break;
            }
            final EventBroker eb7 = this.eb;
            final int n3 = 1;
            final EventBroker eb8 = this.eb;
            eb7.printMess(n3, 7311031045082586890L, new Object[] { this.target });
            this.eb.monitors.remove(this);
            try {
                this.eb.postEvent(new DisconnectEvent(this.target, this.eieio));
            }
            catch (RemoteException ex2) {}
        }
    }
    
    class Locator extends TrackerThread
    {
        String name;
        EventBroker eb;
        int waitInterval;
        IEventInterestObject eieio;
        
        Locator(final String name, final RegistryManager regMan, final IEventInterestObject eieio, final EventBroker eb, final int waitInterval) {
            this.name = null;
            this.eb = null;
            this.eb = eb;
            EventBroker.regMan = regMan;
            this.name = name;
            this.waitInterval = waitInterval;
            this.eieio = eieio;
            this.setName("Process locator");
        }
        
        public void run() {
            Remote lookup;
            while (true) {
                try {
                    while (!super.stop) {
                        try {
                            lookup = EventBroker.regMan.lookup(this.name);
                            final EventBroker eb = this.eb;
                            final int n = 1;
                            final EventBroker eb2 = this.eb;
                            eb.printMess(n, 7311031045082586893L, new Object[] { lookup });
                        }
                        catch (NotBoundException ex) {
                            try {
                                Thread.sleep(this.waitInterval);
                            }
                            catch (InterruptedException ex2) {}
                        }
                    }
                    final EventBroker eb3 = this.eb;
                    final int n2 = 4;
                    final EventBroker eb4 = this.eb;
                    eb3.printMess(n2, 7311031045082586891L, new Object[] { this });
                    return;
                }
                catch (ThreadDeath threadDeath) {
                    final EventBroker eb5 = this.eb;
                    final int n3 = 4;
                    final EventBroker eb6 = this.eb;
                    eb5.printMess(n3, 7311031045082586892L, new Object[] { this });
                    this.eb.locators.remove(this);
                    return;
                }
                catch (Throwable t) {
                    this.eb.printExcp(t, "Error whilst running Locator thread.");
                    continue;
                }
                break;
            }
            this.eb.locators.remove(this);
            try {
                this.eb.postEvent(new LocateEvent(lookup, this.eieio));
            }
            catch (RemoteException ex3) {}
        }
    }
    
    class ThreadTracker
    {
        IEventInterestObject eieio;
        Thread thread;
        
        ThreadTracker(final Thread thread, final IEventInterestObject eieio) {
            this.thread = thread;
            this.eieio = eieio;
        }
    }
    
    class ThreadTrackerVector extends Vector
    {
        IEventInterestObject remove(final Thread thread) {
            int index = -1;
            final Enumeration<ThreadTracker> elements = (Enumeration<ThreadTracker>)this.elements();
            while (elements.hasMoreElements()) {
                ++index;
                final ThreadTracker threadTracker = elements.nextElement();
                if (threadTracker.thread == thread) {
                    this.removeElementAt(index);
                    return threadTracker.eieio;
                }
            }
            return null;
        }
        
        Thread remove(final IEventInterestObject eventInterestObject) {
            int index = -1;
            final Enumeration<ThreadTracker> elements = (Enumeration<ThreadTracker>)this.elements();
            while (elements.hasMoreElements()) {
                ++index;
                final ThreadTracker threadTracker = elements.nextElement();
                if (threadTracker.eieio == eventInterestObject) {
                    this.removeElementAt(index);
                    return threadTracker.thread;
                }
            }
            return null;
        }
        
        void eliminate(final IEventInterestObject eventInterestObject) {
            final TrackerThread trackerThread = (TrackerThread)this.remove(eventInterestObject);
            if (trackerThread != null) {
                EventBroker.this.printMess(4, 7311031045082586869L, new Object[] { trackerThread });
                trackerThread.stop = true;
            }
            try {
                eventInterestObject.getClient().terminate();
            }
            catch (RemoteException ex) {}
        }
    }
}

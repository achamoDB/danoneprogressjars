// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.ServerRuntimeException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.rmi.ConnectException;
import java.rmi.ServerException;
import com.progress.common.collections.linked.OrderedList;
import com.progress.common.collections.linked.OrderedListCursor;
import java.rmi.RemoteException;
import com.progress.common.collections.linked.QueueSynchronized;
import java.rmi.server.UnicastRemoteObject;

class Client extends UnicastRemoteObject implements IClient
{
    QueueSynchronized outBox;
    EventBroker broker;
    Messenger[] messengers;
    String name;
    int threads;
    Integer startupCount;
    
    Client(final String s, final EventBroker eventBroker) throws RemoteException {
        this(s, eventBroker, 1);
    }
    
    protected Client(final String name, final EventBroker broker, final int n) throws RemoteException {
        this.outBox = new QueueSynchronized();
        this.broker = null;
        this.messengers = null;
        this.name = "NONAME";
        this.threads = 1;
        this.startupCount = new Integer(0);
        this.broker = broker;
        this.name = name;
        this.threads = n;
        this.startupCount = new Integer(n);
    }
    
    protected void initMessengers() {
        if (this.messengers != null) {
            return;
        }
        this.messengers = new Messenger[this.threads];
        for (int i = 0; i < this.threads; ++i) {
            (this.messengers[i] = new Messenger(this)).start();
        }
        while (true) {
            synchronized (this) {
                if (this.startupCount == 0) {
                    break;
                }
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
                catch (Throwable t) {
                    this.broker.printErrMess("Error in client: %s", new Object[] { this.name });
                }
            }
        }
    }
    
    public synchronized void terminate() {
        this.outBox = null;
        this.stopThreads();
        this.messengers = null;
        this.broker.bounceClient(this);
    }
    
    public void finalize() {
        this.terminate();
    }
    
    void notifyThreads() {
        if (this.messengers == null) {
            return;
        }
        for (int i = 0; i < this.messengers.length; ++i) {
            synchronized (this.messengers[i]) {
                this.messengers[i].notify();
            }
        }
    }
    
    void stopThreads() {
        if (this.messengers == null) {
            return;
        }
        for (int i = 0; i < this.messengers.length; ++i) {
            synchronized (this.messengers[i]) {
                this.messengers[i].stopRequested = true;
                this.messengers[i].notify();
            }
        }
    }
    
    public synchronized void processEvent(final IEventObject eventObject, final IEventListener eventListener, final Object o, final IClient client, final boolean b) {
        if (this.outBox == null) {
            return;
        }
        this.initMessengers();
        final LocalEventObject localEventObject = new LocalEventObject(eventObject, eventListener, o, client);
        if (b) {
            this.outBox.insertAtHead(localEventObject);
        }
        else {
            this.outBox.insert(localEventObject);
        }
        this.notifyThreads();
    }
    
    void bounceServerObject(final Object obj) {
        try {
            final OrderedListCursor orderedListCursor = new OrderedListCursor(this.outBox);
            for (Object o = orderedListCursor.first(); o != null; o = orderedListCursor.next()) {
                if (((LocalEventObject)o).issuer.equals(obj)) {
                    orderedListCursor.removeCurrent();
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
    }
    
    class Messenger extends Thread
    {
        Client client;
        EventBroker broker;
        QueueSynchronized outBox;
        boolean stopRequested;
        public IEventObject event;
        public Object issuer;
        public IEventListener callback;
        public int threadNum;
        
        Messenger(final Client client) {
            this.client = null;
            this.broker = null;
            this.outBox = null;
            this.stopRequested = false;
            this.event = null;
            this.issuer = null;
            this.callback = null;
            this.threadNum = 0;
            this.client = client;
            this.broker = client.broker;
            this.outBox = client.outBox;
        }
        
        public void run() {
            if (this.outBox == null) {
                return;
            }
            LocalEventObject localEventObject = null;
            synchronized (this.client) {
                this.client.startupCount = new Integer(this.client.startupCount - 1);
                this.client.notify();
            }
            while (!this.stopRequested) {
                synchronized (this) {
                    if ((localEventObject = (LocalEventObject)this.outBox.extract()) == null) {
                        try {
                            this.wait();
                        }
                        catch (InterruptedException ex6) {}
                        catch (Throwable t2) {
                            this.broker.printErrMess("Error in messenger for client: %s", new Object[] { this.client.name });
                        }
                    }
                }
                if (this.stopRequested) {
                    return;
                }
                while (true) {
                    if (localEventObject == null) {
                        localEventObject = (LocalEventObject)this.outBox.extract();
                    }
                    if (localEventObject == null) {
                        break;
                    }
                    this.event = localEventObject.event;
                    this.issuer = localEventObject.issuer;
                    this.callback = localEventObject.callback;
                    localEventObject = null;
                    int n = 0;
                    Object obj = null;
                    this.broker.printMess(1, "Messenger for client " + this.client.name + ": Calling processEvent for event: " + this.event);
                    int i = 0;
                    while (i < 3) {
                        Label_0422: {
                            Label_0427: {
                                try {
                                    this.callback.processEvent(this.event);
                                }
                                catch (ServerException ex) {
                                    n = 1;
                                    obj = ex;
                                    final EventBroker broker = this.broker;
                                    final EventBroker broker2 = this.broker;
                                    broker.printErrMess(7311031045082587016L);
                                    break;
                                }
                                catch (ConnectException ex2) {
                                    n = 2;
                                    obj = ex2;
                                    final EventBroker broker3 = this.broker;
                                    final EventBroker broker4 = this.broker;
                                    broker3.printErrMess(7311031045082587017L);
                                    break Label_0427;
                                }
                                catch (UnmarshalException ex3) {
                                    n = 3;
                                    obj = ex3;
                                    final EventBroker broker5 = this.broker;
                                    final EventBroker broker6 = this.broker;
                                    broker5.printErrMess(7311031045082587017L);
                                    break Label_0427;
                                }
                                catch (MarshalException ex4) {
                                    n = 4;
                                    obj = ex4;
                                    final EventBroker broker7 = this.broker;
                                    final EventBroker broker8 = this.broker;
                                    broker7.printErrMess(7311031045082587017L);
                                    break Label_0427;
                                }
                                catch (ServerRuntimeException ex5) {
                                    n = 5;
                                    obj = ex5;
                                    this.broker.printExcp(ex5, "Exception thrown in client handler.");
                                    break Label_0427;
                                }
                                catch (Throwable t) {
                                    n = 6;
                                    obj = t;
                                    final EventBroker broker9 = this.broker;
                                    final EventBroker broker10 = this.broker;
                                    broker9.printErrMess(7311031045082587018L);
                                    this.broker.printExcp(t, "EventManager can't deliver event to client because of unexpected exception.");
                                    break;
                                }
                                break Label_0422;
                            }
                            ++i;
                            continue;
                        }
                        n = 0;
                        break;
                    }
                    if (n > 0) {
                        try {
                            this.broker.printErrMess("   Exeception = " + obj);
                        }
                        catch (Throwable t3) {
                            this.broker.printErrMess("   11111");
                        }
                        try {
                            this.broker.printErrMess("   Event = " + this.event);
                        }
                        catch (Throwable t4) {
                            this.broker.printErrMess("   11111");
                        }
                        try {
                            this.broker.printErrMess("   From = " + this.event.issuer());
                        }
                        catch (Throwable t5) {
                            this.broker.printErrMess("   22222");
                        }
                        try {
                            this.broker.printErrMess("   To = " + this.callback);
                        }
                        catch (Throwable t6) {
                            this.broker.printErrMess("   33333");
                        }
                    }
                    if (n <= 0) {
                        continue;
                    }
                    Client.this.bounceServerObject(this.issuer);
                }
            }
        }
    }
    
    class LocalEventObject
    {
        public IEventObject event;
        public IEventListener callback;
        public Object issuer;
        public IClient client;
        
        LocalEventObject(final IEventObject event, final IEventListener callback, final Object issuer, final IClient client) {
            this.event = event;
            this.callback = callback;
            this.issuer = issuer;
            this.client = client;
        }
    }
}

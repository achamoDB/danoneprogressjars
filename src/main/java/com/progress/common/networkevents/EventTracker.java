// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.networkevents;

import java.rmi.RemoteException;
import com.progress.common.log.Excp;
import com.progress.common.util.Typing;
import java.rmi.server.RemoteStub;
import java.io.PrintStream;

public class EventTracker extends EventListener
{
    IEventInterestObject eieio;
    PrintStream printStream;
    
    public EventTracker(final IEventBroker eventBroker, final IEventStream eventStream, final PrintStream printStream, final Class clazz) throws XEventException {
        this(eventBroker, eventStream, printStream, clazz, null);
    }
    
    public EventTracker(final IEventBroker eventBroker, final IEventStream eventStream, final PrintStream printStream, final Class clazz, final RemoteStub remoteStub) throws XEventException {
        this.eieio = null;
        this.printStream = null;
        if (!Typing.subtype(clazz, EventObject.class)) {
            throw new XEventException();
        }
        this.printStream = printStream;
        try {
            this.eieio = eventBroker.expressInterest(clazz, this, remoteStub, eventStream);
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
    }
    
    public void cancel() {
        try {
            if (this.eieio != null) {
                this.eieio.revokeInterest();
                this.eieio = null;
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
    }
    
    public void finalize() {
        this.cancel();
    }
    
    public void processEvent(final IEventObject eventObject) {
        try {
            this.printStream.println(eventObject.description());
        }
        catch (RemoteException ex) {}
    }
}

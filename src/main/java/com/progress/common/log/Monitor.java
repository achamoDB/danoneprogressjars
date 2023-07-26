// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.util.Enumeration;
import java.util.Vector;
import com.progress.common.networkevents.IEventObject;
import com.progress.common.networkevents.IEventInterestObject;
import com.progress.common.networkevents.IEventListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.rmi.Remote;

class Monitor extends Thread implements Remote
{
    AbstractLogFileReader reader;
    String descriptor;
    LogFileRef handle;
    Hashtable users;
    boolean stopped;
    
    Monitor(final AbstractLogFileReader reader, final String descriptor, final LogFileRef logFileRef) throws Exception {
        this.descriptor = null;
        this.handle = null;
        this.users = new Hashtable();
        this.stopped = false;
        try {
            UnicastRemoteObject.exportObject(this);
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
        this.reader = reader;
        this.descriptor = descriptor;
        (this.handle = (LogFileRef)logFileRef.clone()).setNextReadPosition(this.handle.getFile().length());
        this.handle.setLastReadPosition(-1L);
    }
    
    void addUser(final IFileRef key, final IEventListener eventListener) throws RemoteException {
        this.users.put(key, this.reader.eventBroker.expressInterestLocal(ENewFileData.class, eventListener, this, this.reader.eventStream));
    }
    
    void removeUser(final IFileRef fileRef) throws RemoteException {
        final IEventInterestObject eventInterestObject;
        if ((eventInterestObject = this.users.get(fileRef)) != null) {
            this.users.remove(fileRef);
            eventInterestObject.revokeInterest();
        }
    }
    
    public void run() {
        try {
            this.setName("File update monitor for file: " + this.handle.getFilePathName());
            while (true) {
                Thread.sleep(4000L);
                synchronized (this.reader) {
                    if (this.done()) {
                        break;
                    }
                    final Vector newFileData = this.reader.getNewFileData(this.handle, 200);
                    if (this.done()) {
                        break;
                    }
                    if (newFileData != null) {
                        this.reader.eventBroker.postEvent(new ENewFileData(this, newFileData));
                    }
                    if (this.done()) {
                        break;
                    }
                    continue;
                }
            }
        }
        catch (InterruptedException ex2) {
            this.stopped = true;
            this.done();
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
    }
    
    protected boolean done() {
        try {
            if (this.stopped || this.users.isEmpty()) {
                this.reader.removeMonitor(this.descriptor);
                final Enumeration<IEventInterestObject> elements = this.users.elements();
                while (elements.hasMoreElements()) {
                    elements.nextElement().revokeInterest();
                }
                return true;
            }
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
        return false;
    }
    
    void stopThread() {
        this.stopped = true;
    }
}

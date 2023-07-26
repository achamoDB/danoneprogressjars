// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.util.Vector;
import java.rmi.RemoteException;
import java.io.File;
import java.rmi.server.UnicastRemoteObject;

class LogFileRef extends UnicastRemoteObject implements IFileRef, Cloneable
{
    File file;
    long nextReadPosition;
    long lastReadPosition;
    boolean isForward;
    Monitor monitor;
    AbstractLogFileReader reader;
    
    public LogFileRef(final AbstractLogFileReader reader) throws RemoteException {
        this.file = null;
        this.nextReadPosition = 0L;
        this.lastReadPosition = 0L;
        this.isForward = false;
        this.monitor = null;
        this.reader = null;
        this.file = null;
        this.nextReadPosition = 0L;
        this.reader = reader;
    }
    
    public boolean isFoward() throws RemoteException {
        return this.isForward;
    }
    
    public boolean isBackward() throws RemoteException {
        return !this.isForward;
    }
    
    public void setForward() throws RemoteException {
        this.isForward = true;
    }
    
    public void setBackward() throws RemoteException {
        this.isForward = false;
    }
    
    public File getFile() throws RemoteException {
        return this.file;
    }
    
    public void setFile(final File file) throws RemoteException {
        this.file = file;
    }
    
    public long getLastReadPosition() throws RemoteException {
        return this.lastReadPosition;
    }
    
    public void setLastReadPosition(final long lastReadPosition) throws RemoteException {
        this.lastReadPosition = lastReadPosition;
    }
    
    public long getNextReadPosition() throws RemoteException {
        return this.nextReadPosition;
    }
    
    public void setNextReadPosition(final long nextReadPosition) throws RemoteException {
        this.nextReadPosition = nextReadPosition;
    }
    
    public Monitor getMonitor() throws RemoteException {
        return this.monitor;
    }
    
    public void setMonitor(final Monitor monitor) throws RemoteException {
        this.monitor = monitor;
    }
    
    public String getFileLength() throws RemoteException {
        return this.reader.getFileLength(this);
    }
    
    public String getFilePathName() throws RemoteException {
        return this.reader.getFilePathName(this);
    }
    
    public void closeFile() throws RemoteException {
        this.reader.closeFile(this);
    }
    
    public Vector getFileData(final int n) throws RemoteException {
        return this.reader.getFileData(this, n);
    }
    
    public void setFilter(final String s) throws RemoteException {
        this.reader.setFilter(this, s);
    }
    
    public void finalize() {
        try {
            this.reader.closeFile(this);
        }
        catch (RemoteException ex) {
            Excp.print(ex);
        }
    }
}

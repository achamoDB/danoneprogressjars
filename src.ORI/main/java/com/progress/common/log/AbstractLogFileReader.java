// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.text.NumberFormat;
import java.io.EOFException;
import java.util.Vector;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import com.progress.common.networkevents.IEventListener;
import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.EventBroker;

public abstract class AbstractLogFileReader implements IRemoteFile
{
    protected EventBroker eventBroker;
    protected IEventStream eventStream;
    Hashtable monitors;
    
    public AbstractLogFileReader(final EventBroker eventBroker, final IEventStream eventStream) throws RemoteException {
        this.eventBroker = null;
        this.eventStream = null;
        this.monitors = new Hashtable();
        UnicastRemoteObject.exportObject(this);
        this.eventBroker = eventBroker;
        this.eventStream = eventStream;
    }
    
    public synchronized IFileRef openFile(final String s) throws RemoteException {
        return this.openFile(s, null);
    }
    
    public synchronized IFileRef openFile(final String s, final IEventListener eventListener) throws RemoteException {
        return this.openFile(s, false, eventListener);
    }
    
    public synchronized IFileRef openFile(String key, final boolean isForward, final IEventListener eventListener) throws RemoteException {
        final LogFileRef logFileRef = new LogFileRef(this);
        logFileRef.isForward = isForward;
        try {
            if (key.length() == 0) {
                key = "admserv.log";
            }
            logFileRef.setFile(new File(key));
            long n;
            if (eventListener != null) {
                Monitor monitor = this.monitors.get(key);
                if (monitor == null) {
                    (monitor = new Monitor(this, key, logFileRef)).start();
                    this.monitors.put(key, monitor);
                }
                logFileRef.setMonitor(monitor);
                monitor.addUser(logFileRef, eventListener);
                n = monitor.handle.getNextReadPosition();
            }
            else {
                n = logFileRef.getFile().length();
            }
            if (isForward) {
                logFileRef.setNextReadPosition(0L);
                logFileRef.setLastReadPosition(n);
            }
            else {
                logFileRef.setNextReadPosition(n);
                logFileRef.setLastReadPosition(0L);
            }
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        return logFileRef;
    }
    
    void removeMonitor(final String key) {
        this.monitors.remove(key);
    }
    
    public synchronized void closeFile(final IFileRef fileRef) throws RemoteException {
        final Monitor monitor;
        if ((monitor = fileRef.getMonitor()) != null) {
            monitor.removeUser(fileRef);
        }
        fileRef.setMonitor(null);
    }
    
    protected RandomAccessFile getAccessToFile(final IFileRef fileRef) {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(fileRef.getFile(), "r");
        }
        catch (IOException ex2) {}
        catch (Exception ex) {
            Excp.print(ex);
        }
        return randomAccessFile;
    }
    
    public synchronized Vector getFileData(final IFileRef fileRef, final int n) {
        final Vector vector = new Vector();
        RandomAccessFile accessToFile = null;
        try {
            accessToFile = this.getAccessToFile(fileRef);
            if (accessToFile != null) {
                if (fileRef.isBackward()) {
                    this.getFileDataReversed(fileRef, accessToFile, n, 0, vector);
                }
                else {
                    fileRef.setNextReadPosition(this.getFileDataForward(fileRef, accessToFile, fileRef.getNextReadPosition(), fileRef.getLastReadPosition(), n, vector));
                }
            }
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        try {
            if (accessToFile != null) {
                accessToFile.close();
            }
        }
        catch (IOException ex2) {
            Excp.print(ex2);
        }
        return vector.isEmpty() ? null : vector;
    }
    
    Vector getNewFileData(final IFileRef fileRef, final int n) {
        final Vector vector = new Vector();
        RandomAccessFile accessToFile = null;
        try {
            accessToFile = this.getAccessToFile(fileRef);
            if (accessToFile != null) {
                fileRef.setNextReadPosition(this.getFileDataForward(fileRef, accessToFile, fileRef.getNextReadPosition(), -1L, n, vector));
            }
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        try {
            if (accessToFile != null) {
                accessToFile.close();
            }
        }
        catch (IOException ex2) {
            Excp.print(ex2);
        }
        return vector.isEmpty() ? null : vector;
    }
    
    long getFileDataForward(final IFileRef fileRef, final RandomAccessFile randomAccessFile, final long pos, final long n, final int n2, final Vector vector) {
        int n3 = 0;
        try {
            try {
                randomAccessFile.seek(pos);
            }
            catch (Exception ex) {
                Excp.print(ex);
            }
            while (randomAccessFile.getFilePointer() != n) {
                if (n3 < n2) {
                    final String encodedLine = this.readEncodedLine(randomAccessFile);
                    if (encodedLine != "") {
                        vector.addElement(encodedLine.replace('\r', ' ').replace('\n', ' '));
                        ++n3;
                        continue;
                    }
                }
                return randomAccessFile.getFilePointer();
            }
            return randomAccessFile.getFilePointer();
        }
        catch (Exception ex2) {
            Excp.print(ex2);
            return 0L;
        }
    }
    
    Vector getFileDataReversed(final IFileRef fileRef, final RandomAccessFile randomAccessFile, final int n, int n2, final Vector vector) {
        try {
            long length;
            if (n != -1) {
                length = n * 132;
            }
            else {
                length = fileRef.getFile().length();
            }
            if (fileRef != null) {
                final long nextReadPosition = fileRef.getNextReadPosition();
                long n3;
                while (true) {
                    n3 = nextReadPosition - length;
                    if (n3 < 0L) {
                        n3 = 0L;
                    }
                    randomAccessFile.seek(n3);
                    if (n3 == 0L) {
                        break;
                    }
                    if (this.readEncodedLine(randomAccessFile) == "") {
                        break;
                    }
                    if (randomAccessFile.getFilePointer() < nextReadPosition) {
                        break;
                    }
                    if (n3 == 0L) {
                        fileRef.setNextReadPosition(0L);
                        return vector;
                    }
                    length += 132L;
                }
                final int index = n2;
                while (randomAccessFile.getFilePointer() < nextReadPosition) {
                    final String encodedLine = this.readEncodedLine(randomAccessFile);
                    if (encodedLine == "") {
                        return vector;
                    }
                    vector.insertElementAt(encodedLine.replace('\r', ' ').replace('\n', ' '), index);
                    ++n2;
                }
                fileRef.setNextReadPosition(n3);
                if (n3 == 0L) {
                    return vector;
                }
                if (n2 < n) {
                    return this.getFileDataReversed(fileRef, randomAccessFile, n, n2, vector);
                }
            }
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        return vector;
    }
    
    String readEncodedLine(final RandomAccessFile randomAccessFile) {
        String string = "";
        try {
            int i;
            do {
                i = randomAccessFile.readUnsignedByte();
                string += new String(new byte[] { (byte)i });
            } while (i != 10);
        }
        catch (EOFException ex2) {}
        catch (Exception ex) {
            Excp.print(ex);
        }
        return string;
    }
    
    String getFileLength(final IFileRef fileRef) {
        long length = 0L;
        try {
            if (fileRef != null && fileRef.getFile() != null) {
                try {
                    length = fileRef.getFile().length();
                }
                catch (Exception ex) {
                    Excp.print(ex);
                }
            }
        }
        catch (Exception ex2) {
            Excp.print(ex2);
        }
        return NumberFormat.getInstance().format(length);
    }
    
    String getFilePathName(final IFileRef fileRef) {
        String canonicalPath = null;
        try {
            if (fileRef != null && fileRef.getFile() != null) {
                canonicalPath = fileRef.getFile().getCanonicalPath();
            }
        }
        catch (Exception ex) {
            Excp.print(ex);
        }
        return canonicalPath;
    }
    
    public synchronized void setFilter(final IFileRef fileRef, final String s) {
    }
}

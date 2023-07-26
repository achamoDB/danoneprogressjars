// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.RemoteException;
import java.io.File;

public interface IFileRef extends IRemoteFileStream
{
    File getFile() throws RemoteException;
    
    void setFile(final File p0) throws RemoteException;
    
    long getNextReadPosition() throws RemoteException;
    
    void setNextReadPosition(final long p0) throws RemoteException;
    
    long getLastReadPosition() throws RemoteException;
    
    void setLastReadPosition(final long p0) throws RemoteException;
    
    Monitor getMonitor() throws RemoteException;
    
    void setMonitor(final Monitor p0) throws RemoteException;
    
    boolean isFoward() throws RemoteException;
    
    boolean isBackward() throws RemoteException;
    
    void setForward() throws RemoteException;
    
    void setBackward() throws RemoteException;
}

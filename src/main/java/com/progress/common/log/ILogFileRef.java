// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.RemoteException;
import java.io.File;
import java.rmi.Remote;

public interface ILogFileRef extends Remote
{
    File getFile() throws RemoteException;
    
    void setFile(final File p0) throws RemoteException;
    
    boolean getEOF() throws RemoteException;
    
    void setEOF(final boolean p0) throws RemoteException;
    
    long getFilePos() throws RemoteException;
    
    void setFilePos(final long p0) throws RemoteException;
    
    long getLastPos() throws RemoteException;
    
    void setLastPos(final long p0) throws RemoteException;
}

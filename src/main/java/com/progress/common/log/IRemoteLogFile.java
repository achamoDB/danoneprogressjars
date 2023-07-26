// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.util.Vector;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IRemoteLogFile extends Remote
{
    ILogFileRef openLogFile(final String p0) throws RemoteException;
    
    void closeLogFile(final ILogFileRef p0) throws RemoteException;
    
    Vector getLogFileData(final ILogFileRef p0, final int p1) throws RemoteException;
    
    String getLogFileLength(final ILogFileRef p0) throws RemoteException;
    
    String getLogFilePathName(final ILogFileRef p0) throws RemoteException;
    
    void setFilter(final ILogFileRef p0, final String p1) throws RemoteException;
}

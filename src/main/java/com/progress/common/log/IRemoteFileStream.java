// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.util.Vector;
import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IRemoteFileStream extends Remote
{
    String getFileLength() throws RemoteException;
    
    String getFilePathName() throws RemoteException;
    
    void closeFile() throws RemoteException;
    
    Vector getFileData(final int p0) throws RemoteException;
    
    void setFilter(final String p0) throws RemoteException;
}

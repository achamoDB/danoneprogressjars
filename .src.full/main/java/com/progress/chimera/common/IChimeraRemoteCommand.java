// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import java.util.Hashtable;
import java.rmi.RemoteException;
import java.util.Enumeration;

public interface IChimeraRemoteCommand extends IChimeraRemoteObject
{
    Enumeration getChildren() throws RemoteException;
    
    String getDisplayName() throws RemoteException;
    
    void setSystemInput(final String p0) throws RemoteException;
    
    int runIt(final String[] p0) throws RemoteException;
    
    String getSystemOutput() throws RemoteException;
    
    Hashtable getStructuredSystemOutput() throws RemoteException;
    
    String getSystemError() throws RemoteException;
}

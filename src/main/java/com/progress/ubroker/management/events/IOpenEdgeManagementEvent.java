// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;
import java.rmi.Remote;

public interface IOpenEdgeManagementEvent extends Remote
{
    COpenEdgeManagementContent getContent() throws RemoteException;
}

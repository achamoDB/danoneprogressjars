// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.RemoteException;
import java.util.Vector;
import com.progress.common.networkevents.IEventObject;

public interface IEFileData extends IEventObject
{
    Vector getLines() throws RemoteException;
}

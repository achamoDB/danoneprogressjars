// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.rmi.RemoteException;
import com.progress.common.util.IEClientSpecificEvent;
import com.progress.common.networkevents.EventObject;

public class EPropertyEvent extends EventObject implements IEClientSpecificEvent
{
    Object guiID;
    
    EPropertyEvent(final Object o, final Object guiID) throws RemoteException {
        super(o);
        this.guiID = null;
        this.guiID = guiID;
    }
    
    public Object guiID() throws RemoteException {
        return this.guiID;
    }
}

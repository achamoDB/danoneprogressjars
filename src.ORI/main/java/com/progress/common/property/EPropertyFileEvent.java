// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.rmi.RemoteException;

public class EPropertyFileEvent extends EPropertyEvent implements IEPropertyFileEvent
{
    String path;
    
    public EPropertyFileEvent(final Object o, final Object o2, final String path) throws RemoteException {
        super(o, o2);
        this.path = null;
        this.path = path;
    }
    
    public String getPropertyFileName() {
        return this.path;
    }
}

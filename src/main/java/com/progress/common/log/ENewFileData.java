// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.RemoteException;
import java.util.Vector;
import com.progress.common.networkevents.EventObject;

class ENewFileData extends EventObject implements IEFileData
{
    Vector linesOfData;
    
    ENewFileData(final Monitor monitor, final Vector linesOfData) throws RemoteException {
        super(monitor);
        this.linesOfData = linesOfData;
    }
    
    public Vector getLines() {
        return this.linesOfData;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.log;

import java.rmi.RemoteException;
import com.progress.common.networkevents.IEventStream;
import com.progress.common.networkevents.EventBroker;

public class RemoteLogFileReader extends AbstractLogFileReader
{
    public RemoteLogFileReader(final EventBroker eventBroker, final IEventStream eventStream) throws RemoteException {
        super(eventBroker, eventStream);
    }
}

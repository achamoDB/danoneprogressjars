// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.management.events;

import java.rmi.RemoteException;

public class EShutdownEvent extends EOpenEdgeManagementEvent
{
    public EShutdownEvent(final Object o, final COpenEdgeManagementContent cOpenEdgeManagementContent) throws RemoteException {
        super(o, cOpenEdgeManagementContent);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.RemoteException;
import com.progress.common.log.ProLog;
import com.sonicsw.mf.framework.container.ContainerImpl;
import java.util.HashSet;
import com.progress.common.rmiregistry.ICallable;

public class AdminServerRemote implements ICallable
{
    String[] m_containerArgs;
    HashSet m_sharedClasses;
    
    public AdminServerRemote(final String[] containerArgs, final HashSet sharedClasses) {
        this.m_containerArgs = containerArgs;
        this.m_sharedClasses = sharedClasses;
    }
    
    public Object call(final Object o) throws RemoteException {
        ContainerImpl containerImpl;
        try {
            containerImpl = new ContainerImpl(this.m_containerArgs, this.m_sharedClasses);
        }
        catch (Exception ex) {
            ProLog.logdErr("Fathom", 1, ex.getMessage());
            containerImpl = null;
        }
        return containerImpl;
    }
}

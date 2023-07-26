// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.Remote;

public interface IServerPlugin
{
    boolean init(final int p0, final IAdministrationServer p1, final String[] p2);
    
    void shutdown();
    
    Remote getRemoteObject(final String p0, final String p1);
}

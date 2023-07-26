// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.adminserver;

import java.rmi.Remote;

public abstract class AbstractServerPlugin implements IServerPlugin
{
    protected int m_myIndex;
    protected IAdministrationServer m_administrationServer;
    protected String[] m_args;
    
    public AbstractServerPlugin() {
        this.m_myIndex = -1;
        this.m_administrationServer = null;
        this.m_args = null;
    }
    
    public boolean init(final int myIndex, final IAdministrationServer administrationServer, final String[] args) {
        this.m_myIndex = myIndex;
        this.m_administrationServer = administrationServer;
        this.m_args = args;
        return true;
    }
    
    public void shutdown() {
    }
    
    public abstract Remote getRemoteObject(final String p0, final String p1);
}

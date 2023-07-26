// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.util.Hashtable;
import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import com.progress.chimera.common.ChimeraRemoteObject;

public class JARmiComponent extends ChimeraRemoteObject implements IJARmiComponent
{
    JARmiComponent m_self;
    JAPlugIn m_jaPlugIn;
    
    public JARmiComponent(final JAPlugIn jaPlugIn) throws RemoteException {
        this.m_self = null;
        this.m_jaPlugIn = null;
        this.m_jaPlugIn = jaPlugIn;
        this.m_self = this;
    }
    
    public IJAPlugIn getPlugIn() {
        return this.m_jaPlugIn;
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
    
    public IJARmiComponent evThis() {
        return (IJARmiComponent)this.remoteStub();
    }
    
    public Hashtable getStatistics(final String s, final String[] array) throws RemoteException {
        Hashtable statistics = new Hashtable();
        final JAPluginComponent jaPluginComponent = (JAPluginComponent)this.m_jaPlugIn.getPluginComponent();
        if (jaPluginComponent != null) {
            statistics = jaPluginComponent.getStatistics(s, array);
        }
        return statistics;
    }
    
    public Object[] getTableSchema(final String s, final String s2) throws RemoteException {
        Object[] tableSchema = new Object[0];
        final JAPluginComponent jaPluginComponent = (JAPluginComponent)this.m_jaPlugIn.getPluginComponent();
        if (jaPluginComponent != null) {
            tableSchema = jaPluginComponent.getTableSchema(s, s2);
        }
        return tableSchema;
    }
}

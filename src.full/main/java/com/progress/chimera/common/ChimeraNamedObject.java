// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import java.rmi.RemoteException;

public abstract class ChimeraNamedObject extends ChimeraRemoteObject
{
    private String name;
    
    public ChimeraNamedObject(final String s) throws RemoteException {
        this.name = null;
        this.name = s;
        if (s != null) {
            final NameContext nameContext = this.getNameContext();
            if (nameContext != null) {
                nameContext.put(s, this);
            }
        }
    }
    
    protected abstract NameContext getNameContext();
    
    protected void delete() {
        if (this.name != null) {
            final NameContext nameContext = this.getNameContext();
            if (nameContext != null) {
                nameContext.remove(this.name);
            }
            this.name = null;
        }
    }
    
    public String name() {
        return this.name;
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import java.util.Enumeration;
import java.rmi.RemoteException;

public abstract class ChimeraHierarchyObject extends ChimeraNamedObject implements IChimeraHierarchy
{
    protected ChimeraHierarchyObject(final String s) throws RemoteException {
        super(s);
    }
    
    public abstract Enumeration getChildren() throws RemoteException;
    
    public String getMMCClientClass() {
        return null;
    }
    
    public String getDisplayName() {
        return this.name();
    }
}

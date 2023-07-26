// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.juniper.admin;

import java.rmi.server.RemoteStub;
import java.rmi.RemoteException;
import java.util.Vector;
import com.progress.chimera.common.ChimeraHierarchyCategory;

class JAAbstractCategory extends ChimeraHierarchyCategory implements IJARemoteObject
{
    IJARemoteObject parent;
    
    public JAAbstractCategory(final IJARemoteObject parent, final String s, final Vector vector) throws RemoteException {
        super(s, vector);
        this.parent = parent;
    }
    
    public IJAPlugIn getPlugIn() throws RemoteException {
        return this.parent.getPlugIn();
    }
    
    public RemoteStub remoteStub() {
        return this.stub();
    }
}

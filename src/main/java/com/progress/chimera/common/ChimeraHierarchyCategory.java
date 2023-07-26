// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.util.Vector;

public class ChimeraHierarchyCategory extends ChimeraHierarchyObject
{
    protected Vector m_children;
    protected static NameContext nameTable;
    
    public ChimeraHierarchyCategory(final String s, final Vector children) throws RemoteException {
        super(s);
        this.m_children = null;
        this.m_children = children;
    }
    
    protected NameContext getNameContext() {
        return ChimeraHierarchyCategory.nameTable;
    }
    
    public Enumeration getChildren() throws RemoteException {
        try {
            if (this.m_children != null) {
                return new SerializableEnumeration(this.m_children);
            }
            return null;
        }
        catch (Throwable t) {
            Tools.px(t);
            throw new XRemoteRequestFailed(t);
        }
    }
    
    static {
        ChimeraHierarchyCategory.nameTable = new NameContext();
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import com.progress.chimera.util.SerializableEnumeration;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.util.Vector;

public class Server extends ChimeraHierarchyObject
{
    protected static NameContext nameTable;
    protected Vector databases;
    
    protected NameContext getNameContext() {
        return Server.nameTable;
    }
    
    protected Server(final String s) throws RemoteException {
        super(s);
        this.databases = new Vector();
    }
    
    public Enumeration getChildren() throws RemoteException {
        try {
            return new SerializableEnumeration(this.databases);
        }
        catch (Throwable t) {
            Tools.px(t, "### Exception getting children. ###");
            throw new XRemoteRequestFailed(t);
        }
    }
    
    public static Server find(final String key) {
        return Server.nameTable.get(key);
    }
    
    static {
        Server.nameTable = new NameContext();
    }
}

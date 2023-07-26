// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.common;

import java.util.Enumeration;
import java.rmi.RemoteException;
import com.progress.common.guiproperties.PropertyValueSet;
import com.progress.common.guiproperties.PropertySetDefinition;

public class Database extends ChimeraHierarchyObject
{
    protected String hostName;
    protected String physicalName;
    protected PropertySetDefinition propList;
    private PropertyValueSet defaultProfile;
    private PropertyValueSet profile;
    private static Class thisClass;
    protected static NameContext nameTable;
    
    public static Class thisClass() {
        return Database.thisClass;
    }
    
    protected Database(final String s, final String hostName, final String physicalName) throws RemoteException {
        super(s);
        this.hostName = null;
        this.physicalName = null;
        this.propList = null;
        this.defaultProfile = null;
        this.profile = null;
        this.physicalName = physicalName;
        this.hostName = hostName;
        this.propList = null;
    }
    
    public static Database find(final String key) {
        return Database.nameTable.get(key);
    }
    
    public Enumeration getChildren() throws RemoteException {
        return null;
    }
    
    public String physicalName() {
        return this.physicalName;
    }
    
    protected void setPhysicalName(final String physicalName) {
        this.physicalName = physicalName;
    }
    
    protected NameContext getNameContext() {
        return Database.nameTable;
    }
    
    static {
        Database.thisClass = Tools.lookupClass("com.progress.chimera.common.Database");
        Database.nameTable = new NameContext();
    }
}

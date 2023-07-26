// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.util.Hashtable;

public class ObjectTable
{
    static ObjectTable objectTable;
    private Hashtable objTable;
    private Hashtable holderTable;
    private long nextObjNum;
    private String jvmId;
    
    ObjectTable(final String jvmId) {
        this.objTable = new Hashtable();
        this.holderTable = new Hashtable();
        this.nextObjNum = 0L;
        this.jvmId = jvmId;
        ObjectTable.objectTable = this;
    }
    
    static ObjectTable getTable() {
        return ObjectTable.objectTable;
    }
    
    synchronized ObjectReference addRef(final Object o) {
        return new ObjectReference(null, 0L, 0L);
    }
    
    synchronized void deleteRef(final ObjectReference objectReference) {
    }
    
    synchronized ObjectHolder getByRef(final ObjectReference objectReference) {
        return null;
    }
    
    static ObjectReference bytesToReference(final byte[] array) {
        return null;
    }
    
    private ObjectHolder getByObject(final Object o) {
        return null;
    }
    
    private void addObjNum(final long n, final ObjectHolder objectHolder) {
    }
    
    private void addObj(final Object o, final ObjectHolder objectHolder) {
    }
    
    private long getNextObjNum() {
        return 0L;
    }
    
    static {
        ObjectTable.objectTable = null;
    }
}

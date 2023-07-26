// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.util.Hashtable;

public class ReferenceList extends Hashtable
{
    private ObjectTable objectTable;
    private byte[] initialObjectReference;
    private String jvmId;
    private Object tempxxxxx;
    
    ReferenceList(final String jvmId) {
        this.objectTable = ObjectTable.getTable();
        this.jvmId = jvmId;
    }
    
    void registerInitialReference(final Object tempxxxxx) {
        this.initialObjectReference = this.registerReference(tempxxxxx);
        this.tempxxxxx = tempxxxxx;
    }
    
    byte[] registerReference(final Object o) {
        final ObjectReference addRef = this.objectTable.addRef(o);
        final String localStringRef = addRef.getLocalStringRef();
        this.put(localStringRef, localStringRef);
        return addRef.getBytesRef();
    }
    
    byte[] getInitialreference() {
        return this.initialObjectReference;
    }
    
    ObjectHolder getByReference(final byte[] array) {
        return new ObjectHolder(this.tempxxxxx, "x", 1L);
    }
    
    void unregister(final byte[] array) {
    }
}

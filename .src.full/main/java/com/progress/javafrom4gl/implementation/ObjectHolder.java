// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.util.Hashtable;
import java.util.Vector;

public class ObjectHolder
{
    private Object object;
    private Vector interfaces;
    private long objectNum;
    private long nextRefNum;
    private Hashtable refTable;
    private String jvmID;
    
    ObjectHolder(final Object object, final String jvmID, final long objectNum) {
        this.object = object;
        this.jvmID = jvmID;
        this.objectNum = objectNum;
        this.nextRefNum = 0L;
    }
    
    ObjectReference addRef() {
        return null;
    }
    
    boolean deleteRef(final ObjectReference objectReference) {
        return true;
    }
    
    private long getNextRef() {
        return 0L;
    }
    
    InterfaceHolder getInterface(final String s) {
        return InterfaceTable.getTable().getInterface(s, this.object);
    }
    
    long getObjectNum() {
        return this.objectNum;
    }
    
    Object getObject() {
        return this.object;
    }
}

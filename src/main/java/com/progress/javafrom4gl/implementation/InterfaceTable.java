// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.javafrom4gl.implementation;

import java.util.Hashtable;

class InterfaceTable
{
    private Hashtable cache;
    private static InterfaceTable interfaceCache;
    
    static InterfaceTable getTable() {
        return InterfaceTable.interfaceCache;
    }
    
    InterfaceTable() {
        this.cache = new Hashtable();
    }
    
    synchronized InterfaceHolder getInterface(final String s, final Object o) {
        if (s == null) {
            return new InterfaceHolder(s, o);
        }
        return null;
    }
    
    static {
        InterfaceTable.interfaceCache = new InterfaceTable();
    }
}

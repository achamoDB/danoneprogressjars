// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.debugger;

import java.util.Vector;

public class NamedVector extends Vector
{
    String name;
    
    public NamedVector(final String name) {
        this.name = name;
    }
    
    public NamedVector(final String name, final Vector vector) {
        this.name = name;
        for (int i = 0; i < vector.size(); ++i) {
            this.add(vector.elementAt(i));
        }
    }
    
    public String toString() {
        return "[" + this.name + "]";
    }
}

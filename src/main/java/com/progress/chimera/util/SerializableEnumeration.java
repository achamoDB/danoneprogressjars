// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.chimera.util;

import java.util.Vector;
import java.io.Serializable;
import java.util.Enumeration;

public class SerializableEnumeration implements Enumeration, Serializable
{
    Vector m_vector;
    int m_currIx;
    
    public SerializableEnumeration(final Vector vector) {
        this.m_currIx = 0;
        this.m_vector = vector;
    }
    
    public boolean hasMoreElements() {
        return this.m_currIx < this.m_vector.size();
    }
    
    public Object nextElement() {
        return this.m_vector.elementAt(this.m_currIx++);
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.dynamicapi;

import java.io.ObjectInput;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.io.ObjectOutput;
import java.util.Hashtable;
import java.io.Externalizable;

public class ExternalHashtable implements Externalizable
{
    private transient Hashtable hashtable;
    
    public ExternalHashtable() {
        this.hashtable = new Hashtable();
    }
    
    public void writeExternal(final ObjectOutput objectOutput) throws IOException {
        final Vector<Object[]> vector = new Vector<Object[]>();
        final Enumeration<Object> keys = this.hashtable.keys();
        while (keys.hasMoreElements()) {
            final Object nextElement = keys.nextElement();
            vector.addElement(new Object[] { nextElement, this.hashtable.get(nextElement) });
        }
        objectOutput.writeObject(vector);
    }
    
    public void readExternal(final ObjectInput objectInput) throws ClassNotFoundException, IOException {
        final Vector vector = (Vector)objectInput.readObject();
        this.hashtable = new Hashtable();
        for (int i = 0; i < vector.size(); ++i) {
            final Object[] array = vector.elementAt(i);
            this.hashtable.put(array[0], array[1]);
        }
    }
    
    Object put(final Object key, final Object value) {
        return this.hashtable.put(key, value);
    }
    
    public Object get(final Object key) {
        return this.hashtable.get(key);
    }
    
    public int size() {
        return this.hashtable.size();
    }
    
    public Enumeration keys() {
        return this.hashtable.keys();
    }
    
    public Enumeration elements() {
        return this.hashtable.elements();
    }
    
    public Object remove(final Object key) {
        return this.hashtable.remove(key);
    }
}

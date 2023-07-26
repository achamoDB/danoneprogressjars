// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.util.Enumeration;
import java.util.Hashtable;

class CIHashtable extends Hashtable
{
    public CIHashtable(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    
    public CIHashtable(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public CIHashtable() {
    }
    
    public Object get(final String s) {
        return super.get(new CIString(s));
    }
    
    public Object put(final String s, final Object value) {
        return super.put(new CIString(s), value);
    }
    
    public boolean containsKey(final String s) {
        return super.containsKey(new CIString(s));
    }
    
    public Object remove(final String s) {
        return super.remove(new CIString(s));
    }
    
    public Enumeration keys() {
        return new CIHashtableEnumeration(super.keys());
    }
}

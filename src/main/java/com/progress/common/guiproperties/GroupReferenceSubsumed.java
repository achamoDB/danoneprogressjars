// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Vector;
import java.util.Enumeration;
import com.progress.common.property.PropertyTransferObject;

public class GroupReferenceSubsumed extends GroupReference implements IDatatypeModelAsDynamicOrderedDictionary
{
    PropertyTransferObject pto;
    
    GroupReferenceSubsumed() {
        this.pto = null;
        this.pto = new PropertyTransferObject();
    }
    
    GroupReferenceSubsumed(final PropertyValueSet set, final String s) {
        super(set, s);
        this.pto = null;
        this.pto = new PropertyTransferObject();
    }
    
    GroupReferenceSubsumed(final PropertyValueSet set, final String s, final String s2) throws XPropertyException {
        super(set, (s != null) ? s : set.instanceName, s2, false);
        this.pto = null;
        this.pto = super.pvs.makePropertyTransferObject(this.fullName());
    }
    
    public void setPropertyTransferObject(final PropertyTransferObject pto) {
        this.pto = pto;
    }
    
    public Enumeration keys() {
        return this.pto.keys();
    }
    
    public Enumeration values() {
        return this.pto.elements.elements();
    }
    
    public Enumeration elements() {
        final Vector<Element> vector = new Vector<Element>();
        if (this.pto != null) {
            final Enumeration keys = this.pto.keys();
            final Enumeration values = this.pto.values();
            while (keys.hasMoreElements()) {
                vector.addElement(new Element(keys.nextElement(), values.nextElement()));
            }
        }
        return vector.elements();
    }
    
    public Object get(final Object o) {
        return this.pto.get(o);
    }
    
    public Object put(final Object obj, final Object obj2) throws XPropertyException {
        if (!(obj instanceof String) || !(obj2 instanceof String)) {
            throw new XPropertyException("Invalid valid for GroupReferenceSubsumed: " + obj + "/" + obj2 + ".  Both values must be strings.");
        }
        return this.pto.put((String)obj, new String[] { (String)obj2 });
    }
    
    public void removeAll() {
        if (this.pto != null) {
            final Enumeration keys = this.pto.keys();
            while (keys.hasMoreElements()) {
                this.remove(keys.nextElement());
            }
        }
    }
    
    public Object remove(final Object o) {
        return this.pto.remove(o);
    }
    
    public int size() {
        return this.pto.size();
    }
    
    public boolean isEmpty() {
        return this.pto.isEmpty();
    }
    
    public Object toObject(final String s) throws XPropertyException {
        try {
            return new GroupReferenceSubsumed(super.pvs, s, super.prefix);
        }
        catch (XPropertyException previous) {
            final XPropertyException ex = new XPropertyException("Can't set value of property: " + this.fullName());
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    class Element implements IDatatypeModelAsDynamicOrderedDictionary.Element
    {
        Object key;
        Object value;
        
        Element(final Object key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        public Object key() {
            return this.key;
        }
        
        public Object value() {
            return this.value;
        }
    }
}

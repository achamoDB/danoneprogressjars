// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;
import java.util.Vector;

class VectorSurrogate extends DatatypeMultiple implements IDatatypeModelAsDynamicList
{
    Object makeInstance() {
        return new Vector();
    }
    
    protected void setBaseValueField(final Object o) throws XPropertyValueIsNotValid {
        if (!(o instanceof Vector)) {
            throw new XPropertyValueIsNotValid("" + o);
        }
        super.setBaseValueField(o);
    }
    
    public int findIndex(final Object obj) throws XPropertyException {
        int n = 0;
        final Enumeration<Object> elements = (Enumeration<Object>)((Vector)super.value).elements();
        while (elements.hasMoreElements()) {
            if (elements.nextElement().equals(obj)) {
                return n;
            }
            ++n;
        }
        return -1;
    }
    
    public Object[] getValues() throws XPropertyException {
        final Vector vector = (Vector)super.value;
        final Object[] array = new Object[vector.size()];
        int n = 0;
        final Enumeration<Object> elements = vector.elements();
        while (elements.hasMoreElements()) {
            array[n] = elements.nextElement();
            ++n;
        }
        return array;
    }
    
    public void setValues(final Object[] array) throws XPropertyException {
        final Vector<Object> baseValueField = new Vector<Object>();
        for (int i = 0; i < array.length; ++i) {
            baseValueField.addElement(array[i]);
        }
        super.setBaseValueField(baseValueField);
    }
    
    public void addValue(final Object obj) throws XPropertyException {
        ((Vector)super.value).addElement(obj);
    }
    
    public void addValue(final Object obj, final int index) throws XPropertyException {
        ((Vector)super.value).insertElementAt(obj, index);
    }
    
    public void removeAll() throws XPropertyException {
        ((Vector)super.value).removeAllElements();
    }
    
    public void removeValue(final int index) throws XPropertyValueIsNotValid {
        ((Vector)super.value).removeElementAt(index);
    }
    
    public void removeValue(final Object obj) throws XPropertyValueIsNotValid {
        ((Vector)super.value).removeElement(obj);
    }
    
    public String[] getValuesAsString() throws XPropertyException {
        final Vector vector = (Vector)super.value;
        final String[] array = new String[vector.size()];
        int n = 0;
        final Enumeration<Object> elements = vector.elements();
        while (elements.hasMoreElements()) {
            final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(elements.nextElement());
            if (!(datatypeModel instanceof IDatatypeSingular)) {
                throw new XPropertyException("Composite datatype members must be scalars");
            }
            array[n] = ((IDatatypeSingular)datatypeModel).getValueAsString();
            ++n;
        }
        return array;
    }
    
    public boolean isValidValue(final Object o) {
        return true;
    }
    
    public Object toObject(final String s) {
        return s;
    }
}

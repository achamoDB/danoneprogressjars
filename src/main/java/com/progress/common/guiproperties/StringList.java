// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.io.Serializable;

public class StringList implements IDatatypeModelAsString, Serializable
{
    String value;
    
    StringList() throws XPropertyException {
    }
    
    StringList(final String valueAsString) throws XPropertyException {
        this.setValueAsString(valueAsString);
    }
    
    public boolean isValidValue(final Object o) throws XPropertyException {
        return o instanceof String && this.isValidValueAsString((String)o);
    }
    
    public void setValue(final Object o) throws XPropertyException {
        if (o instanceof String) {
            this.setValueAsString((String)o);
            return;
        }
        throw new XPropertyValueIsNotValid("");
    }
    
    public void setValues(final Object[] array) throws XPropertyException {
        if (array.length == 1 && array[0] instanceof String) {
            this.setValueAsString((String)array[0]);
            return;
        }
        throw new XPropertyValueIsNotValid("");
    }
    
    public String getValueAsString() throws XPropertyException {
        return this.value;
    }
    
    public String[] getValuesAsString() throws XPropertyException {
        return new String[] { this.value };
    }
    
    public void setValueAsString(final String value) throws XPropertyException {
        if (this.isValidValueAsString(value)) {
            this.value = value;
            return;
        }
        throw new XPropertyValueIsNotValid("");
    }
    
    public boolean isValidValueAsString(final String s) throws XPropertyException {
        return !s.equals("");
    }
    
    public Object toObject(final String s) throws XPropertyException {
        return new StringList(s);
    }
    
    public String normalize(final String s) throws XPropertyException {
        return s;
    }
    
    public Object getValue() throws XPropertyException {
        return this.value;
    }
    
    public Object[] getValues() throws XPropertyException {
        return new Object[] { this.value };
    }
}

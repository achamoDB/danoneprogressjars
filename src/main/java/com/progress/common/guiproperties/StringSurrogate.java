// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

class StringSurrogate extends DatatypeSingular implements IDatatypeModelAsString
{
    Object makeInstance() {
        return new String("");
    }
    
    public void setValue(final Object obj) throws XPropertyValueIsNotValid {
        if (obj instanceof String) {
            this.setValueAsString((String)obj);
            return;
        }
        throw new XPropertyValueIsNotValid("" + obj);
    }
    
    public boolean isValidValue(final Object o) {
        return o instanceof String;
    }
    
    public Object toObject(final String s) {
        return s;
    }
    
    public String normalize(final String s) {
        return s;
    }
    
    public String[] getValuesAsString() {
        return new String[] { this.getValueAsString() };
    }
    
    public String getValueAsString() {
        return (String)this.value();
    }
    
    public void setValueAsString(final String baseValueField) throws XPropertyValueIsNotValid {
        this.setBaseValueField(baseValueField);
    }
    
    public boolean isValidValueAsString(final String s) {
        return true;
    }
}

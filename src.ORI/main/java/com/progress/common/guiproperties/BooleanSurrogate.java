// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

class BooleanSurrogate extends DatatypeSingular implements IDatatypeModelAsString, IDatatypeModelAsBoolean
{
    Object makeInstance() {
        return new Boolean(false);
    }
    
    public void setValue(final Object obj) throws XPropertyValueIsNotValid {
        if (obj instanceof Boolean) {
            this.setValueAsBoolean((Boolean)obj);
        }
        else {
            if (!(obj instanceof String)) {
                throw new XPropertyValueIsNotValid("" + obj);
            }
            this.setValueAsString((String)obj);
        }
    }
    
    public boolean isValidValue(final Object o) {
        return o instanceof Boolean;
    }
    
    public Object toObject(final String s) {
        return Boolean.valueOf(s);
    }
    
    public Object toObject(final Boolean b) {
        return b;
    }
    
    public String normalize(final String s) {
        return ((Boolean)this.toObject(s)).toString();
    }
    
    public String[] getValuesAsString() {
        return new String[] { this.getValueAsString() };
    }
    
    public String getValueAsString() {
        return ((Boolean)this.value()).toString();
    }
    
    public void setValueAsString(final String s) throws XPropertyValueIsNotValid {
        this.setBaseValueField(Boolean.valueOf(s));
    }
    
    public boolean isValidValueAsString(final String s) {
        return true;
    }
    
    public Boolean getValueAsBoolean() {
        return (Boolean)this.value();
    }
    
    public void setValueAsBoolean(final Boolean baseValueField) {
        try {
            this.setBaseValueField(baseValueField);
        }
        catch (XPropertyValueIsNotValid xPropertyValueIsNotValid) {}
    }
}

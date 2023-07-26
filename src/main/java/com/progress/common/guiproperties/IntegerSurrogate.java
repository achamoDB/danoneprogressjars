// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

class IntegerSurrogate extends DatatypeSingular implements IDatatypeModelAsNumericRange, IDatatypeModelAsString
{
    Object makeInstance() {
        return new Integer(0);
    }
    
    public void setValue(final Object obj) throws XPropertyValueIsNotValid {
        if (obj instanceof Number) {
            this.setValueAsNumber((Number)obj);
        }
        else {
            if (!(obj instanceof String)) {
                throw new XPropertyValueIsNotValid("" + obj);
            }
            this.setValueAsString((String)obj);
        }
    }
    
    public Object toObject(final Number n) throws XPropertyValueIsNotValid {
        return new Integer(n.intValue());
    }
    
    public boolean isValidValue(final Object o) {
        if (o instanceof Number) {
            return this.isValidNumericValue((Number)o);
        }
        return o instanceof String && this.isValidValueAsString((String)o);
    }
    
    public String[] getValuesAsString() {
        return new String[] { this.getValueAsString() };
    }
    
    public String getValueAsString() {
        return Integer.toString((int)this.value());
    }
    
    public Object toObject(final String str) throws XPropertyValueIsNotValid {
        Integer value;
        try {
            value = Integer.valueOf(str.trim());
        }
        catch (NumberFormatException ex) {
            throw new XPropertyValueIsNotValid("" + str);
        }
        return value;
    }
    
    public String normalize(final String s) throws XPropertyValueIsNotValid {
        return Integer.toString((int)this.toObject(s));
    }
    
    public void setValueAsString(final String str) throws XPropertyValueIsNotValid {
        try {
            this.setBaseValueField(Integer.valueOf(str.trim()));
        }
        catch (NumberFormatException ex) {
            throw new XPropertyValueIsNotValid("" + str);
        }
    }
    
    public boolean isValidValueAsString(final String s) {
        try {
            Integer.parseInt(s.trim());
        }
        catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    public Number getValueAsNumber() {
        return (Integer)this.value();
    }
    
    public void setValueAsNumber(final Number obj) throws XPropertyValueIsNotValid {
        final Integer baseValueField = new Integer(obj.intValue());
        if (baseValueField != obj) {
            throw new XPropertyValueIsNotValid("" + obj);
        }
        this.setBaseValueField(baseValueField);
    }
    
    public Number upperBound() {
        return null;
    }
    
    public boolean upperBoundIsExclusive() {
        return false;
    }
    
    public Number lowerBound() {
        return null;
    }
    
    public boolean lowerBoundIsExclusive() {
        return false;
    }
    
    public Number representative() {
        return new Integer(0);
    }
    
    public Number discreteInterval() {
        return new Integer(1);
    }
    
    public boolean isValidNumericValue(final Number n) {
        return new Integer(n.intValue()) == n;
    }
}

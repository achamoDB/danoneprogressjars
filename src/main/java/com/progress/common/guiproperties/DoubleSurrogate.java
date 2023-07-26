// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

class DoubleSurrogate extends DatatypeSingular implements IDatatypeModelAsNumericRange, IDatatypeModelAsString
{
    Object makeInstance() {
        return new Double(0.0);
    }
    
    public Object toObject(final Number n) throws XPropertyValueIsNotValid {
        return new Double(n.doubleValue());
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
    
    public boolean isValidValue(final Object o) {
        if (o instanceof Number) {
            return this.isValidNumericValue((Number)o);
        }
        return o instanceof String && this.isValidValueAsString((String)o);
    }
    
    public Object toObject(final String str) throws XPropertyValueIsNotValid {
        Double value;
        try {
            value = Double.valueOf(str.trim());
        }
        catch (NumberFormatException ex) {
            throw new XPropertyValueIsNotValid("" + str);
        }
        return value;
    }
    
    public String normalize(final String s) throws XPropertyValueIsNotValid {
        return Double.toString((double)this.toObject(s));
    }
    
    public String[] getValuesAsString() {
        return new String[] { this.getValueAsString() };
    }
    
    public String getValueAsString() {
        return Double.toString((double)this.value());
    }
    
    public void setValueAsString(final String str) throws XPropertyValueIsNotValid {
        try {
            this.setBaseValueField(Double.valueOf(str.trim()));
        }
        catch (NumberFormatException ex) {
            throw new XPropertyValueIsNotValid("" + str);
        }
    }
    
    public boolean isValidValueAsString(final String s) {
        try {
            Double.valueOf(s.trim());
        }
        catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    public Number getValueAsNumber() {
        return (Double)this.value();
    }
    
    public void setValueAsNumber(final Number obj) throws XPropertyValueIsNotValid {
        final Double baseValueField = new Double(obj.doubleValue());
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
        return new Double(0.0);
    }
    
    public Number discreteInterval() {
        return null;
    }
    
    public boolean isValidNumericValue(final Number n) {
        return new Double(n.doubleValue()) == n;
    }
}

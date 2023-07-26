// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.io.Serializable;

public class IntegerRange implements Serializable, IDatatypeModelAsString, IDatatypeModelAsDynamicNumericRange
{
    protected int min;
    protected int max;
    protected Integer value;
    protected boolean hexValue;
    
    public Object getValue() {
        return this.value;
    }
    
    public Object[] getValues() {
        return new Object[] { this.value };
    }
    
    public void setValues(final Object[] array) throws XPropertyException {
        if (array.length > 1) {
            throw new XPropertyException("Too many values");
        }
        if (!(array[0] instanceof Number)) {
            throw new XPropertyValueIsNotValid("Not numeric");
        }
        this.setValueAsNumber((Number)array[0]);
    }
    
    public String toString() {
        return this.getValueAsString();
    }
    
    public int intValue() {
        return this.value;
    }
    
    public IntegerRange() throws Exception {
        this(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public IntegerRange(final int value, final int min, final int max) throws Exception {
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
        this.value = null;
        this.hexValue = false;
        this.value = new Integer(value);
        if (value < min || value > max) {
            throw new Exception("Int value out of range.");
        }
        this.min = min;
        this.max = max;
    }
    
    public boolean isValidValue(final Object o) {
        if (o instanceof Number) {
            return this.isValidNumericValue((Number)o);
        }
        return o instanceof String && this.isValidValueAsString((String)o);
    }
    
    public void setValue(final Object obj) throws XPropertyValueIsNotValid {
        if (obj instanceof Number) {
            this.setValueAsNumber((Number)obj);
        }
        else if (obj instanceof IntegerRange) {
            this.setValueAsNumber(((IntegerRange)obj).value);
        }
        else {
            if (!(obj instanceof String)) {
                throw new XPropertyValueIsNotValid("Should be compatible with Integer.  Value is: " + obj);
            }
            this.setValueAsString((String)obj);
        }
    }
    
    public String getValueAsString() {
        if (this.hexValue) {
            return "0x" + Integer.toHexString(this.value);
        }
        return Integer.toString(this.value);
    }
    
    public String[] getValuesAsString() {
        return new String[] { this.getValueAsString() };
    }
    
    public void setValueAsString(String substring) throws XPropertyValueIsNotValid {
        int radix = 10;
        if (substring.toLowerCase().trim().startsWith("0x")) {
            radix = 16;
            substring = substring.substring(2);
            this.hexValue = true;
        }
        try {
            final Integer value = Integer.valueOf(substring.trim(), radix);
            final int intValue = value;
            if (intValue < this.min || intValue > this.max) {
                throw new XPropertyValueIsNotValid("Out of range: " + intValue);
            }
            this.value = value;
        }
        catch (NumberFormatException ex) {
            throw new XPropertyValueIsNotValid(substring);
        }
    }
    
    public boolean isValidValueAsString(String substring) {
        int radix = 10;
        if (substring.length() > 2 && substring.toLowerCase().trim().startsWith("0x")) {
            radix = 16;
            substring = substring.substring(2);
        }
        try {
            final int int1 = Integer.parseInt(substring.trim(), radix);
            if (int1 < this.min || int1 > this.max) {
                return false;
            }
        }
        catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    void testValue(final int i) throws XPropertyValueIsNotValid {
        if (i < this.min || i > this.max) {
            throw new XPropertyValueIsNotValid("Out of range: " + i);
        }
    }
    
    public Object toObject(final Number n) throws XPropertyValueIsNotValid {
        Object o = null;
        final int intValue = n.intValue();
        this.testValue(intValue);
        try {
            o = new IntegerRange(intValue, this.min, this.max);
        }
        catch (Throwable t) {}
        return o;
    }
    
    public Object toObject(String substring) throws XPropertyValueIsNotValid {
        IntegerRange integerRange = null;
        int radix = 10;
        if (substring.toLowerCase().trim().startsWith("0x")) {
            radix = 16;
            substring = substring.substring(2);
        }
        try {
            final int intValue = Integer.valueOf(substring.trim(), radix);
            this.testValue(intValue);
            try {
                integerRange = new IntegerRange(intValue, this.min, this.max);
                if (radix == 16) {
                    integerRange.hexValue = true;
                }
            }
            catch (Throwable t) {}
        }
        catch (NumberFormatException ex) {
            throw new XPropertyValueIsNotValid(substring);
        }
        return integerRange;
    }
    
    public String normalize(final String s) throws XPropertyValueIsNotValid {
        return Integer.toString((int)this.toObject(s));
    }
    
    public Number getValueAsNumber() {
        return this.value;
    }
    
    public void setValueAsNumber(final Number obj) throws XPropertyValueIsNotValid {
        final Integer value = new Integer(obj.intValue());
        if (!value.equals(obj)) {
            throw new XPropertyValueIsNotValid(value.toString());
        }
        final int intValue = value;
        if (intValue < this.min || intValue > this.max) {
            throw new XPropertyValueIsNotValid("Out of range: " + intValue);
        }
        this.value = value;
    }
    
    public void setUpperBound(final Number n) throws XPropertyException {
        int intValue;
        try {
            intValue = n.intValue();
        }
        catch (Throwable t) {
            throw new XPropertyException("EXCP: in IntegerRange::setUpperBound - Invalid Bound Value.");
        }
        this.max = intValue;
    }
    
    public Number upperBound() {
        return new Integer(this.max);
    }
    
    public void setLowerBound(final Number n) throws XPropertyException {
        int intValue;
        try {
            intValue = n.intValue();
        }
        catch (Throwable t) {
            throw new XPropertyException("EXCP: in IntegerRange::setLowerBound - Invalid Bound Value.");
        }
        this.min = intValue;
    }
    
    public boolean upperBoundIsExclusive() {
        return false;
    }
    
    public void setUpperBoundIsExclusive(final boolean b) {
    }
    
    public Number lowerBound() {
        return new Integer(this.min);
    }
    
    public boolean lowerBoundIsExclusive() {
        return false;
    }
    
    public void setLowerBoundIsExclusive(final boolean b) {
    }
    
    public Number representative() {
        return new Integer(0);
    }
    
    public Number discreteInterval() {
        return new Integer(1);
    }
    
    public boolean isValidNumericValue(final Number n) {
        final Integer n2 = new Integer(n.intValue());
        if (!n2.equals(n)) {
            return false;
        }
        final int intValue = n2;
        return intValue >= this.min && intValue <= this.max;
    }
    
    public boolean equals(final Object o) {
        return o instanceof IntegerRange && ((IntegerRange)o).intValue() == this.intValue();
    }
}

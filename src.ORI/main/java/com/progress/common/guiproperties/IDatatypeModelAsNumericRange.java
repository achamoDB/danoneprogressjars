// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IDatatypeModelAsNumericRange extends IDatatypeSimple
{
    Number getValueAsNumber();
    
    void setValueAsNumber(final Number p0) throws XPropertyValueIsNotValid;
    
    Number upperBound();
    
    boolean upperBoundIsExclusive();
    
    Number lowerBound();
    
    boolean lowerBoundIsExclusive();
    
    Number representative();
    
    Number discreteInterval();
    
    boolean isValidNumericValue(final Number p0);
    
    Object toObject(final Number p0) throws XPropertyValueIsNotValid;
}

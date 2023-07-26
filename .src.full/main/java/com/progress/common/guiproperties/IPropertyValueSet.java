// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IPropertyValueSet
{
    Object setValue(final String p0, final Object p1) throws XPropertyHasNoValue, XPropertyDoesNotExist, XPropertyValueIsNotValid;
    
    Object setValue(final int p0, final Object p1) throws XPropertyValueIsNotValid;
    
    Object getValue(final String p0) throws XPropertyHasNoValue, XPropertyDoesNotExist;
    
    Object getValue(final int p0) throws XPropertyHasNoValue;
    
    Object unsetValue(final String p0) throws XPropertyHasNoValue, XPropertyDoesNotExist, XUnsettingNonDelegatedPropertyValue;
    
    IPropertySetDefinition getDefinition();
    
    IPropertyDefinition getDefinition(final String p0) throws XPropertyHasNoValue, XPropertyDoesNotExist;
    
    IPropertyDefinition getDefinition(final int p0) throws XPropertyHasNoValue;
    
    int state(final String p0) throws XPropertyDoesNotExist;
    
    boolean isComplete(final boolean p0);
    
    boolean hasMandatoryProperties();
    
    IPropertyValueSet locationOfValue(final String p0) throws XPropertyHasNoValue, XPropertyDoesNotExist;
    
    boolean hasValue(final String p0) throws XPropertyDoesNotExist;
    
    int indexOf(final String p0) throws XPropertyDoesNotExist;
}

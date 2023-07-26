// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IDatatypeSimple extends IPropertyDatatype
{
    void setValue(final Object p0) throws XPropertyValueIsNotValid, XPropertySettingReadOnly, XPropertyException;
    
    boolean isValidValue(final Object p0) throws XPropertyException;
}

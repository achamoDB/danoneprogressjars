// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IDatatypeModelAsBoolean extends IDatatypeSingular
{
    Boolean getValueAsBoolean();
    
    void setValueAsBoolean(final Boolean p0) throws XPropertyValueIsNotValid;
    
    Object toObject(final Boolean p0) throws XPropertyValueIsNotValid;
}

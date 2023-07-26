// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;

public interface IPropertySetDefinition
{
    Enumeration propertyDefinitions();
    
    Enumeration valueSets();
    
    void addValueSet(final PropertyValueSet p0);
}

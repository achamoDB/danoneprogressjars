// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.sql.explorer;

public interface ISQLProperty
{
    public static final int STRING = 1;
    public static final int INTEGER = 2;
    public static final int BOOLEAN = 3;
    public static final int ENUM = 4;
    public static final int ENUMIDX = 5;
    
    String getDefaultValue();
    
    String getName();
    
    String getValue();
    
    String getShow();
    
    void setDefaultValue(final String p0) throws SQLProperties.PropertyValueException;
    
    void setValue(final String p0) throws SQLProperties.PropertyValueException;
    
    void validateValue(final String p0) throws SQLProperties.PropertyValueException;
}

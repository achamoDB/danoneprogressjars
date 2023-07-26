// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public interface IPropertyValidation
{
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final boolean IS_WINDOWS = IPropertyValidation.OS_NAME.toLowerCase().startsWith("windows");
    
    boolean isValid(final String p0, final String p1);
    
    String getErrorMessage();
}

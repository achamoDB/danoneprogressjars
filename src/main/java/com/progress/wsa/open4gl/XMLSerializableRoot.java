// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.wsa.open4gl;

public interface XMLSerializableRoot extends XMLSerializable
{
    void setSchemaLocation(final String p0);
    
    String getSchemaLocation();
    
    void setTargetNamespace(final String p0);
    
    String getTargetNamespace();
    
    void setRoot(final String p0);
    
    String getRoot();
    
    void setPrefix(final String p0);
    
    String getPrefix();
    
    void setXMLType(final String p0);
    
    String getXMLType();
}

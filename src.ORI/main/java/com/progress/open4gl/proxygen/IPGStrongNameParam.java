// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface IPGStrongNameParam
{
    String getStrongName();
    
    boolean hasSameSchema(final IPGStrongNameParam p0);
    
    boolean hasSameSchemaWS(final IPGStrongNameParam p0);
    
    String getParamName();
    
    int getParamType();
    
    String getClassName();
    
    void setClassName(final String p0);
    
    void setClassNameForWS(final String p0);
    
    String getProcName();
    
    void setProcName(final String p0);
    
    String getNamespace();
    
    void setNamespace(final String p0);
    
    void setTopMatch(final boolean p0);
    
    boolean isTopMatch();
    
    void setSameParamNameIndex(final int p0);
    
    int getSameParamNameIndex();
}

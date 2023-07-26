// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface IPGDetail
{
    public static final int METHODTYPE_PROCEDURE = 1;
    public static final int METHODTYPE_FUNCTION = 2;
    
    String getMethodName();
    
    void setMethodName(final String p0);
    
    String valMethodName(final String p0, final boolean p1, final boolean p2, final boolean p3);
    
    String getHelpString();
    
    void setHelpString(final String p0);
    
    boolean usesUnknownDefault();
    
    void setUnknownDefault(final boolean p0);
    
    boolean isCustomized();
    
    void setCustomized(final boolean p0);
    
    boolean usesReturnValue();
    
    void setUseReturnValue(final boolean p0);
    
    PGParam[] getParameters();
    
    void setParameters(final PGParam[] p0);
    
    PGParam getReturnValue();
    
    void setReturnValue(final PGParam p0);
    
    boolean hasParamType(final int p0, final boolean p1);
    
    boolean hasInputParam();
    
    boolean hasOutputParam();
    
    int getProcType();
    
    void setProcType(final int p0);
    
    void updateDataTypesFromXPXGFile();
    
    boolean usesBeforeImageDefault();
    
    void setBeforeImageDefault(final boolean p0);
}

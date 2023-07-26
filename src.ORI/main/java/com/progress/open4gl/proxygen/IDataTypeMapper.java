// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface IDataTypeMapper
{
    String proToNative(final PGParam p0);
    
    String proToNative(final PGParam p0, final String p1);
    
    String proToNative(final PGParam p0, final boolean p1);
    
    String setParameter(final PGParam p0);
    
    String setParameter(final PGParam p0, final String p1, final boolean p2, final boolean p3);
    
    String getOutputNameParameter(final int p0, final boolean p1);
    
    String getParameter(final PGParam p0, final boolean p1);
    
    String setFunctionType(final PGParam p0);
    
    String setMetaData(final int p0);
    
    String getTraceVal(final PGParam p0);
}

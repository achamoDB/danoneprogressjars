// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.open4gl.proxygen;

public interface ISchemaTemplate
{
    String getMetaSchemaSet();
    
    String getDSMetaSchemaSet();
    
    String getStaticTTMetaData(final boolean p0);
    
    String getNewTTMetaData(final boolean p0);
    
    String getStaticDataRelation();
    
    String getNewDataRelation();
    
    String getStaticDSMetaData();
    
    String getNewDSMetaData();
    
    String getGetOutParam();
    
    String getDTToDSMetaSchemaSet();
    
    String getPrimeIdxNaming(final boolean p0);
}

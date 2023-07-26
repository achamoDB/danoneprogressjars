// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiSchemaBuilder;

import java.io.FileWriter;
import java.util.Hashtable;

public interface IPropDefInfo
{
    void setPropFilename(final String p0);
    
    boolean loadProperties();
    
    boolean loadProperties(final String p0);
    
    String[] getPropGroupList();
    
    Hashtable getPropertyList(final String p0);
    
    void addPropGroupList(final String p0);
    
    void addProperty(final String p0);
    
    void setPropertyDataType(final String p0, final String p1);
    
    void setPropertyList(final Hashtable p0);
    
    boolean buildGuiSchema();
    
    boolean saveSchemaDef(final String p0);
    
    boolean saveSchemaDef();
    
    boolean writeSchema(final FileWriter p0);
    
    boolean loadSchemaDef(final String p0);
}

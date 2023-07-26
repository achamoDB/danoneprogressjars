// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiSchemaBuilder;

public class SchemaBuilderGlue implements ISchemaBuilderConst
{
    private String m_propFilename;
    private String m_schemaFilename;
    private IPropDefInfo m_pdiObj;
    private int m_runMode;
    
    public SchemaBuilderGlue(final int runMode) {
        this.m_runMode = -1;
        this.m_propFilename = null;
        this.m_schemaFilename = null;
        this.m_pdiObj = null;
        this.m_runMode = runMode;
    }
    
    public SchemaBuilderGlue(final int runMode, final int pdiObject, final String propFilename, final String schemaFilename) {
        this.m_runMode = -1;
        this.m_propFilename = propFilename;
        this.m_schemaFilename = schemaFilename;
        this.m_runMode = runMode;
        this.setPDIObject(pdiObject);
        this.loadPropFile(this.m_propFilename);
    }
    
    public void setPDIObject(final int i) {
        if (i == 1) {
            this.m_pdiObj = new UBPropDefInfo(this.m_runMode);
        }
        else {
            System.out.println("No support for PropDefInfo of type :" + i);
        }
    }
    
    public boolean loadPropFile(final String propFilename) {
        try {
            this.m_propFilename = propFilename;
            return this.m_pdiObj.loadProperties(propFilename);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public boolean saveSchemaFile(final String schemaFilename) {
        try {
            this.m_schemaFilename = schemaFilename;
            return this.m_pdiObj.saveSchemaDef(this.m_schemaFilename);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public boolean saveSchemaFile() {
        try {
            return this.m_schemaFilename != null && this.m_pdiObj.saveSchemaDef();
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public boolean buildIt() {
        try {
            return this.m_pdiObj.buildGuiSchema();
        }
        catch (Exception ex) {
            return false;
        }
    }
}

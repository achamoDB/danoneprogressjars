// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public class PropertyDefinition implements IPropertyDefinition
{
    String name;
    protected Class datatype;
    protected Object baseDatatypeInstance;
    protected String prompt;
    protected String description;
    boolean isMandatory;
    String schemaKey;
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    public PropertyDefinition(final String name, final Class datatype, final Object baseDatatypeInstance, final String prompt, final String description, final boolean isMandatory, final String schemaKey) {
        this.datatype = null;
        this.baseDatatypeInstance = null;
        this.prompt = null;
        this.description = null;
        this.name = name;
        this.datatype = datatype;
        this.baseDatatypeInstance = baseDatatypeInstance;
        this.prompt = prompt;
        this.description = description;
        this.isMandatory = isMandatory;
        this.schemaKey = schemaKey;
    }
    
    public String name() {
        return this.name;
    }
    
    public Class datatype() {
        return this.datatype;
    }
    
    public void setBaseDatatypeInstance(final Object baseDatatypeInstance) {
        this.baseDatatypeInstance = baseDatatypeInstance;
    }
    
    public Object baseDatatypeInstance() {
        return this.baseDatatypeInstance;
    }
    
    public String prompt() {
        return this.prompt;
    }
    
    public String description(final boolean b) {
        return this.description;
    }
    
    public String schemaKey() {
        return this.schemaKey;
    }
}

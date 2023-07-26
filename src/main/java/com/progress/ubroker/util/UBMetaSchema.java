// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.ubroker.util;

import java.rmi.RemoteException;
import com.progress.common.property.PropertyTransferObject;
import com.progress.common.property.PropertyManager;
import com.progress.common.property.MetaSchema;

public class UBMetaSchema
{
    private static final String MODULE_NAME = "UBMetaSchema";
    static MetaSchema m_metaSchema;
    String m_schemaFileSpec;
    PropertyManager m_pm;
    
    private static synchronized void createMetaSchema(final PropertyManager propertyManager, final String s) {
        try {
            if (UBMetaSchema.m_metaSchema == null) {
                UBMetaSchema.m_metaSchema = new MetaSchema(propertyManager, s);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static synchronized void cleanupSchemaObject() {
        UBMetaSchema.m_metaSchema = null;
    }
    
    public UBMetaSchema(final PropertyManager propertyManager) {
        this(propertyManager, "ubroker.schema");
    }
    
    public UBMetaSchema(final PropertyManager pm, final String schemaFileSpec) {
        this.m_schemaFileSpec = null;
        this.m_pm = null;
        this.m_schemaFileSpec = schemaFileSpec;
        this.m_pm = pm;
        this.makeMetaSchema();
    }
    
    public PropertyTransferObject getPSS(final String s) throws RemoteException {
        if (this.m_pm == null) {
            return null;
        }
        try {
            return new PropertyTransferObject(this.m_pm, s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public void refreshPM(final PropertyManager pm) {
        this.m_pm = pm;
    }
    
    private void makeMetaSchema() {
        try {
            createMetaSchema(this.m_pm, this.m_schemaFileSpec);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    static {
        UBMetaSchema.m_metaSchema = null;
    }
}

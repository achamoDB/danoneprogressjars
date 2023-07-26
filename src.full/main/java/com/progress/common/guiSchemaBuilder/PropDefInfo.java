// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiSchemaBuilder;

import java.util.Enumeration;
import java.io.IOException;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.io.FileWriter;
import java.io.File;
import com.progress.common.property.PropertyManager;
import java.util.Hashtable;

public class PropDefInfo implements UBPropDefConst
{
    static final String GROUP_ROOT = "Group";
    static final String PROPERTY_ROOT = "Property";
    static final String GROUP_JAVA_RSC_PREFIX = "JavaResourceBundlePrefix";
    static final String GROUP_JAVA_RSC_PREFIX_VAL = "com.progress.international.messages";
    static final String GROUP_RESTRICT_PROP = "RestrictProperties";
    static final String GROUP_RESTRICT_PROP_VAL = "false";
    static final String GROUP_RESTRICT_CHILD = "RestrictChildren";
    static final String GROUP_RESTRICT_CHILD_VAL = "false";
    static final String GROUP_RESTRICT_GRANDCHILD = "RestrictGrandchildren";
    static final String GROUP_RESTRICT_GRANDCHILD_VAL = "false";
    static final String GROUP_SAVE_EMPTY_GROUP = "SaveEmptyGroup";
    static final String GROUP_SAVE_EMPTY_GROUP_VAL = "true";
    static final String GROUP_UI = "UI";
    static final String GROUP_UI_VAL = "TRUE";
    static final String JAVA_DT_PREFIX = "JavaDatatypePrefix";
    static final String JAVA_DT_PREFIX_VAL = "com.progress.common.datatypes";
    static final String IS_ARRAY = "IsArray";
    static final String IS_ARRAY_VAL = "false";
    static final String DYNAMIC_PROP_CLASS = "DynamicPropertyClass";
    static final String DYNAMIC_PROP_CLASS_VAL = "com.progress.ubroker.util.DynamicPropertyValues";
    static final String PREFIX = "Prefix";
    static final String PREFIX_VAL = "";
    static final String ENUM_CHOICE = "Choices";
    static final String ENUM_CHOICE_VAL = "";
    static final String DEFAULT = "Default";
    static final String DEFAULT_CHOICE = "0";
    static final String DEFAULT_NULL_STR = "";
    static final String DEFAULT_BOOLEAN_VAL = "false";
    static final String MIN = "Min";
    static final String MAX = "Max";
    static final String MIN_VAL = "-2147483648";
    static final String MAX_VAL = "2147483647";
    static final String GROUP_PREFIX = "Prefix";
    static final String GROUP_PREFIX_VAL = "";
    static final String[] PROPERTY_DATETYPES;
    static final String NEWLINE;
    static final String LINE_INDENT = "  ";
    private IPropDefInfo m_pdi;
    private int m_runMode;
    private Hashtable m_schemaPrologDef;
    private boolean m_rebuildSchema;
    
    public PropDefInfo(final int runMode) {
        this.m_pdi = null;
        this.m_runMode = -1;
        this.m_schemaPrologDef = null;
        this.m_rebuildSchema = false;
        this.m_runMode = runMode;
        this.m_schemaPrologDef = new Hashtable();
    }
    
    public PropDefInfo(final int runMode, final IPropDefInfo pdi) {
        this.m_pdi = null;
        this.m_runMode = -1;
        this.m_schemaPrologDef = null;
        this.m_rebuildSchema = false;
        this.m_runMode = runMode;
        this.m_pdi = pdi;
        this.m_schemaPrologDef = new Hashtable();
    }
    
    public void setPDI(final IPropDefInfo pdi) {
        this.m_pdi = pdi;
    }
    
    public IPropDefInfo getPDI() {
        return this.m_pdi;
    }
    
    public boolean buildSchema() {
        if (this.m_pdi != null) {
            if (this.m_schemaPrologDef.size() == 0 || this.m_rebuildSchema) {
                this.buildSchemaPrologDef();
            }
            return true;
        }
        return false;
    }
    
    public void setRebuildSchema() {
        this.m_rebuildSchema = true;
    }
    
    public String tellPropertyDataType(final PropertyManager.Property property) {
        if (property instanceof PropertyManager.EnumProperty) {
            return "Enum";
        }
        if (property instanceof PropertyManager.IntProperty) {
            return "Integer";
        }
        if (property instanceof PropertyManager.LongProperty) {
            return "Long";
        }
        if (property instanceof PropertyManager.LongProperty) {
            return "Long";
        }
        if (property instanceof PropertyManager.BooleanProperty) {
            return "Boolean";
        }
        return "String";
    }
    
    public boolean saveSchemaFile(final String pathname) {
        try {
            final File file = new File(pathname);
            final String parent = file.getParent();
            if (parent != null) {
                final File file2 = new File(parent);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
            }
            final FileWriter fileWriter = new FileWriter(file);
            this.writeSchema(fileWriter);
            fileWriter.close();
            return true;
        }
        catch (IOException ex) {
            System.out.println(((ProgressResources)ResourceBundle.getBundle("com.progress.international.messages.CMNMsgBundle")).getTranString("Failed to save schema to file", (Object)pathname, (Object)ex.toString()));
            return false;
        }
    }
    
    private void buildSchemaPrologDef() {
        this.m_schemaPrologDef.put("Group", this.defaultGroupSetting());
        this.m_schemaPrologDef.put("Property", this.defaultPropertySetting());
        final String str = "Property.";
        this.m_schemaPrologDef.put(str + "String", this.defaultStringPropertySetting());
        this.m_schemaPrologDef.put(str + "Integer", this.defaultIntegerPropertySetting());
        this.m_schemaPrologDef.put(str + "Enum", this.defaultEnumPropertySetting());
        this.m_schemaPrologDef.put(str + "Boolean", this.defaultBooleanPropertySetting());
        this.m_schemaPrologDef.put(str + "Group", this.defaultGroupPropertySetting());
    }
    
    private Hashtable defaultGroupSetting() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("JavaResourceBundlePrefix", "com.progress.international.messages");
        hashtable.put("RestrictProperties", "false");
        hashtable.put("RestrictChildren", "false");
        hashtable.put("RestrictGrandchildren", "false");
        hashtable.put("SaveEmptyGroup", "true");
        hashtable.put("UI", "TRUE");
        return hashtable;
    }
    
    private Hashtable defaultPropertySetting() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("JavaDatatypePrefix", "com.progress.common.datatypes");
        hashtable.put("IsArray", "false");
        hashtable.put("DynamicPropertyClass", "com.progress.ubroker.util.DynamicPropertyValues");
        hashtable.put("Prefix", "");
        return hashtable;
    }
    
    private Hashtable defaultStringPropertySetting() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Default", "");
        return hashtable;
    }
    
    private Hashtable defaultEnumPropertySetting() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Choices", "");
        hashtable.put("Default", "0");
        return hashtable;
    }
    
    private Hashtable defaultGroupPropertySetting() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Prefix", "");
        return hashtable;
    }
    
    private Hashtable defaultIntegerPropertySetting() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Min", "-2147483648");
        hashtable.put("Max", "2147483647");
        return hashtable;
    }
    
    private Hashtable defaultBooleanPropertySetting() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("Default", "false");
        return hashtable;
    }
    
    private boolean writeSchema(final FileWriter fileWriter) {
        try {
            final boolean writeSchemaProlog = this.writeSchemaProlog(fileWriter);
            final boolean writeSchema = this.m_pdi.writeSchema(fileWriter);
            return writeSchemaProlog || writeSchema;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    private boolean writeSchemaProlog(final FileWriter fileWriter) {
        final boolean b = true;
        try {
            final Enumeration<String> keys = (Enumeration<String>)this.m_schemaPrologDef.keys();
            while (keys.hasMoreElements()) {
                final String s = keys.nextElement();
                fileWriter.write(PropDefInfo.NEWLINE + "[" + s + "]" + PropDefInfo.NEWLINE);
                final Hashtable<String, Hashtable<String, Hashtable<String, Hashtable>>> hashtable = this.m_schemaPrologDef.get(s);
                final Enumeration<String> keys2 = hashtable.keys();
                try {
                    while (keys2.hasMoreElements()) {
                        final String s2 = keys2.nextElement();
                        fileWriter.write("  " + s2 + "=" + (String)hashtable.get(s2) + PropDefInfo.NEWLINE);
                    }
                }
                catch (Exception ex) {}
            }
            return b;
        }
        catch (Exception ex2) {
            return false;
        }
    }
    
    static {
        PROPERTY_DATETYPES = new String[] { "String", "Integer", "Enum", "Boolean", "Group" };
        NEWLINE = System.getProperty("line.separator");
    }
}

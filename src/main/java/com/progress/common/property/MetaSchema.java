// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.util.Enumeration;
import com.progress.chimera.common.Tools;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Hashtable;

public class MetaSchema extends AbstractMetaSchema
{
    private static final String VERSION = "%% version 1.1";
    
    public MetaSchema(final PropertyManager propertyManager, final String s) throws PropertyException {
        super(s);
        this.instantiateSchema(propertyManager);
    }
    
    protected Hashtable getGroupSchemaHashtableS() {
        return super.groupSchemaHashtable;
    }
    
    protected Hashtable getGroupAttributeHashtableS(final String s) {
        Hashtable hashtable;
        if (s != null) {
            hashtable = super.groupAttrHashtable.get(s.toLowerCase());
        }
        else {
            hashtable = new Hashtable();
        }
        return hashtable;
    }
    
    protected Hashtable getPropertySchemaHashtableS(final String s) {
        Hashtable hashtable = null;
        if (s != null) {
            hashtable = super.groupSchemaHashtable.get(s.toLowerCase());
        }
        return hashtable;
    }
    
    protected Hashtable getCategorySchemaHashtableS(String lowerCase, String[] arrayProperty) {
        final Hashtable<String, Hashtable<String, Hashtable>> hashtable = new Hashtable<String, Hashtable<String, Hashtable>>();
        if (lowerCase != null) {
            lowerCase = lowerCase.toLowerCase();
            if (arrayProperty == null) {
                arrayProperty = this.getArrayProperty("Group." + lowerCase + ".Categories");
            }
            final Hashtable propertySchemaHashtableS = this.getPropertySchemaHashtableS(lowerCase);
            for (int i = 0; i < arrayProperty.length; ++i) {
                final Hashtable<String, Hashtable> value = new Hashtable<String, Hashtable>();
                final String[] arrayProperty2 = this.getArrayProperty("Category." + arrayProperty[i] + ".Fields");
                if (arrayProperty2 != null) {
                    for (int j = 0; j < arrayProperty2.length; ++j) {
                        final String s = arrayProperty2[j];
                        final Hashtable value2 = propertySchemaHashtableS.get(s.toLowerCase());
                        if (value2 != null) {
                            value.put(s, value2);
                        }
                        else if (PropertyManager.m_log != null) {
                            PropertyManager.m_log.log(4, "Unknown property " + s + " defined in category " + arrayProperty[i] + " for group " + lowerCase);
                        }
                    }
                }
                hashtable.put(arrayProperty[i], value);
            }
        }
        return hashtable;
    }
    
    protected Hashtable getCategoryAttributeHashtableS(final String s) {
        Hashtable hashtable;
        if (s != null) {
            hashtable = super.categoryAttrHashtable.get(s.toLowerCase());
        }
        else {
            hashtable = new Hashtable();
        }
        return hashtable;
    }
    
    protected boolean chkPropertyVersion(final String s) {
        return s.trim().toLowerCase().equals("%% version 1.1".toLowerCase().trim());
    }
    
    protected void writePropertyVersion(final BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("%% version 1.1" + PropertyManager.NEWLINE);
    }
    
    protected void instantiateSchema(final PropertyManager propertyManager) throws PropertyWithoutCorrespondingGroup, PropertyException {
        super.instantiateSchema();
        try {
            for (int i = 0; i < super.groups.length; ++i) {
                final String s = super.groups[i];
                final Property[] array = new Property[super.propsByGroup[i].size()];
                int n = -1;
                final Enumeration elements = super.propsByGroup[i].elements();
                while (elements.hasMoreElements()) {
                    final PropStorageObject propStorageObject = elements.nextElement();
                    ++n;
                    array[n] = this.makePropertyObject(propStorageObject, propertyManager);
                }
                propertyManager.registerGroup(s, this.getBooleanProperty("Group." + s + ".RestrictChildren"), this.getBooleanProperty("Group." + s + ".RestrictGrandChildren"), this.getBooleanProperty("Group." + s + ".RestrictProperties"), array, this.getBooleanProperty("Group." + s + ".SaveEmptyGroup"));
            }
        }
        catch (Exception previous) {
            if (previous instanceof PropertyException) {
                throw (PropertyException)previous;
            }
            Tools.px(previous);
            final PropertyException ex = new PropertyException("Nested call exception", new Object[0]);
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    Property makePropertyObject(final PropStorageObject propStorageObject, final PropertyManager propertyManager) throws PropertyException {
        Property property = null;
        try {
            final Integer tag = propStorageObject.tag;
            final int n = (tag == null) ? this.getIntProperty(propStorageObject.fullPropName() + ".Tag") : tag;
            final String minValue = propStorageObject.minValue;
            final int n2 = (minValue != null) ? new Integer(minValue) : 0;
            final String maxValue = propStorageObject.maxValue;
            final int n3 = (maxValue != null) ? new Integer(maxValue) : 0;
            final Boolean isMandatory = propStorageObject.isMandatory;
            if (isMandatory == null) {
                this.getBooleanProperty(propStorageObject.fullPropName + ".IsMandatory");
            }
            else {
                isMandatory;
            }
            final Boolean isArray = propStorageObject.isArray;
            final boolean multipleValues = (isArray == null) ? this.getBooleanProperty(propStorageObject.fullPropName + ".IsArray") : isArray;
            String arraySeparator = null;
            if (multipleValues) {
                arraySeparator = ((propStorageObject.arraySeparator == null) ? this.getProperty(propStorageObject.fullPropName + ".ArraySeparator") : propStorageObject.arraySeparator);
            }
            final String defaultValue = propStorageObject.defaultValue;
            if (propStorageObject.type.equalsIgnoreCase("String")) {
                property = new Property(propStorageObject.propName, defaultValue, n);
            }
            else if (propStorageObject.type.equalsIgnoreCase("Boolean")) {
                property = new BooleanProperty(propStorageObject.propName, defaultValue, n);
            }
            else if (propStorageObject.type.equalsIgnoreCase("Integer")) {
                property = new IntProperty(propStorageObject.propName, defaultValue, n2, n3, n);
            }
            else if (propStorageObject.type.equalsIgnoreCase("Long")) {
                property = new LongProperty(propStorageObject.propName, defaultValue, n2, n3, n);
            }
            else if (propStorageObject.type.equalsIgnoreCase("Float")) {
                property = new FloatProperty(propStorageObject.propName, defaultValue, (float)n2, (float)n3, n);
            }
            else if (propStorageObject.type.equalsIgnoreCase("Double")) {
                property = new DoubleProperty(propStorageObject.propName, defaultValue, n2, n3, n);
            }
            else if (propStorageObject.type.equalsIgnoreCase("Group")) {
                property = new GroupProperty(propStorageObject.propName, null, defaultValue, propertyManager, (propStorageObject.groupPrefix == null) ? "" : propStorageObject.groupPrefix, propStorageObject.groupSubsume != null && propStorageObject.groupSubsume, n, true, propStorageObject.doValidate == null || propStorageObject.doValidate);
            }
            else if (propStorageObject.type.equalsIgnoreCase("Enum")) {
                property = new EnumProperty(propStorageObject.propName, defaultValue, propStorageObject.choices, n);
            }
            if (property != null) {
                property.setMultipleValues(multipleValues);
                if (multipleValues) {
                    property.setArraySeparator(arraySeparator);
                }
            }
        }
        catch (PropertyException ex) {
            throw ex;
        }
        return property;
    }
}

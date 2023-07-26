// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.common.guiproperties.IPropertyDatatype;
import com.progress.common.guiproperties.XPropertyException;
import com.progress.common.guiproperties.IDatatypeModelAsList;
import com.progress.common.guiproperties.DatatypeModelMapping;
import com.progress.chimera.common.Tools;
import com.progress.common.networkevents.EventBroker;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Vector;
import java.util.Hashtable;

public abstract class AbstractMetaSchema extends PropertyManager
{
    protected String[] categories;
    protected String[] groups;
    protected String[] groupsFull;
    protected String[] properties;
    protected Hashtable categoryAttrHashtable;
    protected Hashtable groupSchemaHashtable;
    protected Hashtable groupAttrHashtable;
    protected Hashtable[] propertySchemaHashtable;
    public Vector[] propsByGroup;
    
    protected Hashtable getGroupHashtable() {
        return new OrderedHashtable();
    }
    
    protected void loadData(final InputStream in) throws PropertyException {
        this.load(new BufferedReader(new InputStreamReader(in)));
        this.instantiateSchema();
    }
    
    protected AbstractMetaSchema(final InputStream inputStream) throws PropertyException {
        this.categories = null;
        this.groups = null;
        this.groupsFull = null;
        this.properties = null;
        this.categoryAttrHashtable = new Hashtable();
        this.groupSchemaHashtable = new Hashtable();
        this.groupAttrHashtable = new Hashtable();
        this.propsByGroup = null;
        this.loadData(inputStream);
    }
    
    protected AbstractMetaSchema(final String s) throws PropertyException {
        this();
        Class<?> forName;
        try {
            forName = Class.forName("com.progress.common.property.AbstractMetaSchema");
        }
        catch (ClassNotFoundException previous) {
            final PropertyException ex = new PropertyException("AbstractMetaSchema ", new Object[0]);
            ex.setPrevious(previous);
            throw ex;
        }
        final InputStream resourceAsStream = forName.getResourceAsStream("/com/progress/schema/" + s);
        if (resourceAsStream == null) {
            throw new PropertyException("Error locating schema file: /com/progress/schema/" + s, new Object[0]);
        }
        this.loadData(resourceAsStream);
    }
    
    protected AbstractMetaSchema() throws PropertyException {
        super(null);
        this.categories = null;
        this.groups = null;
        this.groupsFull = null;
        this.properties = null;
        this.categoryAttrHashtable = new Hashtable();
        this.groupSchemaHashtable = new Hashtable();
        this.groupAttrHashtable = new Hashtable();
        this.propsByGroup = null;
        this.restrictRootGroups();
        this.registerGroup("Category", false, false, false, new Property[] { new Property("DisplayName"), new Property("Form"), new Property("Parent"), new Property("JavaResourceBundle"), new Property("JavaResourceBundlePrefix"), new Property("UI"), new Property("Fields") }, false);
        this.registerGroup("Group", false, false, false, new Property[] { new BooleanProperty("RestrictProperties"), new BooleanProperty("RestrictChildren"), new BooleanProperty("RestrictGrandchildren"), new BooleanProperty("SaveEmptyGroup"), new Property("Categories"), new Property("JavaResourceBundle"), new Property("JavaResourceBundlePrefix"), new Property("UI") }, false);
        final Property property = new Property("Choices");
        property.setMultipleValues(true);
        this.registerGroup("Property", false, false, true, new Property[] { new Property("DisplayName"), new Property("Control"), new Property("ValidationMethod"), new Property("ChoicesMethod"), new Property("Hidden"), new Property("DynamicPropertyClass"), new Property("JavaTypePrefix"), new Property("JavaType"), new BooleanProperty("IsMandatory"), new BooleanProperty("IsArray"), new Property("ArraySeparator"), new Property("UI"), new IntProperty("Tag"), property, new IntProperty("DefaultChoice", "0"), new Property("DynamicChoices"), new Property("DynamicDefault"), new Property("DynamicDisplayName") }, false);
        this.registerGroup("Property.String", false, false, true, new Property[] { new Property("Default"), new Property("DynamicDefault") }, false);
        this.registerGroup("Property.Boolean", false, false, true, new Property[] { new BooleanProperty("Default"), new Property("DynamicDefault") }, false);
        this.registerGroup("Property.Double", false, false, true, new Property[] { new DoubleProperty("Default"), new DoubleProperty("Min"), new DoubleProperty("Max"), new Property("DynamicMin"), new Property("DynamicMax"), new Property("DynamicDefault") }, false);
        this.registerGroup("Property.Float", false, false, true, new Property[] { new FloatProperty("Default"), new FloatProperty("Min"), new FloatProperty("Max"), new Property("DynamicMin"), new Property("DynamicMax"), new Property("DynamicDefault") }, false);
        this.registerGroup("Property.Integer", false, false, true, new Property[] { new IntProperty("Default"), new IntProperty("Min"), new IntProperty("Max"), new Property("DynamicMin"), new Property("DynamicMax"), new Property("DynamicDefault") }, false);
        this.registerGroup("Property.Enum", false, false, true, new Property[0], false);
        this.registerGroup("Property.Group", false, false, true, new Property[] { new Property("Prefix"), new BooleanProperty("Validate"), new BooleanProperty("Subsume") }, false);
    }
    
    protected void instantiateSchema() throws PropertyException {
        try {
            this.categories = this.groups("Category", true, false);
            if (this.categories == null) {
                this.categories = new String[0];
            }
            this.groups = this.groups("Group", true, false);
            if (this.groups == null) {
                this.groups = new String[0];
            }
            this.groupsFull = this.groups("Group", true, true);
            if (this.groupsFull == null) {
                this.groupsFull = new String[0];
            }
            this.properties = this.groups("Property", true, true);
            if (this.properties == null) {
                this.properties = new String[0];
            }
            for (int i = 0; i < this.categories.length; ++i) {
                final Hashtable<String, String> value = new Hashtable<String, String>();
                final String s = this.categories[i];
                final String property = this.getProperty("Category." + this.categories[i] + ".Parent");
                final String property2 = this.getProperty("Category." + this.categories[i] + ".DisplayName");
                final String[] arrayProperty = this.getArrayProperty("Category." + this.categories[i] + ".Fields");
                if (arrayProperty != null) {
                    for (int j = 0; j < ((String)(Object)arrayProperty).length; ++j) {
                        arrayProperty[j] = arrayProperty[j].toLowerCase();
                    }
                    value.put("fields".toLowerCase(), (String)(Object)arrayProperty);
                }
                if (property != null) {
                    value.put("parent".toLowerCase(), property);
                }
                if (property2 != null) {
                    value.put("displayname".toLowerCase(), property2);
                }
                this.categoryAttrHashtable.put(s.toLowerCase(), value);
            }
            for (int k = 0; k < this.groups.length; ++k) {
                final Hashtable<String, String> value2 = new Hashtable<String, String>();
                final String s2 = this.groups[k];
                final String[] arrayProperty2 = this.getArrayProperty("Group." + this.groups[k] + ".Categories");
                final String property3 = this.getProperty("Group." + this.groups[k] + ".DisplayName");
                if (arrayProperty2 != null) {
                    value2.put("categories".toLowerCase(), (String)(Object)arrayProperty2);
                }
                if (property3 != null) {
                    value2.put("displayname".toLowerCase(), property3);
                }
                this.groupAttrHashtable.put(s2.toLowerCase(), value2);
            }
            this.propsByGroup = new Vector[this.groups.length];
            this.propertySchemaHashtable = new Hashtable[this.groups.length];
            for (int l = 0; l < this.propsByGroup.length; ++l) {
                this.propsByGroup[l] = new Vector();
                this.propertySchemaHashtable[l] = new Hashtable();
                this.groupSchemaHashtable.put(this.groups[l].toLowerCase(), this.propertySchemaHashtable[l]);
            }
            for (int n = 0; n < this.properties.length; ++n) {
                final String s3 = this.properties[n];
                final String[] groups = this.groups(s3, false, false);
                if (groups == null || groups.length <= 0) {
                    PropStorageObject obj = new PropStorageObject(s3);
                    String group;
                    int n2;
                    String s4;
                    for (group = obj.group, n2 = 0; n2 < this.groups.length && group != null; ++n2) {
                        obj = new PropStorageObject(s3);
                        s4 = this.groups[n2];
                        if (!s4.equals(group) && s4.startsWith(group)) {
                            new StringBuffer().append("Property.").append(obj.type()).append(".").append(s4).append(".").append(obj.propName()).toString();
                            obj.setGroup(s4);
                        }
                        if (s4.equalsIgnoreCase(obj.group)) {
                            this.propertySchemaHashtable[n2].put(obj.propName().toLowerCase(), obj.getPropertyAttributes());
                            this.propsByGroup[n2].addElement(obj);
                        }
                    }
                    if (n2 != this.groups.length || obj.group != null) {}
                }
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
    
    protected String getType(final String s) {
        final int index;
        if ((index = s.indexOf(46)) == -1) {
            return null;
        }
        final String substring = s.substring(index + 1);
        final int index2;
        if ((index2 = substring.indexOf(46)) == -1) {
            return null;
        }
        return substring.substring(0, index2);
    }
    
    protected String getGroup(final String s) {
        final int index;
        if ((index = s.indexOf(46)) == -1) {
            return null;
        }
        final String substring = s.substring(index + 1);
        final int index2;
        if ((index2 = substring.indexOf(46)) == -1) {
            return null;
        }
        final String substring2 = substring.substring(index2 + 1);
        final int lastIndex;
        if ((lastIndex = substring2.lastIndexOf(46)) == -1) {
            return null;
        }
        return substring2.substring(0, lastIndex);
    }
    
    protected String getName(final String s) {
        return s.substring(s.lastIndexOf(46) + 1);
    }
    
    protected Object getDynamicValue(final String s, final String s2) throws PropertyException {
        try {
            return Class.forName(this.getProperty(s + ".DynamicPropertyClass")).getMethod(s2, String.class).invoke(null, s);
        }
        catch (Exception previous) {
            if (previous instanceof PropertyException) {
                throw (PropertyException)previous;
            }
            final PropertyException ex = new PropertyException("Nested exception getting dynamic property value. Property: " + s + ".  Method: " + s2, (Object[])null);
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    public class PropStorageObject
    {
        Hashtable propertyAttributes;
        String propName;
        String group;
        String schemaGroup;
        Boolean isMandatory;
        Boolean isArray;
        String type;
        String fullPropName;
        Integer tag;
        String arraySeparator;
        String[] choices;
        Integer defaultChoiceIndex;
        String defaultChoice;
        String defaultValue;
        String minValue;
        String maxValue;
        String displayName;
        String control;
        String validationMethod;
        String choicesMethod;
        Boolean hidden;
        Boolean doValidate;
        String groupPrefix;
        Boolean groupSubsume;
        
        public String propName() {
            return this.propName;
        }
        
        protected void setGroup(final String group) {
            this.addPropertyAttribute("group", this.group = group, true);
        }
        
        public String group() {
            return this.group;
        }
        
        public String schemaGroup() {
            return this.schemaGroup;
        }
        
        public String type() {
            return this.type;
        }
        
        public boolean doValidate() {
            return this.doValidate == null || this.doValidate;
        }
        
        protected void setFullPropName(final String fullPropName) {
            this.addPropertyAttribute("fullname", this.fullPropName = fullPropName, true);
        }
        
        public String fullPropName() {
            return this.fullPropName;
        }
        
        private void addPropertyAttribute(final String s, final Object o) {
            this.addPropertyAttribute(s, o, false);
        }
        
        private void addPropertyAttribute(final String s, Object lowerCase, final boolean b) {
            if (s != null && lowerCase != null) {
                if (b && lowerCase instanceof String) {
                    lowerCase = ((String)lowerCase).toLowerCase();
                }
                this.propertyAttributes.put(s.toLowerCase(), lowerCase);
            }
        }
        
        public Hashtable getPropertyAttributes() {
            return this.propertyAttributes;
        }
        
        PropStorageObject(final String fullPropName) {
            this.propertyAttributes = new Hashtable();
            this.propName = null;
            this.group = null;
            this.schemaGroup = null;
            this.isMandatory = null;
            this.isArray = null;
            this.type = null;
            this.fullPropName = null;
            this.tag = null;
            this.arraySeparator = null;
            this.choices = null;
            this.defaultChoiceIndex = null;
            this.defaultChoice = null;
            this.defaultValue = null;
            this.minValue = null;
            this.maxValue = null;
            this.displayName = null;
            this.control = null;
            this.validationMethod = null;
            this.choicesMethod = null;
            this.hidden = null;
            this.doValidate = null;
            this.groupPrefix = null;
            this.groupSubsume = null;
            if (fullPropName == null) {
                return;
            }
            this.fullPropName = fullPropName;
            final String group = AbstractMetaSchema.this.getGroup(this.fullPropName);
            this.group = group;
            this.schemaGroup = group;
            this.type = AbstractMetaSchema.this.getType(this.fullPropName);
            this.propName = AbstractMetaSchema.this.getName(this.fullPropName);
            if (this.type != null) {
                this.type = this.type.toLowerCase();
            }
            this.control = AbstractMetaSchema.this.getProperty(this.fullPropName + ".Control");
            this.validationMethod = AbstractMetaSchema.this.getProperty(this.fullPropName + ".ValidationMethod");
            this.choicesMethod = AbstractMetaSchema.this.getProperty(this.fullPropName + ".ChoicesMethod");
            this.hidden = new Boolean(AbstractMetaSchema.this.getBooleanProperty(this.fullPropName + ".Hidden"));
            this.displayName = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DisplayName");
            this.tag = new Integer(AbstractMetaSchema.this.getIntProperty(this.fullPropName + ".Tag"));
            final boolean booleanProperty = AbstractMetaSchema.this.getBooleanProperty(this.fullPropName + ".IsMandatory");
            final boolean booleanProperty2 = AbstractMetaSchema.this.getBooleanProperty(this.fullPropName + ".IsArray");
            this.isArray = new Boolean(booleanProperty2);
            this.isMandatory = new Boolean(booleanProperty);
            if (booleanProperty2) {
                this.arraySeparator = AbstractMetaSchema.this.getProperty(this.fullPropName + ".ArraySeparator");
            }
            final String property = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicDisplayName");
            if (property != null) {
                try {
                    this.displayName = (String)AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property);
                }
                catch (PropertyException ex) {}
            }
            final String property2 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicChoices");
            if (property2 != null) {
                try {
                    final int intProperty = AbstractMetaSchema.this.getIntProperty(this.fullPropName + ".DefaultChoice");
                    this.defaultChoiceIndex = new Integer(intProperty);
                    final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property2));
                    if (datatypeModel instanceof IDatatypeModelAsList) {
                        this.choices = ((IDatatypeModelAsList)datatypeModel).getValuesAsString();
                        if (intProperty >= 0) {
                            final String s = this.choices[intProperty];
                            this.defaultChoice = s;
                            this.defaultValue = s;
                        }
                    }
                }
                catch (XPropertyException ex2) {}
                catch (PropertyException ex3) {}
            }
            else {
                this.choices = AbstractMetaSchema.this.getArrayProperty(this.fullPropName + ".Choices");
                if (this.choices != null) {
                    final int intProperty2 = AbstractMetaSchema.this.getIntProperty(this.fullPropName + ".DefaultChoice");
                    this.defaultChoiceIndex = new Integer(intProperty2);
                    if (intProperty2 >= 0) {
                        final String s2 = this.choices[intProperty2];
                        this.defaultChoice = s2;
                        this.defaultValue = s2;
                    }
                }
            }
            try {
                if (this.type != null) {
                    if (this.type.equals("string")) {
                        if (this.control == null) {
                            this.control = "TextField";
                        }
                        if (this.defaultValue == null) {
                            final String property3 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicDefault");
                            if (property3 != null) {
                                this.defaultValue = (String)AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property3);
                            }
                            else {
                                this.defaultValue = AbstractMetaSchema.this.getProperty(this.fullPropName + ".Default");
                                if (this.defaultValue != null) {
                                    this.defaultValue = this.defaultValue.trim();
                                    if (this.defaultValue.startsWith("\"") && this.defaultValue.endsWith("\"")) {
                                        this.defaultValue = this.defaultValue.substring(1, this.defaultValue.length() - 1);
                                    }
                                }
                            }
                        }
                    }
                    else if (this.type.equals("boolean")) {
                        if (this.control == null) {
                            this.control = "CheckBox";
                        }
                        if (this.defaultValue == null) {
                            final String property4 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicDefault");
                            if (property4 != null) {
                                this.defaultValue = ((Boolean)AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property4)).toString();
                            }
                            else {
                                this.defaultValue = AbstractMetaSchema.this.getProperty(this.fullPropName + ".Default");
                            }
                        }
                    }
                    else if (this.type.equals("integer") || this.type.equals("long") || this.type.equals("float") || this.type.equals("double")) {
                        if (this.control == null) {
                            this.control = "TextField";
                        }
                        final String property5 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicMin");
                        final String property6 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicMax");
                        int i;
                        if (property5 != null) {
                            i = (int)AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property5);
                        }
                        else {
                            i = AbstractMetaSchema.this.getIntProperty(this.fullPropName + ".Min");
                        }
                        int j;
                        if (property6 != null) {
                            j = (int)AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property6);
                        }
                        else {
                            j = AbstractMetaSchema.this.getIntProperty(this.fullPropName + ".Max");
                        }
                        this.minValue = i + "";
                        this.maxValue = j + "";
                        final String property7 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicDefault");
                        if (property7 != null) {
                            this.defaultValue = ((Integer)AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property7)).toString();
                        }
                        else {
                            this.defaultValue = AbstractMetaSchema.this.getProperty(this.fullPropName + ".Default");
                        }
                    }
                    else if (this.type.equals("group")) {
                        if (this.defaultValue == null) {
                            final String property8 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".DynamicDefault");
                            if (property8 != null) {
                                this.defaultValue = (String)AbstractMetaSchema.this.getDynamicValue(this.fullPropName, property8);
                            }
                            else {
                                this.defaultValue = AbstractMetaSchema.this.getProperty(this.fullPropName + ".Default");
                            }
                        }
                        this.groupSubsume = new Boolean(AbstractMetaSchema.this.getBooleanProperty(this.fullPropName + ".Subsume"));
                        this.groupPrefix = AbstractMetaSchema.this.getProperty(this.fullPropName + ".Prefix");
                        final String property9 = AbstractMetaSchema.this.getProperty(this.fullPropName + ".Validate");
                        if (property9 != null) {
                            this.doValidate = new Boolean(property9);
                        }
                        else {
                            this.doValidate = new Boolean(true);
                        }
                    }
                }
            }
            catch (PropertyException ex4) {}
            if (this.choices != null && this.control == null) {
                this.control = "ListBox";
            }
            this.addPropertyAttribute("fullname", this.fullPropName, true);
            this.addPropertyAttribute("group", this.group, true);
            this.addPropertyAttribute("schemagroup", this.schemaGroup, true);
            this.addPropertyAttribute("type", this.type, true);
            this.addPropertyAttribute("name", this.propName, true);
            this.addPropertyAttribute("displayname", this.displayName);
            this.addPropertyAttribute("hidden", this.hidden);
            this.addPropertyAttribute("controltype", this.control);
            this.addPropertyAttribute("validationmethod", this.validationMethod);
            this.addPropertyAttribute("choicesmethod", this.choicesMethod);
            this.addPropertyAttribute("tag", this.tag);
            this.addPropertyAttribute("ismandatory", this.isMandatory);
            this.addPropertyAttribute("isarray", this.isArray);
            this.addPropertyAttribute("isarrayseparator", this.arraySeparator);
            this.addPropertyAttribute("choices", this.choices);
            this.addPropertyAttribute("defaultchoiceindex", this.defaultChoiceIndex);
            this.addPropertyAttribute("defaultchoice", this.defaultChoice);
            this.addPropertyAttribute("defaultvalue", this.defaultValue);
            this.addPropertyAttribute("minvalue", this.minValue);
            this.addPropertyAttribute("maxvalue", this.maxValue);
            this.addPropertyAttribute("subsume", this.groupSubsume);
            this.addPropertyAttribute("prefix", this.groupPrefix, true);
            this.addPropertyAttribute("validate", this.doValidate, true);
        }
    }
}

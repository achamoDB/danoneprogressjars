// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import com.progress.chimera.common.Tools;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.progress.international.resources.ProgressResources;
import java.util.Enumeration;
import java.io.InputStream;
import com.progress.common.property.PropertyManager;
import java.util.Vector;
import java.util.Hashtable;
import com.progress.common.property.AbstractMetaSchema;

public class GUIMetaSchema extends AbstractMetaSchema
{
    Hashtable definitions;
    PropertyCategory[] categoryList;
    Vector categoryRoots;
    
    protected GUIMetaSchema(final String s) throws PropertyException {
        super(s);
    }
    
    protected GUIMetaSchema(final InputStream inputStream) throws PropertyException {
        super(inputStream);
    }
    
    public static GUIMetaSchema instantiate(final String str) throws XPropertyException {
        GUIMetaSchema guiMetaSchema;
        try {
            guiMetaSchema = new GUIMetaSchema(str);
        }
        catch (PropertyException previous) {
            final XPropertyException ex = new XPropertyException("GUIMetaSchema load failed: " + str);
            ex.setPrevious(previous);
            throw ex;
        }
        return guiMetaSchema;
    }
    
    int findGroupIndex(final String anotherString) {
        for (int i = 0; i < super.groups.length; ++i) {
            if (super.groups[i].equalsIgnoreCase(anotherString)) {
                return i;
            }
        }
        return -1;
    }
    
    protected void addPropsByGroup(final int n, final Vector vector) throws XPropertyException {
        final Enumeration<PropStorageObject> elements = super.propsByGroup[n].elements();
        while (elements.hasMoreElements()) {
            vector.insertElementAt(this.makePropertyObject(elements.nextElement()), 0);
        }
    }
    
    protected void instantiateSchema() throws PropertyException {
        super.instantiateSchema();
        this.definitions = new Hashtable();
        try {
            for (int i = 0; i < super.groups.length; ++i) {
                if (this.getBooleanProperty("Group." + super.groups[i] + ".UI")) {
                    final Vector vector = new Vector();
                    this.addPropsByGroup(i, vector);
                    this.definitions.put(super.groups[i].toLowerCase(), new PropertySetDefinition(vector));
                }
            }
            this.categoryList = new PropertyCategory[super.categories.length];
            for (int j = 0; j < super.categories.length; ++j) {
                final String s = super.categories[j];
                String property = this.getProperty("Category." + s + ".JavaResourceBundlePrefix");
                if (property == null) {
                    property = "";
                }
                String property2 = this.getProperty("Category." + s + ".JavaResourceBundle");
                if (property2 == null) {
                    property2 = "";
                }
                String tranString2;
                String tranString = tranString2 = s;
                try {
                    final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle(property + property2);
                    tranString = progressResources.getTranString(s);
                    tranString2 = progressResources.getTranString(s + "_D");
                }
                catch (MissingResourceException ex2) {}
                this.categoryList[j] = new PropertyCategory(s, tranString, tranString2, null, this.getProperty("Category." + s + ".Form"));
            }
            this.categoryRoots = new Vector();
            if (this.categoryList != null) {
                for (int k = 0; k < this.categoryList.length; ++k) {
                    this.categoryList[k].setParent(this.findCategory(this.getProperty("Category." + super.categories[k] + ".Parent")));
                }
                for (int l = 0; l < this.categoryList.length; ++l) {
                    final PropertyCategory propertyCategory = this.categoryList[l];
                    PropertyCategory propertyCategory2 = this.categoryList[l];
                    for (PropertyCategory propertyCategory3 = propertyCategory.parent(); propertyCategory3 != null; propertyCategory3 = propertyCategory3.parent()) {
                        propertyCategory2 = propertyCategory3;
                    }
                    if (!this.categoryRoots.contains(propertyCategory2)) {
                        this.categoryRoots.addElement(propertyCategory2);
                    }
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
    
    public PropertyCategory findCategory(final String anotherString) {
        if (anotherString == null) {
            return null;
        }
        for (int i = 0; i < this.categoryList.length; ++i) {
            if (this.categoryList[i].name().equalsIgnoreCase(anotherString)) {
                return this.categoryList[i];
            }
        }
        return null;
    }
    
    public Enumeration getRootDefinitions() {
        return this.categoryRoots.elements();
    }
    
    public PropertySetDefinition findDefintion(final String s) {
        return this.definitions.get(s.toLowerCase());
    }
    
    protected Object getDynamicValue(final String s, final String s2) throws PropertyException {
        return super.getDynamicValue(s, s2);
    }
    
    protected String getType(final String s) {
        return super.getType(s);
    }
    
    protected void initialize(final String str, final PropertySetDefinition propertySetDefinition, final PropertyValueSet set) throws InstantiationException, XPropertyException {
        try {
            int n = -1;
            final Enumeration<PropertyDefinition> elements = (Enumeration<PropertyDefinition>)propertySetDefinition.properties.elements();
            while (elements.hasMoreElements()) {
                ++n;
                final PropertyDefinition propertyDefinition = elements.nextElement();
                final Class datatype = propertyDefinition.datatype();
                Object o;
                IPropertyDatatype propertyDatatype;
                if (Tools.isaSubtype(datatype, IDatatypeModelAsChoices.class)) {
                    if (Tools.isaSubtype(datatype, IDatatypeWrapper.class)) {
                        o = DatatypeModelMapping.makeInstance(propertyDefinition.datatype());
                        propertyDatatype = DatatypeModelMapping.getDatatypeModel(o);
                        final String property = this.getProperty(propertyDefinition.schemaKey() + ".DynamicChoices");
                        if (property != null) {
                            final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(this.getDynamicValue(propertyDefinition.schemaKey(), property));
                            if (!(datatypeModel instanceof IDatatypeModelAsList)) {
                                throw new XPropertyException("Dynamic choices not list model: " + str);
                            }
                            ((IDatatypeModelAsDynamicChoices)propertyDatatype).setChoices(((IDatatypeModelAsList)datatypeModel).getValues());
                        }
                    }
                    else {
                        o = DatatypeModelMapping.makeInstance(propertyDefinition.datatype());
                        propertyDatatype = DatatypeModelMapping.getDatatypeModel(o);
                        final String[] arrayProperty = this.getArrayProperty(propertyDefinition.schemaKey() + ".Choices");
                        if (arrayProperty != null) {
                            final IPropertyDatatype datatypeModel2 = DatatypeModelMapping.getDatatypeModel(propertyDefinition.baseDatatypeInstance());
                            final IDatatypeModelAsDynamicChoices datatypeModelAsDynamicChoices = (IDatatypeModelAsDynamicChoices)propertyDatatype;
                            final Object[] choices = new Object[arrayProperty.length];
                            for (int i = 0; i < arrayProperty.length; ++i) {
                                choices[i] = datatypeModel2.toObject(arrayProperty[i]);
                            }
                            datatypeModelAsDynamicChoices.setChoices(choices);
                        }
                    }
                }
                else if (Tools.isaSubtype(datatype, GroupReference.class)) {
                    final String property2 = this.getProperty(propertyDefinition.schemaKey() + ".Prefix");
                    if (Tools.isaSubtype(datatype, GroupReferenceSubsumed.class)) {
                        o = new GroupReferenceSubsumed(set, property2);
                    }
                    else {
                        o = new GroupReference(set, property2);
                    }
                    propertyDatatype = DatatypeModelMapping.getDatatypeModel(o);
                }
                else {
                    o = DatatypeModelMapping.makeInstance(propertyDefinition.datatype());
                    propertyDatatype = DatatypeModelMapping.getDatatypeModel(o);
                }
                if (propertyDatatype instanceof IDatatypeModelAsDynamicNumericRange) {
                    final IDatatypeModelAsDynamicNumericRange datatypeModelAsDynamicNumericRange = (IDatatypeModelAsDynamicNumericRange)propertyDatatype;
                    final MinMax minMax = new MinMax(propertyDefinition.schemaKey(), this);
                    datatypeModelAsDynamicNumericRange.setLowerBound(minMax.min);
                    datatypeModelAsDynamicNumericRange.setUpperBound(minMax.max);
                }
                if (propertyDatatype instanceof IDatatypeModelAsChoices) {
                    final IDatatypeModelAsDynamicChoices datatypeModelAsDynamicChoices2 = (IDatatypeModelAsDynamicChoices)propertyDatatype;
                    datatypeModelAsDynamicChoices2.setValuesByIndices(new Integer[] { new Integer(this.getIntProperty(propertyDefinition.schemaKey() + ".DefaultChoice")) });
                    o = datatypeModelAsDynamicChoices2;
                }
                set.setValue(n, o);
                if (propertyDefinition.baseDatatypeInstance() instanceof GroupReference) {
                    final String property3 = this.getProperty(propertyDefinition.schemaKey() + ".Prefix");
                    if (propertyDefinition.baseDatatypeInstance() instanceof GroupReferenceSubsumed) {
                        propertyDefinition.setBaseDatatypeInstance(new GroupReferenceSubsumed(set, property3));
                    }
                    else {
                        propertyDefinition.setBaseDatatypeInstance(new GroupReference(set, property3));
                    }
                }
            }
        }
        catch (PropertyException previous) {
            final XPropertyException ex = new XPropertyException("Error initializing property value set");
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    public PropertyValueSet makePVS(final String s) throws XPropertyException {
        try {
            final PropertySetDefinition defintion = this.findDefintion(s);
            final PropertyValueSet set = new PropertyValueSet(defintion);
            this.initialize(s, defintion, set);
            return set;
        }
        catch (InstantiationException previous) {
            final XPropertyException ex = new XPropertyException("Can't instantiate elements of PVS");
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    protected PropertyDefinition makePropertyObject(final PropStorageObject propStorageObject) throws XPropertyException {
        String property = this.getProperty("Group." + propStorageObject.group() + ".JavaResourceBundlePrefix");
        if (property == null) {
            property = "";
        }
        String property2 = this.getProperty("Group." + propStorageObject.group() + ".JavaResourceBundle");
        if (property2 == null) {
            property2 = "";
        }
        String s;
        String tranString = s = propStorageObject.propName();
        try {
            final ProgressResources progressResources = (ProgressResources)ResourceBundle.getBundle(property + property2);
            tranString = progressResources.getTranString(propStorageObject.propName());
            s = progressResources.getTranString(propStorageObject.propName() + "_D");
        }
        catch (MissingResourceException ex) {}
        return this.makePropertyDefiniton(propStorageObject, tranString, s);
    }
    
    Class getStringClassHelper() {
        try {
            return Class.forName("java.lang.String");
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    PropertyDefinition makePropertyDefiniton(final PropStorageObject propStorageObject, final String s, final String s2) throws XPropertyException {
        Class clazz = null;
        Class<?> clazz2 = null;
        Object instance = null;
        try {
            if (this.getProperty(propStorageObject.fullPropName() + ".Choices") != null) {
                if (this.getBooleanProperty(propStorageObject.fullPropName() + ".isArray")) {
                    clazz = MultipleChoiceVector.class;
                }
                else {
                    clazz = ChoiceVector.class;
                }
            }
            else if (this.getProperty(propStorageObject.fullPropName() + ".DynamicChoices") != null) {
                if (this.getBooleanProperty(propStorageObject.fullPropName() + ".isArray")) {
                    clazz = MultipleChoiceArrayWrapper.class;
                }
                else {
                    clazz = ChoiceVectorWrapper.class;
                }
            }
            else if (propStorageObject.type().equalsIgnoreCase("Enum")) {
                if (this.getBooleanProperty(propStorageObject.fullPropName() + ".isArray")) {
                    clazz = MultipleChoiceVector.class;
                }
                else {
                    clazz = ChoiceVector.class;
                }
            }
            else if (this.getBooleanProperty(propStorageObject.fullPropName() + ".isArray")) {
                clazz = Vector.class;
            }
            String str = this.getProperty(propStorageObject.fullPropName() + ".JavaType");
            if (str != null && !str.equals("")) {
                if (this.getProperty(propStorageObject.fullPropName() + ".JavaTypePrefix") != null && !str.startsWith(".")) {
                    str = this.getProperty(propStorageObject.fullPropName() + ".JavaTypePrefix") + str;
                }
                if (str.startsWith(".")) {
                    str = str.substring(1);
                }
                try {
                    clazz2 = Class.forName(str);
                }
                catch (ClassNotFoundException previous) {
                    new XPropertyException("Class load failed for property: " + propStorageObject.fullPropName() + ". ClassName: " + str).setPrevious(previous);
                }
            }
            if (clazz2 == null) {
                if (propStorageObject.type().equalsIgnoreCase("String")) {
                    clazz2 = (Class<?>)this.getStringClassHelper();
                }
                else if (propStorageObject.type().equalsIgnoreCase("Boolean")) {
                    clazz2 = Boolean.class;
                }
                else if (propStorageObject.type().equalsIgnoreCase("Integer")) {
                    clazz2 = IntegerRange.class;
                    clazz = null;
                }
                else if (propStorageObject.type().equalsIgnoreCase("Long")) {
                    clazz2 = IntegerRange.class;
                }
                else if (propStorageObject.type().equalsIgnoreCase("Float")) {
                    clazz2 = Float.class;
                }
                else if (propStorageObject.type().equalsIgnoreCase("Double")) {
                    clazz2 = Double.class;
                }
                else if (propStorageObject.type().equalsIgnoreCase("Enum")) {
                    clazz2 = (Class<?>)this.getStringClassHelper();
                }
                else if (propStorageObject.type().equalsIgnoreCase("Group")) {
                    if (this.getBooleanProperty(propStorageObject.fullPropName() + ".Subsume")) {
                        clazz2 = GroupReferenceSubsumed.class;
                        instance = new GroupReferenceSubsumed();
                    }
                    else {
                        clazz2 = GroupReference.class;
                        instance = new GroupReference();
                    }
                }
            }
            if (clazz == null) {
                clazz = clazz2;
            }
            if (instance == null) {
                instance = DatatypeModelMapping.makeInstance(clazz2);
                final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(instance);
                if (datatypeModel instanceof IDatatypeModelAsDynamicNumericRange) {
                    final IDatatypeModelAsDynamicNumericRange datatypeModelAsDynamicNumericRange = (IDatatypeModelAsDynamicNumericRange)datatypeModel;
                    final MinMax minMax = new MinMax(propStorageObject.fullPropName(), this);
                    datatypeModelAsDynamicNumericRange.setLowerBound(minMax.min);
                    datatypeModelAsDynamicNumericRange.setUpperBound(minMax.max);
                }
                if (datatypeModel instanceof IDatatypeModelAsChoices) {
                    ((IDatatypeModelAsChoices)datatypeModel).setValuesByIndices(new Integer[] { new Integer(this.getIntProperty(propStorageObject.fullPropName() + ".DefaultChoice")) });
                }
            }
        }
        catch (Exception previous2) {
            if (previous2 instanceof XPropertyException) {
                throw (XPropertyException)previous2;
            }
            final XPropertyException ex = new XPropertyException("Error whilst creating property definition for property: " + propStorageObject.propName());
            ex.setPrevious(previous2);
            throw ex;
        }
        return new PropertyDefinition(propStorageObject.propName(), clazz, instance, s, s2, false, propStorageObject.fullPropName());
    }
    
    class MinMax
    {
        Number min;
        Number max;
        
        MinMax(final String s, final GUIMetaSchema guiMetaSchema) throws PropertyException {
            final String type = guiMetaSchema.getType(s);
            final String property = GUIMetaSchema.this.getProperty(s + ".DynamicMin");
            final String property2 = GUIMetaSchema.this.getProperty(s + ".DynamicMax");
            if (property != null) {
                this.min = (Number)guiMetaSchema.getDynamicValue(s, property);
            }
            else {
                final String property3 = GUIMetaSchema.this.getProperty(s + ".Min");
                if (type.equalsIgnoreCase("Integer")) {
                    this.min = Integer.valueOf(property3);
                }
                else if (type.equalsIgnoreCase("Float")) {
                    this.min = Float.valueOf(property3);
                }
                else if (type.equalsIgnoreCase("Double")) {
                    this.min = Double.valueOf(property3);
                }
                else if (type.equalsIgnoreCase("Long")) {
                    this.min = Long.valueOf(property3);
                }
            }
            if (property2 != null) {
                this.max = (Number)guiMetaSchema.getDynamicValue(s, property2);
            }
            else {
                final String property4 = GUIMetaSchema.this.getProperty(s + ".Max");
                if (type.equalsIgnoreCase("Integer")) {
                    this.max = Integer.valueOf(property4);
                }
                else if (type.equalsIgnoreCase("Float")) {
                    this.max = Float.valueOf(property4);
                }
                else if (type.equalsIgnoreCase("Double")) {
                    this.max = Double.valueOf(property4);
                }
                else if (type.equalsIgnoreCase("Long")) {
                    this.max = Long.valueOf(property4);
                }
            }
        }
    }
}

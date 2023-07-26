// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;
import java.util.Vector;

public class PropertySetDefinition implements IPropertySetDefinition
{
    protected Vector categories;
    protected Vector properties;
    protected Vector valueSets;
    
    public PropertySetDefinition() {
        this.categories = new Vector();
        this.properties = null;
        this.valueSets = null;
    }
    
    public PropertySetDefinition(final Vector vector) {
        this.categories = new Vector();
        this.properties = null;
        this.valueSets = null;
        this.assignProps(vector);
    }
    
    public void assignProps(final Vector properties) {
        this.properties = properties;
        if (this.properties == null) {
            return;
        }
        for (int i = 0; i < this.properties.size(); ++i) {
            final PropertyDefinition propertyDefinition = this.properties.elementAt(i);
        }
    }
    
    public void addValueSet(final PropertyValueSet obj) {
        if (this.valueSets == null) {
            this.valueSets = new Vector();
        }
        this.valueSets.addElement(obj);
    }
    
    public int indexOf(final String str) throws XPropertyDoesNotExist {
        for (int i = 0; i < this.properties.size(); ++i) {
            if (str.equalsIgnoreCase(((PropertyDefinition)this.properties.elementAt(i)).name)) {
                return i;
            }
        }
        throw new XPropertyDoesNotExist("Missing property in definition: " + str);
    }
    
    protected PropertyDefinition getDefinition(final int index) {
        return this.properties.elementAt(index);
    }
    
    private String generateClassString(final String str) {
        String s = "class " + str + " extends ChimeraProperties" + "\n{\n";
        for (int i = 0; i < this.properties.size(); ++i) {
            final PropertyDefinition propertyDefinition = this.properties.elementAt(i);
            s = s + "   " + propertyDefinition.datatype.getName() + " " + propertyDefinition.name + ";\n";
        }
        return s + "}";
    }
    
    public Enumeration valueSets() {
        return this.valueSets.elements();
    }
    
    public Enumeration propertyDefinitions() {
        return this.properties.elements();
    }
}

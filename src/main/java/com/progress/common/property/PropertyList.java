// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import java.util.Vector;
import java.io.Serializable;

public class PropertyList implements Serializable
{
    private Vector propertyList;
    
    public PropertyList() {
        this.propertyList = new Vector();
    }
    
    public Vector getPropertyElements() {
        return this.propertyList;
    }
    
    public void addProperty(final String s, final String s2, final String s3, final String[] array) {
        this.propertyList.addElement(new PropertyElement(s, s2, s3, array));
    }
    
    public void addProperty(final String s, final String s2, final String s3, final Vector vector) {
        this.propertyList.addElement(new PropertyElement(s, s2, s3, vector));
    }
    
    public int size() {
        return this.propertyList.size();
    }
    
    public boolean isEmpty() {
        return this.propertyList.size() == 0;
    }
    
    public Vector getPropertyNames() {
        final Vector<String> vector = new Vector<String>();
        for (int i = 0; i < this.propertyList.size(); ++i) {
            vector.addElement(((PropertyElement)this.propertyList.elementAt(i)).getPropertyFullName());
        }
        return vector;
    }
    
    public String getPropertyName(final int index) {
        return this.propertyList.elementAt(index).getPropertyFullName();
    }
    
    public Vector getPropertyValues() {
        final Vector<Vector> vector = new Vector<Vector>();
        for (int i = 0; i < this.propertyList.size(); ++i) {
            vector.addElement(((PropertyElement)this.propertyList.elementAt(i)).propertyValues);
        }
        return vector;
    }
    
    public Vector getPropertyValue(final int index) {
        if (this.propertyList != null && index < this.propertyList.size()) {
            return this.propertyList.elementAt(index).propertyValues;
        }
        return null;
    }
    
    public Vector getPropertyValue(final String anotherString) {
        for (int i = 0; i < this.propertyList.size(); ++i) {
            final PropertyElement propertyElement = this.propertyList.elementAt(i);
            if (propertyElement.getPropertyFullName().equalsIgnoreCase(anotherString)) {
                return propertyElement.propertyValues;
            }
        }
        return null;
    }
    
    public class PropertyElement
    {
        String UNKNOWN;
        String pluginName;
        String propertyName;
        String propertyGroup;
        Vector propertyValues;
        
        public PropertyElement(final String pluginName, final String propertyGroup, final String propertyName, final String[] array) {
            this.UNKNOWN = "Unknown";
            this.pluginName = null;
            this.propertyName = null;
            this.propertyGroup = null;
            this.propertyValues = new Vector();
            this.pluginName = pluginName;
            this.propertyGroup = propertyGroup;
            this.propertyName = propertyName;
            for (int n = 0; array != null && n < array.length; ++n) {
                this.propertyValues.addElement(array[n]);
            }
        }
        
        public PropertyElement(final String pluginName, final String propertyGroup, final String propertyName, final Vector propertyValues) {
            this.UNKNOWN = "Unknown";
            this.pluginName = null;
            this.propertyName = null;
            this.propertyGroup = null;
            this.propertyValues = new Vector();
            this.pluginName = pluginName;
            this.propertyGroup = propertyGroup;
            this.propertyName = propertyName;
            this.propertyValues = propertyValues;
        }
        
        public String getPlugInName() {
            if (this.pluginName == null) {
                return this.UNKNOWN;
            }
            return this.pluginName;
        }
        
        public String getPropertyGroup() {
            if (this.propertyGroup == null) {
                return this.UNKNOWN;
            }
            return this.propertyGroup;
        }
        
        public String getPropertyName() {
            if (this.propertyName == null) {
                return this.UNKNOWN;
            }
            return this.propertyName;
        }
        
        public String getPropertyFullName() {
            if (this.propertyGroup == null || this.propertyName == null) {
                return this.UNKNOWN;
            }
            return this.propertyGroup + "." + this.propertyName;
        }
        
        public Vector getPropertyValues() {
            if (this.propertyValues == null) {
                return new Vector();
            }
            return this.propertyValues;
        }
    }
}

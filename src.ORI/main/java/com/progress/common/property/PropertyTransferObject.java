// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.property;

import com.progress.chimera.common.Tools;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Serializable;

public class PropertyTransferObject implements Serializable
{
    String groupName;
    private int lastElement;
    public Vector elements;
    
    public Enumeration keys() {
        final Vector<String> vector = new Vector<String>();
        final Enumeration<Element> elements = this.elements.elements();
        while (elements.hasMoreElements()) {
            vector.addElement(elements.nextElement().key);
        }
        return vector.elements();
    }
    
    public Enumeration values() {
        final Vector<String> vector = new Vector<String>();
        final Enumeration<Element> elements = this.elements.elements();
        while (elements.hasMoreElements()) {
            vector.addElement(elements.nextElement().values[0]);
        }
        return vector.elements();
    }
    
    public Vector elements() {
        return this.elements;
    }
    
    public int size() {
        return this.elements.size();
    }
    
    public boolean isEmpty() {
        return this.elements.size() == 0;
    }
    
    public Object put(final String s, final String[] array) {
        ++this.lastElement;
        this.elements.addElement(new Element(s, array));
        return array;
    }
    
    Element find(final Object anObject) {
        final Enumeration<Element> elements = this.elements.elements();
        while (elements.hasMoreElements()) {
            final Element element = elements.nextElement();
            if (element.key.equals(anObject)) {
                return element;
            }
        }
        return null;
    }
    
    public Object get(final Object o) {
        final Element find = this.find(o);
        if (find != null) {
            return find.values;
        }
        return null;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
    
    public PropertyTransferObject() {
        this.lastElement = -1;
        this.elements = new Vector();
    }
    
    public PropertyTransferObject(final String groupName) {
        this();
        this.groupName = groupName;
    }
    
    public PropertyTransferObject(final PropertyManager propertyManager, final String s) {
        this(s);
        String name = null;
        try {
            final PropertyManager.PropertyType propertyType = propertyManager.getPropertyType(s);
            if (propertyType.getPropertyType() == PropertyManager.PROPERTY_METRIC || propertyType.getPropertyType() == PropertyManager.PROPERTY_WILDCARD || propertyType.getPropertyType() == PropertyManager.PROPERTY_NAME) {
                final PropertyList list = new PropertyList();
                propertyManager.makePropertyListElements(list, null, s);
                final Vector propertyElements = list.getPropertyElements();
                for (int i = 0; i < propertyElements.size(); ++i) {
                    final PropertyList.PropertyElement propertyElement = propertyElements.elementAt(i);
                    final String propertyName = propertyElement.getPropertyName();
                    final Vector propertyValues = propertyElement.getPropertyValues();
                    final String[] array = new String[propertyValues.size()];
                    for (int j = 0; j < propertyValues.size(); ++j) {
                        array[j] = propertyValues.elementAt(j);
                    }
                    this.put(propertyName, array);
                }
            }
            else if (propertyType.getPropertyType() == PropertyManager.PROPERTY_GROUP) {
                propertyManager.properties(s, true, true);
                final PropertyManager.PropertyCollection properties = propertyManager.properties(s, true, true);
                while (properties.hasMoreElements()) {
                    final PropertyManager.Property property = properties.nextElement();
                    try {
                        name = property.getName();
                        if (property == null) {
                            continue;
                        }
                        this.put(name, PropertyManager.parseArrayProperty(property.getValueOrDefault(), property.arraySeparator(), property.canHaveMultipleValues()));
                    }
                    catch (Throwable t) {
                        Tools.px(t, "PropertyTransferObject:  Failed to load property " + name);
                    }
                }
            }
        }
        catch (Throwable t2) {
            Tools.px(t2, "PropertyTransferObject:  Failed loading properties.");
        }
    }
    
    public Object remove(final Object o) {
        final Element find = this.find(o);
        if (find != null) {
            this.elements.removeElement(find);
            return o;
        }
        return null;
    }
    
    public class Element implements Serializable
    {
        private String key;
        private String[] values;
        
        Element(final String key, final String[] values) {
            this.key = key;
            this.values = values;
        }
        
        public String key() {
            return this.key;
        }
        
        public String[] values() {
            return this.values;
        }
    }
}

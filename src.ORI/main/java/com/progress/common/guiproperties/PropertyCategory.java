// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;
import java.util.Vector;

public class PropertyCategory
{
    protected String name;
    protected PropertyCategory parent;
    protected String descriptionShort;
    protected String descriptionLong;
    protected Vector children;
    protected Object userData;
    
    public String getDisplayName() {
        return this.descriptionShort;
    }
    
    public Object getUserData() {
        return this.userData;
    }
    
    public PropertyCategory(final String name, final String descriptionShort, final String descriptionLong, final PropertyCategory parent, final Object userData) {
        this.name = null;
        this.parent = null;
        this.descriptionShort = null;
        this.descriptionLong = null;
        this.children = new Vector();
        this.userData = null;
        this.name = name;
        this.descriptionShort = descriptionShort;
        this.descriptionLong = descriptionLong;
        this.parent = parent;
        this.userData = userData;
        if (this.parent != null) {
            this.parent.setChild(this);
        }
    }
    
    protected void setChild(final PropertyCategory propertyCategory) {
        if (!this.children.contains(propertyCategory)) {
            this.children.addElement(propertyCategory);
        }
    }
    
    public String name() {
        return this.name;
    }
    
    public String description(final boolean b) {
        if (b) {
            return this.descriptionLong;
        }
        return this.descriptionShort;
    }
    
    public PropertyCategory parent() {
        return this.parent;
    }
    
    void setParent(final PropertyCategory parent) {
        this.parent = parent;
        if (this.parent != null) {
            this.parent.setChild(this);
        }
    }
    
    public Object userData() {
        return this.userData;
    }
    
    public void removeChild(final PropertyCategory propertyCategory) {
        if (this.children.contains(propertyCategory)) {
            this.children.removeElement(propertyCategory);
        }
    }
    
    public Enumeration children() {
        return this.children.elements();
    }
}

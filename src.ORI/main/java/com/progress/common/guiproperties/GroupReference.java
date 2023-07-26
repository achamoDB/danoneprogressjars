// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.rmi.RemoteException;

public class GroupReference implements IDatatypeModelAsString
{
    String name;
    String prefix;
    PropertyValueSet pvs;
    
    GroupReference() {
        this.name = "NAME";
        this.prefix = "PREFIX";
        this.pvs = null;
    }
    
    GroupReference(final PropertyValueSet pvs, final String prefix) {
        this.name = "NAME";
        this.prefix = "PREFIX";
        this.pvs = null;
        this.pvs = pvs;
        this.prefix = prefix;
    }
    
    GroupReference(final PropertyValueSet set, final String s, final String s2) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        this(set, s, s2, true);
    }
    
    GroupReference(final PropertyValueSet pvs, final String s, final String prefix, final boolean b) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        this.name = "NAME";
        this.prefix = "PREFIX";
        this.pvs = null;
        this.prefix = prefix;
        this.pvs = pvs;
        if (b) {
            this.setValueAsString(s);
        }
        else {
            this.name = s;
        }
    }
    
    public void setValues(final Object[] array) throws XPropertyException {
        if (array.length > 1) {
            throw new XPropertyException("Too many values");
        }
        this.setValue(array[0]);
    }
    
    String fullName() {
        return this.prefix + this.name;
    }
    
    public Object getValue() {
        return this.name;
    }
    
    public Object[] getValues() {
        return new Object[] { this.name };
    }
    
    public String groupName() {
        return this.fullName();
    }
    
    public String getValueAsString() {
        return this.name;
    }
    
    public String[] getValuesAsString() {
        return new String[] { this.name };
    }
    
    public void setValueAsString(final String name) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        if (!this.isValidValueAsString(name)) {
            throw new XPropertyValueIsNotValid(this.fullName());
        }
        this.name = name;
    }
    
    public boolean isValidValueAsString(final String s) throws XPropertyException {
        try {
            return this.pvs.remotePropertyManager.groupExists(this.prefix + s);
        }
        catch (RemoteException previous) {
            final XPropertyException ex = new XPropertyException("Error testing group existence: " + this.prefix + s);
            ex.setPrevious(previous);
            throw ex;
        }
    }
    
    public Object toObject(final String s) throws XPropertyException, XPropertyValueIsNotValid {
        return new GroupReference(this.pvs, s, this.prefix);
    }
    
    public String normalize(final String s) throws XPropertyValueIsNotValid {
        return s;
    }
    
    public void setValue(final Object o) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        if (o instanceof String) {
            this.setValueAsString((String)o);
            return;
        }
        throw new XPropertyValueIsNotValid(o.toString());
    }
    
    public boolean isValidValue(final Object o) throws XPropertyException {
        return o instanceof String && this.isValidValueAsString((String)o);
    }
    
    public String toString() {
        final String valueAsString = this.getValueAsString();
        return valueAsString.substring(valueAsString.lastIndexOf(".") + 1);
    }
}

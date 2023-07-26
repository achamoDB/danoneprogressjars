// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;

abstract class ChoicesSurrogate extends DatatypeSurrogate implements IDatatypeModelAsChoices, IDatatypeModelAsString
{
    IDatatypeModelAsChoices delegateObject;
    
    public void setValues(final Object[] values) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        this.delegateObject.setValues(values);
    }
    
    public void setValuesByIndices(final Integer[] valuesByIndices) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        this.delegateObject.setValuesByIndices(valuesByIndices);
    }
    
    public void setValue(final Object o) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        this.delegateObject.setValues(new Object[] { o });
    }
    
    public boolean isValidValue(final Object o) throws XPropertyException {
        return this.delegateObject.isValidValue(o);
    }
    
    public int getCurrentChoiceByIndex() throws XPropertyException {
        return this.delegateObject.getValuesByIndices()[0];
    }
    
    public Object[] getValues() throws XPropertyException {
        return this.delegateObject.getValues();
    }
    
    public Object getValue() throws XPropertyException {
        return this.delegateObject.getValues()[0];
    }
    
    public Integer[] getValuesByIndices() throws XPropertyException {
        return this.delegateObject.getValuesByIndices();
    }
    
    public Enumeration getChoices() throws XPropertyException {
        return this.delegateObject.getChoices();
    }
    
    public int findIndex(final Object o) throws XPropertyException {
        return this.delegateObject.findIndex(o);
    }
    
    public Object getCurrentChoice() throws XPropertyException {
        return this.delegateObject.getValues()[0];
    }
    
    public String getValueAsString() throws XPropertyException {
        return ((IDatatypeModelAsString)DatatypeModelMapping.getDatatypeModel(this.getCurrentChoice())).getValueAsString();
    }
    
    public String[] getValuesAsString() throws XPropertyException {
        return this.delegateObject.getValuesAsString();
    }
    
    public void setValueAsString(final String s) throws XPropertyException, XPropertyValueIsNotValid, XPropertySettingReadOnly {
        this.setValue(((IDatatypeModelAsString)DatatypeModelMapping.getDatatypeModel(this.getCurrentChoice())).toObject(s));
    }
    
    public boolean isValidValueAsString(final String s) throws XPropertyException {
        final IDatatypeModelAsString datatypeModelAsString = (IDatatypeModelAsString)DatatypeModelMapping.getDatatypeModel(this.getCurrentChoice());
        Object object;
        try {
            object = datatypeModelAsString.toObject(s);
        }
        catch (XPropertyValueIsNotValid xPropertyValueIsNotValid) {
            return false;
        }
        return this.isValidValue(object);
    }
    
    public Object toObject(final String s) throws XPropertyException, XPropertyValueIsNotValid {
        return ((IDatatypeModelAsString)DatatypeModelMapping.getDatatypeModel(this.getCurrentChoice())).toObject(s);
    }
    
    public String normalize(final String s) throws XPropertyException {
        return ((IDatatypeModelAsString)DatatypeModelMapping.getDatatypeModel(this.getCurrentChoice())).normalize(s);
    }
    
    public Enumeration getItems() throws XPropertyException {
        return this.delegateObject.getChoices();
    }
    
    public void setCurrentChoiceByIndex(final int value) throws XPropertyException {
        this.delegateObject.setValuesByIndices(new Integer[] { new Integer(value) });
    }
    
    public void setCurrentChoice(final Object o) throws XPropertyException {
        this.delegateObject.setValues(new Object[] { o });
    }
    
    public Object toObject(final Object o) throws XPropertyException {
        return this.delegateObject.toObject(o);
    }
}

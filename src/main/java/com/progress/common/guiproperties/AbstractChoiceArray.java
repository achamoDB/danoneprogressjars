// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;
import java.util.Vector;

public abstract class AbstractChoiceArray implements IDatatypeModelAsDynamicChoices
{
    protected Vector choices;
    protected Object[] domain;
    private boolean caseSensitive;
    
    public void setCaseSensitivity(final boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
    
    AbstractChoiceArray() {
        this.choices = null;
        this.domain = null;
        this.caseSensitive = false;
        this.domain = null;
    }
    
    AbstractChoiceArray(final Object[] domain) {
        this.choices = null;
        this.domain = null;
        this.caseSensitive = false;
        this.domain = domain;
    }
    
    public void setChoices(final Object[] domain) {
        this.domain = domain;
    }
    
    public Object[] domain() {
        return this.domain;
    }
    
    public int findIndex(final Object obj) throws XPropertyException {
        for (int i = 0; i < this.domain.length; ++i) {
            if (obj.equals(this.domain[i])) {
                return i;
            }
        }
        throw new XPropertyException("No such element: " + obj);
    }
    
    protected int find(final String s) throws XPropertyException {
        final String trim = s.trim();
        for (int i = 0; i < this.domain.length; ++i) {
            final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(this.domain[i]);
            if (!(datatypeModel instanceof IDatatypeSingular)) {
                throw new XPropertyValueIsNotValid(trim);
            }
            final String valueAsString = ((IDatatypeSingular)datatypeModel).getValueAsString();
            if (this.caseSensitive) {
                if (trim.equals(valueAsString)) {
                    return i;
                }
            }
            else if (valueAsString.equalsIgnoreCase(valueAsString)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isValidValue(final Object o) {
        try {
            this.findIndex(o);
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    protected void addValueI(final Vector vector, final int value) {
        final Enumeration<Integer> elements = vector.elements();
        while (elements.hasMoreElements()) {
            if (value == elements.nextElement()) {
                return;
            }
        }
        vector.addElement(new Integer(value));
    }
    
    public void setValues(final Object[] array) throws XPropertyException {
        final Vector choices = new Vector();
        for (int i = 0; i < array.length; ++i) {
            this.addValueI(choices, this.findIndex(array[i]));
        }
        this.choices = choices;
    }
    
    protected void setValuesAsString(final String[] array) throws XPropertyException {
        final Vector choices = new Vector();
        for (int i = 0; i < array.length; ++i) {
            final int find = this.find(array[i]);
            if (find < 0) {
                throw new XPropertyValueIsNotValid("" + array[i]);
            }
            this.addValueI(choices, find);
        }
        this.choices = choices;
    }
    
    public void setValuesByIndices(final Integer[] array) throws XPropertyException {
        final Vector choices = new Vector();
        for (int i = 0; i < array.length; ++i) {
            if (array[i] < 0 || array[i] > this.domain.length - 1) {
                throw new XPropertyValueIsNotValid("" + array[i]);
            }
            this.addValueI(choices, array[i]);
        }
        this.choices = choices;
    }
    
    public String[] getValuesAsString() throws XPropertyException {
        if (this.choices == null && this.domain.length > 0) {
            (this.choices = new Vector()).addElement(new Integer[0]);
        }
        final String[] array = new String[this.choices.size()];
        int n = 0;
        final Enumeration<Integer> elements = (Enumeration<Integer>)this.choices.elements();
        while (elements.hasMoreElements()) {
            array[n] = ((IDatatypeSingular)DatatypeModelMapping.getDatatypeModel(this.domain[elements.nextElement()])).getValueAsString();
            ++n;
        }
        return array;
    }
    
    public Object[] getValues() throws XPropertyException {
        if (this.choices == null && this.domain.length > 0) {
            (this.choices = new Vector()).addElement(new Integer[0]);
        }
        final Object[] array = new Object[this.choices.size()];
        int n = 0;
        final Enumeration<Integer> elements = (Enumeration<Integer>)this.choices.elements();
        while (elements.hasMoreElements()) {
            array[n] = this.domain[elements.nextElement()];
            ++n;
        }
        return array;
    }
    
    public Integer[] getValuesByIndices() {
        if (this.choices == null && this.domain.length > 0) {
            (this.choices = new Vector()).addElement(new Integer[0]);
        }
        final Integer[] array = new Integer[this.choices.size()];
        int n = 0;
        final Enumeration<Integer> elements = (Enumeration<Integer>)this.choices.elements();
        while (elements.hasMoreElements()) {
            array[n] = elements.nextElement();
            ++n;
        }
        return array;
    }
    
    public Enumeration getChoices() {
        final Vector<Object> vector = new Vector<Object>();
        for (int i = 0; i < this.domain.length; ++i) {
            vector.addElement(this.domain[i]);
        }
        return vector.elements();
    }
    
    public void addChoice(final Object obj) throws XPropertyValueIsNotValid {
        int i;
        for (i = 0; i < this.domain.length; ++i) {
            if (this.domain[i].equals(obj)) {
                return;
            }
        }
        this.domain[i] = obj;
    }
    
    public Object toObject(final Object o) throws XPropertyException {
        final ChoiceArrayWrapper choiceArrayWrapper = new ChoiceArrayWrapper(this.domain);
        choiceArrayWrapper.setValues(new Object[] { o });
        return choiceArrayWrapper;
    }
    
    public Object toObject(final String[] valuesAsString) throws XPropertyException {
        final ChoiceArrayWrapper choiceArrayWrapper = new ChoiceArrayWrapper(this.domain);
        choiceArrayWrapper.setValuesAsString(valuesAsString);
        return choiceArrayWrapper;
    }
    
    public Object toObject(final String s) throws XPropertyException {
        return this.toObject(new String[] { s });
    }
}

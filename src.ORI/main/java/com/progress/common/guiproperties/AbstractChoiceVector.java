// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Enumeration;
import java.util.Vector;

public abstract class AbstractChoiceVector implements IDatatypeModelAsDynamicChoices
{
    protected Vector choices;
    protected Vector domain;
    private boolean caseSensitive;
    
    public void setCaseSensitivity(final boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
    
    AbstractChoiceVector() {
        this.choices = null;
        this.domain = null;
        this.caseSensitive = false;
        this.domain = new Vector();
    }
    
    AbstractChoiceVector(final Vector domain) {
        this.choices = null;
        this.domain = null;
        this.caseSensitive = false;
        this.domain = domain;
    }
    
    public void setChoices(final Object[] array) {
        final Vector<Object> domain = new Vector<Object>();
        for (int i = 0; i < array.length; ++i) {
            domain.addElement(array[i]);
        }
        this.domain = domain;
    }
    
    public Vector domain() {
        return this.domain;
    }
    
    public int findIndex(final Object o) throws XPropertyException {
        final int index = this.domain.indexOf(o);
        if (index >= 0) {
            return index;
        }
        throw new XPropertyException("No such element: " + o);
    }
    
    protected int find(final String s) throws XPropertyException {
        final String trim = s.trim();
        for (int i = 0; i < this.domain.size(); ++i) {
            final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(this.domain.elementAt(i));
            if (!(datatypeModel instanceof IDatatypeSingular)) {
                throw new XPropertyValueIsNotValid(trim);
            }
            final String valueAsString = ((IDatatypeSingular)datatypeModel).getValueAsString();
            if (this.caseSensitive) {
                if (trim.equals(valueAsString)) {
                    return i;
                }
            }
            else if (trim.equalsIgnoreCase(valueAsString)) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isValidValue(final Object o) {
        try {
            if (!(o instanceof String)) {
                this.findIndex(o);
            }
            else {
                this.find((String)o);
            }
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
            final Object o = array[i];
            int n;
            if (!(o instanceof String)) {
                n = this.findIndex(o);
            }
            else {
                n = this.find((String)o);
            }
            this.addValueI(choices, n);
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
            if (array[i] < 0 || array[i] > this.domain.size() - 1) {
                throw new XPropertyValueIsNotValid("" + array[i]);
            }
            this.addValueI(choices, array[i]);
        }
        this.choices = choices;
    }
    
    public String[] getValuesAsString() throws XPropertyException {
        if (this.choices == null && this.domain.size() > 0) {
            (this.choices = new Vector()).addElement(new Integer[0]);
        }
        final String[] array = new String[this.choices.size()];
        int n = 0;
        final Enumeration<Integer> elements = (Enumeration<Integer>)this.choices.elements();
        while (elements.hasMoreElements()) {
            array[n] = ((IDatatypeSingular)DatatypeModelMapping.getDatatypeModel(this.domain.elementAt(elements.nextElement()))).getValueAsString();
            ++n;
        }
        return array;
    }
    
    public Object[] getValues() throws XPropertyException {
        if (this.choices == null && this.domain.size() > 0) {
            (this.choices = new Vector()).addElement(new Integer[0]);
        }
        final Object[] array = new Object[this.choices.size()];
        int n = 0;
        final Enumeration<Integer> elements = (Enumeration<Integer>)this.choices.elements();
        while (elements.hasMoreElements()) {
            final Integer n2 = elements.nextElement();
            if (n2 == -1) {
                continue;
            }
            array[n] = this.domain.elementAt(n2);
            ++n;
        }
        return array;
    }
    
    public Integer[] getValuesByIndices() {
        if (this.choices == null && this.domain.size() > 0) {
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
        return this.domain.elements();
    }
    
    public void addChoice(final Object o) throws XPropertyValueIsNotValid {
        if (this.domain.contains(o)) {
            return;
        }
        try {
            this.domain.addElement(o);
        }
        catch (Throwable t) {
            throw new XPropertyValueIsNotValid(o.toString());
        }
    }
    
    public Object toObject(final Object o) throws XPropertyException {
        final ChoiceVectorWrapper choiceVectorWrapper = new ChoiceVectorWrapper(this.domain);
        choiceVectorWrapper.setValues(new Object[] { o });
        return choiceVectorWrapper;
    }
    
    public Object toObject(final String[] valuesAsString) throws XPropertyException {
        final ChoiceVectorWrapper choiceVectorWrapper = new ChoiceVectorWrapper(this.domain);
        choiceVectorWrapper.setValuesAsString(valuesAsString);
        return choiceVectorWrapper;
    }
    
    public Object toObject(final String s) throws XPropertyException {
        return this.toObject(new String[] { s });
    }
}

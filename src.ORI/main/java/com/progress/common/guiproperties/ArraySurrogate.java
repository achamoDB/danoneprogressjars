// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

class ArraySurrogate extends DatatypeMultiple implements IDatatypeModelAsList
{
    Object makeInstance() {
        return new Object[0];
    }
    
    protected void setBaseValueField(final Object o) throws XPropertyValueIsNotValid {
        if (!(o instanceof Object[])) {
            throw new XPropertyValueIsNotValid("" + o);
        }
        super.setBaseValueField(o);
    }
    
    public int findIndex(final Object o) throws XPropertyException {
        final Object[] array = (Object[])super.value;
        for (int i = 0; i < array.length; ++i) {
            if (o.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public Object[] getValues() throws XPropertyException {
        return ((Object[])super.value).clone();
    }
    
    public void setValues(final Object[] array) throws XPropertyException {
        super.setBaseValueField(array.clone());
    }
    
    public void addValue(final Object o) throws XPropertyException {
        final Object[] array = (Object[])super.value;
        final Object[] baseValueField = new Object[array.length + 1];
        int i;
        for (i = 0; i < array.length; ++i) {
            baseValueField[i] = array[i];
        }
        baseValueField[i] = o;
        super.setBaseValueField(baseValueField);
    }
    
    public void addValue(final Object o, final int n) throws XPropertyException {
        final Object[] array = (Object[])super.value;
        final Object[] baseValueField = new Object[array.length + 1];
        for (int i = 0; i < n; ++i) {
            baseValueField[i] = array[i];
        }
        baseValueField[n] = o;
        for (int j = n; j < array.length; ++j) {
            baseValueField[j + 1] = array[j];
        }
        super.setBaseValueField(baseValueField);
    }
    
    public void removeValue(final int n) throws XPropertyValueIsNotValid {
        final Object[] array = (Object[])super.value;
        final Object[] baseValueField = new Object[array.length - 1];
        int n2 = 0;
        for (int i = 0; i < array.length; ++i) {
            if (i != n) {
                baseValueField[n2] = array[i];
                ++n2;
            }
        }
        super.setBaseValueField(baseValueField);
    }
    
    public void removeValue(final Object obj) throws XPropertyValueIsNotValid {
        final Object[] array = (Object[])super.value;
        final Object[] baseValueField = new Object[array.length - 1];
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!array[i].equals(obj)) {
                baseValueField[n] = array[i];
                ++n;
            }
        }
        super.setBaseValueField(baseValueField);
    }
    
    public String[] getValuesAsString() throws XPropertyException {
        final Object[] array = (Object[])super.value;
        final String[] array2 = new String[array.length];
        for (int i = 0; i < array.length; ++i) {
            final IPropertyDatatype datatypeModel = DatatypeModelMapping.getDatatypeModel(array[i]);
            if (!(datatypeModel instanceof IDatatypeSingular)) {
                throw new XPropertyException("Composite datatype members must be scalars");
            }
            array2[i] = ((IDatatypeSingular)datatypeModel).getValueAsString();
        }
        return array2;
    }
    
    public boolean isValidValue(final Object o) {
        return true;
    }
    
    public Object toObject(final String s) {
        return s;
    }
}

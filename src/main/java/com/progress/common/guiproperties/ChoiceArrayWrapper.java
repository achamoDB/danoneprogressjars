// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public class ChoiceArrayWrapper extends AbstractChoiceArrayWrapper implements IDatatypeSingular
{
    public ChoiceArrayWrapper() {
    }
    
    public ChoiceArrayWrapper(final Object[] array) {
        super(array);
    }
    
    public void setValue(final Object o) throws XPropertyException {
        this.setValues(new Object[] { o });
    }
    
    public void setValues(final Object[] values) throws XPropertyException {
        if (values.length > 1) {
            throw new XPropertyException("Datatype " + this.getClass() + " should not have multiple values.");
        }
        super.setValues(values);
    }
    
    public void setValuesByIndices(final Integer[] valuesByIndices) throws XPropertyException {
        if (valuesByIndices.length > 1) {
            throw new XPropertyException("Datatype " + this.getClass() + " should not have multiple values.");
        }
        super.setValuesByIndices(valuesByIndices);
    }
    
    public Object getValue() throws XPropertyException {
        final Object[] values = this.getValues();
        if (values.length > 1) {
            throw new XPropertyException("Datatype " + this.getClass() + " should not have multiple values.");
        }
        return values[0];
    }
    
    public String getValueAsString() throws XPropertyException {
        final String[] valuesAsString = this.getValuesAsString();
        if (valuesAsString.length > 1) {
            throw new XPropertyException("Datatype " + this.getClass() + " should not have multiple values.");
        }
        return valuesAsString[0];
    }
}

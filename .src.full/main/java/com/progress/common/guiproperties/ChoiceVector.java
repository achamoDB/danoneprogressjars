// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public class ChoiceVector extends AbstractChoiceVector implements IDatatypeSingular
{
    public void setValue(final Object o) throws XPropertyException {
        this.setValues(new Object[] { o });
    }
    
    public void setValues(final Object[] values) throws XPropertyException {
        if (values.length > 1) {
            throw new XPropertyException("Datatype " + this.getClass() + " should not have multiple values.");
        }
        super.setValues(values);
    }
    
    public void setValuesByIndex(final Integer[] values) throws XPropertyException {
        if (values.length > 1) {
            throw new XPropertyException("Datatype " + this.getClass() + " should not have multiple values.");
        }
        super.setValues(values);
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

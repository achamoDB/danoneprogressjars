// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public class MultipleChoiceVector extends AbstractChoiceVector implements IDatatypeMultiple
{
    public void addValue(final Object o) throws XPropertyException {
        this.addValueI(super.choices, this.findIndex(o));
    }
}

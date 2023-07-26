// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

public class MultipleChoiceArrayWrapper extends AbstractChoiceArrayWrapper implements IDatatypeMultiple
{
    public void addValue(final Object o) throws XPropertyException {
        super.choices.addElement(new Integer(this.findIndex(o)));
    }
}

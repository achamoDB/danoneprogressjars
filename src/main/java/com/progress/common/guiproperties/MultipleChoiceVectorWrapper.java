// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Vector;

public class MultipleChoiceVectorWrapper extends AbstractChoiceVectorWrapper implements IDatatypeMultiple
{
    public MultipleChoiceVectorWrapper(final Vector vector) {
        super(vector);
    }
    
    public void addValue(final Object o) throws XPropertyException {
        super.choices.addElement(new Integer(this.findIndex(o)));
    }
}

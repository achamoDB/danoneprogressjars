// 
// Decompiled by Procyon v0.5.36
// 

package com.progress.common.guiproperties;

import java.util.Vector;
import java.util.Enumeration;

class EnumerationSurrogate extends VectorSurrogate implements IDatatypeModelAsList
{
    protected void setBaseValueField(final Object obj) throws XPropertyValueIsNotValid {
        if (!(obj instanceof Enumeration)) {
            throw new XPropertyValueIsNotValid("" + obj);
        }
        final Vector<Object> baseValueField = new Vector<Object>();
        super.setBaseValueField(baseValueField);
        final Enumeration enumeration = (Enumeration)obj;
        while (enumeration.hasMoreElements()) {
            baseValueField.addElement(enumeration.nextElement());
        }
    }
}
